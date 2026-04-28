package com.example.myapplication.ui.projects;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentCreateProjectBinding;
import com.example.myapplication.ui.data.ProjectsRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateProjectFragment extends Fragment {

    private FragmentCreateProjectBinding binding;
    private final ProjectsRepository repository = new ProjectsRepository();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCreateProjectBinding.inflate(inflater, container, false);

        setupChipGroup();
        setupSaveButton();

        return binding.getRoot();
    }

    private void setupChipGroup() {
        binding.chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
        });
    }

    private void setupSaveButton() {
        binding.btnAddProject.setOnClickListener(v -> {
            String title = binding.etProjectTitle.getText().toString().trim();
            String description = binding.etShortDescription.getText().toString().trim();

            int checkedId = binding.radioGroupStatus.getCheckedRadioButtonId();
            String status = getStatusFromRadio(checkedId);

            if (title.isEmpty()) {
                Toast.makeText(requireContext(), "Введите название проекта", Toast.LENGTH_SHORT).show();
                return;
            }

            if (description.isEmpty()) {
                Toast.makeText(requireContext(), "Введите краткое описание", Toast.LENGTH_SHORT).show();
                return;
            }

            CreateProjectRequest request = new CreateProjectRequest(
                    title,
                    description,
                    "Другое",
                    status
            );

            createProject(request);
        });
    }

    private String getStatusFromRadio(int checkedId) {
        if (checkedId == R.id.rbCompleted) return "завершен";
        if (checkedId == R.id.rbInProgress) return "в процессе";
        return "открыт";
    }

    private void createProject(CreateProjectRequest request) {
        binding.btnAddProject.setEnabled(false);

        repository.createProject(request, new Callback<Project>() {
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                binding.btnAddProject.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    Log.d("CreateProject", "Проект успешно создан: " + response.body().getTitle());
                    Toast.makeText(requireContext(), "Проект успешно создан!", Toast.LENGTH_LONG).show();
                    requireActivity().onBackPressed();
                } else {
                    Log.e("CreateProject", "Ошибка сервера. Код: " + response.code());
                    try {
                        if (response.errorBody() != null) {
                            Log.e("CreateProject", "Error body: " + response.errorBody().string());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(requireContext(), "Не удалось создать проект", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Project> call, Throwable t) {
                binding.btnAddProject.setEnabled(true);
                Log.e("CreateProject", "Ошибка подключения", t);
                Toast.makeText(requireContext(), "Ошибка подключения к серверу", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}