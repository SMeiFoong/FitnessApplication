package com.example.fitnessapplication.Utills;

public class Users {
    private String username, university, profileImage ;

    public Users() {
    }

    public Users(String username, String university, String profileImage) {
        this.username = username;
        this.university = university;
        this.profileImage = profileImage;
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
