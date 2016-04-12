package com.thenicky.fleettracker;

import android.app.Application;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nicholas on 3/30/2016.
 */
public class Garage {
    private static Garage mInstance = null;

    public static ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();

    public Garage() {
        loadVehicles();
    }

    public void loadVehicles() {
        Vehicle vehicle = new Vehicle(1, "Nicky's Car", "123456ABCDEF", "Chrysler", "Sebring", "2004", "White", "ABC 123", "MS", true, new Date(), new Date(), null);
        addVehicles(vehicle);
    }

    public Vehicle addVehicles(Vehicle vehicle) {

        vehicles.add(vehicle);

        return vehicle;
    }

    public void removeVehicles(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }

    public static Vehicle getVehicle(Integer vehicleId) {
        try {
            return vehicles.get(vehicleId);
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            return null;
        }

    }

    public static synchronized Garage getInstance() {
        if(mInstance == null) {
            mInstance = new Garage();
        }
        return mInstance;
    }
}
