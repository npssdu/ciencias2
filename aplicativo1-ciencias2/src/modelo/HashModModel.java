package modelo;

import java.util.ArrayList;
import java.util.Arrays;

public class HashModModel {
    private final String[] tabla;
    private final int tamano;
    // Estructuras anidadas/enlazadas
    private final ArrayList<ArrayList<String>> tablaAnidada;

    public HashModModel(int tamano) {
        if (tamano <= 0) throw new IllegalArgumentException("Tamaño >0");
        this.tamano = tamano;
        this.tabla = new String[tamano];
        Arrays.fill(this.tabla, null);
        this.tablaAnidada = new ArrayList<>();
        // Primera estructura (principal)
        this.tablaAnidada.add(new ArrayList<>(Arrays.asList(this.tabla)));
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

    /** Inserta usando estructuras anidadas. Devuelve [columna, fila] o [-1,-1] si duplicada o llena. */
    public int[] insertarEstructuraAnidada(String clave) {
        // Buscar si ya existe
        for (ArrayList<String> columna : tablaAnidada) {
            if (columna.contains(clave)) return new int[]{-1, -1};
        }
        int idx = hash(clave);
        // Columna 1 (índice 0) es la principal
        if (tablaAnidada.get(0).get(idx) == null) {
            tablaAnidada.get(0).set(idx, clave);
            return new int[]{0, idx};
        }
        // Si hay colisión, buscar la siguiente columna libre en esa fila
        for (int col = 1; col < 100; col++) {
            while (tablaAnidada.size() <= col) {
                ArrayList<String> nueva = new ArrayList<>(tamano);
                for (int i = 0; i < tamano; i++) nueva.add(null);
                tablaAnidada.add(nueva);
            }
            ArrayList<String> actual = tablaAnidada.get(col);
            if (actual.get(idx) == null) {
                actual.set(idx, clave);
                return new int[]{col, idx};
            }
        }
        return new int[]{-1, -1};
    }

    /** Inserta usando estructuras enlazadas. Devuelve [columna, fila] o [-1,-1] si duplicada o llena. */
    public int[] insertarEstructuraEnlazada(String clave) {
        return insertarEstructuraAnidada(clave);
    }

    /** Obtener la estructura anidada/enlazada completa */
    public ArrayList<ArrayList<String>> getTablaAnidada() {
        return tablaAnidada;
    }

    /** Reiniciar ambas estructuras */
    public void reiniciar() {
        Arrays.fill(this.tabla, null);
        tablaAnidada.clear();
        ArrayList<String> principal = new ArrayList<>(tamano);
        for (int i = 0; i < tamano; i++) principal.add(null);
        tablaAnidada.add(principal);
    }
}
