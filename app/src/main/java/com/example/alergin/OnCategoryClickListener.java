package com.example.alergin;

import com.example.alergin.CategoryActivity;
import com.example.alergin.SubCategory;

import org.tensorflow.lite.support.label.Category;

public interface OnCategoryClickListener {
    void onCategoryClick(CategoryActivity category);
    void onCategoryClick(SubCategory subCategory);
}
