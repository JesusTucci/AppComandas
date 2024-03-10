# Comandas App

La **Comandas App** es una aplicación JavaFX para gestionar comandas en un restaurante.

## Características

- Visualización y gestión del estado de las mesas (ocupadas o vacías).
- Agregar y eliminar productos de las comandas de las mesas.
- Generación de facturas y mantenimiento del historial de mesas.
- Interfaz intuitiva y fácil de usar.

## Requisitos del Sistema

- Java 8 o superior.
- MySQL (u otro sistema de gestión de bases de datos compatible).

## Configuración de la Base de Datos

1. Ejecutar el script SQL proporcionado para crear la base de datos y las tablas.
2. Actualizar la configuración de la base de datos en la clase `DBManager`.

## Configuración y Ejecución

1. Clonar el repositorio:

    ```bash
    git clone https://github.com/tu_usuario/comandas-app.git
    ```

2. Abrir el proyecto en tu entorno de desarrollo favorito.

3. Configurar los parámetros de conexión a la base de datos en `DBManager.java`.

4. Ejecutar la aplicación desde la clase `App.java`.

## Uso

1. Al iniciar la aplicación, se mostrará una interfaz con la lista de mesas.
2. Selecciona una mesa para gestionar su comanda.
3. Agrega o elimina productos según sea necesario.
4. Genera facturas y mantén un historial actualizado.

## Contribuciones

¡Las contribuciones son bienvenidas! Si encuentras algún problema o tienes alguna mejora, no dudes en abrir un problema o enviar una solicitud de extracción.

## Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo [LICENSE](LICENSE) para obtener más detalles.
