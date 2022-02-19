package fr.twah2em.reports.inventories;

import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import fr.twah2em.reports.inventories.utils.InventoryUtils;
import fr.twah2em.reports.reasons.Reason;
import fr.twah2em.reports.reasons.Reasons;
import net.kyori.adventure.text.TextComponent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ReportWithReasonFastInv extends FastInv {
    private final Player targetPlayer;

    public ReportWithReasonFastInv(TextComponent reason, Player targetPlayer) {
        super(9, "§cReport -> " + targetPlayer.getName() + " §7(" + ChatColor.of(reason.color().asHexString()) +
                reason.content() + "§7)");
        this.targetPlayer = targetPlayer;

        final List<Reason> reasonList = new ArrayList<>();

        if (reason.content().equals("Cheats")) reasonList.addAll(Reasons.getCheatReasons());
        else if (reason.content().equals("Chat")) reasonList.addAll(Reasons.getChatReasons());

        int slot = 1;
        for (Reason reasons : reasonList) {
            setItem(slot, new ItemBuilder(reasons.reasonMaterial())
                    .name(reasons.reason()).build());

            slot += 2;
        }

        InventoryUtils.setEmptySlotsToGlassPain(this.getInventory());
    }

    @Override
    protected void onClick(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        final ItemStack currentItem = event.getCurrentItem();

        if (!InventoryUtils.isValid(currentItem)) return;

        if (currentItem.getType() != Material.BLACK_STAINED_GLASS_PANE) {
            new ReportConfirmFastInv((TextComponent) currentItem.getItemMeta().displayName().children().get(0), targetPlayer).open(player);
        }
    }
}
