package vista;

import modelo.ArbolesBResiduosParticularModel;
import javax.swing.*;
import java.awt.*;

public class ArbolesBResiduosParticularView extends JFrame {
    private final JTextField txtPalabra;
    private final JButton btnCrearArbol;
    private final TreePanelBResiduosParticular treePanel;
    private final JTextArea consola;
    private final JButton btnBuscarLetra;
    private final JButton btnEliminarLetra;
    private final JButton btnGuardar;
    private final JButton btnImportar;

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
        btnBuscarLetra = new JButton("Buscar Letra");
        top.add(btnBuscarLetra);
        btnEliminarLetra = new JButton("Eliminar Letra");
        top.add(btnEliminarLetra);
        btnGuardar = new JButton("Guardar CSV");
        top.add(btnGuardar);
        btnImportar = new JButton("Importar CSV");
        top.add(btnImportar);

        treePanel = new TreePanelBResiduosParticular(model);

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
    public JButton getBtnCrearArbol() {
        return btnCrearArbol;
    }
    public void repaintTree() {
        treePanel.repaint();
    }
    public void setWordLength(int n) {
        treePanel.setWordLength(n);
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

    public TreePanelBResiduosParticular getTreePanel() {
        return treePanel;
    }
}