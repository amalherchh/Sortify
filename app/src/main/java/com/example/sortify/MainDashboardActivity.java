package com.example.sortify;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sortify.R;
import com.example.sortify.utils.PreferencesManager;

public class MainDashboardActivity extends AppCompatActivity {

    private TextView textViewGreeting, textViewItemsCount, textViewTotalPoints;
    private TextView textViewRecentItem1, textViewRecentItem2, textViewRecentItem1Category, textViewRecentItem2Category;
    private TextView textViewRecentItem1Points, textViewRecentItem2Points;
    private Button buttonProfileDashboard, buttonStartSorting;
    private Button navHome, navSort;
    private PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);

        preferencesManager = new PreferencesManager(this);

        if (!preferencesManager.isLoggedIn()) {
            navigateToAuth();
            return;
        }

        initializeViews();
        updateDashboard();
        setupButtonListeners();
    }

    private void initializeViews() {
        textViewGreeting = findViewById(R.id.textViewGreeting);
        textViewItemsCount = findViewById(R.id.textViewItemsCount);
        textViewTotalPoints = findViewById(R.id.textViewTotalPoints);

        textViewRecentItem1 = findViewById(R.id.textViewRecentItem1);
        textViewRecentItem2 = findViewById(R.id.textViewRecentItem2);
        textViewRecentItem1Category = findViewById(R.id.textViewRecentItem1Category);
        textViewRecentItem2Category = findViewById(R.id.textViewRecentItem2Category);
        textViewRecentItem1Points = findViewById(R.id.textViewRecentItem1Points);
        textViewRecentItem2Points = findViewById(R.id.textViewRecentItem2Points);

        buttonProfileDashboard = findViewById(R.id.buttonProfileDashboard);
        buttonStartSorting = findViewById(R.id.buttonStartSorting);
        navHome = findViewById(R.id.navHome);
        navSort = findViewById(R.id.navSort);
    }

    private void updateDashboard() {
        String userName = preferencesManager.getUserName();
        int totalScore = preferencesManager.getTotalScore();
        int itemsSorted = preferencesManager.getItemsSorted();
        String currentBadge = preferencesManager.getCurrentBadge();
        String badgeEmoji = preferencesManager.getBadgeEmoji(currentBadge);

        textViewGreeting.setText("Welcome back, " + userName + "!\n" + badgeEmoji + " " + currentBadge);
        textViewItemsCount.setText(String.valueOf(itemsSorted));
        textViewTotalPoints.setText(totalScore + " pts");

        updateRecentActivity();
    }

    private void updateRecentActivity() {
        String[] recentItems = preferencesManager.getRecentItems();
        String[] recentCategories = preferencesManager.getRecentCategories();
        int[] recentPoints = preferencesManager.getRecentPoints();

        if (recentItems.length > 0 && recentItems[0] != null && !recentItems[0].isEmpty()) {
            textViewRecentItem1.setText(recentItems[0]);
            textViewRecentItem1Category.setText(recentCategories[0]);
            textViewRecentItem1Points.setText("+" + recentPoints[0] + " pts");
        } else {
            textViewRecentItem1.setText("No items yet");
            textViewRecentItem1Category.setText("Sort your first item!");
            textViewRecentItem1Points.setText("+0 pts");
        }

        if (recentItems.length > 1 && recentItems[1] != null && !recentItems[1].isEmpty()) {
            textViewRecentItem2.setText(recentItems[1]);
            textViewRecentItem2Category.setText(recentCategories[1]);
            textViewRecentItem2Points.setText("+" + recentPoints[1] + " pts");
        } else {
            textViewRecentItem2.setText("—");
            textViewRecentItem2Category.setText("Sort more items");
            textViewRecentItem2Points.setText("+0 pts");
        }
    }

    private void setupButtonListeners() {
        buttonProfileDashboard.setOnClickListener(v -> navigateToProfile());
        buttonStartSorting.setOnClickListener(v -> navigateToWhatToSort());
        navSort.setOnClickListener(v -> navigateToWhatToSort());
    }

    private void navigateToWhatToSort() {
        Intent intent = new Intent(MainDashboardActivity.this, WhatToSortActivity.class);
        startActivity(intent);
    }

    private void navigateToProfile() {
        Intent intent = new Intent(MainDashboardActivity.this, ScoreBadgesActivity.class);
        startActivity(intent);
    }

    private void navigateToAuth() {
        Intent intent = new Intent(MainDashboardActivity.this, AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}