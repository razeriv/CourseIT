package com.example.myapplication.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.data.ReviewsViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class ReviewsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReviewAdapter adapter;
    private ReviewsViewModel viewModel;

    // Выбор получателя
    private RecyclerView recyclerUsers;
    private UserSelectAdapter userAdapter;
    private TextView pickerHint;
    private TextView selectedRecipientLabel;

    private View reviewForm;
    private RatingBar formRating;
    private TextInputEditText formText;
    private Button btnAddReview, btnSubmit, btnCancel;
    private ProgressBar progressBar;
    private View emptyState;

    // Кому пишем отзыв (выбирается из списка)
    private String targetUserId = null;
    private String targetUserName = null;

    // Роль текущего пользователя (чтобы понять, какой список грузить)
    private ProfileViewModel profileViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reviews, container, false);

        bindViews(view);
        setupRecyclerView();
        setupUsersRecycler();
        setupForm();
        setupViewModel();
        observeProfileRole();

        return view;
    }

    private void bindViews(View view) {
        recyclerView           = view.findViewById(R.id.recyclerReviews);
        recyclerUsers          = view.findViewById(R.id.recyclerUsers);
        pickerHint             = view.findViewById(R.id.pickerHint);
        selectedRecipientLabel = view.findViewById(R.id.selectedRecipientLabel);
        reviewForm             = view.findViewById(R.id.reviewForm);
        formRating             = view.findViewById(R.id.formRating);
        formText               = view.findViewById(R.id.formReviewText);
        btnAddReview           = view.findViewById(R.id.btnAddReview);
        btnSubmit              = view.findViewById(R.id.btnSubmitReview);
        btnCancel              = view.findViewById(R.id.btnCancelReview);
        progressBar            = view.findViewById(R.id.progressBar);
        emptyState             = view.findViewById(R.id.emptyState);
    }

    private void setupRecyclerView() {
        adapter = new ReviewAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void setupUsersRecycler() {
        userAdapter = new UserSelectAdapter(this::onRecipientSelected);
        recyclerUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerUsers.setAdapter(userAdapter);
    }

    private void observeProfileRole() {
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        profileViewModel.getProfile().observe(getViewLifecycleOwner(), profile -> {
            if (profile == null) return;
            // Студент видит преподавателей, преподаватель/админ — студентов.
            String roleToLoad = profile.isStudent() ? "teacher" : "student";
            viewModel.loadUsers(roleToLoad);
        });

        // На случай, если профиль ещё не загружен
        if (profileViewModel.getProfile().getValue() == null) {
            profileViewModel.loadProfile();
        }
    }

    private void onRecipientSelected(UserListItem user) {
        targetUserId = user.getId();
        targetUserName = user.getFullName();

        userAdapter.setSelectedId(targetUserId);

        selectedRecipientLabel.setText("Отзывы о: " + targetUserName);
        selectedRecipientLabel.setVisibility(View.VISIBLE);
        btnAddReview.setVisibility(View.VISIBLE);

        // Прячем форму при смене получателя
        reviewForm.setVisibility(View.GONE);
        clearForm();

        viewModel.loadReviews(targetUserId);
    }

    private void setupForm() {
        btnAddReview.setOnClickListener(v -> {
            if (targetUserId == null) {
                Toast.makeText(requireContext(), "Сначала выберите получателя", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean visible = reviewForm.getVisibility() == View.VISIBLE;
            reviewForm.setVisibility(visible ? View.GONE : View.VISIBLE);
        });

        btnCancel.setOnClickListener(v -> {
            reviewForm.setVisibility(View.GONE);
            clearForm();
        });

        btnSubmit.setOnClickListener(v -> {
            if (targetUserId == null) {
                Toast.makeText(requireContext(), "Сначала выберите получателя", Toast.LENGTH_SHORT).show();
                return;
            }

            int rating = (int) formRating.getRating();
            String text = formText.getText() != null ? formText.getText().toString().trim() : "";

            if (rating == 0) {
                Toast.makeText(requireContext(), "Выберите оценку", Toast.LENGTH_SHORT).show();
                return;
            }
            if (text.isEmpty()) {
                Toast.makeText(requireContext(), "Напишите текст отзыва", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.postReview(targetUserId, rating, text);
        });
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(ReviewsViewModel.class);

        viewModel.getUsers().observe(getViewLifecycleOwner(), users -> {
            userAdapter.setData(users);
            boolean empty = users == null || users.isEmpty();
            pickerHint.setText(empty
                    ? "Нет доступных пользователей для отзыва"
                    : "Выберите, кому оставить отзыв:");
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            progressBar.setVisibility(isLoading != null && isLoading ? View.VISIBLE : View.GONE);
            btnSubmit.setEnabled(isLoading == null || !isLoading);
        });

        viewModel.getReviews().observe(getViewLifecycleOwner(), list -> {
            adapter.setData(list);
            boolean isEmpty = list == null || list.isEmpty();
            // emptyState показываем только когда получатель уже выбран
            boolean recipientChosen = targetUserId != null;
            emptyState.setVisibility(recipientChosen && isEmpty ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(!isEmpty ? View.VISIBLE : View.GONE);
        });

        viewModel.getError().observe(getViewLifecycleOwner(), err -> {
            if (err != null) {
                Toast.makeText(requireContext(), err, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getReviewPosted().observe(getViewLifecycleOwner(), posted -> {
            if (posted != null && posted) {
                Toast.makeText(requireContext(), "Отзыв успешно отправлен!", Toast.LENGTH_SHORT).show();
                reviewForm.setVisibility(View.GONE);
                clearForm();
            }
        });
    }

    private void clearForm() {
        formRating.setRating(0);
        if (formText != null) formText.setText("");
    }
}
