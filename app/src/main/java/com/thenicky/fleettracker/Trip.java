package com.thenicky.fleettracker;

import android.location.Location;
import android.util.Log;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.thenicky.fleettracker.entry.Position;
import com.thenicky.fleettracker.entry.Temp;
import com.thenicky.fleettracker.entry.Vital;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Created by Nicholas on 3/31/2016.
 */
@DatabaseTable(tableName = "trips")
public class Trip {
    @DatabaseField(id = true)
    public Integer id;
    @DatabaseField(canBeNull = false, foreign = true)
    public Vehicle vehicle;
    //public Integer vehicle_id;
    @DatabaseField(dataType = DataType.DATE_LONG)
    public Date start_time;
    @DatabaseField(dataType = DataType.DATE_LONG)
    public Date end_time;
    @DatabaseField(canBeNull = false, defaultValue = "0")
    public Boolean synced;
    @DatabaseField(canBeNull = false, defaultValue = "0")
    public Integer view_count;
    @DatabaseField(canBeNull = false, defaultValue = "1")
    public Boolean active;
    @DatabaseField(canBeNull = false, dataType = DataType.DATE_LONG)
    public Date created;
    @DatabaseField(canBeNull = false, dataType = DataType.DATE_LONG)
    public Date modified;
    @DatabaseField(dataType = DataType.DATE_LONG)
    public Date deleted;

    public List<Entry> entries = new ArrayList<>();
    public Entry largest = new Entry(new Vital(), new Temp(), new Position());
    public Entry smallest = new Entry(new Vital(), new Temp(), new Position());

    public Float distance = 0F;
    public Float avgMPG = 0F;
    public long movingTime = 0;
    public long stillTime = 0;

    public long loadEntriesTime = 0;


    /**
     * constructors
     */
    public Trip() {
        // For ORMLite
    }

    public Trip(Integer id, Vehicle vehicle, Date start_time, Date end_time, Boolean synced, Integer view_count, Boolean active, Date created, Date modified, Date deleted) {
        this.id = id;
        this.vehicle = vehicle;
        this.start_time = start_time;
        this.end_time = end_time;
        this.synced = synced;
        this.view_count = view_count;
        this.active = active;
        this.created = created;
        this.modified = modified;
        this.deleted = deleted;
    }

    public void loadEntries(List<Entry> entries) {
        // Push all of the entries onto the entries List
        this.entries.addAll(entries);
    }

