package com.example.fitnessapplication.Utills;

public class Friends {
    private  String profileImageUrl, username, university;

    public Friends(String profileImageUrl, String username, String university) {
        this.profileImageUrl = profileImageUrl;
        this.username = username;
        this.university = university;
    }

    public Friends() {
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
}
