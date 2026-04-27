package com.example.myapplication.ui.network;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request original = chain.request();

                        Request.Builder builder = original.newBuilder()
                                .header("Connection", "close")
                                .header("Accept-Encoding", "identity");

                        String token = getTokenFromPrefs();
                        if (token != null && !token.isEmpty()) {
                            builder.addHeader("Authorization", "Bearer " + token);
                        }

                        return chain.proceed(builder.build());
                    })

                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
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