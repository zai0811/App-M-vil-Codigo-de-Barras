package com.example.alergin.Favoritos;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alergin.DatabaseHelper;
import com.example.alergin.Producto.Product;
import com.example.alergin.Producto.ProductDetailsActivity;
import com.example.alergin.R;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FavoritesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        recyclerView = findViewById(R.id.recycler_view_favorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Cargar y mostrar favoritos
        adapter = new FavoritesAdapter(loadFavorites());
        recyclerView.setAdapter(adapter);
    }

    // Método para cargar favoritos desde la base de datos
    private List<Product> loadFavorites() {
        List<Product> favorites = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Favorites", null);

        int productNameIndex = cursor.getColumnIndex("product_name");
        int ingredientsIndex = cursor.getColumnIndex("ingredients");
        int storeIndex = cursor.getColumnIndex("store");

        if (cursor.moveToFirst()) {
            do {
                String productName = cursor.getString(productNameIndex);
                String ingredients = cursor.getString(ingredientsIndex);
                String store = cursor.getString(storeIndex);
                // Aquí necesitas convertir la cadena de ingredientes dañinos almacenados a ArrayList, si está almacenado como una cadena separada por comas
                ArrayList<String> harmfulIngredients = new ArrayList<>(); // Asumiendo que agregas los ingredientes dañinos aquí

                favorites.add(new Product(productName, ingredients, harmfulIngredients, store));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return favorites;
    }

}
