package controlador;

import modelo.ArbolesBResiduosMultiplesModel;
import vista.ArbolesBResiduosMultiplesView;
import javax.swing.*;

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

        view.setWordLength(palabra.length());
        for (char c : palabra.toCharArray()) {
            String pasos = model.insertarConPasos(c);
            JOptionPane.showMessageDialog(view, pasos,
                "Insertando '" + c + "'", JOptionPane.INFORMATION_MESSAGE);
        }
        view.repaintTree();
    }
}
