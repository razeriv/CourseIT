package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.data.NewsRepository;
import com.example.myapplication.ui.data.NewsViewModel;
import com.example.myapplication.ui.data.ProjectsViewModel;
import com.example.myapplication.ui.data.ProjectsRepository;
import com.example.myapplication.ui.news.NewsAdapter;
import com.example.myapplication.ui.projects.ProjectsAdapter;

public class HomeFragment extends Fragment {

    private ProjectsViewModel viewModelProjects;
    private ProjectsAdapter projectAdapter;
    private NewsViewModel viewModelNews;
    private NewsAdapter newsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerViewProjects = root.findViewById(R.id.recyclerRecommendationProjects);
        RecyclerView recyclerViewNews = root.findViewById(R.id.recyclerRecommendationNews);

        projectAdapter = new ProjectsAdapter();
        newsAdapter = new NewsAdapter();

        recyclerViewProjects.setLayoutManager(
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false)
        );
        recyclerViewNews.setLayoutManager(
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL,
                        false)
        );

        recyclerViewProjects.setAdapter(projectAdapter);
        recyclerViewNews.setAdapter(newsAdapter);

        viewModelProjects = new ViewModelProvider(requireActivity())
                .get(ProjectsViewModel.class);
        viewModelNews = new ViewModelProvider(requireActivity())
                .get(NewsViewModel.class);

        if (viewModelProjects.getProjects().getValue() == null) {
            viewModelProjects.setProjects(ProjectsRepository.getProjects());
        }

        if (viewModelNews.getNews().getValue() == null) {
            viewModelNews.setNews(NewsRepository.getNews());
        }

        viewModelProjects.getProjects().observe(getViewLifecycleOwner(), projects -> {
            projectAdapter.setData(projects);
        });
        viewModelNews.getNews().observe(getViewLifecycleOwner(), news -> {
            newsAdapter.setData(news);
        });

        Button btnProjects = root.findViewById(R.id.btnProjects);

        NavController navController =
                Navigation.findNavController(requireActivity(),
                        R.id.nav_host_fragment_content_main);

        btnProjects.setOnClickListener(v ->
                navController.navigate(R.id.nav_projects));

        projectAdapter.setOnProjectClickListener(project -> {

            Bundle bundle = new Bundle();
            bundle.putString("title", project.getTitle());
            bundle.putString("details", project.getDetails());
            bundle.putString("instructor", project.getInstructor());
            bundle.putString("difficulty", project.getDifficulty());
            bundle.putString("deadline", project.getDeadline());

            navController.navigate(R.id.ProjectDetailsFragment, bundle);
        });

        return root;
    }
}