package vista;

import modelo.ArbolesBResiduosParticularModel;
import javax.swing.*;
import java.awt.*;

public class ArbolesBResiduosParticularView extends JFrame {

    private JTextField txtPalabra;
    private JButton btnCrearArbol;
    private TreePanelBResiduosParticular treePanel;

    public ArbolesBResiduosParticularView(ArbolesBResiduosParticularModel model) {
        setTitle("Árboles B por Residuos Particular");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800,600);
        setLocationRelativeTo(null);

        JPanel panelInput = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
        panelInput.add(new JLabel("Ingrese palabra:"));
        txtPalabra = new JTextField(15);
        panelInput.add(txtPalabra);
        btnCrearArbol = new JButton("Crear Árbol");
        panelInput.add(btnCrearArbol);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(panelInput, BorderLayout.NORTH);

        treePanel = new TreePanelBResiduosParticular(model);
        mainPanel.add(treePanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    public String getPalabra() {
        return txtPalabra.getText().trim();
    }

    public JButton getBtnCrearArbol() {
        return btnCrearArbol;
    }

    public void repaintTree() {
        treePanel.repaint();
    }

    public void setWordLength(int length) {
        treePanel.setWordLength(length);
    }
}
