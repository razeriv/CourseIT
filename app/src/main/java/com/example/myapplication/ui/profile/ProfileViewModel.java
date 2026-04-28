package com.example.myapplication.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {

    private final ProfileRepository repository = new ProfileRepository();

    private final MutableLiveData<Profile> profileLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Profile> currentProfile = new MutableLiveData<>();

    public LiveData<Profile> getProfile() {
        return profileLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Profile> getCurrentProfile() {
        return currentProfile;
    }

    public void loadProfile() {
        isLoading.setValue(true);
        error.setValue(null);

        repository.getProfile(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                isLoading.setValue(false);

                if (response.isSuccessful() && response.body() != null) {
                    Profile profile = response.body();
                    profileLiveData.setValue(profile);
                    currentProfile.setValue(profile);
                } else {
                    error.setValue("Не удалось загрузить профиль. Код: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                isLoading.setValue(false);
                error.setValue("Ошибка подключения: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public void clearProfile() {
        profileLiveData.setValue(null);
        error.setValue(null);
    }
    public void refreshProfile() {
        loadProfile();
    }
}