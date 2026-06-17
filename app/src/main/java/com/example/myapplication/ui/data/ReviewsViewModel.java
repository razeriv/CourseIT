package com.example.myapplication.ui.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.ui.profile.Review;
import com.example.myapplication.ui.profile.UserListItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ReviewsViewModel extends ViewModel {

    private final MutableLiveData<List<Review>> reviews = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> reviewPosted = new MutableLiveData<>();
    private final MutableLiveData<List<UserListItem>> users = new MutableLiveData<>();

    private final ReviewsRepository repository = new ReviewsRepository();

    public LiveData<List<Review>> getReviews()    { return reviews; }
    public LiveData<Boolean> getIsLoading()        { return isLoading; }
    public LiveData<String> getError()             { return error; }
    public LiveData<Boolean> getReviewPosted()     { return reviewPosted; }
    public LiveData<List<UserListItem>> getUsers()  { return users; }

    public void loadUsers(String role) {
        repository.getUsers(role, new retrofit2.Callback<List<UserListItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserListItem>> call,
                                   @NonNull Response<List<UserListItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    users.setValue(response.body());
                } else {
                    error.setValue("Не удалось загрузить список пользователей");
                    users.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<UserListItem>> call, @NonNull Throwable t) {
                error.setValue("Ошибка подключения: " + t.getMessage());
                users.setValue(new ArrayList<>());
            }
        });
    }

    public void loadReviews(String userId) {
        isLoading.setValue(true);
        error.setValue(null);
        repository.getReviews(userId, new retrofit2.Callback<List<Review>>() {
            @Override
            public void onResponse(@NonNull Call<List<Review>> call,
                                   @NonNull Response<List<Review>> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    reviews.setValue(response.body());
                } else {
                    error.setValue("Не удалось загрузить отзывы");
                    reviews.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Review>> call, @NonNull Throwable t) {
                isLoading.setValue(false);
                error.setValue("Ошибка подключения: " + t.getMessage());
                reviews.setValue(new ArrayList<>());
            }
        });
    }

    public void postReview(String userId, int rating, String text) {
        if (rating < 1 || rating > 5) {
            error.setValue("Укажите оценку от 1 до 5");
            return;
        }
        if (text == null || text.trim().isEmpty()) {
            error.setValue("Текст отзыва не может быть пустым");
            return;
        }

        isLoading.setValue(true);
        repository.createReview(userId, rating, text.trim(), new retrofit2.Callback<Review>() {
            @Override
            public void onResponse(@NonNull Call<Review> call,
                                   @NonNull Response<Review> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    Review saved = response.body();
                    List<Review> current = reviews.getValue();
                    List<Review> updated = current != null ? new ArrayList<>(current) : new ArrayList<>();

                    // Если отзыв этого автора уже есть в списке — заменяем (редактирование),
                    // иначе добавляем в начало (новый отзыв).
                    int existingIndex = -1;
                    for (int i = 0; i < updated.size(); i++) {
                        String existingAuthor = updated.get(i).getAuthorId();
                        if (existingAuthor != null && existingAuthor.equals(saved.getAuthorId())) {
                            existingIndex = i;
                            break;
                        }
                    }

                    if (existingIndex >= 0) {
                        updated.set(existingIndex, saved);
                    } else {
                        updated.add(0, saved);
                    }

                    reviews.setValue(updated);
                    reviewPosted.setValue(true);
                } else if (response.code() == 400) {
                    error.setValue("Нельзя оставить отзыв самому себе");
                } else if (response.code() == 404) {
                    error.setValue("Пользователь не найден");
                } else {
                    error.setValue("Не удалось отправить отзыв");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Review> call, @NonNull Throwable t) {
                isLoading.setValue(false);
                error.setValue("Ошибка подключения: " + t.getMessage());
            }
        });
    }
}
