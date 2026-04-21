package com.example.myapplication.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.ui.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends ViewModel {

    private final AuthRepository repository = new AuthRepository();

    private String name;
    private String surname;
    private String email;
    private String faculty;
    private String group;
    private String password;

    private final MutableLiveData<Boolean> registerResult = new MutableLiveData<>();
    private final MutableLiveData<String> tokenLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    public LiveData<Boolean> getRegisterResult() {
        return registerResult;
    }

    public LiveData<String> getToken() {
        return tokenLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
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

    public void login(String email, String password) {
        isLoading.setValue(true);

        repository.login(email, password, new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null && response.body().getToken() != null) {
                    String token = response.body().getToken();
                    tokenLiveData.setValue(token);
                    RetrofitClient.saveToken(token);
                } else {
                    tokenLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                isLoading.setValue(false);
                t.printStackTrace();
                tokenLiveData.setValue(null);
            }
        });
    }

    public void register() {
        if (name == null || surname == null || email == null || password == null) {
            registerResult.setValue(false);
            return;
        }

        isLoading.setValue(true);

        repository.register(name, surname, email, faculty, group, password, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                isLoading.setValue(false);
                registerResult.setValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                isLoading.setValue(false);
                t.printStackTrace();
                registerResult.setValue(false);
            }
        });
    }

    public void clearToken() {
        tokenLiveData.setValue(null);
        RetrofitClient.clearToken();
    }

    public void clearRegistrationData() {
        name = surname = email = faculty = group = password = null;
    }
}