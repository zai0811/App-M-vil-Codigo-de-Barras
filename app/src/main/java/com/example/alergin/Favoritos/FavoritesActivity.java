package com.example.alergin.Favoritos;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alergin.DatabaseHelper;
import com.example.alergin.Producto.Product;
import com.example.alergin.R;
import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FavoritesAdapter adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        recyclerView = findViewById(R.id.recycler_view_favorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicialización del helper de la base de datos
        dbHelper = new DatabaseHelper(this);

        // Cargar y mostrar favoritos
        loadFavorites();
    }

    // Método para cargar favoritos desde la base de datos
    private List<Product> loadFavorites() {
        List<Product> favorites = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try (Cursor cursor = db.rawQuery("SELECT * FROM Favorites", null)) {
            int productNameIndex = cursor.getColumnIndex("product_name");
            //int ingredientsIndex = cursor.getColumnIndex("ingredients");
            int storeIndex = cursor.getColumnIndex("store");
           // int harmfulIngredientsIndex = cursor.getColumnIndex("harmful_ingredients");

            while (cursor.moveToNext()) {
                String productName = cursor.getString(productNameIndex);
              //  String ingredients = cursor.getString(ingredientsIndex);
                String store = cursor.getString(storeIndex);
                //String harmfulIngredientsStr = cursor.getString(harmfulIngredientsIndex);

                // Convertir la cadena de ingredientes dañinos almacenados a ArrayList
                /**   ArrayList<String> harmfulIngredients = new ArrayList<>();
                if (harmfulIngredientsStr != null) {
                    String[] parts = harmfulIngredientsStr.split(", ");
                    for (String part : parts) {
                        harmfulIngredients.add(part.trim());
                    }
                }**/

                favorites.add(new Product(productName,null,null, store));
            }
        }
        db.close();
        return favorites;
    }
}

