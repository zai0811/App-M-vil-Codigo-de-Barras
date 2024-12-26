package com.example.alergin;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private Spinner allergySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        dbHelper = new DatabaseHelper(this);

        // Obtener el nombre de usuario del intent
        //String username = getIntent().getStringExtra("USERNAME");

        // Configurar el TextView con el nombre de usuario
       //TextView userTextView = findViewById(R.id.user_text_vista);
        //userTextView.setText(getString(R.string.user_name_display, username));

        // Configurar el Spinner con tipos de alergias desde la base de datos
        allergySpinner = findViewById(R.id.allergy_spinner);
        ArrayList<String> allergies = getAllAllergies();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allergies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        allergySpinner.setAdapter(adapter);

        // Botón para escanear producto
        Button scanButton = findViewById(R.id.scan_button);
        scanButton.setOnClickListener(v -> startScanActivity());

        // Botón de recetas
        Button recipesButton = findViewById(R.id.button_recetas);
        recipesButton.setOnClickListener(v -> startRecipesActivity());
    }

    // Método para obtener las alergias de la base de datos
    private ArrayList<String> getAllAllergies() {
        ArrayList<String> allergyList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Allergies", new String[]{"Name"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            allergyList.add(cursor.getString(cursor.getColumnIndexOrThrow("Name")));
        }
        cursor.close();
        return allergyList;
    }

    // Método para iniciar la actividad de escaneo
    private void startScanActivity() {
        String selectedAllergy = allergySpinner.getSelectedItem().toString();
        Intent intent = new Intent(WelcomeActivity.this, ScanActivity.class);
        intent.putExtra("SELECTED_ALLERGY", selectedAllergy);
        startActivity(intent);
    }

    // Método para iniciar la actividad de recetas
    private void startRecipesActivity() {
        String selectedAllergy = allergySpinner.getSelectedItem().toString();
        Intent intent = new Intent(WelcomeActivity.this, RecipesActivity.class);
        intent.putExtra("SELECTED_ALLERGY", selectedAllergy);
        startActivity(intent);
    }
}
