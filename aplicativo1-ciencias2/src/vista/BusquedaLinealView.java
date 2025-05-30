package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;

public class BusquedaLinealView extends JFrame {
    private final JTextField txtTamano, txtClave, txtKeyLength; // New field for key length
    private final JButton btnCrear, btnInsert, btnEliminar, btnBuscar, btnOrdenar, btnGuardar, btnImportar;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JTextArea terminal;
    private final RowHighlighter highlighter;

    public BusquedaLinealView() {
        super("Búsqueda Lineal");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Panel tamaño
        JPanel pTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        pTop.add(new JLabel("Tamaño:"));
        txtTamano = new JTextField(5);
        pTop.add(txtTamano);
        pTop.add(new JLabel("Longitud de Clave:")); // Label for key length
        txtKeyLength = new JTextField(5); // Input for key length
        pTop.add(txtKeyLength);
        btnCrear = new JButton("Crear");
        pTop.add(btnCrear);

        // Table
        tableModel = new DefaultTableModel(new Object[]{"Índice","Clave"},0);
        table = new JTable(tableModel);
        highlighter = new RowHighlighter(table);
        JScrollPane scrollTable = new JScrollPane(table);

        // Panel lateral terminal
        terminal = new JTextArea(10, 20);
        terminal.setEditable(false);
        JScrollPane scrollTerm = new JScrollPane(terminal);
        scrollTerm.setBorder(BorderFactory.createTitledBorder("Terminal"));

        // Panel acciones
        JPanel pBot = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        txtClave = new JTextField(10);
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
        btnOrdenar = new JButton("Ordenar");
        btnGuardar = new JButton("Guardar");
        btnImportar = new JButton("Importar");
        pBot.add(btnInsert);
        pBot.add(btnEliminar);
        pBot.add(btnBuscar);
        pBot.add(btnOrdenar);
        pBot.add(btnGuardar);
        pBot.add(btnImportar);

        // Layout principal
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                scrollTable, scrollTerm);
        split.setResizeWeight(0.7);

        setLayout(new BorderLayout(5,5));
        add(pTop, BorderLayout.NORTH);
        add(split, BorderLayout.CENTER);
        add(pBot, BorderLayout.SOUTH);
    }

    // Getters para Controller
    public String getTxtTamano() { return txtTamano.getText().trim(); }
    public String getTxtClave()  { return txtClave.getText().trim(); }
    public String getTxtKeyLength() { return txtKeyLength.getText().trim(); } // Getter for key length
    public JButton getBtnCrear()     { return btnCrear; }
    public JButton getBtnInsert()    { return btnInsert; }
    public JButton getBtnEliminar()  { return btnEliminar; }
    public JButton getBtnBuscar()    { return btnBuscar; }
    public JButton getBtnOrdenar()   { return btnOrdenar; }
    public JButton getBtnGuardar()   { return btnGuardar; }
    public JButton getBtnImportar()  { return btnImportar; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public RowHighlighter getHighlighter()   { return highlighter; }
    public JTextArea getTerminal()           { return terminal; }
}
