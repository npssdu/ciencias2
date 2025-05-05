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
    private final JTextArea consola;
    private final JButton btnBuscarLetra;
    private final JButton btnEliminarLetra;
    private final JButton btnGuardar;
    private final JButton btnImportar;

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

        btnBuscarLetra = new JButton("Buscar Letra");
        top.add(btnBuscarLetra);
        btnEliminarLetra = new JButton("Eliminar Letra");
        top.add(btnEliminarLetra);
        btnGuardar = new JButton("Guardar CSV");
        top.add(btnGuardar);
        btnImportar = new JButton("Importar CSV");
        top.add(btnImportar);

        // Panel de dibujo
        treePanel = new TreePanelBResiduosMultiples(model);

        consola = new JTextArea();
        consola.setEditable(false);
        JScrollPane consolaScroll = new JScrollPane(consola);
        consolaScroll.setPreferredSize(new Dimension(300, getHeight()));
        consolaScroll.setBorder(BorderFactory.createTitledBorder("Consola"));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                          new JScrollPane(treePanel), consolaScroll);
        split.setResizeWeight(0.7);

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(split, BorderLayout.CENTER);
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

    public void repaintTree() {
        treePanel.repaint();
    }

    public void setWordLength(int n) {
        treePanel.setWordLength(n);
    }

    public TreePanelBResiduosMultiples getTreePanel() {
        return treePanel;
    }
}
