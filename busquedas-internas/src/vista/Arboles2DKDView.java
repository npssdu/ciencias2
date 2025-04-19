package vista;

import javax.swing.*;
import java.awt.*;

public class Arboles2DKDView extends JFrame {
    private JTextField txtDatos;
    private JButton btnCrearArbol;
    private JTextArea areaArbol;

    public Arboles2DKDView() {
        setTitle("Arboles 2D (KD)");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel panelInput = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelInput.add(new JLabel("Ingrese datos 2D (x1,y1; x2,y2; ...):"));
        txtDatos = new JTextField(20);
        panelInput.add(txtDatos);
        btnCrearArbol = new JButton("Crear Árbol KD");
        panelInput.add(btnCrearArbol);
        mainPanel.add(panelInput, BorderLayout.NORTH);

        areaArbol = new JTextArea();
        areaArbol.setEditable(false);
        mainPanel.add(new JScrollPane(areaArbol), BorderLayout.CENTER);

        add(mainPanel);
    }

    public String getDatos() {
        return txtDatos.getText().trim();
    }
    public JButton getBtnCrearArbol() {
        return btnCrearArbol;
    }
    public void setAreaArbol(String texto) {
        areaArbol.setText(texto);
    }
}
