package com.thenicky.fleettracker.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.thenicky.fleettracker.R;
import com.thenicky.fleettracker.Vehicle;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class TrackerDBHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "tracker.sqlite3";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // DAO objects used to access vehicle table
    private Dao<Vehicle, Integer> vehicleDao = null;
    private RuntimeExceptionDao<Vehicle, Integer> vehicleRunTimeDao = null;

    // the DAO object we use to access the SimpleData table
    //private Dao<SimpleData, Integer> simpleDao = null;
    //private RuntimeExceptionDao<SimpleData, Integer> simpleRuntimeDao = null;

    public TrackerDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(TrackerDBHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Vehicle.class);
        } catch (SQLException e) {
            Log.e(TrackerDBHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

        // here we try inserting data in the on-create as a test
//        RuntimeExceptionDao<Vehicle, Integer> dao = getSimpleDataDao();
//        long millis = System.currentTimeMillis();
//        // create some entries in the onCreate
//        SimpleData simple = new SimpleData(millis);
//        Vehicle
//        dao.create(simple);
//        simple = new SimpleData(millis + 1);
//        dao.create(simple);
//        Log.i(DatabaseHelper.class.getName(), "created new entries in onCreate: " + millis);
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(TrackerDBHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Vehicle.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(TrackerDBHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
     * value.
     */
    /*public Dao<Vehicle, Integer> getDao() throws SQLException {
        if (vehicleDao == null) {
            vehicleDao = getDao(Vehicle.class);
        }
        return vehicleDao;
    }*/

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    /*public RuntimeExceptionDao<vehicleDao, Integer> getSimpleDataDao() {
        if (vehicleRunTimeDao == null) {
            vehicleRunTimeDao = getRuntimeExceptionDao(vehicleRunTimeDao.class);
        }
        return vehicleR;
    }*/

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        vehicleDao = null;
        vehicleRunTimeDao = null;
    }
}