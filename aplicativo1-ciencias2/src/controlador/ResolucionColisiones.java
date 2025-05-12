package controlador;

public class ResolucionColisiones {

    public static int resolverLineal(int[] tabla, int clave, int tamano) {
        int indice = clave % tamano;
        while (tabla[indice] != 0) {
            indice = (indice + 1) % tamano;
        }
        return indice;
    }

    public static int resolverCuadratica(int[] tabla, int clave, int tamano) {
        int indice = clave % tamano;
        int i = 1;
        while (tabla[indice] != 0) {
            indice = (clave + i * i) % tamano;
            i++;
        }
        return indice;
    }

    public static int resolverEstructurasAnidadas() {
        // Implementación de estructuras anidadas
        return -1; // Placeholder
    }

    public static int resolverEstructurasEnlazadas() {
        // Implementación de estructuras enlazadas
        return -1; // Placeholder
    }
}
