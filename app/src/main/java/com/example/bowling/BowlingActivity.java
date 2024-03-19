package com.example.bowling;

import static java.lang.Math.random;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Random;

public class BowlingActivity extends AppCompatActivity {

    private ImageView kagla1;
    private ImageView kagla2;
    private ImageView kagla3;
    private ImageView kagla4;
    private ImageView kagla5;
    private ImageView kagla6;
    private ImageView blueBall;

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