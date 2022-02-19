package fr.twah2em.reports.inventories;

import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import fr.twah2em.reports.inventories.utils.InventoryUtils;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ReportChooseReasonTypeFastInv extends FastInv {
    private final Player targetPlayer;

    public ReportChooseReasonTypeFastInv(Player targetPlayer) {
        super(9, "§cReport -> " + targetPlayer.getName());
        this.targetPlayer = targetPlayer;

        setItem(2, new ItemBuilder(Material.GOLDEN_SWORD).name("§cCheats").addLore("§7Report §c" +
                targetPlayer.getName(), "§7for a §cCheat §7among the choices.").build());
        setItems(3, 5, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name(" ").build());
        setItem(6, new ItemBuilder(Material.WRITABLE_BOOK).name("§eChat")
                .addLore("§7Report §c" +
                        targetPlayer.getName(), "§7for a problem in the §eChat", "§7among the choices")
                .build());

        InventoryUtils.setEmptySlotsToGlassPain(this.getInventory());
    }

    @Override
    protected void onClick(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        final ItemStack currentItem = event.getCurrentItem();

        if (!InventoryUtils.isValid(currentItem)) return;

        final TextComponent component = (TextComponent) currentItem.getItemMeta().displayName().children().get(0);

        if (component.content().equals("Cheats") || component.content().equals("Chat")) {
            new ReportWithReasonFastInv(component, targetPlayer).open(player);
        }
    }
}
