package com.thenicky.fleettracker.entry;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.thenicky.fleettracker.Trip;

import java.util.Date;
import java.util.List;

/**
 * Created by Nicholas on 4/23/2016.
 */
@DatabaseTable(tableName = "vitals")
public class Vital {
    @DatabaseField(id = true)
    public Integer id;
    @DatabaseField(canBeNull = false, foreign = true)
    public Trip trip;
    @DatabaseField
    public Float engine_rpm;
    @DatabaseField
    public Float speed_obd;
    @DatabaseField
    public Float air_flow_rate;
    @DatabaseField
    public Integer throttle_position;
    @DatabaseField
    public Float load;
    @DatabaseField
    public Float torque;
    @DatabaseField
    public Float boost_pressure;
    @DatabaseField
    public Float turbo_rpm;
    @DatabaseField
    public Float horsepower;
    @DatabaseField
    public Float fuel_rate;
    @DatabaseField(canBeNull = false, dataType = DataType.DATE_LONG)
    public Date created;

    /**
     * constructors
     */
    public Vital() {
        // For ORMLite
    }

    public Vital(Integer id, Trip trip, Float engine_rpm, Float speed_obd, Float air_flow_rate, Integer throttle_position, Float load, Float torque, Float boost_pressure, Float turbo_rpm, Float horsepower, Float fuel_rate) {
        this.id = id;
        this.trip = trip;
        this.engine_rpm = engine_rpm;
        this.speed_obd = speed_obd;
        this.air_flow_rate = air_flow_rate;
        this.throttle_position = throttle_position;
        this.load = load;
        this.torque = torque;
        this.boost_pressure = boost_pressure;
        this.turbo_rpm = turbo_rpm;
        this.horsepower = horsepower;
        this.fuel_rate = fuel_rate;
        this.created = new Date();
    }

    public Vital(Date created) {
        this.id = 0;
        this.trip = null;
        this.engine_rpm = null;
        this.speed_obd = null;
        this.air_flow_rate = null;
        this.throttle_position = null;
        this.load = null;
        this.torque = null;
        this.boost_pressure = null;
        this.turbo_rpm = null;
        this.horsepower = null;
        this.fuel_rate = null;
        this.created = created;
    }

    @Override
    public boolean equals(Object ob) {
        if (ob == null) return false;
        if (ob.getClass() != getClass()) return false;
        Vital other = (Vital)ob;

        // Check that entries are not similar
        // Ignores certain values
        if(engine_rpm != null && !engine_rpm.equals(other.engine_rpm)) return false;
        if(speed_obd != null && !speed_obd.equals(other.speed_obd)) return false;
        if(air_flow_rate != null && !air_flow_rate.equals(other.air_flow_rate)) return false;
        if(throttle_position != null && !throttle_position.equals(other.throttle_position)) return false;
        if(load != null && !load.equals(other.load)) return false;
        if(torque != null && !torque.equals(other.torque)) return false;
        if(boost_pressure != null && !boost_pressure.equals(other.boost_pressure)) return false;
        if(turbo_rpm != null && !turbo_rpm.equals(other.turbo_rpm)) return false;
        if(horsepower != null && !horsepower.equals(other.horsepower)) return false;
        if(fuel_rate != null && !fuel_rate.equals(other.fuel_rate)) return false;
        return true;
    }

    public Vital largest(List<Vital> vitals) {
        try {
            for(Vital vital : vitals) {
                if(this.engine_rpm == null || (vital.engine_rpm != null && vital.engine_rpm > this.engine_rpm)) this.engine_rpm = vital.engine_rpm;
                if(this.speed_obd == null || (vital.speed_obd != null && vital.speed_obd > this.speed_obd)) this.speed_obd = vital.speed_obd;
                if(this.air_flow_rate == null || (vital.air_flow_rate != null && vital.air_flow_rate > this.air_flow_rate)) this.air_flow_rate = vital.air_flow_rate;
                if(this.throttle_position == null || (vital.throttle_position != null && vital.throttle_position > this.throttle_position)) this.throttle_position = vital.throttle_position;
                if(this.load == null || (vital.load != null && vital.load > this.load)) this.load = vital.load;
                if(this.torque == null || (vital.torque != null && vital.torque > this.torque)) this.torque = vital.torque;
                if(this.boost_pressure == null || (vital.boost_pressure != null && vital.boost_pressure > this.boost_pressure)) this.boost_pressure = vital.boost_pressure;
                if(this.turbo_rpm == null || (vital.turbo_rpm != null && vital.turbo_rpm > this.turbo_rpm)) this.turbo_rpm = vital.turbo_rpm;
                if(this.horsepower == null || (vital.horsepower != null && vital.horsepower > this.horsepower)) this.horsepower = vital.horsepower;
                if(this.fuel_rate == null || (vital.fuel_rate != null && vital.fuel_rate > this.fuel_rate)) this.fuel_rate = vital.fuel_rate;
            }
            return this;
        } catch(NullPointerException e) {
            e.printStackTrace();
            return this;
        }
    }

    public Vital smallest(List<Vital> vitals) {
        try {
            for(Vital vital : vitals) {
                if(this.engine_rpm == null || (vital.engine_rpm != null && vital.engine_rpm < this.engine_rpm)) this.engine_rpm = vital.engine_rpm;
                if(this.speed_obd == null || (vital.speed_obd != null && vital.speed_obd < this.speed_obd)) this.speed_obd = vital.speed_obd;
                if(this.air_flow_rate == null || (vital.air_flow_rate != null && vital.air_flow_rate < this.air_flow_rate)) this.air_flow_rate = vital.air_flow_rate;
                if(this.throttle_position == null || (vital.throttle_position != null && vital.throttle_position < this.throttle_position)) this.throttle_position = vital.throttle_position;
                if(this.load == null || (vital.load != null && vital.load < this.load)) this.load = vital.load;
                if(this.torque == null || (vital.torque != null && vital.torque < this.torque)) this.torque = vital.torque;
                if(this.boost_pressure == null || (vital.boost_pressure != null && vital.boost_pressure < this.boost_pressure)) this.boost_pressure = vital.boost_pressure;
                if(this.turbo_rpm == null || (vital.turbo_rpm != null && vital.turbo_rpm < this.turbo_rpm)) this.turbo_rpm = vital.turbo_rpm;
                if(this.horsepower == null || (vital.horsepower != null && vital.horsepower < this.horsepower)) this.horsepower = vital.horsepower;
                if(this.fuel_rate == null || (vital.fuel_rate != null && vital.fuel_rate < this.fuel_rate)) this.fuel_rate = vital.fuel_rate;
            }
            return this;
        } catch(NullPointerException e) {
            e.printStackTrace();
            return this;
        }
    }

    public Float getMPG() {
        return speed_obd / (1 / (fuel_rate * 0.264172F));
    }
}