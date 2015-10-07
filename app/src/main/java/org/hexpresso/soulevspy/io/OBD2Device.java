package org.hexpresso.soulevspy.io;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.widget.Toast;

import org.hexpresso.soulevspy.R;
import org.hexpresso.soulevspy.util.ClientSharedPreferences;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;

/**
 * Created by pemessier on 2015-10-03.
 */
public class OBD2Device {
    final BluetoothSPP mBluetoothDevice;
    final boolean      mIsBluetoothAvailable;

    final ClientSharedPreferences mSharedPreferences;

    /**
     * Constructor
     * @param sharedPreferences
     */
    public OBD2Device(ClientSharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;

        mBluetoothDevice = new BluetoothSPP(mSharedPreferences.getContext());
        mIsBluetoothAvailable = mBluetoothDevice.isBluetoothAvailable();

        mBluetoothDevice.setDeviceTarget(BluetoothState.DEVICE_OTHER);
    }

    /**
     * Verifies if Bluetooth is available
     * @return
     */
    public boolean isBluetoothAvailable() {
        return mIsBluetoothAvailable;
    }

    public void startSetvice() {
        mBluetoothDevice.startService(false);
    }

    public void stopService() {
        mBluetoothDevice.stopService();
    }

    public boolean connect() {
        boolean isDeviceValid = mIsBluetoothAvailable;

        if ( isDeviceValid ) {
            String btAddress = mSharedPreferences.getBluetoothDeviceStringValue();
            BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();

            if ((btAddress != mSharedPreferences.DEFAULT_BLUETOOTH_DEVICE) && (bta != null)) {
                // Set the bluetooth adapter name as summary
                try {
                    BluetoothDevice device = bta.getRemoteDevice(btAddress);
                    mBluetoothDevice.connect(btAddress);
                    isDeviceValid = true;
                } catch (IllegalArgumentException e) {
                    isDeviceValid = false;
                }
            }
            else {
                Toast.makeText(mSharedPreferences.getContext(), R.string.error_no_bluetooth_device, Toast.LENGTH_LONG).show();
                isDeviceValid = false;
            }
        } else {
            Toast.makeText(mSharedPreferences.getContext(), R.string.error_bluetooth_not_available, Toast.LENGTH_LONG).show();
        }

        return isDeviceValid;
    }

}
