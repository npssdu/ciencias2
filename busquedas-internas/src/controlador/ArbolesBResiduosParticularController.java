package controlador;

import modelo.ArbolesBResiduosParticularModel;
import vista.ArbolesBResiduosParticularView;

import javax.swing.*;

public class ArbolesBResiduosParticularController {
    private ArbolesBResiduosParticularModel model;
    private ArbolesBResiduosParticularView view;

    public ArbolesBResiduosParticularController(ArbolesBResiduosParticularModel model, ArbolesBResiduosParticularView view) {
        this.model = model;
        this.view = view;
        initController();
    }

    private void initController() {
        view.getBtnCrearArbol().addActionListener(e -> crearArbol());
        view.setVisible(true);
    }

    private void crearArbol() {
        String palabra = view.getPalabra();
        if(palabra.isEmpty()){
            JOptionPane.showMessageDialog(view, "Ingrese una palabra.");
            return;
        }

        // Establecer customBits como (n-1) bits, donde n = longitud de la palabra.
        int customBits = (palabra.length() - 1) > 0 ? (palabra.length() - 1) : 1;
        model.setCustomBits(customBits);
        view.setWordLength(palabra.length());

        boolean primeraLetra = true;
        for(int i = 0; i < palabra.length(); i++){
            char letra = palabra.charAt(i);
            String pasos = model.insertarConPasos(letra, primeraLetra);
            JOptionPane.showMessageDialog(view, pasos, "Paso a Paso para insertar '" + letra + "'", JOptionPane.INFORMATION_MESSAGE);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                // Ignorar interrupciones.
            }
            primeraLetra = false;
        }

        view.repaintTree();
    }
}
