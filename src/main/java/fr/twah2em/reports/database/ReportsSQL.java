package fr.twah2em.reports.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public record ReportsSQL(SQL sql) {
    public static final String TABLE = "reports";

    public void reportPlayer(ReportObject reportObject) {
        sql.update("INSERT INTO " + TABLE + " (uuid, date, authorUuid, reason) VALUES (" +
                "'" + reportObject.getUniqueId() + "', " +
                "'" + reportObject.getDate() + "', " +
                "'" + reportObject.getAuthorUniqueId() + "', " +
                "'" + reportObject.getReason() + "')");
    }

    public void deleteReport(int id) {
        sql.update("DELETE FROM " + TABLE + " WHERE id='" + id + "'");
    }

    public ReportObject getReport(int id) {
        final AtomicReference<ReportObject> returnReport = new AtomicReference<>();

        sql.query("SELECT * FROM " + TABLE + " WHERE id='" + id + "'", resultSet -> {
            try {
                if (resultSet.next()) {
                    final String uuid = resultSet.getString("uuid");
                    final String date = resultSet.getString("date");
                    final String authorUuid = resultSet.getString("authorUuid");
                    final String reason = resultSet.getString("reason");

                    returnReport.set(new ReportObject(UUID.fromString(uuid), date, UUID.fromString(authorUuid), reason));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return returnReport.get();
    }

    public int getReportId(ReportObject reportObject) {
        final AtomicInteger reportId = new AtomicInteger();
        sql.query("SELECT * FROM " + TABLE + " WHERE uuid='" + reportObject.getUniqueId() + "' AND date='" + reportObject.getDate() + "' " +
                        "AND authorUuid='" + reportObject.getAuthorUniqueId() + "' AND reason='" + reportObject.getReason() + "'",
                resultSet -> {
                    try {
                        if (resultSet.next()) {
                            reportId.set(resultSet.getInt("id"));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
        return reportId.get();
    }

    public List<ReportObject> getReports(UUID uuid) {
        final List<ReportObject> returnReports = new ArrayList<>();
        sql.query("SELECT * FROM " + TABLE + " WHERE uuid='" + uuid + "'",
                resultSet -> {
                    try {
                        while (resultSet.next()) {
                            final String date = resultSet.getString("date");
                            final String authorUuid = resultSet.getString("authorUuid");
                            final String reason = resultSet.getString("reason");

                            returnReports.add(new ReportObject(uuid, date, UUID.fromString(authorUuid), reason));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
        return returnReports;
    }
}
