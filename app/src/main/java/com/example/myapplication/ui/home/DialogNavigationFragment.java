package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;

public class DialogNavigationFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_navigation, container, false);

        NavController navController = NavHostFragment.findNavController(this);

        view.findViewById(R.id.menuProfile).setOnClickListener(v -> {
            navController.navigate(R.id.nav_profile);
            dismiss();
        });

        view.findViewById(R.id.menuProjects).setOnClickListener(v -> {
            navController.navigate(R.id.nav_projects);
            dismiss();
        });

        view.findViewById(R.id.menuChats).setOnClickListener(v -> {
            navController.navigate(R.id.nav_chats);
            dismiss();
        });

        return view;
    }
}