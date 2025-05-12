package modelo;

import java.util.LinkedList;

public class ResolucionColisiones {

    // Método de resolución de colisiones lineal
    public static int solucionLineal(String[] tabla, String clave, int hash, int tamano) {
        for (int i = 0; i < tamano; i++) {
            int pos = (hash + i) % tamano;
            if (tabla[pos] == null) {
                tabla[pos] = clave;
                return pos;
            }
        }
        return -1; // Tabla llena
    }

    // Método de resolución de colisiones cuadrática
    public static int solucionCuadratica(String[] tabla, String clave, int hash, int tamano) {
        for (int i = 0; i < tamano; i++) {
            int pos = (hash + i * i) % tamano;
            if (tabla[pos] == null) {
                tabla[pos] = clave;
                return pos;
            }
        }
        return -1; // Tabla llena
    }

    // Método de resolución de colisiones con estructuras anidadas
    public static int solucionEstructurasAnidadas(LinkedList<String>[] tabla, String clave, int hash) {
        if (tabla[hash] == null) {
            tabla[hash] = new LinkedList<>();
        }
        tabla[hash].add(clave);
        return hash;
    }

    // Método de resolución de colisiones con estructuras enlazadas
    public static int solucionEstructurasEnlazadas(LinkedList<String>[] tabla, String clave, int hash) {
        if (tabla[hash] == null) {
            tabla[hash] = new LinkedList<>();
        }
        tabla[hash].add(clave);
        return hash;
    }
}
