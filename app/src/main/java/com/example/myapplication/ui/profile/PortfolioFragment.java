package com.example.myapplication.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.data.PortfolioViewModel;

public class PortfolioFragment extends Fragment {

    public PortfolioFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_portfolio, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        PortfolioAdapter adapter = new PortfolioAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        PortfolioViewModel viewModel = new ViewModelProvider(this)
                .get(PortfolioViewModel.class);

        viewModel.getPortfolio().observe(getViewLifecycleOwner(), adapter::setData);

        return view;
    }
}