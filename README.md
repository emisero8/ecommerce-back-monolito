# ecommerce-back-monolito

Backend monolítico de una aplicación de ecommerce (tienda de comida) construido con **Java 17** y **Spring Boot 3.5.14**. Expone una API REST para gestionar productos, pedidos, pagos y descuentos, utilizando una base de datos H2 en memoria con datos de ejemplo precargados.

---

## Tabla de contenidos

- [Tecnologías](#tecnologías)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Requisitos previos](#requisitos-previos)
  - [1. Instalar Java 17](#1-instalar-java-17)
  - [2. Instalar Maven](#2-instalar-maven)
- [Levantar el proyecto](#levantar-el-proyecto)
- [Base de datos H2](#base-de-datos-h2)
- [Datos de ejemplo](#datos-de-ejemplo)

---

## Tecnologías

| Tecnología | Versión |
|---|---|
| Java | 17 |
| Spring Boot | 3.5.14 |
| Spring Data JPA | (incluido en Spring Boot) |
| H2 Database | (en memoria) |
| Maven | 3.6+ |

---

## Estructura del proyecto

```
src/main/java/com/tienda/ecommerce/
├── producto/       # Módulo de productos (catálogo, stock)
├── pedido/         # Módulo de pedidos
├── pago/           # Módulo de pagos y descuentos
└── compartido/     # Utilidades y clases compartidas
```

Cada módulo sigue la misma estructura en capas:

```
<modulo>/
├── controlador/    # REST Controllers (@RestController)
├── dominio/        # Entidades JPA (@Entity)
├── dto/            # Data Transfer Objects
├── repositorio/    # Repositorios JPA (JpaRepository)
└── servicio/       # Lógica de negocio (@Service)
```

---

## Requisitos previos

### 1. Instalar Java 17

> **Verificar si ya lo tenés instalado** (PowerShell o CMD):
> ```powershell
> java -version
> ```
> Si la salida muestra `openjdk 17` o `java version "17"`, podés saltar este paso.

1. Ir a [https://openjdk.org/install/](https://openjdk.org/install/) y descargar el `.zip` de **Java 17** para **Windows x64**.
2. Descomprimir el `.zip` en una carpeta fija, por ejemplo:
   ```
   C:\java\jdk-17
   ```
3. Configurar las variables de entorno:
   - Abrir el menú inicio y buscar **"Editar las variables de entorno del sistema"**.
   - Hacer clic en **"Variables de entorno..."**.
   - En la sección **Variables del sistema**, hacer clic en **"Nueva..."** y completar:
     - Nombre: `JAVA_HOME`
     - Valor: `C:\java\jdk-17`
   - Luego, seleccionar la variable **`Path`**, hacer clic en **"Editar..."** y agregar una nueva entrada:
     - `%JAVA_HOME%\bin`
4. Aceptar todos los cuadros de diálogo, **abrir una nueva terminal** y verificar:
   ```powershell
   java -version
   # Esperado: openjdk version "17.x.x" ...
   ```


---

### 2. Instalar Maven

> El repositorio incluye el wrapper **`mvnw.cmd`**, por lo que **Maven no es obligatorio** — podés usar el wrapper directamente (ver [Levantar el proyecto](#levantar-el-proyecto)).  
> Si preferís tener Maven instalado globalmente, seguí estos pasos:

> **Verificar si ya lo tenés instalado** (PowerShell o CMD):
> ```powershell
> mvn -version
> ```

#### Opción A — Instalador oficial

1. Ir a [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)
2. Descargar el binario `apache-maven-3.x.x-bin.zip`.
3. Descomprimir en una carpeta (ej. `C:\tools\maven`).
4. Agregar `C:\tools\maven\bin` a la variable de entorno `PATH`:
   - Buscar **"Variables de entorno"** en el menú inicio.
   - En **Variables del sistema**, editar `Path` y agregar la ruta.
5. Reiniciar la terminal y verificar:
   ```powershell
   mvn -version
   # Esperado: Apache Maven 3.x.x ...
   ```
---

## Levantar el proyecto

### Con el Maven Wrapper (sin instalar Maven)

```powershell
.\mvnw.cmd spring-boot:run
```

### Con Maven instalado globalmente

```powershell
mvn spring-boot:run
```

Una vez iniciado, la API estará disponible en:

```
http://localhost:8080
```

---

## Base de datos H2

La aplicación usa una base de datos **H2 en memoria**. Los datos se crean al iniciar y se pierden al apagar la aplicación.

La consola web de H2 está habilitada y se puede acceder en:

```
http://localhost:8080/h2-console
```

| Campo | Valor |
|---|---|
| JDBC URL | `jdbc:h2:mem:ecommercedb` |
| Usuario | `sa` |
| Contraseña | *(vacía)* |

---

## Datos de ejemplo

Al iniciar la aplicación se ejecuta automáticamente el script `src/main/resources/data.sql`, que carga los siguientes datos de ejemplo:

### Productos

| Nombre | Categoría | Precio | Stock |
|---|---|---|---|
| Smash Burger | Burger | $135 | 50 |
| Pizza Margherita | Pizza | $120 | 40 |
| Taco de Pastor | Tacos | $55 | 80 |
| Pasta Carbonara | Pasta | $110 | 35 |
| Ensalada César | Ensalada | $85 | 60 |
| Agua de Jamaica | Bebida | $35 | 100 |

### Códigos de descuento

| Código | Descuento |
|---|---|
| `FOOD10` | 10% |
| `CLASE20` | 20% |