package com.example.myapplication.ui.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.ui.profile.Portfolio;

import java.util.List;

public class PortfolioViewModel extends ViewModel {

    private final MutableLiveData<List<Portfolio>> portfolio = new MutableLiveData<>();

    public PortfolioViewModel() {
        loadPortfolio();
    }

    private void loadPortfolio() {
        PortfolioRepository repository = new PortfolioRepository();
        portfolio.setValue(repository.getPortfolio());
    }

    public LiveData<List<Portfolio>> getPortfolio() {
        return portfolio;
    }
}