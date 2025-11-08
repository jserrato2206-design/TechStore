package com.techstore.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.techstore.models.Product;
import com.techstore.models.User;
import com.techstore.models.CartItem;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    
    private static final String DATABASE_NAME = "TechStore.db";
    private static final int DATABASE_VERSION = 1;
    
    // Tabla Usuarios
    private static final String TABLE_USERS = "users";
    private static final String COL_USER_ID = "id";
    private static final String COL_USER_NAME = "name";
    private static final String COL_USER_EMAIL = "email";
    private static final String COL_USER_PHONE = "phone";
    private static final String COL_USER_PASSWORD = "password";
    
    // Tabla Productos
    private static final String TABLE_PRODUCTS = "products";
    private static final String COL_PRODUCT_ID = "id";
    private static final String COL_PRODUCT_NAME = "name";
    private static final String COL_PRODUCT_DESCRIPTION = "description";
    private static final String COL_PRODUCT_PRICE = "price";
    private static final String COL_PRODUCT_CATEGORY = "category";
    private static final String COL_PRODUCT_STOCK = "stock";
    private static final String COL_PRODUCT_IMAGE = "image";
    
    // Tabla Carrito
    private static final String TABLE_CART = "cart";
    private static final String COL_CART_ID = "id";
    private static final String COL_CART_USER_ID = "user_id";
    private static final String COL_CART_PRODUCT_ID = "product_id";
    private static final String COL_CART_QUANTITY = "quantity";
    
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla de usuarios
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER_NAME + " TEXT NOT NULL, " +
                COL_USER_EMAIL + " TEXT UNIQUE NOT NULL, " +
                COL_USER_PHONE + " TEXT NOT NULL, " +
                COL_USER_PASSWORD + " TEXT NOT NULL)";
        db.execSQL(createUsersTable);
        
        // Crear tabla de productos
        String createProductsTable = "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                COL_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PRODUCT_NAME + " TEXT NOT NULL, " +
                COL_PRODUCT_DESCRIPTION + " TEXT, " +
                COL_PRODUCT_PRICE + " REAL NOT NULL, " +
                COL_PRODUCT_CATEGORY + " TEXT NOT NULL, " +
                COL_PRODUCT_STOCK + " INTEGER NOT NULL, " +
                COL_PRODUCT_IMAGE + " TEXT)";
        db.execSQL(createProductsTable);
        
        // Crear tabla de carrito
        String createCartTable = "CREATE TABLE " + TABLE_CART + " (" +
                COL_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CART_USER_ID + " INTEGER NOT NULL, " +
                COL_CART_PRODUCT_ID + " INTEGER NOT NULL, " +
                COL_CART_QUANTITY + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + COL_CART_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COL_USER_ID + "), " +
                "FOREIGN KEY(" + COL_CART_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + COL_PRODUCT_ID + "))";
        db.execSQL(createCartTable);
        
        // Insertar productos de ejemplo
        insertSampleProducts(db);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
    
    // ========== OPERACIONES DE USUARIOS ==========
    
    public long insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_NAME, user.getName());
        values.put(COL_USER_EMAIL, user.getEmail());
        values.put(COL_USER_PHONE, user.getPhone());
        values.put(COL_USER_PASSWORD, user.getPassword());
        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }
    
    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, COL_USER_EMAIL + "=?", 
                new String[]{email}, null, null, null);
        
        User user = null;
        if (cursor.moveToFirst()) {
            user = new User(
                    cursor.getInt(cursor.getColumnIndex(COL_USER_ID)),
                    cursor.getString(cursor.getColumnIndex(COL_USER_NAME)),
                    cursor.getString(cursor.getColumnIndex(COL_USER_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(COL_USER_PHONE)),
                    cursor.getString(cursor.getColumnIndex(COL_USER_PASSWORD))
            );
        }
        cursor.close();
        db.close();
        return user;
    }
    
    public boolean validateUser(String email, String password) {
        User user = getUserByEmail(email);
        return user != null && user.getPassword().equals(password);
    }
    
    // ========== OPERACIONES DE PRODUCTOS (CRUD) ==========
    
    public long insertProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PRODUCT_NAME, product.getName());
        values.put(COL_PRODUCT_DESCRIPTION, product.getDescription());
        values.put(COL_PRODUCT_PRICE, product.getPrice());
        values.put(COL_PRODUCT_CATEGORY, product.getCategory());
        values.put(COL_PRODUCT_STOCK, product.getStock());
        values.put(COL_PRODUCT_IMAGE, product.getImage());
        long id = db.insert(TABLE_PRODUCTS, null, values);
        db.close();
        return id;
    }
    
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, null, null, null, null, null, COL_PRODUCT_NAME);
        
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product(
                        cursor.getInt(cursor.getColumnIndex(COL_PRODUCT_ID)),
                        cursor.getString(cursor.getColumnIndex(COL_PRODUCT_NAME)),
                        cursor.getString(cursor.getColumnIndex(COL_PRODUCT_DESCRIPTION)),
                        cursor.getDouble(cursor.getColumnIndex(COL_PRODUCT_PRICE)),
                        cursor.getString(cursor.getColumnIndex(COL_PRODUCT_CATEGORY)),
                        cursor.getInt(cursor.getColumnIndex(COL_PRODUCT_STOCK)),
                        cursor.getString(cursor.getColumnIndex(COL_PRODUCT_IMAGE))
                );
                products.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return products;
    }
    
    public Product getProductById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, null, COL_PRODUCT_ID + "=?", 
                new String[]{String.valueOf(id)}, null, null, null);
        
        Product product = null;
        if (cursor.moveToFirst()) {
            product = new Product(
                    cursor.getInt(cursor.getColumnIndex(COL_PRODUCT_ID)),
                    cursor.getString(cursor.getColumnIndex(COL_PRODUCT_NAME)),
                    cursor.getString(cursor.getColumnIndex(COL_PRODUCT_DESCRIPTION)),
                    cursor.getDouble(cursor.getColumnIndex(COL_PRODUCT_PRICE)),
                    cursor.getString(cursor.getColumnIndex(COL_PRODUCT_CATEGORY)),
                    cursor.getInt(cursor.getColumnIndex(COL_PRODUCT_STOCK)),
                    cursor.getString(cursor.getColumnIndex(COL_PRODUCT_IMAGE))
            );
        }
        cursor.close();
        db.close();
        return product;
    }
    
    public int updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PRODUCT_NAME, product.getName());
        values.put(COL_PRODUCT_DESCRIPTION, product.getDescription());
        values.put(COL_PRODUCT_PRICE, product.getPrice());
        values.put(COL_PRODUCT_CATEGORY, product.getCategory());
        values.put(COL_PRODUCT_STOCK, product.getStock());
        values.put(COL_PRODUCT_IMAGE, product.getImage());
        int rowsAffected = db.update(TABLE_PRODUCTS, values, COL_PRODUCT_ID + "=?", 
                new String[]{String.valueOf(product.getId())});
        db.close();
        return rowsAffected;
    }
    
    public int deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_PRODUCTS, COL_PRODUCT_ID + "=?", 
                new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }
    
    // ========== OPERACIONES DE CARRITO ==========
    
    public long addToCart(int userId, int productId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        // Verificar si el producto ya está en el carrito
        Cursor cursor = db.query(TABLE_CART, null, 
                COL_CART_USER_ID + "=? AND " + COL_CART_PRODUCT_ID + "=?",
                new String[]{String.valueOf(userId), String.valueOf(productId)}, 
                null, null, null);
        
        if (cursor.moveToFirst()) {
            // Actualizar cantidad
            int currentQuantity = cursor.getInt(cursor.getColumnIndex(COL_CART_QUANTITY));
            ContentValues values = new ContentValues();
            values.put(COL_CART_QUANTITY, currentQuantity + quantity);
            long id = cursor.getInt(cursor.getColumnIndex(COL_CART_ID));
            db.update(TABLE_CART, values, COL_CART_ID + "=?", new String[]{String.valueOf(id)});
            cursor.close();
            db.close();
            return id;
        } else {
            // Insertar nuevo item
            ContentValues values = new ContentValues();
            values.put(COL_CART_USER_ID, userId);
            values.put(COL_CART_PRODUCT_ID, productId);
            values.put(COL_CART_QUANTITY, quantity);
            long id = db.insert(TABLE_CART, null, values);
            cursor.close();
            db.close();
            return id;
        }
    }
    
    public List<CartItem> getCartItems(int userId) {
        List<CartItem> cartItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        String query = "SELECT c.*, p." + COL_PRODUCT_NAME + ", p." + COL_PRODUCT_PRICE + 
                ", p." + COL_PRODUCT_IMAGE + " FROM " + TABLE_CART + " c " +
                "INNER JOIN " + TABLE_PRODUCTS + " p ON c." + COL_CART_PRODUCT_ID + 
                " = p." + COL_PRODUCT_ID + " WHERE c." + COL_CART_USER_ID + "=?";
        
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        
        if (cursor.moveToFirst()) {
            do {
                CartItem item = new CartItem(
                        cursor.getInt(cursor.getColumnIndex(COL_CART_ID)),
                        cursor.getInt(cursor.getColumnIndex(COL_CART_USER_ID)),
                        cursor.getInt(cursor.getColumnIndex(COL_CART_PRODUCT_ID)),
                        cursor.getInt(cursor.getColumnIndex(COL_CART_QUANTITY)),
                        cursor.getString(cursor.getColumnIndex(COL_PRODUCT_NAME)),
                        cursor.getDouble(cursor.getColumnIndex(COL_PRODUCT_PRICE)),
                        cursor.getString(cursor.getColumnIndex(COL_PRODUCT_IMAGE))
                );
                cartItems.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cartItems;
    }
    
    public int updateCartQuantity(int cartId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CART_QUANTITY, quantity);
        int rowsAffected = db.update(TABLE_CART, values, COL_CART_ID + "=?", 
                new String[]{String.valueOf(cartId)});
        db.close();
        return rowsAffected;
    }
    
    public int removeFromCart(int cartId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_CART, COL_CART_ID + "=?", 
                new String[]{String.valueOf(cartId)});
        db.close();
        return rowsAffected;
    }
    
    public int clearCart(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_CART, COL_CART_USER_ID + "=?", 
                new String[]{String.valueOf(userId)});
        db.close();
        return rowsAffected;
    }
    
    public double getCartTotal(int userId) {
        List<CartItem> items = getCartItems(userId);
        double total = 0;
        for (CartItem item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
    
    // Insertar productos de ejemplo
    private void insertSampleProducts(SQLiteDatabase db) {
        String[][] products = {
            {"iPhone 15 Pro", "Smartphone Apple con chip A17 Pro, 128GB", "999.99", "Smartphones", "50", ""},
            {"Samsung Galaxy S24", "Smartphone Android con pantalla AMOLED 6.2", "899.99", "Smartphones", "45", ""},
            {"MacBook Pro M3", "Laptop Apple con chip M3, 14 pulgadas, 512GB", "1999.99", "Laptops", "30", ""},
            {"Dell XPS 15", "Laptop Dell con Intel i7, 16GB RAM, 512GB SSD", "1499.99", "Laptops", "25", ""},
            {"iPad Air", "Tablet Apple con chip M1, 64GB", "599.99", "Tablets", "40", ""},
            {"Samsung Galaxy Tab S9", "Tablet Android con pantalla 11 pulgadas", "699.99", "Tablets", "35", ""},
            {"AirPods Pro", "Auriculares inalámbricos con cancelación de ruido", "249.99", "Audio", "60", ""},
            {"Sony WH-1000XM5", "Auriculares over-ear con cancelación de ruido", "399.99", "Audio", "20", ""},
            {"NVIDIA RTX 4090", "Tarjeta gráfica para gaming y diseño", "1599.99", "Componentes", "15", ""},
            {"AMD Ryzen 9 7950X", "Procesador AMD de alto rendimiento", "699.99", "Componentes", "25", ""},
            {"PlayStation 5", "Consola de videojuegos de nueva generación", "499.99", "Consolas", "30", ""},
            {"Xbox Series X", "Consola Microsoft con 1TB de almacenamiento", "499.99", "Consolas", "28", ""}
        };
        
        for (String[] product : products) {
            ContentValues values = new ContentValues();
            values.put(COL_PRODUCT_NAME, product[0]);
            values.put(COL_PRODUCT_DESCRIPTION, product[1]);
            values.put(COL_PRODUCT_PRICE, Double.parseDouble(product[2]));
            values.put(COL_PRODUCT_CATEGORY, product[3]);
            values.put(COL_PRODUCT_STOCK, Integer.parseInt(product[4]));
            values.put(COL_PRODUCT_IMAGE, product[5]);
            db.insert(TABLE_PRODUCTS, null, values);
        }
    }
}
