package com.example.alergin;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;


public class AllergySelectionActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergy_selection);
        dbHelper = new DatabaseHelper(this);

        setupListView();
        setupButtons();
    }

    private void setupListView() {
        ListView listView = findViewById(R.id.allergy_list_view);
        List<String> allergies = getAllergiesFromDatabase();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allergies);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedAllergy = adapter.getItem(position);
            onAllergySelected(selectedAllergy);
        });
    }

    private List<String> getAllergiesFromDatabase() {
        List<String> allergies = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Name FROM Allergies", null);
        if (cursor.moveToFirst()) {
            do {
                allergies.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return allergies;
    }

    private void setupButtons() {
        Button btnRecipes = findViewById(R.id.btn_recipes);
        btnRecipes.setOnClickListener(v -> onRecipesButtonClick());

        Button btnScan = findViewById(R.id.btn_scan);
        btnScan.setOnClickListener(v -> onScanButtonClick());
    }

    public void onAllergySelected(String allergy) {
        Intent intent = new Intent(AllergySelectionActivity.this, RecipeActivity.class);
        intent.putExtra("SelectedAllergy", allergy);
        startActivity(intent);
    }

    public void onScanButtonClick() {
        Intent intent = new Intent(AllergySelectionActivity.this, ScanActivity.class);
        startActivity(intent);
    }

    public void onRecipesButtonClick() {
        Intent intent = new Intent(AllergySelectionActivity.this, AllergiesActivity.class);
        startActivity(intent);
    }
}
