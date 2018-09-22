package com.checkapp.test.data.people_repository.remote.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClientGenerator {
    // Constants
    private static final String TAG = HttpClientGenerator.class.getSimpleName();
    private static final int TIMEOUT_SECONDS = 15;

    private HttpClientGenerator() {
    }

    public static <S> S createClient(Class<S> serviceClass, String baseUrl, Interceptor... interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        addBaseInterceptors(builder);

        addCustomInterceptors(builder, interceptors);

        OkHttpClient client = builder
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Integer.class, new EmptyStringToIntegerTypeAdapter())
                .registerTypeAdapter(Double.class, new EmptyStringToDoubleTypeAdapter())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit.create(serviceClass);
    }

    private static void addBaseInterceptors(OkHttpClient.Builder builder) {
        // Logging interceptor
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);
    }

    private static void addCustomInterceptors(OkHttpClient.Builder builder, Interceptor... interceptors) {
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
    }

    public static class EmptyStringToIntegerTypeAdapter extends TypeAdapter<Integer> {
        @Override
        public void write(JsonWriter jsonWriter, Integer number) throws IOException {
            if (number == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.value(number);
        }

        @Override
        public Integer read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }

            try {
                String value = jsonReader.nextString();
                if ("".equals(value)) {
                    return null;
                }
                return Integer.valueOf(value);
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }
    }

    public static class EmptyStringToDoubleTypeAdapter extends TypeAdapter<Double> {
        @Override
        public void write(JsonWriter jsonWriter, Double number) throws IOException {
            if (number == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.value(number);
        }

        @Override
        public Double read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }

            try {
                String value = jsonReader.nextString();
                if ("".equals(value)) {
                    return null;
                }
                return Double.valueOf(value);
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }
    }
}
