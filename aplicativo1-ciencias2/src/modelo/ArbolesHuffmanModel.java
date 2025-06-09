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
        // 1) calcular frecuencias y orden de aparición
        Map<Character, Integer> freqMap = new LinkedHashMap<>();
        List<Character> orden = new ArrayList<>();
        for (char c : palabra.toCharArray()) {
            if (!freqMap.containsKey(c)) orden.add(c);
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }
        // 2) convertir a lista de nodos hoja
        List<Node> nodes = new ArrayList<>();
        for (char c : orden) {
            nodes.add(new Node(c, freqMap.get(c)));
        }
        // 3) ordenar inicial según orden de aparición
        // (ya está en orden por 'orden')
        // 4) calculamos tabla de probabilidades Pi = freq / n
        probabilities.clear();
        for (char c : orden) {
            probabilities.put(c, freqMap.get(c) / (double)n);
        }

        // Para el paso a paso:
        List<String> pasos = new ArrayList<>();
        pasos.add("Tabla inicial de frecuencias y probabilidades:");
        pasos.add(formatTable(nodes, n, orden));

        // 5) bucle de fusiones
        while (nodes.size() > 1) {
            Node a = nodes.remove(0);
            Node b = nodes.remove(0);
            Node parent = new Node(null, a.freq + b.freq);
            parent.left = a;
            parent.right = b;
            // Para mostrar agrupación de caracteres
            parent.code = getSymbols(a) + getSymbols(b);
            // insertar en orden por frecuencia ascendente
            nodes.add(parent);
            nodes.sort(Comparator.comparingDouble(n1 -> n1.freq));
            pasos.add("Se fusionan " +
                    getSymbols(a) + "(" + a.freq + ") + " +
                    getSymbols(b) + "(" + b.freq + ") = " + parent.freq);
            pasos.add(formatTable(nodes, n, orden));
        }

        root = nodes.get(0);
        // 6) generar códigos recorriendo el árbol
        buildCodes(root, "");

        return pasos;
    }

    // Devuelve los símbolos agrupados de un nodo
    private String getSymbols(Node n) {
        if (n == null) return "";
        if (n.symbol != null) return n.symbol.toString();
        return getSymbols(n.left) + getSymbols(n.right);
    }

    /** Formatea la tabla (Ki, fi, Pi) para mostrar en paso a paso, respetando el orden de aparición */
    private String formatTable(List<Node> list, int total, List<Character> orden) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-5s %-5s\n", "Ki","fi","Pi"));
        // Ordenar: primero hojas según 'orden', luego agrupaciones por frecuencia ascendente
        List<Node> hojas = new ArrayList<>();
        List<Node> grupos = new ArrayList<>();
        for (Node n : list) {
            if (n.symbol != null) hojas.add(n);
            else grupos.add(n);
        }
        hojas.sort(Comparator.comparingInt(n -> orden.indexOf(n.symbol)));
        grupos.sort(Comparator.comparingDouble(n -> n.freq));
        for (Node n : hojas) {
            String ki = n.symbol.toString();
            double pi = n.freq/(double)total;
            sb.append(String.format("%-10s %-5d %-5.3f\n", ki, (int)n.freq, pi));
        }
        for (Node n : grupos) {
            String ki = getSymbols(n);
            double pi = n.freq/(double)total;
            sb.append(String.format("%-10s %-5d %-5.3f\n", ki, (int)n.freq, pi));
        }
        return sb.toString();
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