package com.andyphan.chessopeningtrainer;

public class UserManager {
    private static User currentUser;
    public static User getCurrentUser() {
        return currentUser;
    }
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
