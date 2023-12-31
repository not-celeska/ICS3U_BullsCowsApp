package com.example.ics3u_bullscowsapp;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

// Dont worry about that stuff..


public class GameplayActivity extends AppCompatActivity {

    // Correct number variables.
    int numDigitsInCorrectNumber;
    String correctNumber; // String is required for 0123 possibility.

    // Guess number variables.
    String guess;
    TextView guessDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        // Initialize textfeilds, etc..
        TextView correctNumberShow = findViewById(R.id.correctNumberDisplay); // will remove this
        guessDisplay = findViewById(R.id.guessDisplay);


        numDigitsInCorrectNumber = getIntent().getIntExtra("numberOfDigits", 4);
        guess = "";

    }

    // Start main game.
    public void startGame(){

    }

    public void addNumberToGuess(char number)
    {
        if (guess.length() < numDigitsInCorrectNumber)
        {
            // TODO: Check if the number has been entered before <-- give user a warning.
            guess += number;
            guessDisplay.setText(guess); // updates the text
        }
    }

    public void backspace(View view) {
        if (guess.length() > 0) {
            guess = guess.substring(0, (guess.length() - 1)); // returns the string without the last character.
            guessDisplay.setText(guess); // updates the text
        }
    }

    // == TODO: This is meh.. Try to make this easier..
    public void addOne(View view) { addNumberToGuess('1'); }
    public void addTwo(View view) { addNumberToGuess('2'); }
    public void addThree(View view) { addNumberToGuess('3'); }
    public void addFour(View view) { addNumberToGuess('4'); }
    public void addFive(View view) { addNumberToGuess('5'); }
    public void addSix(View view) { addNumberToGuess('6'); }
    public void addSeven(View view) { addNumberToGuess('7'); }
    public void addEight(View view) { addNumberToGuess('8'); }
    public void addNine(View view) { addNumberToGuess('9'); }
    public void addZero(View view) { addNumberToGuess('0'); }




}