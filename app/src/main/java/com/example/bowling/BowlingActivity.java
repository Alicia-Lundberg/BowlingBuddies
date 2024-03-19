package com.example.bowling;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class BowlingActivity extends AppCompatActivity {

    private Accelerometer accelerometer;
    private Gyroscope gyroscope;
    private TextView accelerometerValuesTextView;
    private TextView gyroscopeValuesTextView;
    private boolean issleeping  = false;
    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bowling);


        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BowlingActivity.this, MainActivity.class));
            }
        });

        Button button2 = findViewById(R.id.button2);


        accelerometer = new Accelerometer(this);
        accelerometerValuesTextView = findViewById(R.id.accelerometerValuesTextView);
        accelerometer.setListener(new Accelerometer.Listener() {
            @Override
            public void onTranslation(float tx, float ty, float tz) {
                String valuesText = String.format(Locale.getDefault(),
                        "Accelerometer x: %.1f y: %.1f, z: %.1f", tx, ty,tz);
                accelerometerValuesTextView.setText(valuesText);

                if (Math.abs(ty) > 2) {
                    //Log.d("accelerometer",valuesText);
                    //onThrow(tx);
                    //onScore(tx, ty, tz);
                }

                button2.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(event.getAction()==MotionEvent.ACTION_UP){
                            return true;
                        }
                        if(Math.abs(ty) > 2){
                            Log.d("accelerometer",valuesText);
                            //onThrow(tx);
                            onScore(tx, ty, tz);
                        }

                        return false;
                    }
                });

            }
        });



        gyroscope = new Gyroscope(this);
        gyroscopeValuesTextView = findViewById(R.id.gyroscopeValuesTextView);
        gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            public void onRotation(float tx, float ty, float tz) {
                String valuesText = String.format(Locale.getDefault(),
                        "Gyroscope x: %.1f y: %.1f, z: %.1f", tx, ty,tz);
                gyroscopeValuesTextView.setText(valuesText);

            }
        });



    }

    @Override
    protected  void onResume(){
        super.onResume();

        accelerometer.register();
        gyroscope.register();

    }


    @Override
    protected  void onPause(){
        super.onPause();

        accelerometer.unregister();
        gyroscope.unregister();
    }

    private void onThrow(float tx){

        if(Math.abs(tx) < 1 ){
            Log.d("rakt", "rakt");
        }else{
            Log.d("snett", "snett");
        }

    }


    private void onScore(float tx, float ty, float tz){
        if(ty < -2 && tz < -2 && Math.abs(tx) < 0.5){
            Log.d("Score", "Score");
        }
    }


}