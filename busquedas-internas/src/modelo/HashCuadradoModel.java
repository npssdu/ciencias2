package modelo;

import java.util.Arrays;

public class HashCuadradoModel {
    private final String[] tabla;
    private final int tamano;

    public HashCuadradoModel(int tamano) {
        if (tamano <= 0) throw new IllegalArgumentException("Tamaño debe ser >0");
        this.tamano = tamano;
        this.tabla = new String[tamano];
        Arrays.fill(this.tabla, null);
    }

    public int getTamano() {
        return tamano;
    }

    /** Método de hash base (sin resolución) */
    private int hashBase(String clave) {
        long num = Long.parseLong(clave);
        long cuadrado = num * num;
        String s = String.valueOf(cuadrado);
        int len = s.length();
        String extraido;
        if (len % 2 == 0) {
            int inicio = (len/2) - 1;
            extraido = s.substring(inicio, inicio+2);
        } else {
            int centro = len/2;
            if (centro-1>=0) extraido = s.substring(centro-1, centro+1);
            else extraido = s;
        }
        int val = Integer.parseInt(extraido) % tamano;
        return val + 1;
    }

    /**
     * Inserta clave, retorna:
     *  -1 si duplicada,
     *  índice si éxito (resuelto con sondeo cuadrático),
     *  -2 si tabla llena.
     * Además deja en `pasos` el texto del proceso para el terminal.
     */
    public int insertar(String clave, StringBuilder pasos) {
        // duplicada
        for (String s : tabla) if (clave.equals(s)) {
            pasos.append("Clave '").append(clave).append("' duplicada.\n");
            return -1;
        }
        int h0 = hashBase(clave);
        pasos.append("HashBase('").append(clave).append("') = ").append(h0).append("\n");
        // cuadrático: h0 + i^2
        for (int i = 0; i < tamano; i++) {
            int idx = (h0 + i*i) % tamano;
            pasos.append("  i=").append(i).append(" → idx=").append(idx);
            if (tabla[idx] == null) {
                tabla[idx] = clave;
                pasos.append(" → libre, insertado.\n");
                return idx;
            } else {
                pasos.append(" → colisión con '").append(tabla[idx]).append("'.\n");
            }
        }
        pasos.append("Tabla llena, no se pudo insertar.\n");
        return -2;
    }

    /** Elimina por clave, compacta con rehash cuadrático */
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
        // rehash todos
        String[] copia = Arrays.copyOf(tabla, tamano);
        Arrays.fill(tabla, null);
        for (String s : copia) {
            if (s != null) {
                hashInsertSinPaso(s);
            }
        }
        return true;
    }

    /** Inserta sin generar pasos (apoyo en eliminar) */
    private void hashInsertSinPaso(String clave) {
        int h0 = hashBase(clave);
        for (int i = 0; i < tamano; i++) {
            int idx = (h0 + i*i) % tamano;
            if (tabla[idx] == null) {
                tabla[idx] = clave;
                return;
            }
        }
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
