package com.example.yassine.robotcontrol;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.PrintWriter;

/**
 * Created by YASSINE on 14/05/2017.
 */
public class SettingActivity extends Activity {
    EditText editMotor1 , editMotor2 , editMotor3 , editMotor4 , editRotationMax , editRotationMin;
    Button btnSendSettings;
    int vitesseMotor1 , vitesseMotor2 , vitesseMotor3 , vitesseMotor4 , vitesseRotationMax , vitesseRotationMin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_interface);

        editMotor1 = (EditText) findViewById(R.id.edit_motor1);
        editMotor2 = (EditText) findViewById(R.id.edit_motor2);
        editMotor3 = (EditText) findViewById(R.id.edit_motor3);
        editMotor4 = (EditText) findViewById(R.id.edit_motor4);
        editRotationMax = (EditText) findViewById(R.id.edit_rotation_max);
        editRotationMin = (EditText) findViewById(R.id.edit_rotation_min);

        btnSendSettings = (Button) findViewById(R.id.btn_send_settings);

        final PrintWriter outPrint = SocketService.getOut();

        btnSendSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vitesseMotor1 = Integer.parseInt(editMotor1.getText().toString());
                vitesseMotor2 = Integer.parseInt(editMotor2.getText().toString());
                vitesseMotor3 = Integer.parseInt(editMotor3.getText().toString());
                vitesseMotor4 = Integer.parseInt(editMotor4.getText().toString());
                vitesseRotationMax = Integer.parseInt(editRotationMax.getText().toString());
                vitesseRotationMin = Integer.parseInt(editRotationMin.getText().toString());
                outPrint.print("settings");
                outPrint.flush();
                /*outPrint.println(vitesseMotor1);
                outPrint.flush();
                outPrint.println(vitesseMotor2);
                outPrint.flush();
                outPrint.println(vitesseMotor3);
                outPrint.flush();
                outPrint.println(vitesseMotor4);
                outPrint.flush();
                outPrint.println(vitesseRotationMax);
                outPrint.flush();
                outPrint.println(vitesseRotationMin);
                outPrint.flush();*/

                outPrint.print(editMotor1.getText().toString() + ' ');
                outPrint.flush();
                outPrint.print(editMotor2.getText().toString() + ' ');
                outPrint.flush();
                outPrint.print(editMotor3.getText().toString() + ' ');
                outPrint.flush();
                outPrint.print(editMotor4.getText().toString() + ' ');
                outPrint.flush();
                outPrint.print(editRotationMax.getText().toString() + ' ');
                outPrint.flush();
                outPrint.print(editRotationMin.getText().toString() + ' ');
                outPrint.flush();
            }
        });
    }
}
