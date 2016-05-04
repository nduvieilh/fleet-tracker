package com.thenicky.fleettracker.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.thenicky.fleettracker.R;

/**
 * Created by Nicholas on 5/1/2016.
 */
public class TripSettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Call super :
        super.onCreate(savedInstanceState);

        // Set the activity's fragment :
        getFragmentManager().beginTransaction().replace(android.R.id.content, new TripSettingsFragment()).commit();
    }


    public static class TripSettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        private SeekBarPreference _seekBarPref;

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.activity_trip_settings);


            // Get widgets :
            _seekBarPref = (SeekBarPreference) this.findPreference("TRIP_RESOLUTION");
            _seekBarPref.setMin(1);

            // Set listener :
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

            // Set seekbar summary :
            int seconds = PreferenceManager.getDefaultSharedPreferences(this.getActivity()).getInt("TRIP_RESOLUTION", 10);
            String unit = (seconds > 1 ? "Seconds" : "Second");
            _seekBarPref.setSummary(this.getString(R.string.pref_trip_resolution_summary).replace("$1", ""+seconds+" "+unit));
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            // Set seekbar summary :
            int seconds = PreferenceManager.getDefaultSharedPreferences(this.getActivity()).getInt("TRIP_RESOLUTION", 10);
            String unit = (seconds > 1 ? "Seconds" : "Second");
            _seekBarPref.setSummary(this.getString(R.string.pref_trip_resolution_summary).replace("$1", ""+seconds+" "+unit));
        }
    }
}
