package org.hexpresso.soulevspy.io;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.hexpresso.elm327.io.ServiceStates;
import org.hexpresso.elm327.io.bluetooth.BluetoothService;
import org.hexpresso.soulevspy.R;
import org.hexpresso.soulevspy.activity.MainActivity;
import org.hexpresso.soulevspy.util.ClientSharedPreferences;

/**
 * Created by Pierre-Etienne Messier <pierre.etienne.messier@gmail.com> on 2015-10-03.
 */
public class OBD2Device implements BluetoothService.ServiceStateListener {
    final BluetoothService mBluetoothService;
    final ClientSharedPreferences mSharedPreferences;
    final Context mContext;

    /**
     * Constructor
     * @param sharedPreferences
     */
    public OBD2Device(ClientSharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
        mContext = sharedPreferences.getContext();

        mBluetoothService = new BluetoothService();
        mBluetoothService.setServiceStateListener(this);

        // Start Bluetooth service
        if (mBluetoothService.isBluetoothAvailable()) {
            mBluetoothService.useSecureConnection(true);
        }
    }

    public boolean connect() {
        boolean isDeviceValid = mBluetoothService.isBluetoothAvailable();

        if ( isDeviceValid ) {
            String btAddress = mSharedPreferences.getBluetoothDeviceStringValue();
            BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();

            Log.d("SOULEV", "Trying to connect to ELM327 device : " + btAddress);

            if (!btAddress.equals(mSharedPreferences.DEFAULT_BLUETOOTH_DEVICE) && (bta != null)) {
                // Set the bluetooth adapter name as summary
                try {
                    mBluetoothService.setDevice(btAddress);
                    mBluetoothService.connect();
                    isDeviceValid = true;
                } catch (IllegalArgumentException e) {
                    isDeviceValid = false;
                }
            }
            else {
                Toast.makeText(mContext, R.string.error_no_bluetooth_device, Toast.LENGTH_LONG).show();
                isDeviceValid = false;
            }
        } else {
            Toast.makeText(mContext, R.string.error_bluetooth_not_available, Toast.LENGTH_LONG).show();
        }

        if(!isDeviceValid)
        {
            disconnect();
        }

        return isDeviceValid;
    }

    public boolean disconnect() {
        mBluetoothService.disconnect();
        return true;
    }

    @Override
    public void onServiceStateChanged(ServiceStates state) {
        String message = null;
        switch(state) {
            case STATE_CONNECTING:
                message = "Connecting...";
                break;
            case STATE_CONNECTED:
                message = "Connected";
                break;
            case STATE_DISCONNECTING:
                message = "Disconnecting...";
                break;
            case STATE_DISCONNECTED:
                message = "Disconnected";
                break;
            default:
                // Do nothing
                break;
        }

        final String msg = message;

        // TODO : This is just for a test!
        ((MainActivity)mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}