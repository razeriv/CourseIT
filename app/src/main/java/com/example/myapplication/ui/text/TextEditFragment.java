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
import com.example.myapplication.ui.profile.Profile;
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

        setupUI();
        observeViewModel();
        loadCurrentAboutText();

        return binding.getRoot();
    }

    private void setupUI() {
        binding.btnSave.setOnClickListener(v -> saveAboutText());
    }

    private void observeViewModel() {
        profileViewModel.getProfile().observe(getViewLifecycleOwner(), profile -> {
            if (profile != null && profile.getAbout() != null) {
                binding.editAbout.setText(profile.getAbout());
            }
        });

        profileViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            binding.btnSave.setEnabled(!isLoading);
        });

        profileViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadCurrentAboutText() {
        Profile currentProfile = profileViewModel.getProfile().getValue();
        if (currentProfile != null && currentProfile.getAbout() != null) {
            binding.editAbout.setText(currentProfile.getAbout());
        }
    }

    private void saveAboutText() {
        String newAbout = binding.editAbout.getText().toString().trim();

        if (newAbout.isEmpty()) {
            Toast.makeText(requireContext(), "Введите информацию о себе", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(requireContext(), "Сохранение...", Toast.LENGTH_SHORT).show();

        requireActivity().onBackPressed();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}