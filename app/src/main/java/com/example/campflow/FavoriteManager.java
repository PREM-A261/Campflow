package com.example.campflow;

import java.util.ArrayList;
import java.util.List;

public class FavoriteManager {

    private static final List<FoodItem> favoriteList = new ArrayList<>();

    public static void addToFavorites(FoodItem item) {
        if (!favoriteList.contains(item)) {
            favoriteList.add(item);
            item.setFavorite(true);
        }
    }

    public static void removeFromFavorites(FoodItem item) {
        favoriteList.remove(item);
        item.setFavorite(false);
    }

    public static List<FoodItem> getFavorites() {
        return favoriteList;
    }

    public static boolean isFavorite(FoodItem item) {
        return favoriteList.contains(item);
    }
}