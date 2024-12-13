package com.example.alergin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    // Mapa de usuarios predefinidos con sus credenciales
    private final Map<String, String> userDatabase = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Cargar usuarios predefinidos
        loadPredefinedUsers();

        // Referencias a los elementos de la vista
        EditText usernameInput = findViewById(R.id.usernameInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.loginButton);

        // Listener del botón de inicio de sesión
        loginButton.setOnClickListener(view -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            // Verificar credenciales
            if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
                Toast.makeText(LoginActivity.this, "Sesión Iniciada", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Credenciales incorrectas, intenta de nuevo.", Toast.LENGTH_LONG).show();
            }
        });
    }

    // Método para cargar usuarios predefinidos en la base de datos simulada
    private void loadPredefinedUsers() {
        userDatabase.put("user1", "1234");
        userDatabase.put("user2", "p24");
        userDatabase.put("admin", "admin3");
    }
}

