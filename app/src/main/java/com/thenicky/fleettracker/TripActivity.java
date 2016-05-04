package com.thenicky.fleettracker;

import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.thenicky.fleettracker.database.TrackerDBHelper;
import com.thenicky.fleettracker.entry.ExportFragment;
import com.thenicky.fleettracker.entry.Position;
import com.thenicky.fleettracker.entry.Temp;
import com.thenicky.fleettracker.entry.TripMapFragment;
import com.thenicky.fleettracker.entry.Vital;
import com.thenicky.fleettracker.settings.TripSettingsActivity;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TripActivity extends AppCompatActivity implements ExportFragment.OnFragmentInteractionListener  {
    private static final int RESULT_SETTINGS = 1;

    private TrackerDBHelper databaseHelper = null;
    private Garage garage = Garage.getInstance();
    private Vehicle vehicle = null;
    private Trip trip = null;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private TabLayout tabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                try {
                    Integer vehicleId = extras.getInt("vehicle_id");
                    Integer tripId = extras.getInt("trip_id");
                    vehicle = Garage.getVehicle(vehicleId);
                    trip = vehicle.getTrip(tripId);
                } catch(NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }

        if(trip != null) {
            // Set the title of the actionBar
            setTitle(trip.start_time.toString());

            // Initialize the entry List
            databaseAction("loadEntries");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unload entries to free up memory
        trip.unloadEntries();

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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trip, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent i = new Intent(this, TripSettingsActivity.class);
            startActivityForResult(i, RESULT_SETTINGS);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SETTINGS:
                databaseAction("loadEntries");

                mSectionsPagerAdapter.onRefresh(tabLayout.getSelectedTabPosition());
                break;

        }

    }

    /**
     * Handle fragment interaction for the export page
     * @param uri
     */
    public void onExportFragmentInteraction(Uri uri) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_graph, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final SparseArray<WeakReference<Fragment>> instantiatedFragments = new SparseArray<>();
        private ArrayList<String> mTabHeader;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);

            switch (position) {
                case 0:
                    return TripMapFragment.newInstance(trip, 30.3099202993725D, -89.8624973905491D, 10F);
                case 1:
                    return PlaceholderFragment.newInstance(position + 1);
                case 2:
                    return PlaceholderFragment.newInstance(position + 1);
                    //return SupportMapFragment.newInstance();
                    //return ExportFragment.newInstance("Export", "");
            }

            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "MAP";
                case 1:
                    return "GRAPHS";
                case 2:
                    return "EXPORT";
            }
            return null;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            final Fragment fragment = (Fragment) super.instantiateItem(container, position);
            instantiatedFragments.put(position, new WeakReference<>(fragment));
            return fragment;
        }

        @Override
        public void destroyItem(final ViewGroup container, final int position, final Object object) {
            instantiatedFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        @Nullable
        public Fragment getFragment(final int position) {
            final WeakReference<Fragment> wr = instantiatedFragments.get(position);
            if (wr != null) {
                return wr.get();
            } else {
                return null;
            }
        }

        // No param version of onRefresh that refreshes the current page
        public void onRefresh() {this.onRefresh(tabLayout.getSelectedTabPosition());}

        public void onRefresh(final int position) {
            Fragment fragment = this.getFragment(position);

            if(fragment != null) {
                switch(position) {
                    case 0:
                        ((TripMapFragment) fragment).onRefresh();
                        break;
                }
            }
        }
    }

    public Trip getTrip() {
        return trip;
    }

    private void databaseAction(String action) {

        switch(action.toLowerCase()) {
            case "loadentries":
                try {
                    int seconds = PreferenceManager.getDefaultSharedPreferences(this).getInt("TRIP_RESOLUTION", 10);
                    long resolution = (long) seconds * 1000;
                    List<Vital> vitals;
                    List<Temp> temps;
                    List<Position> positions;

                    Dao<Vital, Integer> vitalDao = getHelper().getVitalDao();
                    Dao<Temp, Integer> tempDao = getHelper().getTempDao();
                    Dao<Position, Integer> positionDao = getHelper().getPositionDao();

                    vitals = vitalDao.queryForEq("trip_id", trip.id);
                    temps = tempDao.queryForEq("trip_id", trip.id);
                    positions = positionDao.queryForEq("trip_id", trip.id);

                    trip.loadEntries(vitals, temps, positions, resolution);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            case "increaseviewcount":
                try {
                    Dao<Trip, Integer> tripDao = getHelper().getTripDao();
                    trip.view_count++;
                    tripDao.update(trip);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
