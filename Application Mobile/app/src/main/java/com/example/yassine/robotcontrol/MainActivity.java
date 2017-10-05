package com.example.yassine.robotcontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by YASSINE on 12/05/2017.
 */
public class MainActivity extends Activity {

    public static String SOCKET = "socket";
    String strIp;
    ImageButton btnUp, btnLeft, btnRight, btnDown, btnStop, btnSettings;
    Socket socket = null;
    OutputStream outS;
    public PrintWriter printOut;
    WebView mWebView;
    boolean settingsActivity = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_interface);
        btnUp = (ImageButton) findViewById(R.id.btn_up);
        btnLeft = (ImageButton) findViewById(R.id.btn_left);
        btnRight = (ImageButton) findViewById(R.id.btn_right);
        btnDown = (ImageButton) findViewById(R.id.btn_down);
        btnStop = (ImageButton) findViewById(R.id.btn_stop);
        btnSettings = (ImageButton) findViewById(R.id.btn_settings);

        btnUp.setOnClickListener(clickListener);
        btnLeft.setOnClickListener(clickListener);
        btnRight.setOnClickListener(clickListener);
        btnDown.setOnClickListener(clickListener);
        btnStop.setOnClickListener(clickListener);
        Intent i = getIntent();

        strIp = i.getStringExtra(ConnexionActivity.IP);
        //new Thread(new ClientThread()).start();
        mWebView = (WebView)findViewById(R.id.webview_layout);
        mWebView.loadUrl("http://"+strIp+":8081");

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSettings = new Intent(MainActivity.this , SettingActivity.class);
                //intentSettings.putExtra(SOCKET , (Object)socket);
                settingsActivity = true;
                Toast.makeText(getApplicationContext() , "Settings" , Toast.LENGTH_LONG).show();
                startActivity(intentSettings);
            }
        });
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            printOut = SocketService.getOut();
            if(v.getId() == R.id.btn_up){
                printOut.print("up");
                Toast.makeText(getApplicationContext() , "UP" , Toast.LENGTH_LONG).show();
            }
            else if(v.getId() == R.id.btn_left){
                printOut.print("left");
                Toast.makeText(getApplicationContext() , "LEFT" , Toast.LENGTH_LONG).show();
            }
            else if(v.getId() == R.id.btn_right){
                printOut.print("right");
                Toast.makeText(getApplicationContext() , "RIGHT" , Toast.LENGTH_LONG).show();
            }
            else if(v.getId() == R.id.btn_down){
                printOut.print("down");
                Toast.makeText(getApplicationContext() , "DOWN" , Toast.LENGTH_LONG).show();
            }
            else if(v.getId() == R.id.btn_stop){
                printOut.print("stop");
            }
            printOut.flush();
        }
    };



    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(!settingsActivity) {
            printOut.print("close");
            printOut.flush();
            Intent intentService = new Intent(MainActivity.this, SocketService.class);
            stopService(intentService);
        }
        /*try {
            printOut.print("close");
            printOut.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}

