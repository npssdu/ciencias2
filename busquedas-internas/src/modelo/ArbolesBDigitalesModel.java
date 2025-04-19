package modelo;

public class ArbolesBDigitalesModel {

    // Clase interna para representar un nodo del árbol.
    public class Node {
        public Character data;
        public Node left;
        public Node right;

        public Node(Character data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private int customBits = 5; // Valor por defecto (para palabra de 6 letras: 6-1=5 bits)

    public ArbolesBDigitalesModel() {
        root = null;
    }

    //Metodo para establecer la cantidad de bits custom, calculado como (wordLength - 1)
    //public void setCustomBits(int bits) {
    //    this.customBits = bits > 0 ? bits : 1;
    //}

    // Retorna la raíz del árbol.
    public Node getRoot() {
        return root;
    }

    /**
     * Convierte la letra a una representación binaria de 'customBits' bits.
     * Se obtiene el código ASCII en binario y se formatea a customBits:
     * si es mayor que customBits, se toman los bits menos significativos;
     * si es menor, se añade ceros a la izquierda.
     */
    public String getCustomBinary(char c) {
        int ascii = (int)c;
        String bin = Integer.toBinaryString(ascii);
        if (bin.length() > customBits) {
            return bin.substring(bin.length() - customBits);
        } else {
            return String.format("%" + customBits + "s", bin).replace(' ', '0');
        }
    }

    /**
     * Inserta la letra siguiendo este algoritmo:
     * - La primera letra se asigna a la raíz.
     * - Para cada letra se obtiene su representación binaria (custom de customBits).
     * - Se recorre la ruta: para cada bit, '0' intenta insertar a la izquierda, '1' a la derecha.
     *   Si la posición ya está ocupada, se baja y se usa el siguiente bit.
     * Devuelve un String con el paso a paso del proceso.
     */
    public String insertarConPasos(char letra) {
        StringBuilder pasos = new StringBuilder();
        String binary = getCustomBinary(letra);
        pasos.append("Letra: ").append(letra).append("\n")
                .append("Código ASCII (decimal): ").append((int)letra).append("\n")
                .append("Código binario (").append(customBits).append(" bits): ").append(binary).append("\n");

        if (root == null) {
            root = new Node(letra);
            pasos.append("El árbol está vacío. Se inserta '").append(letra)
                    .append("' como nodo raíz.\n");
            return pasos.toString();
        }

        Node current = root;
        pasos.append("Proceso de inserción:\n");
        for (int i = 0; i < binary.length(); i++) {
            char bit = binary.charAt(i);
            pasos.append("Bit ").append(i+1).append(" (").append(bit).append("): ");
            if (bit == '0') {
                if (current.left == null) {
                    current.left = new Node(letra);
                    pasos.append("No hay nodo a la izquierda, se inserta '").append(letra).append("'.\n");
                    return pasos.toString();
                } else {
                    pasos.append("Ya existe nodo a la izquierda (contiene '")
                            .append(current.left.data).append("'). Se baja a ese nodo.\n");
                    current = current.left;
                }
            } else { // bit == '1'
                if (current.right == null) {
                    current.right = new Node(letra);
                    pasos.append("No hay nodo a la derecha, se inserta '").append(letra).append("'.\n");
                    return pasos.toString();
                } else {
                    pasos.append("Ya existe nodo a la derecha (contiene '")
                            .append(current.right.data).append("'). Se baja a ese nodo.\n");
                    current = current.right;
                }
            }
        }
        pasos.append("Se recorrieron todos los bits. Se inserta '").append(letra)
                .append("' en la última posición.\n");
        current.data = letra;
        return pasos.toString();
    }
}
