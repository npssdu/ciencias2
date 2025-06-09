package controlador;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import modelo.*;
import vista.*;

public class MainMenuController {
    private MainMenuView view;

    public MainMenuController(MainMenuView view) {
        this.view = view;
        initController();
    }

    private void initController() {
        // Búsquedas Internas
        view.getBtnBusquedaLineal().addActionListener(e -> abrirBusquedaLineal());
        view.getBtnBusquedaBinaria().addActionListener(e -> abrirBusquedaBinaria());
        view.getBtnHashMod().addActionListener(e -> abrirHashMod());
        view.getBtnHashCuadrado().addActionListener(e -> abrirHashCuadrado());
        view.getBtnHashPlegamiento().addActionListener(e -> abrirHashPlegamiento());
        view.getBtnHashTruncamiento().addActionListener(e -> abrirHashTruncamiento());


        // Búsquedas por residuos
        view.getBtnArbolesBDigitales().addActionListener(e -> abrirArbolesBDigitales());
        view.getBtnArbolesBResiduosParticular().addActionListener(e -> abrirArbolesBResiduosParticular());
        view.getBtnArbolesBResiduosMultiples().addActionListener(e -> abrirArbolesBResiduosMultiples());

        // Árboles de Huffman
        view.getBtnArbolesHuffman().addActionListener(e -> abrirArbolesHuffman());

        view.getBtnExpansionTotal().addActionListener(e -> abrirExpansionTotal());
        view.getBtnExpansionParcial().addActionListener(e -> abrirExpansionParcial());

    }

    // Método auxiliar que solicita al usuario el tamaño de la estructura
    private int solicitarTamano() {
        String input = JOptionPane.showInputDialog("Ingrese el tamaño de la estructura:");
        try {
            return Integer.parseInt(input.trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Valor inválido.");
            return -1;
        }
    }

    // --- Métodos para Búsquedas Internas ---
    
    private void abrirBusquedaLineal() {
        BusquedaLinealView v = new BusquedaLinealView();
        new BusquedaLinealController(null, v);
    }

    private void abrirBusquedaBinaria() {
        BusquedaBinariaView v = new BusquedaBinariaView();
        new BusquedaBinariaController(null, v);
    }
    private void abrirHashMod() {
        HashModView v = new HashModView();
        new HashModController(null, v);
    }

    private void abrirHashCuadrado() {
        HashCuadradoView v = new HashCuadradoView();
        new HashCuadradoController(null, v);
    }

    private void abrirHashPlegamiento() {
        HashPlegamientoView v = new HashPlegamientoView();
        new HashPlegamientoController(null, v);
    }

        private void abrirHashTruncamiento() {
            HashTruncamientoView v = new HashTruncamientoView();
            new HashTruncamientoController(null, v);
        }

    // --- Métodos para Búsquedas por residuos ---
    private void abrirArbolesBDigitales() {
        ArbolesBDigitalesModel modelo = new ArbolesBDigitalesModel();
        ArbolesBDigitalesView vista = new ArbolesBDigitalesView(modelo);
        new ArbolesBDigitalesController(modelo, vista);
    }

    private void abrirArbolesBResiduosParticular() {
        modelo.ArbolesBResiduosParticularModel modelo = new modelo.ArbolesBResiduosParticularModel();
        vista.ArbolesBResiduosParticularView vista = new vista.ArbolesBResiduosParticularView(modelo);
        new controlador.ArbolesBResiduosParticularController(modelo, vista);
    }


    private void abrirArbolesBResiduosMultiples() {
        ArbolesBResiduosMultiplesModel modelo = new ArbolesBResiduosMultiplesModel();
        ArbolesBResiduosMultiplesView vista = new ArbolesBResiduosMultiplesView(modelo);
        new ArbolesBResiduosMultiplesController(modelo, vista);
    }

    // --- Método para Árboles de Huffman ---
    private void abrirArbolesHuffman() {
        ArbolesHuffmanModel m = new ArbolesHuffmanModel();
        ArbolesHuffmanView v = new ArbolesHuffmanView(m);
        new ArbolesHuffmanController(m, v);
    }

    private void abrirExpansionTotal() {
        ExpansionTotalModel model = new ExpansionTotalModel();
        ExpansionTotalView viewET = new ExpansionTotalView();
        new ExpansionTotalController(model, viewET);
        this.view.getTabbedPane().addTab("Expansión Total", viewET);
    }

    private void abrirExpansionParcial() {
        JOptionPane.showMessageDialog(view, "Implementación de Expansión Parcial en desarrollo.");
    }

}
