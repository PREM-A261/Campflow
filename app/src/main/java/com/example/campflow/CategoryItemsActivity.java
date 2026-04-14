package com.example.campflow;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.ArrayList;
import java.util.List;

public class CategoryItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);

        String categoryName = getIntent().getStringExtra("categoryName");

        MaterialToolbar toolbar = findViewById(R.id.categoryToolbar);
        toolbar.setTitle(categoryName);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        RecyclerView recyclerView = findViewById(R.id.categoryItemsRecycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<FoodItem> items = getCategoryItems(categoryName);
        recyclerView.setAdapter(new FoodAdapter(items));
    }

    private List<FoodItem> getCategoryItems(String category) {
        List<FoodItem> list = new ArrayList<>();
        switch (category) {
            case "Drinks":
                list.add(new FoodItem("Fresh Juice", "50", R.drawable.drink));
                list.add(new FoodItem("Cold Coffee", "80", R.drawable.drink));
                list.add(new FoodItem("Mango Lassi", "60", R.drawable.drink));
                list.add(new FoodItem("Iced Tea", "40", R.drawable.drink));
                list.add(new FoodItem("Milkshake", "100", R.drawable.drink));
                list.add(new FoodItem("Lemonade", "30", R.drawable.drink));
                break;
            case "Dessert":
                list.add(new FoodItem("Chocolate Cake", "180", R.drawable.desseret));
                list.add(new FoodItem("Ice Cream", "70", R.drawable.desseret));
                list.add(new FoodItem("Brownie", "120", R.drawable.desseret));
                list.add(new FoodItem("Pastry", "90", R.drawable.desseret));
                list.add(new FoodItem("Gulab Jamun", "50", R.drawable.desseret));
                list.add(new FoodItem("Fruit Salad", "110", R.drawable.desseret));
                break;
            case "Fast Food":
                list.add(new FoodItem("Burger", "110", R.drawable.burger));
                list.add(new FoodItem("Paneer Pizza", "200", R.drawable.paneer_pizza));
                list.add(new FoodItem("Cheese Sandwich", "100", R.drawable.sandwich));
                list.add(new FoodItem("French Fries", "80", R.drawable.sandwich));
                list.add(new FoodItem("Veg Grill", "120", R.drawable.sandwich));
                list.add(new FoodItem("Club Sandwich", "150", R.drawable.sandwich));
                break;
            case "Snacks":
                list.add(new FoodItem("Pav Bhaji", "120", R.drawable.pav));
                list.add(new FoodItem("Vada Pav", "30", R.drawable.pav));
                list.add(new FoodItem("Samosa", "20", R.drawable.pav));
                list.add(new FoodItem("Misal Pav", "100", R.drawable.pav));
                list.add(new FoodItem("Kachori", "25", R.drawable.pav));
                list.add(new FoodItem("Bhel Puri", "50", R.drawable.pav));
                break;
            case "South Indian":
                list.add(new FoodItem("Masala Dosa", "90", R.drawable.dosa));
                list.add(new FoodItem("Idli Sambhar", "60", R.drawable.dosa));
                list.add(new FoodItem("Mendu Vada", "70", R.drawable.dosa));
                list.add(new FoodItem("Uttapam", "80", R.drawable.dosa));
                list.add(new FoodItem("Mysore Dosa", "110", R.drawable.dosa));
                list.add(new FoodItem("Upma", "50", R.drawable.dosa));
                break;
            case "Chinese":
                list.add(new FoodItem("Spicy Noodles", "150", R.drawable.noodle));
                list.add(new FoodItem("Veg Manchurian", "140", R.drawable.noodle));
                list.add(new FoodItem("Fried Rice", "130", R.drawable.noodle));
                list.add(new FoodItem("Spring Roll", "120", R.drawable.noodle));
                list.add(new FoodItem("Hakha Noodles", "160", R.drawable.noodle));
                list.add(new FoodItem("Schezwan Rice", "150", R.drawable.noodle));
                break;
            default:
                list.add(new FoodItem("Food Item 1", "100", R.drawable.campflow_logo));
                list.add(new FoodItem("Food Item 2", "120", R.drawable.campflow_logo));
                break;
        }
        return list;
    }
}
