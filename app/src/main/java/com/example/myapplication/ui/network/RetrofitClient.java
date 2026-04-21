package com.example.myapplication.ui.network;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://82.202.143.69:5000/";
    private static Retrofit retrofit;
    private static Context context;

    public static void init(Context ctx) {
        context = ctx.getApplicationContext();
    }

    public static ApiService getApi() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(chain -> {
                        Request request = chain.request();
                        String token = getTokenFromPrefs();

                        if (token != null && !token.isEmpty()) {
                            request = request.newBuilder()
                                    .addHeader("Authorization", "Bearer " + token)
                                    .build();
                        }
                        return chain.proceed(request);
                    })
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }

    private static String getTokenFromPrefs() {
        if (context == null) return null;
        SharedPreferences prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
        return prefs.getString("token", null);
    }

    public static void saveToken(String token) {
        if (context == null) return;
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
                .edit()
                .putString("token", token)
                .apply();
    }

    public static void clearToken() {
        if (context == null) return;
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
                .edit()
                .remove("token")
                .apply();
    }

    public static void reset() {
        retrofit = null;
    }
}