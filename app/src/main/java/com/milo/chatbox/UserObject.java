package com.milo.chatbox;

public class UserObject {
    private String userId;
    private String profilePic;

    public UserObject() {
    }

    public UserObject(String userId) {
        this.userId = userId;
    }

    public UserObject(String userId, String profilePic) {
        this.userId = userId;
        this.profilePic = profilePic;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
