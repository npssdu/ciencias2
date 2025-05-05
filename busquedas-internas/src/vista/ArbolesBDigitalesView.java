package vista;

import modelo.ArbolesBDigitalesModel;

import javax.swing.*;
import java.awt.*;

/**
 * Vista de Árboles B Digitales con:
 *  - Campo txtPalabra y botones Create/Buscar/Eliminar/Guardar/Importar.
 *  - Consola lateral (JTextArea).
 *  - TreePanel para dibujo.
 */
public class ArbolesBDigitalesView extends JFrame {
    private final JTextField txtPalabra;
    private final JButton btnCrear, btnBuscarLetra, btnEliminarLetra, btnGuardar, btnImportar;
    private final JTextArea consola;
    private final TreePanel treePanel;

    public ArbolesBDigitalesView(ArbolesBDigitalesModel model) {
        super("Árboles B Digitales");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Panel superior: ingreso y botones
        JPanel pTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pTop.add(new JLabel("Palabra:"));
        txtPalabra = new JTextField(15);
        pTop.add(txtPalabra);

        btnCrear = new JButton("Crear Árbol");
        pTop.add(btnCrear);
        btnBuscarLetra = new JButton("Buscar Letra");
        pTop.add(btnBuscarLetra);
        btnEliminarLetra = new JButton("Eliminar Letra");
        pTop.add(btnEliminarLetra);
        btnGuardar = new JButton("Guardar CSV");
        pTop.add(btnGuardar);
        btnImportar = new JButton("Importar CSV");
        pTop.add(btnImportar);

        // Panel central: dibujo y consola
        treePanel = new TreePanel(model);
        JScrollPane drawScroll = new JScrollPane(treePanel);

        consola = new JTextArea();
        consola.setEditable(false);
        JScrollPane consScroll = new JScrollPane(consola);
        consScroll.setPreferredSize(new Dimension(300, getHeight()));
        consScroll.setBorder(BorderFactory.createTitledBorder("Consola"));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                          drawScroll, consScroll);
        split.setResizeWeight(0.7);

        setLayout(new BorderLayout());
        add(pTop, BorderLayout.NORTH);
        add(split, BorderLayout.CENTER);
    }

    // Getters
    public String getTxtPalabra()       { return txtPalabra.getText().trim(); }
    public JButton getBtnCrear()        { return btnCrear; }
    public JButton getBtnBuscarLetra()  { return btnBuscarLetra; }
    public JButton getBtnEliminarLetra(){ return btnEliminarLetra; }
    public JButton getBtnGuardar()      { return btnGuardar; }
    public JButton getBtnImportar()     { return btnImportar; }
    public JTextArea getConsola()       { return consola; }
    public TreePanel getTreePanel()     { return treePanel; }
}
