package com.techstore;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.techstore.database.DatabaseHelper;
import com.techstore.models.Product;

public class ProductFormActivity extends AppCompatActivity {
    
    private EditText etProductName, etProductDescription, etProductPrice, 
                     etProductCategory, etProductStock;
    private Button btnSave;
    private ImageButton btnBack;
    private DatabaseHelper dbHelper;
    private int productId = -1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);
        
        initViews();
        dbHelper = new DatabaseHelper(this);
        
        // Verificar si es edición
        productId = getIntent().getIntExtra("product_id", -1);
        if (productId != -1) {
            loadProduct();
        }
        
        setupClickListeners();
    }
    
    private void initViews() {
        etProductName = findViewById(R.id.etProductName);
        etProductDescription = findViewById(R.id.etProductDescription);
        etProductPrice = findViewById(R.id.etProductPrice);
        etProductCategory = findViewById(R.id.etProductCategory);
        etProductStock = findViewById(R.id.etProductStock);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);
    }
    
    private void loadProduct() {
        Product product = dbHelper.getProductById(productId);
        if (product != null) {
            etProductName.setText(product.getName());
            etProductDescription.setText(product.getDescription());
            etProductPrice.setText(String.valueOf(product.getPrice()));
            etProductCategory.setText(product.getCategory());
            etProductStock.setText(String.valueOf(product.getStock()));
            findViewById(R.id.tvTitle).setContentDescription("Editar Producto");
        }
    }
    
    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> {
            finish();
        });
        
        btnSave.setOnClickListener(v -> saveProduct());
    }
    
    private void saveProduct() {
        String name = etProductName.getText().toString().trim();
        String description = etProductDescription.getText().toString().trim();
        String priceStr = etProductPrice.getText().toString().trim();
        String category = etProductCategory.getText().toString().trim();
        String stockStr = etProductStock.getText().toString().trim();
        
        if (validateInputs(name, description, priceStr, category, stockStr)) {
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(Double.parseDouble(priceStr));
            product.setCategory(category);
            product.setStock(Integer.parseInt(stockStr));
            product.setImage("");
            
            if (productId != -1) {
                product.setId(productId);
                dbHelper.updateProduct(product);
                Toast.makeText(this, "Producto actualizado", Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.insertProduct(product);
                Toast.makeText(this, "Producto creado", Toast.LENGTH_SHORT).show();
            }
            
            setResult(RESULT_OK);
            finish();
        }
    }
    
    private boolean validateInputs(String name, String description, String priceStr, 
                                   String category, String stockStr) {
        boolean isValid = true;
        
        if (TextUtils.isEmpty(name)) {
            etProductName.setError("Campo obligatorio");
            isValid = false;
        }
        
        if (TextUtils.isEmpty(description)) {
            etProductDescription.setError("Campo obligatorio");
            isValid = false;
        }
        
        if (TextUtils.isEmpty(priceStr)) {
            etProductPrice.setError("Campo obligatorio");
            isValid = false;
        } else {
            try {
                double price = Double.parseDouble(priceStr);
                if (price <= 0) {
                    etProductPrice.setError("El precio debe ser mayor a 0");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                etProductPrice.setError("Precio inválido");
                isValid = false;
            }
        }
        
        if (TextUtils.isEmpty(category)) {
            etProductCategory.setError("Campo obligatorio");
            isValid = false;
        }
        
        if (TextUtils.isEmpty(stockStr)) {
            etProductStock.setError("Campo obligatorio");
            isValid = false;
        } else {
            try {
                int stock = Integer.parseInt(stockStr);
                if (stock < 0) {
                    etProductStock.setError("El stock no puede ser negativo");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                etProductStock.setError("Stock inválido");
                isValid = false;
            }
        }
        
        return isValid;
    }
}
