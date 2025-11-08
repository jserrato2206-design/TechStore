package com.techstore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.techstore.database.DatabaseHelper;
import com.techstore.models.Product;

public class ProductDetailActivity extends AppCompatActivity {
    
    private TextView tvProductName, tvProductDescription, tvProductPrice, 
                     tvProductCategory, tvProductStock;
    private Button btnAddToCart;
    private ImageButton btnBack;
    private DatabaseHelper dbHelper;
    private Product product;
    private int userId;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        
        initViews();
        loadUserId();
        
        int productId = getIntent().getIntExtra("product_id", -1);
        if (productId != -1) {
            loadProduct(productId);
        } else {
            Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_SHORT).show();
            finish();
        }
        
        setupClickListeners();
    }
    
    private void initViews() {
        tvProductName = findViewById(R.id.tvProductName);
        tvProductDescription = findViewById(R.id.tvProductDescription);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvProductCategory = findViewById(R.id.tvProductCategory);
        tvProductStock = findViewById(R.id.tvProductStock);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnBack = findViewById(R.id.btnBack);
        dbHelper = new DatabaseHelper(this);
    }
    
    private void loadUserId() {
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        userId = prefs.getInt("user_id", -1);
    }
    
    private void loadProduct(int productId) {
        product = dbHelper.getProductById(productId);
        if (product != null) {
            tvProductName.setText(product.getName());
            tvProductDescription.setText(product.getDescription());
            tvProductPrice.setText(product.getFormattedPrice());
            tvProductCategory.setText(product.getCategory());
            tvProductStock.setText("Stock disponible: " + product.getStock());
            
            if (product.getStock() <= 0) {
                btnAddToCart.setEnabled(false);
                btnAddToCart.setText("Sin Stock");
            }
        }
    }
    
    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> {
            finish();
        });
        
        btnAddToCart.setOnClickListener(v -> {
            if (userId == -1) {
                Toast.makeText(this, "Debes iniciar sesión primero", Toast.LENGTH_SHORT).show();
                return;
            }
            
            if (product != null && product.getStock() > 0) {
                dbHelper.addToCart(userId, product.getId(), 1);
                Toast.makeText(this, "Producto agregado al carrito", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
