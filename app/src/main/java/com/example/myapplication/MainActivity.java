package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.ui.network.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RetrofitClient.init(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment_content_main);

        if (navHostFragment == null) return;

        navController = navHostFragment.getNavController();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_view);

        SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
        String token = prefs.getString("token", null);

       // binding.getRoot().post(() -> {
       //     if (token == null) {
       //         navController.navigate(R.id.loginFragment, null,
       //                 new androidx.navigation.NavOptions.Builder()
       //                         .setPopUpTo(R.id.main_graph, true)
       //                         .build());
       //     }
       // });

        bottomNav.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                navController.navigate(R.id.nav_home, null,
                        new androidx.navigation.NavOptions.Builder()
                                .setPopUpTo(R.id.main_graph, false)
                                .build());
                return true;
            }

            if (itemId == R.id.nav_projects) {
                navController.navigate(R.id.nav_projects);
                return true;
            }

            if (itemId == R.id.nav_chats) {
                navController.navigate(R.id.nav_chats);
                return true;
            }

            if (itemId == R.id.nav_news) {
                navController.navigate(R.id.nav_news);
                return true;
            }

            if (itemId == R.id.nav_profile) {
                navController.popBackStack(R.id.nav_profile, false);
                navController.navigate(R.id.nav_profile);
                return true;
            }

            return false;
        });

        ImageView btnBack = findViewById(R.id.btnBack);
        TextView title = binding.appBarMain.toolbar.findViewById(R.id.toolbarTitle);

        btnBack.setOnClickListener(v -> navController.navigateUp());

        navController.addOnDestinationChangedListener((controller, destination, args) -> {

            int id = destination.getId();

            if (id == R.id.loginFragment || id == R.id.registrationNameFragment|| id == R.id.registrationEmailFragment || id == R.id.registrationPasswordFragment) {
                bottomNav.setVisibility(View.GONE);
                binding.appBarMain.toolbar.setVisibility(View.GONE);
                return;
            } else {
                bottomNav.setVisibility(View.VISIBLE);
                binding.appBarMain.toolbar.setVisibility(View.VISIBLE);
            }

            if (id == R.id.nav_home) {
                btnBack.setVisibility(View.GONE);
                title.setText("");
            } else {
                btnBack.setVisibility(View.VISIBLE);

                if (id == R.id.nav_news)
                    title.setText("Новости");

                else if (id == R.id.nav_projects)
                    title.setText("Проекты");

                else if (id == R.id.nav_chats)
                    title.setText("Чаты");

                else if (id == R.id.nav_profile)
                    title.setText("Профиль");

                else if (id == R.id.ProjectDetailsFragment)
                    title.setText("Детали проекта");

                else if (id == R.id.nav_portfolio)
                    title.setText("Портфолио");

                else if (id == R.id.nav_reviews)
                    title.setText("Отзывы");

                else if (id == R.id.nav_text_edit)
                    title.setText("О себе");
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        ImageView btnHide = findViewById(R.id.btnHide);

        binding.appBarMain.toolbar.findViewById(R.id.btnMenu)
                .setOnClickListener(v ->
                        drawer.openDrawer(GravityCompat.END)
                );

        btnHide.setOnClickListener(v ->
                drawer.closeDrawer(GravityCompat.END)
        );
    }
}