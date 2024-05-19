# Proyecto Aviones

## Descripción del Proyecto

En este proyecto desarrollaremos una API REST utilizando Spring Boot que se conectará a una base de datos. La API permitirá realizar operaciones CRUD (Crear, Leer, Actualizar y Eliminar) sobre un recurso llamado "Aviones". La implementación se realizará en Eclipse y será documentada y subida a GitHub. Además, crearemos un video explicativo en Stream para mostrar cómo se ha llevado a cabo el proyecto.

## Estructura del Proyecto

El proyecto se compondrá de las siguientes partes:

1. **Modelo (Aviones):**
   - Clase que representa la entidad "Aviones" en la base de datos.
   - Atributos típicos incluyen: id, modelo, fabricante, capacidad, entre otros.
   
2. **Controlador (AvionesController):**
   - Clase que manejará las solicitudes HTTP y definirá los métodos:
     - `GET`: Obtener una lista de todos los aviones o un avión específico por su id.
     - `POST`: Crear un nuevo avión.
     - `PUT`: Actualizar la información de un avión existente.
     - `DELETE`: Eliminar un avión por su id.

3. **Repositorio (AvionesRepository):**
   - Interfaz que extiende JpaRepository para proporcionar métodos CRUD automáticos.

4. **Servicio (AvionesService):**
   - Clase que contiene la lógica de negocio y se comunica con el repositorio.

5. **Configuración de Base de Datos:**
   - Archivo `application.properties` para configurar la conexión a la base de datos.

## Pasos para la Implementación

### 1. Configuración del Entorno
- **Instalar Eclipse IDE**: Descargar e instalar Eclipse IDE para Java Developers.
- **Instalar Spring Boot**: Configurar un nuevo proyecto Spring Boot en Eclipse.
- **Configurar Base de Datos**: Definir la base de datos en el archivo de configuración.

### 2. Creación del Modelo
- Definir la clase que representa la entidad "Avion" con los atributos necesarios y las anotaciones para la persistencia en la base de datos.

### 3. Creación del Repositorio
- Definir una interfaz que extiende JpaRepository para proporcionar métodos CRUD automáticos.

### 4. Creación del Servicio
- Implementar una clase de servicio que contenga la lógica de negocio y se comunique con el repositorio.

### 5. Creación del Controlador
- Implementar un controlador REST que maneje las solicitudes HTTP y utilice el servicio para realizar las operaciones CRUD.

### 6. Subir a GitHub
1. Crear un repositorio en GitHub.
2. Subir el código del proyecto al repositorio.

### 7. Creación del Video Explicativo
https://eepmad-my.sharepoint.com/:v:/g/personal/manuel-calvo1_eep-igroup_com/EQi3-_cK-FxHoSHLdkK8juABSkJtR8Yl4ZsU4xA6mcId6g?e=Wi6jYG&nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJTdHJlYW1XZWJBcHAiLCJyZWZlcnJhbFZpZXciOiJTaGFyZURpYWxvZy1MaW5rIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXcifX0%3D

## Conclusión

Este proyecto tiene como objetivo aprender y practicar el desarrollo de una API REST con Spring Boot, incluyendo la conexión a una base de datos y la implementación de operaciones CRUD. La documentación y el video explicativo ayudarán a comprender cada paso del proceso de desarrollo.