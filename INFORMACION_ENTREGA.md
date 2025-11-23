# INFORMACIÓN DE ENTREGA - TECHSTORE

## LINK DE LA APLICACIÓN EN GITHUB

**Repositorio Principal:**
```
https://github.com/jserrato2206-design/TechStore.git
```

**Link Directo:**
https://github.com/jserrato2206-design/TechStore

---

## APLICACIÓN COMPLETA (APK)

### Ubicación del APK en el Repositorio

El archivo APK se encuentra en la siguiente ruta del repositorio:

```
app/build/outputs/apk/debug/app-debug.apk
```

### Cómo Descargar el APK

**Opción 1: Desde GitHub (Recomendado)**

1. Ve al repositorio: https://github.com/jserrato2206-design/TechStore
2. Navega a la carpeta: `app/build/outputs/apk/debug/`
3. Haz clic en el archivo `app-debug.apk`
4. Haz clic en el botón **"Download"** (botón de descarga)
5. El archivo se descargará a tu dispositivo

**Opción 2: Clonar el Repositorio**

```bash
git clone https://github.com/jserrato2206-design/TechStore.git
cd TechStore
# El APK está en: app/build/outputs/apk/debug/app-debug.apk
```

### Instalación del APK

1. **Habilitar Fuentes Desconocidas:**
   - Ve a Configuración > Seguridad
   - Activa "Permitir instalación de aplicaciones de fuentes desconocidas"

2. **Instalar:**
   - Abre el archivo `app-debug.apk` descargado
   - Toca "Instalar"
   - Espera a que termine la instalación
   - Toca "Abrir" para iniciar la aplicación

---

## DOCUMENTACIÓN TÉCNICA

La documentación técnica completa se encuentra en el archivo:
- `DOCUMENTACION_COMPLEMENTARIA.md` (en el repositorio local, no subido a GitHub)

---

## MANUAL DE USUARIO

### Archivo del Manual

El manual de usuario está disponible en:
- `MANUAL_USUARIO.md` (formato Markdown)

### Convertir a Word (.docx)

**Opción 1: Usando Pandoc (Recomendado)**

1. Instala Pandoc: https://pandoc.org/installing.html
2. Abre terminal/cmd en la carpeta del proyecto
3. Ejecuta:
```bash
pandoc MANUAL_USUARIO.md -o MANUAL_USUARIO.docx
```

**Opción 2: Usando Word Directamente**

1. Abre Microsoft Word
2. Ve a Archivo > Abrir
3. Selecciona `MANUAL_USUARIO.md`
4. Word lo convertirá automáticamente
5. Guarda como `.docx`

**Opción 3: Usando Herramientas Online**

- Markdown to Word: https://www.markdowntoword.com/
- Dillinger: https://dillinger.io/
- Copia el contenido de `MANUAL_USUARIO.md` y pégalo en la herramienta
- Exporta como Word

### Agregar Imágenes al Manual

El manual incluye **50 espacios designados para imágenes** con descripciones y etiquetas. Para completar el manual:

1. Toma capturas de pantalla de la aplicación funcionando
2. Reemplaza cada **"IMAGEN X"** con la captura correspondiente
3. Asegúrate de que las imágenes sean claras y de alta calidad
4. Mantén las etiquetas y descripciones proporcionadas

**Ubicaciones de las imágenes en el manual:**
- Cada sección tiene referencias claras como "IMAGEN 1", "IMAGEN 2", etc.
- Las descripciones indican exactamente qué debe mostrar cada imagen
- Las etiquetas indican qué elementos señalar en cada imagen

---

## VIDEO PROMOCIONAL

### Requisitos del Video

El video debe mostrar evidencias funcionales REALES de:

#### CRUD de Productos (Obligatorio)
- ✅ **CREAR:** Formulario completo, captura de imagen con cámara, producto apareciendo en lista
- ✅ **LEER:** Lista de productos, búsqueda funcionando, filtrado por categoría
- ✅ **ACTUALIZAR:** Edición de producto, cambio de información, actualización reflejada
- ✅ **ELIMINAR:** Eliminación de producto, confirmación, producto desapareciendo

