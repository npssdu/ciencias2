package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ExpansionesTotalesView extends JPanel {
    private final JTextField txtK;
    private final JButton btnInsert;
    private final JTable table;
    private final DefaultTableModel tm;
    private final JTextArea consola;

    public ExpansionesTotalesView() {
        setLayout(new BorderLayout(5,5));

        // Panel de control
        JPanel pTop = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        pTop.add(new JLabel("K:"));
        txtK = new JTextField(5);
        pTop.add(txtK);
        btnInsert = new JButton("Insertar");
        pTop.add(btnInsert);
        add(pTop, BorderLayout.NORTH);

        // Tabla
        tm = new DefaultTableModel();
        table = new JTable(tm);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Consola
        consola = new JTextArea(8,30);
        consola.setEditable(false);
        add(new JScrollPane(consola), BorderLayout.SOUTH);
    }

    public String getTxtK() { return txtK.getText().trim(); }
    public JButton getBtnInsert() { return btnInsert; }
    public DefaultTableModel getTableModel() { return tm; }
    public JTextArea getConsola() { return consola; }
}
