package com.example.usermanagementmodule;

public class UserProfile {
    private String pfp;
    private String username;
    private String phone;
    private String type;
    private int haircuts;

    public UserProfile(String pfp, String username, String phone, String type) {
        this.pfp = pfp;
        this.username = username;
        this.phone = phone;
        this.type = type;
        this.haircuts = 0;
    }

    public UserProfile() {
    }

    public int getHaircuts() {
        return haircuts;
    }

    public void setHaircuts(int haircuts) {
        this.haircuts = haircuts;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
