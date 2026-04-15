package com.example.myapplication.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends ViewModel {

    public String name;
    public String surname;
    public String email;
    public String faculty;
    public String group;
    public String password;

    private final MutableLiveData<String> token = new MutableLiveData<>();

    private final MutableLiveData<Boolean> registerResult = new MutableLiveData<>();

    private final AuthRepository repo = new AuthRepository();

    public LiveData<String> getToken() {
        return token;
    }

    public LiveData<Boolean> getRegisterResult() {
        return registerResult;
    }

    public void login(String email, String password) {

        repo.login(email, password, new Callback<AuthResponse>() {

            @Override
            public void onResponse(Call<AuthResponse> call,
                                   Response<AuthResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    token.setValue(response.body().getToken());
                } else {
                    token.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                t.printStackTrace();
                token.setValue(null);
            }
        });
    }

    public void register(String name,
                         String surname,
                         String email,
                         String faculty,
                         String group,
                         String password) {

        repo.register(name, surname, email, faculty, group, password,
                new Callback<Void>() {

                    @Override
                    public void onResponse(Call<Void> call,
                                           Response<Void> response) {

                        registerResult.setValue(response.isSuccessful());
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                        t.printStackTrace();
                        registerResult.setValue(false);
                    }
                });
    }

    public void clearToken() {
        token.setValue(null);
    }

    public void clearRegisterResult() {
        registerResult.setValue(null);
    }
}