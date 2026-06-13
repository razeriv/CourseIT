package com.example.myapplication.ui.auth;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

    private TextView login;
    private TextView password_length;
    private TextView password_symbol;
    private TextView password_specialsymbol;
    private ImageView ivTogglePassword;
    private boolean isPasswordVisible = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_registration_password, container, false);

        // Инициализация View
        login = view.findViewById(R.id.login);
        editPassword = view.findViewById(R.id.editPassword);
        btnFinish = view.findViewById(R.id.btnFinish);
        password_length = view.findViewById(R.id.passwordl_ength);
        password_symbol = view.findViewById(R.id.password_symbol);
        password_specialsymbol = view.findViewById(R.id.password_specialsymbol);
        ivTogglePassword = view.findViewById(R.id.ivTogglePassword);

        viewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        setupButton();
        observeViewModel();
        setupPasswordValidation();
        setupPasswordToggle();

        login.setOnClickListener(v ->
                NavHostFragment.findNavController(this)
                        .navigate(R.id.loginFragment)
        );

        return view;
    }

    private void setupPasswordToggle() {
        ivTogglePassword.setOnClickListener(v -> {
            boolean isPasswordVisible = editPassword.getTransformationMethod() == PasswordTransformationMethod.getInstance();

            if (isPasswordVisible) {
                editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                ivTogglePassword.setImageResource(R.drawable.eye_open);
            } else {
                editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                ivTogglePassword.setImageResource(R.drawable.eye_close);
            }

            editPassword.setSelection(editPassword.getText().length());
        });
    }

    private void setupPasswordValidation() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = editPassword.getText().toString().trim();

                // Спецсимволы
                if (containsSpecialSymbol(password)) {
                    password_specialsymbol.setText("✅ Содержит спецсимволы");
                } else {
                    password_specialsymbol.setText("❌ Содержит спецсимволы");
                }

                // Разный регистр
                if (containsUpperAndLowerCase(password)) {
                    password_symbol.setText("✅ Содержит хотя бы одну заглавную и строчную букву");
                } else {
                    password_symbol.setText("❌ Содержит хотя бы одну заглавную и строчную букву");
                }

                // Длина
                if (password.length() >= 8 && password.length() <= 14) {
                    password_length.setText("✅ Длина от 8 до 14 символов");
                } else {
                    password_length.setText("❌ Длина от 8 до 14 символов");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        editPassword.addTextChangedListener(textWatcher);
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

    // Проверка на спецсимволы
    private boolean containsSpecialSymbol(String str) {
        if (str.isEmpty()) return false;
        String special = "`!@#$%^&*()_+|№;:?=-<.,>{}[]";
        for (char c : str.toCharArray()) {
            if (special.indexOf(c) != -1) {
                return true;
            }
        }
        return false;
    }

    // Проверка на заглавную и строчную буквы
    private boolean containsUpperAndLowerCase(String str) {
        if (str.isEmpty()) return false;

        boolean hasUpper = false;
        boolean hasLower = false;

        for (char c : str.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isLowerCase(c)) hasLower = true;

            if (hasUpper && hasLower) return true;
        }

        return hasUpper && hasLower;
    }
}