package com.example.alergin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;


    public class LoginActivity extends AppCompatActivity {

        private final Map<String, String> userDatabase = new HashMap<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            loadPredefinedUsers();  // Cargar usuarios

            EditText usernameInput = findViewById(R.id.usernameInput);
            EditText passwordInput = findViewById(R.id.passwordInput);
            Button loginButton = findViewById(R.id.loginButton);

            loginButton.setOnClickListener(view -> {
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                // Verificar credenciales
                if (checkCredentials(username, password)) {
                    Toast.makeText(LoginActivity.this, "Sesión Iniciada", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,   WelcomeActivity.class);
                    intent.putExtra("USERNAME", username);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Credenciales incorrectas, intenta de nuevo.", Toast.LENGTH_LONG).show();
                }
            });
        }

        private void loadPredefinedUsers() {
            userDatabase.put("user1", "1234");
            userDatabase.put("user2", "p24");
            userDatabase.put("admin", "admin3");
        }

        private boolean checkCredentials(String username, String password) {
            try {
                String correctPassword = userDatabase.get(username);
                return correctPassword != null && correctPassword.equals(password);
            } catch (Exception e) {
                Log.e("LoginActivity", "Error checking credentials", e);
                return false;
            }
        }

    }


