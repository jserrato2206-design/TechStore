package com.techstore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.techstore.adapters.CartAdapter;
import com.techstore.database.DatabaseHelper;
import com.techstore.models.CartItem;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnCartItemClickListener {
    
    private RecyclerView recyclerViewCart;
    private CartAdapter adapter;
    private DatabaseHelper dbHelper;
    private int userId;
    private TextView tvTotal, tvItemCount, tvEmpty;
    private Button btnCheckout, btnClearCart;
    private ImageButton btnBack;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        
        initViews();
        loadUserId();
        setupRecyclerView();
        loadCartItems();
        setupClickListeners();
    }
    
    private void initViews() {
        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        tvTotal = findViewById(R.id.tvTotal);
        tvItemCount = findViewById(R.id.tvItemCount);
        tvEmpty = findViewById(R.id.tvEmpty);
        btnCheckout = findViewById(R.id.btnCheckout);
        btnClearCart = findViewById(R.id.btnClearCart);
        btnBack = findViewById(R.id.btnBack);
        dbHelper = new DatabaseHelper(this);
    }
    
    private void loadUserId() {
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        userId = prefs.getInt("user_id", -1);
        
        if (userId == -1) {
            Toast.makeText(this, "Debes iniciar sesión primero", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    
    private void setupRecyclerView() {
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CartAdapter(null, this);
        recyclerViewCart.setAdapter(adapter);
    }
    
    private void loadCartItems() {
        List<CartItem> items = dbHelper.getCartItems(userId);
        adapter.updateCartItems(items);
        updateTotal();
        
        if (items.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            recyclerViewCart.setVisibility(View.GONE);
            btnCheckout.setEnabled(false);
            btnClearCart.setEnabled(false);
        } else {
            tvEmpty.setVisibility(View.GONE);
            recyclerViewCart.setVisibility(View.VISIBLE);
            btnCheckout.setEnabled(true);
            btnClearCart.setEnabled(true);
        }
    }
    
    private void updateTotal() {
        List<CartItem> items = dbHelper.getCartItems(userId);
        double total = dbHelper.getCartTotal(userId);
        int itemCount = items.size();
        
        tvTotal.setText(String.format("$%.2f", total));
        tvItemCount.setText(itemCount + (itemCount == 1 ? " producto" : " productos"));
    }
    
    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> {
            finish();
        });
        
        btnCheckout.setOnClickListener(v -> {
            List<CartItem> items = dbHelper.getCartItems(userId);
            if (items.isEmpty()) {
                Toast.makeText(this, "Tu carrito está vacío", Toast.LENGTH_SHORT).show();
                return;
            }
            
            double total = dbHelper.getCartTotal(userId);
            int itemCount = items.size();
            
            String message = "Resumen de compra:\n\n" +
                    "Productos: " + itemCount + "\n" +
                    "Total: $" + String.format("%.2f", total) + "\n\n" +
                    "¿Deseas confirmar la compra?";
            
            new AlertDialog.Builder(this)
                    .setTitle("💳 Confirmar Compra")
                    .setMessage(message)
                    .setPositiveButton("Pagar", (dialog, which) -> {
                        dbHelper.clearCart(userId);
                        Toast.makeText(this, "¡Compra realizada exitosamente! Total pagado: $" + String.format("%.2f", total), Toast.LENGTH_LONG).show();
                        finish();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
        
        btnClearCart.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Vaciar Carrito")
                    .setMessage("¿Estás seguro de vaciar el carrito?")
                    .setPositiveButton("Vaciar", (dialog, which) -> {
                        dbHelper.clearCart(userId);
                        loadCartItems();
                        Toast.makeText(this, "Carrito vaciado", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }
    
    @Override
    public void onQuantityChanged(int cartId, int newQuantity) {
        dbHelper.updateCartQuantity(cartId, newQuantity);
        loadCartItems();
    }
    
    @Override
    public void onRemoveItem(int cartId) {
        dbHelper.removeFromCart(cartId);
        loadCartItems();
        Toast.makeText(this, "Producto eliminado del carrito", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        loadCartItems();
    }
}
