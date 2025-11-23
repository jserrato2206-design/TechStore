package com.techstore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.techstore.database.DatabaseHelper;
import com.techstore.models.User;

public class EditProfileActivity extends AppCompatActivity {
    
    private EditText etName, etEmail, etPhone, etPassword, etConfirmPassword;
    private Button btnSave;
    private ImageButton btnBack;
    private DatabaseHelper dbHelper;
    private int userId;
    private User currentUser;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        
        initViews();
        dbHelper = new DatabaseHelper(this);
        
        userId = getIntent().getIntExtra("user_id", -1);
        if (userId != -1) {
            loadUser();
        } else {
            Toast.makeText(this, "Error al cargar usuario", Toast.LENGTH_SHORT).show();
            finish();
        }
        
        setupClickListeners();
    }
    
    private void initViews() {
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);
    }
    
    private void loadUser() {
        currentUser = dbHelper.getUserById(userId);
        if (currentUser != null) {
            etName.setText(currentUser.getName());
            etEmail.setText(currentUser.getEmail());
            etPhone.setText(currentUser.getPhone());
        }
    }
    
    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());
        
        btnSave.setOnClickListener(v -> saveUser());
    }
    
    private void saveUser() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        
        if (validateInputs(name, email, phone, password, confirmPassword)) {
            currentUser.setName(name);
            currentUser.setEmail(email);
            currentUser.setPhone(phone);
            
            if (!password.isEmpty()) {
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentUser.setPassword(password);
            }
            
            int rowsAffected = dbHelper.updateUser(currentUser);
            if (rowsAffected > 0) {
                // Actualizar SharedPreferences
                SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("user_name", name);
                editor.putString("user_email", email);
                editor.putString("user_phone", phone);
                editor.apply();
                
                Toast.makeText(this, "Perfil actualizado", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar perfil", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    private boolean validateInputs(String name, String email, String phone, 
                                  String password, String confirmPassword) {
        if (TextUtils.isEmpty(name)) {
            etName.setError("El nombre es obligatorio");
            return false;
        }
        
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("El email es obligatorio");
            return false;
        }
        
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Email inválido");
            return false;
        }
        
        if (TextUtils.isEmpty(phone)) {
            etPhone.setError("El teléfono es obligatorio");
            return false;
        }
        
        if (!password.isEmpty() && password.length() < 6) {
            etPassword.setError("La contraseña debe tener al menos 6 caracteres");
            return false;
        }
        
        if (!password.isEmpty() && !password.equals(confirmPassword)) {
            etConfirmPassword.setError("Las contraseñas no coinciden");
            return false;
        }
        
        return true;
    }
}

