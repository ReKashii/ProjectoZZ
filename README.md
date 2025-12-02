# Huerto Hogar 🍎📱

Aplicación móvil nativa Android para la gestión y compra de productos agrícolas frescos. Desarrollada como parte de la Evaluación Parcial 4 y 5 de Desarrollo de Aplicaciones Móviles.

## 1. Integrantes
* **VICTOR GUTIERREZ**
* **RENATO CARCAMO**

## 2. Funcionalidades Implementadas
El proyecto cumple con los requisitos de arquitectura MVVM y conexión remota:
* **Catálogo Conectado:** Consumo de datos en tiempo real desde API REST.
* **Filtrado Dinámico:**
    * Búsqueda por texto (nombre/descripción).
    * Filtrado por categorías (`/api/categorias`) mediante Chips interactivos.
* **Carrito de Compras:** Lógica local para añadir, modificar cantidad y eliminar productos con cálculo de subtotal en tiempo real.
* **Persistencia Híbrida:** Uso de **Room Database** para almacenar productos y permitir funcionamiento *Offline-First* (si falla la red, carga datos locales).
* **Testing:** Pruebas unitarias (`CartTest`) validadas para la lógica de negocio.

## 3. Endpoints Utilizados
La aplicación consume la API alojada en Railway:
* **Base URL:** `https://api-dfs2-dm-production.up.railway.app/`
* **Microservicios / Endpoints:**
    * `GET /api/productos` - Obtención del catálogo completo con mapeo de atributos (Español -> Inglés).
    * `GET /api/categorias` - Obtención dinámica de categorías para filtros.

## 4. Pasos para Ejecutar
1. Clonar este repositorio.
2. Abrir en **Android Studio Koala** (o superior).
3. Esperar la sincronización de Gradle (Dependencies download).
4. Ejecutar en Emulador (API 34+) o Dispositivo Físico.
   * **Nota:** La app requiere conexión a internet para la primera carga de datos.

## 5. Evidencia de Entrega (APK Firmado + Keystore)
A continuación se evidencia la generación del archivo `.apk` en modo release y su llave `.jks` correspondiente:

<img width="1365" height="720" alt="imagen" src="https://github.com/user-attachments/assets/cf7b38fa-2923-46ad-8285-178ba1b06dff" />


*Ubicación en repositorio:*
* APK: `/app/release/app-release.apk`
* Keystore: `/app/keystore_huerto.jks`
