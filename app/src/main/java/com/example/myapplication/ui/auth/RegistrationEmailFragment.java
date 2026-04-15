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

public class RegistrationEmailFragment extends Fragment {

    private EditText editEmail, editFaculty, editGroup;
    private Button btnNext;

    private AuthViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_registration_email, container, false);

        editEmail = view.findViewById(R.id.editEmail);
        editFaculty = view.findViewById(R.id.editFaculty);
        editGroup = view.findViewById(R.id.editGroup);
        btnNext = view.findViewById(R.id.btnNext);

        viewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        btnNext.setOnClickListener(v -> {

            String email = editEmail.getText().toString().trim();
            String faculty = editFaculty.getText().toString().trim();
            String group = editGroup.getText().toString().trim();

            if (TextUtils.isEmpty(email) ||
                    TextUtils.isEmpty(faculty) ||
                    TextUtils.isEmpty(group)) {

                Toast.makeText(requireContext(),
                        "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.email = email;
            viewModel.faculty = faculty;
            viewModel.group = group;

            NavHostFragment.findNavController(this)
                    .navigate(R.id.registrationPasswordFragment);
        });

        return view;
    }
}