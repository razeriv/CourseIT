package com.example.myapplication.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);

        binding.btnAbout.buttonText.setText("О себе");
        binding.btnProjects.buttonText.setText("Портфолио");
        binding.btnReviews.buttonText.setText("Отзывы");

        binding.btnAbout.getRoot().setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.nav_text_edit));

        binding.btnProjects.getRoot().setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.nav_portfolio));

        binding.btnReviews.getRoot().setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.nav_reviews));

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}