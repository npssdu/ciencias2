package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BusquedaLinealModel {
    private final List<String> datos;
    private final int tamano;

    public BusquedaLinealModel(int tamano) {
        if (tamano <= 0) throw new IllegalArgumentException("Tamaño debe ser >0");
        this.tamano = tamano;
        this.datos = new ArrayList<>(tamano);
    }

    public boolean insertar(String clave) {
        if (clave == null || clave.isEmpty() || datos.contains(clave) || datos.size() >= tamano)
            return false;
        datos.add(clave);
        return true;
    }

    public boolean eliminar(String clave) {
        return datos.remove(clave);
    }

    public int buscar(String clave) {
        return datos.indexOf(clave);
    }

    public void ordenar() {
        Collections.sort(datos);
    }

    public List<String> getDatos() {
        return datos;
    }

    public int getTamano() {
        return tamano;
    }

    public void reiniciar() {
        datos.clear();
    }
}
