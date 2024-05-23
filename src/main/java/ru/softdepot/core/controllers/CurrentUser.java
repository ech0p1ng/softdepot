package ru.softdepot.core.controllers;

import ru.softdepot.core.models.Administrator;
import ru.softdepot.core.models.Customer;
import ru.softdepot.core.models.Developer;
import ru.softdepot.core.models.User;

public class CurrentUser {
    private static User currentUser;

    private CurrentUser() {}

    public static void set(User user) {
        currentUser = user;
        System.out.printf("User type: %s\nName: %s\nE-mail: %s\nPassword: %s\n",
                currentUser.getUserType(), currentUser.getName(), currentUser.getEmail(), currentUser.getPassword());
    }

    public static User get() {
        return currentUser;
    }
}
