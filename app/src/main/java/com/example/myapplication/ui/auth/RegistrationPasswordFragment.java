package com.example.myapplication.ui.auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;

public class RegistrationPasswordFragment extends Fragment {

    private EditText editPassword;

    private AuthViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_registration_password, container, false);

        editPassword = view.findViewById(R.id.editPassword);
        Button btnFinish = view.findViewById(R.id.btnFinish);

        viewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        btnFinish.setOnClickListener(v -> {

            String password = editPassword.getText().toString().trim();

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(requireContext(),
                        "Введите пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.setPassword(password);
            viewModel.register();
        });

        viewModel.getRegisterResult().observe(getViewLifecycleOwner(), success -> {

            if (success == null) return;

            if (success) {
                Toast.makeText(requireContext(),
                        "Регистрация успешна", Toast.LENGTH_SHORT).show();

                NavHostFragment.findNavController(this)
                        .navigate(R.id.loginFragment);
            } else {
                Toast.makeText(requireContext(),
                        "Ошибка регистрации", Toast.LENGTH_SHORT).show();
            }

            viewModel.clearRegisterResult();
        });

        return view;
    }
}