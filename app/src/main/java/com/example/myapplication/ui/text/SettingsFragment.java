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

import com.example.myapplication.databinding.FragmentSettingsBinding;
import com.example.myapplication.MainActivity;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        setupClickListeners();

        return binding.getRoot();
    }

    private void setupClickListeners() {
        binding.btnEditProfile.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Редактирование профиля (в разработке)", Toast.LENGTH_SHORT).show());

        binding.btnAccessibility.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Версия для слабовидящих включена", Toast.LENGTH_SHORT).show());

        binding.btnLogout.setOnClickListener(v -> showLogoutConfirmationDialog());
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