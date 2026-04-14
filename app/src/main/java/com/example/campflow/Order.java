package com.example.campflow;

import java.io.Serializable;

public class Order implements Serializable {
    private String orderId;
    private String foodName;
    private String price;
    private int imageResource;
    private String status; // "Placed", "Preparing", "Out for Delivery", "Delivered"
    private String date;

    public Order() {}

    public Order(String orderId, String foodName, String price, int imageResource, String status, String date) {
        this.orderId = orderId;
        this.foodName = foodName;
        this.price = price;
        this.imageResource = imageResource;
        this.status = status;
        this.date = date;
    }

    public String getOrderId() { return orderId; }
    public String getFoodName() { return foodName; }
    public String getPrice() { return price; }
    public int getImageResource() { return imageResource; }
    public String getStatus() { return status; }
    public String getDate() { return date; }
}