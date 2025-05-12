package modelo;

import java.util.ArrayList;

public abstract class HashModel {
    protected ArrayList<String>[] tabla;
    protected int tamanoTabla;

    @SuppressWarnings("unchecked")
    public HashModel(int tamanoTabla) {
        this.tamanoTabla = tamanoTabla;
        tabla = new ArrayList[tamanoTabla];
        for (int i = 0; i < tamanoTabla; i++) {
            tabla[i] = new ArrayList<>();
        }
    }

    // Método abstracto para calcular el índice
    protected abstract int calcularHash(String clave);

    // Método para obtener el índice sin insertar
    public int getIndiceDeInsercion(String clave) {
        return calcularHash(clave);
    }

    // Método de búsqueda: recorre toda la tabla para encontrar la clave
    public int buscar(String clave) {
        for (int i = 0; i < tamanoTabla; i++) {
            if (tabla[i].contains(clave)) {
                return i;
            }
        }
        return -1;
    }

    // Método insertar modificado para no permitir claves repetidas
    public boolean insertar(String clave) {
        // Si la clave ya existe en la tabla, no se inserta
        if (buscar(clave) != -1) {
            return false;
        }
        int indice = calcularHash(clave);
        if (indice == -1)
            return false;
        tabla[indice].add(clave);
        return true;
    }

    public ArrayList<String>[] getTabla() {
        return tabla;
    }

    public void actualizar(String claveAntigua, String claveNueva) {
        for (int i = 0; i < tamanoTabla; i++) {
            if (tabla[i].contains(claveAntigua)) {
                tabla[i].remove(claveAntigua);
                int nuevoIndice = calcularHash(claveNueva);
                tabla[nuevoIndice].add(claveNueva);
                break;
            }
        }
    }

    public void eliminar(String clave) {
        for (int i = 0; i < tamanoTabla; i++) {
            if (tabla[i].contains(clave)) {
                tabla[i].remove(clave);
                break;
            }
        }
    }

    public void reiniciar() {
        for (int i = 0; i < tamanoTabla; i++) {
            tabla[i].clear();
        }
    }

    public int getTamanoTabla() {
        return tamanoTabla;
    }
}
