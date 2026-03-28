package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment_content_main);

        if (navHostFragment == null) return;

        navController = navHostFragment.getNavController();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_view);

        NavigationUI.setupWithNavController(bottomNav, navController);

        ImageView btnBack = findViewById(R.id.btnBack);
        TextView title = binding.appBarMain.toolbar.findViewById(R.id.toolbarTitle);

        btnBack.setOnClickListener(v ->
                navController.navigate(R.id.home_fragment)
        );

        navController.addOnDestinationChangedListener((controller, destination, args) -> {

            int id = destination.getId();

            if (id == R.id.home_fragment) {

                btnBack.setVisibility(View.GONE);
                title.setText("Главная");

                for (int i = 0; i < bottomNav.getMenu().size(); i++) {
                    bottomNav.getMenu().getItem(i).setChecked(false);
                }

            }
            else {

                btnBack.setVisibility(View.VISIBLE);

                if (id == R.id.nav_menu)
                    title.setText("Меню");

                else if (id == R.id.nav_news)
                    title.setText("Новости");

                else if (id == R.id.nav_projects)
                    title.setText("Проекты");

                else if (id == R.id.nav_chats)
                    title.setText("Чаты");

                else if (id == R.id.nav_profile)
                    title.setText("Профиль");
            }

            boolean isMainScreen =
                    id == R.id.home_fragment ||
                            id == R.id.nav_news ||
                            id == R.id.nav_projects ||
                            id == R.id.nav_chats ||
                            id == R.id.nav_profile ||
                            id == R.id.nav_menu;

            bottomNav.setVisibility(View.VISIBLE);
        });

        binding.appBarMain.toolbar.findViewById(R.id.btnMenu)
                .setOnClickListener(v -> {
                    if (binding.drawerLayout != null) {
                        binding.drawerLayout.openDrawer(
                                androidx.core.view.GravityCompat.END);
                    }
                });
    }
}