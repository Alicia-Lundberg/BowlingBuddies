package com.example.bowling;

import static java.lang.Math.random;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Random;

public class BowlingActivity extends AppCompatActivity {

    private Accelerometer accelerometer;
    private Gyroscope gyroscope;
    private TextView accelerometerValuesTextView;
    private TextView gyroscopeValuesTextView;
    private boolean issleeping  = false;
    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    private ImageView kagla1;
    private ImageView kagla2;
    private ImageView kagla3;
    private ImageView kagla4;
    private ImageView kagla5;
    private ImageView kagla6;
    private ImageView blueBall;

    private int scoreCounter;

    private boolean ThreeIsPositive = true;

   private float zNow = 1;

    private float oneZago = 1;

    private float twoZago = 1;

    private float threeZago = 1;
    private float fourZago = 1;
    private float fiveZago = 1;



    //private boolean isForward = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bowling);


        ImageButton returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BowlingActivity.this, MainActivity.class));
            }
        });

        Button button2 = findViewById(R.id.button2);


        accelerometer = new Accelerometer(this);

        accelerometer.setListener(new Accelerometer.Listener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onTranslation(float tx, float ty, float tz) {
                String valuesText = String.format(Locale.getDefault(),
                        "Accelerometer x: %.1f y: %.1f, z: %.1f", tx, ty,tz);

                if (Math.abs(ty) > 2) {
                    //Log.d("accelerometer",valuesText);
                    //onThrow(tx);
                    //onScore(tx, ty, tz);
                }
                fiveZago = fourZago;
                fourZago = threeZago;
                threeZago = twoZago;
                twoZago = oneZago;
                oneZago = zNow;
                zNow = tz;

               if (fiveZago < 0) {
                   scoreCounter = 0;
               }

                button2.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(event.getAction()==MotionEvent.ACTION_UP){
                            scoreCounter=0;



                            return true;
                        }
                        if(Math.abs(ty) > 2 ){
                            Log.d("accelerometer",valuesText);
                            Log.d("counter", Integer.toString(scoreCounter));
                            //onThrow(tx);
                            onScore(tx, ty, tz);

                        }

                        return false;
                    }
                });

            }
        });



        gyroscope = new Gyroscope(this);
        gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            public void onRotation(float tx, float ty, float tz) {
                String valuesText = String.format(Locale.getDefault(),
                        "Gyroscope x: %.1f y: %.1f, z: %.1f", tx, ty,tz);

            }
        });



        kagla6 = (ImageView) findViewById(R.id.kagla6);
        kagla5 = (ImageView) findViewById(R.id.kagla5);
        kagla4 = (ImageView) findViewById(R.id.kagla4);
        kagla3 = (ImageView) findViewById(R.id.kagla3);
        kagla2 = (ImageView) findViewById(R.id.kagla2);
        kagla1 = (ImageView) findViewById(R.id.kagla1);
        Button bowlingButton = findViewById(R.id.bowlingButton);
        bowlingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){

                kaglaFall(kagla6);
                kaglaFall(kagla5);
                kaglaFall(kagla4);
                kaglaFall(kagla3);
                kaglaFall(kagla2);
                kaglaFall(kagla1);

                MediaPlayer mediaPlayer = MediaPlayer.create(BowlingActivity.this, R.raw.strike);
                mediaPlayer.start();
            }
        });

        Button resetPins = findViewById(R.id.resetPins);
        resetPins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                resetPins();
            }
        });

        Button throwButton = findViewById(R.id.bowlingball);
        blueBall = (ImageView) findViewById(R.id.blueBall);
        throwButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                MediaPlayer mediaPlayer = MediaPlayer.create(BowlingActivity.this, R.raw.rolling);
                mediaPlayer.start();
                throwBall(blueBall);
            }
        });

        Button resetBall = findViewById(R.id.resetBall);
        resetBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                resetBall(blueBall);
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

    private void onThrow(float tx) {

        if (Math.abs(tx) < 1) {
            Log.d("rakt", "rakt");
        } else {
            Log.d("snett", "snett");
        }
    }

    private void onScore(float tx, float ty, float tz) {




        if (ty < -2 && tz > 2 && Math.abs(tx) < 0.5) {
             //Baklänges kast som blir positiv i slutet
            Log.d("Score", "Score");
            scoreCounter ++;

        }

        if (scoreCounter > 7) {
            kaglaFall(kagla1);
            kaglaFall(kagla2);
            kaglaFall(kagla3);
            kaglaFall(kagla4);
            kaglaFall(kagla5);
            kaglaFall(kagla6);
            scoreCounter = 0;
        }
    }






    private void kaglaFallRight(ImageView kagla) {
        final float kaglarotation = kagla.getRotation() + 90f;
        float kaglaplacement = kagla.getHeight() * 0.2f;

        kagla.animate()
                .setDuration(300)
                .rotation(kaglarotation)
                .translationYBy(kaglaplacement)
                .start();
    }

    private void kaglaFallLeft(ImageView kagla) {
        final float kaglarotation = kagla.getRotation() - 90f;
        float kaglaplacement = kagla.getHeight() * 0.2f;

        kagla.animate()
                .setDuration(300)
                .rotation(kaglarotation)
                .translationYBy(kaglaplacement)
                .start();
    }

    private void kaglaFall(ImageView kagla) {
        Random random = new Random();
        final float kaglarotation;
        if (random.nextBoolean()) {
            kaglarotation = kagla.getRotation() + 90f;
        } else {
            kaglarotation = kagla.getRotation() - 90f;
        }
        float kaglaplacement = kagla.getHeight() * 0.2f;
        kagla.animate()
                .setDuration(300)
                .rotation(kaglarotation)
                .translationYBy(kaglaplacement)
                .start();
    }

    private void resetPins() {
        kagla6.setRotation(0f);
        kagla6.setTranslationY(0f);
        kagla5.setRotation(0f);
        kagla5.setTranslationY(0f);
        kagla4.setRotation(0f);
        kagla4.setTranslationY(0f);
        kagla3.setRotation(0f);
        kagla3.setTranslationY(0f);
        kagla2.setRotation(0f);
        kagla2.setTranslationY(0f);
        kagla1.setRotation(0f);
        kagla1.setTranslationY(0f);
    }

    private void resetBall(ImageView ball) {
        ball.setTranslationY(0f);
        ball.setVisibility(View.VISIBLE);
    }

    private void throwBall(ImageView ball) {
        float ballPlacement = -ball.getHeight() * 1.3f;

        ball.animate()
                .setDuration(700)
                .translationYBy(ballPlacement)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        // Hide the ImageView when animation ends
                        ball.setVisibility(View.GONE);
                    }
                })
                .start();

    }
}