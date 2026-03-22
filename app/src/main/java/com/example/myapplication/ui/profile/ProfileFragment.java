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
        View root = binding.getRoot();

        // Кнопка "О себе"
        binding.btnAbout.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.nav_text_edit));

        // Кнопка "Портфолио"
        binding.btnPortfolio.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.nav_text_edit));

        // Кнопка "Отзывы"
        binding.btnReviews.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.nav_text_edit));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}