#### CRUD de Carrito (Obligatorio)
- ✅ **AGREGAR:** Producto agregándose al carrito
- ✅ **VER:** Lista de items en carrito con cantidades y precios
- ✅ **MODIFICAR:** Cambio de cantidad funcionando
- ✅ **ELIMINAR:** Eliminación de items del carrito
- ✅ **COMPRA:** Proceso completo de realizar compra

### Estructura Sugerida

1. **Introducción (0:00-0:30)**
   - Presentación de TechStore
   - Propuesta de valor

2. **CRUD Productos (0:30-2:30)**
   - Crear producto con cámara (GRABACIÓN REAL)
   - Ver lista y buscar
   - Editar producto
   - Eliminar producto

3. **CRUD Carrito (2:30-4:00)**
   - Agregar al carrito
   - Ver carrito completo
   - Modificar cantidades
   - Eliminar items
   - Realizar compra

4. **Funcionalidades Adicionales (4:00-4:30)**
   - Geolocalización
   - Perfil de usuario

5. **Cierre (4:30-5:00)**
   - Resumen de funcionalidades

### Plataformas para Subir el Video

- YouTube (recomendado)
- Vimeo
- Google Drive (compartir link)
- Cualquier plataforma pública

**Importante:** El video debe mostrar grabaciones REALES de la aplicación funcionando, no mockups ni imágenes estáticas.

---

## RESUMEN DE ENTREGABLES

✅ **Aplicación Completa en GitHub:**
   - Link: https://github.com/jserrato2206-design/TechStore.git
   - APK disponible en: `app/build/outputs/apk/debug/app-debug.apk`

✅ **Documentación Técnica Completa:**
   - Archivo: `DOCUMENTACION_COMPLEMENTARIA.md`
   - Incluye: Diagramas, esquema de BD, CRUD, geolocalización, cámara, almacenamiento, depuración

✅ **Manual de Usuario:**
   - Archivo: `MANUAL_USUARIO.md`
   - Formato: Markdown (convertible a Word)
   - Incluye: 50 espacios para imágenes con descripciones y etiquetas
   - Instrucciones para conversión a Word incluidas

✅ **Video Promocional:**
   - Requisitos detallados en esta documentación
   - Debe evidenciar CRUD de productos y carrito
   - Grabaciones REALES de la aplicación

---

## INSTRUCCIONES PARA EVALUACIÓN

### Para Probar la Aplicación:

1. **Descargar el APK:**
   - Ve a: https://github.com/jserrato2206-design/TechStore
   - Descarga: `app/build/outputs/apk/debug/app-debug.apk`

2. **Instalar en dispositivo Android:**
   - Android 7.0 (API 24) o superior
   - Habilitar "Fuentes desconocidas"
   - Instalar el APK

3. **Probar Funcionalidades:**
   - Registrarse o usar: admin@techstore.com / admin123
   - Probar CRUD de productos
   - Probar CRUD de carrito
   - Probar geolocalización
   - Probar cámara y almacenamiento

### Para Revisar el Código:

1. **Clonar el repositorio:**
```bash
git clone https://github.com/jserrato2206-design/TechStore.git
```

2. **Abrir en Android Studio:**
   - Abrir Android Studio
   - File > Open > Seleccionar carpeta TechStore
   - Sincronizar proyecto Gradle

3. **Revisar Implementación:**
   - CRUD completo en DatabaseHelper.java
   - Activities en package com.techstore
   - Uso correcto de strings.xml (sin hardcodeo)
   - Permisos en AndroidManifest.xml

---

## CONTACTO

Para preguntas sobre la aplicación o el código, contactar a través del repositorio de GitHub.

---

**TechStore** - Sistema CRUD Android Completo  
**Repositorio:** https://github.com/jserrato2206-design/TechStore.git  
**Versión:** 1.0  
**Fecha:** 2024

