package com.example.usermanagementmodule;

public class UserProfile {
    private String pfp;
    private String username;
    private String phone;

    public UserProfile(String pfp, String username, String phone) {
        this.pfp = pfp;
        this.username = username;
        this.phone = phone;
    }

    public UserProfile() {
    }

    public String getPfp() {
        return pfp;
    }

    public void setPfp(String pfp) {
        this.pfp = pfp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "pfp='" + pfp + '\'' +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
