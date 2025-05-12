package modelo;

import java.util.Arrays;

public class HashPlegamientoModel {
    private final String[] tabla;
    private final int tamano;

    public HashPlegamientoModel(int tamano) {
        if (tamano <= 0) throw new IllegalArgumentException("Tamaño > 0");
        this.tamano = tamano;
        this.tabla = new String[tamano];
        Arrays.fill(this.tabla, null);
    }

    public int getTamano() {
        return tamano;
    }

    /**
     * Calcula el hash por plegamiento.
     * Ahora es público para uso externo (Controlador).
     */
    public int hashBase(String clave) {
        int groupSize = String.valueOf(tamano - 1).length();
        int suma = 0;
        for (int i = 0; i < clave.length(); i += groupSize) {
            int fin = Math.min(i + groupSize, clave.length());
            String grupo = clave.substring(i, fin);
            suma += Integer.parseInt(grupo);
        }
        return (suma % tamano) + 1;
    }

    /**
     * Inserta la clave en la tabla:
     *  -1 si duplicada
     *  índice si éxito (resuelto lineal +1 wrap)
     *  -2 si tabla llena.
     * Apendea pasos en el StringBuilder.
     */
    public int insertar(String clave, StringBuilder pasos) {
        // Duplicada
        for (String s : tabla) if (clave.equals(s)) {
            pasos.append("Clave '").append(clave).append("' duplicada.\n");
            return -1;
        }
        int h0 = hashBase(clave);
        pasos.append("HashBase('").append(clave).append("') = ").append(h0).append("\n");
        // Colisión lineal
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
        boolean found = false;
        for (int i = 0; i < tamano; i++) {
            if (clave.equals(tabla[i])) {
                tabla[i] = null;
                found = true;
                break;
            }
        }
        if (!found) return false;
        // Rehash de todos los elementos
        String[] copia = Arrays.copyOf(tabla, tamano);
        Arrays.fill(tabla, null);
        for (String s : copia) {
            if (s != null) {
                // Reinsertar sin pasos
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
        Arrays.fill(tabla, null);
    }
}
