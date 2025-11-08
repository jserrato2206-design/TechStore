package com.techstore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.techstore.database.DatabaseHelper;
import com.techstore.models.User;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnBack;
    private TextView tvRegisterLink, tvForgotPassword;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnBack = findViewById(R.id.btnBack);
        tvRegisterLink = findViewById(R.id.tvRegisterLink);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        dbHelper = new DatabaseHelper(this);
    }

    private void setupClickListeners() {
        btnLogin.setOnClickListener(v -> performLogin());
        
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        });
        
        tvRegisterLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        
        tvForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    private void performLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (validateInputs(email, password)) {
            if (authenticateUser(email, password)) {
                saveUserSession(email);
                Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                
                Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, getString(R.string.invalid_credentials), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateInputs(String email, String password) {
        boolean isValid = true;

        if (TextUtils.isEmpty(email)) {
            etEmail.setError(getString(R.string.field_required));
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.invalid_email));
            isValid = false;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError(getString(R.string.field_required));
            isValid = false;
        } else if (password.length() < 6) {
            etPassword.setError(getString(R.string.password_too_short));
            isValid = false;
        }

        return isValid;
    }

    private boolean authenticateUser(String email, String password) {
        return dbHelper.validateUser(email, password);
    }

    private void saveUserSession(String email) {
        User user = dbHelper.getUserByEmail(email);
        if (user != null) {
            SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("user_id", user.getId());
            editor.putString("user_name", user.getName());
            editor.putString("user_email", user.getEmail());
            editor.putString("user_phone", user.getPhone());
            editor.putString("user_role", user.getRole());
            editor.putBoolean("is_logged_in", true);
            editor.apply();
        }
    }
}
