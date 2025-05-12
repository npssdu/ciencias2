package modelo;

import java.util.*;

public class ArbolesHuffmanModel {
    public static class Node {
        public Character symbol;    // null para nodos internos
        public double freq;         // frecuencia absoluta (o probabilidad)
        public Node left, right;
        // para código final:
        public String code = "";

        public Node(Character symbol, double freq) {
            this.symbol = symbol;
            this.freq = freq;
        }
    }

    private Node root;
    private Map<Character, String> codes = new LinkedHashMap<>();
    private Map<Character, Double> probabilities = new LinkedHashMap<>();

    /** Construye el árbol de Huffman a partir de la palabra dada */
    public List<String> buildTree(String palabra) {
        int n = palabra.length();
        // 1) calcular frecuencias
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : palabra.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }
        // 2) convertir a lista de nodos hoja
        List<Node> nodes = new ArrayList<>();
        for (var e : freqMap.entrySet()) {
            nodes.add(new Node(e.getKey(), e.getValue()));
        }
        // 3) ordenar inicial
        nodes.sort(Comparator.comparingDouble(n1 -> n1.freq));
        // 4) calculamos tabla de probabilidades Pi = freq / n
        probabilities.clear();
        for (var e : freqMap.entrySet()) {
            probabilities.put(e.getKey(), e.getValue() / (double)n);
        }

        // Para el paso a paso:
        List<String> pasos = new ArrayList<>();
        pasos.add("Tabla inicial de frecuencias y probabilidades:");
        pasos.add(formatTable(nodes, n));

        // 5) bucle de fusiones
        while (nodes.size() > 1) {
            Node a = nodes.remove(0);
            Node b = nodes.remove(0);
            Node parent = new Node(null, a.freq + b.freq);
            parent.left = a;
            parent.right = b;
            // insertar en orden
            nodes.add(parent);
            nodes.sort(Comparator.comparingDouble(n1 -> n1.freq));
            pasos.add("Se fusionan " +
                    (a.symbol==null? "⊕": a.symbol) + "(" + a.freq + ") + " +
                    (b.symbol==null? "⊕": b.symbol) + "(" + b.freq + ") = " + parent.freq);
            pasos.add(formatTable(nodes, n));
        }

        root = nodes.get(0);
        // 6) generar códigos recorriendo el árbol
        buildCodes(root, "");

        return pasos;
    }

    private void buildCodes(Node node, String prefix) {
        if (node == null) return;
        if (node.symbol != null) {
            codes.put(node.symbol, prefix);
            node.code = prefix;
        }
        buildCodes(node.left, prefix + "0");
        buildCodes(node.right, prefix + "1");
    }

    /** Formatea la tabla (Ki, fi, Pi) para mostrar en paso a paso */
    private String formatTable(List<Node> list, int total) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-5s %-5s %-5s\n", "Ki","fi","Pi"));
        for (Node n : list) {
            String ki = n.symbol==null? "⊕": n.symbol.toString();
            double pi = n.freq/total;
            sb.append(String.format("%-5s %-5d %-5.3f\n", ki, (int)n.freq, pi));
        }
        return sb.toString();
    }

    public Node getRoot() {
        return root;
    }

    public Map<Character, String> getCodes() {
        return codes;
    }

    public Map<Character, Double> getProbabilities() {
        return probabilities;
    }
}