package com.example.campflow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;
    private OnOrderClickListener listener;

    public interface OnOrderClickListener {
        void onOrderClick(Order order);
    }

    public OrderAdapter(List<Order> orderList, OnOrderClickListener listener) {
        this.orderList = orderList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.foodName.setText(order.getFoodName());
        holder.orderId.setText("Order #" + order.getOrderId());
        holder.status.setText(order.getStatus());
        holder.price.setText("₹" + order.getPrice());
        holder.image.setImageResource(order.getImageResource());

        holder.itemView.setOnClickListener(v -> listener.onOrderClick(order));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView foodName, orderId, status, price;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.orderImage);
            foodName = itemView.findViewById(R.id.orderFoodName);
            orderId = itemView.findViewById(R.id.orderId);
            status = itemView.findViewById(R.id.orderStatus);
            price = itemView.findViewById(R.id.orderPrice);
        }
    }
}