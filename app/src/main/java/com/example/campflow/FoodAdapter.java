package com.example.campflow;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<FoodItem> foodList;

    public FoodAdapter(List<FoodItem> foodList) {
        this.foodList = foodList;
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {

        ImageView foodImage, heartIcon;
        TextView foodName, foodPrice;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage = itemView.findViewById(R.id.foodImage);
            heartIcon = itemView.findViewById(R.id.heartIcon);
            foodName = itemView.findViewById(R.id.foodName);
            foodPrice = itemView.findViewById(R.id.foodPrice);
        }
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {

        FoodItem item = foodList.get(position);

        holder.foodImage.setImageResource(item.getImage());
        holder.foodName.setText(item.getName());
        holder.foodPrice.setText("₹" + item.getPrice());

        if (FavoriteManager.isFavorite(item)) {
            holder.heartIcon.setImageResource(R.drawable.favorite2);
        } else {
            holder.heartIcon.setImageResource(R.drawable.favorite_24px);
        }

        holder.heartIcon.setOnClickListener(v -> {

            if (FavoriteManager.isFavorite(item)) {
                FavoriteManager.removeFromFavorites(item);
                holder.heartIcon.setImageResource(R.drawable.favorite_24px);
            } else {
                FavoriteManager.addToFavorites(item);
                holder.heartIcon.setImageResource(R.drawable.favorite2);
            }

            notifyItemChanged(holder.getAdapterPosition());

        });

        //-----onclick listener for fooditems------
        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(holder.itemView.getContext(), ProductDetailActivity.class);
            intent.putExtra("name", item.getName());
            intent.putExtra("price", item.getPrice());
            intent.putExtra("image", item.getImage());
            intent.putExtra("description", "This is a delicious food made with fresh ingredients. Highly recommended!");

            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }
}