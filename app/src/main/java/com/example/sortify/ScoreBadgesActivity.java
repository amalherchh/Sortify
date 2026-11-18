package com.example.sortify;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sortify.R;
import com.example.sortify.utils.PreferencesManager;

public class ScoreBadgesActivity extends AppCompatActivity {

    private TextView textViewUserName, textViewUserEmail, textViewItemsCount, textViewTotalScore;
    private TextView textViewCurrentBadgeLevel, textViewProgressText;
    private ProgressBar progressBarScore;
    private TextView textViewBadge1Status, textViewBadge2Status, textViewBadge3Status;
    private Button buttonSortMore, buttonShareBadge, buttonBackProfile, buttonLogout;
    private PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_badges);

        preferencesManager = new PreferencesManager(this);

        initializeViews();
        updateProfileData();
        updateBadgeStatus();
        setupButtonListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateProfileData();
        updateBadgeStatus();
    }

    private void initializeViews() {
        textViewUserName = findViewById(R.id.textViewUserName);
        textViewUserEmail = findViewById(R.id.textViewUserEmail);
        textViewItemsCount = findViewById(R.id.textViewItemsCount);
        textViewTotalScore = findViewById(R.id.textViewTotalScore);
        textViewCurrentBadgeLevel = findViewById(R.id.textViewCurrentBadgeLevel);
        textViewProgressText = findViewById(R.id.textViewProgressText);
        progressBarScore = findViewById(R.id.progressBarScore);
        textViewBadge1Status = findViewById(R.id.textViewBadge1Status);
        textViewBadge2Status = findViewById(R.id.textViewBadge2Status);
        textViewBadge3Status = findViewById(R.id.textViewBadge3Status);
        buttonSortMore = findViewById(R.id.buttonSortMore);
        buttonShareBadge = findViewById(R.id.buttonShareBadge);
        buttonBackProfile = findViewById(R.id.buttonBackProfile);
        buttonLogout = findViewById(R.id.buttonLogout);
    }

    private void updateProfileData() {
        String userName = preferencesManager.getUserName();
        String userEmail = preferencesManager.getUserEmail();
        int itemsCount = preferencesManager.getItemsSorted();
        int totalScore = preferencesManager.getTotalScore();

        textViewUserName.setText(userName);
        textViewUserEmail.setText(userEmail);
        textViewItemsCount.setText(String.valueOf(itemsCount));
        textViewTotalScore.setText(totalScore + " pts");
    }

    private void updateBadgeStatus() {
        int totalScore = preferencesManager.getTotalScore();
        String currentBadge = preferencesManager.getCurrentBadge();
        String nextBadgeName = preferencesManager.getNextBadgeName();
        int pointsNeeded = preferencesManager.getPointsNeededForNextBadge();
        int progress = preferencesManager.getProgressToNextBadge();

        String badgeEmoji = preferencesManager.getBadgeEmoji(currentBadge);
        textViewCurrentBadgeLevel.setText("Current Level: " + badgeEmoji + " " + currentBadge);

        int progressPercentage = preferencesManager.getProgressPercentage();
        if (progressPercentage > 100) {
            progressPercentage = 100;
        }
        progressBarScore.setProgress(progressPercentage);

        if (currentBadge.equals("Recycling Hero")) {
            textViewProgressText.setText("You're a Recycling Hero! Total: " + totalScore + " points");
        } else if (currentBadge.equals("Eco-Learner")) {
            textViewProgressText.setText(progress + "/50 points to Recycling Hero");
        } else {
            textViewProgressText.setText(progress + "/50 points to Eco-Learner");
        }

        updateBadgeStatusDisplay("Beginner");
        updateBadgeStatusDisplay("Eco-Learner");
        updateBadgeStatusDisplay("Recycling Hero");
    }

    private void updateBadgeStatusDisplay(String badge) {
        boolean isUnlocked = preferencesManager.isBadgeUnlocked(badge);
        String currentBadge = preferencesManager.getCurrentBadge();

        TextView statusView = null;
        if (badge.equals("Beginner")) {
            statusView = textViewBadge1Status;
        } else if (badge.equals("Eco-Learner")) {
            statusView = textViewBadge2Status;
        } else if (badge.equals("Recycling Hero")) {
            statusView = textViewBadge3Status;
        }

        if (statusView != null) {
            if (badge.equals(currentBadge) || isUnlocked) {
                statusView.setText("✓ Earned");
                statusView.setTextColor(getResources().getColor(R.color.dark_green));
            } else {
                int pointsNeeded = calculatePointsNeeded(badge);
                statusView.setText(pointsNeeded + " points needed");
                statusView.setTextColor(getResources().getColor(android.R.color.darker_gray));
            }
        }
    }

    private int calculatePointsNeeded(String badge) {
        int totalScore = preferencesManager.getTotalScore();
        switch (badge) {
            case "Recycling Hero":
                return Math.max(0, 100 - totalScore);
            case "Eco-Learner":
                return Math.max(0, 50 - totalScore);
            case "Beginner":
            default:
                return 0;
        }
    }

    private void setupButtonListeners() {
        buttonSortMore.setOnClickListener(v -> navigateToWhatToSort());
        buttonShareBadge.setOnClickListener(v -> shareBadgeProgress());
        buttonBackProfile.setOnClickListener(v -> onBackPressed());

        // Logout button listener
        buttonLogout.setOnClickListener(v -> performLogout());
    }

    private void performLogout() {
        // Show confirmation dialog
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Clear user session
                    preferencesManager.logoutUser();

                    // Navigate to Welcome screen
                    Intent intent = new Intent(ScoreBadgesActivity.this, WelcomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                    Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void navigateToWhatToSort() {
        Intent intent = new Intent(ScoreBadgesActivity.this, WhatToSortActivity.class);
        startActivity(intent);
    }

    private void shareBadgeProgress() {
        String userName = preferencesManager.getUserName();
        String currentBadge = preferencesManager.getCurrentBadge();
        String badgeEmoji = preferencesManager.getBadgeEmoji(currentBadge);
        int totalScore = preferencesManager.getTotalScore();

        String shareText = "I'm an " + badgeEmoji + " " + currentBadge + " on SORTIFY! " +
                "I've sorted waste items and earned " + totalScore + " points toward a sustainable Tunisia! 🌍♻️\n\n" +
                "Join me in managing waste responsibly with SORTIFY.";

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        shareIntent.setType("text/plain");

        try {
            startActivity(Intent.createChooser(shareIntent, "Share Your Badge"));
        } catch (Exception e) {
            Toast.makeText(this, "Error sharing", Toast.LENGTH_SHORT).show();
        }
    }
}