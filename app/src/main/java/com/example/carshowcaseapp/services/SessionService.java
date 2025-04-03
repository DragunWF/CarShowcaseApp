package com.example.carshowcaseapp.services;

import com.example.carshowcaseapp.data.Admin;
import com.example.carshowcaseapp.helpers.DatabaseHelper;

import java.util.List;

public class SessionService {
    private static int currentAdminId = -1; // -1 means no admin

    public static int getCurrentAdminId() {
        return currentAdminId;
    }

    public static boolean login(String username, String password) {
        List<Admin> admins = DatabaseHelper.getAdminBank().getAll();
        for (Admin admin : admins) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                currentAdminId = admin.getId();
                return true;
            }
        }
        return false;
    }

    public static void logout() {
        currentAdminId = -1;
    }
}
