// == FILE LOCATION ===================
package com.example.ics3u_bullscowsapp;

// == IMPORTS ==================================
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

// == GAMEPLAY SCREEN ===================================
public class GameplayActivity extends AppCompatActivity {

    // ==================================
    // == CLASS VARAIABLES [FIELDS] =====
    // ==================================

    // [FEILD] User stats.
    private int totalGuesses;
    private long startTime;
    private long endTime;

    // [FEILD] Correct number variables.
    private int numDigitsInCorrectNumber;
    private String correctNumber; // String is required for "0123" possibility.
    private ArrayList<Character> correctNumberCharacters;

    // [FEILD] Guess number variables.
    private TextView guessDisplay;
    private String guess;

    // [FEILD] Feedback variables.
    private int[] guessFeedback = new int[2]; // [CLARITY] Where the feedback will be stored.
    private final int BULLS = 0; // [CLARITY] This is just for the code to be more readable.
    private final int COWS = 1; // [CLARITY] This is just for the code to be more readable.

    // [FEILD] Miscellanious.
    private TextView resultsDisplay;
    private TextView errorMessageDisplay;
    private TextView correctNumberShow; // TODO: remove this =====
    private TextView feedbackText;

    // ==================================
    // == CLASS METHODS [FUNCTIONS] =====
    // ==================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // [LAYOUT] Default layout creation.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        // [SETUP] Initialize textfeilds, etc..
        setupViews();

