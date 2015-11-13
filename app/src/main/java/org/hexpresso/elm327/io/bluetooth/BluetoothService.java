package org.hexpresso.elm327.io.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import org.hexpresso.elm327.io.Service;
import org.hexpresso.elm327.io.ServiceStates;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Pierre-Etienne Messier <pierre.etienne.messier@gmail.com> on 2015-10-30.
 */
public class BluetoothService extends Service {

    // We are connecting to a Bluetooth serial board, therefore we use the well-known SPP UUID
    private static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private final BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBluetoothDevice = null;
    private BluetoothSocket mBluetoothSocket = null;

    private boolean mUseSecureConnection = true;
    private Boolean mBluetoothAvailable = null;

    /**
     * Constructor
     */
    public BluetoothService() {
        super();

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    /**
     * Sets the Bluetooth address of the device to use for connection
     *
     * @param address Device Bluetooth address as string
     */
    public void setDevice(String address) {
        mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(address);
    }

    /**
     * Connects to the ELM327 device
     */
    @Override
    public synchronized void connect() {
        // TODO prevent dual connect?
        if(isBluetoothAvailable()) {
            Thread connectionThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // Make sure we are disconnected first
                    internalDisconnect();

                    // Start the connection routine
                    internalConnect();
                }
            });
            connectionThread.start();
        }
    }

    /**
     * Connection routine
     */
    private void internalConnect() {
        setState(ServiceStates.STATE_CONNECTING);

        mBluetoothAdapter.cancelDiscovery();

        try {
            // Create the Bluetooth connection with the device
            if (mUseSecureConnection) {
                mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(SPP_UUID);
            }
            else {
                mBluetoothSocket = mBluetoothDevice.createInsecureRfcommSocketToServiceRecord(SPP_UUID);
            }
            mBluetoothSocket.connect();

            mInputStream  = mBluetoothSocket.getInputStream();
            mOutputStream = mBluetoothSocket.getOutputStream();

            setState(ServiceStates.STATE_CONNECTED);

            startProtocol();

        } catch (IOException e) {
            disconnect();
        }
    }

    /**
     * Disconnects from the ELM327 device
     */
    @Override
    public synchronized void disconnect() {
        // TODO prevent dual disconnect?
        Thread disconnectionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                internalDisconnect();
            }
        });
        disconnectionThread.start();
    }

    /**
     * Disconnection routine
     */
    public void internalDisconnect() {
        if( getState() != ServiceStates.STATE_DISCONNECTED) {

            // Starting the disconnection routine
            setState(ServiceStates.STATE_DISCONNECTING);

            // Stops the ELM327 protocol
            stopProtocol();

            // Close the streams
            closeStreams();

            // Then close the socket
            if (mBluetoothSocket != null) {
                try {
                    mBluetoothSocket.close();
                } catch (IOException e) {
                    // Do nothing
                }
                mBluetoothSocket = null;
            }

            setState(ServiceStates.STATE_DISCONNECTED);
        }
    }

    /**
     * Configure the class to use a secure or insecure connection
     *
     * @param useSecureConnection true to use a secure connection, false otherwise
     */
    public void useSecureConnection(boolean useSecureConnection) {
        mUseSecureConnection = useSecureConnection;
    }

    /**
     * Verifies if Bluetooth is available
     *
     * @return True if Bluetooth is available, false otherwise
     */
    public boolean isBluetoothAvailable() {
        if(mBluetoothAvailable == null) {
            try {
                mBluetoothAvailable = new Boolean((mBluetoothAdapter != null) && (!mBluetoothAdapter.getAddress().equals((Object) null)));
            } catch (NullPointerException e) {
                mBluetoothAvailable = new Boolean(false);
            }
        }
        return mBluetoothAvailable.booleanValue();
    }
}