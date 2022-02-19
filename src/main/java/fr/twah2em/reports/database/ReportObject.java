package fr.twah2em.reports.database;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ReportObject {
    private final UUID uuid;
    private final String date;
    private final UUID authorUuid;
    private final String reason;

    public ReportObject(UUID uuid, String date, UUID authorUuid, String reason) {
        this.uuid = uuid;
        this.date = date;
        this.authorUuid = authorUuid;
        this.reason = reason;
    }

    public ReportObject(UUID uuid, UUID authorUuid, String reason) {
        this(uuid, new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SS").format(new Date(System.currentTimeMillis())), authorUuid, reason);
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public String getDate() {
        return date;
    }

    public UUID getAuthorUniqueId() {
        return authorUuid;
    }

    public String getReason() {
        return reason;
    }
}
