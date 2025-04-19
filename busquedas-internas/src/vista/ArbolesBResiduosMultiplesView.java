package vista;

import javax.swing.*;
import java.awt.*;

public class ArbolesBResiduosMultiplesView extends JFrame {
    private JTextField txtPalabra;
    private JButton btnCrearArbol;
    private JTextArea areaArbol;

    public ArbolesBResiduosMultiplesView() {
        setTitle("Arboles B por Residuos Múltiples");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel panelInput = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelInput.add(new JLabel("Ingrese palabra:"));
        txtPalabra = new JTextField(15);
        panelInput.add(txtPalabra);
        btnCrearArbol = new JButton("Crear Árbol");
        panelInput.add(btnCrearArbol);
        mainPanel.add(panelInput, BorderLayout.NORTH);

        areaArbol = new JTextArea();
        areaArbol.setEditable(false);
        mainPanel.add(new JScrollPane(areaArbol), BorderLayout.CENTER);

        add(mainPanel);
    }

    public String getPalabra() {
        return txtPalabra.getText().trim();
    }

    public JButton getBtnCrearArbol() {
        return btnCrearArbol;
    }

    public void setAreaArbol(String texto) {
        areaArbol.setText(texto);
    }
}
