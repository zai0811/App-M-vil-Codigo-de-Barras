package com.example.alergin;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alergin.Favoritos.FavoritesActivity;
import com.example.alergin.RecipeActivity;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private Spinner allergySpinner;
    private Button btnVerRecetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        dbHelper = new DatabaseHelper(this);
        allergySpinner = findViewById(R.id.allergy_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getAllAllergies());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        allergySpinner.setAdapter(adapter);

        findViewById(R.id.scan_button).setOnClickListener(v -> startScanActivity());

        btnVerRecetas = findViewById(R.id.button_recetas);
        btnVerRecetas.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, AllergiesActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.favorite_button).setOnClickListener(v -> {
            Intent intent = new Intent(this, FavoritesActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent); // Actualiza el intent actual con el nuevo intent
    }

    private ArrayList<String> getAllAllergies() {
        ArrayList<String> allergyList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Allergies", new String[]{"Name"}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                allergyList.add(cursor.getString(cursor.getColumnIndexOrThrow("Name")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return allergyList;
    }

    private void startScanActivity() {
        Intent intent = new Intent(WelcomeActivity.this, ScanActivity.class);
        intent.putExtra("SELECTED_ALLERGY", allergySpinner.getSelectedItem().toString());
        startActivity(intent);
    }

    private void startRecipesActivity() {
        Intent intent = new Intent(WelcomeActivity.this, RecipeActivity.class);
        intent.putExtra("SELECTED_ALLERGY", allergySpinner.getSelectedItem().toString());
        startActivity(intent);
    }
}
