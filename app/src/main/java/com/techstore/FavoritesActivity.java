package com.techstore;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.techstore.adapters.ProductAdapter;
import com.techstore.database.DatabaseHelper;
import com.techstore.models.Product;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {
    
    private RecyclerView recyclerViewFavorites;
    private ProductAdapter adapter;
    private DatabaseHelper dbHelper;
    private int userId;
    private TextView tvEmpty;
    private ImageButton btnBack;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        
        initViews();
        loadUserId();
        setupRecyclerView();
        loadFavorites();
        setupClickListeners();
    }
    
    private void initViews() {
        recyclerViewFavorites = findViewById(R.id.recyclerViewFavorites);
        tvEmpty = findViewById(R.id.tvEmpty);
        btnBack = findViewById(R.id.btnBack);
        dbHelper = new DatabaseHelper(this);
    }
    
    private void loadUserId() {
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        userId = prefs.getInt("user_id", -1);
    }
    
    private void setupRecyclerView() {
        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(null, this, false);
        recyclerViewFavorites.setAdapter(adapter);
    }
    
    private void loadFavorites() {
        List<Product> favorites = dbHelper.getFavoriteProducts(userId);
        adapter.updateProducts(favorites);
        
        if (favorites.isEmpty()) {
            tvEmpty.setVisibility(android.view.View.VISIBLE);
            recyclerViewFavorites.setVisibility(android.view.View.GONE);
        } else {
            tvEmpty.setVisibility(android.view.View.GONE);
            recyclerViewFavorites.setVisibility(android.view.View.VISIBLE);
        }
    }
    
    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());
    }
    
    @Override
    public void onProductClick(Product product) {
        // Ver detalles del producto
    }
    
    @Override
    public void onAddToCartClick(Product product) {
        // Agregar al carrito
    }
    
    @Override
    public void onEditClick(Product product) {
        // No aplica en favoritos
    }
    
    @Override
    public void onDeleteClick(Product product) {
        // No aplica en favoritos
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        loadFavorites();
    }
}

