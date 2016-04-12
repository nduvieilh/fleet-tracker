package com.thenicky.fleettracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class VehicleActivity extends AppCompatActivity {
    Vehicle currentVehicle = null;
    Garage garage = Garage.getInstance();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                currentVehicle = (Vehicle) garage.getVehicle(extras.getInt("vehicle"));
            }

            VehicleFragment vehicleFragment = new VehicleFragment();
            vehicleFragment.setArguments(extras);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, vehicleFragment)
                    .commit();
        }
    }

    public void onListFragmentInteraction(Trip trip) {

    }

}
