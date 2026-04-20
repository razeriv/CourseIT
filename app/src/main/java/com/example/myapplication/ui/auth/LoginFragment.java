package com.example.myapplication.ui.auth;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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
import com.example.myapplication.ui.network.RetrofitClient;

public class LoginFragment extends Fragment {

    private AuthViewModel viewModel;

    private EditText editEmail, editPassword;
    private Button btnLogin;
    private View tvRegister;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences prefs = requireContext()
                .getSharedPreferences("auth", Context.MODE_PRIVATE);

        String token = prefs.getString("token", null);

        if (token != null) {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.nav_home, null,
                            new androidx.navigation.NavOptions.Builder()
                                    .setPopUpTo(R.id.loginFragment, true)
                                    .build());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        editEmail = view.findViewById(R.id.editEmail);
        editPassword = view.findViewById(R.id.editPassword);

        btnLogin = view.findViewById(R.id.btnLogin);
        tvRegister = view.findViewById(R.id.tvRegister);

        viewModel = new ViewModelProvider(requireActivity())
                .get(AuthViewModel.class);

        ImageView toggle = view.findViewById(R.id.ivTogglePassword);

        toggle.setOnClickListener(v -> {
            boolean visible = editPassword.getTransformationMethod() == PasswordTransformationMethod.getInstance();

            if (visible) {
                editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }

            editPassword.setSelection(editPassword.getText().length());
        });

        NavController navController = NavHostFragment.findNavController(this);

        btnLogin.setVisibility(GONE);

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

        btnLogin.setOnClickListener(v -> {

            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            viewModel.login(email, password);
        });

        tvRegister.setOnClickListener(v ->
                navController.navigate(R.id.registrationNameFragment)
        );

        viewModel.getToken().observe(getViewLifecycleOwner(), token -> {

            if (token == null) return;

            RetrofitClient.saveToken(token);
            RetrofitClient.reset();

            Toast.makeText(requireContext(),
                    "Успешный вход", Toast.LENGTH_SHORT).show();

            navController.navigate(R.id.nav_home, null,
                    new NavOptions.Builder()
                            .setPopUpTo(R.id.loginFragment, true)
                            .build());

            viewModel.clearToken();
        });

        if (!editEmail.getText().toString().isEmpty() && !editPassword.getText().toString().isEmpty()){
            btnLogin.setVisibility(VISIBLE);
        }
        else btnLogin.setVisibility(GONE);

        return view;
    }
    private void checkFields() {
        boolean isFilled =
                !editEmail.getText().toString().trim().isEmpty() &&
                        !editPassword.getText().toString().trim().isEmpty();

        btnLogin.setVisibility(isFilled ? VISIBLE : GONE);
    }

    private void saveToken(String token) {
        SharedPreferences prefs = requireContext()
                .getSharedPreferences("auth", Context.MODE_PRIVATE);

        prefs.edit().putString("token", token).apply();
    }
}