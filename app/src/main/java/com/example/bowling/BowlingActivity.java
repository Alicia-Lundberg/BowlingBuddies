package com.example.bowling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class BowlingActivity extends AppCompatActivity {

    private ImageView kagla1;
    private ImageView kagla2;
    private ImageView kagla3;
    private ImageView kagla4;
    private ImageView kagla5;
    private ImageView kagla6;

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
        Button bowlingButton = findViewById(R.id.bowlingButton);

        bowlingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v){
                // Calculate the final rotation angle (90 degrees to the right)
                final float finalRotation = kagla6.getRotation() + 90f;
                float translationYDistance = kagla6.getHeight() * 0.2f;

                // Start the animation
                kagla6.animate()
                        .setDuration(300)
                        .rotation(finalRotation)
                        .translationYBy(translationYDistance)
                        .start();
                }

        });
    }
}