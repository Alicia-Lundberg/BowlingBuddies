package com.example.bowling;

import static java.lang.Math.random;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.util.Locale;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.content.SharedPreferences; // Till highscore

import com.google.android.material.color.utilities.Score;

import java.util.Random;

public class BowlingActivity extends AppCompatActivity {
    private ImageView person1, person2, person3;
    private TextView text1, text2, text3;
    //private Handler handler;
    private int counter = 0;
    private PopupWindow popupWindow;
    private Runnable runnable;
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
    private ImageView orangeBall;

    private ImageView crown;
    private ImageView strikePopup;
    private ImageView sparePopup;
    private ImageView pointsPopup;

    private int strikeCounter;

    private int leftCounter;

    private int rightCounter;

    private boolean ThreeIsPositive = true;

    private float zNow = 1;

    private float oneZago = 1;

    private float twoZago = 1;

    private float threeZago = 1;
    private float fourZago = 1;
    private float fiveZago = 1;

    private ImageView blueBall;
    private ImageView one;
    private ImageView two;
    private ImageView three;
    private ImageView four;
    private ImageView five;
    private ImageView six;

    private int Throw = 0;

    private int pinsDown = 0;

    private int score = 0;

    private int roundCount = 10;
    private TextView scoreView;

    private TextView highScoreView;

    private ImageView throw1;
    private ImageView throw2;
    private ImageView round1;
    private ImageView round2;
    private ImageView round3;
    private ImageView round4;
    private ImageView round5;
    private ImageView round6;
    private ImageView round7;
    private ImageView round8;
    private ImageView round9;
    private ImageView round10;

    private TextView highScoreText;

    Vibrator vibe;
    private Vibrator vibrator;
    private Handler handler = new Handler();
    private boolean isVibrating = false;
    private MediaPlayer mediaPlayer;

