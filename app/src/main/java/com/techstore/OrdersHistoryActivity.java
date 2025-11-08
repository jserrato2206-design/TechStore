package com.techstore;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.techstore.database.DatabaseHelper;
import com.techstore.models.Order;
import java.util.List;

public class OrdersHistoryActivity extends AppCompatActivity {
    
    private RecyclerView recyclerViewOrders;
    private DatabaseHelper dbHelper;
    private int userId;
    private TextView tvEmpty;
    private ImageButton btnBack;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_history);
        
        initViews();
        loadUserId();
        loadOrders();
        setupClickListeners();
    }
    
    private void initViews() {
        recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
        tvEmpty = findViewById(R.id.tvEmpty);
        btnBack = findViewById(R.id.btnBack);
        dbHelper = new DatabaseHelper(this);
    }
    
    private void loadUserId() {
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        userId = prefs.getInt("user_id", -1);
    }
    
    private void loadOrders() {
        List<Order> orders = dbHelper.getUserOrders(userId);
        
        if (orders.isEmpty()) {
            tvEmpty.setVisibility(android.view.View.VISIBLE);
            recyclerViewOrders.setVisibility(android.view.View.GONE);
        } else {
            tvEmpty.setVisibility(android.view.View.GONE);
            recyclerViewOrders.setVisibility(android.view.View.VISIBLE);
            // TODO: Crear adapter para mostrar pedidos
        }
    }
    
    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());
    }
}

