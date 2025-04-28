package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BusquedaBinariaModel {
    private final List<Integer> datos;
    private final int tamano;

    public BusquedaBinariaModel(int tamano) {
        if (tamano <= 0) throw new IllegalArgumentException("Tamaño debe ser >0");
        this.tamano = tamano;
        this.datos = new ArrayList<>(tamano);
    }

    public boolean insertar(int clave) {
        if (datos.contains(clave) || datos.size() >= tamano) {
            return false;
        }
        datos.add(clave);
        Collections.sort(datos);
        return true;
    }

    public boolean eliminar(int clave) {
        return datos.remove((Integer) clave);
    }

    /**
     * Retorna el índice (0-based) si existe, o -1 si no.
     * Usa Collections.binarySearch internamente.
     */
    public int buscar(int clave) {
        int idx = Collections.binarySearch(datos, clave);
        return idx >= 0 ? idx : -1;
    }

    public List<Integer> getDatos() {
        return datos;
    }

    public int getTamano() {
        return tamano;
    }

    public void reiniciar() {
        datos.clear();
    }
}
