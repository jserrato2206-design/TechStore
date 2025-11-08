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

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPhone, etPassword, etConfirmPassword;
    private Button btnRegister, btnBack;
    private TextView tvLoginLink;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnBack = findViewById(R.id.btnBack);
        tvLoginLink = findViewById(R.id.tvLoginLink);
        dbHelper = new DatabaseHelper(this);
    }

    private void setupClickListeners() {
        btnRegister.setOnClickListener(v -> performRegister());
        
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        });
        
        tvLoginLink.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void performRegister() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (validateInputs(name, email, phone, password, confirmPassword)) {
            // Verificar si el email ya existe
            if (dbHelper.getUserByEmail(email) != null) {
                etEmail.setError("Este email ya está registrado");
                return;
            }
            
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPhone(phone);
            user.setPassword(password);
            
            long userId = dbHelper.insertUser(user);
            if (userId > 0) {
                user.setId((int) userId);
                saveUserSession(user);
                Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT).show();
                
                Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateInputs(String name, String email, String phone, String password, String confirmPassword) {
        boolean isValid = true;

        if (TextUtils.isEmpty(name)) {
            etName.setError(getString(R.string.field_required));
            isValid = false;
        }

        if (TextUtils.isEmpty(email)) {
            etEmail.setError(getString(R.string.field_required));
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.invalid_email));
            isValid = false;
        }

        if (TextUtils.isEmpty(phone)) {
            etPhone.setError(getString(R.string.field_required));
            isValid = false;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError(getString(R.string.field_required));
            isValid = false;
        } else if (password.length() < 6) {
            etPassword.setError(getString(R.string.password_too_short));
            isValid = false;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            etConfirmPassword.setError(getString(R.string.field_required));
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError(getString(R.string.passwords_dont_match));
            isValid = false;
        }

        return isValid;
    }

    private void saveUserSession(User user) {
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("user_id", (int) user.getId());
        editor.putString("user_name", user.getName());
        editor.putString("user_email", user.getEmail());
        editor.putString("user_phone", user.getPhone());
        editor.putString("user_role", user.getRole());
        editor.putBoolean("is_logged_in", true);
        editor.apply();
    }
}
