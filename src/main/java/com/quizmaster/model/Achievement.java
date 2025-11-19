package com.quizmaster.model;

public class Achievement {

    private int id;
    private String name;
    private String description;
    private String unlockCondition;

    public Achievement(int id, String name, String description, String unlockCondition) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unlockCondition = unlockCondition;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnlockCondition() {
        return unlockCondition;
    }

    public void setUnlockCondition(String unlockCondition) {
        this.unlockCondition = unlockCondition;
    }
}