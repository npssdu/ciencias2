package modelo;

import java.util.ArrayList;
import java.util.List;

public class ArbolesBResiduosMultiplesModel {
    public class Node {
        public Character data;
        public List<Node> children;

        public Node() {
            this.data = null;
            this.children = new ArrayList<>();
        }
    }

    private final Node root;
    private int M = 0;
    private int numChildren = 0;

    public ArbolesBResiduosMultiplesModel() {
        this.root = new Node();
    }

    /** Configura M (bits por rama) y calcula 2^M */
    public void setM(int M) {
        this.M = M;
        this.numChildren = (int) Math.pow(2, M);
    }

    public int getM() {
        return M;
    }

    public int getNumChildren() {
        return numChildren;
    }

    public Node getRoot() {
        return root;
    }

    /** Convierte carácter a binario de 5 bits (últimos 5 bits de ASCII) */
    private String to5BitBinary(char c) {
        String bin = Integer.toBinaryString((int)c);
        if (bin.length() > 5) {
            bin = bin.substring(bin.length() - 5);
        } else {
            bin = String.format("%5s", bin).replace(' ', '0');
        }
        return bin;
    }

    /**
     * Inserta una letra paso a paso:
     * - Toma ASCII → 5 bits.
     * - Recorre segmentos de M bits:
     *   * Si el hijo en ese índice es null ⇒ INSERCIÓN DIRECTA. Fin.
     *   * Si el hijo existe y data==null ⇒ baja sin crear nodo.
     *   * Si el hijo existe y data!=null ⇒ colisión ⇒ baja y continúa.
     */
    public String insertarConPasos(char letra) {
        StringBuilder pasos = new StringBuilder();
        int ascii = (int) letra;
        String bits5 = to5BitBinary(letra);
        pasos.append("Letra '").append(letra)
             .append("' → ASCII=").append(ascii)
             .append(", bits5=").append(bits5).append("\n");

        Node curr = root;
        int idxBit = 0;
        while (idxBit < bits5.length()) {
            int end = Math.min(idxBit + M, bits5.length());
            String seg = bits5.substring(idxBit, end);
            int branch = Integer.parseInt(seg, 2);
            pasos.append("Segmento '").append(seg)
                 .append("' → rama ").append(branch).append("\n");

            // ampliar lista de hijos si es necesario
            while (curr.children.size() <= branch) {
                curr.children.add(null);
            }

            Node child = curr.children.get(branch);
            if (child == null) {
                // espacio libre: inserción directa
                pasos.append("  Espacio libre → insertar aquí sin crear nodo extra.\n");
                child = new Node();
                child.data = letra;
                curr.children.set(branch, child);
                pasos.append("  Letra '").append(letra).append("' insertada en rama ").append(branch).append("\n");
                return pasos.toString();
            }

            if (child.data == null) {
                // nodo vacío existente: descendemos sin crear
                pasos.append("  Nodo vacío ya existe → descender.\n");
                curr = child;
            } else {
                // colisión con letra previa
                pasos.append("  Colisión con '").append(child.data).append("' → descender y continuar.\n");
                curr = child;
            }
            idxBit = end;
        }

        // Si se acaban los bits, almacenamos en curr
        pasos.append("Bits agotados → asignar '").append(letra).append("' en nodo actual.\n");
        curr.data = letra;
        return pasos.toString();
    }

    /**
     * Elimina la letra y nivela el árbol (elimina nodos vacíos).
     */
    public boolean eliminar(char letra) {
        boolean[] eliminado = new boolean[1];
        eliminarRec(root, letra, eliminado);
        return eliminado[0];
    }

    // Elimina el nodo con la letra y nivela el árbol
    private Node eliminarRec(Node n, char letra, boolean[] eliminado) {
        if (n == null) return null;
        if (n.data != null && n.data == letra) {
            eliminado[0] = true;
            // Si no tiene hijos, eliminar el nodo
            if (n.children == null || n.children.isEmpty() || allNull(n.children)) return null;
            // Si solo tiene hijos vacíos, eliminar el nodo
            if (allNull(n.children)) return null;
            // Si tiene hijos, borrar el dato pero mantener hijos
            n.data = null;
        }
        if (n.children != null) {
            for (int i = 0; i < n.children.size(); i++) {
                n.children.set(i, eliminarRec(n.children.get(i), letra, eliminado));
            }
            // Si después de eliminar todos los hijos son null y no tiene data, eliminar este nodo
            if (n.data == null && allNull(n.children)) return null;
        }
        return n;
    }

    // Verifica si todos los hijos son null
    private boolean allNull(List<Node> list) {
        for (Node n : list) if (n != null) return false;
        return true;
    }
}
