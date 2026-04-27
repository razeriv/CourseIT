package com.example.myapplication.ui.text;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.FragmentTextEditBinding;

public class TextEditFragment extends Fragment {

    private FragmentTextEditBinding binding;
    private static final String PREFS_NAME = "user_profile";
    private static final String KEY_ABOUT = "about_text";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTextEditBinding.inflate(inflater, container, false);

        setupSaveButton();
        loadSavedText();

        return binding.getRoot();
    }

    private void setupSaveButton() {
        binding.btnSave.setOnClickListener(v -> saveAboutText());
    }

    private void loadSavedText() {
        SharedPreferences prefs = requireContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        String savedText = prefs.getString(KEY_ABOUT, "");
        binding.editAbout.setText(savedText);
    }

    private void saveAboutText() {
        String text = binding.editAbout.getText().toString().trim();

        if (text.isEmpty()) {
            Toast.makeText(requireContext(), "Введите информацию о себе", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences prefs = requireContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        prefs.edit().putString(KEY_ABOUT, text).apply();

        Toast.makeText(requireContext(), "Информация успешно сохранена", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}