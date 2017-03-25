package util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {

    public static String USDFormatter(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        return "$" + decimalFormat.format(value);
    }

    public static String USDFormatter(String value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        return "$" + decimalFormat.format(Double.valueOf(value));
    }

    public static String getFormattedDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String phoneFormatter(String phone) {
        return String.format("(%s)-%s-%s", phone.substring(0, 3), phone.substring(3, 6), phone.substring(6));
    }
}
