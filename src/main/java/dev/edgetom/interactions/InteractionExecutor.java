package dev.edgetom.interactions;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;


/**
 * <pre>
 * An {@link InteractionExecutor} can be used to intercept an interaction of a
 * {@link Player} which is intercepted by a {@link org.bukkit.event.player.PlayerInteractEvent}.
 *
 * The interaction is linked to an item using an Entry in the {@link org.bukkit.persistence.PersistentDataContainer}
 * of the item. This means that it is <strong>not</strong> specific to one special type of item but can be added to react to every
 * item that carries the {@link #interactionKey} in its {@link org.bukkit.persistence.PersistentDataContainer}.
 * <strong>
 * Warning! An instance of this class is used for every (!) interaction which uses the same {@link #interactionKey}.
 * Every variable which is specific to this instance is the same for every execution of this executor!</strong>
 *
 * </pre>
 */

@Getter
@SuppressWarnings("unused")
public abstract class InteractionExecutor {

    /**
     * The {@link InteractionManager} the executor is registered in.
     */
    @NotNull
    protected final InteractionManager interactionManager;

    /**
     * The entry which is put into the {@link org.bukkit.persistence.PersistentDataContainer} of an item to identify whether
     * an interaction is linked to it. Can be any arbitrary {@link String}.
     */
    @NotNull
    protected final String interactionKey;

    /**
     * The {@link Action}s at which the {@link InteractionExecutor} is triggered.
     */
    protected final Action[] actions;

    /**
     * Whether the {@link ItemStack} the interacting {@link Player} is holding in hand is supposed to
     * be placed on the ground. If the player is interacting with a block (e.g. a button) this will be
     * cancelled either.
     */
    @Setter
    protected boolean placeable;

    /**
     * The time the item should be on cooldown after the interaction.
     * The cooldown is specific to the {@link org.bukkit.Material} which the
     * player uses to interact.
     */
    @Setter
    protected int cooldown;

    /**
     * <pre>
     * Whether the {@link #cooldown} time is supposed to be applied instantly after the
     * player interacts.
     *
     * If this is set to false the cooldown has to be applied
     * manually by calling the {@link #addCooldown(Player, Material)} method from the
     * instance of the {@link InteractionExecutor}.
     * </pre>
     */
    @Setter
    protected boolean cooldownInstant;

    /**
     * Create an {@link InteractionExecutor}.
     *
     * @param interactionManager The manager defined in the plugins main class controlling the interactions.
     * @param interactionKey     The key to be put into the {@link org.bukkit.persistence.PersistentDataContainer}
     *                           of the item to interact with.
     * @param placeable          Whether the {@link ItemStack} the player interacts with should be able to be placed.
     *                           Also affects whether block interactions (e.g. pressing buttons is possible with the item.
     * @param cooldown           The time that the interaction should be on cooldown after the interaction. Set {@code 0} for no cooldown.
     * @param cooldownInstant    Whether the cooldown should be applied immediately after the interaction triggers.
     *                           If set to false the cooldown has to applied by using the {@link #addCooldown(Player, Material)} method
     * @param actions            The {@link Action}s that should trigger the executor.
     */
    public InteractionExecutor(@NotNull InteractionManager interactionManager, @NotNull String interactionKey, boolean placeable,
                               int cooldown, boolean cooldownInstant, Action... actions) {
        this.interactionManager = interactionManager;
        this.interactionKey = interactionKey;
        this.placeable = placeable;
        this.cooldown = cooldown;
        this.cooldownInstant = cooldownInstant;
        this.actions = actions;

        interactionManager.registerInteraction(this);

    }

    /**
     * Create an {@link InteractionExecutor}.
     *
     * @param interactionManager The manager defined in the plugins main class controlling the interactions.
     * @param interactionKey     The {@link String} to be put into the {@link org.bukkit.persistence.PersistentDataContainer}
     *                           of the item to interact with.
     * @param placeable          Whether the {@link ItemStack} the player interacts with should be able to be placed.
     *                           Also affects whether block interactions (e.g. pressing buttons) is possible with the item.
     * @param cooldown           The time that the interaction should be on cooldown after the interaction. Set {@code 0} for no cooldown.
     * @param cooldownInstant    Whether the cooldown should be applied immediately after the interaction triggers.
     *                           If set to false the cooldown has to applied by using the {@link #addCooldown(Player, Material)} method
     * @param actionClass        The {@link ActionClass} that categorizes the {@link Action}s which should trigger the executor.
     */
    public InteractionExecutor(@NotNull InteractionManager interactionManager, String interactionKey, boolean placeable, int cooldown,
                               boolean cooldownInstant, ActionClass actionClass) {
        this(interactionManager, interactionKey, placeable, cooldown, cooldownInstant, actionClass.getActions());
    }

