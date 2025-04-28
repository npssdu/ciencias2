package vista;

import modelo.ArbolesHuffmanModel;
import javax.swing.*;
import java.awt.*;

public class ArbolesHuffmanView extends JFrame {
    private JTextField txtPalabra;
    private JButton btnCrear;
    private TreePanelHuffman treePanel;

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

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);

        treePanel = new TreePanelHuffman(model);
        add(new JScrollPane(treePanel), BorderLayout.CENTER);
    }

    public String getPalabra() {
        return txtPalabra.getText().trim();
    }

    public JButton getBtnCrear() {
        return btnCrear;
    }

    public void repaintTree() {
        treePanel.repaint();
    }
}
