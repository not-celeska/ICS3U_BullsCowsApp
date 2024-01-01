// == FILE LOCATION ===================
package com.example.ics3u_bullscowsapp;

// == IMPORTS ==================================
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

// == GAME SETTINGS MENU ====================================
public class GameSettingsActivity extends AppCompatActivity {

    SeekBar numDigitsSlider;
    TextView numDigitsDisplay;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        // [LAYOUT] Default layout creation.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);

        // [VIEWS] This is the display which says how many digits there are.
        numDigitsDisplay = findViewById(R.id.numberOfDigits);

        // [VIEWS] Initialize and configure slider.

        // Initialize slider.
        numDigitsSlider = findViewById(R.id.numDigitsSlider);

        // Set progress, maximum and minimum value.
        numDigitsSlider.setKeyProgressIncrement(1);
        numDigitsSlider.setMax(9); // Is actually 8.
        numDigitsSlider.setMin(3); // Is actually 2.

        // Setup logic for when the slider is changed.
        numDigitsSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int notchValue = 1;

                int alignedProgress = Math.round((float) progress / notchValue) * notchValue;

                // Set the digits
                numDigitsDisplay.setText("" + (alignedProgress - 1));

                seekBar.setProgress(alignedProgress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void returnToMainMenu(View view) {
        finish();
    }

    public void goToGameplayScreen(View view) {
        Intent intent = new Intent(this, GameplayActivity.class);
        intent.putExtra("numberOfDigits", Integer.parseInt(String.valueOf(numDigitsDisplay.getText())));
        startActivity(intent);
        finish();
    }
}