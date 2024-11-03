package dev.edgetom.interactions.utils;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

/**
 * A helper class to create and easily manage tasks created using {@link org.bukkit.scheduler.BukkitScheduler#scheduleSyncRepeatingTask(Plugin, Runnable, long, long)}.
 * Intended to be used for API-internal processes.
 */

@Getter
public class RepeatingTask implements Runnable {

    /**
     * The plugin for which the task is scheduled.
     */
    private final Plugin plugin;

    /**
     * The consumer which is executed each run of the task.
     */
    private final Consumer<RepeatingTask> consumer;

    /**
     * The tick rate of the task
     */
    private final int tickRate;

    /**
     * The id of the created task.
     */
    private int bukkitTaskId;

    /**
     * Creates and registers a {@link RepeatingTask}
     *
     * @param plugin   The plugin for which the task is scheduled.
     * @param tickRate The tick rate of the task
     * @param consumer The consumer which is executed each run of the task.
     */
    public RepeatingTask(Plugin plugin, int tickRate, Consumer<RepeatingTask> consumer) {
        this.plugin = plugin;
        this.tickRate = tickRate;
        this.consumer = consumer;
        register();
    }

    /**
     * Registers the {@link RepeatingTask}.
     */
    public void register() {
        this.bukkitTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, 0, tickRate);
    }

    /**
     * Called every run of the task.
     */
    @Override
    public void run() {
        consumer.accept(this);
    }

    /**
     * Cancels the task.
     */
    public void cancel() {
        Bukkit.getScheduler().cancelTask(bukkitTaskId);
    }
}
