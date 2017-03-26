package util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {

    public static String USDFormatter(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return "$" + decimalFormat.format(value);
    }

    public static String USDFormatter(String value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return "$" + decimalFormat.format(Double.valueOf(value));
    }

    public static String USDtoString(String value) {
        value = value.replace("$", "");
        value = value.replace(",", "");
        return value;
    }

    public static String getFormattedDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static Date parseDate(String src) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(src);
    }

    public static String phoneFormatter(String phone) {
        return String.format("(%s)-%s-%s", phone.substring(0, 3), phone.substring(3, 6), phone.substring(6));
    }

    public static String parsePhone(String value) {
        return value.replace("(", "").replace(")", "").replace("-", "");
    }
}