    private int highScore;
    private SharedPreferences sharedPreferences;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bowling);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE); //Highscore
        highScore = sharedPreferences.getInt("highScore", 0);


        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.setVolume(0.8f, 0.8f);
        mediaPlayer.setLooping(true); // Gör så musiken spelar på loop i bakgrunden
        mediaPlayer.start();

        throw1 = (ImageView) findViewById(R.id.throw1);
        throw2 = (ImageView) findViewById(R.id.throw2);
        round1 = (ImageView) findViewById(R.id.round1);
        round2 = (ImageView) findViewById(R.id.round2);
        round3 = (ImageView) findViewById(R.id.round3);
        round4 = (ImageView) findViewById(R.id.round4);
        round5 = (ImageView) findViewById(R.id.round5);
        round6 = (ImageView) findViewById(R.id.round6);
        round7 = (ImageView) findViewById(R.id.round7);
        round8 = (ImageView) findViewById(R.id.round8);
        round9 = (ImageView) findViewById(R.id.round9);
        round10 = (ImageView) findViewById(R.id.round10);

        crown = (ImageView) findViewById(R.id.crown);


        ImageButton returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //stänger av musiken när man går till hemskärm
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                startActivity(new Intent(BowlingActivity.this, MainActivity.class));
            }
        });

        ImageButton settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindow popupWindow = new PopupWindow(BowlingActivity.this);

                View popupView = getLayoutInflater().inflate(R.layout.activity_notimplemented, null);

                popupWindow.setContentView(popupView);

                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);

                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
            }

        });

        ImageButton leaderboard = findViewById(R.id.leaderboard);
        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindow popupWindow = new PopupWindow(BowlingActivity.this);

                View popupView = getLayoutInflater().inflate(R.layout.activity_leaderboard, null);

                popupWindow.setContentView(popupView);

                popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

                ImageButton returnButton = popupView.findViewById(R.id.returnButton);

                returnButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
            }

        });

        ImageButton info = findViewById(R.id.infoIcon);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonShowPopupWindowClick(v);
            }
        });

        Button button2 = findViewById(R.id.button2);
        accelerometer = new Accelerometer(this);

        scoreView = (TextView) this.findViewById(R.id.score);

        highScoreView = (TextView) this.findViewById(R.id.highScore);

        highScoreText = (TextView) this.findViewById(R.id.highScorePop);

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
                    rightCounter = 0;
                    leftCounter = 0;
                }
                button2.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        //När man släpper kärme
                        if(event.getAction()==MotionEvent.ACTION_UP){
                            MediaPlayer mediaPlayer = MediaPlayer.create(BowlingActivity.this, R.raw.rolling);
                            mediaPlayer.setVolume(1.0f, 1.0f);
                            mediaPlayer.start();
                            vibe.vibrate(80);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mediaPlayer.stop();
                                    mediaPlayer.release();
                                }
                            }, 1500);
                            checkScore();
                            button2.setEnabled(false);
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    button2.setEnabled(true);
                                }
                            }, 3000);
                            strikeCounter=0;
                            leftCounter=0;
                            rightCounter=0;


                            if(Throw == 2 || pinsDown == 6){
                                Throw = 0;
                                score = score + pinsDown;
                                pinsDown = 0;
                                roundCount -= 1;
                                //Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        resetGame();
                                    }
                                }, 2000);
                            }

                            if(roundCount < 1){
                                if (score > highScore) {
                                    highScore = score;
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putInt("highScore", highScore);
                                    editor.apply(); // kanske editor.commit()
                                    highScoreView.animate()
                                            .scaleX(1.5f)
                                            .scaleY(1.5f)
                                            .setDuration(800)
                                            .withEndAction(() -> highScoreView.animate().scaleX(1f).scaleY(1f).setDuration(500));
                                    crown.animate()
                                            .scaleX(1.5f)
                                            .scaleY(1.5f)
                                            .setDuration(500)
                                            .withEndAction(() -> {
                                                crown.animate()
                                                        .scaleX(1f)
                                                        .scaleY(1f)
                                                        .setDuration(800)
                                                        .start();
                                            });

                                }
                                Throw = 0;
                                score = 0;
                                pinsDown = 0;
                                roundCount = 10;
                                resetGame();
                                //display score och berätta för användaren att rundan är över
                            }

                            scoreView.setText("Score: " + score * 100);
                            highScoreView.setText("" + highScore * 100);


                            Log.d("accelerometer",valuesText);
                            Log.d("counter", Integer.toString(strikeCounter));
                            Log.d("throw", Integer.toString(Throw));
                            Log.d("pins", Integer.toString(pinsDown));
                            Log.d("SCORE", Integer.toString(score));
                            Log.d("Highscore", Integer.toString(highScore));
                            return true;
                        }

                        //När man trycker på kärmen, reset både pins och boll
                        if(event.getAction()==MotionEvent.ACTION_DOWN){
                            resetBall(orangeBall);
                            Log.d("SPECIALPINSPECIAL", Integer.toString(pinsDown));
                            Throw++;
                            countBalls();
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



        kagla6 = (ImageView) findViewById(R.id.kagla6);
        kagla5 = (ImageView) findViewById(R.id.kagla5);
        kagla4 = (ImageView) findViewById(R.id.kagla4);
        kagla3 = (ImageView) findViewById(R.id.kagla3);
        kagla2 = (ImageView) findViewById(R.id.kagla2);
        kagla1 = (ImageView) findViewById(R.id.kagla1);
        one = (ImageView) findViewById(R.id.one);
        two = (ImageView) findViewById(R.id.two);
        three = (ImageView) findViewById(R.id.three);
        four = (ImageView) findViewById(R.id.four);
        five = (ImageView) findViewById(R.id.five);
        six = (ImageView) findViewById(R.id.six);
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


        Button throwButton = findViewById(R.id.bowlingball);
        orangeBall = (ImageView) findViewById(R.id.orangeBall);
        throwButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                MediaPlayer mediaPlayer = MediaPlayer.create(BowlingActivity.this, R.raw.rolling);
                mediaPlayer.start();
                throwBallStrike(orangeBall);
                vibe.vibrate(80);
            }
        });

    }

    private void checkScore() {
        strikePopup = (ImageView) findViewById(R.id.strikepopup);
        sparePopup = (ImageView) findViewById(R.id.sparepopup);
        // countBalls();

        if (strikeCounter > 3) {    //STRIKE!
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    throwBallStrike(orangeBall);
                }
            }, 400);

            pinsDown = 6;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    kaglaFall(kagla1);
                    kaglaFall(kagla2);
                    kaglaFall(kagla3);
                    kaglaFall(kagla4);
                    kaglaFall(kagla5);
                    kaglaFall(kagla6);
                    one.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                    two.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                    three.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                    four.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                    five.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                    six.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                    vibe.vibrate(500);
                    MediaPlayer mediaPlayer = MediaPlayer.create(BowlingActivity.this, R.raw.strike);
                    mediaPlayer.start();

                    if(kagla2.getRotation() != 0 && kagla4.getRotation() != 0 || kagla3.getRotation() != 0 && kagla6.getRotation() != 0){
                        sparePopup.setScaleX(0.1f);
                        sparePopup.setScaleY(0.1f);
                        sparePopup.setVisibility(View.VISIBLE);
                        sparePopup.animate()
                                .setDuration(700)
                                .scaleX(1.0f)
                                .scaleY(1.0f)
                                .withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Keep the ImageView visible for two seconds before hiding it
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                sparePopup.setVisibility(View.GONE);
                                            }
                                        }, 2000);
                                    }
                                })
                                .start();
                    }else {
                        strikePopup.setScaleX(0.1f);
                        strikePopup.setScaleY(0.1f);
                        strikePopup.setVisibility(View.VISIBLE);
                        strikePopup.animate()
                                .setDuration(700)
                                .scaleX(1.0f)
                                .scaleY(1.0f)
                                .withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Keep the ImageView visible for two seconds before hiding it
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                strikePopup.setVisibility(View.GONE);
                                            }
                                        }, 2000);
                                    }
                                })
                                .start();
                    }

                }
            }, 1000);
            strikeCounter = 0;
        }

        else if (leftCounter > rightCounter && leftCounter > 3) {   //3 i mitten trillar
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    throwBallLeft(orangeBall);
                }
            }, 400);


            //Handler handler = new Handler();
            pinsDown = 3;

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    kaglaFall(kagla1);
                    kaglaFall(kagla2);
                    kaglaFall(kagla4);
                    kaglaFall(kagla5);

                    two.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                    four.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                    six.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                    vibe.vibrate(500);
                    MediaPlayer mediaPlayer = MediaPlayer.create(BowlingActivity.this, R.raw.strike);
                    mediaPlayer.start();

                    if(kagla3.getRotation() != 0 && kagla6.getRotation() != 0){
                        sparePopup.setScaleX(0.1f);
                        sparePopup.setScaleY(0.1f);
                        sparePopup.setVisibility(View.VISIBLE);
                        sparePopup.animate()
                                .setDuration(700)
                                .scaleX(1.0f)
                                .scaleY(1.0f)
                                .withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Keep the ImageView visible for two seconds before hiding it
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                sparePopup.setVisibility(View.GONE);
                                            }
                                        }, 2000);
                                    }
                                })
                                .start();
                    }
                }
            }, 1000);
            leftCounter = 0;
        }
        else if (rightCounter >= leftCounter && rightCounter > 3) {
            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    throwBallRight(orangeBall);
                }
            }, 400);

            //throwBallRight(orangeBall);
            //Handler handler = new Handler();
            pinsDown = 3;

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    kaglaFall(kagla1);
                    kaglaFall(kagla3);
                    kaglaFall(kagla6);
                    one.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                    three.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                    six.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                    vibe.vibrate(500);
                    MediaPlayer mediaPlayer = MediaPlayer.create(BowlingActivity.this, R.raw.strike);
                    mediaPlayer.start();


                    if(kagla2.getRotation() != 0 && kagla4.getRotation() != 0){
                        sparePopup.setScaleX(0.1f);
                        sparePopup.setScaleY(0.1f);
                        sparePopup.setVisibility(View.VISIBLE);
                        sparePopup.animate()
                                .setDuration(700)
                                .scaleX(1.0f)
                                .scaleY(1.0f)
                                .withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Keep the ImageView visible for two seconds before hiding it
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                sparePopup.setVisibility(View.GONE);
                                            }
                                        }, 2000);
                                    }
                                })
                                .start();
                    }
                }
            }, 1000);
            rightCounter = 0;
        } else{
            if(leftCounter > rightCounter){ //MISS
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        throwBallMissLeft(orangeBall);
                    }
                }, 400);

            }else{
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        throwBallMissRight(orangeBall);
                    }
                }, 400);

            }
        }
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                resetBall(orangeBall);
            }
        }, 3000);
    }
    private void onScore(float tx, float ty, float tz) {
        if(ty < -1 && tz > 1){
            if (Math.abs(tx) < 0.5) {
                strikeCounter ++; //strike
            }
            if ( tx > -1 && tx < 0) {
                leftCounter ++; //lite vänster?
            }
            if (tx < 1 && tx > 0) {
                rightCounter ++; // lite höger?
            }
        }


    }





    private void kaglaFall(ImageView kagla) {
        Random random = new Random();
        final float kaglarotation;
        if (kagla.getRotation() == 0.0) {
            if (random.nextBoolean()) {
                if (random.nextBoolean()) {
                    kaglarotation = kagla.getRotation() - 90f;
                } else {
                    kaglarotation = kagla.getRotation() - 120f;
                }
            } else {
                if (random.nextBoolean()) {
                    kaglarotation = kagla.getRotation() + 90f;
                } else {
                    kaglarotation = kagla.getRotation() + 120f;
                }
            }

            float kaglaplacement = kagla.getHeight() * 0.2f;
            kagla.animate()
                    .setDuration(300)
                    .rotation(kaglarotation)
                    .translationYBy(kaglaplacement)
                    .start();

            float saturationFactor = 0.5f; // Adjust as needed (0.0f for fully desaturated, 1.0f for original saturation)
        }
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

    private void resetGame(){
        resetPins();
        resetBall(orangeBall);
        one.clearColorFilter();
        two.clearColorFilter();
        three.clearColorFilter();
        four.clearColorFilter();
        five.clearColorFilter();
        six.clearColorFilter();
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


    private void countBalls() {
        if (roundCount == 10 && Throw == 1) {
            round1.clearColorFilter();
            round2.clearColorFilter();
            round3.clearColorFilter();
            round4.clearColorFilter();
            round5.clearColorFilter();
            round6.clearColorFilter();
            round7.clearColorFilter();
            round8.clearColorFilter();
            round9.clearColorFilter();
            round10.clearColorFilter();
            throw1.setVisibility(View.GONE);
            throw2.setVisibility(View.VISIBLE);
        } else if (roundCount == 10 && Throw == 2) {
            round1.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            throw1.setVisibility(View.VISIBLE);
        } else if (roundCount == 9 && Throw == 1) {
            round1.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            throw1.setVisibility(View.GONE);
        } else if (roundCount == 9 && Throw == 2) {
            round1.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round2.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            throw1.setVisibility(View.VISIBLE);
        } else if (roundCount == 8 && Throw == 1) {
            round1.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round2.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            throw1.setVisibility(View.GONE);
        } else if (roundCount == 8 && Throw == 2) {
            round1.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round2.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round3.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            throw1.setVisibility(View.VISIBLE);
        } else if (roundCount == 7 && Throw == 1) {
            round1.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round2.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round3.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            throw1.setVisibility(View.GONE);
        } else if (roundCount == 7 && Throw == 2) {
            round1.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round2.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round3.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round4.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            throw1.setVisibility(View.VISIBLE);
        } else if (roundCount == 6 && Throw == 1) {
            round1.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round2.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round3.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round4.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            throw1.setVisibility(View.GONE);
        } else if (roundCount == 6 && Throw == 2) {
            round1.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round2.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round3.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round4.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round5.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            throw1.setVisibility(View.VISIBLE);
        } else if (roundCount == 5 && Throw == 1) {
            round1.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round2.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round3.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round4.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round5.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            throw1.setVisibility(View.GONE);
        } else if (roundCount == 5 && Throw == 2) {
            round1.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round2.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round3.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round4.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round5.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round6.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            throw1.setVisibility(View.VISIBLE);
        } else if (roundCount == 4 && Throw == 1) {
            round1.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round2.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round3.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round4.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round5.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round6.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            throw1.setVisibility(View.GONE);
        } else if (roundCount == 4 && Throw == 2) {
            round1.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round2.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round3.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round4.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round5.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round6.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round7.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            throw1.setVisibility(View.VISIBLE);
        } else if (roundCount == 3 && Throw == 1) {
            round1.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round2.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round3.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round4.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round5.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round6.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round7.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            throw1.setVisibility(View.GONE);
        } else if (roundCount == 3 && Throw == 2) {
            round1.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round2.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round3.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round4.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round5.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round6.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round7.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round8.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            throw1.setVisibility(View.VISIBLE);
        } else if (roundCount == 2 && Throw == 1) {
            round1.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round2.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round3.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round4.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round5.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round6.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round7.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round8.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            throw1.setVisibility(View.GONE);
        } else if (roundCount == 2 && Throw == 2) {
            round1.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round2.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round3.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round4.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round5.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round6.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round7.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round8.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round9.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            throw1.setVisibility(View.VISIBLE);
        } else if (roundCount == 1 && Throw == 1) {
            round1.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round2.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round3.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round4.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round5.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round6.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round7.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round8.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            round9.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            throw1.setVisibility(View.GONE);
        } else if (roundCount == 1 && Throw == 2) {
            round1.clearColorFilter();
            round2.clearColorFilter();
            round3.clearColorFilter();
            round4.clearColorFilter();
            round5.clearColorFilter();
            round6.clearColorFilter();
            round7.clearColorFilter();
            round8.clearColorFilter();
            round9.clearColorFilter();
            round10.clearColorFilter();
            throw1.setVisibility(View.VISIBLE);
            throw2.setVisibility(View.VISIBLE);
            pointsPopup();
        } else {
            round1.clearColorFilter();
            round2.clearColorFilter();
            round3.clearColorFilter();
            round4.clearColorFilter();
            round5.clearColorFilter();
            round6.clearColorFilter();
            round7.clearColorFilter();
            round8.clearColorFilter();
            round9.clearColorFilter();
            round10.clearColorFilter();
            throw1.setVisibility(View.VISIBLE);
            throw2.setVisibility(View.VISIBLE);
        }
    }

    private void pointsPopup(){
        highScoreText.setText("Scorepooop:" + highScore * 100);
        highScoreText.setScaleX(0.1f);
        highScoreText.setScaleY(0.1f);
        highScoreText.setVisibility(View.VISIBLE);
        pointsPopup = (ImageView) findViewById(R.id.pointspopup);
        pointsPopup.setScaleX(0.1f);
        pointsPopup.setScaleY(0.1f);
        pointsPopup.setVisibility(View.VISIBLE);
        pointsPopup.animate()
                .setDuration(700)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        // Keep the ImageView visible for two seconds before hiding it
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pointsPopup.setVisibility(View.GONE);
                            }
                        }, 2000);
                    }
                })
                .start();
        highScoreText.animate()
                .setDuration(700)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        // Keep the ImageView visible for two seconds before hiding it
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                highScoreText.setVisibility(View.GONE);
                            }
                        }, 2000);
                    }
                })
                .start();

    }


}