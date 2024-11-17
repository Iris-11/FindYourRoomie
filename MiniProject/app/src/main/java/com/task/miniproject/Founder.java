package com.task.miniproject;

public class Founder {
    private final String name;
    private final String role;

    private final int imageResId;

    public Founder(String name, String role, int imageResId) {
        this.name = name;
        this.role = role;
        this.imageResId = imageResId;

    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public int getImageResId() {
        return imageResId;
    }

}
