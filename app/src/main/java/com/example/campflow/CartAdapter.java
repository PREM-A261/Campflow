package com.example.campflow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<FoodItem> cartList;

    public CartAdapter(List<FoodItem> cartList) {
        this.cartList = cartList;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name, price;
        Button removeBtn;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.foodImage);
            name = itemView.findViewById(R.id.foodName);
            price = itemView.findViewById(R.id.foodPrice);
            removeBtn = itemView.findViewById(R.id.btnRemove);
        }
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        FoodItem item = cartList.get(position);

        holder.image.setImageResource(item.getImage());
        holder.name.setText(item.getName());
        holder.price.setText("₹" + item.getPrice());

        // 🔥 SHOW remove button in cart
        holder.removeBtn.setVisibility(View.VISIBLE);

        holder.removeBtn.setOnClickListener(v -> {

            int currentPosition = holder.getAdapterPosition();

            cartList.remove(currentPosition);

            notifyItemRemoved(currentPosition);
            notifyItemRangeChanged(currentPosition, cartList.size());

            if (cartList.isEmpty()) {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}