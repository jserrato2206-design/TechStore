# MANUAL DE USUARIO - TECHSTORE
## Aplicación Móvil Android

---

## TABLA DE CONTENIDOS

1. [Introducción](#introducción)
2. [Requisitos del Sistema](#requisitos-del-sistema)
3. [Instalación](#instalación)
4. [Primeros Pasos](#primeros-pasos)
5. [Gestión de Usuario](#gestión-de-usuario)
6. [Gestión de Productos](#gestión-de-productos)
7. [Carrito de Compras](#carrito-de-compras)
8. [Geolocalización](#geolocalización)
9. [Solución de Problemas](#solución-de-problemas)

---

## 1. INTRODUCCIÓN

TechStore es una aplicación móvil Android que permite gestionar una tienda de productos tecnológicos. La aplicación incluye funcionalidades completas de CRUD (Crear, Leer, Actualizar, Eliminar) para usuarios y productos, así como gestión de carrito de compras y geolocalización.

### Características Principales

- ✅ Sistema CRUD completo para usuarios y productos
- ✅ Gestión de carrito de compras
- ✅ Búsqueda y filtrado de productos
- ✅ Geolocalización de usuarios y tiendas
- ✅ Historial de pedidos
- ✅ Sistema de favoritos

---

## 2. REQUISITOS DEL SISTEMA

### Dispositivo
- Android 7.0 (API 24) o superior
- Mínimo 50 MB de espacio disponible
- GPS (opcional, para geolocalización)

### Permisos Requeridos
La aplicación solicitará los siguientes permisos:
- **Ubicación:** Para funcionalidades de geolocalización

---

## 3. INSTALACIÓN

### Paso 1: Descargar la Aplicación

**IMAGEN 1: Link de descarga del APK**
- Descripción: Captura de pantalla mostrando el link de GitHub donde se encuentra el APK
- Etiqueta: [Link: https://github.com/jserrato2206-design/TechStore.git]

**IMAGEN 2: Ubicación del APK en GitHub**
- Descripción: Captura mostrando la ruta: app/build/outputs/apk/debug/app-debug.apk
- Etiqueta: [Ruta del APK en el repositorio]

### Paso 2: Habilitar Instalación desde Fuentes Desconocidas

**IMAGEN 3: Configuración de Android - Seguridad**
- Descripción: Captura de pantalla de Configuración > Seguridad
- Etiqueta: [Flecha señalando opción "Fuentes desconocidas"]

1. Abre **Configuración** en tu dispositivo Android
2. Ve a **Seguridad** o **Aplicaciones**
3. Activa la opción **"Permitir instalación de aplicaciones de fuentes desconocidas"**

### Paso 3: Instalar el APK

**IMAGEN 4: Instalación del APK**
- Descripción: Captura mostrando el diálogo de instalación
- Etiqueta: [Botón "Instalar" resaltado]

1. Abre el archivo **app-debug.apk** descargado
2. Toca el botón **"Instalar"**
3. Espera a que termine la instalación
4. Toca **"Abrir"** para iniciar la aplicación

---

## 4. PRIMEROS PASOS

### 4.1. Pantalla de Inicio (Splash Screen)

**IMAGEN 5: Splash Screen**
- Descripción: Captura de la pantalla de carga inicial
- Etiqueta: [Logo de TechStore y animación de carga]

Al abrir la aplicación por primera vez, verás la pantalla de inicio con el logo de TechStore.

### 4.2. Pantalla de Bienvenida

**IMAGEN 6: Pantalla de Bienvenida**
- Descripción: Captura mostrando los botones "Iniciar Sesión" y "Registrarse"
- Etiqueta: [Flechas señalando ambos botones]

Esta pantalla te permite:
- **Iniciar Sesión:** Si ya tienes una cuenta
- **Registrarse:** Si eres un usuario nuevo

---

## 5. GESTIÓN DE USUARIO

### 5.1. Registrarse

**IMAGEN 7: Pantalla de Registro**
- Descripción: Captura del formulario de registro completo
- Etiqueta: [Números señalando cada campo: 1-Nombre, 2-Email, 3-Teléfono, 4-Contraseña, 5-Confirmar Contraseña]

**Pasos para registrarse:**

1. En la pantalla de Bienvenida, toca **"Registrarse"**
2. Completa el formulario:
   - **Nombre:** Ingresa tu nombre completo
   - **Email:** Ingresa un email válido (debe ser único)
   - **Teléfono:** Ingresa tu número de teléfono
   - **Contraseña:** Mínimo 6 caracteres
   - **Confirmar Contraseña:** Repite la contraseña
3. Toca el botón **"Crear Cuenta"**

**IMAGEN 8: Registro Exitoso**
- Descripción: Captura mostrando mensaje "Registro exitoso"
- Etiqueta: [Toast de confirmación visible]

Si el registro es exitoso, serás redirigido automáticamente a la pantalla de productos.

### 5.2. Iniciar Sesión

**IMAGEN 9: Pantalla de Login**
- Descripción: Captura del formulario de inicio de sesión
- Etiqueta: [Campos: Email y Contraseña señalados]

**Pasos para iniciar sesión:**

1. En la pantalla de Bienvenida, toca **"Iniciar Sesión"**
2. Ingresa tu **Email** y **Contraseña**
3. Toca el botón **"Iniciar Sesión"**

**IMAGEN 10: Login Exitoso**
- Descripción: Captura mostrando redirección a productos después del login
- Etiqueta: [Transición a pantalla de productos]

**Credenciales de Prueba:**
- Email: admin@techstore.com
- Contraseña: admin123

### 5.3. Recuperar Contraseña

**IMAGEN 11: Pantalla de Recuperación**
- Descripción: Captura de la pantalla "¿Olvidaste tu contraseña?"
- Etiqueta: [Campo de email y botón "Enviar"]

1. En la pantalla de Login, toca **"¿Olvidaste tu contraseña?"**
2. Ingresa tu email
3. Toca **"Enviar Enlace de Recuperación"**

### 5.4. Ver Perfil

**IMAGEN 12: Pantalla de Perfil**
- Descripción: Captura mostrando información del usuario
- Etiqueta: [Secciones: 1-Información del Usuario, 2-Acciones Rápidas]

**Para acceder al perfil:**

1. Desde cualquier pantalla, toca el icono de **Perfil** en el menú
2. Verás tu información:
   - Nombre
   - Email
   - Teléfono

**IMAGEN 13: Opciones del Perfil**
- Descripción: Captura mostrando botones: Editar Perfil, Mis Pedidos, Favoritos, Mi Ubicación, Ubicaciones de Tiendas
- Etiqueta: [Cada botón señalado con número]

### 5.5. Editar Perfil

**IMAGEN 14: Pantalla de Editar Perfil**
- Descripción: Captura del formulario de edición
- Etiqueta: [Campos editables señalados]

**Pasos para editar perfil:**

1. En la pantalla de Perfil, toca **"Editar Perfil"**
2. Modifica los campos que desees:
   - Nombre
   - Teléfono
   - Contraseña (opcional)
3. Toca **"Guardar Cambios"**

**IMAGEN 15: Perfil Actualizado**
- Descripción: Captura mostrando confirmación de actualización
- Etiqueta: [Mensaje de éxito visible]

### 5.6. Eliminar Cuenta

**IMAGEN 16: Diálogo de Confirmación**
- Descripción: Captura del diálogo "¿Está seguro de eliminar su cuenta?"
- Etiqueta: [Botones "Eliminar" y "Cancelar" señalados]

**Pasos para eliminar cuenta:**

1. En la pantalla de Perfil, toca **"Eliminar Cuenta"**
2. Confirma la eliminación en el diálogo
3. Tu cuenta y todos los datos asociados serán eliminados permanentemente

---

## 6. GESTIÓN DE PRODUCTOS

### 6.1. Ver Lista de Productos

**IMAGEN 17: Pantalla de Productos**
- Descripción: Captura mostrando la lista de productos en RecyclerView
- Etiqueta: [Elementos: 1-Barra de búsqueda, 2-Filtro por categoría, 3-Lista de productos, 4-Botón agregar producto (admin)]

**Características de la lista:**

- **Búsqueda:** Usa el campo de búsqueda para encontrar productos por nombre
- **Filtro:** Selecciona una categoría del spinner para filtrar
- **Scroll:** Desliza hacia abajo para ver más productos
- **Detalle:** Toca cualquier producto para ver sus detalles

**IMAGEN 18: Búsqueda de Productos**
- Descripción: Captura mostrando búsqueda en acción
- Etiqueta: [Campo de búsqueda con texto y resultados filtrados]

### 6.2. Ver Detalle de Producto

**IMAGEN 19: Pantalla de Detalle de Producto**
- Descripción: Captura mostrando información completa del producto
- Etiqueta: [Elementos: 1-Nombre, 2-Descripción, 3-Precio, 4-Categoría, 5-Stock, 6-Botón Agregar al Carrito]

**Información mostrada:**

- Nombre del producto
- Descripción detallada
- Precio formateado
- Categoría
- Stock disponible

**IMAGEN 20: Producto Agregado al Carrito**
- Descripción: Captura mostrando mensaje "Producto agregado al carrito"
- Etiqueta: [Toast de confirmación visible]

### 6.3. Crear Producto (Administrador)

**IMAGEN 21: Botón Agregar Producto**
- Descripción: Captura mostrando el botón "+" para agregar producto
- Etiqueta: [Botón flotante o en la barra superior señalado]

**Nota:** Solo usuarios administradores pueden crear productos.

**Pasos para crear producto:**

1. En la pantalla de Productos, toca el botón **"+"** o **"Agregar Producto"**
2. Completa el formulario:

**IMAGEN 22: Formulario de Crear Producto**
- Descripción: Captura del formulario completo
- Etiqueta: [Campos: 1-Nombre, 2-Descripción, 3-Precio, 4-Categoría, 5-Stock]

   - **Nombre:** Nombre del producto
   - **Descripción:** Descripción detallada
   - **Precio:** Precio en formato numérico
   - **Categoría:** Selecciona de la lista (Smartphones, Laptops, etc.)
   - **Stock:** Cantidad disponible

3. Toca **"Guardar"** para crear el producto

**IMAGEN 23: Producto Creado Exitosamente**
- Descripción: Captura mostrando el nuevo producto en la lista
- Etiqueta: [Producto recién creado visible en la lista]

### 6.4. Editar Producto

**IMAGEN 24: Botón Editar en Item de Producto**
- Descripción: Captura de un item de producto con botón de editar visible
- Etiqueta: [Icono de editar señalado]

**Pasos para editar producto:**

1. En la lista de productos, toca el icono de **"Editar"** en el producto deseado
2. Se abrirá el formulario con los datos actuales prellenados

**IMAGEN 25: Formulario de Edición**
- Descripción: Captura del formulario con datos existentes
- Etiqueta: [Campos con valores actuales visibles]

3. Modifica los campos que desees
4. Toca **"Guardar Cambios"**

**IMAGEN 26: Producto Actualizado**
- Descripción: Captura mostrando confirmación y producto actualizado en lista
- Etiqueta: [Mensaje de éxito y cambios reflejados]

### 6.5. Eliminar Producto

**IMAGEN 27: Botón Eliminar en Item de Producto**
- Descripción: Captura mostrando icono de eliminar
- Etiqueta: [Icono de papelera señalado]

**Pasos para eliminar producto:**

1. En la lista de productos, toca el icono de **"Eliminar"** (papelera)
2. Se mostrará un diálogo de confirmación

**IMAGEN 28: Diálogo de Confirmación de Eliminación**
- Descripción: Captura del diálogo "¿Eliminar este producto?"
- Etiqueta: [Botones "Eliminar" y "Cancelar" señalados]

3. Confirma la eliminación
4. El producto será eliminado de la base de datos

**IMAGEN 29: Producto Eliminado**
- Descripción: Captura mostrando que el producto ya no aparece en la lista
- Etiqueta: [Lista sin el producto eliminado]

---

## 7. CARRITO DE COMPRAS

### 7.1. Agregar Productos al Carrito

**IMAGEN 30: Agregar al Carrito desde Detalle**
- Descripción: Captura mostrando botón "Agregar al Carrito" en detalle de producto
- Etiqueta: [Botón "Agregar al Carrito" señalado]

**Métodos para agregar al carrito:**

1. **Desde el detalle del producto:**
   - Ve al detalle del producto
   - Toca **"Agregar al Carrito"**

2. **Desde la lista de productos:**
   - Toca el icono de carrito en el item del producto

**IMAGEN 31: Badge del Carrito**
- Descripción: Captura mostrando el contador de items en el carrito
- Etiqueta: [Número en el badge del carrito señalado]

### 7.2. Ver Carrito

**IMAGEN 32: Pantalla del Carrito**
- Descripción: Captura mostrando todos los items en el carrito
- Etiqueta: [Elementos: 1-Lista de items, 2-Cantidad, 3-Precio unitario, 4-Subtotal, 5-Total, 6-Botón Realizar Compra]

**Para acceder al carrito:**

1. Toca el icono del **Carrito** en la barra superior
2. O toca el botón flotante del carrito

**Información mostrada:**

- Lista de productos agregados
- Cantidad de cada producto
- Precio unitario
- Subtotal por producto
- **Total general** de la compra

**IMAGEN 33: Carrito Vacío**
- Descripción: Captura mostrando mensaje "Tu carrito está vacío"
- Etiqueta: [Mensaje y botón "Ir a la Tienda" visibles]

### 7.3. Modificar Cantidad

**IMAGEN 34: Controles de Cantidad**
- Descripción: Captura mostrando botones + y - para modificar cantidad
- Etiqueta: [Botones de incrementar y decrementar señalados]

**Pasos para modificar cantidad:**

1. En el carrito, localiza el producto
2. Usa los botones **"+"** y **"-"** para ajustar la cantidad
3. El subtotal y total se actualizarán automáticamente

**IMAGEN 35: Cantidad Modificada**
- Descripción: Captura mostrando cantidad actualizada y nuevo total
- Etiqueta: [Nueva cantidad y total recalculado visibles]

### 7.4. Eliminar Item del Carrito

**IMAGEN 36: Botón Eliminar Item**
- Descripción: Captura mostrando icono de eliminar en un item del carrito
- Etiqueta: [Icono de papelera en el item señalado]

**Pasos para eliminar item:**

1. En el carrito, toca el icono de **"Eliminar"** en el item deseado
2. El item será eliminado inmediatamente
3. El total se recalculará automáticamente

**IMAGEN 37: Item Eliminado del Carrito**
- Descripción: Captura mostrando carrito sin el item eliminado
- Etiqueta: [Lista actualizada y nuevo total]

### 7.5. Vaciar Carrito

**IMAGEN 38: Botón Vaciar Carrito**
- Descripción: Captura mostrando botón "Vaciar Carrito"
- Etiqueta: [Botón señalado]

**Pasos para vaciar carrito:**

1. En el carrito, toca **"Vaciar Carrito"**
2. Se mostrará un diálogo de confirmación
3. Confirma para eliminar todos los items

**IMAGEN 39: Carrito Vaciado**
- Descripción: Captura mostrando carrito vacío después de vaciar
- Etiqueta: [Mensaje "Carrito vacío" visible]

### 7.6. Realizar Compra

**IMAGEN 40: Botón Realizar Compra**
- Descripción: Captura mostrando botón "Realizar Compra" con total
- Etiqueta: [Botón y total señalados]

**Pasos para realizar compra:**

1. Revisa los items en tu carrito
2. Verifica el total
3. Toca **"Realizar Compra"**
4. Se mostrará un diálogo de confirmación

**IMAGEN 41: Diálogo de Confirmación de Compra**
- Descripción: Captura del diálogo "¿Confirmar compra?"
- Etiqueta: [Resumen de compra y botones de confirmación]

5. Confirma la compra
6. Se procesará la compra y se guardará en el historial

**IMAGEN 42: Compra Realizada**
- Descripción: Captura mostrando mensaje "Compra realizada exitosamente"
- Etiqueta: [Mensaje de éxito y carrito vacío]

---

## 8. GEOLOCALIZACIÓN

### 8.1. Establecer Mi Ubicación

**IMAGEN 43: Opción Mi Ubicación en Perfil**
- Descripción: Captura mostrando botón "Mi Ubicación" en perfil
- Etiqueta: [Botón señalado]

**Pasos para establecer ubicación:**

1. Ve a **Perfil**
2. Toca **"Mi Ubicación"**
3. La aplicación solicitará permisos de ubicación

**IMAGEN 44: Solicitud de Permisos de Ubicación**
- Descripción: Captura del diálogo de permisos
- Etiqueta: [Botón "Permitir" señalado]

4. Permite el acceso a la ubicación
5. La aplicación obtendrá tu ubicación actual

**IMAGEN 45: Ubicación Obtenida**
- Descripción: Captura mostrando coordenadas y dirección
- Etiqueta: [Latitud, Longitud y dirección visible]

### 8.2. Ver Ubicaciones de Tiendas

**IMAGEN 46: Opción Ubicaciones de Tiendas**
- Descripción: Captura mostrando botón "Ubicaciones de Tiendas"
- Etiqueta: [Botón señalado]

**Pasos para ver tiendas:**

1. En el Perfil, toca **"Ubicaciones de Tiendas"**
2. Se abrirá un mapa con todas las tiendas registradas

**IMAGEN 47: Mapa con Tiendas**
- Descripción: Captura del mapa mostrando marcadores de tiendas
- Etiqueta: [Marcadores de tiendas y ubicación del usuario señalados]

**Características del mapa:**

- Marcadores rojos: Ubicaciones de tiendas
- Marcador azul: Tu ubicación actual
- Toca un marcador para ver detalles de la tienda

**IMAGEN 48: Detalle de Tienda en Mapa**
- Descripción: Captura mostrando información de tienda al tocar marcador
- Etiqueta: [Nombre, dirección y distancia señalados]

---

## 9. FUNCIONALIDADES ADICIONALES

### 9.1. Favoritos

**IMAGEN 49: Agregar a Favoritos**
- Descripción: Captura mostrando botón "Agregar a Favoritos" en detalle de producto
- Etiqueta: [Botón señalado]

**Pasos:**

1. Ve al detalle de un producto
2. Toca **"Agregar a Favoritos"**
3. El producto se guardará en tu lista de favoritos

**IMAGEN 50: Ver Favoritos**
- Descripción: Captura mostrando lista de productos favoritos
- Etiqueta: [Lista de favoritos visible]

### 9.2. Historial de Pedidos

**IMAGEN 51: Historial de Pedidos**
- Descripción: Captura mostrando lista de pedidos realizados
- Etiqueta: [Pedidos con fecha, productos y totales señalados]

**Para acceder:**

1. Ve a **Perfil**
2. Toca **"Mis Pedidos"**
3. Verás tu historial completo de compras

---

## 10. SOLUCIÓN DE PROBLEMAS

### Problema: No puedo iniciar sesión

**Solución:**
- Verifica que el email y contraseña sean correctos
- Asegúrate de estar registrado primero
- Intenta recuperar tu contraseña

### Problema: No puedo agregar productos al carrito

**Solución:**
- Verifica que hayas iniciado sesión
- Asegúrate de que el producto tenga stock disponible
- Revisa los permisos de la aplicación

### Problema: No se obtiene la ubicación

**Solución:**
- Activa el GPS en tu dispositivo
- Otorga permisos de ubicación a la aplicación
- Verifica que tengas conexión a internet

### Problema: La aplicación se cierra inesperadamente

**Solución:**
- Reinicia la aplicación
- Verifica que tengas suficiente espacio disponible
- Desinstala y reinstala la aplicación

---

## 11. INFORMACIÓN ADICIONAL

### Link de la Aplicación en GitHub

**Repositorio:** https://github.com/jserrato2206-design/TechStore.git

**Ubicación del APK:**
- Ruta: `app/build/outputs/apk/debug/app-debug.apk`
- Descarga directa desde el repositorio

### Contacto y Soporte

Para reportar problemas o sugerencias, contacta al equipo de desarrollo a través del repositorio de GitHub.

---

## NOTAS FINALES

Este manual cubre todas las funcionalidades principales de TechStore. La aplicación está diseñada para ser intuitiva y fácil de usar. Si encuentras algún problema no mencionado aquí, consulta la sección de Solución de Problemas o contacta al soporte.

**TechStore** - Tu tienda de tecnología favorita 🚀

---

**Versión del Manual:** 1.0  
**Fecha:** 2024  
**Aplicación:** TechStore v1.0

