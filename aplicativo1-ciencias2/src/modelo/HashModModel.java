package modelo;

import java.util.Arrays;

public class HashModModel {
    private final String[] tabla;
    private final int tamano;

    public HashModModel(int tamano) {
        if (tamano <= 0) throw new IllegalArgumentException("Tamaño >0");
        this.tamano = tamano;
        this.tabla = new String[tamano];
        Arrays.fill(this.tabla, null);
    }

    public int getTamano() {
        return tamano;
    }

    /** Hash por módulo */
    private int hash(String clave) {
        int num = Integer.parseInt(clave);
        return num % tamano;
    }

    /**
     * Inserta clave, retorna:
     *  -1 si duplicada,
     *  índice si éxito (resuelto colisión lineal con wrap),
     *  -2 si tabla llena.
     */
    public int insertar(String clave) {
        // duplicada
        for (String s : tabla) if (clave.equals(s)) return -1;
        // búsqueda de hueco
        int idx = hash(clave);
        for (int i = 0; i < tamano; i++) {
            int pos = (idx + i) % tamano + 1;
            if (tabla[pos] == null) {
                tabla[pos] = clave;
                return pos;
            }
        }
        return -2; // llena
    }

    /** Elimina por clave, retorna true si existía */
    public boolean eliminar(String clave) {
        for (int i = 0; i < tamano; i++) {
            if (clave.equals(tabla[i])) {
                tabla[i] = null;
                // compactar: re‑reinsertar cluster
                String[] copia = Arrays.copyOf(tabla, tamano);
                Arrays.fill(tabla, null);
                for (String s : copia) if (s != null) {
                    insertar(s);
                }
                return true;
            }
        }
        return false;
    }

    /** Busca índice de clave o -1 */
    public int buscar(String clave) {
        for (int i = 0; i < tamano; i++) {
            if (clave.equals(tabla[i])) return i;
        }
        return -1;
    }

    public String[] getTabla() {
        return tabla;
    }

    public void reiniciar() {
        Arrays.fill(this.tabla, null);
    }
}
