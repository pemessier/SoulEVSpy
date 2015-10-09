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
public class OBD2Device implements BluetoothSPP.OnDataReceivedListener,
                                   BluetoothSPP.BluetoothConnectionListener,
                                   BluetoothSPP.BluetoothStateListener {
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
        if (mIsBluetoothAvailable) {
            mBluetoothDevice.setOnDataReceivedListener(this);
            mBluetoothDevice.setBluetoothConnectionListener(this);
            mBluetoothDevice.setBluetoothStateListener(this);
            mBluetoothDevice.setupService();
            mBluetoothDevice.setDeviceTarget(BluetoothState.DEVICE_OTHER);
        }
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

            Log.d("SOULEV", "Trying to connect to ELM327 device : " + btAddress);

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

    public void sendData(String data) {
        mBluetoothDevice.send(data, true);
    }

    public void onDataReceived(byte[] data, String message) {
        Log.i("Check", "Length : " + data.length);
        Log.i("Check", "Message : " + message);
    }

    public void onDeviceConnected(String name, String address) {
        // Do something when successfully connected
    }

    public void onDeviceDisconnected() {
        // Do something when connection was disconnected
    }

    public void onDeviceConnectionFailed() {
        // Do something when connection failed
    }

    public void onServiceStateChanged(int state) {
        if(state == BluetoothState.STATE_CONNECTED) {
            // Do something when successfully connected
        }
        else if(state == BluetoothState.STATE_CONNECTING){
            // Do something while connecting
        }
        else if(state == BluetoothState.STATE_LISTEN) {
            // Do something when device is waiting for connection
        } else if(state == BluetoothState.STATE_NONE) {
            // Do something when device don't have any connection
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("SoulEVSpy", String.format("onActivityResult: %d, %d", requestCode, resultCode));
    }
}