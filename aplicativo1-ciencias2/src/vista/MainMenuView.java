package vista;

import javax.swing.*;
import java.awt.*;

public class MainMenuView extends JFrame {

    private JTabbedPane tabbedPane;

    // Sección 1: Métodos de Búsqueda
    private JPanel panelBusqueda;
    private JButton btnBusquedaLineal;
    private JButton btnBusquedaBinaria;

    // Sección 2: Funciones Hash
    private JPanel panelHash;
    private JButton btnHashMod;
    private JButton btnHashCuadrado;
    private JButton btnHashPlegamiento;
    private JButton btnHashTruncamiento;

    // Pestaña 2: Búsquedas por residuos
    private JPanel panelResiduos;
    private JButton btnArbolesBDigitales;
    private JButton btnArbolesBResiduosParticular;
    private JButton btnArbolesBResiduosMultiples;

    // Pestaña: Estructuras dinámicas
    private JPanel panelEstructurasDinamicas;
    private final JButton btnExpansionTotal;
    private final JButton btnExpansionParcial;
    // private JButton btnExpansionTotal;
    // private JButton btnExpansionParcial;

    // Pestaña 3: Árboles de Huffman
    private JPanel panelHuffman;
    private JButton btnArbolesHuffman;

    public MainMenuView() {
        setTitle("Aplicación Ciencias de la Computación II");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla

        tabbedPane = new JTabbedPane();

        // --- Pestaña 1: Métodos de Búsqueda y Funciones Hash ---
        JPanel panelBusquedaYHash = new JPanel(new GridLayout(1, 2, 20, 0));

        // Panel de Métodos de Búsqueda
        panelBusqueda = new JPanel(new GridBagLayout());
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Búsquedas"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        btnBusquedaLineal = new JButton("Búsqueda Lineal");
        panelBusqueda.add(btnBusquedaLineal, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        btnBusquedaBinaria = new JButton("Búsqueda Binaria");
        panelBusqueda.add(btnBusquedaBinaria, gbc);

        // Panel de Funciones Hash
        panelHash = new JPanel(new GridBagLayout());
        panelHash.setBorder(BorderFactory.createTitledBorder("Funciones Hash"));

        gbc.gridx = 0;
        gbc.gridy = 0;
        btnHashMod = new JButton("Función Hash Mod");
        panelHash.add(btnHashMod, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        btnHashCuadrado = new JButton("Función Hash Cuadrado");
        panelHash.add(btnHashCuadrado, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        btnHashPlegamiento = new JButton("Función Hash Plegamiento");
        panelHash.add(btnHashPlegamiento, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        btnHashTruncamiento = new JButton("Función Hash Truncamiento");
        panelHash.add(btnHashTruncamiento, gbc);

        // Agregar ambos paneles a la pestaña
        panelBusquedaYHash.add(panelBusqueda);
        panelBusquedaYHash.add(panelHash);
        tabbedPane.addTab("Búsquedas Internas", panelBusquedaYHash);

        // --- Pestaña 2: Búsquedas por residuos ---
        panelResiduos = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        btnArbolesBDigitales = new JButton("Arboles Digitales");
        panelResiduos.add(btnArbolesBDigitales, gbc);

        gbc.gridx = 1;
        btnArbolesBResiduosParticular = new JButton("Arboles por Residuos Particular");
        panelResiduos.add(btnArbolesBResiduosParticular, gbc);

        gbc.gridx = 2;
        btnArbolesBResiduosMultiples = new JButton("Arboles por Residuos Múltiples");
        panelResiduos.add(btnArbolesBResiduosMultiples, gbc);

        tabbedPane.addTab("Árboles Búsquedas por residuos", panelResiduos);

        // --- Pestaña: Estructuras dinámicas ---
        panelEstructurasDinamicas = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        btnExpansionTotal = new JButton("Expansión Total");
        btnExpansionParcial = new JButton("Expansión Parcial");
        panelEstructurasDinamicas.add(btnExpansionTotal);
        panelEstructurasDinamicas.add(btnExpansionParcial);
        tabbedPane.addTab("Estructuras dinámicas", panelEstructurasDinamicas);
        // btnExpansionTotal = new JButton("Expansión Total");
        // btnExpansionParcial = new JButton("Expansión Parcial");
        // pMenu.add(btnExpansionTotal);
        // pMenu.add(btnExpansionParcial);

        // --- Pestaña 3: Árboles de Huffman ---
        panelHuffman = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        btnArbolesHuffman = new JButton("Arboles de Huffman");
        panelHuffman.add(btnArbolesHuffman);
        tabbedPane.addTab("Huffman", panelHuffman);

        add(tabbedPane, BorderLayout.CENTER);
    }

    // Getters para los botones de Métodos de Búsqueda
    public JButton getBtnBusquedaLineal() {
        return btnBusquedaLineal;
    }
    public JButton getBtnBusquedaBinaria() {
        return btnBusquedaBinaria;
    }

    // Getters para los botones de Funciones Hash
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

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public JButton getBtnExpansionTotal() { return btnExpansionTotal; }
    public JButton getBtnExpansionParcial() { return btnExpansionParcial; }
}