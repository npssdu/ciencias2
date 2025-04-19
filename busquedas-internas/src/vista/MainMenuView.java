package vista;

import javax.swing.*;
import java.awt.*;

public class MainMenuView extends JFrame {

    private JTabbedPane tabbedPane;

    // Pestaña 1: Búsquedas Internas
    private JPanel panelInternas;
    private JButton btnBusquedaLineal;
    private JButton btnBusquedaBinaria;
    private JButton btnHashMod;
    private JButton btnHashCuadrado;
    private JButton btnHashPlegamiento;
    private JButton btnHashTruncamiento;

    // Pestaña 2: Búsquedas por residuos
    private JPanel panelResiduos;
    private JButton btnArbolesBDigitales;
    private JButton btnArbolesBResiduosParticular;
    private JButton btnArbolesBResiduosMultiples;

    // Pestaña 3: Árboles de Huffman
    private JPanel panelHuffman;
    private JButton btnArbolesHuffman;

    // Pestaña 4: Búsquedas por rangos
    private JPanel panelRangos;
    private JButton btnMetodosElementales;
    private JButton btnMetodosRejilla;
    private JButton btnArboles2DKD;

    public MainMenuView() {
        setTitle("Menú de Algoritmos y Búsquedas Internas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla

        tabbedPane = new JTabbedPane();

        // --- Pestaña 1: Búsquedas Internas ---
        panelInternas = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        btnBusquedaLineal = new JButton("Búsqueda Lineal");
        panelInternas.add(btnBusquedaLineal, gbc);

        gbc.gridx = 1;
        btnBusquedaBinaria = new JButton("Búsqueda Binaria");
        panelInternas.add(btnBusquedaBinaria, gbc);

        gbc.gridx = 2;
        btnHashMod = new JButton("Función Hash Mod");
        panelInternas.add(btnHashMod, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        btnHashCuadrado = new JButton("Función Hash Cuadrado");
        panelInternas.add(btnHashCuadrado, gbc);

        gbc.gridx = 1;
        btnHashPlegamiento = new JButton("Función Hash Plegamiento");
        panelInternas.add(btnHashPlegamiento, gbc);

        gbc.gridx = 2;
        btnHashTruncamiento = new JButton("Función Hash Truncamiento");
        panelInternas.add(btnHashTruncamiento, gbc);

        tabbedPane.addTab("Búsquedas Internas", panelInternas);

        // --- Pestaña 2: Búsquedas por residuos ---
        panelResiduos = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        btnArbolesBDigitales = new JButton("Arboles B Digitales");
        panelResiduos.add(btnArbolesBDigitales, gbc);

        gbc.gridx = 1;
        btnArbolesBResiduosParticular = new JButton("Arboles B por Residuos Particular");
        panelResiduos.add(btnArbolesBResiduosParticular, gbc);

        gbc.gridx = 2;
        btnArbolesBResiduosMultiples = new JButton("Arboles B por Residuos Múltiples");
        panelResiduos.add(btnArbolesBResiduosMultiples, gbc);

        tabbedPane.addTab("Búsquedas por residuos", panelResiduos);

        // --- Pestaña 3: Árboles de Huffman ---
        panelHuffman = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        btnArbolesHuffman = new JButton("Arboles de Huffman");
        panelHuffman.add(btnArbolesHuffman);
        tabbedPane.addTab("Huffman", panelHuffman);

        // --- Pestaña 4: Búsquedas por rangos ---
        panelRangos = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        btnMetodosElementales = new JButton("Métodos Elementales");
        panelRangos.add(btnMetodosElementales, gbc);

        gbc.gridx = 1;
        btnMetodosRejilla = new JButton("Métodos de la rejilla");
        panelRangos.add(btnMetodosRejilla, gbc);

        gbc.gridx = 2;
        btnArboles2DKD = new JButton("Arboles 2D (KD)");
        panelRangos.add(btnArboles2DKD, gbc);

        tabbedPane.addTab("Búsquedas por rangos", panelRangos);

        add(tabbedPane, BorderLayout.CENTER);
    }

    // Getters para los botones de Búsquedas Internas
    public JButton getBtnBusquedaLineal() {
        return btnBusquedaLineal;
    }
    public JButton getBtnBusquedaBinaria() {
        return btnBusquedaBinaria;
    }
    public JButton getBtnHashMod() {
        return btnHashMod;
    }
    public JButton getBtnHashCuadrado() {
        return btnHashCuadrado;
    }
    public JButton getBtnHashPlegamiento() {
        return btnHashPlegamiento;
    }
    public JButton getBtnHashTruncamiento() {
        return btnHashTruncamiento;
    }

    // Getters para Búsquedas por residuos
    public JButton getBtnArbolesBDigitales() {
        return btnArbolesBDigitales;
    }
    public JButton getBtnArbolesBResiduosParticular() {
        return btnArbolesBResiduosParticular;
    }
    public JButton getBtnArbolesBResiduosMultiples() {
        return btnArbolesBResiduosMultiples;
    }

    // Getter para Árboles de Huffman
    public JButton getBtnArbolesHuffman() {
        return btnArbolesHuffman;
    }

    // Getters para Búsquedas por rangos
    public JButton getBtnMetodosElementales() {
        return btnMetodosElementales;
    }
    public JButton getBtnMetodosRejilla() {
        return btnMetodosRejilla;
    }
    public JButton getBtnArboles2DKD() {
        return btnArboles2DKD;
    }
}
