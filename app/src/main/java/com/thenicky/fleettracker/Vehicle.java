package com.thenicky.fleettracker;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.thenicky.fleettracker.Trip;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nicholas on 3/30/2016.
 */

@DatabaseTable(tableName = "vehciles")
public class Vehicle {
    @DatabaseField(id = true)
    public final Integer id;
    @DatabaseField(canBeNull = false)
    public final String name;
    @DatabaseField(unique = true)
    public final String vin;
    @DatabaseField
    public final String make;
    @DatabaseField
    public final String model;
    @DatabaseField
    public final String year;
    @DatabaseField
    public final String color;
    @DatabaseField
    public final String plate;
    @DatabaseField
    public final String state;
    @DatabaseField(canBeNull = false)
    private final Boolean active;
    @DatabaseField(canBeNull = false)
    public final Date created;
    @DatabaseField(canBeNull = false)
    public final Date modified;
    @DatabaseField()
    private final Date deleted;


    public ArrayList trips = new ArrayList<Trip>();

    /**
     * constructors
     */
    //public Vehicle() {
        // For ORMLite
    //}
    public Vehicle(Integer id, String name, String vin, String make, String model, String year, String color, String plate, String state, Date created, Date modified, Date deleted) {
        this.id = id;
        this.name = name;
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.plate = plate;
        this.state = state;
        this.active = true;
        this.created = created;
        this.modified = modified;
        this.deleted = null;
    }


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