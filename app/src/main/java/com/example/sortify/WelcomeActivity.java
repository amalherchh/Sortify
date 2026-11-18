package com.example.sortify;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sortify.utils.PreferencesManager;

public class WelcomeActivity extends AppCompatActivity {

    private Button buttonGetStarted;
    private PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Initialize PreferencesManager
        preferencesManager = new PreferencesManager(this);

        // Initialize views
        buttonGetStarted = findViewById(R.id.buttonGetStarted);

        // Set up button listener
        buttonGetStarted.setOnClickListener(v -> {
            // Always go to AuthActivity (login/register screen)
            // Don't auto-login - user must choose to login or register
            navigateToAuth();
        });
    }

    /**
     * Navigate to authentication screen
     */
    private void navigateToAuth() {
        Intent intent = new Intent(WelcomeActivity.this, AuthActivity.class);
        startActivity(intent);
    }
}