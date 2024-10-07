package dev.edgetom.iteminteractions;

import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

@AllArgsConstructor
public class InteractionListener implements Listener {

    private final InteractionManager interactionManager;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (!event.hasItem()) return;
        if (!event.getItem().hasItemMeta()) return;
        if (!event.getItem().getItemMeta().getPersistentDataContainer()
                .has(interactionManager.getPersistentDataContainerKey(), PersistentDataType.STRING)) return;

        String interactionValue = event.getItem().getItemMeta().getPersistentDataContainer()
                .get(interactionManager.getPersistentDataContainerKey(), PersistentDataType.STRING);

        Interaction interaction = interactionManager.createInteraction(interactionValue);
        interaction.execute();

        if (interaction.getCooldown() > 0 && interaction.isCooldownInstant())
            interaction.addCooldown(event.getPlayer(), event.getItem());

    }

}
