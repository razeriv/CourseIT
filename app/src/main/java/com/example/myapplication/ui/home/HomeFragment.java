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
import com.example.myapplication.ui.data.NewsRepository;
import com.example.myapplication.ui.data.NewsViewModel;
import com.example.myapplication.ui.data.ProjectsRepository;
import com.example.myapplication.ui.data.ProjectsViewModel;
import com.example.myapplication.ui.news.NewsAdapter;
import com.example.myapplication.ui.projects.ProjectsAdapter;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        ProjectsAdapter projectAdapter =
                new ProjectsAdapter(ProjectsAdapter.TYPE_HORIZONTAL);
        NewsAdapter newsAdapter = new NewsAdapter();

        binding.recyclerRecommendationProjects.setLayoutManager(
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false)
        );

        binding.recyclerRecommendationNews.setLayoutManager(
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL,
                        false)
        );

        binding.recyclerRecommendationProjects.setAdapter(projectAdapter);
        binding.recyclerRecommendationNews.setAdapter(newsAdapter);

        binding.btnProjects.buttonText.setText("Проекты");
        binding.btnInternShips.buttonText.setText("Стажировки");
        binding.btnEvents.buttonText.setText("Мероприятия");
        binding.btnCommunity.buttonText.setText("Сообщество");

        NavController navController = NavHostFragment.findNavController(this);

        ProjectsViewModel viewModelProjects = new ViewModelProvider(requireActivity())
                .get(ProjectsViewModel.class);

        NewsViewModel viewModelNews = new ViewModelProvider(requireActivity())
                .get(NewsViewModel.class);

        if (viewModelProjects.getProjects().getValue() == null) {
            viewModelProjects.loadProjects();
        }

        if (viewModelNews.getNews().getValue() == null) {
            viewModelNews.setNews(NewsRepository.getNews());
        }

        viewModelProjects.getProjects().observe(getViewLifecycleOwner(),
                projectAdapter::setData);

        viewModelNews.getNews().observe(getViewLifecycleOwner(),
                newsAdapter::setData);

        projectAdapter.setOnProjectClickListener(project -> {

            Bundle bundle = new Bundle();
            bundle.putString("title", project.getTitle());
            bundle.putString("details", project.getDetails());
            bundle.putString("date", project.getDeadline());
            bundle.putString("instructor", project.getInstructor());
            bundle.putString("difficulty", project.getDifficulty());
            bundle.putString("deadline", project.getDeadline());
            bundle.putString("requirements", project.getRequirements());

            navController.navigate(R.id.ProjectDetailsFragment, bundle);
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}