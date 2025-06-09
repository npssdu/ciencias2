package vista;

import javax.swing.*;
import java.awt.*;

public class ExpansionTotalView extends JPanel {
    public JTextField txtBuckets, txtRecords, txtDO, txtDOredu, txtKey;
    public JButton btnInit, btnInsert, btnDelete;
    public JTextArea consola;

    public ExpansionTotalView() {
        setLayout(new BorderLayout());

        JPanel top = new JPanel();
        txtBuckets = new JTextField(3);
        txtRecords = new JTextField(3);
        txtDO = new JTextField(3);
        txtDOredu = new JTextField(3);
        txtKey = new JTextField(5);

        btnInit = new JButton("Inicializar");
        btnInsert = new JButton("Insertar");
        btnDelete = new JButton("Eliminar");

        top.add(new JLabel("Cubetas:")); top.add(txtBuckets);
        top.add(new JLabel("Registros:")); top.add(txtRecords);
        top.add(new JLabel("DO Max (%):")); top.add(txtDO);
        top.add(new JLabel("DO Reducción (%):")); top.add(txtDOredu);
        top.add(btnInit);
        top.add(new JLabel("Clave:")); top.add(txtKey);
        top.add(btnInsert); top.add(btnDelete);

        consola = new JTextArea(20, 50);
        consola.setEditable(false);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(consola), BorderLayout.CENTER);
    }
}
