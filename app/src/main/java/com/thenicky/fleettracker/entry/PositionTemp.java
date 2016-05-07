package com.thenicky.fleettracker.entry;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.thenicky.fleettracker.Trip;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Nicholas on 4/23/2016.
 */
@DatabaseTable(tableName = "positions")
public class Position {
    @DatabaseField(id = true)
    public Integer id;
    @DatabaseField(canBeNull = false, foreign = true)
    public Trip trip;
    @DatabaseField
    public Double lat;
    @DatabaseField
    public Double lon;
    @DatabaseField
    public Float bearing;
    @DatabaseField
    public Double altitude;
    @DatabaseField
    public Float accuracy;
    @DatabaseField
    public Float speed;
    @DatabaseField
    public Integer satellites;
    @DatabaseField
    public Float accel_x;
    @DatabaseField
    public Float accel_y;
    @DatabaseField
    public Float accel_z;
    @DatabaseField
    public Float gyro_x;
    @DatabaseField
    public Float gyro_y;
    @DatabaseField
    public Float gyro_z;
    @DatabaseField(canBeNull = false, dataType = DataType.DATE_LONG)
    public Date created;

    /**
     * constructors
     */
    public Position() {
        // For ORMLite
    }

    public Position(Integer id, Trip trip, Double lat, Double lon, Float bearing, Double altitude, Float accuracy, Float speed, Integer satellites, Float accel_x, Float accel_y, Float accel_z, Float gyro_x, Float gyro_y, Float gyro_z) {
        this.id = id;
        this.trip = trip;
        this.lat = lat;
        this.lon = lon;
        this.bearing = bearing;
        this.altitude = altitude;
        this.accuracy = accuracy;
        this.speed = speed;
        this.satellites = satellites;
        this.accel_x = accel_x;
        this.accel_y = accel_y;
        this.accel_z = accel_z;
        this.gyro_x = gyro_x;
        this.gyro_y = gyro_y;
        this.gyro_z = gyro_z;
        this.created = new Date();
    }

    public Position(Date created) {
        this.id = 0;
        this.trip = null;
        this.lat = null;
        this.lon = null;
        this.bearing = null;
        this.altitude = null;
        this.accuracy = null;
        this.speed = null;
        this.satellites = null;
        this.accel_x = null;
        this.accel_y = null;
        this.accel_z = null;
        this.gyro_x = null;
        this.gyro_y = null;
        this.gyro_z = null;
        this.created = created;
    }

