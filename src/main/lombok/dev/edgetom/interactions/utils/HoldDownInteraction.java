package dev.edgetom.interactions.utils;

import dev.edgetom.interactions.HoldDownInteractionExecutor;
import lombok.Getter;
import org.bukkit.entity.Player;

/**
 * A class containing information about a HoldDownInteraction, meaning an interaction in which a
 * specific interaction (e.g. LEFT CLICK) has to be held in order to be executed.
 * This class is mainly used by the API to save and evaluate the interactions and is not meant to be used otherwise.
 */

@Getter
public class HoldDownInteraction {

    /**
     * The {@link HoldDownInteractionExecutor} with which this interaction was initiated.
     */
    private final HoldDownInteractionExecutor interactionExecutor;

    /**
     * The player who caused the interaction.
     */
    private final Player player;

    /**
     * The scheduled repeating task which updates the tick counts and performs the checks whether the clicks
     * arrived in time to be counted as holding the interaction.
     */
    private final RepeatingTask repeatingTask;

    /**
     * The ticks which elapsed since the interaction was first created.
     */
    private long elapsedTicks = 0;

    /**
     * The ticks which elapsed since the last {@link org.bukkit.event.player.PlayerInteractEvent} was triggered for this interaction.
     */
    private long lastCheckTicks = 0;

    /**
     * Creates a new {@link HoldDownInteraction}.
     *
     * @param interactionExecutor The {@link HoldDownInteractionExecutor} with which this interaction was initiated.
     * @param player              The player who caused the interaction.
     */
    public HoldDownInteraction(HoldDownInteractionExecutor interactionExecutor, Player player) {
        this.interactionExecutor = interactionExecutor;
        this.player = player;

        this.repeatingTask = new RepeatingTask(interactionExecutor.getInteractionManager().getPlugin(), 1, task -> {
            if (!isValid()) {
                cancel(true);
                return;
            }

            if (isFinished()) {
                this.lastCheckTicks++;
                return;
            }

            this.lastCheckTicks++;
            this.elapsedTicks++;
            this.interactionExecutor.onTickCheck(player, elapsedTicks, lastCheckTicks);
        });

        interactionExecutor.getInteractionManager().getHoldDownInteractions().put(player, this);
    }

    /**
     * Cancels the execution of all scheduled repeating tasks and unregisters the interaction.
     *
     * @param invalid Whether the interaction was canceled because the item was not clicked anymore.
     */
    public void cancel(boolean invalid) {
        if (invalid)
            interactionExecutor.onCancel(player, elapsedTicks, lastCheckTicks);

        this.repeatingTask.cancel();
        interactionExecutor.getInteractionManager().getHoldDownInteractions().remove(player);
    }

    /**
     * Called on every {@link org.bukkit.event.player.PlayerInteractEvent} which belongs to this interaction.
     *
     * @return Whether the {@link org.bukkit.event.player.PlayerInteractEvent} was called in time so that the interaction is counted as held down.
     */
    public boolean onClick() {
        this.lastCheckTicks = 0;

        if (!isValid())
            cancel(true);

        return isValid();
    }

    /**
     * @return Whether the last associated call of a {@link org.bukkit.event.player.PlayerInteractEvent} was in time to be counted as held down.
     */
    public boolean isValid() {
        return lastCheckTicks <= interactionExecutor.getInteractionManager().getHoldDownEventTriggerTicks();
    }

    /**
     * @return Whether the duration which the interaction has to be held has elapsed.
     */
    public boolean isFinished() {
        return interactionExecutor.getHoldDownDuration() <= elapsedTicks;
    }

}
