package com.example.yassine.robotcontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by YASSINE on 13/05/2017.
 */
public class ConnexionActivity extends Activity {
    EditText txtIp;
    Button btnConnect , btnPage;
    String strIp;

    public static String IP = "ip";
    int object;

    //static final String OBJECT = "object";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion_interface);
        txtIp = (EditText) findViewById(R.id.edit_ip);
        btnConnect = (Button) findViewById(R.id.btn_connect);
        txtIp.setText("192.168.43.44");
        //btnPage = (Button) findViewById(R.id.camera_button);

        //object = getIntent().getIntExtra(OBJECT , 1);

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strIp = txtIp.getText().toString();
                Intent intentService = new Intent(ConnexionActivity.this , SocketService.class);
                intentService.putExtra(IP , strIp);
                startService(intentService);
                Intent i = new Intent(getApplicationContext() , HomeActivity.class);
                i.putExtra(IP , strIp);
                startActivity(i);
            }
        });

    }
}
