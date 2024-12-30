package com.example.alergin;

public interface OnClickListener {
    void onCategoryClick(CategoryActivity category);
    void onCategoryClick(SubCategory subCategory);

    // Método para manejar clics en las categorías
    void onCategoryClicked(int categoryId, String categoryName);

    // Método para manejar clics en subcategorías, si fuera necesario
    void onSubCategoryClicked(int subCategoryId, String subCategoryName);
}
