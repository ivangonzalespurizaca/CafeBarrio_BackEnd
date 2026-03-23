## Instrucciones de Ejecución

### 1. Requisitos del Entorno (Backend)

Para asegurar la correcta compilación y ejecución de la API, asegúrese de contar con:

* **JDK 17** o superior (Recomendado: OpenJDK 17).
* **Maven 3.8+** (El proyecto incluye el wrapper `./mvnw` para facilitar la ejecución).
* **MySQL Server 8.0** o superior.
* **Lombok**: Si utiliza un IDE como IntelliJ o Eclipse, asegúrese de tener instalado el plugin de Lombok para procesar las anotaciones de los modelos.

Siga estos pasos en orden para poner en marcha el sistema localmente.

### 2. Preparación de la Base de Datos (MySQL)
Antes de iniciar el servidor, es necesario preparar el esquema de datos:

1.  Abrir su gestor de base de datos MySQL Workbench.
2.  Localizar el archivo **`DB_Create.sql`** ubicado en la raíz del proyecto backend.
3.  Ejecutar el script completo. 
    * *Nota: Esto creará automáticamente la base de datos `db_cafeteria` y las tablas.*

---

### 3. Configuración del Backend (Spring Boot)
El proyecto está configurado para usar variables de entorno, lo que permite flexibilidad en la conexión. Tiene dos opciones para configurar sus credenciales:

#### **Opción A: Edición directa (Recomendada para pruebas rápidas)**
Edite el archivo `backend/src/main/resources/application.properties` y reemplace los valores por su usuario y contraseña local:

```properties
# Configuración de conexión local
spring.datasource.username=TU_USUARIO  # Ej: root
spring.datasource.password=TU_CLAVE    # Ej: su_password_aqui
```

#### **Opción B: Variables de Entorno**
Si prefiere no editar el archivo, puede configurar las siguientes variables de entorno en su IDE (IntelliJ, Eclipse, VS Code) o en su Sistema Operativo:
* `DB_USER`: Su usuario de MySQL.
* `DB_PASSWORD`: Su contraseña de MySQL.

---

### 4. Ejecución de los Proyectos

#### **Levantar el Backend**
1.  Navegar a la carpeta `/backend` desde su terminal o IDE.
2.  Ejecutar la clase principal desde su IDE o usar el comando Maven:
    ```bash
    ./mvnw spring-boot:run
    ```
    *El servidor iniciará por defecto en: `http://localhost:8080`*
*La aplicación estará disponible en el navegador: `http://localhost:4200`*
> **Datos de Prueba:** El proyecto incluye un componente **`DatabaseLoader`** que se ejecutará automáticamente al iniciar el servidor por primera vez. Este componente se encarga de insertar las categorías y productos de prueba necesarios para que el catálogo sea funcional desde el primer segundo.

---

### 5. Pruebas de Endpoints (Postman)

Se ha incluido una colección completa de Postman para facilitar las pruebas de la API.

**Cómo usarla:**
1. Importar el archivo `API_Cafeteria.postman_collection.json` en Postman.
2. Asegurarse de que el Backend esté ejecutándose en `http://localhost:8080`.
3. Podrá probar el flujo completo: desde listar productos hasta registrar un pedido y cambiar su estado.
