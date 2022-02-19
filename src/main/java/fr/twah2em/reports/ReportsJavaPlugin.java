package fr.twah2em.reports;

import fr.mrmicky.fastinv.FastInvManager;
import fr.twah2em.reports.commands.ReportCommand;
import fr.twah2em.reports.commands.ReportsListCommand;
import fr.twah2em.reports.config.ConfigManager;
import fr.twah2em.reports.database.ReportsSQL;
import fr.twah2em.reports.database.SQL;
import org.bukkit.plugin.java.JavaPlugin;

public final class ReportsJavaPlugin extends JavaPlugin {
    private SQL sql;
    private ReportsSQL reportsSQL;

    @Override
    public void onEnable() {
        getSLF4JLogger().info("Plugin is starting !");

        saveDefaultConfig();

        this.sql = new SQL(ConfigManager.getType());
        sql.createTables();

        this.reportsSQL = new ReportsSQL(sql);

        FastInvManager.register(this);

        new ReportCommand();
        new ReportsListCommand();
    }

    @Override
    public void onDisable() {
        FastInvManager.closeAll();
    }

    public static ReportsJavaPlugin getInstance() {
        return getPlugin(ReportsJavaPlugin.class);
    }

    public SQL getSql() {
        return sql;
    }

    public ReportsSQL getReportsSQL() {
        return reportsSQL;
    }
}
