package com.techstore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.techstore.database.DatabaseHelper;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvUserName, tvUserEmail, tvUserPhone, tvWelcome;
    private TextView btnMyOrders, btnFavorites;
    private Button btnGoToStore, btnLogout, btnBack, btnEditProfile, btnDeleteAccount;
    private DatabaseHelper dbHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initViews();
        loadUserData();
        setupClickListeners();
    }

    private void initViews() {
        tvUserName = findViewById(R.id.tvUserName);
        tvUserEmail = findViewById(R.id.tvUserEmail);
        tvUserPhone = findViewById(R.id.tvUserPhone);
        tvWelcome = findViewById(R.id.tvWelcome);
        btnMyOrders = findViewById(R.id.btnMyOrders);
        btnFavorites = findViewById(R.id.btnFavorites);
        btnGoToStore = findViewById(R.id.btnGoToStore);
        btnLogout = findViewById(R.id.btnLogout);
        btnBack = findViewById(R.id.btnBack);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
        dbHelper = new DatabaseHelper(this);
    }

    private void loadUserData() {
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        
        userId = prefs.getInt("user_id", -1);
        String name = prefs.getString("user_name", "");
        String email = prefs.getString("user_email", "");
        String phone = prefs.getString("user_phone", "");

        // Si no hay datos guardados, usar los datos del Intent
        if (name.isEmpty() && getIntent().hasExtra("user_name")) {
            name = getIntent().getStringExtra("user_name");
        }
        if (email.isEmpty() && getIntent().hasExtra("user_email")) {
            email = getIntent().getStringExtra("user_email");
        }
        if (phone.isEmpty() && getIntent().hasExtra("user_phone")) {
            phone = getIntent().getStringExtra("user_phone");
        }
        if (userId == -1 && getIntent().hasExtra("user_id")) {
            userId = getIntent().getIntExtra("user_id", -1);
        }

        tvWelcome.setText("¡Bienvenido, " + name + "!");
        tvUserName.setText("Nombre: " + name);
        tvUserEmail.setText("Email: " + email);
        tvUserPhone.setText("Teléfono: " + phone);
    }

    private void setupClickListeners() {
        btnGoToStore.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, ProductsActivity.class);
            startActivity(intent);
        });

        btnMyOrders.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, OrdersHistoryActivity.class);
            startActivity(intent);
        });

        btnFavorites.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            clearUserSession();
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
            
            Intent intent = new Intent(ProfileActivity.this, WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        });
        
        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            intent.putExtra("user_id", userId);
            startActivityForResult(intent, 100);
        });
        
        btnDeleteAccount.setOnClickListener(v -> {
            showDeleteConfirmationDialog();
        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            loadUserData();
        }
    }
    
    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
            .setTitle("Eliminar Cuenta")
            .setMessage("¿Está seguro de que desea eliminar su cuenta? Esta acción no se puede deshacer.")
            .setPositiveButton("Eliminar", (dialog, which) -> {
                if (userId != -1) {
                    dbHelper.deleteUser(userId);
                    clearUserSession();
                    Toast.makeText(this, "Cuenta eliminada", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProfileActivity.this, WelcomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            })
            .setNegativeButton("Cancelar", null)
            .show();
    }

    private void clearUserSession() {
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
}
