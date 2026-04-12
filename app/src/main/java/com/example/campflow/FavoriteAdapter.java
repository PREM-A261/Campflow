package com.example.campflow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavViewHolder> {

    private List<FoodItem> favoriteList;

    public FavoriteAdapter(List<FoodItem> favoriteList) {
        this.favoriteList = favoriteList;
    }

    public class FavViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name, price;
        Button removeBtn, buyNowBtn;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.foodImage);
            name = itemView.findViewById(R.id.foodName);
            price = itemView.findViewById(R.id.foodPrice);
            removeBtn = itemView.findViewById(R.id.btnRemove);
            buyNowBtn = itemView.findViewById(R.id.btnBuyNow);
        }
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite, parent, false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {

        FoodItem item = favoriteList.get(position);

        holder.image.setImageResource(item.getImage());
        holder.name.setText(item.getName());
        holder.price.setText("₹" + item.getPrice());

        holder.removeBtn.setOnClickListener(v -> {

            int currentPosition = holder.getAdapterPosition();

            FavoriteManager.removeFromFavorites(item);

            notifyItemRemoved(currentPosition);
            notifyItemRangeChanged(currentPosition, favoriteList.size());
        });

        holder.buyNowBtn.setOnClickListener(v -> {

            Toast.makeText(v.getContext(),
                    item.getName() + " purchased!",
                    Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }
}