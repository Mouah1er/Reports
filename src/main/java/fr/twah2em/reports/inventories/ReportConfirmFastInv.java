package fr.twah2em.reports.inventories;

import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import fr.twah2em.reports.ReportsJavaPlugin;
import fr.twah2em.reports.database.ReportObject;
import fr.twah2em.reports.inventories.utils.InventoryUtils;
import net.kyori.adventure.text.TextComponent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ReportConfirmFastInv extends FastInv {
    private final TextComponent reason;
    private final Player targetPlayer;

    public ReportConfirmFastInv(TextComponent reason, Player targetPlayer) {
        super(9, "§cReport -> " + targetPlayer.getName() + " §7(Confirmation)");
        this.reason = reason;
        this.targetPlayer = targetPlayer;

        setItem(2, new ItemBuilder(Material.GREEN_WOOL)
                .name("§aYes §7(You will not be able to remove this report !)")
                .build());

        setItem(6, new ItemBuilder(Material.RED_WOOL)
                .name("§cNo")
                .build());

        InventoryUtils.setEmptySlotsToGlassPain(this.getInventory());
    }

    @Override
    protected void onClick(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        final ItemStack currentItem = event.getCurrentItem();

        if (!InventoryUtils.isValid(currentItem)) return;

        if (currentItem.getType() == Material.GREEN_WOOL) {
            ReportsJavaPlugin.getInstance().getReportsSQL().reportPlayer(new ReportObject(targetPlayer.getUniqueId(), player.getUniqueId(),
                    reason.content()));
            player.closeInventory();
            player.sendMessage("§aYou have successfully report the player §c" + targetPlayer.getName() + " §afor the reason " +
                    ChatColor.of(reason.color().asHexString()) + reason.content());
        }
    }
}
