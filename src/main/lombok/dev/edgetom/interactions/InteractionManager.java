package dev.edgetom.interactions;

import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

/**
 * The InteractionManager controls the assignment of the interactions that are
 * called in the {@link InteractionExecutor}s.
 */
public class InteractionManager {

    /**
     * The unique key which is used for the entries in the {@link org.bukkit.persistence.PersistentDataContainer}
     * of items which have an InteractionExecutor. The key is generated automatically when initializing this manager.
     */
    @Getter
    private final NamespacedKey persistentDataContainerKey;

    /**
     * A {@link HashMap} containing the interactions
     */
    private final HashMap<String, InteractionExecutor> interactions;

    /**
     * Creates a new {@link InteractionManager}.
     *
     * @param plugin The plugin instance
     */
    public InteractionManager(Plugin plugin) {
        this.persistentDataContainerKey = new NamespacedKey(plugin, UUID.randomUUID().toString().toLowerCase());
        this.interactions = new HashMap<>();
        plugin.getServer().getPluginManager().registerEvents(new InteractionListener(this), plugin);
    }

    /**
     * Get an {@link InteractionExecutor} by the assigned key, which is put into the {@link org.bukkit.persistence.PersistentDataContainer}
     * of the items which should trigger the
     *
     * @param key The key which assigns items to the interaction
     * @return The {@link InteractionExecutor} associated with the key
     */
    public InteractionExecutor getInteractionExecutorByKey(String key) {
        return interactions.get(key);
    }

    /**
     * Registers an {@link InteractionExecutor} so that it can be called.
     *
     * @param executor The executor to register
     */
    public void registerInteraction(InteractionExecutor executor) {
        interactions.put(executor.getInteractionKey(), executor);
    }


    /**
     * Unregisters an {@link InteractionExecutor} so that it can't be called anymore.
     * @param executor The executor to unregister.
     */
    public void unregisterInteraction(InteractionExecutor executor) {
        interactions.remove(executor.getInteractionKey());
    }

}
