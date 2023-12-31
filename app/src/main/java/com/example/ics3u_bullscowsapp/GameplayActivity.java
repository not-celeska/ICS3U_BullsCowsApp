package com.example.ics3u_bullscowsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

// Dont worry about that stuff..


public class GameplayActivity extends AppCompatActivity {

    // Correct number variables.
    int numDigitsInCorrectNumber;
    String correctNumber; // String is required for 0123 possibility.
    ArrayList<Character> correctNumberCharacters;

    // Guess number variables.
    String guess;
    TextView guessDisplay;

    // Feedback variables.
    int[] guessFeedback = new int[2];
    int BULLS = 0;
    int COWS = 1;

    // Misc.
    TextView errorMessageDisplay;
    TextView correctNumberShow; // will remove this
    TextView feedbackText;
    int totalGuesses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        // Initialize textfeilds, etc..
        guessDisplay = findViewById(R.id.guessDisplay);
        errorMessageDisplay = findViewById(R.id.errorMessageDisplay);
        errorMessageDisplay.setTextColor(Color.RED);
        correctNumberShow = findViewById(R.id.correctNumberDisplay);
        feedbackText = findViewById(R.id.feedbackText);
        feedbackText.setMovementMethod(new ScrollingMovementMethod());

        // Start the game.
        setupGameVariables();
    }

    // Start main game.
    public void setupGameVariables() {

        // Declare guess, generate
        numDigitsInCorrectNumber = getIntent().getIntExtra("numberOfDigits", 4);
        correctNumber = generateRandomNumber(numDigitsInCorrectNumber);
        correctNumberShow.setText("CORRECT NUMBER: " + correctNumber);
        totalGuesses = 0;
        guess = "";

        // in TUI, this is where the while loop would start..

    }

    public void addNumberToGuess(char number) {
        if (guess.length() < numDigitsInCorrectNumber) {
            // TODO: Check if the number has been entered before <-- give user a warning.
            guess += number;
            guessDisplay.setText(guess); // updates the text
            if (guess.length() == numDigitsInCorrectNumber) {
                errorMessageDisplay.setText("");
            }
            else {
                errorMessageDisplay.setText("* Needs to be " + (numDigitsInCorrectNumber - guess.length()) + " numbers longer");
            }
        }

    }

    public void submit(View view) {
        if (guess.length() == numDigitsInCorrectNumber) {

            // Process guess..
            guessFeedback = getGuessFeedback(); // Feedback is the breakfast of champions!!!
            feedbackText.append(formatFeedback(guessFeedback));

            // Guess aftermath..
            guessDisplay.setText("");
            guess = "";
            totalGuesses++;

            // win condition..
            if (guessFeedback[BULLS] == 4) {

            }


        } else {
            errorMessageDisplay.setText("Submission Error: \nNOT ENOUGH NUMBERS");
        }
    }

    public void backspace(View view) {
        if (guess.length() > 0) {
            guess = guess.substring(0, (guess.length() - 1)); // returns the string without the last character.
            guessDisplay.setText(guess); // updates the text
            if (!(guess.length() == numDigitsInCorrectNumber)) {
                errorMessageDisplay.setText("* Needs to be " + (numDigitsInCorrectNumber - guess.length()) + " numbers longer");
            }
        }
    }

    // == TODO: This is meh.. Try to make this easier..
    public void addOne(View view) {
        addNumberToGuess('1');
    }

    public void addTwo(View view) {
        addNumberToGuess('2');
    }

    public void addThree(View view) {
        addNumberToGuess('3');
    }

    public void addFour(View view) {
        addNumberToGuess('4');
    }

    public void addFive(View view) {
        addNumberToGuess('5');
    }

    public void addSix(View view) {
        addNumberToGuess('6');
    }

    public void addSeven(View view) {
        addNumberToGuess('7');
    }

    public void addEight(View view) {
        addNumberToGuess('8');
    }

    public void addNine(View view) {
        addNumberToGuess('9');
    }

    public void addZero(View view) {
        addNumberToGuess('0');
    }

    public String formatFeedback(int[] feedback) {
        return (totalGuesses + " | " + guess + " | " + feedback[BULLS] + "B" + feedback[COWS] + "C\n");
    }



    public String generateRandomNumber(int numDigits)
    {
        Random numberGenerator = new Random();
        String randomNumber = "";

        // 1. We will generate [numDigits] random numbers from 0-9.
        for (int i = 0; i < numDigits; i++)
        {
            int randomDigit;

            randomDigit = numberGenerator.nextInt(10);

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

        return randomNumber;
    }

    // GET GUESS FEEDBACK: Takes the input and correctNumber, returns bulls (right number and place) and cows (right number wrong place)
    public int[] getGuessFeedback()
    {
        int[] guessFeedback = {0, 0}; // Check "Array Indexing" class variables.

        ArrayList<Character> guessArray = stringToCharArray(guess);
        ArrayList<Character> correctArray = stringToCharArray(correctNumber); // this can be a class variable..

        // Go through the guessed array and compare it to the correct array.
        for (int digitIndex = 0; digitIndex < String.valueOf(guess).length(); digitIndex++)
        {
            // Is the guessed digit at the current index even in the correct number?
            if (correctArray.contains(guessArray.get(digitIndex)))
            {
                // Is the digit in the same spot as the digit in the correct number?
                if (guessArray.get(digitIndex) == correctArray.get(digitIndex))
                {
                    guessFeedback[BULLS]++;
                }
                else
                {
                    guessFeedback[COWS]++;
                }
            }
        }

        return guessFeedback;
    }

    public ArrayList<Character> stringToCharArray(String textString)
    {
        ArrayList<Character> characterArrayList = new ArrayList<>();

        for (int characterIndex = 0; characterIndex < textString.length(); characterIndex++)
        {
            characterArrayList.add(textString.charAt(characterIndex));
        }

        return characterArrayList;

    }

}