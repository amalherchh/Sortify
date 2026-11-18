package com.example.sortify.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.sortify.models.User;
import java.util.Date;

public class PreferencesManager {
    private static final String PREFS_NAME = "SortifyPrefs";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_TOTAL_SCORE = "total_score";
    private static final String KEY_ITEMS_SORTED = "items_sorted";
    private static final String KEY_CURRENT_BADGE = "current_badge";
    private static final String KEY_LAST_LOGIN = "last_login";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_PASSWORD_HASH = "password_hash";
    private static final String KEY_CREATED_DATE = "created_date";

    // Recent items keys
    private static final String KEY_RECENT_ITEM_1 = "recent_item_1";
    private static final String KEY_RECENT_ITEM_1_CATEGORY = "recent_item_1_category";
    private static final String KEY_RECENT_ITEM_1_POINTS = "recent_item_1_points";
    private static final String KEY_RECENT_ITEM_2 = "recent_item_2";
    private static final String KEY_RECENT_ITEM_2_CATEGORY = "recent_item_2_category";
    private static final String KEY_RECENT_ITEM_2_POINTS = "recent_item_2_points";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public PreferencesManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveUser(User user) {
        // Clear all previous data first
        editor.clear().apply();

        editor.putString(KEY_USER_ID, user.getUserId())
                .putString(KEY_USER_NAME, user.getFullName())
                .putString(KEY_USER_EMAIL, user.getEmail())
                .putString(KEY_PASSWORD_HASH, user.getPasswordHash())
                .putInt(KEY_TOTAL_SCORE, user.getTotalScore())
                .putInt(KEY_ITEMS_SORTED, user.getItemsSorted())
                .putString(KEY_CURRENT_BADGE, user.getCurrentBadge())
                .putLong(KEY_CREATED_DATE, user.getCreatedDate().getTime())
                .putBoolean(KEY_IS_LOGGED_IN, true)
                .apply();

        // Recent items are already cleared by editor.clear()
    }

    public User getUser() {
        if (!isLoggedIn()) {
            return null;
        }

        User user = new User();
        user.setUserId(prefs.getString(KEY_USER_ID, ""));
        user.setFullName(prefs.getString(KEY_USER_NAME, ""));
        user.setEmail(prefs.getString(KEY_USER_EMAIL, ""));
        user.setPasswordHash(prefs.getString(KEY_PASSWORD_HASH, ""));
        user.setTotalScore(prefs.getInt(KEY_TOTAL_SCORE, 0));
        user.setItemsSorted(prefs.getInt(KEY_ITEMS_SORTED, 0));
        user.setCurrentBadge(prefs.getString(KEY_CURRENT_BADGE, "Beginner"));
        user.setCreatedDate(new Date(prefs.getLong(KEY_CREATED_DATE, System.currentTimeMillis())));

        long lastLogin = prefs.getLong(KEY_LAST_LOGIN, 0);
        if (lastLogin > 0) {
            user.setLastLoginDate(new Date(lastLogin));
        }

        return user;
    }

    public void updateUserScore(int newScore, int newItemCount, String newBadge) {
        editor.putInt(KEY_TOTAL_SCORE, newScore)
                .putInt(KEY_ITEMS_SORTED, newItemCount)
                .putString(KEY_CURRENT_BADGE, newBadge)
                .apply();
    }

    public void awardPoints(int points, String itemName, String category) {
        int currentScore = prefs.getInt(KEY_TOTAL_SCORE, 0);
        int itemsCount = prefs.getInt(KEY_ITEMS_SORTED, 0);
        int newScore = currentScore + points;
        int newItemsCount = itemsCount + 1;

        String newBadge = calculateBadge(newScore);
        updateUserScore(newScore, newItemsCount, newBadge);

        addRecentItem(itemName, category, points);
    }

    public void awardPoints(int points) {
        awardPoints(points, "Unknown item", "General");
    }

    private String calculateBadge(int score) {
        if (score >= 100) {
            return "Recycling Hero";
        } else if (score >= 50) {
            return "Eco-Learner";
        } else {
            return "Beginner";
        }
    }

    public int getTotalScore() {
        return prefs.getInt(KEY_TOTAL_SCORE, 0);
    }

    public int getItemsSorted() {
        return prefs.getInt(KEY_ITEMS_SORTED, 0);
    }

