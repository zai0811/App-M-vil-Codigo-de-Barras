package com.example.alergin.Producto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

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

    public ArrayList<String> getHarmfulIngredients() {
        return harmfulIngredients;
    }

    public String getStore() {
        return store;
    }
}
