package org.hexpresso.soulevspy.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.hexpresso.soulevspy.R;

/**
 * Created by pemessier on 2015-09-28.
 */
public class ClientSharedPreferences {

    // SharedPreferences name
    public static final String SHARED_PREFERENCES_NAME = "SoulEvSpySharedPreferences";

    // preferences.xml keys
    private final String PREF_UNITS_DISTANCE;
    private final String PREF_UNITS_ENERGY_CONSUMPTION;
    private final String PREF_UNITS_TEMPERATURE;
    private final String PREF_BLUETOOTH_DEVICE;

    // Default values
    public final String DEFAULT_UNITS_DISTANCE;
    public final String DEFAULT_UNITS_ENERGY_CONSUMPTION;
    public final String DEFAULT_UNITS_TEMPERATURE;
    public final String DEFAULT_BLUETOOTH_DEVICE;

    final private Context mContext;
    final private SharedPreferences sharedPreferences;

    public ClientSharedPreferences(Context context) {
        // Load preference keys from XML
        PREF_UNITS_DISTANCE = context.getString(R.string.key_list_units_distance);
        PREF_UNITS_ENERGY_CONSUMPTION = context.getString(R.string.key_list_units_energy_consumption);
        PREF_UNITS_TEMPERATURE = context.getString(R.string.key_list_units_temperature);
        PREF_BLUETOOTH_DEVICE = context.getString(R.string.key_list_bluetooth_device);

        // Load default values
        DEFAULT_UNITS_DISTANCE = context.getString(R.string.list_distance_km);
        DEFAULT_UNITS_ENERGY_CONSUMPTION = context.getString(R.string.list_energy_consumption_kwh_100km);
        DEFAULT_UNITS_TEMPERATURE = context.getString(R.string.list_temperature_c);
        DEFAULT_BLUETOOTH_DEVICE = "";

        // Create the SharedPreferences object
        mContext = context;
        sharedPreferences = context.getSharedPreferences( SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE );
    }

    public String getUnitsDistanceStringValue() {
        return sharedPreferences.getString(PREF_UNITS_DISTANCE, DEFAULT_UNITS_DISTANCE);
    }

    public String getUnitsEnergyConsumptionStringValue() {
        return sharedPreferences.getString(PREF_UNITS_ENERGY_CONSUMPTION, DEFAULT_UNITS_ENERGY_CONSUMPTION);
    }

    public String getUnitsTemperatureStringValue() {
        return sharedPreferences.getString(PREF_UNITS_TEMPERATURE, DEFAULT_UNITS_TEMPERATURE);
    }

    public String getBluetoothDeviceStringValue() {
        return sharedPreferences.getString(PREF_BLUETOOTH_DEVICE, DEFAULT_BLUETOOTH_DEVICE);
    }

    public Context getContext() {
        return mContext;
    }
}