package controlador;

import modelo.ArbolesBResiduosParticularModel;
import vista.ArbolesBResiduosParticularView;
import vista.TreePanelBResiduosParticular; // Import the TreePanelBResiduosParticular class
import javax.swing.*;
import java.io.*;
import java.nio.file.*;

public class ArbolesBResiduosParticularController {
    private final ArbolesBResiduosParticularModel model;
    private final ArbolesBResiduosParticularView view;

    public ArbolesBResiduosParticularController(
            ArbolesBResiduosParticularModel model,
            ArbolesBResiduosParticularView view) {
        this.model = model;
        this.view = view;
        init();
    }

    private void init() {
        view.getBtnCrearArbol().addActionListener(e -> crear());
        view.getBtnBuscarLetra().addActionListener(e -> buscarLetra());
        view.getBtnEliminarLetra().addActionListener(e -> eliminarLetra());
        view.getBtnGuardar().addActionListener(e -> guardarCSV());
        view.getBtnImportar().addActionListener(e -> importarCSV());
        view.setVisible(true);
    }

    private void crear() {
        String palabra = view.getPalabra();
        if (palabra.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Debe escribir una palabra.");
            return;
        }

        view.getConsola().setText("Palabra: [" + String.join(", ", palabra.split("")) + "]\n");
        view.setWordLength(palabra.length());

        for (char c : palabra.toCharArray()) {
            String pasos = model.insertarConPasos(c);
            view.getConsola().append(pasos + "\n");
        }

        view.repaintTree();
    }

    private void buscarLetra() {
        String input = JOptionPane.showInputDialog(view, "Ingrese la letra a buscar:");
        if (input == null || input.isEmpty()) {
            JOptionPane.showMessageDialog(view, "No se ingresó ninguna letra.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        char letra = input.charAt(0);
        var nodo = buscarNodo(model.getRoot(), letra);
        if (nodo != null) {
            view.getConsola().append("Encontrado: '" + letra + "'\n");
            view.getTreePanel().setHighlightedNode(nodo); // Highlight the node
        } else {
            view.getConsola().append("No encontrado: '" + letra + "'\n");
        }
    }

    private ArbolesBResiduosParticularModel.Node buscarNodo(ArbolesBResiduosParticularModel.Node node, char letra) {
        if (node == null) return null;
        if (node.data != null && node.data == letra) return node;
        var left = buscarNodo(node.left, letra);
        return left != null ? left : buscarNodo(node.right, letra);
    }

    private void eliminarLetra() {
        String input = JOptionPane.showInputDialog(view, "Ingrese la letra a eliminar:");
        if (input == null || input.isEmpty()) return;
        char letra = input.charAt(0);
        boolean ok = model.eliminar(letra);
        view.getConsola().append(
            ok ? "Eliminado y nivelado: '" + letra + "'\n"
               : "No encontrado: '" + letra + "'\n");
        view.repaintTree();
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

    private void guardarNodo(BufferedWriter writer, ArbolesBResiduosParticularModel.Node node, int depth) throws IOException {
        if (node == null) return;
        writer.write("  ".repeat(depth) + (node.data == null ? "Vacío" : node.data) + "\n");
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
    private ArbolesBResiduosParticularModel.Node recuperarArbol(java.util.List<String> lines) {
        return recuperarArbolRec(lines, new int[]{0}, 0);
    }
    private ArbolesBResiduosParticularModel.Node recuperarArbolRec(java.util.List<String> lines, int[] idx, int depth) {
        if (idx[0] >= lines.size()) return null;
        String line = lines.get(idx[0]);
        int actualDepth = 0;
        while (line.startsWith("  ")) { actualDepth++; line = line.substring(2); }
        if (actualDepth != depth) return null;
        idx[0]++;
        if (line.equals("Vacío")) return null;
        ArbolesBResiduosParticularModel.Node n = model.new Node(line.equals("null") ? null : line.charAt(0));
        n.left = recuperarArbolRec(lines, idx, depth + 1);
        n.right = recuperarArbolRec(lines, idx, depth + 1);
        return n;
    }
}
