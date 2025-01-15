package com.example.alergin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeActivity extends AppCompatActivity {
    private int allergyId;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        dbHelper = new DatabaseHelper(this);
        allergyId = getIntent().getIntExtra("ALLERGY_ID", -1);

        if (allergyId == -1) {
            Toast.makeText(this, "Error al cargar la alergia seleccionada.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        LinearLayout recipeContainer = findViewById(R.id.recipeContainer);

        String[] categories = {"Desayuno", "Almuerzo", "Cena"};
        for (String category : categories) {
            Button button = new Button(this);
            button.setText(category);
            button.setOnClickListener(v -> {
                String videoUrl = dbHelper.getVideoUrlByCategory(allergyId, category);
                if (videoUrl != null && !videoUrl.isEmpty()) {
                    Intent intent = new Intent(RecipeActivity.this, VideoPlayerActivity.class);
                    intent.putExtra("ALLERGY_ID", allergyId);
                    intent.putExtra("CATEGORY", category);
                    intent.putExtra("videoUrl", videoUrl); // Asegúrate de pasar la URL del video
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "No se encontró un video para esta categoría.", Toast.LENGTH_SHORT).show();
                }
            });
            recipeContainer.addView(button);
        }
    }
}


