package com.example.ics3u_bullscowsapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Random;

public class GameplayActivity extends AppCompatActivity {

    int numDigits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        TextView correctNumberShow = findViewById(R.id.correctNumberDisplay);
        numDigits = getIntent().getIntExtra("numberOfDigits", 4);

        // GAME START

        correctNumberShow.setText(generateRandomNumber(numDigits) + "");

    }

    public void startGame(){

    }

    // GENERATE RANDOM NUMBER: Just generates a random number with the length of numDigits.
    public static int generateRandomNumber(int numDigits)
    {
        Random numberGenerator = new Random();
        String randomNumber = "";

        // 1. We will generate [numDigits] random numbers from 0-9.
        for (int i = 0; i < numDigits; i++)
        {
            int randomDigit;

            // a) check i: 0123 cannot happen <-- conversion from string to integer will cause it to become 123.
//            if (i == 0)
//            {
//                randomDigit = numberGenerator.nextInt(1, 10)
//            }
//            else
//            {
                randomDigit = numberGenerator.nextInt(/*0, */10);
//            }

            // b) Make sure each number in the integer is unique; helps us out later on when checking Bulls and Cows.
            if (randomNumber.contains(String.valueOf(randomDigit)))
            {
                numDigits++; // This has the for loop go on one more time.
            }
            else
            {
                randomNumber += randomDigit; // Adds it to the random number.
            }
        }

        return Integer.parseInt(randomNumber);
    }

}