package com.thenicky.fleettracker;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nicholas on 3/31/2016.
 */
@DatabaseTable(tableName = "trips")
public class Trip {
    @DatabaseField(id = true)
    public final Integer id;
    @DatabaseField(canBeNull = false, foreign = true)
    public final Integer trip_id;
    @DatabaseField
    public final Date start_time;
    @DatabaseField
    public final Date end_time;
    @DatabaseField
    public final Boolean synced;
    @DatabaseField(canBeNull = false)
    public final Integer view_count;
    @DatabaseField(canBeNull = false)
    public final Boolean active;
    @DatabaseField(canBeNull = false)
    public final Date created;
    @DatabaseField(canBeNull = false)
    public final Date modified;
    @DatabaseField()
    private final Date deleted;


    public ArrayList entries = new ArrayList<Entry>();


    /**
     * constructors
     */
    // public Trip() {
        // For ORMLite
    // }

    public Trip(Integer id, Integer trip_id, Date start_time, Date end_time, Boolean synced, Integer view_count, Boolean active, Date created, Date modified, Date deleted) {
        this.id = id;
        this.trip_id = trip_id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.synced = synced;
        this.view_count = view_count;
        this.active = active;
        this.created = created;
        this.modified = modified;
        this.deleted = deleted;
    }



    public void loadEntries() {

    }

    public void unloadEntries() {

    }

    public void sync() {

    }

    public void removeAllEntries() {
        entries.clear();
    }
}
