package root.service.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String toShortDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat();
        formatter.applyPattern("yyyy/mm/dd");
        return formatter.format(date);
    }
}