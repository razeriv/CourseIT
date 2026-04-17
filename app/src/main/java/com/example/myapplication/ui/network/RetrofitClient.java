package com.example.myapplication.ui.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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

                        Request request = chain.request();
                        String token = getTokenFromPrefs();

                        if (token != null) {
                            request = request.newBuilder()
                                    .addHeader("Authorization", "Bearer " + token)
                                    .build();
                        }

                        android.util.Log.d("API", "Token: " + token);

                        return chain.proceed(request);
                    })
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

        SharedPreferences prefs =
                context.getSharedPreferences("auth", Context.MODE_PRIVATE);

        String token = prefs.getString("token", null);

        Log.d("API", "Token: " + token);

        return token;
    }

    public static void reset() {
        retrofit = null;
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