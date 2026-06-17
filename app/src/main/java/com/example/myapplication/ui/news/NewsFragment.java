package com.example.myapplication.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.FragmentNewsBinding;
import com.example.myapplication.ui.data.NewsViewModel;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private NewsViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerView;
        NewsAdapter adapter = new NewsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);

        if (viewModel.getNews().getValue() == null || viewModel.getNews().getValue().isEmpty()) {
            viewModel.loadNews();
        }

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            binding.progressBar.setVisibility(isLoading != null && isLoading ? android.view.View.VISIBLE : android.view.View.GONE);
        });

        viewModel.getError().observe(getViewLifecycleOwner(), err -> {
            if (err != null) {
                android.widget.Toast.makeText(requireContext(), err, android.widget.Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getNews().observe(getViewLifecycleOwner(), list -> {
            if (list != null) {
                adapter.setData(list);
                binding.emptyState.setVisibility(list.isEmpty() ? android.view.View.VISIBLE : android.view.View.GONE);
                binding.bottomLayout.setVisibility(list.isEmpty() ? android.view.View.GONE : android.view.View.VISIBLE);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}