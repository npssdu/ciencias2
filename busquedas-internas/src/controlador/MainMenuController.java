package controlador;

import javax.swing.JOptionPane;

import modelo.*;
import vista.MainMenuView;
import vista.BusquedaLinealView;
import vista.BusquedaBinariaView;
import vista.HashModView;
import vista.HashCuadradoView;
import vista.HashPlegamientoView;
import vista.HashTruncamientoView;
import vista.ArbolesBDigitalesView;

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

        // Búsquedas por rangos
        view.getBtnMetodosElementales().addActionListener(e -> abrirMetodosElementales());
        view.getBtnMetodosRejilla().addActionListener(e -> abrirMetodosRejilla());
        view.getBtnArboles2DKD().addActionListener(e -> abrirArboles2DKD());
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
        int tamano = solicitarTamano();
        if(tamano <= 0) return;
        BusquedaLinealModel modelo = new BusquedaLinealModel(tamano);
        new BusquedaLinealController(modelo, new BusquedaLinealView());
    }

    private void abrirBusquedaBinaria() {
        int tamano = solicitarTamano();
        if(tamano <= 0) return;
        BusquedaBinariaModel modelo = new BusquedaBinariaModel(tamano);
        new BusquedaBinariaController(modelo, new BusquedaBinariaView());
    }

    private void abrirHashMod() {
        int tamano = solicitarTamano();
        if(tamano <= 0) return;
        HashModModel modelo = new HashModModel(tamano);
        new HashController(modelo, new HashModView());
    }

    private void abrirHashCuadrado() {
        int tamano = solicitarTamano();
        if(tamano <= 0) return;
        HashCuadradoModel modelo = new HashCuadradoModel(tamano);
        new HashController(modelo, new HashCuadradoView());
    }

    private void abrirHashPlegamiento() {
        int tamano = solicitarTamano();
        if(tamano <= 0) return;
        HashPlegamientoModel modelo = new HashPlegamientoModel(tamano);
        new HashController(modelo, new HashPlegamientoView());
    }

    private void abrirHashTruncamiento() {
        int tamano = solicitarTamano();
        if(tamano <= 0) return;
        HashTruncamientoModel modelo = new HashTruncamientoModel(tamano);
        new HashController(modelo, new HashTruncamientoView());
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
        JOptionPane.showMessageDialog(view, "Abriendo ventana para Árboles B por Residuos Múltiples.\n(Pendiente la implementación del algoritmo)");
    }

    // --- Método para Árboles de Huffman ---
    private void abrirArbolesHuffman() {
        JOptionPane.showMessageDialog(view, "Abriendo ventana para Árboles de Huffman.\n(Pendiente la implementación del algoritmo)");
    }

    // --- Métodos para Búsquedas por rangos ---
    private void abrirMetodosElementales() {
        JOptionPane.showMessageDialog(view, "Abriendo ventana para Búsqueda por Rangos - Métodos Elementales.\n(Pendiente la implementación del algoritmo)");
    }

    private void abrirMetodosRejilla() {
        JOptionPane.showMessageDialog(view, "Abriendo ventana para Búsqueda por Rangos - Métodos de la Rejilla.\n(Pendiente la implementación del algoritmo)");
    }

    private void abrirArboles2DKD() {
        JOptionPane.showMessageDialog(view, "Abriendo ventana para Árboles 2D (KD).\n(Pendiente la implementación del algoritmo)");
    }
}
