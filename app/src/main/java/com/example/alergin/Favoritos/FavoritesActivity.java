package com.example.alergin.Favoritos;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        dbHelper = new DatabaseHelper(this);

        ListView favoritesListView = findViewById(R.id.favorites_list_view);
        ArrayList<String> favoritesList = dbHelper.getAllFavorites();

        if (favoritesList.isEmpty()) {
            // Mostrar un mensaje si no hay favoritos y regresar a WelcomeActivity
            Toast.makeText(this, "No hay favoritos añadidos", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            // Mostrar los favoritos si existen
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favoritesList);
            favoritesListView.setAdapter(adapter);
        }
    }

}

