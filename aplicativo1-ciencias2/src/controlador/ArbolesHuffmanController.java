package controlador;

import modelo.ArbolesHuffmanModel;
import vista.ArbolesHuffmanView;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Map;
import java.nio.file.*;
import java.io.*;

public class ArbolesHuffmanController {
    private ArbolesHuffmanModel model;
    private ArbolesHuffmanView view;

    public ArbolesHuffmanController(ArbolesHuffmanModel m, ArbolesHuffmanView v) {
        this.model = m;
        this.view = v;
        init();
    }

    private void init() {
        view.getBtnCrear().addActionListener(e -> crear());
        view.getBtnBuscarLetra().addActionListener(e -> buscarLetra());
        view.getBtnEliminarLetra().addActionListener(e -> eliminarLetra());
        view.getBtnGuardar().addActionListener(e -> guardarCSV());
        view.getBtnImportar().addActionListener(e -> importarCSV());
        view.setVisible(true);
    }

    private void crear() {
        String palabra = view.getPalabra();
        if (palabra.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Ingrese una palabra.");
            return;
        }

        view.getConsola().setText("Palabra: [" + String.join(", ", palabra.split("")) + "]\n");
        var pasos = model.buildTree(palabra);
        for (String paso : pasos) {
            view.getConsola().append(paso + "\n");
        }
        view.repaintTree();
    }

    private void buscarLetra() {
        String input = JOptionPane.showInputDialog(view, "Ingrese la letra a buscar:");
        if (input == null || input.isEmpty()) return;
        char letra = input.charAt(0);
        var nodo = buscarNodo(model.getRoot(), letra);
        if (nodo != null) {
            view.getConsola().append("Encontrado: '" + letra + "'\n");
            view.getTreePanel().setHighlightedNode(nodo); // Highlight the node in purple
        } else {
            view.getConsola().append("No encontrado: '" + letra + "'\n");
        }
    }

    private ArbolesHuffmanModel.Node buscarNodo(ArbolesHuffmanModel.Node node, char letra) {
        if (node == null) return null;
        if (node.symbol != null && node.symbol == letra) return node;
        var left = buscarNodo(node.left, letra);
        return left != null ? left : buscarNodo(node.right, letra);
    }

    private void eliminarLetra() {
        String input = JOptionPane.showInputDialog(view, "Ingrese la letra a eliminar:");
        if (input == null || input.isEmpty()) return;
        char letra = input.charAt(0);
        if (eliminarNodo(model.getRoot(), letra)) {
            view.getConsola().append("Eliminado: '" + letra + "'\n");
        } else {
            view.getConsola().append("No encontrado: '" + letra + "'\n");
        }
        view.repaintTree();
    }

    private boolean eliminarNodo(ArbolesHuffmanModel.Node node, char letra) {
        if (node == null) return false;
        if (node.symbol != null && node.symbol == letra) {
            node.symbol = null;
            return true;
        }
        return eliminarNodo(node.left, letra) || eliminarNodo(node.right, letra);
    }

    private void guardarCSV() {
        try {
            String palabra = view.getPalabra();
            if (palabra.isEmpty()) {
                JOptionPane.showMessageDialog(view, "No hay datos para guardar.");
                return;
            }
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(new java.io.File("arbol_" + palabra + ".csv"));
            if (chooser.showSaveDialog(view) != JFileChooser.APPROVE_OPTION) return;
            java.io.File file = chooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("#CONSOLA\n");
                writer.write(view.getConsola().getText());
                writer.write("#ARBOL\n");
                guardarNodo(writer, model.getRoot(), 0);
            }
            JOptionPane.showMessageDialog(view, "Datos guardados en: " + file.getAbsolutePath());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error al guardar: " + e.getMessage());
        }
    }

    private void guardarNodo(BufferedWriter writer, ArbolesHuffmanModel.Node node, int depth) throws IOException {
        if (node == null) return;
        writer.write("  ".repeat(depth) + (node.symbol == null ? "Vacío" : node.symbol) + "\n");
        guardarNodo(writer, node.left, depth + 1);
        guardarNodo(writer, node.right, depth + 1);
    }

    private void importarCSV() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (chooser.showOpenDialog(view) != JFileChooser.APPROVE_OPTION) return;
        java.io.File file = chooser.getSelectedFile();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder consola = new StringBuilder();
            String line;
            boolean enConsola = false, enArbol = false;
            java.util.List<String> arbolLines = new java.util.ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (line.equals("#CONSOLA")) { enConsola = true; enArbol = false; continue; }
                if (line.equals("#ARBOL")) { enConsola = false; enArbol = true; continue; }
                if (enConsola) consola.append(line).append("\n");
                if (enArbol) arbolLines.add(line);
            }
            view.getConsola().setText(consola.toString());
            model.setRoot(recuperarArbol(arbolLines));
            view.repaintTree();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error al importar: " + e.getMessage());
        }
    }

    // Reconstruye el árbol desde la lista de líneas
    private ArbolesHuffmanModel.Node recuperarArbol(java.util.List<String> lines) {
        return recuperarArbolRec(lines, new int[]{0}, 0);
    }
    private ArbolesHuffmanModel.Node recuperarArbolRec(java.util.List<String> lines, int[] idx, int depth) {
        if (idx[0] >= lines.size()) return null;
        String line = lines.get(idx[0]);
        int actualDepth = 0;
        while (line.startsWith("  ")) { actualDepth++; line = line.substring(2); }
        if (actualDepth != depth) return null;
        idx[0]++;
        if (line.equals("Vacío")) return null;
        // Para nodos internos, symbol es null
        Character symbol = line.equals("null") ? null : (line.length() == 1 ? line.charAt(0) : null);
        ArbolesHuffmanModel.Node n = new ArbolesHuffmanModel.Node(symbol, 0);
        n.left = recuperarArbolRec(lines, idx, depth + 1);
        n.right = recuperarArbolRec(lines, idx, depth + 1);
        return n;
    }
}
