package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.myapplication.R;
import com.example.myapplication.ui.news.NewsViewModel;
import com.example.myapplication.ui.projects.ProjectsFragment;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        NewsViewModel newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        Button btnProject = rootView.findViewById(R.id.btnProjects);
        btnProject.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.home_fragment, new ProjectsFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return rootView;
    }

    private void navigateToNewsScreen() {

    }
}