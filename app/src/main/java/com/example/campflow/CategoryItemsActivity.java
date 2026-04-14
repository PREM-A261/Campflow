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
        if (category == null) return list;

        switch (category) {
            case "Drinks":
                list.add(new FoodItem("Fresh Juice", "50", R.drawable.fresh_juice));
                list.add(new FoodItem("Cold Coffee", "80", R.drawable.cold_coffe));
                list.add(new FoodItem("Mango Lassi", "60", R.drawable.mango_lassi));
                list.add(new FoodItem("Iced Tea", "40", R.drawable.iced_tea));
                list.add(new FoodItem("Milkshake", "100", R.drawable.milkshake));
                list.add(new FoodItem("Lemonade", "30", R.drawable.lemonade));
                break;
            case "Dessert":
                list.add(new FoodItem("Chocolate Cake", "180", R.drawable.chocolate_cake));
                list.add(new FoodItem("Ice Cream", "70", R.drawable.ice_cream));
                list.add(new FoodItem("Brownie", "120", R.drawable.brownie));
                list.add(new FoodItem("Pastry", "90", R.drawable.pastry));
                list.add(new FoodItem("Gulab Jamun", "50", R.drawable.gulab_jamun));
                list.add(new FoodItem("Fruit Salad", "110", R.drawable.fruit_salad));
                break;
            case "Fast Food":
                list.add(new FoodItem("Burger", "110", R.drawable.burger));
                list.add(new FoodItem("Paneer Pizza", "200", R.drawable.paneer_pizza));
                list.add(new FoodItem("Cheese Sandwich", "100", R.drawable.cheese_sandwich));
                list.add(new FoodItem("French Fries", "80", R.drawable.french_fries));
                list.add(new FoodItem("Veg Grill", "120", R.drawable.veg_grill));
                list.add(new FoodItem("Club Sandwich", "150", R.drawable.club_sandwich));
                break;
            case "Snacks":
                list.add(new FoodItem("Pav Bhaji", "120", R.drawable.pav_bhaji));
                list.add(new FoodItem("Vada Pav", "30", R.drawable.vada_pav));
                list.add(new FoodItem("Samosa", "20", R.drawable.samosa));
                list.add(new FoodItem("Misal Pav", "100", R.drawable.misal_pav));
                list.add(new FoodItem("Kachori", "25", R.drawable.kachori));
                list.add(new FoodItem("Bhel Puri", "50", R.drawable.bhel_puri));
                break;
            case "South Indian":
                list.add(new FoodItem("Masala Dosa", "90", R.drawable.masala_dosa));
                list.add(new FoodItem("Idli Sambhar", "60", R.drawable.idli_samber));
                list.add(new FoodItem("Mendu Vada", "70", R.drawable.mendu_vada));
                list.add(new FoodItem("Uttapam", "80", R.drawable.uttapam));
                list.add(new FoodItem("Mysore Dosa", "110", R.drawable.mysore_dosa));
                list.add(new FoodItem("Upma", "50", R.drawable.upma));
                break;
            case "Chinese":
                list.add(new FoodItem("Spicy Noodles", "150", R.drawable.spicy_noodles));
                list.add(new FoodItem("Veg Manchurian", "140", R.drawable.veg_manchurian));
                list.add(new FoodItem("Fried Rice", "130", R.drawable.fried_rice));
                list.add(new FoodItem("Spring Roll", "120", R.drawable.spring_roll));
                list.add(new FoodItem("Hakha Noodles", "160", R.drawable.hakha_noodles));
                list.add(new FoodItem("Schezwan Rice", "150", R.drawable.schezwan_rice));
                break;
            default:
                list.add(new FoodItem("Food Item 1", "100", R.drawable.campflow_logo));
                list.add(new FoodItem("Food Item 2", "120", R.drawable.campflow_logo));
                break;
        }
        return list;
    }
}
