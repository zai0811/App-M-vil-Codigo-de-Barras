package com.example.alergin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ProductDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // Obtener datos del intent
        Intent intent = getIntent();
        String productName = intent.getStringExtra("PRODUCT_NAME");
        String ingredients = intent.getStringExtra("PRODUCT_INGREDIENTS");
        ArrayList<String> harmfulIngredients = intent.getStringArrayListExtra("HARMFUL_INGREDIENTS");

        // Configurar vistas
        TextView productNameTextView = findViewById(R.id.product_name);
        TextView productIngredientsTextView = findViewById(R.id.product_ingredients);
        TextView harmfulIngredientsTextView = findViewById(R.id.harmful_ingredients);

        productNameTextView.setText("Producto:" + productName);
        productIngredientsTextView.setText("Ingredientes:" + ingredients);

        if (harmfulIngredients != null && ! harmfulIngredients.isEmpty()) {
            harmfulIngredientsTextView.setText("Ingredientes dañinos:" + harmfulIngredients);
        } else {
            harmfulIngredientsTextView.setText("No se encontraron ingredientes dañinos.");
        }
    }
}