    public void loadEntries(List<Vital> vitals, List<Temp> temps, List<Position> positions, long resolution) {
        // Set start time for performance calcualtion
        long start = System.nanoTime();

        // Make sure entries is empty
        this.unloadEntries();

        // Cached last values
        Vital lastVital = new Vital(this.start_time);
        Temp lastTemp = new Temp(this.start_time);
        Position lastPosition = new Position(this.start_time);
        Entry lastEntry;
        Date lastEntryDate = this.start_time;
        long prevTime = this.start_time.getTime();
        long lengthTime = 0;

        try {
            // calculate the finalEntryDate
            Date finalVitalDate = (vitals != null && !vitals.isEmpty()) ? vitals.get(vitals.size() - 1).created : null;
            Date finalTempDate = (temps != null && !temps.isEmpty()) ? temps.get(temps.size() - 1).created : null;
            Date finalPositionDate = (positions != null && !positions.isEmpty()) ? vitals.get(positions.size() - 1).created : null;
            Date finalEntryDate = mostRecent(this.end_time, finalVitalDate, finalTempDate, finalPositionDate);

            // Initialize float array to store distance results
            float[] results = new float[5];

            do {
                // Increment by 1 second
                Date nextSecond = (Date) lastEntryDate.clone();
                nextSecond.setTime(lastEntryDate.getTime() + resolution);

                // Find the new lastEntryDate
                lastEntryDate = mostRecent(
                        leastRecent(
                            lastVital.created,
                            lastTemp.created,
                            lastPosition.created
                        ),
                        nextSecond);

                // Find most recent vital while removing others
                Iterator<Vital> v = vitals.iterator();
                while (v.hasNext()) {
                    lastVital = v.next();

                    // Break out of loop
                    if (lastVital.created == lastEntryDate || lastVital.created.after(lastEntryDate)) {
                        v.remove();
                        break;
                    }
                    v.remove();
                }

                // Find most recent temp while removing others
                Iterator<Temp> t = temps.iterator();
                while (t.hasNext()) {
                    lastTemp = t.next();

                    // Break out of loop
                    if (lastTemp.created == lastEntryDate || lastTemp.created.after(lastEntryDate)) {
                        t.remove();
                        break;
                    }
                    t.remove();
                }

                // Find most recent position
                Iterator<Position> p = positions.iterator();
                while (p.hasNext()) {
                    lastPosition = p.next();

                    // Break out of loop
                    if (lastPosition.created == lastEntryDate || lastPosition.created.after(lastEntryDate)) {
                        p.remove();
                        break;
                    }
                    p.remove();
                }



                // Create and add the new entry to the
                lastEntry = new Entry(lastVital, lastTemp, lastPosition);
                entries.add(lastEntry);

                // Add to the distance
                if(entries.size() > 1) {
                    Location.distanceBetween(
                        entries.get(entries.size()-2).getPosition().lat,
                        entries.get(entries.size()-2).getPosition().lon,
                        entries.get(entries.size()-1).getPosition().lat,
                        entries.get(entries.size()-1).getPosition().lon,
                        results
                    );

                    distance += results[0];
                }

                // Calculate averageMPG
                avgMPG += lastEntry.getVital().getMPG();

                // Calculate time
                lengthTime = lastEntry.getVital().created.getTime() - prevTime;
                if(lastEntry.getVital().speed_obd > 1) {
                    movingTime += lengthTime;
                } else {
                    stillTime += lengthTime;
                }
                prevTime = lastEntry.getVital().created.getTime();

            } while (lastEntryDate.before(finalEntryDate));
        } catch(NullPointerException e)
        {
            e.printStackTrace();
        }

        // Final calculation for avgMPG
        avgMPG = avgMPG / entries.size();

        // Calculate extremes based on for smallest and largest
        calculateExtremes(entries);

        loadEntriesTime = (System.nanoTime() - start) / 1000000;
    }

    public void unloadEntries() {
        if(entries != null) {
            entries.clear();
        }
    }

    private void addEntry(Entry entry) {
        this.entries.add(entry);
    }

    private void addEntry(Vital vital, Temp temp, Position position) {
        Entry entry = new Entry(vital, temp, position);
        this.addEntry(entry);
    }

    public void addEnteries(List<Entry> entries) {
        this.entries.addAll(entries);
    }

    public void removeEntery(Entry entry) {
        this.entries.remove(entry);
    }

    public void sync() {

    }

    public void removeAllEntries() {
        entries.clear();
    }

    private Date mostRecent(Date firstDate, Date ... dates) {
        Date largest = firstDate;
        for(Date date : dates) {
            if(date != null && largest.before(date)) {
                largest = date;
            }
        }
        return largest;
    }

    private Date leastRecent(Date firstDate, Date ... dates) {
        Date smallest = firstDate;
        for(Date date : dates) {
            if(date != null && smallest.after(date)) {
                smallest = date;
            }
        }
        return smallest;
    }

    private void calculateExtremes(List<Entry> entries) {
        List<Vital> vitals = new ArrayList<>();
        List<Temp> temps = new ArrayList<>();
        List<Position> positions = new ArrayList<>();

        for(Entry entry : entries) {
            vitals.add(entry.getVital());
            temps.add(entry.getTemp());
            positions.add(entry.getPosition());
        }

        if(entries.size() > 0) {
            largest.setVital(largest.getVital().largest(vitals));
            smallest.setVital(smallest.getVital().smallest(vitals));

            largest.setTemp(largest.getTemp().largest(temps));
            smallest.setTemp(smallest.getTemp().smallest(temps));

            largest.setPosition(largest.getPosition().largest(positions));
            smallest.setPosition(smallest.getPosition().smallest(positions));
        }
    }
}
