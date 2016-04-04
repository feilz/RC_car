package com.example.feil.rc_car;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class WeAreConnected extends Activity{
    Button forward,backward,lefts,rights, stop,rotright, rotleft;
    MyApp myapp;
    omaHandler myHandler;
    omaConnectedThread mThread;
    public Handler bgHandler;
    protected static final int SUCCESS_CONNECT = 0;
    protected static final int MESSAGE_READ = 1;
    protected static final int UP = 2;
    protected static final int DOWN = 3;
    protected static final int TRANSFORM = 4;
    protected static final int LEFT = 5;
    protected static final int RIGHT = 6;
    protected static final int ROTLEFT = 7;
    protected static final int ROTRIGHT = 8;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weareconnected);
        myapp = (MyApp) getApplication();
        myHandler = new omaHandler(myapp.bSocket, this);
        mThread = new omaConnectedThread(myapp.bSocket,myHandler);
        /*forward = (Button)findViewById(R.id.top);
        backward = (Button)findViewById(R.id.bot);
        lefts = (Button)findViewById(R.id.left);
        rights = (Button)findViewById(R.id.right);
        stop = (Button)findViewById(R.id.mid);
        rotleft=(Button)findViewById(R.id.rotateLeft);
        rotright=(Button)findViewById(R.id.rotateRight);*/
        mThread.start();
        myHandler.obtainMessage(SUCCESS_CONNECT).sendToTarget();
    }
    public void pressUp(View v){
        myHandler.obtainMessage(UP).sendToTarget();
    }
    public void pressDown(View v){
        myHandler.obtainMessage(DOWN).sendToTarget();
    }
    public void transform(View view) {
        myHandler.obtainMessage(TRANSFORM).sendToTarget();
    }
    public void turnLeft(View view) {
        myHandler.obtainMessage(LEFT).sendToTarget();
    }
    public void turnRight(View view) {
        myHandler.obtainMessage(RIGHT).sendToTarget();
    }
    public void rotateLeft(View view) {
        myHandler.obtainMessage(ROTLEFT).sendToTarget();
    }
    public void rotateRight(View view) {
        myHandler.obtainMessage(ROTRIGHT).sendToTarget();
    }
    public void onDestroy(){
        super.onDestroy();
        mThread.cancel();
        finish();
    }

}

