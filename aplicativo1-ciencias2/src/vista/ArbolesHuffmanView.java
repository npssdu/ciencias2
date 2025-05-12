package vista;

import modelo.ArbolesHuffmanModel;
import javax.swing.*;
import java.awt.*;

public class ArbolesHuffmanView extends JFrame {
    private JTextField txtPalabra;
    private JButton btnCrear;
    private TreePanelHuffman treePanel;
    private final JTextArea consola;
    private final JButton btnBuscarLetra;
    private final JButton btnEliminarLetra;
    private final JButton btnGuardar;
    private final JButton btnImportar;

    public ArbolesHuffmanView(ArbolesHuffmanModel model) {
        setTitle("Árboles de Huffman");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        top.add(new JLabel("Palabra:"));
        txtPalabra = new JTextField(20);
        top.add(txtPalabra);
        btnCrear = new JButton("Crear Árbol Huffman");
        top.add(btnCrear);
        btnBuscarLetra = new JButton("Buscar Letra");
        top.add(btnBuscarLetra);
        btnEliminarLetra = new JButton("Eliminar Letra");
        top.add(btnEliminarLetra);
        btnGuardar = new JButton("Guardar CSV");
        top.add(btnGuardar);
        btnImportar = new JButton("Importar CSV");
        top.add(btnImportar);

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);

        treePanel = new TreePanelHuffman(model);
        consola = new JTextArea();
        consola.setEditable(false);
        JScrollPane consolaScroll = new JScrollPane(consola);
        consolaScroll.setPreferredSize(new Dimension(300, getHeight()));
        consolaScroll.setBorder(BorderFactory.createTitledBorder("Consola"));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                          new JScrollPane(treePanel), consolaScroll);
        split.setResizeWeight(0.7);

        add(split, BorderLayout.CENTER);
    }

    public String getPalabra() {
        return txtPalabra.getText().trim();
    }

    public JButton getBtnCrear() {
        return btnCrear;
    }

    public JTextArea getConsola() {
        return consola;
    }

    public JButton getBtnBuscarLetra() {
        return btnBuscarLetra;
    }

    public JButton getBtnEliminarLetra() {
        return btnEliminarLetra;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnImportar() {
        return btnImportar;
    }

    public TreePanelHuffman getTreePanel() {
        return treePanel;
    }

    public void repaintTree() {
        treePanel.repaint();
    }
}
