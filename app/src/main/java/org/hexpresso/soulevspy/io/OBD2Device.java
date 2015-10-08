package org.hexpresso.soulevspy.io;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.hexpresso.soulevspy.R;
import org.hexpresso.soulevspy.util.ClientSharedPreferences;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;

/**
 * Created by pemessier on 2015-10-03.
 */
public class OBD2Device implements BluetoothSPP.OnDataReceivedListener {
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

        // Start Bluetooth service
        mBluetoothDevice.setOnDataReceivedListener(this);
        mBluetoothDevice.setDeviceTarget(BluetoothState.DEVICE_OTHER);
    }

    /**
     * Verifies if Bluetooth is available
     * @return
     */
    public boolean isBluetoothAvailable() {
        return mIsBluetoothAvailable;
    }

    public boolean connect() {
        boolean isDeviceValid = mIsBluetoothAvailable;

        if ( isDeviceValid ) {
            String btAddress = mSharedPreferences.getBluetoothDeviceStringValue();
            BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();

            if ((btAddress != mSharedPreferences.DEFAULT_BLUETOOTH_DEVICE) && (bta != null)) {
                // Set the bluetooth adapter name as summary
                try {
                    if( mBluetoothDevice.isServiceAvailable() )
                    {
                        mBluetoothDevice.connect(btAddress);
                        isDeviceValid = true;
                    }
                    else
                    {
                        Toast.makeText(mSharedPreferences.getContext(), R.string.error_bluetooth_service_not_available, Toast.LENGTH_LONG).show();
                        isDeviceValid = false;
                    }
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

        if(!isDeviceValid)
        {
            disconnect();
        }

        return isDeviceValid;
    }

    public boolean disconnect() {
        mBluetoothDevice.disconnect();

        return true;
    }

    public void stopService() {
        mBluetoothDevice.stopService();
    }

    public void onDataReceived(byte[] data, String message) {
        Log.i("Check", "Length : " + data.length);
        Log.i("Check", "Message : " + message);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("SoulEVSpy", String.format("onActivityResult: %d, %d", requestCode, resultCode));
    }
}
