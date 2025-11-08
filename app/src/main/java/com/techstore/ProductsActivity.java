package com.techstore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.techstore.adapters.ProductAdapter;
import com.techstore.database.DatabaseHelper;
import com.techstore.models.CartItem;
import com.techstore.models.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {
    
    private RecyclerView recyclerViewProducts;
    private ProductAdapter adapter;
    private DatabaseHelper dbHelper;
    private int userId;
    private boolean isAdmin;
    private View layoutEmpty;
    private ImageButton btnAddProduct, btnCart, btnBack;
    private FloatingActionButton fabCart;
    private TextView tvCartBadge;
    private EditText etSearch;
    private Spinner spinnerCategory;
    private List<Product> allProducts;
    private List<String> categories;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        
        initViews();
        loadUserId();
        setupRecyclerView();
        loadProducts();
        setupClickListeners();
        updateCartBadge();
        setupAdminControls();
    }
    
    private void setupAdminControls() {
        // Ocultar botón de agregar producto si no es admin
        if (!isAdmin) {
            btnAddProduct.setVisibility(View.GONE);
        }
    }
    
    private void initViews() {
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        layoutEmpty = findViewById(R.id.layoutEmpty);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnCart = findViewById(R.id.btnCart);
        btnBack = findViewById(R.id.btnBack);
        fabCart = findViewById(R.id.fabCart);
        tvCartBadge = findViewById(R.id.tvCartBadge);
        etSearch = findViewById(R.id.etSearch);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        dbHelper = new DatabaseHelper(this);
    }
    
    private void loadUserId() {
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        userId = prefs.getInt("user_id", -1);
        String role = prefs.getString("user_role", "user");
        isAdmin = "admin".equalsIgnoreCase(role);
    }
    
    private void setupRecyclerView() {
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(null, this, isAdmin);
        recyclerViewProducts.setAdapter(adapter);
    }
    
    private void loadProducts() {
        allProducts = dbHelper.getAllProducts();
        adapter.updateProducts(allProducts);
        setupCategoryFilter();
        setupSearchFilter();
        updateEmptyState();
    }
    
    private void setupCategoryFilter() {
        categories = new ArrayList<>();
        categories.add("Todas las categorías");
        categories.addAll(dbHelper.getAllCategories());
        
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, 
                android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);
        
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterProducts();
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    
    private void setupSearchFilter() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts();
            }
            
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    
    private void filterProducts() {
        String searchQuery = etSearch.getText().toString().trim();
        String selectedCategory = spinnerCategory.getSelectedItem().toString();
        
        List<Product> filteredProducts = new ArrayList<>();
        
        if (selectedCategory.equals("Todas las categorías")) {
            if (searchQuery.isEmpty()) {
                filteredProducts = allProducts;
            } else {
                filteredProducts = dbHelper.searchProducts(searchQuery);
            }
        } else {
            if (searchQuery.isEmpty()) {
                filteredProducts = dbHelper.getProductsByCategory(selectedCategory);
            } else {
                List<Product> categoryProducts = dbHelper.getProductsByCategory(selectedCategory);
                for (Product product : categoryProducts) {
                    if (product.getName().toLowerCase().contains(searchQuery.toLowerCase()) ||
                        product.getDescription().toLowerCase().contains(searchQuery.toLowerCase())) {
                        filteredProducts.add(product);
                    }
                }
            }
        }
        
        adapter.updateProducts(filteredProducts);
        updateEmptyState();
    }
    
    private void updateEmptyState() {
        if (adapter.getItemCount() == 0) {
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
        
        btnCart.setOnClickListener(v -> openCart());
        
        fabCart.setOnClickListener(v -> openCart());
    }
    
    private void openCart() {
        if (userId == -1) {
            Toast.makeText(this, "Debes iniciar sesión primero", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(ProductsActivity.this, CartActivity.class);
        startActivity(intent);
    }
    
    private void updateCartBadge() {
        if (userId != -1) {
            List<CartItem> cartItems = dbHelper.getCartItems(userId);
            int itemCount = cartItems.size();
            if (itemCount > 0) {
                tvCartBadge.setText(String.valueOf(itemCount));
                tvCartBadge.setVisibility(View.VISIBLE);
            } else {
                tvCartBadge.setVisibility(View.GONE);
            }
        }
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
        updateCartBadge();
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
        updateCartBadge();
    }
}
