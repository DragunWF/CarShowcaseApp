package com.example.carshowcaseapp.services;

import com.example.carshowcaseapp.data.Admin;
import com.example.carshowcaseapp.helpers.DatabaseHelper;
import com.example.carshowcaseapp.helpers.ModelBank;

public class AdminService {
    public static void register(Admin admin) {
        ModelBank<Admin> bank = DatabaseHelper.getAdminBank();
        bank.add(admin);
    }
}
