package dev.edgetom.interactions;

import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

/**
 * The listener intercepts the {@link PlayerInteractEvent} and executes an {@link InteractionExecutor}
 * if it contains a registered entry in its {@link org.bukkit.persistence.PersistentDataContainer}.
 */
@AllArgsConstructor
public class InteractionListener implements Listener {

    /**
     * The {@link InteractionManager} the executors are registered in.
     */
    private final InteractionManager interactionManager;

    /**
     * The EventHandler for the {@link PlayerInteractEvent}
     *
     * @param event The intercepted event
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (!event.hasItem()) return;
        assert event.getItem() != null;

        if (!event.getItem().hasItemMeta()) return;
        assert event.getItem().getItemMeta() != null;

        if (!event.getItem().getItemMeta().getPersistentDataContainer()
                .has(interactionManager.getPersistentDataContainerKey(), PersistentDataType.STRING)) return;

        String interactionKey = event.getItem().getItemMeta().getPersistentDataContainer()
                .get(interactionManager.getPersistentDataContainerKey(), PersistentDataType.STRING);

        InteractionExecutor interactionExecutor = interactionManager.getInteractionExecutorByKey(interactionKey);

        if (interactionExecutor == null) return;
        if (Arrays.stream(interactionExecutor.getActions()).noneMatch(action -> action == event.getAction())) return;

        interactionExecutor.execute(event, event.getPlayer());

        if (interactionExecutor.getCooldown() > 0 && interactionExecutor.isCooldownInstant())
            interactionExecutor.addCooldown(event.getPlayer(), event.getItem().getType());

        if (!interactionExecutor.isPlaceable())
            event.setCancelled(true);

    }

}
