package com.rile.issuetracker.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Stefan
 */
public class Util {

    public String getCurrentDateTimeMySQLFormat() {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(dt);
    }

    public String getDateOrTimeFrom(String dateTime, int parameter) {
        if (dateTime == null || dateTime.isEmpty() || !dateTime.contains(" ")) {
            return null;
        }
        return dateTime.split(" ")[parameter];
    }
    
    public String getPriorityColor(String priority) {
        if (priority == null || priority.isEmpty()) return null;
        priority = priority.toLowerCase();
        switch (priority) {
            case "critical":
                return "danger";
            case "urgent":
            case "medium":
                return "warning";
            case "low":
                return "success";
            case "very low":
            case "very_low":
            case "not assigned":
            case "not_assigned":
                return "default";
            default:
                return null;
        }
    }
    
    public String getStatusColor(String status) {
        if (status == null || status.isEmpty()) return null;
        status = status.toLowerCase();
        switch (status) {
            case "open/new":
            case "open":
            case "new":
                return "primary";
            case "information required":
            case "information_required":
            case "confirmed":
                return "info";
            case "pending":
                return "warning";
            case "solved":
                return "success";
            case "closed":
                return "default";
            default:
                return null;
        }
    }
    
}
