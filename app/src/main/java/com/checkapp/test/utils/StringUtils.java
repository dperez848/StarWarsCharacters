package com.checkapp.test.utils;

import com.google.gson.Gson;

import java.util.List;

public class StringUtils {

    public static String toString(Object object) {
        return new Gson().toJson(object);
    }

    public static boolean isEmpty(String text) {
        return text == null || text.length() == 0;
    }

    public static String join(List<String> strings) {
        return join(strings, ", ", " y ");
    }

    public static String join(List<String> strings, String separator, String lastSeparator) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < strings.size(); i++) {
            result.append(strings.get(i));
            if (i < strings.size() - 2) {
                result.append(separator);
            }
            if (i == strings.size() - 2) {
                result.append(lastSeparator);
            }
        }

        return result.toString();
    }
}