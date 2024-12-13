package com.example.alergin;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CategoryActivity extends AppCompatActivity {
    public CategoryActivity(int id, String name, String imageCode) {
        this.id = id;
        this.name = name;
        this.imageCode = imageCode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Obtener la alergia seleccionada del intent
        String selectedAllergy = getIntent().getStringExtra("SELECTED_ALLERGY");
        if (selectedAllergy != null) {
            Toast.makeText(this, "Alergia seleccionada: " + selectedAllergy, Toast.LENGTH_SHORT).show();
        }

    }
    private int id;
    private String name;
    private String imageCode;

    // Constructor

    // Getter para 'id'
    public int getId() {
        return id;
    }

    // Getter para 'name'
    public String getName() {
        return name;
    }

    // Getter para 'imageCode'
    public String getImageCode() {
        return imageCode;
    }
}


