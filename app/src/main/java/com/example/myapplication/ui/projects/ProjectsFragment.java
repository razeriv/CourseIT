package com.example.myapplication.ui.projects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentProjectsBinding;

public class ProjectsFragment extends Fragment {

    private FragmentProjectsBinding binding;
    private ProjectsAdapter adapter;
    private EditText editTextSearch;
    private boolean filterWeb = false;
    private boolean filterAdmin = false;
    private boolean filterAndroid = false;

    private String filterDifficulty = "";

    private String filterDateFrom = "";
    private String filterDateTo = "";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProjectsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        this.adapter = new ProjectsAdapter();
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

        List<Project> dummyData = new ArrayList<>();

        dummyData.add(new Project(
                "Финансовое приложение",
                "Учет расходов",
                "Интеграция API банка",
                "Иванов И.И.",
                "android",
                "средний",
                "01.04 - 15.05"
        ));

        dummyData.add(new Project(
                "Корпоративный сайт",
                "Редизайн",
                "Figma + адаптив",
                "Петров П.П.",
                "web",
                "лёгкий",
                "10.03 - 01.04"
        ));

        dummyData.add(new Project(
                "Серверная инфраструктура",
                "Настройка Linux",
                "Docker + nginx",
                "Сидоров С.С.",
                "admin",
                "сложный",
                "15.05 - 30.06"
        ));
        adapter.setData(dummyData);

        adapter.setOnProjectClickListener(this::showProjectDetailsDialog);

        return view;
    }
    private void applyFilters() {

        String query = editTextSearch.getText().toString().trim();

        adapter.applyFilters(
                query,
                filterWeb,
                filterAdmin,
                filterAndroid,
                filterDifficulty,
                filterDateFrom,
                filterDateTo
        );
    }

    private void showFilterDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_filters, null);

        builder.setView(view);

        CheckBox web = view.findViewById(R.id.checkboxWeb);
        CheckBox admin = view.findViewById(R.id.checkboxAdmin);
        CheckBox android = view.findViewById(R.id.checkboxAndroid);

        RadioGroup difficultyGroup = view.findViewById(R.id.radioGroupDifficulty);

        EditText dateFrom = view.findViewById(R.id.editTextDateFrom);
        EditText dateTo = view.findViewById(R.id.editTextDateTo);

        Button apply = view.findViewById(R.id.btnApply);
        Button cancel = view.findViewById(R.id.btnCancel);
        Button reset = view.findViewById(R.id.btnReset);

        AlertDialog dialog = builder.create();

        web.setChecked(filterWeb);
        admin.setChecked(filterAdmin);
        android.setChecked(filterAndroid);

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

            filterDifficulty = "";
            filterDateFrom = "";
            filterDateTo = "";

            applyFilters();
            dialog.dismiss();
        });


        cancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void performSearch() {
        applyFilters();
        editTextSearch.clearFocus();
    }

    @SuppressLint("SetTextI18n")
    private void showProjectDetailsDialog(Project project) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_project_details, null);

        TextView tvTitle = dialogView.findViewById(R.id.project_title);
        TextView tvDetails = dialogView.findViewById(R.id.project_description);
        TextView tvInstructor = dialogView.findViewById(R.id.project_instructor);

        tvTitle.setText(project.getTitle());
        tvDetails.setText(project.getDetails());
        tvInstructor.setText("Преподаватель: " + project.getInstructor());

        builder.setView(dialogView)
                .setPositiveButton("Закрыть", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        Button btnRespond = dialogView.findViewById(R.id.btnRespond);
        btnRespond.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Отклик отправлен на: " + project.getTitle(), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}