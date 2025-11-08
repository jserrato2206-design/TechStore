package com.techstore.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.techstore.R;
import com.techstore.models.CartItem;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    
    private List<CartItem> cartItems;
    private OnCartItemClickListener listener;
    
    public interface OnCartItemClickListener {
        void onQuantityChanged(int cartId, int newQuantity);
        void onRemoveItem(int cartId);
    }
    
    public CartAdapter(List<CartItem> cartItems, OnCartItemClickListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.bind(item);
    }
    
    @Override
    public int getItemCount() {
        return cartItems != null ? cartItems.size() : 0;
    }
    
    public void updateCartItems(List<CartItem> newItems) {
        this.cartItems = newItems;
        notifyDataSetChanged();
    }
    
    class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView tvProductName, tvProductPrice, tvQuantity, tvSubtotal;
        private ImageButton btnIncrease, btnDecrease, btnRemove;
        
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvSubtotal = itemView.findViewById(R.id.tvSubtotal);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
        
        public void bind(CartItem item) {
            tvProductName.setText(item.getProductName());
            tvProductPrice.setText(String.format("$%.2f c/u", item.getPrice()));
            tvQuantity.setText(String.valueOf(item.getQuantity()));
            tvSubtotal.setText(item.getFormattedSubtotal());
            
            btnIncrease.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onQuantityChanged(item.getId(), item.getQuantity() + 1);
                }
            });
            
            btnDecrease.setOnClickListener(v -> {
                if (item.getQuantity() > 1 && listener != null) {
                    listener.onQuantityChanged(item.getId(), item.getQuantity() - 1);
                }
            });
            
            btnRemove.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onRemoveItem(item.getId());
                }
            });
        }
    }
}
