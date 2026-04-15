package com.example.myapplication.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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

        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(requireActivity())
                .get(NewsViewModel.class);

        viewModel.loadNews();

        viewModel.getNews().observe(getViewLifecycleOwner(), list -> {
            if(list != null) {
                adapter.setData(list);
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