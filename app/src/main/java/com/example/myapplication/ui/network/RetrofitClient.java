package com.example.myapplication.ui.network;

import android.content.Context;
import android.content.SharedPreferences;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://10.0.2.2:5000/";
    private static Retrofit retrofit;
    private static Context context;

    public static void init(Context ctx) {
        context = ctx.getApplicationContext();
    }

    public static ApiService getApi() {

        if (retrofit == null) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {

                        Request request = chain.request();
                        String token = getTokenFromPrefs();

                        if (token != null) {
                            request = request.newBuilder()
                                    .addHeader("Authorization", "Bearer " + token)
                                    .build();
                        }

                        return chain.proceed(request);
                    })
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

        SharedPreferences prefs =
                context.getSharedPreferences("auth", Context.MODE_PRIVATE);

        return prefs.getString("token", null);
    }

    public static void saveToken(String token) {
        if (context == null) return;

        SharedPreferences prefs =
                context.getSharedPreferences("auth", Context.MODE_PRIVATE);

        prefs.edit().putString("token", token).apply();
    }

    public static void clearToken() {
        if (context == null) return;

        SharedPreferences prefs =
                context.getSharedPreferences("auth", Context.MODE_PRIVATE);

        prefs.edit().remove("token").apply();
    }
}