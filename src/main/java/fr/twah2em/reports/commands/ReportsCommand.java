package fr.twah2em.reports.commands;

import fr.twah2em.reports.ReportsJavaPlugin;
import fr.twah2em.reports.inventories.ReportsListFastInv;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ReportsCommand implements CommandExecutor {

    public ReportsCommand() {
        Objects.requireNonNull(ReportsJavaPlugin.getInstance().getCommand("reports")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cError: You need to be a player to report someone !");
        } else {
            if (player.hasPermission("reports.see")) {
                if (args.length != 0) {
                    player.sendMessage("§cError : Invalid command ! Usage: /reportslist");
                } else {
                    new ReportsListFastInv(1).open(player);
                }
            } else {
                player.sendMessage("§cYou don't have the permission to do that !");
            }
        }
        return false;
    }
}
