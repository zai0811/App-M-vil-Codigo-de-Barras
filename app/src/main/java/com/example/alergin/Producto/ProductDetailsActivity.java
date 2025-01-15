package com.example.alergin.Producto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alergin.DatabaseHelper;
import com.example.alergin.Favoritos.FavoritesActivity;
import com.example.alergin.R;
import com.example.alergin.WelcomeActivity;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;


public class ProductDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        DatabaseHelper dbHelper = new DatabaseHelper(this);


        // Configurar vistas
        TextView productNameTextView = findViewById(R.id.product_name);
        TextView productIngredientsTextView = findViewById(R.id.product_ingredients);
        TextView harmfulIngredientsTextView = findViewById(R.id.harmful_ingredients);
        EditText storeNameEditText = findViewById(R.id.nombre_tienda);
        Button favoriteButton = findViewById(R.id.favorite_button);
        Button backButton = findViewById(R.id.back_button);


// Obtener datos pasados por el Intent
        String productName = getIntent().getStringExtra("PRODUCT_NAME");
        String productIngredients = getIntent().getStringExtra("PRODUCT_INGREDIENTS");
        ArrayList<String> harmfulIngredients = getIntent().getStringArrayListExtra("HARMFUL_INGREDIENTS");

// Configuración del TextView para mostrar los ingredientes dañinos
        if (harmfulIngredients != null && !harmfulIngredients.isEmpty()) {
            harmfulIngredientsTextView.setText("Ingredientes dañinos: " + String.join(", ", harmfulIngredients));
        } else {
            harmfulIngredientsTextView.setText("No se encontraron ingredientes dañinos.");
        }



        productNameTextView.setText("Producto: " + productName);
        productIngredientsTextView.setText("Ingredientes: " + productIngredients);



        // Guardar producto en favoritos y volver a WelcomeActivity
        favoriteButton.setOnClickListener(v -> {
            String storeName = storeNameEditText.getText().toString();
            if (!storeName.isEmpty()) {
                dbHelper.addFavorite(productName, storeName);
                Toast.makeText(this, "Producto añadido a favoritos", Toast.LENGTH_SHORT).show();

                // Regresar directamente a WelcomeActivity y mostrar favoritos
                Intent intent = new Intent(ProductDetailsActivity.this, WelcomeActivity.class);
                intent.putExtra("SHOW_FAVORITES", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Por favor ingresa el nombre de la tienda", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón Volver
        backButton.setOnClickListener(v -> finish());
    }}
