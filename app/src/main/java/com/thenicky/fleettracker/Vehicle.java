package com.thenicky.fleettracker;

import java.util.ArrayList;

/**
 * Created by Nicholas on 3/30/2016.
 */
public class Vehicle {

    public ArrayList trips = new ArrayList<Trip>();

    public void loadTrips() {

    }

    public void unloadTrips() {
        trips.clear();
    }

    public Trip newTrip() {

        Trip trip = new Trip();

        return trip;
    }

    public void viewTrip(Trip trip) {

    }

    public void removeTrip(Trip trip) {
        trips.remove(trip);
    }
}