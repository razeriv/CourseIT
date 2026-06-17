package com.example.myapplication.ui.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.ui.projects.UpdateProfileRequest;

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
                Log.d("Profile", "Получен профиль: " + response.body());

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

    public void updateAbout(String aboutText) {
        if (aboutText == null || aboutText.trim().isEmpty()) {
            error.setValue("Текст не может быть пустым");
            return;
        }

        isLoading.setValue(true);
        error.setValue(null);

        UpdateProfileRequest request = new UpdateProfileRequest(aboutText);

        repository.updateProfile(request, new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                handleUpdateResponse(response);
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                handleUpdateFailure(t);
            }
        });
    }

    public void updateProfile(String firstName, String lastName, String course,
                              String groupNumber, String avatarUrl, String about) {

        isLoading.setValue(true);
        error.setValue(null);

        UpdateProfileRequest request = new UpdateProfileRequest(
                firstName, lastName, course, groupNumber, avatarUrl, about);

        repository.updateProfile(request, new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                handleUpdateResponse(response);
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                handleUpdateFailure(t);
            }
        });
    }

    private void handleUpdateResponse(Response<Profile> response) {
        isLoading.setValue(false);
        if (response.isSuccessful() && response.body() != null) {
            Profile updated = response.body();
            profileLiveData.setValue(updated);
            currentProfile.setValue(updated);
        } else {
            error.setValue("Не удалось сохранить. Код: " + response.code());
        }
    }

    private void handleUpdateFailure(Throwable t) {
        isLoading.setValue(false);
        error.setValue("Ошибка подключения: " + t.getMessage());
        t.printStackTrace();
    }

    public void clearProfile() {
        profileLiveData.setValue(null);
        error.setValue(null);
    }
    public void refreshProfile() {
        loadProfile();
    }
}