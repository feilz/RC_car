package com.example.feil.rc_car;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class omaConnectedThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private omaHandler mHandler;
    private Handler thisHandler;

    public omaConnectedThread(BluetoothSocket socket, omaHandler handler) {
        mHandler = handler;

        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    public void write(byte[] bytes) {
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        thisHandler.getLooper().quit();
    }
    public void run() {
        byte[] buffer;  // buffer store for the stream
        int bytes; // bytes returned from read()
        //Looper.prepare();
        thisHandler = new Handler();
        // Keep listening to the InputStream until an exception occurs
        mHandler.sendMessage(Message.obtain(mHandler, 12, thisHandler));
        while (true) {
            try {
                // Read from the InputStream
                buffer = new byte[1024];
                bytes = mmInStream.read(buffer);
                // Send the obtained bytes to the UI activity
                mHandler.sendMessage(Message.obtain(thisHandler, bytes));
                //omaHandler(MESSAGE_READ, bytes, -1, buffer)
                  //      .sendToTarget();

            } catch (IOException e) {
                break;
            }
        }
        //Looper.loop();

    }
}
