package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.ui.auth.AuthViewModel;
import com.example.myapplication.ui.network.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RetrofitClient.init(getApplicationContext());

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_main);

        if (navHostFragment == null) return;
        navController = navHostFragment.getNavController();

        bottomNav = findViewById(R.id.bottom_nav_view);

        setupBottomNavigation();
        setupDrawer();
        setupToolbar();
        observeDestinationChanges();

        checkAuthAndRedirect();
    }

    private void checkAuthAndRedirect() {
        String token = RetrofitClient.getTokenFromPrefs();

        Log.d("MainActivity", "checkAuthAndRedirect - Token: " + (token != null ? "exists" : "null"));

        if (token == null || token.isEmpty()) {
            navController.navigate(R.id.loginFragment, null,
                    new NavOptions.Builder()
                            .setPopUpTo(R.id.main_graph, true)
                            .build());
            Log.d("MainActivity", "No token → redirected to Login");
        } else {
            Log.d("MainActivity", "Token exists → opening Home");
            navController.navigate(R.id.nav_home);
        }
    }

    public void logout() {
        RetrofitClient.clearToken();

        AuthViewModel authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        authViewModel.clearToken();

        navController.navigate(R.id.loginFragment, null,
                new NavOptions.Builder()
                        .setPopUpTo(R.id.main_graph, true)
                        .build());

        Toast.makeText(this, "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show();
    }

    private void setupBottomNavigation() {
        bottomNav.setOnItemSelectedListener(item -> {
            int currentId = navController.getCurrentDestination() != null
                    ? navController.getCurrentDestination().getId() : -1;

            if (item.getItemId() == currentId) {
                return true;
            }

            if (item.getItemId() == R.id.nav_home) {
                navController.navigate(R.id.nav_home);
            } else if (item.getItemId() == R.id.nav_news) {
                navController.navigate(R.id.nav_news);
            } else if (item.getItemId() == R.id.nav_chats) {
                navController.navigate(R.id.nav_chats);
            } else if (item.getItemId() == R.id.nav_projects || item.getItemId() == R.id.nav_create_project || item.getItemId() == R.id.ProjectDetailsFragment) {
                navController.navigate(R.id.nav_projects);
            } else if (item.getItemId() == R.id.nav_profile || item.getItemId() == R.id.nav_text_edit || item.getItemId() == R.id.nav_portfolio || item.getItemId() == R.id.nav_reviews) {
                navController.navigate(R.id.nav_profile);
            }

            return true;
        });
    }

    private void setupDrawer() {
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        ImageView btnMenu = binding.appBarMain.toolbar.findViewById(R.id.btnMenu);

        btnMenu.setOnClickListener(v -> drawer.openDrawer(GravityCompat.END));

        findViewById(R.id.menuHome).setOnClickListener(v -> navigateAndClose(R.id.nav_home, drawer));
        findViewById(R.id.menuProfile).setOnClickListener(v -> navigateAndClose(R.id.nav_profile, drawer));
        findViewById(R.id.menuChats).setOnClickListener(v -> navigateAndClose(R.id.nav_chats, drawer));
        findViewById(R.id.menuProjects).setOnClickListener(v -> navigateAndClose(R.id.nav_projects, drawer));
        findViewById(R.id.menuNews).setOnClickListener(v -> navigateAndClose(R.id.nav_news, drawer));
        findViewById(R.id.menuSettings).setOnClickListener(v -> navigateAndClose(R.id.nav_settings, drawer));

        findViewById(R.id.btnHide).setOnClickListener(v -> drawer.closeDrawer(GravityCompat.END));
    }

    private void navigateAndClose(int destination, DrawerLayout drawer) {
        navController.navigate(destination);
        drawer.closeDrawer(GravityCompat.END);
    }

    private void setupToolbar() {
        ImageView btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            NavDestination current = navController.getCurrentDestination();
            if (current == null) return;

            int id = current.getId();
            if (id == R.id.nav_news || id == R.id.nav_projects ||
                    id == R.id.nav_chats || id == R.id.nav_profile) {
                navController.navigate(R.id.nav_home);
            } else {
                navController.navigateUp();
            }
        });
    }

    private void observeDestinationChanges() {
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int id = destination.getId();

            updateBottomNavSelection(id);

            boolean isAuthScreen = id == R.id.loginFragment ||
                    id == R.id.registrationNameFragment ||
                    id == R.id.registrationEmailFragment ||
                    id == R.id.registrationPasswordFragment;

            bottomNav.setVisibility(isAuthScreen ? View.GONE : View.VISIBLE);
            binding.appBarMain.toolbar.setVisibility(isAuthScreen ? View.GONE : View.VISIBLE);

            ImageView btnBack = findViewById(R.id.btnBack);
            TextView title = binding.appBarMain.toolbar.findViewById(R.id.toolbarTitle);

            if (id == R.id.nav_home) {
                btnBack.setVisibility(View.GONE);
                title.setText("Главная");
            } else {
                btnBack.setVisibility(View.VISIBLE);

                if (id == R.id.nav_news) title.setText("Новости");
                else if (id == R.id.nav_projects) title.setText("Проекты");
                else if (id == R.id.nav_chats) title.setText("Чаты");
                else if (id == R.id.nav_profile) title.setText("Профиль");
                else if (id == R.id.ProjectDetailsFragment) title.setText("Детали проекта");
                else if (id == R.id.nav_portfolio) title.setText("Портфолио");
                else if (id == R.id.nav_reviews) title.setText("Отзывы");
                else if (id == R.id.nav_text_edit) title.setText("О себе");
                else title.setText("CourseIT");
            }
        });
    }

    private void updateBottomNavSelection(int destinationId) {
        int menuItemId = -1;

        if (destinationId == R.id.nav_home) {
            menuItemId = R.id.nav_home;
        } else if (destinationId == R.id.nav_projects) {
            menuItemId = R.id.nav_projects;
        } else if (destinationId == R.id.nav_chats) {
            menuItemId = R.id.nav_chats;
        } else if (destinationId == R.id.nav_news) {
            menuItemId = R.id.nav_news;
        } else if (destinationId == R.id.nav_profile) {
            menuItemId = R.id.nav_profile;
        }

        if (menuItemId != -1) {
            bottomNav.setSelectedItemId(menuItemId);
        }
    }
}