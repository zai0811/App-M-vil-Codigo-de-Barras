package com.example.alergin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.example.alergin.Producto.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Definir el nombre de la base de datos y la versión
    private static final String DATABASE_NAME = "alergiabd.db";
    private static final int DATABASE_VERSION = 12;

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
    private static final String TABLE_FAVORITES = "Favorites";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PRODUCT_NAME = "product_name";
    private static final String COLUMN_INGREDIENTS = "ingredients";
    private static final String COLUMN_HARMFUL_INGREDIENTS = "harmful_ingredients";
    private static final String COLUMN_STORE = "store";
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

        // Crea la tabla de Favoritos
        String CREATE_FAVORITES_TABLE = "CREATE TABLE " + TABLE_FAVORITES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PRODUCT_NAME + " TEXT,"
                + COLUMN_INGREDIENTS + " TEXT,"
                + COLUMN_HARMFUL_INGREDIENTS + " TEXT,"
                + COLUMN_STORE + " TEXT" + ")";
        db.execSQL(CREATE_FAVORITES_TABLE);
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
        // Especificar las alergias
        insertAllergy(db, "Gluten");
        insertAllergy(db, "Frutos secos");
        insertAllergy(db, "Lácteos");
        insertAllergy(db, "Diabetes");
        insertAllergy(db, "Presion Alta");
    }

    private void insertDefaultAllergyIngredients(SQLiteDatabase db) {
        // Insertar ingredientes específicos para cada categoría de alergia, incluyendo traducciones y consideraciones para las minúsculas y mayúsculas
            // Gluten y sus derivados
            insertAllergyIngredient(db, 1, "Gluten");
            insertAllergyIngredient(db, 1, "Trigo");
            insertAllergyIngredient(db, 1, "Cebada");
            insertAllergyIngredient(db, 1, "Centeno");
            insertAllergyIngredient(db, 1, "Avena");
            insertAllergyIngredient(db, 1, "WHEAT");
            insertAllergyIngredient(db, 1, "BARLEY");
            insertAllergyIngredient(db, 1, "RYE");
            insertAllergyIngredient(db, 1, "OATS");

            // Frutos secos y sus variedades comunes
            insertAllergyIngredient(db, 2, "Nueces");
            insertAllergyIngredient(db, 2, "Almendras");
            insertAllergyIngredient(db, 2, "Avellanas");
            insertAllergyIngredient(db, 2, "NUTS");
            insertAllergyIngredient(db, 2, "ALMONDS");
            insertAllergyIngredient(db, 2, "HAZELNUTS");

            // Lácteos
            insertAllergyIngredient(db, 3, "Lactosa");
            insertAllergyIngredient(db, 3, "Leche");
            insertAllergyIngredient(db, 3, "Queso");
            insertAllergyIngredient(db, 3, "LACTOSE");
            insertAllergyIngredient(db, 3, "MILK");
            insertAllergyIngredient(db, 3, "CHEESE");

            // Diabetes (sensibles a azúcares)
            insertAllergyIngredient(db, 4, "Azucar");
            insertAllergyIngredient(db, 4, "Glucosa");
            insertAllergyIngredient(db, 4, "SUGAR");
            insertAllergyIngredient(db, 4, "GLUCOSE");

            // Presión alta (sensibles a la sal)
            insertAllergyIngredient(db, 5, "Sal");
            insertAllergyIngredient(db, 5, "Sodio");
            insertAllergyIngredient(db, 5, "SALT");
            insertAllergyIngredient(db, 5, "SODIUM");

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

    // Método para obtener el nombre de la categoría por su ID
    public String getCategoryNameById(int categoryId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"Name"};
        String selection = "Id = ?";
        String[] selectionArgs = {String.valueOf(categoryId)};
        Cursor cursor = db.query("Categories", columns, selection, selectionArgs, null, null, null);
        String name = null;

        // Si se encuentra el resultado, obtener el nombre
        if (cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
        }
        cursor.close();
        return name;
    }
    // Método para obtener el código de la imagen de la categoría por su ID
    public String getCategoryImageCodeById(int categoryId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"ImageCode"};
        String selection = "Id = ?";
        String[] selectionArgs = {String.valueOf(categoryId)};
        Cursor cursor = db.query("Categories", columns, selection, selectionArgs, null, null, null);
        String imageCode = null;

        // Si se encuentra el resultado, obtener el código de la imagen
        if (cursor.moveToFirst()) {
            imageCode = cursor.getString(cursor.getColumnIndexOrThrow("ImageCode"));
        }
        cursor.close();
        return imageCode;
    }
    public ArrayList<SubCategory> getSubCategoriesByCategoryId(int categoryId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SubCategory> subCategories = new ArrayList<>();

        // Consulta SQL para obtener las subcategorías basadas en el ID de la categoría
        String SELECT_QUERY = "SELECT * FROM SubCategories WHERE CategoryId = ?";
        Cursor cursor = db.rawQuery(SELECT_QUERY, new String[]{String.valueOf(categoryId)});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("Id"));
                String name = cursor.getString(cursor.getColumnIndex("Name"));
                String imageCode = cursor.getString(cursor.getColumnIndex("ImageCode"));
                String videoURL = cursor.getString(cursor.getColumnIndex("VideoURL"));

                // Crear un nuevo objeto SubCategory y agregarlo a la lista
                subCategories.add(new SubCategory(id, name, imageCode, videoURL));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return subCategories;
    }
    // Método para agregar un favorito
    // Método para agregar un favorito
    public void addFavorite(String productName, String ingredients, ArrayList<String> harmfulIngredients, String store) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_NAME, productName);
        values.put(COLUMN_INGREDIENTS, ingredients);
        // Convierte harmfulIngredients de ArrayList a String si es necesario
        String harmfulIngredientsString = TextUtils.join(", ", harmfulIngredients);
        values.put(COLUMN_HARMFUL_INGREDIENTS, harmfulIngredientsString);
        values.put(COLUMN_STORE, store);
        db.insert(TABLE_FAVORITES, null, values);
        db.close();
    }


    // Método para obtener todos los favoritos
    public List<Product> getAllFavorites() {
        List<Product> favorites = new ArrayList<>();
        // Utilizando try-with-resources para auto-cerrar la base de datos y el cursor
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FAVORITES, null)) {

            int productNameIndex = cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME);
            int ingredientsIndex = cursor.getColumnIndexOrThrow(COLUMN_INGREDIENTS);
            int storeIndex = cursor.getColumnIndexOrThrow(COLUMN_STORE);
            int harmfulIngredientsIndex = cursor.getColumnIndexOrThrow(COLUMN_HARMFUL_INGREDIENTS);

            while (cursor.moveToNext()) {
                String productName = cursor.getString(productNameIndex);
                String ingredients = cursor.getString(ingredientsIndex);
                String store = cursor.getString(storeIndex);
                String harmfulIngredientsStr = cursor.getString(harmfulIngredientsIndex);
                ArrayList<String> harmfulIngredients = new ArrayList<>(Arrays.asList(harmfulIngredientsStr.split(", ")));

                // Crea un nuevo objeto Producto y lo añade a la lista
                favorites.add(new Product(productName, ingredients, harmfulIngredients, store));
            }
        } catch (Exception e) {
            // Manejar posibles excepciones
            Log.e("DatabaseHelper", "Error al cargar favoritos", e);
        }
        return favorites;
    }


}

