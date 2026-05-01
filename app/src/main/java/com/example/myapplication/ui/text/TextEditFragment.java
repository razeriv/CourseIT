package com.example.myapplication.ui.text;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentTextEditBinding;
import com.example.myapplication.ui.profile.ProfileViewModel;

public class TextEditFragment extends Fragment {

    private FragmentTextEditBinding binding;
    private ProfileViewModel profileViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTextEditBinding.inflate(inflater, container, false);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        loadCurrentAboutText();
        setupSaveButton();

        return binding.getRoot();
    }

    private void loadCurrentAboutText() {
        profileViewModel.getProfile().observe(getViewLifecycleOwner(), profile -> {
            if (profile != null && profile.getAbout() != null) {
                binding.editAbout.setText(profile.getAbout());
            }
        });
    }

    private void setupSaveButton() {
        binding.btnSave.setOnClickListener(v -> {
            String aboutText = binding.editAbout.getText().toString().trim();

            if (aboutText.isEmpty()) {
                Toast.makeText(requireContext(), "Введите информацию о себе", Toast.LENGTH_SHORT).show();
                return;
            }

            binding.btnSave.setEnabled(false);
            binding.btnSave.setText("Сохранение...");

            profileViewModel.updateAbout(aboutText);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                binding.btnSave.setEnabled(!isLoading);
                binding.btnSave.setText(isLoading ? "Сохранение..." : "Сохранить");
            }
        });

        profileViewModel.getError().observe(getViewLifecycleOwner(), errorMsg -> {
            if (errorMsg != null) {
                Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_LONG).show();
                binding.btnSave.setEnabled(true);
                binding.btnSave.setText("Сохранить");
            }
        });

        profileViewModel.getProfile().observe(getViewLifecycleOwner(), profile -> {
        });
    }

    public void onAboutUpdatedSuccessfully() {
        Toast.makeText(requireContext(), "Информация успешно сохранена", Toast.LENGTH_SHORT).show();
        requireActivity().onBackPressed();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}