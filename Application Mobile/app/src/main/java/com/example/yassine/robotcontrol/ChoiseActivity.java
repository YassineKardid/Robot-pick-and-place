package com.example.yassine.robotcontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by YASSINE on 30/06/2017.
 */
public class ChoiseActivity extends Activity {
    ImageButton buttonManual , buttonAutomatic;

    String ipString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choise_interface);

        ipString = getIntent().getStringExtra(ConnexionActivity.IP);
        final int object = getIntent().getIntExtra(HomeActivity.OBJECT , 1);

        buttonManual = (ImageButton) findViewById(R.id.button_manual);
        buttonAutomatic = (ImageButton) findViewById(R.id.button_automatic);

        buttonManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChoiseActivity.this , MainActivity.class);
                i.putExtra(ConnexionActivity.IP , ipString);
                i.putExtra(HomeActivity.OBJECT , object);
                startActivity(i);
            }
        });

    }
}
