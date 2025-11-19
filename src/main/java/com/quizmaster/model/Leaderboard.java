package com.quizmaster.model;

public class Leaderboard {

    private int rank;
    private String username;
    private double accuracy;

    public Leaderboard(int rank, String username, double accuracy) {
        this.rank = rank;
        this.username = username;
        this.accuracy = accuracy;
    }

    // Getters and Setters
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }
}