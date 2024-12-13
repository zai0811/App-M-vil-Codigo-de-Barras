package com.example.alergin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Definir el nombre de la base de datos y la versión
    private static final String DATABASE_NAME = "alergiabd.db";
    private static final int DATABASE_VERSION = 10;

    // Información de los usuarios
    private static final String TABLE_USERS = "Users";
    private static final String KEY_USER_ID = "User_Id";
    private static final String KEY_USERNAME = "UserName";
    private static final String KEY_NAME = "Name";
    private static final String KEY_LAST_NAME = "LastName";
    private static final String KEY_PASSWORD = "Password";

    // Información de alergias
    private static final String TABLE_ALLERGIES = "Allergies";
    private static final String KEY_ALLERGY_ID = "Allergy_Id";
    private static final String KEY_ALLERGY_NAME = "Name";

    // Información de ingredientes dañinos
    private static final String TABLE_ALLERGY_INGREDIENTS = "AllergyIngredients";
    private static final String KEY_ALLERGY_INGREDIENT_ID = "Id";
    private static final String KEY_ALLERGY_INGREDIENT_NAME = "Ingredient";
    private static final String KEY_ALLERGY_INGREDIENT_ALLERGY_ID = "Allergy_Id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla Users
        String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + "(" +
                KEY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_USERNAME + " TEXT UNIQUE, " +
                KEY_NAME + " TEXT, " +
                KEY_LAST_NAME + " TEXT, " +
                KEY_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Crear tabla Allergies
        String CREATE_ALLERGIES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ALLERGIES + "(" +
                KEY_ALLERGY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_ALLERGY_NAME + " TEXT UNIQUE" + ")";
        db.execSQL(CREATE_ALLERGIES_TABLE);

        // Crear tabla AllergyIngredients
        String CREATE_ALLERGY_INGREDIENTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ALLERGY_INGREDIENTS + "(" +
                KEY_ALLERGY_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_ALLERGY_INGREDIENT_NAME + " TEXT, " +
                KEY_ALLERGY_INGREDIENT_ALLERGY_ID + " INTEGER, " +
                "FOREIGN KEY (" + KEY_ALLERGY_INGREDIENT_ALLERGY_ID + ") REFERENCES " + TABLE_ALLERGIES + "(" + KEY_ALLERGY_ID + "))";
        db.execSQL(CREATE_ALLERGY_INGREDIENTS_TABLE);

        // Insertar datos predeterminados
        insertDefaultUsers(db);
        insertDefaultAllergies(db);
        insertDefaultAllergyIngredients(db);
    }

    private void insertDefaultUsers(SQLiteDatabase db) {
        insertUser(db, "juan.perez", "Juan", "Pérez", "123");
        insertUser(db, "maria.gon", "Maria", "González", "456");
        insertUser(db, "carlos_lopez", "Carlos", "López", "789");
    }

    private void insertDefaultAllergies(SQLiteDatabase db) {
        insertAllergy(db, "Celíacos");
        insertAllergy(db, "Intolerantes a la lactosa");
        insertAllergy(db, "Alergia a frutos secos");
        insertAllergy(db, "Alergia a mariscos");
    }

    private void insertDefaultAllergyIngredients(SQLiteDatabase db) {
        insertAllergyIngredient(db, 1, "gluten"); // Celíacos
        insertAllergyIngredient(db, 2, "lactosa"); // Intolerancia a la lactosa
        insertAllergyIngredient(db, 3, "nueces"); // Alergia a frutos secos
        insertAllergyIngredient(db, 4, "mariscos"); // Alergia a mariscos
    }

    private void insertUser(SQLiteDatabase db, String username, String name, String lastName, String password) {
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, username);
        values.put(KEY_NAME, name);
        values.put(KEY_LAST_NAME, lastName);
        values.put(KEY_PASSWORD, password);
        db.insert(TABLE_USERS, null, values);
    }

    private void insertAllergy(SQLiteDatabase db, String allergyName) {
        ContentValues values = new ContentValues();
        values.put(KEY_ALLERGY_NAME, allergyName);
        db.insert(TABLE_ALLERGIES, null, values);
    }

    private void insertAllergyIngredient(SQLiteDatabase db, int allergyId, String ingredient) {
        ContentValues values = new ContentValues();
        values.put(KEY_ALLERGY_INGREDIENT_ALLERGY_ID, allergyId);
        values.put(KEY_ALLERGY_INGREDIENT_NAME, ingredient);
        db.insert(TABLE_ALLERGY_INGREDIENTS, null, values);
    }

    // Método para obtener todas las alergias
    public Cursor getAllAllergies() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ALLERGIES, null);
    }

    // Método para obtener los ingredientes dañinos para una alergia
    public ArrayList<String> getIngredientsForAllergy(int allergyId) {
        ArrayList<String> ingredients = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + KEY_ALLERGY_INGREDIENT_NAME +
                        " FROM " + TABLE_ALLERGY_INGREDIENTS +
                        " WHERE " + KEY_ALLERGY_INGREDIENT_ALLERGY_ID + " = ?",
                new String[]{String.valueOf(allergyId)});
        if (cursor.moveToFirst()) {
            do {
                ingredients.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ingredients;
    }

    // Método para obtener el ID de una alergia a partir de su nombre
    public int getAllergyIdByName(String allergyName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + KEY_ALLERGY_ID +
                        " FROM " + TABLE_ALLERGIES +
                        " WHERE " + KEY_ALLERGY_NAME + " = ?",
                new String[]{allergyName});
        int allergyId = -1;
        if (cursor.moveToFirst()) {
            allergyId = cursor.getInt(0);
        }
        cursor.close();
        return allergyId;
    }

    // Método para verificar las credenciales del usuario
    public boolean checkUserCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_USER_ID},
                KEY_USERNAME + "=? AND " + KEY_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }

    // Método para obtener los detalles del usuario
    public Cursor getUserDetails(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_USERS, new String[]{KEY_NAME, KEY_LAST_NAME},
                KEY_USERNAME + "=?", new String[]{username}, null, null, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALLERGIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALLERGY_INGREDIENTS);
        onCreate(db);
    }
}

