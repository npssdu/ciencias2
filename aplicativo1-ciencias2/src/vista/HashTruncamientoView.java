package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;

public class HashTruncamientoView extends JFrame {
    private final JTextField txtTamano, txtClave, txtPos, txtKeyLength; // New field for key length
    private final JButton btnCrear, btnInsert, btnEliminar, btnBuscar, btnGuardar, btnImportar;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JTextArea terminal;
    private final RowHighlighter highlighter;

    public HashTruncamientoView() {
        super("Hash Truncamiento");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(850,600);
        setLocationRelativeTo(null);

        // Top: tamaño y posiciones
        JPanel pTop = new JPanel(new FlowLayout(FlowLayout.LEFT,8,8));
        pTop.add(new JLabel("Tamaño:"));
        txtTamano = new JTextField(5);
        pTop.add(txtTamano);
        pTop.add(new JLabel("Longitud de Clave:")); // Label for key length
        txtKeyLength = new JTextField(5); // Input for key length
        pTop.add(txtKeyLength);
        pTop.add(new JLabel("Posiciones (Ej: 1,4,7):"));
        txtPos = new JTextField(8);
        pTop.add(txtPos);
        btnCrear = new JButton("Crear");
        pTop.add(btnCrear);

        // Tabla
        tableModel = new DefaultTableModel(new Object[]{"Índice","Clave"},0);
        table = new JTable(tableModel);
        highlighter = new RowHighlighter(table);
        JScrollPane scrollTable = new JScrollPane(table);

        // Terminal lateral
        terminal = new JTextArea(10,20);
        terminal.setEditable(false);
        JScrollPane scrollTerm = new JScrollPane(terminal);
        scrollTerm.setBorder(BorderFactory.createTitledBorder("Terminal"));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollTable, scrollTerm);
        split.setResizeWeight(0.7);

        // Bottom actions
        JPanel pBot = new JPanel(new FlowLayout(FlowLayout.LEFT,8,8));
        txtClave = new JTextField(12);
        txtClave.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str == null) return;
                int keyLength = txtKeyLength.getText().isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(txtKeyLength.getText());
                if ((getLength() + str.length()) <= keyLength) {
                    super.insertString(offs, str, a);
                }
            }
        });
        pBot.add(new JLabel("Clave:"));
        pBot.add(txtClave);
        btnInsert = new JButton("Insertar");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar");
        btnGuardar = new JButton("Guardar");
        btnImportar = new JButton("Importar");
        pBot.add(btnInsert);
        pBot.add(btnEliminar);
        pBot.add(btnBuscar);
        pBot.add(btnGuardar);
        pBot.add(btnImportar);

        setLayout(new BorderLayout(5,5));
        add(pTop, BorderLayout.NORTH);
        add(split, BorderLayout.CENTER);
        add(pBot, BorderLayout.SOUTH);
    }

    public String getTxtTamano()   { return txtTamano.getText().trim(); }
    public String getTxtPos()      { return txtPos.getText().trim(); }
    public String getTxtClave()    { return txtClave.getText().trim(); }
    public String getTxtKeyLength(){ return txtKeyLength.getText().trim(); } // Getter for key length
    public JButton getBtnCrear()   { return btnCrear; }
    public JButton getBtnInsert()  { return btnInsert; }
    public JButton getBtnEliminar(){ return btnEliminar; }
    public JButton getBtnBuscar()  { return btnBuscar; }
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnImportar(){ return btnImportar; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public RowHighlighter getHighlighter()   { return highlighter; }
    public JTextArea getTerminal()           { return terminal; }
    public JComboBox<String> getCollisionMethodCombo() {
        return null;
    }

    /**
     * Actualiza la tabla para mostrar la estructura anidada o enlazada.
     * @param tablaAnidada Matriz de estructuras
     * @param soloCasillaResuelta true = solo mostrar la casilla con dato en cada columna (enlazada), false = mostrar toda la columna (anidada)
     */
    public void updateTableAnidada(java.util.ArrayList<java.util.ArrayList<String>> tablaAnidada, boolean soloCasillaResuelta) {
        int filas = 0;
        for (java.util.ArrayList<String> col : tablaAnidada) {
            if (col != null && col.size() > filas) filas = col.size();
        }
        Object[] headers = new Object[1 + tablaAnidada.size()];
        headers[0] = "Índice";
        for (int i = 1; i <= tablaAnidada.size(); i++) headers[i] = "Columna " + i;
        tableModel.setColumnIdentifiers(headers);
        tableModel.setRowCount(0);
        for (int i = 0; i < filas; i++) {
            Object[] row = new Object[1 + tablaAnidada.size()];
            row[0] = i;
            for (int j = 0; j < tablaAnidada.size(); j++) {
                java.util.ArrayList<String> col = tablaAnidada.get(j);
                if (col != null && i < col.size()) {
                    if (soloCasillaResuelta) {
                        boolean hayDato = false;
                        for (int k = 0; k < col.size(); k++) {
                            if (col.get(k) != null) {
                                if (k == i) {
                                    row[j+1] = col.get(k);
                                    hayDato = true;
                                }
                            }
                        }
                        if (!hayDato) row[j+1] = "";
                    } else {
                        row[j+1] = col.get(i) != null ? col.get(i) : "";
                    }
                } else {
                    row[j+1] = "";
                }
            }
            tableModel.addRow(row);
        }
    }
}