        // [SETUP] Start the game.
        setupGameVariables();

    }

    // [SETUP] Initialize TextViews, etc.
    private void setupViews() {

        guessDisplay = findViewById(R.id.guessDisplay);
        errorMessageDisplay = findViewById(R.id.errorMessageDisplay);
        errorMessageDisplay.setTextColor(Color.RED);
        correctNumberShow = findViewById(R.id.correctNumberDisplay);
        resultsDisplay = findViewById(R.id.resultsDisplay);
        feedbackText = findViewById(R.id.feedbackText);
        feedbackText.setMovementMethod(new ScrollingMovementMethod());

    }

    // [SETUP] Setup the main game variables.
    private void setupGameVariables() {

        // [CLARITY] Takes the passed 'numberOfDigits' from the GameSettings class.
        numDigitsInCorrectNumber = getIntent().getIntExtra("numberOfDigits", 4);
        correctNumber = generateRandomNumber(numDigitsInCorrectNumber);
        //        getCorrectNumber();


        // TODO | == TO REMOVE =======================================
        correctNumberShow.setText("CORRECT NUMBER: " + correctNumber);
        // TODO | == TO REMOVE =======================================

        // [STATS] Initializes user stats.
        totalGuesses = 0;
        guess = "";

        // [TIME] Takes note of start time; will be used later.
        startTime = System.currentTimeMillis();

        // [THOUGHT] In a TUI, this is where the while loop would start.

    }

    // ==================================
    // == GAMEPLAY METHODS ==============
    // ==================================

    private void getCorrectNumber() {
        // [CLARITY] If the passed 'twoPlayerMode' is true; if the two player mode checkbox was pressed.
        if (getIntent().getBooleanExtra("twoPlayerMode", false)) {
            // get a player to enter a number
            View numberEnterScreen = getLayoutInflater().inflate(R.layout.two_player_enter, null);
            TextView digitSpecification = numberEnterScreen.findViewById(R.id.twoPlayerDigits);
            digitSpecification.setText("ENTER A NUMBER [" + numDigitsInCorrectNumber + "] DIGITS: ");
            TextView warningDisplay = numberEnterScreen.findViewById(R.id.warningDisplay);
            EditText numberEnter = numberEnterScreen.findViewById(R.id.numberEnter);
            Button enterNumberButton = numberEnterScreen.findViewById(R.id.enterButton);
            Button cancelButton = numberEnterScreen.findViewById(R.id.cancelTwoPlayer);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            enterNumberButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String correctNumberProposal = String.valueOf(numberEnter.getText());
                    warningDisplay.setTextColor(Color.RED);
                    warningDisplay.setText("WARNINGS:\n");

                    if (correctNumberProposal.length() != numDigitsInCorrectNumber) {
                        warningDisplay.append(" - NUMBER NEEDS TO HAVE " + numDigitsInCorrectNumber + " DIGITS\n");

                        if (!allDigitsAreUnique(correctNumberProposal)) {
                            warningDisplay.append(" - ALL DIGITS MUST BE UNIQUE\n");
                        }
                    }
                    else if (!allDigitsAreUnique(correctNumberProposal)) {
                        warningDisplay.append(" - ALL DIGITS MUST BE UNIQUE\n");

                        if (correctNumberProposal.length() != numDigitsInCorrectNumber) {
                            warningDisplay.append(" - NUMBER NEEDS TO HAVE " + numDigitsInCorrectNumber + " DIGITS\n");
                        }
                    }
                    else
                    {
                        correctNumber = correctNumberProposal;
                        setContentView(R.layout.activity_gameplay);
                    }
                }
            });

            setContentView(numberEnterScreen);
        }
        else { // i.e., singleplayer mode.
            correctNumber = generateRandomNumber(numDigitsInCorrectNumber);
        }

    }

    // Parameters: Any symbol | Only used for numbers; could be used for emoji's and such in the future.
    // Description: Adds a number to the guess after checking a few conditions.
    private void addSymbolToGuess(char symbol) {

        // TODO: Check if the number has been entered before <-- give user a warning.

        // [CLARITY] Is there already enough digits in the guess?
        if (guess.length() < numDigitsInCorrectNumber) {

            guess += symbol; // [CLARITY] Concatenates the chosen number to the guess.
            guessDisplay.setText(guess); // [CLARITY] Updates the display.

            // [CLARITY] Just a warning which tells the user whether they need more numbers or not.
            if (guess.length() == numDigitsInCorrectNumber) {
                errorMessageDisplay.setText("");
            }
            else {
                errorMessageDisplay.setText("* Needs to be " + (numDigitsInCorrectNumber - guess.length()) + " numbers longer");
            }
        }

    }

    // Parameters: [N / A] | Uses access to feilds.
    // Description: Calculates score, shows user their stats.
    private void userWinsGame() {

        // [TIME] Takes note of end time; then processes it.
        endTime = System.currentTimeMillis();
        int secondsElapsed = Math.round((endTime - startTime) / 1000);
        String formattedTime = formatTime(secondsElapsed);

        // [SCORE] Scuffly calculates the score based off: Guesses needed, time needed (in seconds), and number of digits.
        int score = (int) (((15.0 / (1.0 * totalGuesses)) + (10.0 / (1.0 * secondsElapsed))) * 200.0 * (numDigitsInCorrectNumber - 1));

        // [WIN SCREEN] Initialize the win screen with text and all.
        View winScreen = getLayoutInflater().inflate(R.layout.win_screen, null);
        TextView results = winScreen.findViewById(R.id.resultsDisplay);
        results.setText("CORRECT NUMBER: " + correctNumber + "\nGuesses: " + totalGuesses + "\nTime Taken: " + formattedTime + "\nSCORE: " + score);

        // [BUTTON] Add a function to the 'OKAY!' button; to close the gameplay and go back to main menu.
        winScreen.findViewById(R.id.gameExitButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // [CLARITY] Go back to the gameplay screen.
                setContentView(R.layout.activity_gameplay);

                // [CLARITY] Close the gameplay screen.
                finish();

                // [CLARITY] All the values reset because we 'close' this activity here.
            }

        });

        // [TOGGLE SCREEN] This doesnt actually change the screen; just overlays it.
        setContentView(winScreen);

    }

    // Parameters: Seconds
    // Description: Formats the time to minutes and seconds.
    private String formatTime(int seconds) {

        // [CLARITY] If there's only seconds, it will look like "#s".
        if (seconds < 60) {
            return (seconds + "s"); // [EXAMPLE] | "23s"
        }

        // [CLARITY] If there's both minutes and seconds, it will look like "#m #s".
        else
        {
            return (seconds / 60) + "m " + (seconds % 60) + "s"; // [EXAMPLE] | "1m 23s"
        }

    }

    // Parameters: The feedback given in a int array.
    // Description: Formats the feedback to look presentable.
    private String formatFeedback(int[] feedback) {
        return (totalGuesses + " | " + guess + " | " + feedback[BULLS] + "B" + feedback[COWS] + "C\n");
    }

    // Parameters: The number of digits (which the user chose).
    // Description: Generates a random number with the number of digits given.
    private String generateRandomNumber(int numDigits) {
        Random numberGenerator = new Random();
        String randomNumber = "";

        // [THOUGHT] Doing each digit individually allows for:
        // 1. Numbers to start with 0. Example | "0123".
        // 2. More 'random' numbers than if generating a number from 1000 to 9999.

        // [THOUGHT] Having each digit be unique allows for:
        // 1. Better user experience.

        // [LOOP] Loops through each digit generation numDigit times.
        for (int i = 0; i < numDigits; i++)
        {
            int randomDigit;

            randomDigit = numberGenerator.nextInt(10);

            // [CLARITY] This checks whether the new randomDigit already appears in the generating number.
            if (randomNumber.contains(String.valueOf(randomDigit)))
            {
                numDigits++; // [CLARITY] This will have the for loop go on one more time.
            }
            else
            {
                randomNumber += randomDigit; // Appends this random digit to the randomnumber.
            }
        }

        return randomNumber;

    }

    // Parameters: [N / A] | Uses access to feilds.
    // Description: Compares the guess to the correct number and returns the Bulls and Cows.
    public int[] getGuessFeedback() {

        int[] guessFeedback = {0, 0};

        // [LOOP] Go through the each digit and compare between the guess and correct numbers.
        for (int digitIndex = 0; digitIndex < String.valueOf(guess).length(); digitIndex++)
        {
            // [CONDITION] Is the guessed digit even in the correct number?
            if (correctNumber.contains(String.valueOf(guess.charAt(digitIndex))))
            {
                // [CONDITION] Is the digit in the same spot as the digit in the correct number?
                if (guess.charAt(digitIndex) == correctNumber.charAt(digitIndex))
                {
                    guessFeedback[BULLS]++; // [REMINDER] Bulls refers to the value at index 0.
                }
                else
                {
                    guessFeedback[COWS]++; // [REMINDER] Cows refers to the value at index 1.
                }
            }
        }

        return guessFeedback;

    }

    // ALL DIGITS ARE UNIQUE: Checks that all the digits in a number are unique - this will help not confuse the player in the feedback.
    public boolean allDigitsAreUnique(String number)
    {
        // Go through the whole list to compare each number individually.
        for (int digitIndex = 0; digitIndex < number.length(); digitIndex++)
        {
            // Get the digit itself and set how many times it was found to 0.
            int currentDigit = Character.getNumericValue(number.charAt(digitIndex));
            int timesDigitFound = 0;

            // Go through the list again to determine how many times this digit appears in the number.
            for (int allDigits = 0; allDigits < number.length(); allDigits++)
            {
                // If the digit appeared in the number.
                if (currentDigit == Character.getNumericValue(number.charAt(allDigits)))
                {
                    timesDigitFound ++;
                }
            }

            // If the digit ended up showing more than once.
            if (timesDigitFound > 1)
            {
                return false;
            }

        }

        return true;
    }


    // ==================================
    // == KEYPAD METHODS ================
    // ==================================

    // Parameters: [N / A] | Uses access to feilds.
    // Description: When the submit button is pressed, the guess is processed.
    public void submit(View view) {

        // [CLARITY] Checks if there's enough digits in the guess to process it.
        if (guess.length() == numDigitsInCorrectNumber) {

            // [FEEDBACK] Gets the feedback & Writes it in the feedbackText.
            guessFeedback = getGuessFeedback(); // [THOUGHT] "Feedback is the breakfast of champions!"
            feedbackText.append(formatFeedback(guessFeedback));

            totalGuesses++; // [CLARITY] Updates the total guesses took.

            // [WIN CONDITION] Calls on the win condition.
            if (guessFeedback[BULLS] == numDigitsInCorrectNumber) {
                userWinsGame();
            }

            // [RESET] Reset the guess for next turn.
            guessDisplay.setText("");
            guess = "";


        }
        else {
            errorMessageDisplay.setText("Submission Error: \nNOT ENOUGH NUMBERS");
        }

    }

    // Parameters: [N / A] | Uses access to feilds.
    // Description: If the user makes a mistake, they can remove the last digit in their guess.
    public void backspace(View view) {

        // [CLARITY] Checks that the guess has more than 0 numbers in the guess.
        if (guess.length() > 0) {

            // [CLARITY] Updates the current guess without its last digit.
            guess = guess.substring(0, (guess.length() - 1));
            guessDisplay.setText(guess);

            // [CLARITY] Will update the guess length warning.
            if (!(guess.length() == numDigitsInCorrectNumber)) {
                errorMessageDisplay.setText("* Needs to be " + (numDigitsInCorrectNumber - guess.length()) + " numbers longer");
            }
        }

    }

    // Parameters: [N / A] | Needs no parameters.
    // Description: If the user decides to quit the round, they can press this button.
    public void quit(View view) {
        finish();
    }


    // Parameters: [N / A] | Button-input based function.
    // Description: When a numbered button is pressed, it will add the number to the guess.
    public void addOne(View view) {
        addSymbolToGuess('1');
    }
    public void addTwo(View view) {
        addSymbolToGuess('2');
    }
    public void addThree(View view) {
        addSymbolToGuess('3');
    }
    public void addFour(View view) {
        addSymbolToGuess('4');
    }
    public void addFive(View view) {
        addSymbolToGuess('5');
    }
    public void addSix(View view) {
        addSymbolToGuess('6');
    }
    public void addSeven(View view) {
        addSymbolToGuess('7');
    }
    public void addEight(View view) {
        addSymbolToGuess('8');
    }
    public void addNine(View view) {
        addSymbolToGuess('9');
    }
    public void addZero(View view) {
        addSymbolToGuess('0');
    }

    // [NOTE] The reasons why there are 10 separate methods for this is because:
    // 1. Parameters are illegal in the OnClick attribute in buttons.
    // 2. Having a list with the initialized button ids and assigning ActionListeners manually is less efficient.

}