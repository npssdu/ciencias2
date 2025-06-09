package modelo;

import java.util.ArrayList;
import java.util.Arrays;

public class HashTruncamientoModel {
    private final String[] tabla;
    private final int tamano;
    private int[] posiciones; // 1-indexadas
    private final ArrayList<ArrayList<String>> tablaAnidada = new ArrayList<>();

    public HashTruncamientoModel(int tamano) {
        if (tamano <= 0) throw new IllegalArgumentException("Tamaño >0");
        this.tamano = tamano;
        this.tabla = new String[tamano];
        Arrays.fill(this.tabla, null);
        ArrayList<String> principal = new ArrayList<>(tamano);
        for (int i = 0; i < tamano; i++) principal.add(null);
        tablaAnidada.add(principal);
    }

    public int getTamano() {
        return tamano;
    }

    public void setPosiciones(int[] posiciones) {
        this.posiciones = posiciones;
    }

    /** Calcula hash por truncamento según posiciones, o por defecto */
    public int hashBase(String clave) {
        String s;
        if (posiciones == null || posiciones.length == 0) {
            int dig = String.valueOf(tamano - 1).length();
            s = clave.length() > dig
                ? clave.substring(clave.length() - dig)
                : clave;
        } else {
            StringBuilder sb = new StringBuilder();
            for (int p : posiciones) {
                int idx = p - 1;
                if (idx >= 0 && idx < clave.length())
                    sb.append(clave.charAt(idx));
            }
            s = sb.toString();
        }
        int v = Integer.parseInt(s) + 1;
        return v % tamano;
    }

    /**
     * Inserta clave:
     * -1 duplicada;
     * índice si éxito (lineal+1 wrap);
     * -2 tabla llena.
     * Llena `pasos` con detalle.
     */
    public int insertar(String clave, StringBuilder pasos) {
        // duplicada
        for (String e : tabla) if (clave.equals(e)) {
            pasos.append("Clave '").append(clave).append("' duplicada.\n");
            return -1;
        }
        int h0 = hashBase(clave);
        pasos.append("HashBase('").append(clave).append("') = ").append(h0).append("\n");
        for (int i = 0; i < tamano; i++) {
            int idx = (h0 + i) % tamano;
            pasos.append("  intento i=").append(i).append(" → idx=").append(idx);
            if (tabla[idx] == null) {
                tabla[idx] = clave;
                pasos.append(" → libre, insertado.\n");
                return idx;
            } else {
                pasos.append(" → colisión con '").append(tabla[idx]).append("'.\n");
            }
        }
        pasos.append("Tabla llena, no se puede insertar.\n");
        return -2;
    }

    /** Elimina por clave y rehash lineal para compactar */
    public boolean eliminar(String clave) {
        boolean ok = false;
        for (int i = 0; i < tamano; i++) {
            if (clave.equals(tabla[i])) {
                tabla[i] = null;
                ok = true;
                break;
            }
        }
        if (!ok) return false;
        // rehash
        String[] copia = Arrays.copyOf(tabla, tamano);
        Arrays.fill(tabla, null);
        for (String s : copia) {
            if (s != null) {
                int h0 = hashBase(s);
                for (int j = 0; j < tamano; j++) {
                    int idx = (h0 + j) % tamano;
                    if (tabla[idx] == null) {
                        tabla[idx] = s;
                        break;
                    }
                }
            }
        }
        return true;
    }

    /** Busca índice o -1 */
    public int buscar(String clave) {
        for (int i = 0; i < tamano; i++)
            if (clave.equals(tabla[i])) return i;
        return -1;
    }

    public String[] getTabla() {
        return tabla;
    }

    public int[] insertarEstructuraAnidada(String clave) {
        for (ArrayList<String> columna : tablaAnidada) {
            if (columna.contains(clave)) return new int[]{-1, -1};
        }
        int idx = hashBase(clave);
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

    public int[] insertarEstructuraEnlazada(String clave) {
        return insertarEstructuraAnidada(clave);
    }

    public ArrayList<ArrayList<String>> getTablaAnidada() {
        return tablaAnidada;
    }

    public void reiniciar() {
        Arrays.fill(this.tabla, null);
        tablaAnidada.clear();
        ArrayList<String> principal = new ArrayList<>(tamano);
        for (int i = 0; i < tamano; i++) principal.add(null);
        tablaAnidada.add(principal);
    }
}
