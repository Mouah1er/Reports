package fr.twah2em.reports.inventories.utils;

import fr.mrmicky.fastinv.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class InventoryUtils {

    public static void setEmptySlotsToGlassPain(Inventory inventory) {
        final List<ItemStack> content = Arrays.stream(inventory.getContents()).toList();

        for (int i = 0; i < content.size(); i++) {
            if (content.get(i) == null) {
                inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
                        .name(" ")
                        .flags(ItemFlag.HIDE_ATTRIBUTES)
                        .build());
            }
        }
    }

    public static boolean isValid(ItemStack itemStack) {
        return itemStack != null && itemStack.getItemMeta() != null && itemStack.getItemMeta().displayName() != null;
    }
}
