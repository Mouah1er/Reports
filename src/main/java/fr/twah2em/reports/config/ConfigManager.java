package fr.twah2em.reports.config;

import fr.twah2em.reports.ReportsJavaPlugin;

public class ConfigManager {
    public static String getType() {
        return ReportsJavaPlugin.getInstance().getConfig().getString("database.type");
    }

    public static String getUsername() {
        if (!getType().equalsIgnoreCase("SQLite")) {
            return ReportsJavaPlugin.getInstance().getConfig().getString("database.username");
        } else {
            return null;
        }
    }

    public static String getPassword() {
        if (!getType().equalsIgnoreCase("SQLite")) {
            return ReportsJavaPlugin.getInstance().getConfig().getString("database.password");
        } else {
            return null;
        }
    }

    public static String getHost() {
        if (!getType().equalsIgnoreCase("SQLite")) {
            return ReportsJavaPlugin.getInstance().getConfig().getString("database.host");
        } else {
            return null;
        }
    }

    public static String getDbName() {
        if (!getType().equalsIgnoreCase("SQLite")) {
            return ReportsJavaPlugin.getInstance().getConfig().getString("database.database-name");
        } else {
            return null;
        }
    }

    public static String getPort() {
        if (!getType().equalsIgnoreCase("SQLite")) {
            return ReportsJavaPlugin.getInstance().getConfig().getString("database.port");
        } else {
            return null;
        }
    }
}