    public String getCurrentBadge() {
        return prefs.getString(KEY_CURRENT_BADGE, "Beginner");
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void loginUser(String userName) {
        editor.putString(KEY_USER_NAME, userName)
                .putLong(KEY_LAST_LOGIN, System.currentTimeMillis())
                .putBoolean(KEY_IS_LOGGED_IN, true)
                .apply();
    }

    public void logoutUser() {
        editor.putBoolean(KEY_IS_LOGGED_IN, false).apply();
    }

    public void clearUserData() {
        editor.clear().apply();
    }

    public String getUserName() {
        return prefs.getString(KEY_USER_NAME, "User");
    }

    public String getUserEmail() {
        return prefs.getString(KEY_USER_EMAIL, "");
    }

    public String getBadgeEmoji(String badgeName) {
        switch (badgeName) {
            case "Recycling Hero": return "🏆";
            case "Eco-Learner": return "♻️";
            default: return "🌱";
        }
    }

    public int getProgressToNextBadge() {
        int score = getTotalScore();
        String badge = getCurrentBadge();
        if (badge.equals("Recycling Hero")) {
            return score - 100;
        } else {
            return score % 50;
        }
    }

    public int getPointsNeededForNextBadge() {
        int score = getTotalScore();
        String badge = getCurrentBadge();
        if (badge.equals("Recycling Hero")) {
            return 0;
        } else if (badge.equals("Eco-Learner")) {
            return 100 - score;
        } else {
            return 50 - score;
        }
    }

    public String getNextBadgeName() {
        String badge = getCurrentBadge();
        if (badge.equals("Beginner")) {
            return "Eco-Learner";
        } else if (badge.equals("Eco-Learner")) {
            return "Recycling Hero";
        }
        return "";
    }

    public boolean isBadgeUnlocked(String badge) {
        int score = getTotalScore();
        switch (badge) {
            case "Recycling Hero": return score >= 100;
            case "Eco-Learner": return score >= 50;
            case "Beginner": return score >= 0;
            default: return false;
        }
    }

    public int getProgressPercentage() {
        int progress = getProgressToNextBadge();
        if (progress > 50) return 100;
        return (progress * 100) / 50;
    }

    // Recent Items Methods
    public void addRecentItem(String itemName, String category, int points) {
        String currentItem1 = prefs.getString(KEY_RECENT_ITEM_1, "");
        String currentCategory1 = prefs.getString(KEY_RECENT_ITEM_1_CATEGORY, "");
        int currentPoints1 = prefs.getInt(KEY_RECENT_ITEM_1_POINTS, 0);

        editor.putString(KEY_RECENT_ITEM_2, currentItem1)
                .putString(KEY_RECENT_ITEM_2_CATEGORY, currentCategory1)
                .putInt(KEY_RECENT_ITEM_2_POINTS, currentPoints1)
                .apply();

        editor.putString(KEY_RECENT_ITEM_1, itemName)
                .putString(KEY_RECENT_ITEM_1_CATEGORY, category)
                .putInt(KEY_RECENT_ITEM_1_POINTS, points)
                .apply();
    }

    public String[] getRecentItems() {
        String[] items = new String[2];
        items[0] = prefs.getString(KEY_RECENT_ITEM_1, "");
        items[1] = prefs.getString(KEY_RECENT_ITEM_2, "");
        return items;
    }

    public String[] getRecentCategories() {
        String[] categories = new String[2];
        categories[0] = prefs.getString(KEY_RECENT_ITEM_1_CATEGORY, "");
        categories[1] = prefs.getString(KEY_RECENT_ITEM_2_CATEGORY, "");
        return categories;
    }

    public int[] getRecentPoints() {
        int[] points = new int[2];
        points[0] = prefs.getInt(KEY_RECENT_ITEM_1_POINTS, 0);
        points[1] = prefs.getInt(KEY_RECENT_ITEM_2_POINTS, 0);
        return points;
    }

    // Authentication Methods
    public boolean isUserExists(String email) {
        String savedEmail = prefs.getString(KEY_USER_EMAIL, "");
        return savedEmail.equals(email);
    }

    public boolean verifyPassword(String email, String passwordHash) {
        String savedEmail = prefs.getString(KEY_USER_EMAIL, "");
        String savedPassword = prefs.getString(KEY_PASSWORD_HASH, "");
        return savedEmail.equals(email) && savedPassword.equals(passwordHash);
    }

    public User getUserByEmail(String email) {
        String savedEmail = prefs.getString(KEY_USER_EMAIL, "");
        if (savedEmail.equals(email)) {
            return getUser();
        }
        return null;
    }
    // Add this method to PreferencesManager.java
    private void clearRecentItems() {
        editor.putString(KEY_RECENT_ITEM_1, "")
                .putString(KEY_RECENT_ITEM_1_CATEGORY, "")
                .putInt(KEY_RECENT_ITEM_1_POINTS, 0)
                .putString(KEY_RECENT_ITEM_2, "")
                .putString(KEY_RECENT_ITEM_2_CATEGORY, "")
                .putInt(KEY_RECENT_ITEM_2_POINTS, 0)
                .apply();
    }
}