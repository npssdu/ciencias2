package controlador;

import modelo.ArbolesBResiduosMultiplesModel;
import vista.ArbolesBResiduosMultiplesView;
import javax.swing.*;
import java.nio.file.*;
import java.io.*;

public class ArbolesBResiduosMultiplesController {
    private final ArbolesBResiduosMultiplesModel model;
    private final ArbolesBResiduosMultiplesView view;
    private boolean enlacesCalculados = false;

    public ArbolesBResiduosMultiplesController(
            ArbolesBResiduosMultiplesModel model,
            ArbolesBResiduosMultiplesView view) {
        this.model = model;
        this.view = view;
        init();
    }

    private void init() {
        view.getBtnCalcular().addActionListener(e -> onCalcular());
        view.getBtnCrear().addActionListener(e -> onCrear());
        view.getBtnBuscarLetra().addActionListener(e -> buscarLetra());
        view.getBtnEliminarLetra().addActionListener(e -> eliminarLetra());
        view.getBtnGuardar().addActionListener(e -> guardarCSV());
        view.getBtnImportar().addActionListener(e -> importarCSV());
        view.setVisible(true);
    }

    private void onCalcular() {
        try {
            int M = Integer.parseInt(view.getM());
            if (M < 1 || M > 5) {
                JOptionPane.showMessageDialog(view, "Ingrese M entre 1 y 5.");
                return;
            }
            model.setM(M);
            enlacesCalculados = true;
            view.getBtnCrear().setEnabled(true);
            JOptionPane.showMessageDialog(view, "Enlaces: 2^" + M + " = " + model.getNumChildren());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "M debe ser un número válido.");
        }
    }

    private void onCrear() {
        if (!enlacesCalculados) {
            JOptionPane.showMessageDialog(view, "Primero calcule los enlaces (M).");
            return;
        }
        String palabra = view.getPalabra();
        if (palabra.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Ingrese una palabra.");
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
        if (input == null || input.isEmpty()) return;
        char letra = input.charAt(0);
        var nodo = buscarNodo(model.getRoot(), letra);
        if (nodo != null) {
            view.getConsola().append("Encontrado: '" + letra + "'\n");
            view.getTreePanel().setHighlightedNode(nodo); // Use getTreePanel to highlight the node
        } else {
            view.getConsola().append("No encontrado: '" + letra + "'\n");
        }
    }

    private ArbolesBResiduosMultiplesModel.Node buscarNodo(ArbolesBResiduosMultiplesModel.Node node, char letra) {
        if (node == null) return null;
        if (node.data != null && node.data == letra) return node;
        for (var child : node.children) {
            var found = buscarNodo(child, letra);
            if (found != null) return found;
        }
        return null;
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
            Path dir = Paths.get("archivos/ArbolesBResiduosMultiples");
            Files.createDirectories(dir);
            String palabra = view.getPalabra();
            if (palabra.isEmpty()) {
                JOptionPane.showMessageDialog(view, "No hay datos para guardar.");
                return;
            }
            Path file = dir.resolve("arbol_" + palabra + ".csv");
            try (BufferedWriter writer = Files.newBufferedWriter(file)) {
                writer.write("Palabra," + palabra + "\n");
                writer.write("Estructura del árbol:\n");
                guardarNodo(writer, model.getRoot(), 0);
            }
            JOptionPane.showMessageDialog(view, "Datos guardados en: " + file);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "Error al guardar: " + e.getMessage());
        }
    }

    private void guardarNodo(BufferedWriter writer, ArbolesBResiduosMultiplesModel.Node node, int depth) throws IOException {
        if (node == null) return;
        writer.write("  ".repeat(depth) + (node.data == null ? "Vacío" : node.data) + "\n");
        for (var child : node.children) {
            guardarNodo(writer, child, depth + 1);
        }
    }

    private void importarCSV() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (chooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                view.getConsola().append("Importando desde: " + file.getName() + "\n");
                String line;
                while ((line = reader.readLine()) != null) {
                    view.getConsola().append(line + "\n");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view, "Error al importar: " + e.getMessage());
            }
        }
    }
}
