package com.example.myapplication.ui.network;

import android.content.Context;

import com.example.myapplication.BuildConfig;
import android.content.SharedPreferences;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public interface UnauthorizedListener {
        void onUnauthorized();
    }

    private static volatile UnauthorizedListener unauthorizedListener;

    public static void setUnauthorizedListener(UnauthorizedListener listener) {
        unauthorizedListener = listener;
    }

    private static final String BASE_URL = BuildConfig.BASE_URL;
    private static Retrofit retrofit;
    private static Context appContext;

    public static void init(Context context) {
        appContext = context.getApplicationContext();
    }

    public static ApiService getApi() {
        if (appContext == null) {
            throw new IllegalStateException("RetrofitClient не инициализирован. Убедитесь, что App.onCreate() вызван.");
        }
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
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor authInterceptor = chain -> {
            Request original = chain.request();
            String url = original.url().toString();
            String token = getTokenFromPrefs();

            Request.Builder builder = original.newBuilder();

            if (token != null && !token.isEmpty() && shouldAttachToken(url)) {
                builder.header("Authorization", "Bearer " + token);
            }

            return chain.proceed(builder.build());
        };

        Interceptor unauthorizedInterceptor = chain -> {
            Response response = chain.proceed(chain.request());
            if (response.code() == 401 && unauthorizedListener != null) {
                clearToken();
                unauthorizedListener.onUnauthorized();
            }
            return response;
        };

        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(authInterceptor)
                .addInterceptor(unauthorizedInterceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    private static boolean shouldAttachToken(String url) {
        if (url.contains("/register") ||
                url.contains("/login")) {
            return false;
        }

        return url.contains("/api/v1/") ||
                url.contains("/profile");
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