package com.example.yassine.robotcontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by YASSINE on 30/06/2017.
 */
public class HomeActivity extends Activity{
    ImageButton buttonWater , buttonDrug;
    static final String OBJECT = "object";
    String ipString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_interface);

        ipString = getIntent().getStringExtra(ConnexionActivity.IP);

        buttonWater = (ImageButton) findViewById(R.id.button_water);
        buttonDrug = (ImageButton) findViewById(R.id.button_drug);

        buttonWater.setOnClickListener(listener);
        buttonDrug.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(HomeActivity.this , ChoiseActivity.class);
            i.putExtra(ConnexionActivity.IP , ipString);
            if(v.getId() == R.id.button_water){
                i.putExtra(OBJECT , 1);
            }
            else if(v.getId() == R.id.button_drug){
                i.putExtra(OBJECT , 2);
            }

            startActivity(i);
        }
    };
}
