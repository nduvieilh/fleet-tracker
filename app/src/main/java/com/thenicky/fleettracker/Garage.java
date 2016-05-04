package com.thenicky.fleettracker;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.thenicky.fleettracker.database.TrackerDBHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Nicholas on 3/30/2016.
 */
public class Garage {
    private static Garage mInstance = null;
    public static List<Vehicle> vehicles = new ArrayList<>();

    public Garage() {

    }

    public void loadVehicles(List<Vehicle> _vehicles) {
        // Ensure that the vehicles List is empty
        unloadVehicles();

        vehicles = _vehicles;
    }

    public void unloadVehicles() {
        if(vehicles != null) {
            vehicles.clear();
        }
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
            for(Vehicle vehicle : vehicles) {
                if(vehicle.id == vehicleId) {
                    return vehicle;
                }
            }
            return null;
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

    public enum TripFilter {
        POI,
        TEMPS,
        RPMS,
        FUEL,
        GEARS
    }
}
