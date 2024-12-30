package com.example.alergin.Producto;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Locale;

public class Product implements Parcelable {
    private String name;
    private String ingredients;
    private ArrayList<String> harmfulIngredients;
    private String store;  // Tienda de compra del producto

    public Product(String name, String ingredients, ArrayList<String> harmfulIngredients, String store) {
        this.name = name;
        this.ingredients = ingredients;
        this.harmfulIngredients = harmfulIngredients;
        this.store = store;
    }

    protected Product(Parcel in) {
        name = in.readString();
        ingredients = in.readString();
        harmfulIngredients = in.createStringArrayList();
        store = in.readString();  // Leer la tienda desde el parcel
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(ingredients);
        dest.writeStringList(harmfulIngredients);
        dest.writeString(store);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }
    // Método para normalizar texto y eliminar acentos
    public static String normalizeText(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return normalized.toLowerCase(Locale.ROOT);
    }
    // Uso del método para comparar ingredientes ignorando acentos y mayúsculas/minúsculas
    public ArrayList<String> getHarmfulIngredients(String ingredientsText, ArrayList<String> allergyIngredients) {
        ArrayList<String> harmfulIngredients = new ArrayList<>();
        String normalizedIngredientsText = normalizeText(ingredientsText);

        for (String ingredient : allergyIngredients) {
            String normalizedIngredient = normalizeText(ingredient);
            if (normalizedIngredientsText.contains(normalizedIngredient)) {
                harmfulIngredients.add(ingredient);
            }
        }

        return harmfulIngredients;
    }
    public String getStore() {
        return store;
    }
}
