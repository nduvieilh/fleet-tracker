package com.thenicky.fleettracker.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.thenicky.fleettracker.R;
import com.thenicky.fleettracker.Trip;
import com.thenicky.fleettracker.Vehicle;
import com.thenicky.fleettracker.entry.Position;
import com.thenicky.fleettracker.entry.Temp;
import com.thenicky.fleettracker.entry.Vital;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class TrackerDBHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "tracker.sqlite3";
    private static String DATABASE_PATH = "/data/data/com.thenicky.fleettracker/databases/";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // DAO objects used to access vehicles and trips tables
    private Dao<Vehicle, Integer> vehicleDao = null;
    private Dao<Trip, Integer> tripDao = null;
    private Dao<Vital, Integer> vitalDao = null;
    private Dao<Temp, Integer> tempDao = null;
    private Dao<Position, Integer> positionDao = null;

    public TrackerDBHelper(Context context) {
        super(context, DATABASE_PATH + DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
        File dbPath = context.getDatabasePath(DATABASE_NAME);
        DATABASE_PATH = dbPath.getPath().replace(DATABASE_NAME, "");

        boolean dbexist = checkdatabase();
        if(!dbexist) {
            try {
                File dir = new File(DATABASE_PATH);
                dir.mkdirs();
                InputStream myinput = context.getAssets().open(DATABASE_NAME);
                String outfilename = DATABASE_PATH + DATABASE_NAME;
                Log.i(TrackerDBHelper.class.getName(), "DB Path : " + outfilename);
                OutputStream myoutput = new FileOutputStream(outfilename);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myinput.read(buffer)) > 0) {
                    myoutput.write(buffer, 0, length);
                }
                myoutput.flush();
                myoutput.close();
                myinput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkdatabase() {
        boolean checkdb = false;

        String myPath = DATABASE_PATH + DATABASE_NAME;
        File dbfile = new File(myPath);
        checkdb = dbfile.exists();

        Log.i(TrackerDBHelper.class.getName(), "DB Exist : " + checkdb);

        return checkdb;
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(TrackerDBHelper.class.getName(), "onCreate");
            TableUtils.createTableIfNotExists(connectionSource, Vehicle.class);
            TableUtils.createTableIfNotExists(connectionSource, Trip.class);
            TableUtils.createTableIfNotExists(connectionSource, Vital.class);
            TableUtils.createTableIfNotExists(connectionSource, Temp.class);
            TableUtils.createTableIfNotExists(connectionSource, Position.class);
        } catch (SQLException e) {
            Log.e(TrackerDBHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(TrackerDBHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Vehicle.class, true);
            TableUtils.dropTable(connectionSource, Trip.class, true);
            TableUtils.dropTable(connectionSource, Vital.class, true);
            TableUtils.dropTable(connectionSource, Temp.class, true);
            TableUtils.dropTable(connectionSource, Position.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(TrackerDBHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    public Dao<Vehicle, Integer> getVehicleDao() throws SQLException {
        if (vehicleDao == null) {
            try {
                vehicleDao = getDao(Vehicle.class);
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return vehicleDao;
    }

    public Dao<Trip, Integer> getTripDao() throws SQLException {
        if (tripDao == null) {
            try {
                tripDao = getDao(Trip.class);
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return tripDao;
    }

    public Dao<Vital, Integer> getVitalDao() throws SQLException {
        if (vitalDao == null) {
            try {
                vitalDao = getDao(Vital.class);
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return vitalDao;
    }

    public Dao<Temp, Integer> getTempDao() throws SQLException {
        if (tempDao == null) {
            try {
                tempDao = getDao(Temp.class);
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return tempDao;
    }

    public Dao<Position, Integer> getPositionDao() throws SQLException {
        if (positionDao == null) {
            try {
                positionDao = getDao(Position.class);
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return positionDao;
    }

    @Override
    public void close() {
        super.close();
        vehicleDao = null;
        tripDao = null;
    }
}