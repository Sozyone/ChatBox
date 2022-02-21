package com.milo.chatbox;

import java.util.Date;

/**
 * Message object class. Messages are the ones displayed in the chat.
 * They hold a username, a message, and a timestamp.
 * @author Michael Loubier
 * @version 2022-01
 */
public class MessageObject {
    private String user, message;
    private long time;

    /**
     * Default constructor is a must for Firebase functionalities.
     */
    public MessageObject(){}

    /**
     * Actual constructor used when creating new messages.
     * @param message written.
     * @param user name.
     */
    public MessageObject(String message, String user) {
        this.user = user;
        this.message = message;
        time = new Date().getTime();
    }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getText() { return message; }
    public void setText(String message) { this.message = message; }

    public long getTime() { return time; }
    public void setTime(long time) { this.time = time; }
}
