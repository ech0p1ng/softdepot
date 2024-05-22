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
    }

    public static User get() {
        return currentUser;
    }
}
