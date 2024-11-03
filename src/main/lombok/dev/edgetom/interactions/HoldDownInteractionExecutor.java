package dev.edgetom.interactions;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * A different variant of an {@link InteractionExecutor} which can be used if an interaction
 * requires players to <strong>hold</strong> an interaction with a specific item (e.g. press RIGHT CLICK with
 * an item for 2 seconds (40 ticks)).
 * <p>
 * Internally it is not possible to check if someone actually holds a button. Instead, the system checks if the
 * server registers interactions within a predefined time (or tick) margin. If for example the server registers a
 * click with the specified item every 5 ticks, it will count it as if the interaction is being held continuously.
 * The time margin can be changed by calling {@link InteractionManager#setHoldDownEventTriggerTicks(long)} or in the
 * constructor of the {@link InteractionManager}.
 */

@Setter
@Getter
@SuppressWarnings("unused")
public abstract class HoldDownInteractionExecutor extends InteractionExecutor {

    /**
     * The amount of ticks the interaction has to be held continuously to be executed.
     */
    protected long holdDownDuration;

    /**
     * Create an {@link InteractionExecutor}.
     *
     * @param interactionManager The manager defined in the plugins main class controlling the interactions.
     * @param interactionKey     The key to be put into the {@link org.bukkit.persistence.PersistentDataContainer}
     *                           of the item to interact with.
     * @param holdDownDuration   The amount of ticks the interaction has to be held continuously to be executed.
     * @param placeable          Whether the {@link ItemStack} the player interacts with should be able to be placed.
     *                           Also affects whether block interactions (e.g. pressing buttons is possible with the item.
     * @param cooldown           The ticks that the interaction should be on cooldown after the interaction. Set {@code 0} for no cooldown.
     * @param cooldownInstant    Whether the cooldown should be applied immediately after the interaction triggers.
     *                           If set to false the cooldown has to applied by using the {@link #addCooldown(Player, Material)} method
     * @param actions            The {@link Action}s that should trigger the executor.
     */
    public HoldDownInteractionExecutor(@NotNull InteractionManager interactionManager, @NotNull String interactionKey, long holdDownDuration, boolean placeable, int cooldown, boolean cooldownInstant, Action... actions) {
        super(interactionManager, interactionKey, placeable, cooldown, cooldownInstant, actions);
        this.holdDownDuration = holdDownDuration;
    }

    /**
     * Create an {@link HoldDownInteractionExecutor}.
     *
     * @param interactionManager The manager defined in the plugins main class controlling the interactions.
     * @param interactionKey     The {@link String} to be put into the {@link org.bukkit.persistence.PersistentDataContainer}
     *                           of the item to interact with.
     * @param holdDownDuration   The amount of ticks the interaction has to be held continuously to be executed.
     * @param placeable          Whether the {@link ItemStack} the player interacts with should be able to be placed.
     *                           Also affects whether block interactions (e.g. pressing buttons) is possible with the item.
     * @param cooldown           The ticks that the interaction should be on cooldown after the interaction. Set {@code 0} for no cooldown.
     * @param cooldownInstant    Whether the cooldown should be applied immediately after the interaction triggers.
     *                           If set to false the cooldown has to applied by using the {@link #addCooldown(Player, Material)} method
     * @param actionClass        The {@link ActionClass} that categorizes the {@link Action}s which should trigger the executor.
     */
    public HoldDownInteractionExecutor(@NotNull InteractionManager interactionManager, @NotNull String interactionKey, long holdDownDuration, boolean placeable, int cooldown, boolean cooldownInstant, ActionClass actionClass) {
        super(interactionManager, interactionKey, placeable, cooldown, cooldownInstant, actionClass);
        this.holdDownDuration = holdDownDuration;
    }

    /**
     * Create an {@link InteractionExecutor} <strong>without a cooldown</strong>.
     *
     * @param interactionManager The manager defined in the plugins main class controlling the interactions.
     * @param interactionKey     The {@link String} to be put into the {@link org.bukkit.persistence.PersistentDataContainer}
     *                           of the item to interact with.
     * @param holdDownDuration   The amount of ticks the interaction has to be held continuously to be executed.
     * @param placeable          Whether the {@link ItemStack} the player interacts with should be able to be placed.
     *                           Also affects whether block interactions (e.g. pressing buttons) is possible with the item.
     *                           If set to false the cooldown has to applied by using the {@link #addCooldown(Player, Material)} method
     * @param actions            The {@link Action}s that should trigger the executor.
     */
    public HoldDownInteractionExecutor(@NotNull InteractionManager interactionManager, @NotNull String interactionKey, long holdDownDuration, boolean placeable, Action... actions) {
        super(interactionManager, interactionKey, placeable, actions);
        this.holdDownDuration = holdDownDuration;
    }

    /**
     * Create an {@link InteractionExecutor} <strong>without a cooldown</strong>.
     *
     * @param interactionManager The manager defined in the plugins main class controlling the interactions.
     * @param interactionKey     The {@link String} to be put into the {@link org.bukkit.persistence.PersistentDataContainer}
     *                           of the item to interact with.
     * @param holdDownDuration   The amount of ticks the interaction has to be held continuously to be executed.
     * @param placeable          Whether the {@link ItemStack} the player interacts with should be able to be placed.
     *                           Also affects whether block interactions (e.g. pressing buttons) is possible with the item.
     *                           If set to false the cooldown has to applied by using the {@link #addCooldown(Player, Material)} method
     * @param actionClass        The {@link ActionClass} that categorizes the {@link Action}s which should trigger the executor.
     */
    public HoldDownInteractionExecutor(@NotNull InteractionManager interactionManager, @NotNull String interactionKey, long holdDownDuration, boolean placeable, ActionClass actionClass) {
        super(interactionManager, interactionKey, placeable, actionClass);
        this.holdDownDuration = holdDownDuration;
    }

    /**
     * The method which is called every tick which passes after the interaction is triggered for the first time
     * until the interaction is finished or canceled.
     *
     * @param player              The player who caused the interaction.
     * @param tickSinceFirstClick The ticks which elapsed since the interaction was triggered for the first time.
     * @param ticksSinceLastClick The ticks which elapsed since the last click was registered.
     */
    public abstract void onTickCheck(Player player, long tickSinceFirstClick, long ticksSinceLastClick);

    /**
     * The method which is called if the interaction is canceled because the player stopped interacting with the item.
     *
     * @param player              The player who caused the interaction.
     * @param tickSinceFirstClick The ticks which elapsed since the interaction was triggered for the first time.
     * @param ticksSinceLastClick The ticks which elapsed since the last click was registered.
     */
    public abstract void onCancel(Player player, long tickSinceFirstClick, long ticksSinceLastClick);

}
