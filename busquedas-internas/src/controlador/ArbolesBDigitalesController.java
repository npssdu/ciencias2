package controlador;

import modelo.ArbolesBDigitalesModel;
import vista.ArbolesBDigitalesView;
import javax.swing.*;

public class ArbolesBDigitalesController {
    private ArbolesBDigitalesModel model;
    private ArbolesBDigitalesView view;

    public ArbolesBDigitalesController(ArbolesBDigitalesModel model, ArbolesBDigitalesView view) {
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
        if (palabra.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Ingrese una palabra.");
            return;
        }

        // Establecer el número de bits custom en base a la longitud de la palabra.
        int customBits = 5;
        // model.setCustomBits(customBits);
        // También se ajusta el panel de dibujo para mejorar el espaciado.
        view.setWordLength(palabra.length());

        // Para cada letra se realiza el proceso de inserción paso a paso.
        for (int i = 0; i < palabra.length(); i++) {
            char letra = palabra.charAt(i);
            String pasos = model.insertarConPasos(letra);
            // Se muestra el paso a paso en una ventana emergente.
            JOptionPane.showMessageDialog(view, pasos, "Paso a paso para insertar '" + letra + "'", JOptionPane.INFORMATION_MESSAGE);
            // Se agrega una breve pausa (opcional) para visualizar cada paso.
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                // Ignorar interrupciones
            }
        }

        // Una vez procesadas todas las letras, se actualiza el dibujo del árbol.
        view.repaintTree();
    }
}
