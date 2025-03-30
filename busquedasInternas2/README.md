# BUSQUEDAS INTERNAS

Este proyecto es una aplicación de escritorio desarrollada en Java que integra diferentes algoritmos y funciones mediante una interfaz gráfica (GUI) utilizando Swing. La aplicación presenta un menú inicial que permite al usuario seleccionar entre distintos métodos y funciones para trabajar con estructuras de datos, donde cada opción despliega una ventana emergente con operaciones CRUD.

## Funcionalidades

La aplicación ofrece las siguientes funcionalidades:

- **Menú Inicial:**
  - Selección de método o función.
  - Entrada del tamaño de la estructura (tabla o arreglo).

- **Algoritmos de Búsqueda:**
  - **Búsqueda Lineal:** Inserción, actualización, eliminación y búsqueda de claves en un arreglo.
  - **Búsqueda Binaria:** Inserción ordenada (de valores numéricos), actualización, eliminación y búsqueda utilizando búsqueda binaria.

- **Funciones Hash:**
  - **Hash Mod:** Aplica el operador módulo sobre la clave numérica.
  - **Hash Cuadrado:** Eleva al cuadrado la clave numérica y utiliza el módulo.
  - **Hash Plegamiento:** Divide la clave en dos partes iguales (o casi iguales), suma ambas partes y aplica el módulo.
  - **Hash Truncamiento:** Trunca los dígitos de la clave según el tamaño de la estructura y aplica el módulo.

- **Operaciones CRUD y Reinicio:**
  - Cada ventana permite insertar claves, actualizar, eliminar y reiniciar la estructura.
  - Se muestra una tabla actualizada con tres columnas: **Índice**, **Clave(s)** y **Colisiones** (indicando si existen múltiples claves en el mismo índice).

## Tecnologías Utilizadas

- **Lenguaje:** Java
- **Interfaz Gráfica:** Swing

## Requisitos

- Java Development Kit (JDK) 8 o superior.

## Cómo Compilar y Ejecutar

1. **Clonar o descargar el repositorio.**

2. **Compilar el código:**
   Abre una terminal en el directorio donde se encuentre el archivo `MainMenu.java` (o el archivo fuente principal) y ejecuta:
   ```bash
   javac MainMenu.java
