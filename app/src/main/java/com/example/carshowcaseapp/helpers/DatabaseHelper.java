package com.example.carshowcaseapp.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.carshowcaseapp.data.Admin;
import com.example.carshowcaseapp.data.Bookmark;
import com.example.carshowcaseapp.data.Vehicle;
import com.example.carshowcaseapp.services.AdminService;
import com.example.carshowcaseapp.services.VehicleService;

public class DatabaseHelper {
    private static final String FILE_KEY = "db";

    private static SharedPreferences sharedPref;
    private static SharedPreferences.Editor editor;

    private static ModelBank<Admin> adminBank;
    private static ModelBank<Vehicle> vehicleBank;
    private static ModelBank<Bookmark> bookmarkBank;

    public static void initialize(Context context) {
        sharedPref = context.getSharedPreferences(FILE_KEY, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        adminBank = new ModelBank<>(sharedPref, editor, "admins", Admin.class);
        vehicleBank = new ModelBank<>(sharedPref, editor, "vehicles", Vehicle.class);
        bookmarkBank = new ModelBank<>(sharedPref, editor, "bookmarks", Bookmark.class);

        if (adminBank.getAll().isEmpty()) {
            addInitialAdmins();
        }
        if (vehicleBank.getAll().isEmpty()) {
            addVehiclesData();
        }
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
        // Create and add dummy vehicle objects
        VehicleService.add(new Vehicle("X5", "BMW", "Leather seats, GPS navigation, Panoramic sunroof", "Luxury", "suv", 75000.00));
        VehicleService.add(new Vehicle("Camry", "Toyota", "Bluetooth, Backup camera, Lane assist", "Family", "sedan", 32999.99));
        VehicleService.add(new Vehicle("F8 Tributo", "Ferrari", "Twin-turbo V8, 710 hp, Carbon fiber interior", "Performance", "ferrari", 276550.00));
        VehicleService.add(new Vehicle("Odyssey", "Honda", "8 seats, Entertainment system, Power sliding doors", "Family", "minivan", 37890.00));
        VehicleService.add(new Vehicle("Model S", "Tesla", "Autopilot, Electric, 400 mile range", "Luxury", "sedan2", 94990.00));
        VehicleService.add(new Vehicle("Wrangler", "Jeep", "4x4, Removable top, Off-road package", "Performance", "suv2", 49995.00));
        VehicleService.add(new Vehicle("911", "Porsche", "Twin-turbo flat-six, Sport chrono package, Carbon ceramic brakes", "Performance", "coupe", 112550.00));
        VehicleService.add(new Vehicle("E-Class", "Mercedes-Benz", "Premium sound system, Heated seats, Driver assistance package", "Luxury", "sedan", 62000.00));
        VehicleService.add(new Vehicle("Pacifica", "Chrysler", "Stow n' Go seats, Hybrid option, Rear entertainment", "Family", "minivan", 41750.00));
        VehicleService.add(new Vehicle("Q7", "Audi", "Quattro AWD, Virtual cockpit, Air suspension", "Luxury", "suv", 67500.00));
        VehicleService.add(new Vehicle("Corvette", "Chevrolet", "6.2L V8, Mid-engine, Z51 performance package", "Performance", "coupe", 68000.00));
        VehicleService.add(new Vehicle("Sienna", "Toyota", "Hybrid powertrain, AWD available, Captain's chairs", "Family", "minivan", 35000.00));
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
