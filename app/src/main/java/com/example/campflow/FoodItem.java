package com.example.campflow;

public class FoodItem {

    private String name;
    private String price;
    private int image;
    private boolean isFavorite;
    private int quantity;

    public FoodItem(String name, String price, int image) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.isFavorite = false;
        this.quantity = 1;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}