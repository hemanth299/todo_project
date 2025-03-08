package mypac;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MyUtil {

    // Format java.sql.Date to "YYYY-MM-DD" for display
    public static String formatDate(Date date) {
        if (date == null) return "N/A";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    // Convert String to java.sql.Date (used for input parsing)
    public static Date parseDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(dateStr);
            return new Date(utilDate.getTime()); // Convert to java.sql.Date
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Return null if parsing fails
        }
    }
}
