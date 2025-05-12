package modelo;

public class ArbolesBResiduosParticularModel {

    // Nodo del árbol
    public class Node {
        public Character data;  // null en el nodo raíz
        public Node left, right;
        public Node(Character data) {
            this.data = data;
            this.left = this.right = null;
        }
    }

    private final Node root;

    public ArbolesBResiduosParticularModel() {
        // la raíz siempre existe, pero comienza vacía (data == null)
        this.root = new Node(null);
    }

    /** Devuelve la raíz para el dibujo */
    public Node getRoot() {
        return root;
    }

    /**
     * Obtiene los últimos 5 bits del código ASCII de la letra.
     */
    private String getLast5Bits(char c) {
        String bin = Integer.toBinaryString((int)c);
        if (bin.length() > 5) {
            return bin.substring(bin.length() - 5);
        } else {
            return String.format("%5s", bin).replace(' ', '0');
        }
    }

    /**
     * Inserta la letra siguiendo el algoritmo “Residuos Particular”:
     *  - Root.data == null siempre.
     *  - Recorrer los 5 bits; en cada bit:
     *      0 → izquierda, 1 → derecha.
     *      Si el hijo está libre, insertar y parar.
     *      Si no, bajar y seguir con el siguiente bit.
     * Devuelve un String con el paso a paso (ASCII, binario y colisiones).
     */

    public String insertarConPasos(char letra) {
        StringBuilder pasos = new StringBuilder();
        String bits = getLast5Bits(letra);

        pasos.append("Letra: '").append(letra).append("'\n")
                .append("ASCII: ").append((int) letra).append("\n")
                .append("Últimos 5 bits: ").append(bits).append("\n\n")
                .append("Insertando en el árbol:\n");

        insertarRecursivo(root, bits, 0, letra, pasos);
        return pasos.toString();
    }

    private void insertarRecursivo(Node actual, String bits, int idx, char letra, StringBuilder pasos) {
        if (idx >= bits.length()) {
            if (actual.data == null) {
                actual.data = letra;
                pasos.append(" Nodo final vacío, asigno '").append(letra).append("'.\n");
            } else {
                pasos.append(" Nodo final ocupado con '").append(actual.data).append("', sobrescribo con '")
                        .append(letra).append("'.\n");
                actual.data = letra;
            }
            return;
        }

        char b = bits.charAt(idx);
        Node next;

        pasos.append(" Bit ").append(idx + 1).append(" = ").append(b).append(" → ");

        if (b == '0') {
            next = actual.left;
            if (next == null) {
                actual.left = new Node(letra);
                pasos.append("inserto '").append(letra).append("' a la IZQUIERDA.\n");
                return;
            } else if (next.data != null) {
                pasos.append("colisión con '").append(next.data).append("', creo rama y reubico.\n");
                char existente = next.data;
                next.data = null;
                insertarRecursivo(next, getLast5Bits(existente), idx + 1, existente, pasos);
                insertarRecursivo(next, bits, idx + 1, letra, pasos);
                return;
            } else {
                pasos.append("nodo intermedio, bajo a la IZQUIERDA.\n");
                insertarRecursivo(next, bits, idx + 1, letra, pasos);
            }
        } else {
            next = actual.right;
            if (next == null) {
                actual.right = new Node(letra);
                pasos.append("inserto '").append(letra).append("' a la DERECHA.\n");
                return;
            } else if (next.data != null) {
                pasos.append("colisión con '").append(next.data).append("', creo rama y reubico.\n");
                char existente = next.data;
                next.data = null;
                insertarRecursivo(next, getLast5Bits(existente), idx + 1, existente, pasos);
                insertarRecursivo(next, bits, idx + 1, letra, pasos);
                return;
            } else {
                pasos.append("nodo intermedio, bajo a la DERECHA.\n");
                insertarRecursivo(next, bits, idx + 1, letra, pasos);
            }
        }
    }

    // Para pruebas manuales
    public static void main(String[] args) {
        ArbolesBResiduosParticularModel arbol = new ArbolesBResiduosParticularModel();
        System.out.println(arbol.insertarConPasos('A'));
        System.out.println(arbol.insertarConPasos('B'));
        System.out.println(arbol.insertarConPasos('C'));
    }

}



/** public String insertarConPasos(char letra) {
     StringBuilder pasos = new StringBuilder();
     String bits = getLast5Bits(letra);
     pasos.append("Letra: '").append(letra).append("'\n")
             .append("ASCII: ").append((int)letra).append("\n")
             .append("Últimos 5 bits: ").append(bits).append("\n\n")
             .append("Insertando en el árbol:\n");

     Node current = root;
     for (int i = 0; i < bits.length(); i++) {
         char b = bits.charAt(i);
         pasos.append(" Bit ").append(i+1).append(" = ").append(b).append(" → ");
         if (b == '0') {
             if (current.left == null) {
                 current.left = new Node(letra);
                 pasos.append("inserto '").append(letra).append("' a la IZQUIERDA.\n");
                 return pasos.toString();
             } else {
                 pasos.append("colisión (ya hay '")
                         .append(current.left.data).append("'), bajo a la IZQUIERDA.\n");
                 current = current.left;
             }
         } else {
             if (current.right == null) {
                 current.right = new Node(letra);
                 pasos.append("inserto '").append(letra).append("' a la DERECHA.\n");
                 return pasos.toString();
             } else {
                 pasos.append("colisión (ya hay '")
                         .append(current.right.data).append("'), bajo a la DERECHA.\n");
                 current = current.right;
             }
         }
     }

     pasos.append("Se completó el recorrido de bits sin hueco, asigno '")
             .append(letra).append("' al nodo final.\n");
     current.data = letra;
     return pasos.toString();
 }
}
*/