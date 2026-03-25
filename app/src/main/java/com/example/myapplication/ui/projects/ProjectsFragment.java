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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentProjectsBinding;

public class ProjectsFragment extends Fragment {

    private FragmentProjectsBinding binding;
    private ProjectsAdapter adapter;
    private EditText editTextSearch;

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
        dummyData.add(new Project("Проект 1: Новое приложение", "Создание мобильного приложения для учета финансов.", "Детали: Требуется интеграция с API банка, разработка дизайна.", "Иванов И.И."));
        dummyData.add(new Project("Проект 2: Редизайн сайта", "Обновление визуальной части корпоративного сайта.", "Детали: Использование Figma, адаптивная верстка, оптимизация скорости загрузки.", "Петров П.П."));

        adapter.setData(dummyData);

        adapter.setOnProjectClickListener(this::showProjectDetailsDialog);

        return view;
    }

    private void showFilterDialog() {
        // Ваш код фильтра (без изменений)
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Фильтры");
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_filters, null);
        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();

        Button btnApply = dialogView.findViewById(R.id.btnApply);
        btnApply.setOnClickListener(v -> dialog.dismiss());

        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void performSearch() {
        String query = editTextSearch.getText().toString().trim();
        adapter.filter(query);
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