package com.techstore;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.techstore.database.DatabaseHelper;
import com.techstore.models.Product;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProductFormActivity extends AppCompatActivity {
    
    private static final int REQUEST_CAMERA = 100;
    private static final int REQUEST_GALLERY = 101;
    private static final int REQUEST_CAMERA_PERMISSION = 102;
    private static final int REQUEST_STORAGE_PERMISSION = 103;
    
    private EditText etProductName, etProductDescription, etProductPrice, 
                     etProductCategory, etProductStock;
    private Button btnSave, btnTakePhoto, btnPickImage;
    private ImageButton btnBack;
    private ImageView ivProductImage;
    private DatabaseHelper dbHelper;
    private int productId = -1;
    private String currentImagePath = "";
    private Uri cameraImageUri;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);
        
        initViews();
        dbHelper = new DatabaseHelper(this);
        
        // Verificar si es edición
        productId = getIntent().getIntExtra("product_id", -1);
        if (productId != -1) {
            loadProduct();
        }
        
        setupClickListeners();
    }
    
    private void initViews() {
        etProductName = findViewById(R.id.etProductName);
        etProductDescription = findViewById(R.id.etProductDescription);
        etProductPrice = findViewById(R.id.etProductPrice);
        etProductCategory = findViewById(R.id.etProductCategory);
        etProductStock = findViewById(R.id.etProductStock);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);
        btnTakePhoto = findViewById(R.id.btnTakePhoto);
        btnPickImage = findViewById(R.id.btnPickImage);
        ivProductImage = findViewById(R.id.ivProductImage);
    }
    
    private void loadProduct() {
        Product product = dbHelper.getProductById(productId);
        if (product != null) {
            etProductName.setText(product.getName());
            etProductDescription.setText(product.getDescription());
            etProductPrice.setText(String.valueOf(product.getPrice()));
            etProductCategory.setText(product.getCategory());
            etProductStock.setText(String.valueOf(product.getStock()));
            currentImagePath = product.getImage();
            
            // Cargar imagen si existe
            if (!TextUtils.isEmpty(currentImagePath)) {
                loadImageFromPath(currentImagePath);
            }
            
            findViewById(R.id.tvTitle).setContentDescription("Editar Producto");
        }
    }
    
    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());
        btnSave.setOnClickListener(v -> saveProduct());
        btnTakePhoto.setOnClickListener(v -> openCamera());
        btnPickImage.setOnClickListener(v -> openGallery());
    }
    
    private void openCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) 
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, 
                    new String[]{Manifest.permission.CAMERA}, 
                    REQUEST_CAMERA_PERMISSION);
            return;
        }
        
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            File imageFile = createImageFile();
            if (imageFile != null) {
                try {
                    cameraImageUri = FileProvider.getUriForFile(this,
                            getApplicationContext().getPackageName() + ".fileprovider",
                            imageFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri);
                    cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    startActivityForResult(cameraIntent, REQUEST_CAMERA);
                } catch (Exception e) {
                    Toast.makeText(this, "Error al abrir la cámara: " + e.getMessage(), 
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Error al crear archivo de imagen", 
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.camera_permission_required), 
                    Toast.LENGTH_SHORT).show();
        }
    }
    
    private void openGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, 
                    Manifest.permission.READ_EXTERNAL_STORAGE) 
                    != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, 
                    Manifest.permission.READ_MEDIA_IMAGES) 
                    != PackageManager.PERMISSION_GRANTED) {
                String[] permissions;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissions = new String[]{Manifest.permission.READ_MEDIA_IMAGES};
                } else {
                    permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
                }
                ActivityCompat.requestPermissions(this, permissions, REQUEST_STORAGE_PERMISSION);
                return;
            }
        }
        
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, 
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_GALLERY);
    }
    
    private File createImageFile() {
        String imageFileName = "product_" + System.currentTimeMillis() + ".jpg";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (storageDir == null) {
            storageDir = getExternalFilesDir(null);
        }
        if (storageDir != null && !storageDir.exists()) {
            storageDir.mkdirs();
        }
        if (storageDir != null) {
            File imageFile = new File(storageDir, imageFileName);
            currentImagePath = imageFile.getAbsolutePath();
            return imageFile;
        }
        return null;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                if (cameraImageUri != null) {
                    loadImageFromPath(currentImagePath);
                    Toast.makeText(this, getString(R.string.image_saved), 
                            Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == REQUEST_GALLERY) {
                if (data != null && data.getData() != null) {
                    Uri selectedImageUri = data.getData();
                    try {
                        String savedPath = saveImageFromUri(selectedImageUri);
                        if (savedPath != null) {
                            currentImagePath = savedPath;
                            loadImageFromPath(currentImagePath);
                            Toast.makeText(this, getString(R.string.image_saved), 
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        Toast.makeText(this, getString(R.string.error_saving_image), 
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
    
    private String saveImageFromUri(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        if (inputStream == null) return null;
        
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        inputStream.close();
        
        File imageFile = createImageFile();
        FileOutputStream outputStream = new FileOutputStream(imageFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
        outputStream.flush();
        outputStream.close();
        
        return imageFile.getAbsolutePath();
    }
    
    private void loadImageFromPath(String imagePath) {
        if (!TextUtils.isEmpty(imagePath)) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                ivProductImage.setImageBitmap(bitmap);
            }
        }
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, 
                                          @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, getString(R.string.camera_permission_required), 
                        Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, getString(R.string.storage_permission_required), 
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    private void saveProduct() {
        String name = etProductName.getText().toString().trim();
        String description = etProductDescription.getText().toString().trim();
        String priceStr = etProductPrice.getText().toString().trim();
        String category = etProductCategory.getText().toString().trim();
        String stockStr = etProductStock.getText().toString().trim();
        
        if (validateInputs(name, description, priceStr, category, stockStr)) {
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(Double.parseDouble(priceStr));
            product.setCategory(category);
            product.setStock(Integer.parseInt(stockStr));
            product.setImage(currentImagePath);
            
            if (productId != -1) {
                product.setId(productId);
                dbHelper.updateProduct(product);
                Toast.makeText(this, "Producto actualizado", Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.insertProduct(product);
                Toast.makeText(this, "Producto creado", Toast.LENGTH_SHORT).show();
            }
            
            setResult(RESULT_OK);
            finish();
        }
    }
    
    private boolean validateInputs(String name, String description, String priceStr, 
                                   String category, String stockStr) {
        boolean isValid = true;
        
        if (TextUtils.isEmpty(name)) {
            etProductName.setError("Campo obligatorio");
            isValid = false;
        }
        
        if (TextUtils.isEmpty(description)) {
            etProductDescription.setError("Campo obligatorio");
            isValid = false;
        }
        
        if (TextUtils.isEmpty(priceStr)) {
            etProductPrice.setError("Campo obligatorio");
            isValid = false;
        } else {
            try {
                double price = Double.parseDouble(priceStr);
                if (price <= 0) {
                    etProductPrice.setError("El precio debe ser mayor a 0");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                etProductPrice.setError("Precio inválido");
                isValid = false;
            }
        }
        
        if (TextUtils.isEmpty(category)) {
            etProductCategory.setError("Campo obligatorio");
            isValid = false;
        }
        
        if (TextUtils.isEmpty(stockStr)) {
            etProductStock.setError("Campo obligatorio");
            isValid = false;
        } else {
            try {
                int stock = Integer.parseInt(stockStr);
                if (stock < 0) {
                    etProductStock.setError("El stock no puede ser negativo");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                etProductStock.setError("Stock inválido");
                isValid = false;
            }
        }
        
        return isValid;
    }
}
