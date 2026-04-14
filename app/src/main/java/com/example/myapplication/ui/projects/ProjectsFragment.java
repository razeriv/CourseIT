package com.example.myapplication.ui.projects;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.List;
import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentProjectsBinding;
import com.example.myapplication.ui.data.ProjectsRepository;
import com.example.myapplication.ui.data.ProjectsViewModel;
import com.google.android.material.chip.Chip;

public class ProjectsFragment extends Fragment {

    private FragmentProjectsBinding binding;
    private ProjectsAdapter adapter;
    private EditText editTextSearch;
    private boolean filterWeb = false;
    private boolean filterAdmin = false;
    private boolean filterAndroid = false;
    private boolean filterAI = false;
    private boolean filterAnalytics = false;
    private boolean filterDB = false;
    private String filterDifficulty = "";

    private String filterDateFrom = "";
    private String filterDateTo = "";
    ProjectsViewModel viewModel;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProjectsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity()).get(ProjectsViewModel.class);

        adapter = new ProjectsAdapter(ProjectsAdapter.TYPE_VERTICAL);
        binding.recyclerViewProjects.setAdapter(adapter);
        this.editTextSearch = binding.editTextSearch;

        final GestureDetector gestureDetector = new GestureDetector(requireContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
                Drawable[] drawables = editTextSearch.getCompoundDrawables();
                if (drawables[2] == null) return false;
                int iconWidth = drawables[2].getBounds().width();
                int iconPadding = editTextSearch.getPaddingEnd();
                float iconStartPos = editTextSearch.getRight() - iconPadding - iconWidth;
                if (e.getX() >= iconStartPos) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });

        editTextSearch.setOnTouchListener((v, event) -> {
            boolean isIconClicked = gestureDetector.onTouchEvent(event);
            if (isIconClicked) return true;
            return editTextSearch.onTouchEvent(event);
        });

        ImageButton btnFilter = binding.btnFilter;
        btnFilter.setOnClickListener(v -> showFilterDialog());

        binding.recyclerViewProjects.setLayoutManager(new LinearLayoutManager(requireContext()));
        viewModel.loadProjects();

        viewModel.getProjects().observe(getViewLifecycleOwner(), list -> {
            if (list != null) {
                adapter.setData(list);
            }
        });

        adapter.setOnProjectClickListener(project -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", project.getTitle());
            bundle.putString("details", project.getDetails());
            bundle.putString("instructor", project.getInstructor());
            bundle.putString("difficulty", project.getDifficulty());
            bundle.putString("deadline", project.getDeadline());
            bundle.putString("requirements", project.getRequirements());

            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.ProjectDetailsFragment, bundle);
        });

        return view;
    }
    private void applyFilters() {
        String query = editTextSearch.getText() == null
                ? ""
                : editTextSearch.getText().toString().trim();

        adapter.applyFilters(
                query,
                filterWeb,
                filterAdmin,
                filterAndroid,
                filterAnalytics,
                filterAI,
                filterDB,
                filterDifficulty,
                filterDateFrom,
                filterDateTo
        );
    }

    private void showFilterDialog() {

        binding.btnFilter.setImageResource(R.drawable.ic_filter_open);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_filters, null);

        builder.setView(view);

        Chip web = view.findViewById(R.id.chipWeb);
        Chip admin = view.findViewById(R.id.chipAdmin);
        Chip android = view.findViewById(R.id.chipAndroid);
        Chip analytics = view.findViewById(R.id.chipAnalytics);
        Chip ai = view.findViewById(R.id.chipAI);
        Chip db = view.findViewById(R.id.chipDB);

        RadioGroup difficultyGroup = view.findViewById(R.id.radioGroupDifficulty);

        EditText dateFrom = view.findViewById(R.id.editTextDateFrom);
        EditText dateTo = view.findViewById(R.id.editTextDateTo);

        Button apply = view.findViewById(R.id.btnApply);
        Button reset = view.findViewById(R.id.btnReset);

        AlertDialog dialog = builder.create();

        web.setChecked(filterWeb);
        admin.setChecked(filterAdmin);
        android.setChecked(filterAndroid);
        analytics.setChecked(filterAnalytics);
        ai.setChecked(filterAI);
        db.setChecked(filterDB);

        dateFrom.setText(filterDateFrom);
        dateTo.setText(filterDateTo);

        if (filterDifficulty.equals("лёгкий"))
            difficultyGroup.check(R.id.radioEasy);

        else if (filterDifficulty.equals("средний"))
            difficultyGroup.check(R.id.radioMedium);

        else if (filterDifficulty.equals("сложный"))
            difficultyGroup.check(R.id.radioHard);


        apply.setOnClickListener(v -> {

            filterWeb = web.isChecked();
            filterAdmin = admin.isChecked();
            filterAndroid = android.isChecked();
            filterAnalytics = analytics.isChecked();
            filterAI = ai.isChecked();
            filterDB = db.isChecked();

            filterDateFrom = dateFrom.getText().toString();
            filterDateTo = dateTo.getText().toString();

            int selectedId = difficultyGroup.getCheckedRadioButtonId();

            filterDifficulty = "";

            if (selectedId == R.id.radioEasy)
                filterDifficulty = "лёгкий";

            else if (selectedId == R.id.radioMedium)
                filterDifficulty = "средний";

            else if (selectedId == R.id.radioHard)
                filterDifficulty = "сложный";

            applyFilters();
            dialog.dismiss();
        });

        reset.setOnClickListener(v -> {

            filterWeb = false;
            filterAdmin = false;
            filterAndroid = false;
            filterAnalytics = false;
            filterAI = false;
            filterDB = false;

            filterDifficulty = "";
            filterDateFrom = "";
            filterDateTo = "";

            applyFilters();
            dialog.dismiss();
        });

        dialog.setOnDismissListener(d -> {
            binding.btnFilter.setImageResource(R.drawable.ic_filter_close);
        });

        dialog.show();
    }

    private void performSearch() {
        applyFilters();
        editTextSearch.clearFocus();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}