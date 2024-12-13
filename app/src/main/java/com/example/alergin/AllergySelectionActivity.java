package com.example.alergin;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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
        ListView allergyListView = findViewById(R.id.allergy_list_view);

        // Obtener la lista de alergias de la base de datos
        List<String> allergyList = getAllergiesFromDatabase();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, allergyList);
        allergyListView.setAdapter(adapter);
        allergyListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    private List<String> getAllergiesFromDatabase() {
        List<String> allergies = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM Allergies", null);
        if (cursor.moveToFirst()) {
            do {
                allergies.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return allergies;
    }
}
