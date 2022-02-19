package fr.twah2em.reports.database;

import fr.twah2em.reports.ReportsJavaPlugin;
import fr.twah2em.reports.config.ConfigManager;

import java.io.File;
import java.sql.*;
import java.util.Locale;
import java.util.function.Consumer;

public class SQL {
    private final String method;

    private Connection connection;

    public SQL(String method) {
        this.method = method;

        connect();
    }

    private void connect() {
        try {
            if (method.equalsIgnoreCase("SQLite")) {
                Class.forName("org.sqlite.JDBC");
                this.connection = DriverManager.getConnection("jdbc:sqlite:" + new File(ReportsJavaPlugin.getInstance().getDataFolder(),
                        "reports.db"));
            } else if (method.equalsIgnoreCase("MariaDB") || method.equalsIgnoreCase("MySQL") ||
                    method.equalsIgnoreCase("PostgreSQL")) {
                final String host = ConfigManager.getHost();
                final String port = ConfigManager.getPort();
                final String dbName = ConfigManager.getDbName();
                final String username = ConfigManager.getUsername();
                final String password = ConfigManager.getPassword();

                if (method.equalsIgnoreCase("MariaDB")) Class.forName("org.mariadb.jdbc.Driver");
                else if (method.equalsIgnoreCase("MySQL")) Class.forName("com.mysql.jdbc.Driver");
                else if (method.equalsIgnoreCase("PostgreSQL")) Class.forName("org.postgresql.Driver");

                this.connection = DriverManager.getConnection("jdbc:" + method.toLowerCase(Locale.ROOT) + "://" +
                        host + ":" + port + "/" + dbName, username, password);
            } else {
                throw new RuntimeException("Invalid database type in config.yml (" + method + ") ! Please use SQLite, MariaDB, MySQL" +
                        " or PostgreSQL !");
            }
        } catch (ClassNotFoundException | SQLException e) {
            if (e instanceof SQLException) {
                e.printStackTrace();
                throw new RuntimeException("Unable to connect to the database because the identifiers are invalid ! ");
            }
        }
    }

    public void createTables() {
        if (method.equalsIgnoreCase("SQLite")) {
            update("CREATE TABLE IF NOT EXISTS reports(" +
                    "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "uuid VARCHAR(37), " +
                    "date VARCHAR(255), " +
                    "authorUuid VARCHAR(37), " +
                    "reason VARCHAR(255))");
        } else {
            update("CREATE TABLE IF NOT EXISTS reports(" +
                    "id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                    "uuid VARCHAR(37), " +
                    "date VARCHAR(255), " +
                    "authorUuid VARCHAR(37), " +
                    "reason VARCHAR(255))");
        }
    }

    public void update(String query) {
        connect();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void query(String query, Consumer<ResultSet> consumer) {
        connect();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            consumer.accept(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
