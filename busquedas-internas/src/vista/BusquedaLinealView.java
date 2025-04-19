package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BusquedaLinealView extends JFrame {
    private JTextField txtInsert, txtUpdateIndex, txtUpdateValue, txtDelete, txtBuscar;
    private JButton btnInsert, btnUpdate, btnDelete, btnBuscar, btnReset;
    private JTable table;
    private DefaultTableModel tableModel;

    public BusquedaLinealView() {
        setTitle("Búsqueda Lineal");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Centrar la ventana en pantalla

        // Panel principal con BoxLayout vertical
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Panel de controles: cada fila se centra usando FlowLayout
        JPanel panelControls = new JPanel();
        panelControls.setLayout(new BoxLayout(panelControls, BoxLayout.Y_AXIS));

        // Fila de Insertar
        JPanel rowInsert = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        rowInsert.add(new JLabel("Insertar clave:"));
        txtInsert = new JTextField(10);
        rowInsert.add(txtInsert);
        btnInsert = new JButton("Insertar");
        rowInsert.add(btnInsert);
        panelControls.add(rowInsert);

        // Fila de Actualizar
        JPanel rowActualizar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        rowActualizar.add(new JLabel("Actualizar índice:"));
        txtUpdateIndex = new JTextField(5);
        rowActualizar.add(txtUpdateIndex);
        rowActualizar.add(new JLabel("Nuevo valor:"));
        txtUpdateValue = new JTextField(10);
        rowActualizar.add(txtUpdateValue);
        btnUpdate = new JButton("Actualizar");
        rowActualizar.add(btnUpdate);
        panelControls.add(rowActualizar);

        // Fila de Eliminar
        JPanel rowEliminar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        rowEliminar.add(new JLabel("Eliminar índice:"));
        txtDelete = new JTextField(5);
        rowEliminar.add(txtDelete);
        btnDelete = new JButton("Eliminar");
        rowEliminar.add(btnDelete);
        panelControls.add(rowEliminar);

        // Fila de Buscar
        JPanel rowBuscar = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        rowBuscar.add(new JLabel("Buscar clave:"));
        txtBuscar = new JTextField(10);
        rowBuscar.add(txtBuscar);
        btnBuscar = new JButton("Buscar");
        rowBuscar.add(btnBuscar);
        panelControls.add(rowBuscar);

        // Fila de Reiniciar
        JPanel rowReset = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnReset = new JButton("Reiniciar");
        rowReset.add(btnReset);
        panelControls.add(rowReset);

        mainPanel.add(panelControls);

        // Panel de la tabla (centrado)
        tableModel = new DefaultTableModel(new Object[]{"Índice", "Valor", "Colisiones"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(scrollPane);

        add(mainPanel);
    }

    // Getters y setters de los componentes
    public String getTxtInsert() { return txtInsert.getText().trim(); }
    public String getTxtUpdateIndex() { return txtUpdateIndex.getText().trim(); }
    public String getTxtUpdateValue() { return txtUpdateValue.getText().trim(); }
    public String getTxtDelete() { return txtDelete.getText().trim(); }
    public String getTxtBuscar() { return txtBuscar.getText().trim(); }
    public void setTxtInsert(String text) { txtInsert.setText(text); }
    public void setTxtUpdateIndex(String text) { txtUpdateIndex.setText(text); }
    public void setTxtUpdateValue(String text) { txtUpdateValue.setText(text); }
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
