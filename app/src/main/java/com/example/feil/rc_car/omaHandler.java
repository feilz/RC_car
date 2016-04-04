package com.example.feil.rc_car;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class omaHandler extends Handler {
    protected static final int SUCCESS_CONNECT = 0;
    protected static final int MESSAGE_READ = 1;
    protected static final int UP = 2;
    protected static final int DOWN = 3;
    protected static final int TRANSFORM = 4;
    protected static final int LEFT = 5;
    protected static final int RIGHT = 6;
    protected static final int ROTLEFT = 7;
    protected static final int ROTRIGHT = 8;
    protected static final int receiveHandler = 12;

    BluetoothSocket socket;
    static String tag = "debugging";
    WeAreConnected self;

    public omaHandler(BluetoothSocket bs, WeAreConnected asd) {
        socket = bs;
        self = asd;
    }
    @Override
    public void handleMessage(Message msg) {
        Log.i(tag, "in handler");
        super.handleMessage(msg);
        String s;
        omaConnectedThread connectedThread;
        switch (msg.what) {
            case SUCCESS_CONNECT:
                // DO something
                connectedThread = new omaConnectedThread(socket,this);
                //Toast.makeText(getApplicationContext(), "CONNECT", Toast.LENGTH_SHORT).show();
                s = "successfully connected";
                connectedThread.write(s.getBytes());
                Log.i(tag, "connected");
                break;
            case receiveHandler:
                self.bgHandler = (Handler)msg.obj;
                break;
            case MESSAGE_READ:
                byte[] readBuf = (byte[]) msg.obj;
                //String string = new String(readBuf);
                //Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
                break;
            case UP:
                connectedThread = new omaConnectedThread(socket, this);
                s = "2";
                connectedThread.write(s.getBytes());
                break;
            case DOWN:
                connectedThread = new omaConnectedThread(socket,this);
                s = "3";
                connectedThread.write(s.getBytes());
                break;
            case TRANSFORM:
                connectedThread = new omaConnectedThread(socket,this);
                s = "4";
                connectedThread.write(s.getBytes());
                break;
            case LEFT:
                connectedThread = new omaConnectedThread(socket,this);
                s = "5";
                connectedThread.write(s.getBytes());
                break;
            case RIGHT:
                connectedThread = new omaConnectedThread(socket,this);
                s = "6";
                connectedThread.write(s.getBytes());
        }
    }
}
