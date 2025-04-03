package com.example.carshowcaseapp.services;

import com.example.carshowcaseapp.data.Vehicle;
import com.example.carshowcaseapp.helpers.DatabaseHelper;
import com.example.carshowcaseapp.helpers.ModelBank;

public class VehicleService {
    public static void add(Vehicle vehicle) {
        ModelBank<Vehicle> bank = DatabaseHelper.getVehicleBank();
        bank.add(vehicle);
    }

    public static void edit(Vehicle vehicle) {
        ModelBank<Vehicle> bank = DatabaseHelper.getVehicleBank();
        bank.update(vehicle);
    }

    public static void delete(int id) {
        ModelBank<Vehicle> bank = DatabaseHelper.getVehicleBank();
        bank.delete(id);
    }
}
