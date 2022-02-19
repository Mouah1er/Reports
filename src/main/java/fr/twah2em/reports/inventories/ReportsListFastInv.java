package fr.twah2em.reports.inventories;

import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import fr.twah2em.reports.ReportsJavaPlugin;
import fr.twah2em.reports.database.ReportObject;
import fr.twah2em.reports.inventories.utils.InventoryUtils;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class ReportsListFastInv extends FastInv {
    private final int page;
    private int index;

    public ReportsListFastInv(int page) {
        super(54, "§aReports -> List");
        this.page = page;

        final List<ReportObject> reports = new ArrayList<>();
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            reports.addAll(ReportsJavaPlugin.getInstance().getReportsSQL().getReports(offlinePlayer.getUniqueId()));
        }

        if (reports.isEmpty()) {
            setItem(31, new ItemBuilder(Material.BARRIER)
                    .name("§cThere are no reports here !")
                    .build());

            return;
        }

        if (page != 1) {
            setItem(48, new ItemBuilder(Material.ACACIA_BUTTON)
                    .name("§aPrevious page")
                    .build());
        }

        setItem(49, new ItemBuilder(Material.PAPER)
                .name("§aCurrent page: " + page)
                .build());

        final int maxItemsPerPage = 45;
        for (int i = 0; i < maxItemsPerPage; i++) {
            index = maxItemsPerPage * page + i;

            if (i > reports.size() - 1) break;

            final ReportObject reportObject = reports.get(i);
            if (reportObject != null) {
                final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(reportObject.getUniqueId());
                final Player player = Bukkit.getPlayer(reportObject.getUniqueId());
                final String date = reportObject.getDate();
                final OfflinePlayer offlineAuthor = Bukkit.getOfflinePlayer(reportObject.getAuthorUniqueId());
                final String reason = reportObject.getReason();

                final int reportId = ReportsJavaPlugin.getInstance().getReportsSQL().getReportId(reportObject);

                addItem(new ItemBuilder(Material.BOOK)
                        .name("§c" + (player == null ? offlinePlayer.getName() + " §7(Offline) " : player.getName() + " §7(Online)")
                                + " §7-> §e" + reason)
                        .lore("§eDate: " + date.substring(0, 16), "§aAuthor: " + offlineAuthor.getName(),
                                " ",
                                "§7Right click to teleport to the player if",
                                "§7he's connected",
                                "§7Left click to delete the report",
                                "",
                                "§7§oId: " + reportId)
                        .meta(itemMeta -> itemMeta.getPersistentDataContainer().set(
                                new NamespacedKey(ReportsJavaPlugin.getInstance(), "id"), PersistentDataType.INTEGER, reportId))
                        .build());
            }
        }

        if (!(index + 1 >= reports.size())) {
            setItem(50, new ItemBuilder(Material.ACACIA_BUTTON)
                    .name("§aNext page")
                    .build());
        }
    }

    @Override
    protected void onClick(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        final ItemStack currentItem = event.getCurrentItem();

        if (!InventoryUtils.isValid(currentItem)) return;

        if (currentItem.getType() != Material.BOOK && currentItem.getType() != Material.PAPER) {
            final TextComponent component = (TextComponent) currentItem.getItemMeta().displayName().children().get(0);

            if (component.content().equals("Previous page")) {
                new ReportsListFastInv(page - 1).open(player);
            } else {
                new ReportsListFastInv(page + 1).open(player);
            }
        } else if (currentItem.getType() == Material.BOOK) {
            final Integer reportId = currentItem.getItemMeta().getPersistentDataContainer().get(
                    new NamespacedKey(ReportsJavaPlugin.getInstance(), "id"), PersistentDataType.INTEGER);

            if (event.getClick() == ClickType.RIGHT) {
                final ReportObject reportObject = ReportsJavaPlugin.getInstance().getReportsSQL().getReport(reportId);
                final Player targetPlayer = Bukkit.getPlayer(reportObject.getUniqueId());

                player.closeInventory();
                if (targetPlayer == null) {
                    player.sendMessage("§cError: The player probably logged out while you were in the menu !");
                } else {
                    player.teleport(targetPlayer);
                    player.sendMessage("§aYou have been teleported to the player !");
                }
            } else if (event.getClick() == ClickType.LEFT) {
                ReportsJavaPlugin.getInstance().getReportsSQL().deleteReport(reportId);

                player.closeInventory();
                player.sendMessage("§aYou have successfully delete the report !");
            }
        }
    }
}
