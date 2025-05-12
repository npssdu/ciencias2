package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public abstract class HashView extends JFrame {
    protected JTextField txtInsert, txtUpdateOld, txtUpdateNew, txtDelete, txtBuscar;
    protected JButton btnInsert, btnUpdate, btnDelete, btnBuscar, btnReset;
    protected JTable table;
    protected DefaultTableModel tableModel;

    public HashView(String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Centrar la ventana

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Panel de controles
        JPanel panelControls = new JPanel();
        panelControls.setLayout(new BoxLayout(panelControls, BoxLayout.Y_AXIS));

        // Fila para Insertar
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        row1.add(new JLabel("Insertar clave:"));
        txtInsert = new JTextField(10);
        row1.add(txtInsert);
        btnInsert = new JButton("Insertar");
        row1.add(btnInsert);
        panelControls.add(row1);

        // Fila para Actualizar
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        row2.add(new JLabel("Actualizar clave (vieja):"));
        txtUpdateOld = new JTextField(10);
        row2.add(txtUpdateOld);
        row2.add(new JLabel("Actualizar clave (nueva):"));
        txtUpdateNew = new JTextField(10);
        row2.add(txtUpdateNew);
        btnUpdate = new JButton("Actualizar");
        row2.add(btnUpdate);
        panelControls.add(row2);

        // Fila para Eliminar
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        row3.add(new JLabel("Eliminar clave:"));
        txtDelete = new JTextField(10);
        row3.add(txtDelete);
        btnDelete = new JButton("Eliminar");
        row3.add(btnDelete);
        panelControls.add(row3);

        // Fila para Buscar
        JPanel row4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        row4.add(new JLabel("Buscar clave:"));
        txtBuscar = new JTextField(10);
        row4.add(txtBuscar);
        btnBuscar = new JButton("Buscar");
        row4.add(btnBuscar);
        panelControls.add(row4);

        // Fila para Reiniciar
        JPanel row5 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnReset = new JButton("Reiniciar");
        row5.add(btnReset);
        panelControls.add(row5);

        mainPanel.add(panelControls);

        // Panel de la tabla
        tableModel = new DefaultTableModel(new Object[]{"Índice", "Clave(s)", "Colisiones"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(scrollPane);

        add(mainPanel);
    }

    // Getters y setters
    public String getTxtInsert() { return txtInsert.getText().trim(); }
    public String getTxtUpdateOld() { return txtUpdateOld.getText().trim(); }
    public String getTxtUpdateNew() { return txtUpdateNew.getText().trim(); }
    public String getTxtDelete() { return txtDelete.getText().trim(); }
    public String getTxtBuscar() { return txtBuscar.getText().trim(); }
    public void setTxtInsert(String text) { txtInsert.setText(text); }
    public void setTxtUpdateOld(String text) { txtUpdateOld.setText(text); }
    public void setTxtUpdateNew(String text) { txtUpdateNew.setText(text); }
    public void setTxtDelete(String text) { txtDelete.setText(text); }
    public void setTxtBuscar(String text) { txtBuscar.setText(text); }
    public JButton getBtnInsert() { return btnInsert; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnReset() { return btnReset; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JTable getTable() { return table; }
}
