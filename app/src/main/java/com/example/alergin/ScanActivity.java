package com.example.alergin;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alergin.Producto.ProductDetailsActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class ScanActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private String selectedAllergy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        dbHelper = new DatabaseHelper(this);

        // Obtener la alergia seleccionada del intent
        selectedAllergy = getIntent().getStringExtra("SELECTED_ALLERGY");

        // Iniciar el escaneo del código de barras
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Escanea un código de barras");
        integrator.setCameraId(0); // Usar cámara trasera
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null && result.getContents() != null) {
            String scannedCode = result.getContents();
            fetchProductInfo(scannedCode);
        } else {
            Toast.makeText(this, "No se escaneó ningún código", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchProductInfo(String barcode) {
        String url = "https://world.openfoodfacts.org/api/v0/product/" + barcode + ".json";

        new Thread(() -> {
            try {
                java.net.URL apiUrl = new java.net.URL(url);
                java.net.HttpURLConnection connection = (java.net.HttpURLConnection) apiUrl.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    processApiResponse(response.toString());
                } else {
                    runOnUiThread(() -> Toast.makeText(this, "Error al consultar la API. Código: " + responseCode, Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "Error de conexión con la API", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void processApiResponse(String jsonResponse) {
        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject(jsonResponse);
            if (jsonObject.getInt("status") == 1) {
                org.json.JSONObject product = jsonObject.getJSONObject("product");
                String productName = product.getString("product_name");
                String ingredientsText = product.getString("ingredients_text");

                // Verificar ingredientes dañinos
                ArrayList<String> harmfulIngredients = getHarmfulIngredients(ingredientsText);

                Intent intent = new Intent(this, ProductDetailsActivity.class);
                intent.putExtra("PRODUCT_NAME", productName);
                intent.putExtra("PRODUCT_INGREDIENTS", ingredientsText);
                intent.putExtra("HARMFUL_INGREDIENTS", harmfulIngredients);
                startActivity(intent);
            } else {
                runOnUiThread(() -> Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_SHORT).show());
            }
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(() -> Toast.makeText(this, "Error al procesar la API", Toast.LENGTH_SHORT).show());
        }
    }
    private ArrayList<String> getHarmfulIngredients(String ingredientsText) {
        ArrayList<String> harmfulIngredients = new ArrayList<>();
        // Obtener la lista de ingredientes dañinos de la base de datos para la alergia seleccionada
        int allergyId = dbHelper.getAllergyIdByName(selectedAllergy);  // Suponiendo que 'selectedAllergy' es una variable de instancia con la alergia seleccionada del usuario
        ArrayList<String> allergyIngredients = dbHelper.getIngredientsForAllergy(allergyId);

        // Convertir el texto de ingredientes en una lista (suponiendo que cada ingrediente está separado por comas)
        String[] ingredientsArray = ingredientsText.split(",");

        // Comprobar si algún ingrediente del producto está en la lista de ingredientes dañinos
        for (String ingredient : ingredientsArray) {
            ingredient = ingredient.trim().toLowerCase();  // Limpiar y convertir a minúsculas para comparación consistente
            for (String allergyIngredient : allergyIngredients) {
                if (ingredient.contains(allergyIngredient.toLowerCase())) {
                    harmfulIngredients.add(allergyIngredient);
                }
            }
        }

        return harmfulIngredients;
    }

}
