package com.example.sortify;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sortify.R;

public class WhatToSortActivity extends AppCompatActivity {

    private EditText editTextItem;
    private Button buttonClassify, buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_to_sort);

        initializeViews();
        setupButtonListeners();
    }

    private void initializeViews() {
        editTextItem = findViewById(R.id.editTextItem);
        buttonClassify = findViewById(R.id.buttonClassify);
        buttonBack = findViewById(R.id.buttonBack);
    }

    private void setupButtonListeners() {
        buttonClassify.setOnClickListener(v -> performClassification());

        buttonBack.setOnClickListener(v -> {
            // Go back to previous screen (MainDashboardActivity)
            finish();
        });
    }

    private void performClassification() {
        String itemName = editTextItem.getText().toString().trim();

        if (TextUtils.isEmpty(itemName)) {
            Toast.makeText(this, "Please enter an item name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (itemName.length() < 2) {
            Toast.makeText(this, "Item name must be at least 2 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        navigateToResults(itemName);
    }

    private void navigateToResults(String itemName) {
        Intent intent = new Intent(WhatToSortActivity.this, SortResultActivity.class);
        intent.putExtra("item_name", itemName);
        startActivity(intent);
    }
}