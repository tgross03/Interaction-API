package dev.edgetom.iteminteractions;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

public class ItemInteractionManager {

    private final Plugin plugin;
    private final NamespacedKey persistentDataContainerKey;

    private final HashMap<String, ItemInteractionBuilder> interactionBuilders;
    private final HashMap<UUID, ItemInteraction> itemInteractions;


    public void registerItemInteractionBuilder(ItemInteractionBuilder builder) {
        interactionBuilders.put(builder.getInteractionEntry(), builder);
    }

    public ItemInteractionManager(Plugin plugin) {
        this.plugin = plugin;
        this.persistentDataContainerKey = new NamespacedKey(plugin, UUID.randomUUID().toString().toLowerCase());

        this.interactionBuilders = new HashMap<>();
        this.itemInteractions = new HashMap<>();

    }
}
