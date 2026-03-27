package com.example.myapplication;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_main);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        DrawerLayout drawerLayout = binding.drawerLayout;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_profile,
                R.id.nav_menu,
                R.id.nav_projects,
                R.id.nav_news,
                R.id.nav_chats)
                .setOpenableLayout(drawerLayout)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);

        if (bottomNavigationView != null) {
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}