package util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {

    public static String parseName(String input) {
        String temp = input.replaceAll("[^A-Za-z]", "").toLowerCase();
        return Character.toUpperCase(temp.charAt(0)) + temp.substring(1);
    }

    public static String parseNumber(String input) {
        return input.replaceAll("[^0-9]", "");
    }

    public static String USDFormatter(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return "$" + decimalFormat.format(value);
    }

    public static String USDFormatter(String value) {
        if (!value.equals("0")) {
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            return "$" + decimalFormat.format(Double.valueOf(value));
        } else {
            return "$0";
        }
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

}
