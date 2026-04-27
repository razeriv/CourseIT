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

    // Данные для регистрации
    private String firstName;
    private String lastName;
    private String email;
    private String course;
    private String groupNumber;
    private String password;

    private final MutableLiveData<Boolean> registerResult = new MutableLiveData<>();
    private final MutableLiveData<String> tokenLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>(); // Добавлено для удобства

    public LiveData<Boolean> getRegisterResult() {
        return registerResult;
    }

    public LiveData<String> getToken() {
        return tokenLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void clearRegisterResult() {
        registerResult.setValue(null);
        errorMessage.setValue(null);
    }

    public void setNameData(String firstName, String lastName) {
        this.firstName = firstName != null ? firstName.trim() : null;
        this.lastName = lastName != null ? lastName.trim() : null;
    }
    public void setEmailData(String email, String course, String groupNumber) {
        this.email = email != null ? email.trim() : null;
        this.course = course != null ? course.trim() : null;
        this.groupNumber = groupNumber != null ? groupNumber.trim() : null;
    }
    public void setPassword(String password) {
        this.password = password != null ? password.trim() : null;
    }

    public void register() {
        if (firstName == null || firstName.isEmpty() ||
                lastName == null || lastName.isEmpty() ||
                email == null || email.isEmpty() ||
                password == null || password.isEmpty()) {

            registerResult.setValue(false);
            errorMessage.setValue("Пожалуйста, заполните все обязательные поля");
            return;
        }

        isLoading.setValue(true);
        errorMessage.setValue(null);

        repository.register(
                firstName,
                lastName,
                email,
                course,
                groupNumber,
                password,
                new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        isLoading.setValue(false);
                        if (response.isSuccessful() && response.body() != null && response.body().getToken() != null) {
                            String token = response.body().getToken();
                            tokenLiveData.setValue(token);
                            RetrofitClient.saveToken(token);
                            registerResult.setValue(true);
                        } else {
                            registerResult.setValue(false);
                            errorMessage.setValue("Ошибка регистрации: " +
                                    (response.body() != null && response.body().getMessage() != null
                                            ? response.body().getMessage()
                                            : response.message()));
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        isLoading.setValue(false);
                        t.printStackTrace();
                        registerResult.setValue(false);
                        errorMessage.setValue("Ошибка сети: " + t.getMessage());
                    }
                });
    }

    public void login(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            tokenLiveData.setValue(null);
            errorMessage.setValue("Введите email и пароль");
            return;
        }

        isLoading.setValue(true);
        errorMessage.setValue(null);

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
                    errorMessage.setValue("Неверные данные для входа");
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                isLoading.setValue(false);
                t.printStackTrace();
                tokenLiveData.setValue(null);
                errorMessage.setValue("Ошибка сети: " + t.getMessage());
            }
        });
    }

    public void clearToken() {
        tokenLiveData.setValue(null);
        RetrofitClient.clearToken();
    }

    public void clearRegistrationData() {
        firstName = lastName = email = course = groupNumber = password = null;
    }
}