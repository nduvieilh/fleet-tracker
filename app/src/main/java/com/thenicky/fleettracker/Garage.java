package com.thenicky.fleettracker;

import java.util.ArrayList;

/**
 * Created by Nicholas on 3/30/2016.
 */
public class Garage {

    private ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();



    public void loadVehicles() {

    }

    public Vehicle addVehicles(Vehicle vehicle) {

        vehicles.add(vehicle);

        return vehicle;
    }


    public void removeVehicles(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }
}
