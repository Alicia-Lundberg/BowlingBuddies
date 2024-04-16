package com.example.bowling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button play = findViewById(R.id.button);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BowlingActivity.class));
            }
        });

        ImageButton info = findViewById(R.id.imageButton2);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonShowPopupWindowClick(v);
            }
        });


    }


    public void onButtonShowPopupWindowClick(View v) {
        // Inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_info, null);

        ImageView person1 = popupView.findViewById(R.id.person1);
        ImageView person2 = popupView.findViewById(R.id.person2);
        ImageView person3 = popupView.findViewById(R.id.person3);
        TextView text1 = popupView.findViewById(R.id.text1);
        TextView text2 = popupView.findViewById(R.id.text2);
        TextView text3 = popupView.findViewById(R.id.text3);

        person1.setVisibility(View.GONE);
        person2.setVisibility(View.GONE);
        person3.setVisibility(View.GONE);
        text1.setVisibility(View.GONE);
        text2.setVisibility(View.GONE);
        text3.setVisibility(View.GONE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                person2.setVisibility(View.VISIBLE);
                text2.setVisibility(View.VISIBLE);
            }
        }, 500);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                person2.setVisibility(View.GONE);
                text2.setVisibility(View.GONE);
                person1.setVisibility(View.VISIBLE);
                text1.setVisibility(View.VISIBLE);
            }
        }, 2000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                person1.setVisibility(View.GONE);
                text1.setVisibility(View.GONE);
                person3.setVisibility(View.VISIBLE);
                text3.setVisibility(View.VISIBLE);
            }
        }, 3500);


        // Create the popup window
        int width = 1000;
        int height = 1200;
        boolean focusable = true; // Lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // Show the popup window
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        // Dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}