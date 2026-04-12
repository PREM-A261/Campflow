package com.example.campflow.utils;

import java.util.ArrayList;
import java.util.List;

public class SearchManager {

    public static <T> List<T> filterList(List<T> originalList, String query, FilterCondition<T> condition) {

        List<T> filteredList = new ArrayList<>();

        if (query == null || query.isEmpty()) {
            return originalList;
        }

        for (T item : originalList) {
            if (condition.matches(item, query)) {
                filteredList.add(item);
            }
        }

        return filteredList;
    }

    // Interface for custom filtering logic
    public interface FilterCondition<T> {
        boolean matches(T item, String query);
    }
}