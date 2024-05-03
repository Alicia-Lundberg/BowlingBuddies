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
    private ImageView person1, person2, person3;
    private TextView text1, text2, text3;
    private Handler handler;
    private int counter = 0;
    private PopupWindow popupWindow;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button play = findViewById(R.id.buttonSingle);
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
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_info, null);

        person1 = popupView.findViewById(R.id.person1);
        person2 = popupView.findViewById(R.id.person2);
        person3 = popupView.findViewById(R.id.person3);
        text1 = popupView.findViewById(R.id.text1);
        text2 = popupView.findViewById(R.id.text2);
        text3 = popupView.findViewById(R.id.text3);

        hideEverything();

        handler = new Handler();

        popupLoop();

        // Create the popup window
        int width = 1000;
        int height = 1200;
        boolean focusable = true;
        popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                counter = 0;
                handler.removeCallbacks(runnable);
            }
        });
    }

    private void popupLoop() {
        runnable = new Runnable() {
            @Override
            public void run() {
                switch (counter) {
                    case 0:
                        hideEverything();
                        person2.setVisibility(View.VISIBLE);
                        text2.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        hideEverything();
                        person1.setVisibility(View.VISIBLE);
                        text1.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        hideEverything();
                        person3.setVisibility(View.VISIBLE);
                        text3.setVisibility(View.VISIBLE);
                        break;
                }
                counter = (counter + 1) % 3;
                handler.postDelayed(this, 1500);
            }
        };

        handler.postDelayed(runnable, 1500);
    }

    private void hideEverything() {
        person1.setVisibility(View.GONE);
        person2.setVisibility(View.GONE);
        person3.setVisibility(View.GONE);
        text1.setVisibility(View.GONE);
        text2.setVisibility(View.GONE);
        text3.setVisibility(View.GONE);
    }
}
