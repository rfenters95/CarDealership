package util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {
    public static String priceFormatter(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        return "$" + decimalFormat.format(value);
    }

    public static String getFormattedDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
