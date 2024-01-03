// == FILE LOCATION ===================
package com.example.ics3u_bullscowsapp;

// == IMPORTS ==================================
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

// == GAME SETTINGS MENU ====================================
public class GameSettingsActivity extends AppCompatActivity {

    TextView numDigitsDisplay;
    CheckBox twoPlayerToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        // [LAYOUT] Default layout creation.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);

        // [VIEWS] This is the display which says how many digits there are.
        numDigitsDisplay = findViewById(R.id.numberOfDigits);

        // [VIEWS] This is the two player checkbox toggle.
        twoPlayerToggle = findViewById(R.id.twoPlayerToggle);

        // [VIEWS] Initialize and configure slider.

        // Declare and initialize slider.
        SeekBar numDigitsSlider = findViewById(R.id.numDigitsSlider);

        // Set progress, maximum and minimum value.
        numDigitsSlider.setKeyProgressIncrement(1);
        numDigitsSlider.setMax(11); // Is actually 8.
        numDigitsSlider.setMin(2); // Is actually 2.
        numDigitsSlider.setProgress(5);

        // Setup logic for when the slider is changed.
        numDigitsSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                // Determine the current value at which the slider is at.
                int alignedProgress = Math.round((float) progress);

                // Update the digits display.
                numDigitsDisplay.setText(String.valueOf(alignedProgress - 1));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // [CLARITY] This is just an implemented function; it isn't used.
                // TODO: Add colours?
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // [CLARITY] This is just an implemented function; it isn't used.
            }
        });
    }

    // ==================================
    // == MENU BUTTON FUNCTIONS =========
    // ==================================

    // [MENU] Return to main menu; closes the activity.
    public void returnToMainMenu(View view) {
        finish();
    }

    // [MENU] Opens the gameplay screen; passes in number of digits.
    public void goToGameplayScreen(View view) {
        Intent intent = new Intent(this, GameplayActivity.class);
        intent.putExtra("twoPlayerMode", twoPlayerToggle.isChecked());
        intent.putExtra("numberOfDigits", Integer.parseInt(String.valueOf(numDigitsDisplay.getText())));
        startActivity(intent);

        // [CLARITY] When user closes the gameplay (via win or close activity), user goes to the main menu immediately.
        finish();
    }

}