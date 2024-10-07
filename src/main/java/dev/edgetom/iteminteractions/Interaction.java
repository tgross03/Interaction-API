package dev.edgetom.iteminteractions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

@Getter
@SuperBuilder(toBuilder = true)
public abstract class Interaction {

    @Getter(AccessLevel.NONE)
    private final UUID uuid;

    protected final String interactionEntry;

    protected final Action[] actions;

    @Setter
    protected boolean placeable;

    @Setter
    protected int cooldown;

    @Setter
    protected boolean cooldownInstant;

    public Interaction(String interactionEntry, boolean placeable, int cooldown, boolean cooldownInstant, Action... actions) {
        this.uuid = UUID.randomUUID();
        this.interactionEntry = interactionEntry;
        this.placeable = placeable;
        this.cooldown = cooldown;
        this.cooldownInstant = cooldownInstant;
        this.actions = actions;
    }

    public Interaction(String interactionEntry, boolean placeable, int cooldown, boolean cooldownInstant, ActionClass actionClass) {
        this(interactionEntry, placeable, cooldown, cooldownInstant, actionClass.getActions());
    }

    public Interaction(String interactionEntry, boolean placeable, Action... actions) {
        this(interactionEntry, placeable, 0, false, actions);
    }

    public Interaction(String interactionEntry, boolean placeable, ActionClass actionClass) {
        this(interactionEntry, placeable, 0, false, actionClass);
    }

    public abstract void execute();

    public ItemStack addToItem(NamespacedKey namespacedKey, ItemStack itemStack) {

        if (!itemStack.hasItemMeta())
            throw new IllegalArgumentException("The provided ItemStack must have an ItemMeta!");

        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;

        itemMeta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.STRING, this.interactionEntry);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public void addCooldown(Player player, ItemStack itemStack) {
        player.setCooldown(itemStack.getType(), this.cooldown);
    }

    public UUID getUUID() {
        return uuid;
    }

}
