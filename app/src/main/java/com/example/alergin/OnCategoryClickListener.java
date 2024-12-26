package com.example.alergin;

import com.example.alergin.CategoryActivity;
import com.example.alergin.SubCategory;

import org.tensorflow.lite.support.label.Category;

public interface OnCategoryClickListener {
    void onCategoryClick(CategoryActivity category);
    void onCategoryClick(SubCategory subCategory);

    // Método para manejar clics en las categorías
    void onCategoryClicked(int categoryId, String categoryName);

    // Método para manejar clics en subcategorías, si fuera necesario
    void onSubCategoryClicked(int subCategoryId, String subCategoryName);
}
