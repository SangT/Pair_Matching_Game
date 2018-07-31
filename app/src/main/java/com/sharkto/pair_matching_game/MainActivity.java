package com.sharkto.pair_matching_game;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private ImageButton[] imageButtons = new ImageButton[36];
    private ImagePair[] imagePairs = new ImagePair[18];
    private static MyButton[] myButtons = new MyButton[36];
    private ArrayList<Integer> randomList = new ArrayList<>();
    private static boolean firstClicked;
    private static boolean secondClicked;
    private static int firstButton = -2;
    private static int secondButton = -2;
    private Button btnRestart;
    private Button btnStart;
    private Button btnPause;
    private Button btnExit;
    private long lastPause;
    private LinearLayout game;
    private static int countCompare = 0;
    private static Chronometer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialButtons();
        initialPairs();
        // set ImageDefault for 36 buttons
        for (int i = 0; i < 36; i++) {
            myButtons[i] = new MyButton(imageButtons[i], R.drawable.questionmark);
            myButtons[i].getImageButton().setClickable(false);
            Toast.makeText(this, "Please click START to play!", Toast.LENGTH_LONG).show();
        }

        game = findViewById(R.id.game);
        timer = findViewById(R.id.cmtimer);
        btnStart = findViewById(R.id.btn_start);
        btnPause = findViewById(R.id.btn_pause);
        btnRestart = findViewById(R.id.btn_restart);
        btnExit = findViewById(R.id.btn_exit);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastPause != 0) {
                    timer.setBase(timer.getBase() + SystemClock.elapsedRealtime() - lastPause);
                    continueGame();
                } else {
                    timer.setBase(SystemClock.elapsedRealtime());
                    startGame();
                }

                timer.start();
                btnStart.setEnabled(false);
                btnPause.setEnabled(true);
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastPause = SystemClock.elapsedRealtime();
                timer.stop();
                pauseGame();
                btnPause.setEnabled(false);
                btnStart.setEnabled(true);
            }
        });

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.stop();
                timer.setBase(SystemClock.elapsedRealtime());
                lastPause = 0;
                btnPause.setEnabled(true);
                btnStart.setEnabled(false);
                startGame();
                timer.start();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initialButtons() {
        imageButtons[0] = findViewById(R.id.btn1);
        imageButtons[1] = findViewById(R.id.btn2);
        imageButtons[2] = findViewById(R.id.btn3);
        imageButtons[3] = findViewById(R.id.btn4);
        imageButtons[4] = findViewById(R.id.btn5);
        imageButtons[5] = findViewById(R.id.btn6);
        imageButtons[6] = findViewById(R.id.btn7);
        imageButtons[7] = findViewById(R.id.btn8);
        imageButtons[8] = findViewById(R.id.btn9);
        imageButtons[9] = findViewById(R.id.btn10);
        imageButtons[10] = findViewById(R.id.btn11);
        imageButtons[11] = findViewById(R.id.btn12);
        imageButtons[12] = findViewById(R.id.btn13);
        imageButtons[13] = findViewById(R.id.btn14);
        imageButtons[14] = findViewById(R.id.btn15);
        imageButtons[15] = findViewById(R.id.btn16);
        imageButtons[16] = findViewById(R.id.btn17);
        imageButtons[17] = findViewById(R.id.btn18);
        imageButtons[18] = findViewById(R.id.btn19);
        imageButtons[19] = findViewById(R.id.btn20);
        imageButtons[20] = findViewById(R.id.btn21);
        imageButtons[21] = findViewById(R.id.btn22);
        imageButtons[22] = findViewById(R.id.btn23);
        imageButtons[23] = findViewById(R.id.btn24);
        imageButtons[24] = findViewById(R.id.btn25);
        imageButtons[25] = findViewById(R.id.btn26);
        imageButtons[26] = findViewById(R.id.btn27);
        imageButtons[27] = findViewById(R.id.btn28);
        imageButtons[28] = findViewById(R.id.btn29);
        imageButtons[29] = findViewById(R.id.btn30);
        imageButtons[30] = findViewById(R.id.btn31);
        imageButtons[31] = findViewById(R.id.btn32);
        imageButtons[32] = findViewById(R.id.btn33);
        imageButtons[33] = findViewById(R.id.btn34);
        imageButtons[34] = findViewById(R.id.btn35);
        imageButtons[35] = findViewById(R.id.btn36);

        btnRestart = findViewById(R.id.btn_restart);
    }

    private void initialPairs() {
        imagePairs[0] = new ImagePair(R.drawable.one, 0);
        imagePairs[1] = new ImagePair(R.drawable.two, 1);
        imagePairs[2] = new ImagePair(R.drawable.three, 2);
        imagePairs[3] = new ImagePair(R.drawable.four, 3);
        imagePairs[4] = new ImagePair(R.drawable.five, 4);
        imagePairs[5] = new ImagePair(R.drawable.six, 5);
        imagePairs[6] = new ImagePair(R.drawable.seven, 6);
        imagePairs[7] = new ImagePair(R.drawable.eight, 7);
        imagePairs[8] = new ImagePair(R.drawable.nine, 8);
        imagePairs[9] = new ImagePair(R.drawable.ten, 9);
        imagePairs[10] = new ImagePair(R.drawable.eleven, 10);
        imagePairs[11] = new ImagePair(R.drawable.twelve, 11);
        imagePairs[12] = new ImagePair(R.drawable.thirteen, 12);
        imagePairs[13] = new ImagePair(R.drawable.fourteen, 13);
        imagePairs[14] = new ImagePair(R.drawable.fifteen, 14);
        imagePairs[15] = new ImagePair(R.drawable.sixteen, 15);
        imagePairs[16] = new ImagePair(R.drawable.seventeen, 16);
        imagePairs[17] = new ImagePair(R.drawable.eighteen, 17);
    }

    // Random 18 numbers (0->17) then add to array of 36, each number loops only once.
    private void createRandomList() {
        // Make an array of 36 then make {0,0,1,1,...,17,17}
        // shuffle the numbers
        for (int i = 0; i < 18; i++) {
            randomList.add(i);
            randomList.add(i);
        }
        Collections.shuffle(randomList);
    }

    private void clearRandomList() {
        randomList.clear();
    }

    private void setRandomImage() {
        // have imagePairs, randomList, imageButtons
        for (int i = 0; i < randomList.size(); i++){
            for(ImagePair imagePair: imagePairs){
                if (randomList.get(i) == imagePair.getPairNumber()) {
                    myButtons[i].setImageId(imagePair.getImageId());
                    myButtons[i].setImageNumber(imagePair.getPairNumber());
                    myButtons[i].setClicked(false);
                    myButtons[i].setPaired(false);
                    myButtons[i].getImageButton().setClickable(true);
                    myButtons[i].getImageButton().setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private static void compareImages() {
        if(myButtons[firstButton].getImageNumber() == myButtons[secondButton].getImageNumber()) {
            myButtons[firstButton].getImageButton().setVisibility(View.INVISIBLE);
            myButtons[firstButton].getImageButton().setClickable(false);
            myButtons[firstButton].setClicked(false);
            myButtons[firstButton].setPaired(true);
            myButtons[secondButton].getImageButton().setVisibility(View.INVISIBLE);
            myButtons[secondButton].getImageButton().setClickable(false);
            myButtons[secondButton].setClicked(false);
            myButtons[secondButton].setPaired(true);
            setDefaultButton();
            countCompare++;
            if (countCompare == 18) {
                timer.stop();
            }
        }
    }

    private static void oneBtnClicked(int a) {
        if(!firstClicked) {
            firstClicked = true;
            firstButton = a;
            myButtons[a].getImageButton().setImageResource(myButtons[a].getImageId());
            myButtons[a].getImageButton().setClickable(false);
        }
    }

    private static void twoBtnClicked(int b) {
        if(firstClicked && !secondClicked && b != firstButton) {
            secondClicked = true;
            secondButton = b;
            myButtons[b].getImageButton().setImageResource(myButtons[b].getImageId());
            myButtons[b].getImageButton().setClickable(false);
            compareImages();
        }
    }

    private static void threeBtnClicked(int c) {
        if(firstClicked && secondClicked && c != firstButton && c != secondButton) {
            secondClicked = false;
            firstClicked = false;
            myButtons[firstButton].setClicked(false);
            myButtons[secondButton].setClicked(false);
            myButtons[firstButton].getImageButton().setImageResource(myButtons[firstButton].getImageDefault());
            myButtons[secondButton].getImageButton().setImageResource(myButtons[secondButton].getImageDefault());
            myButtons[firstButton].getImageButton().setClickable(true);
            myButtons[secondButton].getImageButton().setClickable(true);
            firstButton = c;
            secondButton = -2;
            oneBtnClicked(c);
        }
    }

    public static void playGame() {
        // rules: after clicked -> seClickable:False
        for (int i = 0 ; i < myButtons.length; i++) {
            if(!myButtons[i].isPaired()){
                if (!myButtons[i].isClicked()) {
                    myButtons[i].getImageButton().setImageResource(myButtons[i].getImageDefault());
                }else {
                    oneBtnClicked(i);
                    twoBtnClicked(i);
                    threeBtnClicked(i);
                }
            }
        }
    }

    private void startGame() {
        game.setVisibility(View.VISIBLE);
        clearRandomList();
        createRandomList();
        setRandomImage();
        setDefaultButton();
        playGame();
    }

    private void pauseGame() {
        for (int i = 0; i < myButtons.length; i++) {
            myButtons[i].getImageButton().setClickable(false);
        }
    }

    private void continueGame() {
        for (int i = 0; i < myButtons.length; i++) {
            myButtons[i].getImageButton().setClickable(true);
        }
    }

    private static void setDefaultButton(){
        firstClicked = false;
        secondClicked = false;
        firstButton = -2;
        secondButton = -2;
    }

}
