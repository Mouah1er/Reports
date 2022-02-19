package fr.twah2em.reports.commands;

import fr.twah2em.reports.ReportsJavaPlugin;
import fr.twah2em.reports.inventories.ReportChooseReasonTypeFastInv;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ReportCommand implements CommandExecutor {

    public ReportCommand() {
        Objects.requireNonNull(ReportsJavaPlugin.getInstance().getCommand("report")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cError: You need to be a player to report someone !");
        } else {
            if (args.length != 1) {
                player.sendMessage("§cError : Invalid command ! Usage: /report <player>");
            } else {
                final Player targetPlayer = Bukkit.getPlayer(args[0]);

                if (targetPlayer == null) {
                    player.sendMessage("§cError : The player " + args[0] + " is not connected !");
                } else {
                    new ReportChooseReasonTypeFastInv(targetPlayer).open(player);
                }
            }
        }
        return false;
    }
}
