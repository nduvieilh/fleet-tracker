package com.thenicky.fleettracker.entry;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thenicky.fleettracker.Entry;
import com.thenicky.fleettracker.Garage;
import com.thenicky.fleettracker.R;
import com.thenicky.fleettracker.Trip;

import java.util.Date;
import java.util.concurrent.TimeUnit;


public class TripInfoFragment extends Fragment {
    private static Garage.TripFilter lastFilter = Garage.TripFilter.POI;

    //private static final String LAT = "lat";
    //private static final String LON = "lon";
    //private static final String ZOOM = "zoom";

    //private Double mLat;
    //private Double mLon;
    //private Float mZoom;
    private static Trip mTrip;
    private static View mView;

    // Required empty constructor
    public TripInfoFragment() {
        super();
    }

    public static TripInfoFragment newInstance(Trip trip) {
        TripInfoFragment fragment = new TripInfoFragment();
        Bundle args = new Bundle();
        // args.putDouble(LAT, lat);
        // args.putDouble(LON, lon);
        // args.putFloat(ZOOM, zoom);
        fragment.setArguments(args);

        // Set mTrip if not set
        if(mTrip == null) {
            mTrip = trip;
        }

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup params
        if (getArguments() != null) {
            // mLat = getArguments().getDouble(LAT);
            // mLon = getArguments().getDouble(LON);
            // mZoom = getArguments().getFloat(ZOOM);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_trip_info, container, false);
        mView = view;

        // Initial draw Info
        drawQuickStats();
        drawDrivingRecord();
        return view;
    }

    // Allows refresh of Fragment from Activity
    public void onRefresh() {
        // Redraw Info
        drawQuickStats();
        drawDrivingRecord();
    }

    private void drawQuickStats() {
        if(mView != null) {
            TextView topSpeedTextView = (TextView) mView.findViewById(R.id.quick_stat_top_speed_value);
            TextView avgMpgTextView = (TextView) mView.findViewById(R.id.quick_stat_avg_mpg_value);
            TextView movingTimeTextView = (TextView) mView.findViewById(R.id.quick_stat_moving_time_value);
            TextView stillTimeTextView = (TextView) mView.findViewById(R.id.quick_stat_still_time_value);

            Float topSpeed = mTrip.largest.getVital().speed_obd;
            Float avgMpg = mTrip.avgMPG;
            long movingTime = mTrip.movingTime;
            long stillTime = mTrip.stillTime;

            topSpeedTextView.setText(String.format("%.2f", topSpeed));
            avgMpgTextView.setText(String.format("%.2f", avgMpg));
            movingTimeTextView.setText(formatTime(movingTime));
            stillTimeTextView.setText(formatTime(stillTime));
        }
    }

    private void drawDrivingRecord() {

    }

    private String formatTime(long millis) {
        String time = "-";
        if(millis > 36000000) {
            time = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1));
        } else {
            time = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
        }

        return time;
    }

}