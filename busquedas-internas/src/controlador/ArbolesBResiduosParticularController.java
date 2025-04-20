package controlador;

import modelo.ArbolesBResiduosParticularModel;
import vista.ArbolesBResiduosParticularView;
import javax.swing.*;

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
        view.setVisible(true);
    }

    private void crear() {
        String palabra = view.getPalabra();
        if (palabra.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Debe escribir una palabra.");
            return;
        }

        // Fijamos siempre 5 bits
        // (no hace falta setCustomBits, pues el modelo usa internamente 5)
        view.setWordLength(palabra.length());

        for (char c : palabra.toCharArray()) {
            String pasos = model.insertarConPasos(c);
            JOptionPane.showMessageDialog(
                    view,
                    pasos,
                    "Insertando '" + c + "'",
                    JOptionPane.INFORMATION_MESSAGE
            );
            try { Thread.sleep(300); } catch (InterruptedException ignored) {}
        }

        view.repaintTree();
    }
}
