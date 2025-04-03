package com.example.carshowcaseapp.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.carshowcaseapp.data.Admin;

public class DatabaseHelper {
    private static final String FILE_KEY = "db";

    private static SharedPreferences sharedPref;
    private static SharedPreferences.Editor editor;

    private static ModelBank<Admin> adminBank;

    public void initialize(Context context) {
        sharedPref = context.getSharedPreferences(FILE_KEY, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        adminBank = new ModelBank<>(sharedPref, editor, "admins", Admin.class);
    }

    public static ModelBank<Admin> getAdminBank() {
        return adminBank;
    }
}
