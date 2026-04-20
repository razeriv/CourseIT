package com.example.myapplication.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends ViewModel {

    private final AuthRepository repository = new AuthRepository();

    public String name;
    public String surname;
    public String email;
    public String faculty;
    public String group;
    public String password;

    private final MutableLiveData<Boolean> registerResult = new MutableLiveData<>();
    private final MutableLiveData<String> token = new MutableLiveData<>();

    public LiveData<Boolean> getRegisterResult() {
        return registerResult;
    }

    public void clearRegisterResult() {
        registerResult.setValue(null);
    }

    public void setNameData(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public void setEmailData(String email, String faculty, String group) {
        this.email = email;
        this.faculty = faculty;
        this.group = group;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void clearToken() {
        token.setValue(null);
    }

    public LiveData<String> getToken() {
        return token;
    }
    public void login(String email, String password) {

        repository.login(email, password, new Callback<AuthResponse>() {

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
    public void register() {

        repository.register(
                name,
                surname,
                email,
                faculty,
                group,
                password,
                new Callback<Void>() {

                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            registerResult.setValue(true);
                        } else {
                            registerResult.setValue(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        registerResult.setValue(false);
                    }
                }
        );
    }
}