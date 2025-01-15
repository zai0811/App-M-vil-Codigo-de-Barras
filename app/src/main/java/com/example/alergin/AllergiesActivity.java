package com.example.alergin;



import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class AllergiesActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private LinearLayout allergiesContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergies);

        dbHelper = new DatabaseHelper(this);
        allergiesContainer = findViewById(R.id.allergiesContainer);

        Cursor cursor = dbHelper.getAllAllergies();
        while (cursor.moveToNext()) {
            String allergyName = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
            int allergyId = cursor.getInt(cursor.getColumnIndexOrThrow("Allergy_Id"));

            Button button = new Button(this);
            button.setText(allergyName);
            button.setOnClickListener(v -> {
                Intent intent = new Intent(AllergiesActivity.this, RecipeActivity.class);
                intent.putExtra("ALLERGY_ID", allergyId);
                startActivity(intent);
            });
            allergiesContainer.addView(button);
        }
        cursor.close();
    }
}
