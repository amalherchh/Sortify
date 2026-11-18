package com.example.sortify.models;

import java.io.Serializable;
import java.util.Date;

/**
 * User model class for SORTIFY
 * Represents a user account with profile information and statistics
 */
public class User implements Serializable {
    private String userId;
    private String fullName;
    private String email;
    private String passwordHash;
    private int totalScore;
    private int itemsSorted;
    private String currentBadge;
    private Date createdDate;
    private Date lastLoginDate;

    // Constructors
    public User() {
        this.totalScore = 0;
        this.itemsSorted = 0;
        this.currentBadge = "Beginner";
        this.createdDate = new Date();
    }

    public User(String fullName, String email, String passwordHash) {
        this();
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
        updateBadge();
    }

    public int getItemsSorted() {
        return itemsSorted;
    }

    public void setItemsSorted(int itemsSorted) {
        this.itemsSorted = itemsSorted;
    }

    public String getCurrentBadge() {
        return currentBadge;
    }

    public void setCurrentBadge(String currentBadge) {
        this.currentBadge = currentBadge;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    // Badge calculation
    public void updateBadge() {
        if (totalScore >= 100) {
            this.currentBadge = "Recycling Hero";
        } else if (totalScore >= 50) {
            this.currentBadge = "Eco-Learner";
        } else {
            this.currentBadge = "Beginner";
        }
    }

    /**
     * Award points to user for sorting an item
     * @param points points to award (typically 10)
     */
    public void awardPoints(int points) {
        this.totalScore += points;
        this.itemsSorted++;
        updateBadge();
    }

    /**
     * Get emoji for current badge
     * @return emoji string
     */
    public String getBadgeEmoji() {
        switch (currentBadge) {
            case "Recycling Hero":
                return "🏆";
            case "Eco-Learner":
                return "♻️";
            case "Beginner":
            default:
                return "🌱";
        }
    }

    /**
     * Get progress to next badge (0-50 or 0-50 range)
     * @return current progress within badge tier
     */
    public int getProgressToNextBadge() {
        if (currentBadge.equals("Recycling Hero")) {
            return totalScore - 100; // Show how far beyond 100
        } else {
            return totalScore % 50;
        }
    }

    /**
     * Get points needed to reach next badge
     * @return points needed
     */
    public int getPointsNeededForNextBadge() {
        if (currentBadge.equals("Recycling Hero")) {
            return 0; // No next badge
        } else if (currentBadge.equals("Eco-Learner")) {
            return 100 - totalScore;
        } else {
            return 50 - totalScore;
        }
    }

    /**
     * Get next badge name
     * @return next badge name or empty if at max
     */
    public String getNextBadgeName() {
        if (currentBadge.equals("Beginner")) {
            return "Eco-Learner";
        } else if (currentBadge.equals("Eco-Learner")) {
            return "Recycling Hero";
        }
        return "";
    }

    /**
     * Check if badge is unlocked
     * @param badge badge name
     * @return true if unlocked
     */
    public boolean isBadgeUnlocked(String badge) {
        switch (badge) {
            case "Recycling Hero":
                return totalScore >= 100;
            case "Eco-Learner":
                return totalScore >= 50;
            case "Beginner":
                return totalScore >= 0;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", totalScore=" + totalScore +
                ", itemsSorted=" + itemsSorted +
                ", currentBadge='" + currentBadge + '\'' +
                '}';
    }
}
