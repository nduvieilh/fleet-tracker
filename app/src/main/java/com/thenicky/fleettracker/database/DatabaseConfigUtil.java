package com.thenicky.fleettracker.database;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import com.thenicky.fleettracker.Trip;
import com.thenicky.fleettracker.Vehicle;
import com.thenicky.fleettracker.entry.Position;
import com.thenicky.fleettracker.entry.Temp;
import com.thenicky.fleettracker.entry.Vital;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class DatabaseConfigUtil extends OrmLiteConfigUtil {
    private static final Class<?>[] classes = new Class[] {
        Vehicle.class, Trip.class, Vital.class, Temp.class, Position.class
    };

    public static void main(String[] args) throws SQLException, IOException {
        writeConfigFile(new File("res/raw/ormlite_config.txt"), classes);
    }
}