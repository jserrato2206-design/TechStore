# TechStore - Aplicación Móvil Android

## Descripción
TechStore es una aplicación móvil nativa para Android que simula una tienda online especializada en productos tecnológicos. La aplicación está diseñada para ofrecer una experiencia de compra moderna e intuitiva.

## Características Implementadas

### Pantallas Principales
- **Splash Screen**: Pantalla de carga con logo y animaciones
- **Pantalla de Bienvenida**: Punto de entrada con opciones de navegación
- **Login**: Autenticación de usuarios con validaciones
- **Registro**: Formulario de registro completo con validaciones
- **Perfil de Usuario**: Información del usuario y acciones rápidas

### Funcionalidades
- ✅ Navegación fluida entre pantallas
- ✅ Validaciones de formularios
- ✅ Autenticación simulada
- ✅ Persistencia de sesión de usuario
- ✅ Diseño responsivo con Material Design 3
- ✅ Gradientes y animaciones personalizadas

## Estructura del Proyecto

```
app/
├── src/main/
│   ├── java/com/techstore/
│   │   ├── SplashActivity.java
│   │   ├── WelcomeActivity.java
│   │   ├── LoginActivity.java
│   │   ├── RegisterActivity.java
│   │   └── ProfileActivity.java
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_splash.xml
│   │   │   ├── activity_welcome.xml
│   │   │   ├── activity_login.xml
│   │   │   ├── activity_register.xml
│   │   │   └── activity_profile.xml
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

## Credenciales de Prueba

Para probar la funcionalidad de login, usar las siguientes credenciales:
- **Email**: admin@tienda.com
- **Contraseña**: 123456

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

## Próximas Funcionalidades

- [ ] Catálogo de productos
- [ ] Carrito de compras
- [ ] Sistema de búsqueda y filtros
- [ ] Integración con APIs reales
- [ ] Notificaciones push
- [ ] Sistema de reviews y calificaciones

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
