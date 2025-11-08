package com.techstore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.techstore.adapters.ProductAdapter;
import com.techstore.database.DatabaseHelper;
import com.techstore.models.Product;
import java.util.List;

public class ProductsActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {
    
    private RecyclerView recyclerViewProducts;
    private ProductAdapter adapter;
    private DatabaseHelper dbHelper;
    private int userId;
    private View layoutEmpty;
    private ImageButton btnAddProduct, btnCart, btnBack;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        
        initViews();
        loadUserId();
        setupRecyclerView();
        loadProducts();
        setupClickListeners();
    }
    
    private void initViews() {
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        layoutEmpty = findViewById(R.id.layoutEmpty);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnCart = findViewById(R.id.btnCart);
        btnBack = findViewById(R.id.btnBack);
        dbHelper = new DatabaseHelper(this);
    }
    
    private void loadUserId() {
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        userId = prefs.getInt("user_id", -1);
    }
    
    private void setupRecyclerView() {
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(null, this);
        recyclerViewProducts.setAdapter(adapter);
    }
    
    private void loadProducts() {
        List<Product> products = dbHelper.getAllProducts();
        adapter.updateProducts(products);
        
        if (products.isEmpty()) {
            layoutEmpty.setVisibility(View.VISIBLE);
            recyclerViewProducts.setVisibility(View.GONE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
            recyclerViewProducts.setVisibility(View.VISIBLE);
        }
    }
    
    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ProductsActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        });
        
        btnAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(ProductsActivity.this, ProductFormActivity.class);
            startActivityForResult(intent, 100);
        });
        
        btnCart.setOnClickListener(v -> {
            Intent intent = new Intent(ProductsActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            loadProducts();
        }
    }
    
    @Override
    public void onProductClick(Product product) {
        // Mostrar detalles del producto
        Intent intent = new Intent(ProductsActivity.this, ProductDetailActivity.class);
        intent.putExtra("product_id", product.getId());
        startActivity(intent);
    }
    
    @Override
    public void onAddToCartClick(Product product) {
        if (userId == -1) {
            Toast.makeText(this, "Debes iniciar sesión primero", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (product.getStock() <= 0) {
            Toast.makeText(this, "Producto sin stock disponible", Toast.LENGTH_SHORT).show();
            return;
        }
        
        dbHelper.addToCart(userId, product.getId(), 1);
        Toast.makeText(this, "Producto agregado al carrito", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onEditClick(Product product) {
        Intent intent = new Intent(ProductsActivity.this, ProductFormActivity.class);
        intent.putExtra("product_id", product.getId());
        startActivityForResult(intent, 100);
    }
    
    @Override
    public void onDeleteClick(Product product) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Producto")
                .setMessage("¿Estás seguro de eliminar " + product.getName() + "?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    dbHelper.deleteProduct(product.getId());
                    Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show();
                    loadProducts();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();
    }
}
