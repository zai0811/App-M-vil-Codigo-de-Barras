package com.example.alergin.Producto;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alergin.DatabaseHelper;
import com.example.alergin.R;



public class ProductDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // Obtener datos del intent
        Intent intent = getIntent();
        Product product = intent.getParcelableExtra("product");

        // Configurar vistas
        TextView productNameTextView = findViewById(R.id.product_name);
        TextView productIngredientsTextView = findViewById(R.id.product_ingredients);
        TextView harmfulIngredientsTextView = findViewById(R.id.harmful_ingredients);

        productNameTextView.setText("Producto: " + product.getName());
        productIngredientsTextView.setText("Ingredientes: " + product.getIngredients());

        if (product.getHarmfulIngredients() != null && !product.getHarmfulIngredients().isEmpty()) {
            harmfulIngredientsTextView.setText("Ingredientes dañinos: " + String.join(", ", product.getHarmfulIngredients()));
        } else {
            harmfulIngredientsTextView.setText("No se encontraron ingredientes dañinos.");
        }
    }
}
