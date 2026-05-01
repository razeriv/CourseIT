package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.util.Log;
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
import com.example.myapplication.ui.auth.AuthViewModel;
import com.example.myapplication.ui.data.NewsViewModel;
import com.example.myapplication.ui.data.ProjectsViewModel;
import com.example.myapplication.ui.news.NewsAdapter;
import com.example.myapplication.ui.projects.ProjectsAdapter;
import com.example.myapplication.ui.projects.Project;
import com.example.myapplication.ui.network.RetrofitClient;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ProjectsAdapter projectsAdapter;
    private NewsAdapter newsAdapter;

    private ProjectsViewModel projectsViewModel;
    private NewsViewModel newsViewModel;
    private AuthViewModel authViewModel;

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

        return binding.getRoot();
    }

    private void setupAdapters() {
        projectsAdapter = new ProjectsAdapter(
                ProjectsAdapter.TYPE_HORIZONTAL,
                this::onProjectClicked
        );

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
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        projectsViewModel = new ViewModelProvider(requireActivity()).get(ProjectsViewModel.class);
        newsViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);

        Log.d("HomeFragment", "setupViewModels called");

        authViewModel.getToken().observe(getViewLifecycleOwner(), token -> {
            Log.d("HomeFragment", "Token changed. Valid: " + (token != null && !token.isEmpty()));
            if (token != null && !token.isEmpty()) {
                loadHomeData();
            }
        });

        String savedToken = RetrofitClient.getTokenFromPrefs();
        if (savedToken != null && !savedToken.isEmpty()) {
            Log.d("HomeFragment", "Saved token found → loading data");
            loadHomeData();
        } else {
            Log.d("HomeFragment", "No token → waiting for login");
        }

        projectsViewModel.getProjects().observe(getViewLifecycleOwner(), projectsAdapter::submitList);
        newsViewModel.getNews().observe(getViewLifecycleOwner(), newsAdapter::setData);
    }
    private void loadHomeData() {
        android.util.Log.d("HomeFragment", "loadHomeData() called - loading projects and news");

        if (projectsViewModel.getProjects().getValue() == null ||
                projectsViewModel.getProjects().getValue().isEmpty()) {
            projectsViewModel.loadProjects();
        }

        if (newsViewModel.getNews().getValue() == null ||
                newsViewModel.getNews().getValue().isEmpty()) {
            newsViewModel.loadNews();
        }
    }

    private void onProjectClicked(Project project) {
        Bundle bundle = new Bundle();
        bundle.putString("title", project.getTitle());
        bundle.putString("details", project.getDetails());
        bundle.putString("instructor", project.getInstructor());
        bundle.putString("difficulty", project.getDifficulty());
        bundle.putString("deadline", project.getDeadline());
        bundle.putString("requirements", project.getRequirements());

        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.ProjectDetailsFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}