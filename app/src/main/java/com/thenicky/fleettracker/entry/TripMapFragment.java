package com.thenicky.fleettracker.entry;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.thenicky.fleettracker.Entry;
import com.thenicky.fleettracker.Garage;
import com.thenicky.fleettracker.R;
import com.thenicky.fleettracker.Trip;
import com.thenicky.fleettracker.TripFragment;


public class TripMapFragment extends SupportMapFragment implements OnMapReadyCallback {
    private static Garage.TripFilter lastFilter = Garage.TripFilter.POI;

    private static final String LAT = "lat";
    private static final String LON = "lon";
    private static final String ZOOM = "zoom";


    private static GoogleMap mMap;
    private Double mLat;
    private Double mLon;
    private Float mZoom;
    private static Trip mTrip;

    //private List<MarkerOptions> mMarkers;

    // Required empty constructor
    public TripMapFragment() {
        super();
    }

    public static TripMapFragment newInstance(Trip trip, Double lat, Double lon, Float zoom) {
        TripMapFragment fragment = new TripMapFragment();
        Bundle args = new Bundle();
        args.putDouble(LAT, lat);
        args.putDouble(LON, lon);
        args.putFloat(ZOOM, zoom);
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
            mLat = getArguments().getDouble(LAT);
            mLon = getArguments().getDouble(LON);
            mZoom = getArguments().getFloat(ZOOM);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        // Get map
        this.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Set the camera to the default position
        if(mLat != null && mLon != null && mZoom != null) {
            LatLng cameraLatLng = new LatLng(mLat, mLon);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(cameraLatLng)
                    .zoom(mZoom)
                    .bearing(0)
                    .tilt(0)
                    .build();
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

        drawMap(Garage.TripFilter.POI, true);
    }

    // Allows redrawing of map from Activity
    public void onRefresh() {
        drawMap(lastFilter, true);
    }

    private Integer rand(Integer min, Integer max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }


    public Polyline drawMap(Garage.TripFilter filter, Boolean clear) {
        try {
            Polyline line;
            PolylineOptions lineOptions = new PolylineOptions();

            if(clear) {
                // Clear map
                mMap.clear();
            }

            int lower = Color.rgb(255, 0, 0);
            int upper = Color.rgb(0,158,0);
            float filterAmount = 0F;
            ArgbEvaluator evaluator = new ArgbEvaluator();
            int color = 0;
            double size = 0D;

            switch(filter) {
                case POI:
                    for(Entry entry : mTrip.entries){
                        filterAmount = (entry.getVital().engine_rpm - mTrip.smallest.getVital().engine_rpm) / (mTrip.largest.getVital().engine_rpm - mTrip.smallest.getVital().engine_rpm);
                        color = (Integer) evaluator.evaluate(filterAmount, upper, lower);
                        size = 100 * filterAmount * filterAmount;

                        mMap.addCircle(new CircleOptions()
                                        .center(new LatLng(entry.getPosition().lat, entry.getPosition().lon))
                                        .strokeColor(color)
                                        .fillColor(color)
                                        .radius(size)
                        );
                    }
                    break;
                case TEMPS:

                    break;
                case RPMS:
                    for(Entry entry : mTrip.entries){
                        filterAmount = (entry.getVital().engine_rpm - mTrip.smallest.getVital().engine_rpm) / (mTrip.largest.getVital().engine_rpm - mTrip.smallest.getVital().engine_rpm);
                        color = (Integer) evaluator.evaluate(filterAmount, upper, lower);
                        size = 100 * filterAmount * filterAmount;

                        mMap.addCircle(new CircleOptions()
                                        .center(new LatLng(entry.getPosition().lat, entry.getPosition().lon))
                                        .strokeColor(color)
                                        .fillColor(color)
                                        .radius(size)
                        );
                    }
                    break;
                case FUEL:
                    for(Entry entry : mTrip.entries){
                        filterAmount = (entry.getVital().fuel_rate - mTrip.smallest.getVital().fuel_rate) / (mTrip.largest.getVital().fuel_rate - mTrip.smallest.getVital().fuel_rate);
                        color = (Integer) evaluator.evaluate(filterAmount, upper, lower);
                        size = 100 * filterAmount * filterAmount;

                        mMap.addCircle(new CircleOptions()
                                        .center(new LatLng(entry.getPosition().lat, entry.getPosition().lon))
                                        .strokeColor(color)
                                        .fillColor(color)
                                        .radius(size)
                        );
                    }
                    break;
                case GEARS:

                    break;
            }
            lastFilter = filter;
            line = mMap.addPolyline(lineOptions);

            return line;
        } catch(NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }


}