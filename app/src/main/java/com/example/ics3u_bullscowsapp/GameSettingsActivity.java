package com.example.ics3u_bullscowsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class GameSettingsActivity extends AppCompatActivity {

    SeekBar howManyDigits;
    TextView numDigits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);
        howManyDigits = findViewById(R.id.howManyDigits);
        howManyDigits.setKeyProgressIncrement(1);
        howManyDigits.setMax(9); // actually 8
        howManyDigits.setMin(3);

        numDigits = findViewById(R.id.numberOfDigits);

        howManyDigits.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int notchValue = 1;

                int alignedProgress = Math.round((float) progress / notchValue) * notchValue;

                numDigits.setText("" + (alignedProgress - 1));

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
        intent.putExtra("numberOfDigits", Integer.parseInt(String.valueOf(numDigits.getText())));
        startActivity(intent);
        finish();
    }
}