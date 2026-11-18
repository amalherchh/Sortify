package com.example.sortify;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sortify.R;
import com.example.sortify.models.User;
import com.example.sortify.utils.PreferencesManager;
import java.util.UUID;

public class AuthActivity extends AppCompatActivity {

    private Button buttonLoginTab, buttonRegisterTab;
    private LinearLayout loginForm, registerForm;
    private EditText editTextLoginEmail, editTextLoginPassword;
    private EditText editTextRegisterName, editTextRegisterEmail, editTextRegisterPassword, editTextConfirmPassword;
    private Button buttonLogin, buttonRegister;
    private CheckBox checkBoxTerms;
    private PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        preferencesManager = new PreferencesManager(this);

        // REMOVE THIS BLOCK - Don't auto-navigate to dashboard
        // if (preferencesManager.isLoggedIn()) {
        //     navigateToMainDashboard();
        //     return;
        // }

        initializeViews();
        setupTabToggle();

        buttonLogin.setOnClickListener(v -> performLogin());
        buttonRegister.setOnClickListener(v -> performRegistration());
    }
    private void initializeViews() {
        buttonLoginTab = findViewById(R.id.buttonLoginTab);
        buttonRegisterTab = findViewById(R.id.buttonRegisterTab);
        loginForm = findViewById(R.id.loginForm);
        registerForm = findViewById(R.id.registerForm);
        editTextLoginEmail = findViewById(R.id.editTextLoginEmail);
        editTextLoginPassword = findViewById(R.id.editTextLoginPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        editTextRegisterName = findViewById(R.id.editTextRegisterName);
        editTextRegisterEmail = findViewById(R.id.editTextRegisterEmail);
        editTextRegisterPassword = findViewById(R.id.editTextRegisterPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        checkBoxTerms = findViewById(R.id.checkBoxTerms);
    }

    private void setupTabToggle() {
        buttonLoginTab.setOnClickListener(v -> showLoginForm());
        buttonRegisterTab.setOnClickListener(v -> showRegisterForm());
    }

    private void showLoginForm() {
        loginForm.setVisibility(View.VISIBLE);
        registerForm.setVisibility(View.GONE);
        buttonLoginTab.setBackgroundColor(getResources().getColor(R.color.primary_green));
        buttonRegisterTab.setBackgroundColor(getResources().getColor(R.color.light_green));
        buttonLoginTab.setTextColor(getResources().getColor(android.R.color.white));
        buttonRegisterTab.setTextColor(getResources().getColor(R.color.primary_green));
    }

    private void showRegisterForm() {
        loginForm.setVisibility(View.GONE);
        registerForm.setVisibility(View.VISIBLE);
        buttonRegisterTab.setBackgroundColor(getResources().getColor(R.color.primary_green));
        buttonLoginTab.setBackgroundColor(getResources().getColor(R.color.light_green));
        buttonRegisterTab.setTextColor(getResources().getColor(android.R.color.white));
        buttonLoginTab.setTextColor(getResources().getColor(R.color.primary_green));
    }

    private void performLogin() {
        String email = editTextLoginEmail.getText().toString().trim();
        String password = editTextLoginPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            showError("Please enter email");
            return;
        }
        if (!isValidEmail(email)) {
            showError("Please enter valid email");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            showError("Please enter password");
            return;
        }
        if (password.length() < 6) {
            showError("Password must be at least 6 characters");
            return;
        }

        // **FIX: Check if user exists in SharedPreferences**
        if (!preferencesManager.isUserExists(email)) {
            showError("Account not found. Please register first.");
            return;
        }

        // **FIX: Verify password**
        if (!preferencesManager.verifyPassword(email, hashPassword(password))) {
            showError("Invalid password. Please try again.");
            return;
        }

        // Get existing user and login
        User user = preferencesManager.getUserByEmail(email);
        if (user != null) {
            preferencesManager.loginUser(user.getFullName());
            showSuccess("Login successful!");
            navigateToMainDashboard();
        } else {
            showError("Login failed. Please try again.");
        }
    }

    private void performRegistration() {
        String fullName = editTextRegisterName.getText().toString().trim();
        String email = editTextRegisterEmail.getText().toString().trim();
        String password = editTextRegisterPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();
        boolean termsAccepted = checkBoxTerms.isChecked();

        if (TextUtils.isEmpty(fullName)) {
            showError("Please enter full name");
            return;
        }
        if (fullName.length() < 2) {
            showError("Full name must be at least 2 characters");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            showError("Please enter email");
            return;
        }
        if (!isValidEmail(email)) {
            showError("Please enter valid email");
            return;
        }

        // **FIX: Check if email already registered**
        if (preferencesManager.isUserExists(email)) {
            showError("Email already registered. Please login.");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            showError("Please enter password");
            return;
        }
        if (password.length() < 6) {
            showError("Password must be at least 6 characters");
            return;
        }
        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match");
            return;
        }
        if (!termsAccepted) {
            showError("Please accept terms and conditions");
            return;
        }

        User user = new User(fullName, email, hashPassword(password));
        user.setUserId(UUID.randomUUID().toString());

        preferencesManager.saveUser(user);
        preferencesManager.loginUser(fullName);

        showSuccess("Account created successfully!");
        navigateToMainDashboard();
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    private String hashPassword(String password) {
        return Integer.toString(password.hashCode());
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void navigateToMainDashboard() {
        Intent intent = new Intent(AuthActivity.this, MainDashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}