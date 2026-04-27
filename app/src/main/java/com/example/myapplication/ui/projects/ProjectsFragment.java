package com.example.myapplication.ui.projects;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentProjectsBinding;
import com.example.myapplication.ui.data.ProjectsViewModel;
import com.google.android.material.chip.Chip;

import android.app.AlertDialog;

public class ProjectsFragment extends Fragment {

    private FragmentProjectsBinding binding;
    private ProjectsAdapter adapter;
    private ProjectsViewModel viewModel;

    // Фильтры
    private boolean filterWeb = false;
    private boolean filterAdmin = false;
    private boolean filterAndroid = false;
    private boolean filterAnalytics = false;
    private boolean filterAI = false;
    private boolean filterDB = false;
    private String filterDifficulty = "";
    private String filterDateFrom = "";
    private String filterDateTo = "";

    private EditText editTextSearch;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProjectsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ProjectsViewModel.class);

        setupRecyclerView();
        setupSearch();
        setupFilterButton();

        viewModel.loadProjects();
        observeViewModel();

        return binding.getRoot();
    }

    private void setupRecyclerView() {
        adapter = new ProjectsAdapter(
                ProjectsAdapter.TYPE_VERTICAL,
                project -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("title", project.getTitle());
                    bundle.putString("details", project.getDetails());
                    bundle.putString("instructor", project.getInstructor());
                    bundle.putString("difficulty", project.getDifficulty());
                    bundle.putString("deadline", project.getDeadline());
                    bundle.putString("requirements", project.getRequirements());

                    NavHostFragment.findNavController(this)
                            .navigate(R.id.ProjectDetailsFragment, bundle);
                }
        );

        binding.recyclerViewProjects.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewProjects.setAdapter(adapter);
    }

    private void setupSearch() {
        editTextSearch = binding.editTextSearch;

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                applyFilters();
            }
        });
    }

    private void setupFilterButton() {
        ImageButton btnFilter = binding.btnFilter;
        btnFilter.setOnClickListener(v -> showFilterDialog());
    }

    private void observeViewModel() {
        viewModel.getProjects().observe(getViewLifecycleOwner(), projects -> {
            if (projects != null) {
                adapter.submitList(projects);
            }
        });
    }

    private void applyFilters() {
        String query = (editTextSearch.getText() == null)
                ? ""
                : editTextSearch.getText().toString().trim();

        viewModel.applyFilters(
                query,
                filterWeb, filterAdmin, filterAndroid,
                filterAnalytics, filterAI, filterDB,
                filterDifficulty,
                filterDateFrom, filterDateTo
        );
    }

    private void showFilterDialog() {
        binding.btnFilter.setImageResource(R.drawable.ic_filter_open);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_filters, null);
        builder.setView(dialogView);

        Chip chipWeb = dialogView.findViewById(R.id.chipWeb);
        Chip chipAdmin = dialogView.findViewById(R.id.chipAdmin);
        Chip chipAndroid = dialogView.findViewById(R.id.chipAndroid);
        Chip chipAnalytics = dialogView.findViewById(R.id.chipAnalytics);
        Chip chipAI = dialogView.findViewById(R.id.chipAI);
        Chip chipDB = dialogView.findViewById(R.id.chipDB);

        RadioGroup difficultyGroup = dialogView.findViewById(R.id.radioGroupDifficulty);
        EditText etDateFrom = dialogView.findViewById(R.id.editTextDateFrom);
        EditText etDateTo = dialogView.findViewById(R.id.editTextDateTo);

        Button btnApply = dialogView.findViewById(R.id.btnApply);
        Button btnReset = dialogView.findViewById(R.id.btnReset);

        AlertDialog dialog = builder.create();

        chipWeb.setChecked(filterWeb);
        chipAdmin.setChecked(filterAdmin);
        chipAndroid.setChecked(filterAndroid);
        chipAnalytics.setChecked(filterAnalytics);
        chipAI.setChecked(filterAI);
        chipDB.setChecked(filterDB);

        etDateFrom.setText(filterDateFrom);
        etDateTo.setText(filterDateTo);

        if ("лёгкий".equals(filterDifficulty)) difficultyGroup.check(R.id.radioEasy);
        else if ("средний".equals(filterDifficulty)) difficultyGroup.check(R.id.radioMedium);
        else if ("сложный".equals(filterDifficulty)) difficultyGroup.check(R.id.radioHard);

        btnApply.setOnClickListener(v -> {
            filterWeb = chipWeb.isChecked();
            filterAdmin = chipAdmin.isChecked();
            filterAndroid = chipAndroid.isChecked();
            filterAnalytics = chipAnalytics.isChecked();
            filterAI = chipAI.isChecked();
            filterDB = chipDB.isChecked();

            filterDateFrom = etDateFrom.getText().toString().trim();
            filterDateTo = etDateTo.getText().toString().trim();

            int selectedId = difficultyGroup.getCheckedRadioButtonId();
            filterDifficulty = getDifficultyFromId(selectedId);

            applyFilters();
            dialog.dismiss();
        });

        btnReset.setOnClickListener(v -> {
            resetFilters();
            applyFilters();
            dialog.dismiss();
        });

        dialog.setOnDismissListener(d ->
                binding.btnFilter.setImageResource(R.drawable.ic_filter_close)
        );

        dialog.show();
    }

    private String getDifficultyFromId(int selectedId) {
        if (selectedId == R.id.radioEasy) return "лёгкий";
        if (selectedId == R.id.radioMedium) return "средний";
        if (selectedId == R.id.radioHard) return "сложный";
        return "";
    }

    private void resetFilters() {
        filterWeb = filterAdmin = filterAndroid = filterAnalytics = filterAI = filterDB = false;
        filterDifficulty = "";
        filterDateFrom = "";
        filterDateTo = "";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}