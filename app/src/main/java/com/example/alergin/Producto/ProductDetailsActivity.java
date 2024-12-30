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

        // Obtener datos del intent
        String productName = getIntent().getStringExtra("PRODUCT_NAME");
        String productIngredients = getIntent().getStringExtra("PRODUCT_INGREDIENTS");
        ArrayList<String> harmfulIngredients = getIntent().getStringArrayListExtra("HARMFUL_INGREDIENTS");

        // Configurar vistas
        TextView productNameTextView = findViewById(R.id.product_name);
        TextView productIngredientsTextView = findViewById(R.id.product_ingredients);
        TextView harmfulIngredientsTextView = findViewById(R.id.harmful_ingredients);
        EditText storeNameEditText = findViewById(R.id.nombre_tienda);
        Button favoriteButton = findViewById(R.id.favorite_button);
        Button backButton = findViewById(R.id.back_button);

        productNameTextView.setText("Producto: " + productName);
        productIngredientsTextView.setText("Ingredientes: " + productIngredients);

        if (harmfulIngredients != null && !harmfulIngredients.isEmpty()) {
            harmfulIngredientsTextView.setText("Ingredientes dañinos: " + String.join(", ", harmfulIngredients));
        } else {
            harmfulIngredientsTextView.setText("No se encontraron ingredientes dañinos.");
        }

        // Manejar el evento del botón Añadir a Favoritos
        favoriteButton.setOnClickListener(v -> {
            String storeName = storeNameEditText.getText().toString();
            if (!storeName.isEmpty()) {
                dbHelper.addFavorite(productName, productIngredients, harmfulIngredients, storeName);
                Toast.makeText(this, "Producto añadido a favoritos", Toast.LENGTH_SHORT).show();

                // Launch FavoritesActivity to see the updated list of favorites
                Intent favoritesIntent = new Intent(this, FavoritesActivity.class);
                startActivity(favoritesIntent);
            } else {
                Toast.makeText(this, "Por favor, ingresa el nombre de la tienda", Toast.LENGTH_SHORT).show();
            }
        });

        // Manejar el evento del botón Volver
        // Manejar el evento del botón Volver para ir a la actividad Welcome
        backButton.setOnClickListener(v -> {
            Intent backIntent = new Intent(this, WelcomeActivity.class);
            backIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(backIntent);
            finish();
        });
    }
}
