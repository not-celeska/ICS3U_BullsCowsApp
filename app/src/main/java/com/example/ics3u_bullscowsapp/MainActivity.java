/*
[NAME] MISHA BUSHKOV
[TASK] ICS3U SUMMATIVE
[DATE] 01 / 22 / 2024
 */

// == FILE LOCATION ===================
package com.example.ics3u_bullscowsapp;

// == IMPORTS ==================================
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

// == MAIN MENU =====================================
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // [LAYOUT] Default layout creation.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    // ==================================
    // == MENU BUTTON FUNCTIONS =========
    // ==================================

    // [MENU] Info menu.
    public void goToInfoMenu(View view) {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    // [MENU] Game Settings.
    public void goToGameSettings(View view) {
        Intent intent = new Intent(this, GameSettingsActivity.class);
        startActivity(intent);
    }
}