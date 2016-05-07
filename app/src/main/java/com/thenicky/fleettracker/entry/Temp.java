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
@DatabaseTable(tableName = "temps")
public class Temp {
    @DatabaseField(id = true)
    public Integer id;
    @DatabaseField(canBeNull = false, foreign = true)
    public Trip trip;
    @DatabaseField
    public Float engine_coolant;
    @DatabaseField
    public Float intake_air;
    @DatabaseField
    public Float catalyst;
    @DatabaseField
    public Float ambient_air;
    @DatabaseField
    public Float engine_oil;
    @DatabaseField
    public Float turbocharger;
    @DatabaseField(canBeNull = false, dataType = DataType.DATE_LONG)
    public Date created;

    /**
     * constructors
     */
    public Temp() {
        // For ORMLite
    }

    public Temp(Integer id, Trip trip, Float engine_coolant, Float intake_air, Float catalyst, Float ambient_air, Float engine_oil, Float turbocharger) {
        this.id = id;
        this.trip = trip;
        this.engine_coolant = engine_coolant;
        this.intake_air = intake_air;
        this.catalyst = catalyst;
        this.ambient_air = ambient_air;
        this.engine_oil = engine_oil;
        this.turbocharger = turbocharger;
        this.created = new Date();
    }

    public Temp(Date created) {
        this.id = 0;
        this.trip = null;
        this.engine_coolant = null;;
        this.intake_air = null;
        this.catalyst = null;
        this.ambient_air = null;
        this.engine_oil = null;
        this.turbocharger = null;
        this.created = created;
    }

    @Override
    public boolean equals(Object ob) {
        if (ob == null) return false;
        if (ob.getClass() != getClass()) return false;
        Temp other = (Temp)ob;

        // Check that entries are not similar
        // Ignores certain values
        if(!engine_coolant.equals(other.engine_coolant)) return false;
        if(!intake_air.equals(other.intake_air)) return false;
        if(!catalyst.equals(other.catalyst)) return false;
        if(!ambient_air.equals(other.ambient_air)) return false;
        if(!engine_oil.equals(other.engine_oil)) return false;
        if(!turbocharger.equals(other.turbocharger)) return false;
        return true;
    }

    public Temp largest(List<Temp> temps) {
        try {
            for(Temp temp : temps) {
                if(this.engine_coolant == null || (temp.engine_coolant != null && temp.engine_coolant > this.engine_coolant)) this.engine_coolant = temp.engine_coolant;
                if(this.intake_air == null || (temp.intake_air != null && temp.intake_air > this.intake_air)) this.intake_air = temp.intake_air;
                if(this.catalyst == null || (temp.catalyst != null && temp.catalyst > this.catalyst)) this.catalyst = temp.catalyst;
                if(this.ambient_air == null || (temp.ambient_air != null && temp.ambient_air > this.ambient_air)) this.ambient_air = temp.ambient_air;
                if(this.engine_oil == null || (temp.engine_oil != null && temp.engine_oil > this.engine_oil)) this.engine_oil = temp.engine_oil;
                if(this.turbocharger == null || (temp.turbocharger != null && temp.turbocharger > this.turbocharger)) this.turbocharger = temp.turbocharger;
            }
            return this;
        } catch(NullPointerException e) {
            e.printStackTrace();
            return this;
        }
    }

    public Temp smallest(List<Temp> temps) {
        try {
            for(Temp temp : temps) {
                if(this.engine_coolant == null || (temp.engine_coolant != null && temp.engine_coolant < this.engine_coolant)) this.engine_coolant = temp.engine_coolant;
                if(this.intake_air == null || (temp.intake_air != null && temp.intake_air < this.intake_air)) this.intake_air = temp.intake_air;
                if(this.catalyst == null || (temp.catalyst != null && temp.catalyst < this.catalyst)) this.catalyst = temp.catalyst;
                if(this.ambient_air == null || (temp.ambient_air != null && temp.ambient_air < this.ambient_air)) this.ambient_air = temp.ambient_air;
                if(this.engine_oil == null || (temp.engine_oil != null && temp.engine_oil < this.engine_oil)) this.engine_oil = temp.engine_oil;
                if(this.turbocharger == null || (temp.turbocharger != null && temp.turbocharger < this.turbocharger)) this.turbocharger = temp.turbocharger;
            }
            return this;
        } catch(NullPointerException e) {
            e.printStackTrace();
            return this;
        }
    }
}