package vista;

import modelo.ArbolesBResiduosParticularModel;
import javax.swing.*;
import java.awt.*;

public class ArbolesBResiduosParticularView extends JFrame {
    private final JTextField txtPalabra;
    private final JButton btnCrearArbol;
    private final TreePanelBResiduosParticular treePanel;

    public ArbolesBResiduosParticularView(ArbolesBResiduosParticularModel model) {
        setTitle("Árboles B por Residuos Particular");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        top.add(new JLabel("Palabra:"));
        txtPalabra = new JTextField(15);
        top.add(txtPalabra);
        btnCrearArbol = new JButton("Crear Árbol");
        top.add(btnCrearArbol);

        treePanel = new TreePanelBResiduosParticular(model);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(treePanel), BorderLayout.CENTER);
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
    public void setWordLength(int n) {
        treePanel.setWordLength(n);
    }
}
