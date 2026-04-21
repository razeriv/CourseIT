package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.ui.data.NewsViewModel;
import com.example.myapplication.ui.data.ProjectsViewModel;
import com.example.myapplication.ui.news.NewsAdapter;
import com.example.myapplication.ui.projects.ProjectsAdapter;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ProjectsAdapter projectsAdapter;
    private NewsAdapter newsAdapter;

    private ProjectsViewModel projectsViewModel;
    private NewsViewModel newsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        setupAdapters();
        setupRecyclerViews();
        setupButtons();
        setupViewModels();
        setupProjectClickListener();

        return binding.getRoot();
    }

    private void setupAdapters() {
        projectsAdapter = new ProjectsAdapter(ProjectsAdapter.TYPE_HORIZONTAL);
        newsAdapter = new NewsAdapter();
    }

    private void setupRecyclerViews() {
        binding.recyclerRecommendationProjects.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerRecommendationProjects.setAdapter(projectsAdapter);

        binding.recyclerRecommendationNews.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerRecommendationNews.setAdapter(newsAdapter);
    }

    private void setupButtons() {
        binding.btnProjects.buttonText.setText("Проекты");
        binding.btnInternShips.buttonText.setText("Стажировки");
        binding.btnEvents.buttonText.setText("Мероприятия");
        binding.btnCommunity.buttonText.setText("Сообщество");
    }

    private void setupViewModels() {
        projectsViewModel = new ViewModelProvider(requireActivity()).get(ProjectsViewModel.class);
        newsViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);

        if (projectsViewModel.getProjects().getValue() == null) {
            projectsViewModel.loadProjects();
        }
        if (newsViewModel.getNews().getValue() == null) {
            newsViewModel.loadNews();
        }

        projectsViewModel.getProjects().observe(getViewLifecycleOwner(), projectsAdapter::setData);
        newsViewModel.getNews().observe(getViewLifecycleOwner(), newsAdapter::setData);
    }

    private void setupProjectClickListener() {
        projectsAdapter.setOnProjectClickListener(project -> {
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}