package vista;

import modelo.ArbolesBResiduosMultiplesModel;
import javax.swing.*;
import java.awt.*;

public class ArbolesBResiduosMultiplesView extends JFrame {
    private final JTextField txtPalabra;
    private final JTextField txtM;
    private final JButton btnCalcular;
    private final JButton btnCrear;
    private final TreePanelBResiduosMultiples treePanel;

    public ArbolesBResiduosMultiplesView(ArbolesBResiduosMultiplesModel model) {
        setTitle("Árboles B Residuos Múltiples");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);

        // Panel superior unificado
        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        top.add(new JLabel("Palabra:"));
        txtPalabra = new JTextField(12);
        top.add(txtPalabra);

        top.add(new JLabel("M (bits/rama):"));
        txtM = new JTextField(3);
        top.add(txtM);

        btnCalcular = new JButton("Calcular enlaces");
        top.add(btnCalcular);

        btnCrear = new JButton("Crear Árbol");
        btnCrear.setEnabled(false);
        top.add(btnCrear);

        // Panel de dibujo
        treePanel = new TreePanelBResiduosMultiples(model);

        setLayout(new BorderLayout(5,5));
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(treePanel), BorderLayout.CENTER);
    }

    public String getPalabra() {
        return txtPalabra.getText().trim();
    }

    public String getM() {
        return txtM.getText().trim();
    }

    public JButton getBtnCalcular() {
        return btnCalcular;
    }

    public JButton getBtnCrear() {
        return btnCrear;
    }

    public void repaintTree() {
        treePanel.repaint();
    }

    public void setWordLength(int n) {
        treePanel.setWordLength(n);
    }
}
