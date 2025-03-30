# BUSQUEDAS INTERNAS

Este proyecto es una aplicación de escritorio desarrollada en Java que integra diferentes algoritmos y funciones mediante una interfaz gráfica (GUI) utilizando Swing. La aplicación presenta un menú inicial que permite al usuario seleccionar entre distintos métodos y funciones para trabajar con estructuras de datos, donde cada opción despliega una ventana emergente con operaciones CRUD.


## Funcionalidades

### Menú Principal
- **Selección del método:**  
  Permite elegir entre:
  - Búsqueda Lineal
  - Búsqueda Binaria
  - Hash Mod
  - Hash Cuadrado
  - Hash Plegamiento
  - Hash Truncamiento

- **Tamaño de la estructura:**  
  El usuario ingresa el tamaño de la tabla o arreglo a utilizar.  
- **Interfaz de inicio:**  
  Al presionar "Iniciar", se abre la ventana correspondiente al método seleccionado.

### Búsqueda Lineal
- Se utiliza un arreglo de tamaño fijo.
- Permite realizar operaciones CRUD: Insertar, Actualizar, Eliminar, Buscar y Reiniciar.
- Al insertar, se muestra un mensaje indicando la clave, el índice de inserción y que, al ser una inserción secuencial, no se detectan colisiones.

### Búsqueda Binaria
- Se usa un `ArrayList` que se mantiene ordenado.
- Implementa la búsqueda binaria para localizar claves de forma eficiente.
- También permite las operaciones CRUD sobre la estructura.

### Funciones Hash
Cada función hash extiende una clase abstracta `HashModel` y se implementa de la siguiente manera:
- **Hash Mod:** Utiliza el módulo de la clave numérica.
- **Hash Cuadrado:** Eleva la clave al cuadrado y aplica el módulo.
- **Hash Plegamiento:** Divide la clave en dos partes y suma los valores resultantes antes de aplicar el módulo.
- **Hash Truncamiento:** Trunca los dígitos de la clave según el tamaño de la tabla y aplica el módulo.

En cada ventana de funciones hash se muestra un mensaje emergente indicando:
- La clave insertada.
- El índice donde se insertó.
- Si se produjo una colisión (cuando hay más de una clave en el mismo índice).

### Interfaz Gráfica
- La interfaz se ha desarrollado utilizando Swing.
- Se ha mejorado la apariencia visual de la ventana principal (MainMenuView) mediante:
  - Uso del Look and Feel Nimbus.
  - Personalización de fuentes y colores.
  - Aplicación de márgenes y paddings para un diseño más moderno.
- Cada ventana emergente (vista) para cada método presenta controles CRUD y una tabla para visualizar el estado actual de la estructura.

## Uso de la Aplicación

1. **Menú principal:**
  - Selecciona el método deseado del desplegable.
  - Ingresa el tamaño de la estructura.
  - Presiona "Iniciar" para abrir la ventana correspondiente.

2. **Ventanas de Operación:**
  - Realiza operaciones CRUD (Insertar, Actualizar, Eliminar, Buscar y Reiniciar) en la ventana emergente.
  - En el caso de las funciones hash, al insertar se mostrará un mensaje indicando la clave, el índice de inserción y si se detectó colisión.
  - La tabla se actualiza en tiempo real para reflejar el estado actual de la estructura.

## Personalización Visual

En `MainMenuView.java` se utiliza Nimbus Look and Feel junto con configuraciones específicas para:

- Fuentes: Se utiliza la fuente "Arial" con distintos estilos y tamaños.
- Colores: Se han aplicado colores personalizados a etiquetas y botones para mejorar la experiencia visual.

## Requisitos

- **Java JDK 8 o superior**
- Un IDE de Java (Visual Studio Code, Eclipse, IntelliJ IDEA, NetBeans, etc.) o compilación desde línea de comandos.

## Tecnologías Utilizadas

- **Lenguaje:** Java
- **Interfaz Gráfica:** Swing


## Autores

- Nelson David Posso Suárez (20212020132)
- Alicia Pineda Quiroga (20222020047)
- 