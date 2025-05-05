package controlador;

import modelo.ArbolesBDigitalesModel;
import modelo.ArbolesBDigitalesModel.Node;
import vista.ArbolesBDigitalesView;

import javax.swing.*;

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
        // guardar/importar quedan igual...
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
            ok ? "Eliminado '" + c + "'\n"
               : "No existe '" + c + "'\n");
        view.getTreePanel().repaint();
    }
}
