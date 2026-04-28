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
        initViewModels();
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

    private void initViewModels() {
        portfolioViewModel = new ViewModelProvider(this).get(PortfolioViewModel.class);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
    }

    private void observeViewModels() {
        portfolioViewModel.getPortfolio().observe(getViewLifecycleOwner(), portfolioList -> {
            if (portfolioList != null) {
                adapter.setData(portfolioList);
            }
        });

        profileViewModel.getProfile().observe(getViewLifecycleOwner(), profile -> {
            if (profile != null) {
                boolean isTeacher = "teacher".equalsIgnoreCase(profile.getRole());
                binding.addButton.setVisibility(isTeacher ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}