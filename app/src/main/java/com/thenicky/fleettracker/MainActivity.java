package com.thenicky.fleettracker;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.thenicky.fleettracker.VehicleFragment;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.thenicky.fleettracker.database.TrackerDBHelper;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements VehicleFragment.OnListFragmentInteractionListener {
    private TrackerDBHelper databaseHelper = null;
    private Garage garage = Garage.getInstance();
    private List<Vehicle> vehicles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainContainer, new VehicleFragment())
                    .commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVehicle(view);
            }
        });

        // Initialize the vehicles List
        databaseAction("getVehicles");
        garage.loadVehicles(vehicles);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Release the database helper when Activity is destroyed
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }


    public void onListFragmentInteraction(Vehicle vehicle) {
        Intent intent = new Intent(this, VehicleActivity.class);
        intent.putExtra("vehicle_id", vehicle.id);
        startActivity(intent);
    }

    private TrackerDBHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, TrackerDBHelper.class);
        }
        return databaseHelper;
    }

    public void addVehicle(View view) {
        // TODO: Make this link to addVehicle activity
        Intent intent = new Intent(this, TripActivity.class);
        startActivity(intent);
    }

    private void databaseAction(String action) {

        switch(action.toLowerCase()) {
            case "getvehicles":
                try {
                    Dao<Vehicle, Integer> vehicleDao = getHelper().getVehicleDao();
                    vehicles = vehicleDao.queryForAll();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
                break;
        }

    }
}
