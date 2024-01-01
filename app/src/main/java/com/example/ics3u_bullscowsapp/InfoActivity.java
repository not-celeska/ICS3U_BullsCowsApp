// == FILE LOCATION ===================
package com.example.ics3u_bullscowsapp;

// == IMPORTS ==================================
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

// == INFO MENU =====================================
public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // [LAYOUT] Default layout creation.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

    }

    // [MENU] Return to main menu; closes the activity.
     public void returnToMainMenu(View view) {
        finish();
    }

}