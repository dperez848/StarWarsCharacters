package com.checkapp.test.utils;

import android.arch.persistence.room.TypeConverter;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class StringTypeConverters {

    private static Moshi moshi = new Moshi.Builder().build();

    @TypeConverter
    public static List<String> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listMyData = Types.newParameterizedType(List.class, String.class);
        JsonAdapter<List<String>> adapter = moshi.adapter(listMyData);
        try {
            return adapter.fromJson(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @TypeConverter
    public static String someObjectListToString(List<String> someObjects) {
        Type listMyData = Types.newParameterizedType(List.class, String.class);
        JsonAdapter<List<String>> adapter = moshi.adapter(listMyData);
        return adapter.toJson(someObjects);
    }


}