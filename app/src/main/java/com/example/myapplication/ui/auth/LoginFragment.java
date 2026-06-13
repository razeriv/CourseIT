package com.example.myapplication.ui.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;

public class LoginFragment extends Fragment {

    private AuthViewModel viewModel;
    private EditText editEmail, editPassword;
    private Button btnLogin;
    private View tvRegister;
    private ImageView ivTogglePassword;

    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        editEmail = view.findViewById(R.id.editEmail);
        editPassword = view.findViewById(R.id.editPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        tvRegister = view.findViewById(R.id.tvRegister);
        ivTogglePassword = view.findViewById(R.id.ivTogglePassword);

        viewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        navController = NavHostFragment.findNavController(this);

        setupPasswordToggle();
        setupTextWatchers();
        setupButtons();

        viewModel.getToken().observe(getViewLifecycleOwner(), token -> {
            if (token == null || token.isEmpty()) return;

            Toast.makeText(requireContext(), "Успешный вход", Toast.LENGTH_SHORT).show();

            navController.navigate(R.id.nav_home, null,
                    new NavOptions.Builder()
                            .setPopUpTo(R.id.loginFragment, true)
                            .build());

            viewModel.clearToken();
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences prefs = requireContext()
                .getSharedPreferences("auth", Context.MODE_PRIVATE);

        if (prefs.getString("token", null) != null) {
            navController.navigate(R.id.nav_home, null,
                    new NavOptions.Builder()
                            .setPopUpTo(R.id.loginFragment, true)
                            .build());
        }
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

    private void setupTextWatchers() {
        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                checkFields();
            }
        };

        editEmail.addTextChangedListener(watcher);
        editPassword.addTextChangedListener(watcher);
    }

    private void setupButtons() {
        btnLogin.setOnClickListener(v -> {
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty()) {
                viewModel.login(email, password);
            }
        });

        tvRegister.setOnClickListener(v ->
                navController.navigate(R.id.registrationNameFragment)
        );
    }

    private void checkFields() {
        boolean isFilled = !editEmail.getText().toString().trim().isEmpty() &&
                !editPassword.getText().toString().trim().isEmpty();

        btnLogin.setVisibility(isFilled ? View.VISIBLE : View.GONE);
    }
}