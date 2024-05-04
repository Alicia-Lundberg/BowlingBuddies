package com.example.bowling;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class LeaderboardActivity extends AppCompatActivity {

    Vibrator vibe;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.setLooping(true); // Gör så musiken spelar på loop i bakgrunden
        mediaPlayer.start();

        ImageButton returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //stänger av musiken när man går till hemskärm
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                startActivity(new Intent(LeaderboardActivity.this, BowlingActivity.class));
            }
        });

    }

    @Override
    protected  void onResume(){
        super.onResume();

    }
    @Override
    protected  void onPause(){
        super.onPause();
    }
}
