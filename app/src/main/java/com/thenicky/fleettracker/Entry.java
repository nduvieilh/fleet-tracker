package com.thenicky.fleettracker;

import com.thenicky.fleettracker.entry.Vital;
import com.thenicky.fleettracker.entry.Temp;
import com.thenicky.fleettracker.entry.Position;


/**
 * Created by Nicholas on 3/31/2016.
 */
public class Entry {
    private Vital vital = null;
    private Temp temp = null;
    private Position position = null;


    /**
     * Constructors
      */
    public Entry() {

    }
    public Entry(Vital vital, Temp temp, Position position) {
        this.vital = vital;
        this.temp = temp;
        this.position = position;
    }

    public void setVital(Vital vital) {
        this.vital = vital;
    }
    public Vital getVital() {
        return this.vital;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }
    public Temp getTemp() {
        return this.temp;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    public Position getPosition() {
        return this.position;
    }
}
