package com.thenicky.fleettracker;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;
import java.util.List;

/**
 * Created by Nicholas on 3/30/2016.
 */
@DatabaseTable(tableName = "vehicles")
public class Vehicle {
    @DatabaseField(id = true)
    public Integer id;
    @DatabaseField(canBeNull = false)
    public String name;
    @DatabaseField(unique = true)
    public String vin;
    @DatabaseField
    public String make;
    @DatabaseField
    public String model;
    @DatabaseField
    public String year;
    @DatabaseField
    public String color;
    @DatabaseField
    public String plate;
    @DatabaseField
    public String state;
    @DatabaseField(canBeNull = false, defaultValue = "1")
    public Boolean active;
    @DatabaseField(canBeNull = false, dataType = DataType.DATE_LONG)
    public Date created;
    @DatabaseField(canBeNull = false, dataType = DataType.DATE_LONG)
    public Date modified;
    @DatabaseField(dataType = DataType.DATE_LONG)
    public Date deleted;

    public List<Trip> trips;

    /**
     * constructors
     */
    public Vehicle() {
        // For ORMLite
    }

    public Vehicle(Integer id, String name, String vin, String make, String model, String year, String color, String plate, String state, Boolean active, Date created, Date modified, Date deleted) {
        this.id = id;
        this.name = name;
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.plate = plate;
        this.state = state;
        this.active = active;
        this.created = created;
        this.modified = modified;
        this.deleted = deleted;
    }


    public void loadTrips(List<Trip> _trips) {
        // Ensure that the trips List is empty
        unloadTrips();

       trips = _trips;
    }

    public void unloadTrips() {
        if(trips != null) {
            trips.clear();
        }
    }

    public Trip getTrip(Integer tripId) {
        for(Trip trip : trips) {
            if(trip.id == tripId) {
                return trip;
            }
        }
        return null;
    }

    public void removeTrip(Trip trip) {
        trips.remove(trip);
    }
}