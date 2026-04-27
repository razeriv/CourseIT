package com.example.myapplication.ui.projects;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.data.ProjectsViewModel;

public class ProjectDetailsFragment extends Fragment {

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_project_details, container, false);

        TextView tvTitle = view.findViewById(R.id.project_title);
        TextView tvDetails = view.findViewById(R.id.project_description);
        TextView tvInstructor = view.findViewById(R.id.project_instructor);
        TextView tvDifficulty = view.findViewById(R.id.project_difficulty);
        TextView tvDate = view.findViewById(R.id.project_deadline);
        TextView tvRequirements = view.findViewById(R.id.project_requirement);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerSimilarProjects);

        Bundle args = getArguments();
        if (args != null) {
            tvTitle.setText(args.getString("title", "Без названия"));
            tvDetails.setText(args.getString("details", "Нет описания"));
            tvDifficulty.setText("Сложность: " + args.getString("difficulty", "-"));
            tvDate.setText(args.getString("date", "Нет дедлайна"));
            tvInstructor.setText("Преподаватель: " + args.getString("instructor", "Не указан"));
            tvRequirements.setText(args.getString("requirements", "Нет требований"));
        }

        ProjectsAdapter adapter = new ProjectsAdapter(
                ProjectsAdapter.TYPE_HORIZONTAL,
                this::onSimilarProjectClicked
        );

        ProjectsViewModel viewModel = new ViewModelProvider(requireActivity())
                .get(ProjectsViewModel.class);

        viewModel.getProjects().observe(getViewLifecycleOwner(), adapter::submitList);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void onSimilarProjectClicked(Project project) {
        Bundle bundle = new Bundle();
        bundle.putString("title", project.getTitle());
        bundle.putString("details", project.getDetails());
        bundle.putString("instructor", project.getInstructor());
        bundle.putString("difficulty", project.getDifficulty());
        bundle.putString("date", project.getDeadline());
        bundle.putString("requirements", project.getRequirements());

        NavHostFragment.findNavController(this)
                .navigate(R.id.ProjectDetailsFragment, bundle);
    }
}