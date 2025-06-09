package controlador;

import modelo.ArbolesBDigitalesModel;
import modelo.ArbolesBDigitalesModel.Node;
import vista.ArbolesBDigitalesView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ArbolesBDigitalesController {
    private final ArbolesBDigitalesModel model;
    private final ArbolesBDigitalesView view;

    public ArbolesBDigitalesController(ArbolesBDigitalesModel m,
                                       ArbolesBDigitalesView v) {
        this.model = m;
        this.view = v;
        init();
        view.setVisible(true);
    }

    private void init() {
        view.getBtnCrear().addActionListener(e -> crearArbol());
        view.getBtnBuscarLetra().addActionListener(e -> buscarLetra());
        view.getBtnEliminarLetra().addActionListener(e -> eliminarLetra());
        view.getBtnGuardar().addActionListener(e -> guardarCSV());
        view.getBtnImportar().addActionListener(e -> importarCSV());
    }

    private void crearArbol() {
        String palabra = view.getTxtPalabra();
        if (palabra.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Ingrese palabra.");
            return;
        }
        view.getConsola().setText("");
        model.setCustomBits(palabra.length());
        view.getTreePanel().setWordLength(palabra.length());
        view.getConsola().append("Palabra: ["
            + String.join(", ", palabra.split("")) + "]\n");
        for (char c : palabra.toCharArray()) {
            String paso = model.insertarConPasos(c);
            view.getConsola().append(paso + "\n");
        }
        view.getTreePanel().repaint();
    }

    private void buscarLetra() {
        String s = JOptionPane.showInputDialog(view, "Letra a buscar:");
        if (s == null || s.isEmpty()) return;
        char c = s.charAt(0);
        Node n = model.buscar(c);
        if (n != null) {
            view.getConsola().append("Encontrado '" + c + "'\n");
            view.getTreePanel().setHighlightedNode(n);
        } else {
            view.getConsola().append("No encontrado '" + c + "'\n");
        }
    }

    private void eliminarLetra() {
        String s = JOptionPane.showInputDialog(view, "Letra a eliminar:");
        if (s == null || s.isEmpty()) return;
        char c = s.charAt(0);
        boolean ok = model.eliminar(c);
        view.getConsola().append(
            ok ? "Eliminado y nivelado '" + c + "'\n"
               : "No existe '" + c + "'\n");
        view.getTreePanel().repaint();
    }

    private void guardarCSV() {
        try {
            String palabra = view.getTxtPalabra();
            if (palabra.isEmpty()) {
                JOptionPane.showMessageDialog(view, "No hay datos para guardar.");
                return;
            }
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(new java.io.File("arbol_" + palabra + ".csv"));
            if (chooser.showSaveDialog(view) != JFileChooser.APPROVE_OPTION) return;
            java.io.File file = chooser.getSelectedFile();
            try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(file))) {
                writer.write("#CONSOLA\n");
                writer.write(view.getConsola().getText());
                writer.write("#ARBOL\n");
                guardarNodo(writer, model.getRoot(), 0);
            }
            JOptionPane.showMessageDialog(view, "Datos guardados en: " + file.getName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error al guardar: " + e.getMessage());
        }
    }

    private void guardarNodo(java.io.BufferedWriter writer, Node node, int depth) throws java.io.IOException {
        if (node == null) {
            writer.write("  ".repeat(depth) + "Vacío\n");
            return;
        }
        writer.write("  ".repeat(depth) + (node.data == null ? "Vacío" : node.data) + "\n");
        guardarNodo(writer, node.left, depth + 1);
        guardarNodo(writer, node.right, depth + 1);
    }

    private void importarCSV() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (chooser.showOpenDialog(view) != JFileChooser.APPROVE_OPTION) return;
        java.io.File file = chooser.getSelectedFile();
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file))) {
            StringBuilder consola = new StringBuilder();
            String line;
            boolean enConsola = false, enArbol = false;
            List<String> arbolLines = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (line.equals("#CONSOLA")) {
                    enConsola = true; enArbol = false; continue;
                }
                if (line.equals("#ARBOL")) {
                    enConsola = false; enArbol = true; continue;
                }
                if (enConsola) consola.append(line).append("\n");
                if (enArbol) arbolLines.add(line);
            }
            view.getConsola().setText(consola.toString());
            model.setRoot(recuperarArbol(arbolLines));
            view.getTreePanel().repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error al importar: " + e.getMessage());
        }
    }

    // Reconstruye el árbol desde la lista de líneas
    private Node recuperarArbol(List<String> lines) {
        return recuperarArbolRec(lines, new int[]{0}, 0);
    }
    private Node recuperarArbolRec(List<String> lines, int[] idx, int depth) {
        if (idx[0] >= lines.size()) return null;
        String line = lines.get(idx[0]);
        int actualDepth = 0;
        while (line.startsWith("  ")) { actualDepth++; line = line.substring(2); }
        if (actualDepth != depth) return null;
        idx[0]++;
        if (line.equals("Vacío")) return null;
        Node n = model.new Node(line.charAt(0));
        n.left = recuperarArbolRec(lines, idx, depth + 1);
        n.right = recuperarArbolRec(lines, idx, depth + 1);
        return n;
    }
}
