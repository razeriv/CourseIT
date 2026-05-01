package com.example.myapplication.ui.network;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://82.202.143.69:5000/";
    private static Retrofit retrofit;
    private static Context appContext;

    public static void init(Context context) {
        appContext = context.getApplicationContext();
    }

    public static ApiService getApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(buildOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }

    private static OkHttpClient buildOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        Interceptor authInterceptor = chain -> {
            Request original = chain.request();
            String token = getTokenFromPrefs();

            Request.Builder builder = original.newBuilder();

            if (token != null && !token.isEmpty()) {
                builder.header("Authorization", "Bearer " + token);
            }

            return chain.proceed(builder.build());
        };

        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(authInterceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    public static String getTokenFromPrefs() {
        if (appContext == null) return null;
        SharedPreferences prefs = appContext.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
        return prefs.getString("token", null);
    }

    public static void saveToken(String token) {
        if (appContext == null) return;
        appContext.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
                .edit()
                .putString("token", token)
                .apply();

        reset();
    }

    public static void clearToken() {
        if (appContext == null) return;
        appContext.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
                .edit()
                .remove("token")
                .apply();
        reset();
    }

    public static void reset() {
        retrofit = null;
    }
}