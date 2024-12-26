package com.example.alergin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.alergin.*;
import java.util.ArrayList;

public class SubCategoryActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private SubCategoriesAdapter subCategoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        int categoryId = getIntent().getIntExtra("CATEGORY_ID", -1);
        if (categoryId == -1) {
            finish();  // Cerrar esta actividad si no se encontró el CATEGORY_ID
            return;
        }

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerViewSubCategories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Usar función para obtener el nombre de la categoría
        String categoryName = dbHelper.getCategoryNameById(categoryId);
        TextView textSubCategory = findViewById(R.id.textSubCategory);
        textSubCategory.setText(categoryName);  // Mostrar el nombre de la categoría en el TextView

        // Obtener el código de la imagen de la categoría
        String imageCode = dbHelper.getCategoryImageCodeById(categoryId);
        ImageView imageView = findViewById(R.id.categoryImageView);

        // Obtener el recurso de la imagen usando el código de la imagen
        int imageResourceId = getResources().getIdentifier(imageCode, "drawable", getPackageName());
        if (imageResourceId != 0) {
            imageView.setImageResource(imageResourceId);  // Mostrar la imagen de la categoría
        } else {
            imageView.setImageResource(R.drawable.logo);  // Imagen predeterminada si no se encuentra la imagen
        }

        loadSubCategories(categoryId);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void loadSubCategories(int categoryId) {
        Context context = this;
        subCategoriesAdapter = new SubCategoriesAdapter(new ArrayList<>(), context, subCategory -> {
            Log.d("SubCategoryActivity", "Video URL: " + subCategory.getVideoURL());  // Verificar el videoUrl
            Intent intent = new Intent(context, VideoPlayerActivity.class);
            intent.putExtra("videoUrl", subCategory.getVideoURL());  // Pasar el videoUrl
            intent.putExtra("subCategoryName", subCategory.getName());  // Pasar el nombre de la subcategoría
            intent.putExtra("imageCode", subCategory.getImageCode());  // Pasar la imagen de la subcategoría
            startActivity(intent);
        });

        recyclerView.setAdapter(subCategoriesAdapter);

        // Suponiendo que tienes un método en dbHelper que devuelve una lista de SubCategory
        ArrayList<SubCategory> subCategories = dbHelper.getSubCategoriesByCategoryId(categoryId);
        subCategoriesAdapter.setSubCategories(subCategories);
    }
}
