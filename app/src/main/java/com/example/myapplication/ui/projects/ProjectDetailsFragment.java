package com.example.myapplication.ui.projects;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.data.ProjectsViewModel;

public class ProjectDetailsFragment extends Fragment {

    private TextView tvTitle, tvDetails, tvInstructor, tvDifficulty, tvDate, tvRequirements;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_project_details, container, false);

        tvTitle = view.findViewById(R.id.project_title);
        tvDetails = view.findViewById(R.id.project_description);
        tvInstructor = view.findViewById(R.id.project_instructor);
        tvDifficulty = view.findViewById(R.id.project_difficulty);
        tvDate = view.findViewById(R.id.project_deadline);
        tvRequirements = view.findViewById(R.id.project_requirement);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerSimilarProjects);

        Bundle args = getArguments();
        if (args != null) {
            tvTitle.setText(args.getString("title"));
            tvDetails.setText(args.getString("details"));
            tvDifficulty.setText("Сложность: " + args.getString("difficulty"));
            tvDate.setText(args.getString("date"));
            tvInstructor.setText("Преподаватель: " + args.getString("instructor"));
            tvRequirements.setText(args.getString("requirements"));
        }

        ProjectsAdapter adapter =
                new ProjectsAdapter(ProjectsAdapter.TYPE_HORIZONTAL);

        ProjectsViewModel viewModel = new ViewModelProvider(requireActivity())
                .get(ProjectsViewModel.class);

        viewModel.getProjects().observe(getViewLifecycleOwner(), adapter::setData);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        recyclerView.setAdapter(adapter);

        adapter.setOnProjectClickListener(project -> {

            Bundle bundle = new Bundle();
            bundle.putString("title", project.getTitle());
            bundle.putString("details", project.getDetails());
            bundle.putString("instructor", project.getInstructor());
            bundle.putString("difficulty", project.getDifficulty());
            bundle.putString("date", project.getDeadline());
            bundle.putString("requirements", project.getRequirements());

            NavHostFragment.findNavController(this)
                    .navigate(R.id.ProjectDetailsFragment, bundle);
        });
        return view;
    }
}