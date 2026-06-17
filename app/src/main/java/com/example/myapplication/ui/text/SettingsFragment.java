package com.example.myapplication.ui.text;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.MainActivity;
import com.example.myapplication.databinding.FragmentSettingsBinding;
import com.example.myapplication.ui.profile.Profile;
import com.example.myapplication.ui.profile.ProfileViewModel;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private ProfileViewModel profileViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        setupObservers();
        setupClickListeners();

        profileViewModel.loadProfile();

        return binding.getRoot();
    }

    private void setupObservers() {
        profileViewModel.getProfile().observe(getViewLifecycleOwner(), this::populateFields);

        profileViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (binding.btnEditProfile != null) {
                binding.btnEditProfile.setEnabled(!isLoading);
                binding.btnEditProfile.setText(isLoading ? "Сохранение..." : "Сохранить изменения");
            }
        });

        profileViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show();
            }
        });

        profileViewModel.getProfileUpdated().observe(getViewLifecycleOwner(), updated -> {
            if (updated != null && updated) {
                Toast.makeText(requireContext(), "Профиль сохранён", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateFields(Profile profile) {
        if (profile == null) return;

        if (binding.etEmail != null) {
            binding.etEmail.setText(profile.getEmail());
            binding.etEmail.setEnabled(false); // email — логин, менять нельзя
        }
        if (binding.etFirstName != null) binding.etFirstName.setText(profile.getName());
        if (binding.etLastName != null) binding.etLastName.setText(profile.getSurname());
        if (binding.etCourse != null) binding.etCourse.setText(profile.getFaculty());
        if (binding.etGroup != null) binding.etGroup.setText(profile.getGroup_number());
    }

    private void setupClickListeners() {
        binding.btnEditProfile.setOnClickListener(v -> saveProfile());

        binding.btnAccessibility.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Версия для слабовидящих включена", Toast.LENGTH_SHORT).show());

        binding.btnLogout.setOnClickListener(v -> showLogoutConfirmationDialog());
    }

    private void saveProfile() {
        String firstName = binding.etFirstName != null ? binding.etFirstName.getText().toString().trim() : "";
        String lastName = binding.etLastName != null ? binding.etLastName.getText().toString().trim() : "";
        String course = binding.etCourse != null ? binding.etCourse.getText().toString().trim() : "";
        String group = binding.etGroup != null ? binding.etGroup.getText().toString().trim() : "";

        if (firstName.isEmpty() || lastName.isEmpty()) {
            Toast.makeText(requireContext(), "Имя и фамилия не могут быть пустыми", Toast.LENGTH_SHORT).show();
            return;
        }

        // Email менять нельзя (это логин). Сохраняем только реально редактируемые поля.
        // Сигнатура: updateProfile(firstName, lastName, course, groupNumber, avatarUrl, about)
        profileViewModel.updateProfile(
                firstName,
                lastName,
                course.isEmpty() ? null : course,
                group.isEmpty() ? null : group,
                null, // avatar_url
                null  // about здесь не трогаем — оно редактируется на отдельном экране
        );
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Выход из аккаунта")
                .setMessage("Вы действительно хотите выйти из аккаунта?")
                .setPositiveButton("Выйти", (dialog, which) -> {
                    performLogout();
                })
                .setNegativeButton("Отмена", null)
                .setCancelable(true)
                .show();
    }

    private void performLogout() {
        if (requireActivity() instanceof MainActivity) {
            ((MainActivity) requireActivity()).logout();
        } else {
            Toast.makeText(requireContext(), "Ошибка выхода из аккаунта", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}