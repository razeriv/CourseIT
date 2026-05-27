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

public class RegistrationNameFragment extends Fragment {

    private EditText editName, editSurname;
    private Button btnNext;
    private AuthViewModel viewModel;
    private TextView login;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_registration_name, container, false);

        login = view.findViewById(R.id.login);
        editName = view.findViewById(R.id.editName);
        editSurname = view.findViewById(R.id.editSurname);
        btnNext = view.findViewById(R.id.btnNext);

        viewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        btnNext.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            String surname = editSurname.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(surname)) {
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.setNameData(name, surname);
            NavHostFragment.findNavController(this)
                    .navigate(R.id.registrationEmailFragment);
        });

        login.setOnClickListener(v ->
                NavHostFragment.findNavController(this)
                        .navigate(R.id.loginFragment)
        );

        return view;
    }
}