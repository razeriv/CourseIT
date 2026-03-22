package com.example.myapplication.ui.projects;

import androidx.recyclerview.widget.LinearLayoutManager; // Важно!
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
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
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentProjectsBinding;

public class ProjectsFragment extends Fragment {

    private FragmentProjectsBinding binding;
    private ProjectsAdapter adapter;
    private EditText editTextSearch;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProjectsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        this.adapter = new ProjectsAdapter();
        binding.recyclerViewProjects.setAdapter(adapter);

        this.editTextSearch = binding.editTextSearch;

        final GestureDetector gestureDetector = new GestureDetector(requireContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                float x = e.getX();

                Drawable[] drawables = editTextSearch.getCompoundDrawables();
                if (drawables[2] == null) return false;

                int iconWidth = drawables[2].getBounds().width();
                int iconPadding = editTextSearch.getPaddingEnd();

                float iconStartPos = editTextSearch.getRight() - iconPadding - iconWidth;

                if (x >= iconStartPos) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });

        editTextSearch.setOnTouchListener((v, event) -> {
            boolean isIconClicked = gestureDetector.onTouchEvent(event);
            if (isIconClicked) {
                return true;
            }
            return editTextSearch.onTouchEvent(event);
        });

        ImageButton btnFilter = binding.btnFilter;
        btnFilter.setOnClickListener(v -> showFilterDialog());

        binding.recyclerViewProjects.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<String> dummyData = Arrays.asList(
                "Проект 1: Новое приложение",
                "Проект 2: Редизайн сайта",
                "Проект 3: Оптимизация БД",
                "Проект 4: Мобильная игра",
                "Проект 5: Интеграция API",
                "Проект 6: Еще один длинный проект для проверки переноса строк"
        );

        this.adapter.setData(dummyData);

        return view;
    }

    private void showFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Фильтры");

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_filters, null);
        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();

        Button btnApply = dialogView.findViewById(R.id.btnApply);
        btnApply.setOnClickListener(v -> {
            dialog.dismiss();
        });

        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void performSearch() {
        String query = editTextSearch.getText().toString().trim();
        adapter.filter(query);
        editTextSearch.clearFocus();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}