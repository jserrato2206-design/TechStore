# TechStore - Aplicación Móvil Android

## Descripción
TechStore es una aplicación móvil nativa para Android que simula una tienda online especializada en productos tecnológicos. La aplicación está diseñada para ofrecer una experiencia de compra moderna e intuitiva.

## Características Implementadas

### Pantallas Principales
- **Splash Screen**: Pantalla de carga con logo y animaciones
- **Pantalla de Bienvenida**: Punto de entrada con opciones de navegación
- **Login**: Autenticación de usuarios con base de datos SQLite
- **Registro**: Formulario de registro completo con validaciones y almacenamiento en BD
- **Recuperación de Contraseña**: Sistema de recuperación
- **Listado de Productos**: RecyclerView con catálogo completo
- **Formulario de Productos**: Crear y editar productos (CRUD)
- **Detalle de Producto**: Vista detallada de cada producto
- **Carrito de Compras**: Gestión completa del carrito con persistencia
- **Perfil de Usuario**: Información del usuario y acciones rápidas

### Funcionalidades
- ✅ Base de datos SQLite con 3 tablas (users, products, cart)
- ✅ CRUD completo de productos (Create, Read, Update, Delete)
- ✅ RecyclerView para listado eficiente de productos
- ✅ Carrito de compras con persistencia en base de datos
- ✅ Sistema de autenticación con validación en BD
- ✅ Registro de usuarios con validación de email único
- ✅ Navegación fluida entre pantallas
- ✅ Validaciones completas de formularios
- ✅ Persistencia de sesión de usuario
- ✅ Diseño responsivo con Material Design 3
- ✅ Gradientes y animaciones personalizadas
- ✅ 12 productos de ejemplo precargados

## Estructura del Proyecto

```
app/
├── src/main/
│   ├── java/com/techstore/
│   │   ├── activities/
│   │   │   ├── SplashActivity.java
│   │   │   ├── WelcomeActivity.java
│   │   │   ├── LoginActivity.java
│   │   │   ├── RegisterActivity.java
│   │   │   ├── ForgotPasswordActivity.java
│   │   │   ├── ProductsActivity.java
│   │   │   ├── ProductFormActivity.java
│   │   │   ├── ProductDetailActivity.java
│   │   │   ├── CartActivity.java
│   │   │   └── ProfileActivity.java
│   │   ├── database/
│   │   │   └── DatabaseHelper.java
│   │   ├── models/
│   │   │   ├── User.java
│   │   │   ├── Product.java
│   │   │   └── CartItem.java
│   │   └── adapters/
│   │       ├── ProductAdapter.java
│   │       └── CartAdapter.java
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_splash.xml
│   │   │   ├── activity_welcome.xml
│   │   │   ├── activity_login.xml
│   │   │   ├── activity_register.xml
│   │   │   ├── activity_forgot_password.xml
│   │   │   ├── activity_products.xml
│   │   │   ├── activity_product_form.xml
│   │   │   ├── activity_product_detail.xml
│   │   │   ├── activity_cart.xml
│   │   │   ├── activity_profile.xml
│   │   │   ├── item_product.xml
│   │   │   └── item_cart.xml
│   │   ├── values/
│   │   │   ├── strings.xml
│   │   │   ├── colors.xml
│   │   │   └── themes.xml
│   │   └── drawable/
│   │       ├── gradient_background.xml
│   │       ├── button_primary.xml
│   │       ├── button_secondary.xml
│   │       ├── button_danger.xml
│   │       ├── card_background.xml
│   │       ├── circle_background.xml
│   │       └── ic_logo.xml
│   └── AndroidManifest.xml
├── build.gradle
└── proguard-rules.pro
```

## Requisitos del Sistema
- Android 7.0 (API 24) o superior
- Android Studio Arctic Fox o superior
- Java 8 o superior

## Instalación y Configuración

1. **Clonar el repositorio**:
   ```bash
   git clone [url-del-repositorio]
   cd TechStore
   ```

2. **Abrir en Android Studio**:
   - Abrir Android Studio
   - Seleccionar "Open an existing project"
   - Navegar a la carpeta del proyecto y seleccionarla

3. **Sincronizar el proyecto**:
   - Android Studio detectará automáticamente el proyecto Gradle
   - Hacer clic en "Sync Now" cuando aparezca la notificación

4. **Ejecutar la aplicación**:
   - Conectar un dispositivo Android o iniciar un emulador
   - Hacer clic en el botón "Run" (▶️) en Android Studio

## Base de Datos

### Estructura
- **Tabla users**: Almacena información de usuarios registrados
- **Tabla products**: Catálogo de productos tecnológicos
- **Tabla cart**: Items del carrito de compras por usuario

### Productos de Ejemplo
La base de datos se inicializa automáticamente con 12 productos de ejemplo en diferentes categorías:
- Smartphones (iPhone 15 Pro, Samsung Galaxy S24)
- Laptops (MacBook Pro M3, Dell XPS 15)
- Tablets (iPad Air, Samsung Galaxy Tab S9)
- Audio (AirPods Pro, Sony WH-1000XM5)
- Componentes (NVIDIA RTX 4090, AMD Ryzen 9 7950X)
- Consolas (PlayStation 5, Xbox Series X)

## Credenciales de Prueba

Para probar la funcionalidad, puedes:
1. **Registrarte** con cualquier email válido
2. **O usar un usuario existente** después del primer registro

## Características Técnicas

### Arquitectura
- **Patrón**: Actividades individuales con navegación por Intent
- **Persistencia**: SharedPreferences para datos de sesión
- **Validaciones**: Frontend con validaciones de formato y longitud

### Diseño
- **Tema**: Material Design 3 personalizado
- **Colores**: Paleta azul (#2196F3) como color primario
- **Tipografía**: Jerarquía clara con diferentes tamaños y pesos
- **Componentes**: CardView, TextInputLayout, botones personalizados

### Validaciones Implementadas
- ✅ Campos obligatorios
- ✅ Formato de email válido
- ✅ Longitud mínima de contraseña (6 caracteres)
- ✅ Confirmación de contraseña
- ✅ Validación de teléfono

## Funcionalidades CRUD

### Productos
- ✅ **Create**: Agregar nuevos productos al catálogo
- ✅ **Read**: Ver lista completa de productos en RecyclerView
- ✅ **Update**: Editar información de productos existentes
- ✅ **Delete**: Eliminar productos del catálogo

### Carrito de Compras
- ✅ Agregar productos al carrito
- ✅ Ver items del carrito con RecyclerView
- ✅ Modificar cantidades
- ✅ Eliminar items individuales
- ✅ Vaciar carrito completo
- ✅ Calcular total automáticamente
- ✅ Realizar compra (simulación)

## Próximas Funcionalidades

- [ ] Sistema de búsqueda y filtros por categoría
- [ ] Integración con APIs reales
- [ ] Notificaciones push
- [ ] Sistema de reviews y calificaciones
- [ ] Historial de compras
- [ ] Sistema de favoritos
- [ ] Integración con métodos de pago

## Contribución

1. Fork el proyecto
2. Crear una rama para la nueva funcionalidad (`git checkout -b feature/nueva-funcionalidad`)
3. Commit los cambios (`git commit -m 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abrir un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## Contacto

Para preguntas o sugerencias sobre el proyecto, contactar al equipo de desarrollo.

---

**TechStore** - Tu tienda de tecnología favorita 🚀
