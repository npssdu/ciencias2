package vista;

import modelo.ArbolesBDigitalesModel;
import javax.swing.*;
import java.awt.*;

public class ArbolesBDigitalesView extends JFrame {

    private JTextField txtPalabra;
    private JButton btnCrearArbol;
    private TreePanel treePanel;

    public ArbolesBDigitalesView(ArbolesBDigitalesModel model) {
        setTitle("Árboles B Digitales");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Centrar la ventana

        JPanel panelInput = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelInput.add(new JLabel("Ingrese palabra:"));
        txtPalabra = new JTextField(15);
        panelInput.add(txtPalabra);
        btnCrearArbol = new JButton("Crear Árbol");
        panelInput.add(btnCrearArbol);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(panelInput, BorderLayout.NORTH);

        treePanel = new TreePanel(model);
        mainPanel.add(treePanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    public String getPalabra() {
        return txtPalabra.getText().trim();
    }

    public JButton getBtnCrearArbol() {
        return btnCrearArbol;
    }

    // Permite actualizar el dibujo del árbol.
    public void repaintTree() {
        treePanel.repaint();
    }

    // Permite ajustar el espaciado del árbol en función de la longitud de la palabra.
    public void setWordLength(int length) {
        treePanel.setWordLength(length);
    }
}
