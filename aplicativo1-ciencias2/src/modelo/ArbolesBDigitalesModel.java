package modelo;

import java.util.*;

/**
 * Modelo de Árboles B Digitales con:
 *  - insertarConPasos(char)
 *  - buscar(char)
 *  - eliminar(char)
 *  - preorden()
 */
public class ArbolesBDigitalesModel {

    public class Node {
        public Character data;
        public Node left, right;
        public Node(Character data) {
            this.data = data;
            this.left = this.right = null;
        }
    }

    private Node root;
    private int customBits = 1;

    public ArbolesBDigitalesModel() {
        this.root = null;
    }

    public void setCustomBits(int wordLength) {
        this.customBits = Math.max(1, wordLength - 1);
    }

    public String insertarConPasos(char letra) {
        StringBuilder log = new StringBuilder();
        String bin = Integer.toBinaryString((int)letra);
        if (bin.length() > customBits) {
            bin = bin.substring(bin.length() - customBits);
        } else {
            bin = String.format("%" + customBits + "s", bin).replace(' ', '0');
        }
        log.append("Letra: ").append(letra)
           .append(" (ASCII ").append((int)letra).append(") → binario: ").append(bin).append("\n");

        if (root == null) {
            root = new Node(letra);
            log.append("Árbol vacío, raíz = '").append(letra).append("'\n");
            return log.toString();
        }
        Node cur = root;
        for (int i = 0; i < bin.length(); i++) {
            char b = bin.charAt(i);
            log.append("Bit ").append(i+1).append("='").append(b).append("': ");
            if (b == '0') {
                if (cur.left == null) {
                    cur.left = new Node(letra);
                    log.append("insertado a la izquierda\n");
                    return log.toString();
                } else {
                    log.append("baja por izquierda (").append(cur.left.data).append(")\n");
                    cur = cur.left;
                }
            } else {
                if (cur.right == null) {
                    cur.right = new Node(letra);
                    log.append("insertado a la derecha\n");
                    return log.toString();
                } else {
                    log.append("baja por derecha (").append(cur.right.data).append(")\n");
                    cur = cur.right;
                }
            }
        }
        log.append("Bits agotados, reemplaza nodo con '").append(letra).append("'\n");
        cur.data = letra;
        return log.toString();
    }

    public Node getRoot() {
        return root;
    }

    public Node buscar(char letra) {
        return buscarRec(root, letra);
    }
    private Node buscarRec(Node n, char letra) {
        if (n == null) return null;
        if (n.data != null && n.data == letra) return n;
        Node l = buscarRec(n.left, letra);
        if (l != null) return l;
        return buscarRec(n.right, letra);
    }

    public boolean eliminar(char letra) {
        Node n = buscar(letra);
        if (n == null) return false;
        n.data = null;
        return true;
    }

    public List<String> preorden() {
        List<String> out = new ArrayList<>();
        preordenRec(root, out);
        return out;
    }
    private void preordenRec(Node n, List<String> out) {
        if (n == null) { out.add(""); return; }
        out.add(n.data == null ? "" : n.data.toString());
        preordenRec(n.left, out);
        preordenRec(n.right, out);
    }
}
