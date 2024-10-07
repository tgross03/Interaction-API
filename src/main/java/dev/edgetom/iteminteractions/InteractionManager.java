package dev.edgetom.iteminteractions;

import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

public class InteractionManager {

    private final Plugin plugin;

    @Getter
    private final NamespacedKey persistentDataContainerKey;

    private final HashMap<String, InteractionBuilder> interactionBuilders;
    private final HashMap<UUID, Interaction> interactions;

    public InteractionManager(Plugin plugin) {
        this.plugin = plugin;
        this.persistentDataContainerKey = new NamespacedKey(plugin, UUID.randomUUID().toString().toLowerCase());

        this.interactionBuilders = new HashMap<>();
        this.interactions = new HashMap<>();
    }

    public void registerInteractionBuilder(InteractionBuilder builder) {
        interactionBuilders.put(builder.build().getInteractionEntry(), builder);
    }

    public boolean isRegisteredInteraction(String interactionEntry) {
        return interactionBuilders.get(interactionEntry) != null;
    }

    public InteractionBuilder getInteractionBuilder(String interactionEntry) {
        return interactionBuilders.get(interactionEntry);
    }

    public Interaction getInteractionByUUID(UUID uuid) {
        return interactions.get(uuid);
    }

    public Interaction createInteraction(String interactionEntry) {
        InteractionBuilder interactionBuilder = interactionBuilders.get(interactionEntry);

        if (interactionBuilder == null) return null;

        Interaction interaction = interactionBuilder.build();
        interactions.put(interaction.getUUID(), interaction);

        return interaction;
    }

}