    // Average constructor
    public Position(ArrayList<Position> positions) {
        try {
            Float avgBearing = 0F;
            Double avgAltitude = 0D;
            Float avgAccuracy = 0F;
            Float avgSpeed = 0F;
            Integer avgSatellites = 0;
            Float avgAccel_x = 0F;
            Float avgAccel_y = 0F;
            Float avgAccel_z = 0F;
            Float avgGyro_x = 0F;
            Float avgGyro_y = 0F;
            Float avgGyro_z = 0F;
            Integer size = positions.size();
            Position midPosition = positions.get(size / 2);

            this.id = midPosition.id;
            this.trip = midPosition.trip;
            this.lat = midPosition.lat;
            this.lon = midPosition.lon;

            for(Position position : positions) {
                avgBearing += position.bearing != null ? position.bearing : avgBearing;
                avgAltitude += position.altitude != null ? position.altitude : avgAltitude;
                avgAccuracy += position.accuracy != null ? position.accuracy : avgAccuracy;
                avgSpeed += position.speed != null ? position.speed : avgSpeed;
                avgSatellites += position.satellites != null ? position.satellites : avgSatellites;
                avgAccel_x += position.accel_x != null ? position.accel_x : avgAccel_x;
                avgAccel_y += position.accel_y != null ? position.accel_y : avgAccel_y;
                avgAccel_z += position.accel_z != null ? position.accel_z : avgAccel_z;
                avgGyro_x += position.gyro_x != null ? position.gyro_x : avgGyro_x;
                avgGyro_y += position.gyro_y != null ? position.gyro_y : avgGyro_y;
                avgGyro_z += position.gyro_z != null ? position.gyro_z : avgGyro_z;
            }

            this.bearing = avgBearing / size;
            this.altitude = avgAltitude / size;
            this.accuracy = avgAccuracy / size;
            this.speed = avgSpeed / size;
            this.satellites = avgSatellites / size;
            this.accel_x = avgAccel_x / size;
            this.accel_y = avgAccel_y / size;
            this.accel_z = avgAccel_z / size;
            this.gyro_x = avgGyro_x / size;
            this.gyro_y = avgGyro_y / size;
            this.gyro_z = avgGyro_z / size;

            this.created = midPosition.created;
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object ob) {
        if (ob == null) return false;
        if (ob.getClass() != getClass()) return false;
        Position other = (Position)ob;

        // Check that entries are not similar
        // Ignores certain values
        if(!trip.equals(other.trip)) return false;
        if(!lat.equals(other.lat)) return false;
        if(!lon.equals(other.lon)) return false;
        if(!bearing.equals(other.bearing)) return false;
        if(!altitude.equals(other.altitude)) return false;
        if(!accuracy.equals(other.accuracy)) return false;
        if(!speed.equals(other.speed)) return false;
        if(!satellites.equals(other.satellites)) return false;
        if(!accel_x.equals(other.accel_x)) return false;
        if(!accel_y.equals(other.accel_y)) return false;
        if(!accel_z.equals(other.accel_z)) return false;
        if(!gyro_x.equals(other.gyro_x)) return false;
        if(!gyro_y.equals(other.gyro_y)) return false;
        if(!gyro_z.equals(other.gyro_z)) return false;
        return true;
    }

    public Position largest(List<Position> positions) {
        try {
            for(Position position : positions) {
                if(this.lat == null || (position.lat != null && position.lat > this.lat)) this.lat = position.lat;
                if(this.lon == null || (position.lon != null && position.lon > this.lon)) this.lon = position.lon;
                if(this.bearing == null || (position.bearing != null && position.bearing > this.bearing)) this.bearing = position.bearing;
                if(this.altitude == null || (position.altitude != null && position.altitude > this.altitude)) this.altitude = position.altitude;
                if(this.accuracy == null || (position.accuracy != null && position.accuracy > this.accuracy)) this.accuracy = position.accuracy;
                if(this.speed == null || (position.speed != null && position.speed > this.speed)) this.speed = position.speed;
                if(this.satellites == null || (position.satellites != null && position.satellites > this.satellites)) this.satellites = position.satellites;
                if(this.accel_x == null || (position.accel_x != null && position.accel_x > this.accel_x)) this.accel_x = position.accel_x;
                if(this.accel_y == null || (position.accel_y != null && position.accel_y > this.accel_y)) this.accel_y = position.accel_y;
                if(this.accel_z == null || (position.accel_z != null && position.accel_z > this.accel_z)) this.accel_z = position.accel_z;
                if(this.gyro_x == null || (position.gyro_x != null && position.gyro_x > this.gyro_x)) this.gyro_x = position.gyro_x;
                if(this.gyro_y == null || (position.gyro_y != null && position.gyro_y > this.gyro_y)) this.gyro_y = position.gyro_y;
                if(this.gyro_z == null || (position.gyro_z != null && position.gyro_z > this.gyro_z)) this.gyro_z = position.gyro_z;
            }
            return this;
        } catch(NullPointerException e) {
            e.printStackTrace();
            return this;
        }
    }

    public Position smallest(List<Position> positions) {
        try {
            for(Position position : positions) {
                if(this.lat == null || (position.lat != null && position.lat < this.lat)) this.lat = position.lat;
                if(this.lon == null || (position.lon != null && position.lon < this.lon)) this.lon = position.lon;
                if(this.bearing == null || (position.bearing != null && position.bearing < this.bearing)) this.bearing = position.bearing;
                if(this.altitude == null || (position.altitude != null && position.altitude < this.altitude)) this.altitude = position.altitude;
                if(this.accuracy == null || (position.accuracy != null && position.accuracy < this.accuracy)) this.accuracy = position.accuracy;
                if(this.speed == null || (position.speed != null && position.speed < this.speed)) this.speed = position.speed;
                if(this.satellites == null || (position.satellites != null && position.satellites < this.satellites)) this.satellites = position.satellites;
                if(this.accel_x == null || (position.accel_x != null && position.accel_x < this.accel_x)) this.accel_x = position.accel_x;
                if(this.accel_y == null || (position.accel_y != null && position.accel_y < this.accel_y)) this.accel_y = position.accel_y;
                if(this.accel_z == null || (position.accel_z != null && position.accel_z < this.accel_z)) this.accel_z = position.accel_z;
                if(this.gyro_x == null || (position.gyro_x != null && position.gyro_x < this.gyro_x)) this.gyro_x = position.gyro_x;
                if(this.gyro_y == null || (position.gyro_y != null && position.gyro_y < this.gyro_y)) this.gyro_y = position.gyro_y;
                if(this.gyro_z == null || (position.gyro_z != null && position.gyro_z < this.gyro_z)) this.gyro_z = position.gyro_z;
            }
            return this;
        } catch(NullPointerException e) {
            e.printStackTrace();
            return this;
        }
    }
}