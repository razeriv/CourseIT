package com.example.myapplication.ui.auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;

public class RegistrationPasswordFragment extends Fragment {

    private EditText editPassword;
    private Button btnFinish;
    private AuthViewModel viewModel;
    TextView login;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_registration_password, container, false);

        login = view.findViewById(R.id.login);
        editPassword = view.findViewById(R.id.editPassword);
        btnFinish = view.findViewById(R.id.btnFinish);

        viewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        setupButton();
        observeViewModel();

        login.setOnClickListener(v ->
                NavHostFragment.findNavController(this)
                        .navigate(R.id.loginFragment)
        );

        return view;
    }

    private void setupButton() {
        btnFinish.setOnClickListener(v -> {
            String password = editPassword.getText().toString().trim();

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(requireContext(), "Введите пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.setPassword(password);
            viewModel.register();
        });
    }

    private void observeViewModel() {
        viewModel.getRegisterResult().observe(getViewLifecycleOwner(), success -> {
            if (success == null) return;

            if (success) {
                Toast.makeText(requireContext(), "Регистрация прошла успешно!", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(this).navigate(R.id.loginFragment);
                viewModel.clearRegistrationData();
            } else {
                Toast.makeText(requireContext(), "Ошибка регистрации. Попробуйте ещё раз.", Toast.LENGTH_SHORT).show();
            }

            viewModel.clearRegisterResult();
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                btnFinish.setEnabled(!isLoading);
                btnFinish.setText(isLoading ? "Регистрация..." : "Завершить регистрацию");
            }
        });
    }
}