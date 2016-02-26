package com.example.feil.rc_car;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WeAreConnected extends Activity{
    Button forward,backward,lefts,rights, stop;
    MyApp myapp;
    BluetoothSocket socket;
    static String tag = "debugging";
    protected static final int SUCCESS_CONNECT = 0;
    protected static final int MESSAGE_READ = 1;
    protected static final int UP=2;
    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Log.i(tag, "in handler");
            super.handleMessage(msg);
            String s;
            ConnectedThread connectedThread;
            switch(msg.what){
                case SUCCESS_CONNECT:
                    // DO something
                    connectedThread = new ConnectedThread((BluetoothSocket)msg.obj);
                    Toast.makeText(getApplicationContext(), "CONNECT", Toast.LENGTH_SHORT).show();
                    s = "successfully connected";
                    connectedThread.write(s.getBytes());
                    Log.i(tag, "connected");
                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[])msg.obj;
                    String string = new String(readBuf);
                    Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
                    break;
                case UP:
                    connectedThread = new ConnectedThread((BluetoothSocket)msg.obj);
                    s = "45";
                    connectedThread.write(s.getBytes());
                    break;

            }
        }
    };
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weareconnected);
        myapp = (MyApp)getApplication();
        socket=myapp.bSocket;
        forward = (Button)findViewById(R.id.top);
        backward = (Button)findViewById(R.id.bot);
        lefts = (Button)findViewById(R.id.left);
        rights = (Button)findViewById(R.id.right);
        stop = (Button)findViewById(R.id.mid);
        mHandler.obtainMessage(SUCCESS_CONNECT, socket).sendToTarget();
    }
    public void pressUp(View v){
        mHandler.obtainMessage(UP, socket).sendToTarget();
    }
    public class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {e.printStackTrace(); }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer;  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    buffer = new byte[1024];
                    bytes = mmInStream.read(buffer);
                    // Send the obtained bytes to the UI activity
                    mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget();

                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) { e.printStackTrace();}
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { e.printStackTrace();}
        }
    }
}
