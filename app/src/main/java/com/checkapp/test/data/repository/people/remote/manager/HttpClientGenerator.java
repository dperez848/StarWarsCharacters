package com.checkapp.test.data.repository.people.remote.manager;


import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
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
}
