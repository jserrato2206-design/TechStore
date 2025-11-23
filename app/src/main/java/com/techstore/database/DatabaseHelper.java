package com.techstore.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.techstore.models.Product;
import com.techstore.models.User;
import com.techstore.models.CartItem;
import com.techstore.models.Order;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    
    private static final String DATABASE_NAME = "TechStore.db";
    private static final int DATABASE_VERSION = 3;
    
    // Tabla Usuarios
    private static final String TABLE_USERS = "users";
    private static final String COL_USER_ID = "id";
    private static final String COL_USER_NAME = "name";
    private static final String COL_USER_EMAIL = "email";
    private static final String COL_USER_PHONE = "phone";
    private static final String COL_USER_PASSWORD = "password";
    private static final String COL_USER_ROLE = "role";
    
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
    
    // Tabla Pedidos
    private static final String TABLE_ORDERS = "orders";
    private static final String COL_ORDER_ID = "id";
    private static final String COL_ORDER_USER_ID = "user_id";
    private static final String COL_ORDER_PRODUCT_ID = "product_id";
    private static final String COL_ORDER_PRODUCT_NAME = "product_name";
    private static final String COL_ORDER_QUANTITY = "quantity";
    private static final String COL_ORDER_PRICE = "price";
    private static final String COL_ORDER_TOTAL = "total";
    private static final String COL_ORDER_DATE = "order_date";
    
    // Tabla Favoritos
    private static final String TABLE_FAVORITES = "favorites";
    private static final String COL_FAVORITE_ID = "id";
    private static final String COL_FAVORITE_USER_ID = "user_id";
    private static final String COL_FAVORITE_PRODUCT_ID = "product_id";
    
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
                COL_USER_PASSWORD + " TEXT NOT NULL, " +
                COL_USER_ROLE + " TEXT NOT NULL DEFAULT 'user')";
        db.execSQL(createUsersTable);
        
        // Crear usuario administrador por defecto
        ContentValues adminValues = new ContentValues();
        adminValues.put(COL_USER_NAME, "Administrador");
        adminValues.put(COL_USER_EMAIL, "admin@techstore.com");
        adminValues.put(COL_USER_PHONE, "0000000000");
        adminValues.put(COL_USER_PASSWORD, "admin123");
        adminValues.put(COL_USER_ROLE, "admin");
        db.insert(TABLE_USERS, null, adminValues);
        
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
        
        // Crear tabla de pedidos
        String createOrdersTable = "CREATE TABLE " + TABLE_ORDERS + " (" +
                COL_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ORDER_USER_ID + " INTEGER NOT NULL, " +
                COL_ORDER_PRODUCT_ID + " INTEGER NOT NULL, " +
                COL_ORDER_PRODUCT_NAME + " TEXT NOT NULL, " +
                COL_ORDER_QUANTITY + " INTEGER NOT NULL, " +
                COL_ORDER_PRICE + " REAL NOT NULL, " +
                COL_ORDER_TOTAL + " REAL NOT NULL, " +
                COL_ORDER_DATE + " TEXT NOT NULL, " +
                "FOREIGN KEY(" + COL_ORDER_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COL_USER_ID + "), " +
                "FOREIGN KEY(" + COL_ORDER_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + COL_PRODUCT_ID + "))";
        db.execSQL(createOrdersTable);
        
        // Crear tabla de favoritos
        String createFavoritesTable = "CREATE TABLE " + TABLE_FAVORITES + " (" +
                COL_FAVORITE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_FAVORITE_USER_ID + " INTEGER NOT NULL, " +
                COL_FAVORITE_PRODUCT_ID + " INTEGER NOT NULL, " +
                "UNIQUE(" + COL_FAVORITE_USER_ID + ", " + COL_FAVORITE_PRODUCT_ID + "), " +
                "FOREIGN KEY(" + COL_FAVORITE_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COL_USER_ID + "), " +
                "FOREIGN KEY(" + COL_FAVORITE_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + COL_PRODUCT_ID + "))";
        db.execSQL(createFavoritesTable);
        
        // Insertar productos de ejemplo
        insertSampleProducts(db);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Agregar columna role si no existe
            try {
                db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COL_USER_ROLE + " TEXT NOT NULL DEFAULT 'user'");
                
                // Crear usuario admin si no existe
                Cursor cursor = db.query(TABLE_USERS, null, COL_USER_EMAIL + "=?", 
                        new String[]{"admin@techstore.com"}, null, null, null);
                if (!cursor.moveToFirst()) {
                    ContentValues adminValues = new ContentValues();
                    adminValues.put(COL_USER_NAME, "Administrador");
                    adminValues.put(COL_USER_EMAIL, "admin@techstore.com");
                    adminValues.put(COL_USER_PHONE, "0000000000");
                    adminValues.put(COL_USER_PASSWORD, "admin123");
                    adminValues.put(COL_USER_ROLE, "admin");
                    db.insert(TABLE_USERS, null, adminValues);
                }
                cursor.close();
            } catch (Exception e) {
                // Si falla, recrear la tabla
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
                onCreate(db);
            }
        }
        if (oldVersion < 3) {
            // Crear tabla de pedidos
            String createOrdersTable = "CREATE TABLE IF NOT EXISTS " + TABLE_ORDERS + " (" +
                    COL_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_ORDER_USER_ID + " INTEGER NOT NULL, " +
                    COL_ORDER_PRODUCT_ID + " INTEGER NOT NULL, " +
                    COL_ORDER_PRODUCT_NAME + " TEXT NOT NULL, " +
                    COL_ORDER_QUANTITY + " INTEGER NOT NULL, " +
                    COL_ORDER_PRICE + " REAL NOT NULL, " +
                    COL_ORDER_TOTAL + " REAL NOT NULL, " +
                    COL_ORDER_DATE + " TEXT NOT NULL)";
            db.execSQL(createOrdersTable);
            
            // Crear tabla de favoritos
            String createFavoritesTable = "CREATE TABLE IF NOT EXISTS " + TABLE_FAVORITES + " (" +
                    COL_FAVORITE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_FAVORITE_USER_ID + " INTEGER NOT NULL, " +
                    COL_FAVORITE_PRODUCT_ID + " INTEGER NOT NULL, " +
                    "UNIQUE(" + COL_FAVORITE_USER_ID + ", " + COL_FAVORITE_PRODUCT_ID + "))";
            db.execSQL(createFavoritesTable);
        }
    }
    
    // ========== OPERACIONES DE USUARIOS ==========
    
    public long insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_NAME, user.getName());
        values.put(COL_USER_EMAIL, user.getEmail());
        values.put(COL_USER_PHONE, user.getPhone());
        values.put(COL_USER_PASSWORD, user.getPassword());
        values.put(COL_USER_ROLE, user.getRole() != null ? user.getRole() : "user");
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
            int roleIndex = cursor.getColumnIndex(COL_USER_ROLE);
            String role = roleIndex >= 0 ? cursor.getString(roleIndex) : "user";
            user = new User(
                    cursor.getInt(cursor.getColumnIndex(COL_USER_ID)),
                    cursor.getString(cursor.getColumnIndex(COL_USER_NAME)),
                    cursor.getString(cursor.getColumnIndex(COL_USER_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(COL_USER_PHONE)),
                    cursor.getString(cursor.getColumnIndex(COL_USER_PASSWORD)),
                    role
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
    
    public User getUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, COL_USER_ID + "=?", 
                new String[]{String.valueOf(userId)}, null, null, null);
        
        User user = null;
        if (cursor.moveToFirst()) {
            int roleIndex = cursor.getColumnIndex(COL_USER_ROLE);
            String role = roleIndex >= 0 ? cursor.getString(roleIndex) : "user";
            user = new User(
                    cursor.getInt(cursor.getColumnIndex(COL_USER_ID)),
                    cursor.getString(cursor.getColumnIndex(COL_USER_NAME)),
                    cursor.getString(cursor.getColumnIndex(COL_USER_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(COL_USER_PHONE)),
                    cursor.getString(cursor.getColumnIndex(COL_USER_PASSWORD)),
                    role
            );
        }
        cursor.close();
        db.close();
        return user;
    }
    
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_NAME, user.getName());
        values.put(COL_USER_EMAIL, user.getEmail());
        values.put(COL_USER_PHONE, user.getPhone());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            values.put(COL_USER_PASSWORD, user.getPassword());
        }
        if (user.getRole() != null) {
            values.put(COL_USER_ROLE, user.getRole());
        }
        int rowsAffected = db.update(TABLE_USERS, values, COL_USER_ID + "=?", 
                new String[]{String.valueOf(user.getId())});
        db.close();
        return rowsAffected;
    }
    
    public int deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Primero eliminar registros relacionados
        db.delete(TABLE_CART, COL_CART_USER_ID + "=?", new String[]{String.valueOf(userId)});
        db.delete(TABLE_ORDERS, COL_ORDER_USER_ID + "=?", new String[]{String.valueOf(userId)});
        db.delete(TABLE_FAVORITES, COL_FAVORITE_USER_ID + "=?", new String[]{String.valueOf(userId)});
        // Luego eliminar el usuario
        int rowsAffected = db.delete(TABLE_USERS, COL_USER_ID + "=?", 
                new String[]{String.valueOf(userId)});
        db.close();
        return rowsAffected;
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
            // Smartphones
            {"iPhone 15 Pro", "Smartphone Apple con chip A17 Pro, 128GB, pantalla Super Retina XDR", "999.99", "Smartphones", "50", ""},
            {"iPhone 15", "Smartphone Apple con chip A16 Bionic, 128GB, pantalla 6.1 pulgadas", "799.99", "Smartphones", "60", ""},
            {"Samsung Galaxy S24", "Smartphone Android con pantalla AMOLED 6.2, 256GB", "899.99", "Smartphones", "45", ""},
            {"Samsung Galaxy S24 Ultra", "Smartphone premium con S Pen, 512GB, cámara 200MP", "1199.99", "Smartphones", "30", ""},
            {"Google Pixel 8 Pro", "Smartphone Google con Tensor G3, cámara avanzada", "899.99", "Smartphones", "40", ""},
            {"Xiaomi 14 Pro", "Smartphone con Snapdragon 8 Gen 3, pantalla 2K", "699.99", "Smartphones", "55", ""},
            {"OnePlus 12", "Smartphone con Snapdragon 8 Gen 3, carga rápida 100W", "799.99", "Smartphones", "35", ""},
            
            // Laptops
            {"MacBook Pro M3", "Laptop Apple con chip M3, 14 pulgadas, 512GB SSD", "1999.99", "Laptops", "30", ""},
            {"MacBook Air M2", "Laptop Apple con chip M2, 13 pulgadas, 256GB SSD", "1299.99", "Laptops", "40", ""},
            {"Dell XPS 15", "Laptop Dell con Intel i7, 16GB RAM, 512GB SSD, pantalla 4K", "1499.99", "Laptops", "25", ""},
            {"HP Spectre x360", "Laptop convertible 2-en-1, Intel i7, 16GB RAM", "1399.99", "Laptops", "20", ""},
            {"Lenovo ThinkPad X1", "Laptop empresarial con Intel i7, 16GB RAM, 512GB", "1599.99", "Laptops", "18", ""},
            {"ASUS ROG Strix", "Laptop gaming con RTX 4060, AMD Ryzen 9, 16GB RAM", "1699.99", "Laptops", "15", ""},
            {"Acer Predator Helios", "Laptop gaming con RTX 4070, Intel i7, 32GB RAM", "1899.99", "Laptops", "12", ""},
            
            // Tablets
            {"iPad Air", "Tablet Apple con chip M1, 64GB, pantalla 10.9 pulgadas", "599.99", "Tablets", "40", ""},
            {"iPad Pro 12.9", "Tablet Apple con chip M2, 256GB, pantalla 12.9 pulgadas", "1099.99", "Tablets", "25", ""},
            {"Samsung Galaxy Tab S9", "Tablet Android con pantalla 11 pulgadas, S Pen incluido", "699.99", "Tablets", "35", ""},
            {"Microsoft Surface Pro 9", "Tablet 2-en-1 con Windows 11, Intel i5, 256GB", "999.99", "Tablets", "20", ""},
            {"Lenovo Tab P12", "Tablet Android con pantalla 12.6 pulgadas, 256GB", "449.99", "Tablets", "30", ""},
            
            // Audio
            {"AirPods Pro", "Auriculares inalámbricos con cancelación de ruido activa", "249.99", "Audio", "60", ""},
            {"AirPods Max", "Auriculares over-ear premium con cancelación de ruido", "549.99", "Audio", "25", ""},
            {"Sony WH-1000XM5", "Auriculares over-ear con cancelación de ruido líder", "399.99", "Audio", "20", ""},
            {"Bose QuietComfort 45", "Auriculares con cancelación de ruido premium", "329.99", "Audio", "30", ""},
            {"JBL Flip 6", "Parlante Bluetooth portátil con sonido potente", "129.99", "Audio", "50", ""},
            {"Sonos Beam", "Barra de sonido para TV con Alexa integrada", "449.99", "Audio", "15", ""},
            {"Samsung Galaxy Buds2 Pro", "Auriculares inalámbricos con cancelación de ruido", "199.99", "Audio", "40", ""},
            
            // Componentes
            {"NVIDIA RTX 4090", "Tarjeta gráfica para gaming y diseño, 24GB GDDR6X", "1599.99", "Componentes", "15", ""},
            {"NVIDIA RTX 4080", "Tarjeta gráfica de alto rendimiento, 16GB GDDR6X", "1199.99", "Componentes", "20", ""},
            {"AMD Ryzen 9 7950X", "Procesador AMD de alto rendimiento, 16 núcleos", "699.99", "Componentes", "25", ""},
            {"Intel Core i9-13900K", "Procesador Intel de alto rendimiento, 24 núcleos", "589.99", "Componentes", "30", ""},
            {"Corsair Vengeance DDR5", "Memoria RAM 32GB DDR5 6000MHz", "199.99", "Componentes", "40", ""},
            {"Samsung 990 PRO SSD", "SSD NVMe 2TB de alta velocidad", "179.99", "Componentes", "35", ""},
            {"ASUS ROG Strix B650E", "Placa base AMD AM5 con WiFi 6E", "349.99", "Componentes", "20", ""},
            {"Corsair RM850x", "Fuente de alimentación 850W 80 Plus Gold", "149.99", "Componentes", "25", ""},
            
            // Consolas
            {"PlayStation 5", "Consola de videojuegos de nueva generación, 825GB SSD", "499.99", "Consolas", "30", ""},
            {"PlayStation 5 Digital", "Consola PS5 sin lector de discos, 825GB SSD", "399.99", "Consolas", "25", ""},
            {"Xbox Series X", "Consola Microsoft con 1TB de almacenamiento SSD", "499.99", "Consolas", "28", ""},
            {"Xbox Series S", "Consola Microsoft compacta, 512GB SSD", "299.99", "Consolas", "35", ""},
            {"Nintendo Switch OLED", "Consola portátil con pantalla OLED de 7 pulgadas", "349.99", "Consolas", "40", ""},
            {"Steam Deck", "Consola portátil para gaming PC, 256GB", "529.99", "Consolas", "15", ""},
            
            // Smart Home
            {"Amazon Echo Dot", "Altavoz inteligente con Alexa, 4ta generación", "49.99", "Smart Home", "60", ""},
            {"Google Nest Hub", "Pantalla inteligente de 7 pulgadas con Google Assistant", "99.99", "Smart Home", "40", ""},
            {"Philips Hue Starter Kit", "Kit de iluminación inteligente con 3 bombillas", "199.99", "Smart Home", "30", ""},
            {"Ring Video Doorbell", "Timbre inteligente con cámara HD y visión nocturna", "99.99", "Smart Home", "25", ""},
            {"Nest Thermostat", "Termostato inteligente con ahorro de energía", "129.99", "Smart Home", "20", ""},
            
            // Accesorios
            {"Apple Watch Series 9", "Reloj inteligente con GPS, pantalla Always-On", "399.99", "Accesorios", "35", ""},
            {"Samsung Galaxy Watch 6", "Reloj inteligente con monitor de salud avanzado", "299.99", "Accesorios", "30", ""},
            {"Logitech MX Master 3S", "Ratón inalámbrico ergonómico para productividad", "99.99", "Accesorios", "45", ""},
            {"Keychron K8 Pro", "Teclado mecánico inalámbrico con retroiluminación RGB", "119.99", "Accesorios", "25", ""},
            {"HyperX Cloud Alpha", "Auriculares gaming con sonido surround 7.1", "99.99", "Accesorios", "40", ""},
            {"Anker PowerBank 20000mAh", "Batería externa de alta capacidad con carga rápida", "49.99", "Accesorios", "50", ""}
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
    
    // ========== BÚSQUEDA Y FILTROS ==========
    
    public List<Product> searchProducts(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Product> products = new ArrayList<>();
        
        String selection = COL_PRODUCT_NAME + " LIKE ? OR " + COL_PRODUCT_DESCRIPTION + " LIKE ?";
        String[] selectionArgs = {"%" + query + "%", "%" + query + "%"};
        
        Cursor cursor = db.query(TABLE_PRODUCTS, null, selection, selectionArgs, null, null, null);
        
        if (cursor.moveToFirst()) {
            do {
                int imageIndex = cursor.getColumnIndex(COL_PRODUCT_IMAGE);
                String image = imageIndex >= 0 ? cursor.getString(imageIndex) : "";
                Product product = new Product(
                        cursor.getInt(cursor.getColumnIndex(COL_PRODUCT_ID)),
                        cursor.getString(cursor.getColumnIndex(COL_PRODUCT_NAME)),
                        cursor.getString(cursor.getColumnIndex(COL_PRODUCT_DESCRIPTION)),
                        cursor.getDouble(cursor.getColumnIndex(COL_PRODUCT_PRICE)),
                        cursor.getString(cursor.getColumnIndex(COL_PRODUCT_CATEGORY)),
                        cursor.getInt(cursor.getColumnIndex(COL_PRODUCT_STOCK)),
                        image
                );
                products.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return products;
    }
    
    public List<Product> getProductsByCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Product> products = new ArrayList<>();
        
        Cursor cursor = db.query(TABLE_PRODUCTS, null, COL_PRODUCT_CATEGORY + "=?", 
                new String[]{category}, null, null, null);
        
        if (cursor.moveToFirst()) {
            do {
                int imageIndex = cursor.getColumnIndex(COL_PRODUCT_IMAGE);
                String image = imageIndex >= 0 ? cursor.getString(imageIndex) : "";
                Product product = new Product(
                        cursor.getInt(cursor.getColumnIndex(COL_PRODUCT_ID)),
                        cursor.getString(cursor.getColumnIndex(COL_PRODUCT_NAME)),
                        cursor.getString(cursor.getColumnIndex(COL_PRODUCT_DESCRIPTION)),
                        cursor.getDouble(cursor.getColumnIndex(COL_PRODUCT_PRICE)),
                        cursor.getString(cursor.getColumnIndex(COL_PRODUCT_CATEGORY)),
                        cursor.getInt(cursor.getColumnIndex(COL_PRODUCT_STOCK)),
                        image
                );
                products.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return products;
    }
    
    public List<String> getAllCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> categories = new ArrayList<>();
        
        Cursor cursor = db.query(true, TABLE_PRODUCTS, new String[]{COL_PRODUCT_CATEGORY}, 
                null, null, null, null, COL_PRODUCT_CATEGORY + " ASC", null);
        
        if (cursor.moveToFirst()) {
            do {
                categories.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categories;
    }
    
    // ========== OPERACIONES DE PEDIDOS ==========
    
    public long createOrder(int userId, int productId, String productName, int quantity, double price, double total) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ORDER_USER_ID, userId);
        values.put(COL_ORDER_PRODUCT_ID, productId);
        values.put(COL_ORDER_PRODUCT_NAME, productName);
        values.put(COL_ORDER_QUANTITY, quantity);
        values.put(COL_ORDER_PRICE, price);
        values.put(COL_ORDER_TOTAL, total);
        values.put(COL_ORDER_DATE, java.text.SimpleDateFormat.getDateTimeInstance().format(new java.util.Date()));
        
        long id = db.insert(TABLE_ORDERS, null, values);
        db.close();
        return id;
    }
    
    public List<Order> getUserOrders(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Order> orders = new ArrayList<>();
        
        Cursor cursor = db.query(TABLE_ORDERS, null, COL_ORDER_USER_ID + "=?", 
                new String[]{String.valueOf(userId)}, null, null, COL_ORDER_DATE + " DESC");
        
        if (cursor.moveToFirst()) {
            do {
                Order order = new Order(
                        cursor.getInt(cursor.getColumnIndex(COL_ORDER_ID)),
                        cursor.getInt(cursor.getColumnIndex(COL_ORDER_USER_ID)),
                        cursor.getInt(cursor.getColumnIndex(COL_ORDER_PRODUCT_ID)),
                        cursor.getString(cursor.getColumnIndex(COL_ORDER_PRODUCT_NAME)),
                        cursor.getInt(cursor.getColumnIndex(COL_ORDER_QUANTITY)),
                        cursor.getDouble(cursor.getColumnIndex(COL_ORDER_PRICE)),
                        cursor.getDouble(cursor.getColumnIndex(COL_ORDER_TOTAL)),
                        cursor.getString(cursor.getColumnIndex(COL_ORDER_DATE))
                );
                orders.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return orders;
    }
    
    // ========== OPERACIONES DE FAVORITOS ==========
    
    public boolean addToFavorites(int userId, int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_FAVORITE_USER_ID, userId);
        values.put(COL_FAVORITE_PRODUCT_ID, productId);
        
        try {
            long id = db.insertWithOnConflict(TABLE_FAVORITES, null, values, SQLiteDatabase.CONFLICT_IGNORE);
            db.close();
            return id != -1;
        } catch (Exception e) {
            db.close();
            return false;
        }
    }
    
    public boolean removeFromFavorites(int userId, int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_FAVORITES, 
                COL_FAVORITE_USER_ID + "=? AND " + COL_FAVORITE_PRODUCT_ID + "=?", 
                new String[]{String.valueOf(userId), String.valueOf(productId)});
        db.close();
        return rowsAffected > 0;
    }
    
    public boolean isFavorite(int userId, int productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FAVORITES, null, 
                COL_FAVORITE_USER_ID + "=? AND " + COL_FAVORITE_PRODUCT_ID + "=?", 
                new String[]{String.valueOf(userId), String.valueOf(productId)}, 
                null, null, null);
        
        boolean isFavorite = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isFavorite;
    }
    
    public List<Product> getFavoriteProducts(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Product> products = new ArrayList<>();
        
        String query = "SELECT p.* FROM " + TABLE_PRODUCTS + " p " +
                "INNER JOIN " + TABLE_FAVORITES + " f ON p." + COL_PRODUCT_ID + " = f." + COL_FAVORITE_PRODUCT_ID + " " +
                "WHERE f." + COL_FAVORITE_USER_ID + " = ?";
        
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        
        if (cursor.moveToFirst()) {
            do {
                int imageIndex = cursor.getColumnIndex(COL_PRODUCT_IMAGE);
                String image = imageIndex >= 0 ? cursor.getString(imageIndex) : "";
                Product product = new Product(
                        cursor.getInt(cursor.getColumnIndex(COL_PRODUCT_ID)),
                        cursor.getString(cursor.getColumnIndex(COL_PRODUCT_NAME)),
                        cursor.getString(cursor.getColumnIndex(COL_PRODUCT_DESCRIPTION)),
                        cursor.getDouble(cursor.getColumnIndex(COL_PRODUCT_PRICE)),
                        cursor.getString(cursor.getColumnIndex(COL_PRODUCT_CATEGORY)),
                        cursor.getInt(cursor.getColumnIndex(COL_PRODUCT_STOCK)),
                        image
                );
                products.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return products;
    }
}
