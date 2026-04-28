package com.example.myapplication.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        setupButtons();
        observeViewModel();

        viewModel.loadProfile();

        return binding.getRoot();
    }

    private void setupButtons() {
        binding.btnAbout.buttonText.setText("О себе");
        binding.btnProjects.buttonText.setText("Портфолио");
        binding.btnReviews.buttonText.setText("Отзывы");

        binding.btnAbout.getRoot().setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.nav_text_edit));

        binding.btnProjects.getRoot().setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.nav_portfolio));

        binding.btnReviews.getRoot().setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.nav_reviews));
    }

    private void observeViewModel() {
        viewModel.getProfile().observe(getViewLifecycleOwner(), profile -> {
            if (profile != null) {
                updateUI(profile);
            }
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
            }
        });
    }

    private void updateUI(Profile profile) {
        String fullName = profile.getName() + " " + profile.getSurname();
        binding.profileName.setText(fullName);

        String info = profile.getFaculty() + " • " + profile.getGroup_number();
        binding.profileInfo.setText(info);

        // TODO: Загрузка аватарки по URL (Glide / Coil)
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}