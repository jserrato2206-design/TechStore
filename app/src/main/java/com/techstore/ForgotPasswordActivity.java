package com.techstore;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etRecoveryEmail;
    private Button btnSendRecovery, btnBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        etRecoveryEmail = findViewById(R.id.etRecoveryEmail);
        btnSendRecovery = findViewById(R.id.btnSendRecovery);
        btnBackToLogin = findViewById(R.id.btnBackToLogin);
    }

    private void setupClickListeners() {
        btnSendRecovery.setOnClickListener(v -> sendRecoveryEmail());
        
        btnBackToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void sendRecoveryEmail() {
        String email = etRecoveryEmail.getText().toString().trim();

        if (validateEmail(email)) {
            if (isValidUser(email)) {
                // Simular envío de email de recuperación
                Toast.makeText(this, getString(R.string.recovery_sent), Toast.LENGTH_LONG).show();
                
                // Volver al login después de 2 segundos
                new android.os.Handler().postDelayed(() -> {
                    Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }, 2000);
            } else {
                Toast.makeText(this, getString(R.string.user_not_found), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            etRecoveryEmail.setError(getString(R.string.field_required));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etRecoveryEmail.setError(getString(R.string.invalid_email));
            return false;
        }
        return true;
    }

    private boolean isValidUser(String email) {
        // Simulación de verificación de usuario
        // En una app real, esto verificaría en una base de datos
        return email.equals("admin@tienda.com") || email.contains("@");
    }
}
