package com.example.carshowcaseapp.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.carshowcaseapp.data.Admin;
import com.example.carshowcaseapp.data.Bookmark;
import com.example.carshowcaseapp.data.Vehicle;
import com.example.carshowcaseapp.services.AdminService;

public class DatabaseHelper {
    private static final String FILE_KEY = "db";

    private static SharedPreferences sharedPref;
    private static SharedPreferences.Editor editor;

    private static ModelBank<Admin> adminBank;
    private static ModelBank<Vehicle> vehicleBank;
    private static ModelBank<Bookmark> bookmarkBank;

    public void initialize(Context context) {
        sharedPref = context.getSharedPreferences(FILE_KEY, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        adminBank = new ModelBank<>(sharedPref, editor, "admins", Admin.class);
    }

    public static ModelBank<Admin> getAdminBank() {
        return adminBank;
    }

    public static ModelBank<Vehicle> getVehicleBank() {
        return vehicleBank;
    }

    public static ModelBank<Bookmark> getBookmarkBank() {
        return bookmarkBank;
    }

    public static void addVehiclesData() {

    }

    public static void addInitialAdmins() {
        AdminService.register(new Admin("DragunWF", "password123"));
        AdminService.register(new Admin("Lothern", "pass"));
    }

    public static void clear() {
        editor.clear();
        editor.apply();
    }
}
