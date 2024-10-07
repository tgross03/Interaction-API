package dev.edgetom.iteminteractions;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bukkit.NamespacedKey;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

@Getter
@SuperBuilder(toBuilder = true)
public class ItemInteractionBuilder {

    private String interactionEntry;
    private final Action[] actions;

    @Setter
    protected boolean placeable;

    @Setter
    protected int cooldown;

    @Setter
    protected boolean cooldownInstantly;

    public ItemStack addToItem(NamespacedKey namespacedKey, ItemStack itemStack) {

        if (!itemStack.hasItemMeta())
            throw new IllegalArgumentException("The provided ItemStack must have an ItemMeta!");

        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;

        itemMeta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.STRING, this.interactionEntry);
        itemStack.setItemMeta(itemMeta);

        return itemStack;

    }

}
