package com.example.bowling;

import static java.lang.Math.random;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
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

    private int strikeCounter;

    private int LeftstrikeCounter;

    private int RightstrikeCounter;

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
        Button button2 = findViewById(R.id.button2);
        accelerometer = new Accelerometer(this);

        accelerometer.setListener(new Accelerometer.Listener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onTranslation(float tx, float ty, float tz) {
                String valuesText = String.format(Locale.getDefault(),
                        "Accelerometer x: %.1f y: %.1f, z: %.1f", tx, ty,tz);


               // Detta behövs för att checka att inbromsningen i baksvingen inte räknas som et rakt kast
               fiveZago = fourZago;
               fourZago = threeZago;
               threeZago = twoZago;
               twoZago = oneZago;
               oneZago = zNow;
               zNow = tz;
               if (fiveZago < 0) {
                   strikeCounter = 0;
               }
                button2.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        //När man trycker ner knappen, "kasta" bollen, kan behövas öndra så att kastet sykas med att alla pins faller
                        if(event.getAction()==MotionEvent.ACTION_UP){
                            strikeCounter=0;
                            throwBallStrike(blueBall);
                            return true;
                        }

                        //När man släpper knappen, reset både pins och boll
                        if(event.getAction()==MotionEvent.ACTION_DOWN){
                            resetBall(blueBall);
                            resetPins();
                            return true;
                        }

                        //logga värdena och anropa onScore för att check om det är en score
                        Log.d("accelerometer",valuesText);
                        Log.d("counter", Integer.toString(strikeCounter));
                        onScore(tx, ty, tz);
                        return false;
                    }
                });

            }
        });


        ImageButton returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BowlingActivity.this, MainActivity.class));
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
                throwBallStrike(blueBall);
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

    }
    @Override
    protected  void onPause(){
        super.onPause();
        accelerometer.unregister();
    }
    private void onScore(float tx, float ty, float tz) {
        if (ty < -1 && tz > 1 && Math.abs(tx) < 0.5) {
            Log.d("Score", "Score");
            strikeCounter ++;
        }

        if (strikeCounter > 3) {
            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    kaglaFall(kagla1);
                    kaglaFall(kagla2);
                    kaglaFall(kagla3);
                    kaglaFall(kagla4);
                    kaglaFall(kagla5);
                    kaglaFall(kagla6);
                    MediaPlayer mediaPlayer = MediaPlayer.create(BowlingActivity.this, R.raw.strike);
                    mediaPlayer.start();
                }
            }, 800);
            strikeCounter = 0;
            MediaPlayer mediaPlayer = MediaPlayer.create(BowlingActivity.this, R.raw.strike);
            mediaPlayer.start();
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
            if (random.nextBoolean()) {
                kaglarotation = kagla.getRotation() - 90f;
            }else{
                kaglarotation = kagla.getRotation() - 120f;
            }
        } else {
            if (random.nextBoolean()) {
                kaglarotation = kagla.getRotation() + 90f;
            }else{
                kaglarotation = kagla.getRotation() + 120f;
            }
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
        ball.setTranslationX(0f);
        ball.setScaleX(1.0f);
        ball.setScaleY(1.0f);
        ball.setVisibility(View.VISIBLE);
    }

    private void throwBallStrike(final ImageView ball) {
        final float ballPlacement = -ball.getHeight() * 0.8f;
        final float scale = 0.5f;

        ball.animate()
                .setDuration(700)
                .translationYBy(ballPlacement)
                .scaleX(scale)
                .scaleY(scale)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        // Hide the ImageView when animation ends
                        ball.setVisibility(View.GONE);
                    }
                })
                .start();
    }

    private void throwBallMissLeft(final ImageView ball) {
        final float ballPlacement = -ball.getHeight() * 0.8f;
        final float scale = 0.5f;
        final float left = -180f;

        ball.animate()
                .setDuration(700)
                .translationXBy(left)
                .translationYBy(ballPlacement)
                .scaleX(scale)
                .scaleY(scale)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        // Hide the ImageView when animation ends
                        ball.setVisibility(View.GONE);
                    }
                })
                .start();
    }

    private void throwBallMissRight(final ImageView ball) {
        final float ballPlacement = -ball.getHeight() * 0.8f;
        final float scale = 0.5f;
        final float right = 180f;

        ball.animate()
                .setDuration(700)
                .translationXBy(right)
                .translationYBy(ballPlacement)
                .scaleX(scale)
                .scaleY(scale)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        // Hide the ImageView when animation ends
                        ball.setVisibility(View.GONE);
                    }
                })
                .start();
    }

    private void throwBallLeft(final ImageView ball) {
        final float ballPlacement = -ball.getHeight() * 0.8f;
        final float scale = 0.5f;
        final float left = -90f;

        ball.animate()
                .setDuration(700)
                .translationXBy(left)
                .translationYBy(ballPlacement)
                .scaleX(scale)
                .scaleY(scale)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        // Hide the ImageView when animation ends
                        ball.setVisibility(View.GONE);
                    }
                })
                .start();
    }

    private void throwBallRight(final ImageView ball) {
        final float ballPlacement = -ball.getHeight() * 0.8f;
        final float scale = 0.5f;
        final float right = 90f;

        ball.animate()
                .setDuration(700)
                .translationXBy(right)
                .translationYBy(ballPlacement)
                .scaleX(scale)
                .scaleY(scale)
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