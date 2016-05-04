package com.thenicky.fleettracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.thenicky.fleettracker.database.TrackerDBHelper;

import java.sql.SQLException;
import java.util.List;

public class VehicleActivity extends AppCompatActivity implements TripFragment.OnListFragmentInteractionListener {
    private TrackerDBHelper databaseHelper = null;
    private Garage garage = Garage.getInstance();
    private Vehicle vehicle = null;
    private List<Trip> trips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                Integer vehicleId = extras.getInt("vehicle_id");
                vehicle = garage.getVehicle(vehicleId);
            }

            TripFragment tripFragment = new TripFragment();
            tripFragment.setArguments(extras);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.vehicleContainer, tripFragment)
                    .commit();
        }

        if(vehicle != null) {
            // Set the title of the actionBar
            setTitle(vehicle.name);

            // Initialize the vehicles List
            databaseAction("getTrips");
            vehicle.loadTrips(trips);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unload trips to free up memory
        vehicle.unloadTrips();

        // Release the database helper when Activity is destroyed
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    private TrackerDBHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, TrackerDBHelper.class);
        }
        return databaseHelper;
    }

    public void onListFragmentInteraction(Trip trip) {
        Intent intent = new Intent(this, TripActivity.class);
        intent.putExtra("vehicle_id", vehicle.id);
        intent.putExtra("trip_id", trip.id);
        startActivity(intent);
    }

    private void databaseAction(String action) {

        switch(action.toLowerCase()) {
            case "gettrips":
                try {
                    Dao<Trip, Integer> tripDao = getHelper().getTripDao();
                    trips = tripDao.queryForEq("vehicle_id", vehicle.id);
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
                break;
        }

    }
}
