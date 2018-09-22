package com.checkapp.test.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static final String TAG = DateUtils.class.getSimpleName();

    public static String toddmmYYYY(Date date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.format(date);
        }
        return null;
    }

    public static String toddmmYYYYhhmm(Date date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            return dateFormat.format(date).toLowerCase();
        }
        return null;
    }

    public static String toYYYYmmdd(Date date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(date);
        }
        return null;
    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    public static String format(Date date, String format) {
        if (date != null && format != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(date);
        }
        return null;
    }

    public static String getDayNumber(String dateString) {
        Date date = fromString(dateString, "yyyy-MM-dd");
        if (date != null) {
            return format(date, "d");
        }

        return null;
    }

    public static Date fromString(String string) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date fromString(String date, String format) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String fromTimestampToddmmYYYY(String date) {
        if (date == null) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        String[] timestampParts = date.split(" ");

        if (timestampParts.length == 0) {
            return "";
        }

        String[] dateParts = timestampParts[0].split("-");

        for (int i = dateParts.length - 1; i >= 0; i--) {
            stringBuilder.append(dateParts[i]);
            if (i > 0) {
                stringBuilder.append("/");
            }
        }

        return stringBuilder.toString();
    }

    public static String fromYYYYmmTommYYYY(String date) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] dateParts = date.split("-");

        for (int i = dateParts.length - 1; i >= 0; i--) {
            stringBuilder.append(dateParts[i]);
            if (i > 0) {
                stringBuilder.append("/");
            }
        }

        return stringBuilder.toString();
    }

    public static String fromYYYYmmddToddmmYYYY(String date) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] dateParts = date.split("-");

        for (int i = dateParts.length - 1; i >= 0; i--) {
            stringBuilder.append(dateParts[i]);
            if (i > 0) {
                stringBuilder.append("/");
            }
        }

        return stringBuilder.toString();
    }

    public static String fromddmmYYYYToYYYYmmdd(String date) {
        if (date == null || date.isEmpty()) {
            return null;
        }

        try {
            new SimpleDateFormat("dd/MM/yyyy").parse(date);

            StringBuilder stringBuilder = new StringBuilder();
            String[] dateParts = date.split("/");

            for (int i = dateParts.length - 1; i >= 0; i--) {
                stringBuilder.append(dateParts[i]);
                if (i > 0) {
                    stringBuilder.append("-");
                }
            }

            return stringBuilder.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String today() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    public static String now() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static boolean beforeToday(String dateString) {
        Date today = new Date();
        String todayString = format(today, "yyyy-MM-dd");
        today = fromString(todayString);

        Date anotherDate = fromString(dateString);
        return (anotherDate != null) && anotherDate.before(today);
    }

    public static String fromTimestampToddmmYYYYhhmmaa(String date) {
        return format(fromString(date), "dd/MM/yyyy hh:mm aa");
    }
}