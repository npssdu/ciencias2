package modelo;

public class ArbolesBResiduosParticularModel {

    // Clase interna para el nodo
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

    // La raíz siempre se inicializa como vacía.
    private Node root;
    private int customBits = 5; // Valor por defecto, se ajustará según la longitud de la palabra (n-1 bits)

    public ArbolesBResiduosParticularModel() {
        // Inicializar raíz vacía.
        root = new Node(null);
    }

    public void setCustomBits(int bits) {
        this.customBits = (bits > 0 ? bits : 1);
    }

    public Node getRoot() {
        return root;
    }

    /**
     * Convierte la letra a una representación binaria de 'customBits' bits (tomando
     * los bits menos significativos en caso de que el valor ASCII tenga más bits).
     */
    public String getCustomBinary(char c) {
        int ascii = (int)c;
        String bin = Integer.toBinaryString(ascii);
        if(bin.length() > customBits) {
            return bin.substring(bin.length() - customBits);
        } else {
            return String.format("%" + customBits + "s", bin).replace(' ', '0');
        }
    }

    /**
     * Inserta la letra en el árbol siguiendo el algoritmo para “Árboles B por Residuos Particular”.
     *
     * @param letra         Letra a insertar.
     * @param primeraLetra  Si es la primera letra, se recorre todo el código binario.
     *                      Para las siguientes, se ignoran las partes ya creadas y se crea la rama
     *                      a partir de la aparición de un nuevo "1".
     * @return              Una cadena con el paso a paso de la inserción.
     */
    public String insertarConPasos(char letra, boolean primeraLetra) {
        StringBuilder pasos = new StringBuilder();
        String binary = getCustomBinary(letra);
        pasos.append("Letra: ").append(letra).append("\n")
                .append("Código ASCII (decimal): ").append((int)letra).append("\n")
                .append("Código binario (").append(customBits).append(" bits): ").append(binary).append("\n");

        // Comenzamos siempre desde la raíz (que permanece vacía)
        Node current = root;
        pasos.append("La raíz está vacía (siempre).\n");

        if (primeraLetra) {
            // Para la primera letra se recorre todo el código.
            for (int i = 0; i < binary.length(); i++) {
                char bit = binary.charAt(i);
                pasos.append("Bit ").append(i + 1).append(" (").append(bit).append("): ");
                if (bit == '0') {
                    if (current.left == null) {
                        current.left = new Node(null);
                        pasos.append("No existe rama izquierda: se crea.\n");
                    } else {
                        pasos.append("La rama izquierda existe: se baja.\n");
                    }
                    current = current.left;
                } else { // bit == '1'
                    if (current.right == null) {
                        current.right = new Node(null);
                        pasos.append("No existe rama derecha: se crea.\n");
                    } else {
                        pasos.append("La rama derecha existe: se baja.\n");
                    }
                    current = current.right;
                }
            }
            current.data = letra;
            pasos.append("Se asigna la letra '").append(letra).append("' en el nodo final.\n");
            return pasos.toString();
        }

        // Para letras siguientes:
        // Recorremos los bits; si el bit es '1' y la rama ya existe (activada por letras previas),
        // se baja sin crear nada; de lo contrario, se crea la rama a partir de la primera diferencia.
        boolean rutaPreexistente = true;
        for (int i = 0; i < binary.length(); i++) {
            char bit = binary.charAt(i);
            pasos.append("Bit ").append(i + 1).append(" (").append(bit).append("): ");
            if (rutaPreexistente) {
                // Si el bit es '1' y la rama correspondiente existe, se baja.
                if (bit == '1') {
                    if (current.right != null) {
                        pasos.append("La rama derecha ya existe (").append(current.right.data == null ? "vacío" : current.right.data)
                                .append("), se baja.\n");
                        current = current.right;
                        continue;
                    } else {
                        rutaPreexistente = false;
                        pasos.append("La rama derecha no existe, se crea.\n");
                        current.right = new Node(null);
                        current = current.right;
                    }
                } else { // bit == '0'
                    if (current.left != null) {
                        pasos.append("La rama izquierda ya existe (").append(current.left.data == null ? "vacío" : current.left.data)
                                .append("), se baja.\n");
                        current = current.left;
                        continue;
                    } else {
                        rutaPreexistente = false;
                        pasos.append("La rama izquierda no existe, se crea.\n");
                        current.left = new Node(null);
                        current = current.left;
                    }
                }
            } else {
                // Una vez terminada la parte preexistente, se crean las ramas según el bit.
                if (bit == '0') {
                    pasos.append("Creando rama izquierda.\n");
                    current.left = new Node(null);
                    current = current.left;
                } else {
                    pasos.append("Creando rama derecha.\n");
                    current.right = new Node(null);
                    current = current.right;
                }
            }
        }
        current.data = letra;
        pasos.append("Se asigna la letra '").append(letra).append("' en el nodo final.\n");
        return pasos.toString();
    }
}
