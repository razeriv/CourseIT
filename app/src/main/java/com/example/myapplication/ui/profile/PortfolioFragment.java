package com.example.myapplication.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentPortfolioBinding;
import com.example.myapplication.ui.data.PortfolioViewModel;

import java.util.List;

public class PortfolioFragment extends Fragment {

    private FragmentPortfolioBinding binding;
    private PortfolioAdapter adapter;

    private PortfolioViewModel portfolioViewModel;
    private ProfileViewModel profileViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPortfolioBinding.inflate(inflater, container, false);

        setupRecyclerView();
        setupAddButton();
        observeViewModels();

        portfolioViewModel.loadPortfolio();
        profileViewModel.loadProfile();

        return binding.getRoot();
    }

    private void setupRecyclerView() {
        adapter = new PortfolioAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);
    }

    private void setupAddButton() {
        binding.addButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.nav_create_project);
        });
    }

    private void observeViewModels() {
        portfolioViewModel = new ViewModelProvider(this).get(PortfolioViewModel.class);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        portfolioViewModel.getPortfolio().observe(getViewLifecycleOwner(), this::updatePortfolioUI);

        profileViewModel.getProfile().observe(getViewLifecycleOwner(), profile -> {
            if (profile != null) {
                boolean isTeacher = profile.isTeacher();
                binding.addButton.setVisibility(isTeacher ? View.VISIBLE : View.GONE);
            }
        });
    }

    private void updatePortfolioUI(List<Portfolio> portfolioList) { 
        if (portfolioList == null || portfolioList.isEmpty()) {
            adapter.setData(null);
            binding.recyclerView.setVisibility(View.GONE);
            binding.emptyView.setVisibility(View.VISIBLE);
        } else {
            adapter.setData(portfolioList);
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}