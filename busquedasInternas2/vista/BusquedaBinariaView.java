package busquedasInternas2.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BusquedaBinariaView extends JFrame {
    private JTextField txtInsert, txtUpdateIndex, txtUpdateValue, txtDelete, txtSearch;
    private JButton btnInsert, btnUpdate, btnDelete, btnSearch, btnReset;
    private JTable table;
    private DefaultTableModel tableModel;

    public BusquedaBinariaView() {
        setTitle("Búsqueda Binaria");
        setLayout(new BorderLayout());

        JPanel panelControls = new JPanel(new GridLayout(0, 2, 5, 5));
        // Insertar
        panelControls.add(new JLabel("Insertar clave (numérica):"));
        txtInsert = new JTextField();
        panelControls.add(txtInsert);
        btnInsert = new JButton("Insertar");
        panelControls.add(btnInsert);
        panelControls.add(new JLabel(""));

        // Actualizar
        panelControls.add(new JLabel("Actualizar índice:"));
        txtUpdateIndex = new JTextField();
        panelControls.add(txtUpdateIndex);
        panelControls.add(new JLabel("Nuevo valor:"));
        txtUpdateValue = new JTextField();
        panelControls.add(txtUpdateValue);
        btnUpdate = new JButton("Actualizar");
        panelControls.add(btnUpdate);
        panelControls.add(new JLabel(""));

        // Eliminar
        panelControls.add(new JLabel("Eliminar índice:"));
        txtDelete = new JTextField();
        panelControls.add(txtDelete);
        btnDelete = new JButton("Eliminar");
        panelControls.add(btnDelete);
        panelControls.add(new JLabel(""));

        // Buscar
        panelControls.add(new JLabel("Buscar clave:"));
        txtSearch = new JTextField();
        panelControls.add(txtSearch);
        btnSearch = new JButton("Buscar");
        panelControls.add(btnSearch);
        panelControls.add(new JLabel(""));

        // Reiniciar
        btnReset = new JButton("Reiniciar");
        panelControls.add(btnReset);
        panelControls.add(new JLabel(""));

        add(panelControls, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"Índice", "Valor", "Colisiones"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        setSize(600, 400);
        setLocationRelativeTo(null);
    }

    // Métodos getters y setters para los componentes
    public String getTxtInsert() { return txtInsert.getText().trim(); }
    public String getTxtUpdateIndex() { return txtUpdateIndex.getText().trim(); }
    public String getTxtUpdateValue() { return txtUpdateValue.getText().trim(); }
    public String getTxtDelete() { return txtDelete.getText().trim(); }
    public String getTxtSearch() { return txtSearch.getText().trim(); }

    public void setTxtInsert(String text) { txtInsert.setText(text); }
    public void setTxtUpdateIndex(String text) { txtUpdateIndex.setText(text); }
    public void setTxtUpdateValue(String text) { txtUpdateValue.setText(text); }
    public void setTxtDelete(String text) { txtDelete.setText(text); }
    public void setTxtSearch(String text) { txtSearch.setText(text); }

    public JButton getBtnInsert() { return btnInsert; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnSearch() { return btnSearch; }
    public JButton getBtnReset() { return btnReset; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JTable getTable() { return table; }
}