    /**
     * Create an {@link InteractionExecutor} <strong>without a cooldown</strong>.
     *
     * @param interactionManager The manager defined in the plugins main class controlling the interactions.
     * @param interactionKey     The {@link String} to be put into the {@link org.bukkit.persistence.PersistentDataContainer}
     *                           of the item to interact with.
     * @param placeable          Whether the {@link ItemStack} the player interacts with should be able to be placed.
     *                           Also affects whether block interactions (e.g. pressing buttons) is possible with the item.
     *                           If set to false the cooldown has to applied by using the {@link #addCooldown(Player, Material)} method
     * @param actions            The {@link Action}s that should trigger the executor.
     */
    public InteractionExecutor(@NotNull InteractionManager interactionManager, String interactionKey, boolean placeable, Action... actions) {
        this(interactionManager, interactionKey, placeable, 0, false, actions);
    }


    /**
     * Create an {@link InteractionExecutor} <strong>without a cooldown</strong>.
     *
     * @param interactionManager The manager defined in the plugins main class controlling the interactions.
     * @param interactionKey     The {@link String} to be put into the {@link org.bukkit.persistence.PersistentDataContainer}
     *                           of the item to interact with.
     * @param placeable          Whether the {@link ItemStack} the player interacts with should be able to be placed.
     *                           Also affects whether block interactions (e.g. pressing buttons) is possible with the item.
     *                           If set to false the cooldown has to applied by using the {@link #addCooldown(Player, Material)} method
     * @param actionClass        The {@link ActionClass} that categorizes the {@link Action}s which should trigger the executor.
     */
    public InteractionExecutor(@NotNull InteractionManager interactionManager, String interactionKey, boolean placeable, ActionClass actionClass) {
        this(interactionManager, interactionKey, placeable, 0, false, actionClass);
    }

    /**
     * The method which is called if a valid interaction with the
     * designated {@link #interactionKey} is detected.
     *
     * @param event  The triggered {@link PlayerInteractEvent}.
     * @param player The player responsible for the event.
     */
    public abstract void execute(PlayerInteractEvent event, Player player);

    /**
     * Remove the {@link #interactionKey} from the {@link org.bukkit.persistence.PersistentDataContainer}
     * of the given item. This removes every entry with the same {@link org.bukkit.NamespacedKey}.
     *
     * @param itemStack The item the key should be added to.
     * @return The modified item without the entry in the {@link org.bukkit.persistence.PersistentDataContainer}.
     */
    public ItemStack addToItem(ItemStack itemStack) {

        if (!itemStack.hasItemMeta())
            throw new IllegalArgumentException("The provided ItemStack must have an ItemMeta!");

        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;

        itemMeta.getPersistentDataContainer().remove(this.interactionManager.getPersistentDataContainerKey());
        itemStack.setItemMeta(itemMeta);

        return itemStack;

    }

    /**
     * Removes the {@link #interactionKey} to the {@link org.bukkit.persistence.PersistentDataContainer}
     * of the given item.
     *
     * @param itemStack The item the key should be added to.
     * @return The modified item containing the entry in the {@link org.bukkit.persistence.PersistentDataContainer}.
     */
    public ItemStack removeFromItem(ItemStack itemStack) {

        if (!itemStack.hasItemMeta())
            throw new IllegalArgumentException("The provided ItemStack must have an ItemMeta!");

        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;

        itemMeta.getPersistentDataContainer().set(this.interactionManager.getPersistentDataContainerKey(),
                PersistentDataType.STRING, this.interactionKey);
        itemStack.setItemMeta(itemMeta);

        return itemStack;

    }

    /**
     * Adds the {@link #cooldown} defined in the executor to the given
     * item for the given player.
     *
     * @param player   The player the cooldown should be set for.
     * @param material The item type the cooldown should be applied to.
     */
    public void addCooldown(Player player, Material material) {
        player.setCooldown(material, this.cooldown);
    }

    /**
     * Removes every cooldown defined in the executor to the given
     * item for the given player.
     *
     * @param player   The player the cooldown should be removed for.
     * @param material The item type the cooldown should be removed from.
     */
    public static void removeCooldown(Player player, Material material) {
        player.setCooldown(material, 0);
    }

}
