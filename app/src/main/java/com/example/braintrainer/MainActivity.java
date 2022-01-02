package com.example.braintrainer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.gridlayout.widget.GridLayout;

import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView rdNumTextView;
    TextView staticsTextView;
    TextView timerTextView;
    TextView finishTextView;
    TextView startTextView;
    GridLayout gridLayout;
    Button playAgainButton;
    Button goButton;
    int counter;
    int correct;
    int result;
    int gameTime = 15000;
    SeekBar progressBar;
    CountDownTimer timer;

    public void checkAnswer(View view) {

        finishTextView.setVisibility(View.VISIBLE);
        Button selectedButton = (Button) view;

        if (selectedButton.getText().equals(Integer.toString(result))) {
            correct++;
            finishTextView.setText("Correct!");
            gameTime += 3000;
            if(gameTime > 30000) {
                gameTime = 30000;
            }
            timer.cancel();
            setTimer(gameTime);
        } else {
            finishTextView.setText("Wrong :(");
        }

        staticsTextView.setText(counter + "/" + correct);
        generateQuestion();

    }

    public void generateQuestion() {

        Button choice1 = (Button) findViewById(R.id.choice1);
        Button choice2 = (Button) findViewById(R.id.choice2);
        Button choice3 = (Button) findViewById(R.id.choice3);
        Button choice4 = (Button) findViewById(R.id.choice4);

        ArrayList<Button> buttons = new ArrayList<Button>(Arrays.asList(choice1, choice2, choice3, choice4));
        Random rd = new Random();
        int rd1 = rd.nextInt(100);
        int rd2 = rd.nextInt(100);
        rdNumTextView.setText(rd1 + " + " + rd2);

        result = rd1 + rd2;
        int randomNum = rd.nextInt(4);

        Button randomButton = buttons.get(randomNum);
        randomButton.setText(Integer.toString(result));
        buttons.remove(randomButton);

        for (Button choice : buttons) {
            int wrongAnswer = (result + rd.nextInt(20) - 20);
            if (wrongAnswer == result) {
                while (wrongAnswer == result) {
                    wrongAnswer = (result + rd.nextInt(20) - 20);
                }
            }
            choice.setText(Integer.toString(wrongAnswer));
        }


        counter++;
    }

    public void setTimer(int timeCount) {

        timer = new CountDownTimer(timeCount, 1000-500) {
            @Override
            public void onTick(long millisUntilFinished) {

                progressBar.setProgress(Integer.parseInt(String.valueOf(millisUntilFinished / 1000)));
                timerTextView.setText((int) (millisUntilFinished / 1000) + "");
                gameTime -= 500;
            }

            @Override
            public void onFinish() {

                playAgainButton.setVisibility(View.VISIBLE);
                finishTextView.setText("Times Up!");
                disableButtons(gridLayout);
            }
        };
        timer.start();
    }

    private void enableButtons(GridLayout layout) {

        for (int i = 0; i < layout.getChildCount(); i++) {
            Button child = (Button) layout.getChildAt(i);
            child.setEnabled(true);
        }
    }

    private void disableButtons(GridLayout layout) {

        ArrayList<View> layoutButtons = layout.getTouchables();

        for (View v : layoutButtons) {
            if (v instanceof Button) {
                ((Button) v).setEnabled(false);
            }
        }
    }

    public void goButton(View view) {


        goButton.setVisibility(View.INVISIBLE);
        startTextView.setVisibility(View.INVISIBLE);

        rdNumTextView.setVisibility(View.VISIBLE);
        staticsTextView.setVisibility(View.VISIBLE);
        timerTextView.setVisibility(View.VISIBLE);
        gridLayout.setVisibility(View.VISIBLE);
        finishTextView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        setTimer(gameTime);
        generateQuestion();


    }


    public void playAgain(View view) {

        gameTime = 15000;
        counter = 0;
        correct = 0;

        gridLayout.setEnabled(true);
        finishTextView.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);


        staticsTextView.setText(counter + "/" + correct);
        enableButtons(gridLayout);

        setTimer(gameTime);
        generateQuestion();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff5983")));

        rdNumTextView = (TextView) findViewById(R.id.rdNumTextView);
        staticsTextView = (TextView) findViewById(R.id.staticsTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        finishTextView = (TextView) findViewById(R.id.finishTextView);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        goButton = (Button) findViewById(R.id.goButton);
        progressBar = (SeekBar) findViewById(R.id.timerSeekBar);
        startTextView = (TextView) findViewById(R.id.startTextView);

        progressBar.setMax(30);

        rdNumTextView.setVisibility(View.INVISIBLE);
        staticsTextView.setVisibility(View.INVISIBLE);
        timerTextView.setVisibility(View.INVISIBLE);
        finishTextView.setVisibility(View.INVISIBLE);
        gridLayout.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        startTextView.setVisibility(View.VISIBLE);
        goButton.setVisibility(View.VISIBLE);


    }
}