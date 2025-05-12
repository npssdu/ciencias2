package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BusquedaBinariaView extends JFrame {
    private final JTextField txtTamano, txtClave;
    private final JButton btnCrear, btnInsert, btnEliminar, btnBuscar, btnGuardar, btnImportar;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JTextArea terminal;
    private final RowHighlighter highlighter;

    public BusquedaBinariaView() {
        super("Búsqueda Binaria");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Panel tamaño
        JPanel pTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        pTop.add(new JLabel("Tamaño:"));
        txtTamano = new JTextField(5);
        pTop.add(txtTamano);
        btnCrear = new JButton("Crear");
        pTop.add(btnCrear);

        // Tabla
        tableModel = new DefaultTableModel(new Object[]{"Índice","Valor"},0);
        table = new JTable(tableModel);
        highlighter = new RowHighlighter(table);
        JScrollPane scrollTable = new JScrollPane(table);

        // Terminal lateral
        terminal = new JTextArea(10, 20);
        terminal.setEditable(false);
        JScrollPane scrollTerm = new JScrollPane(terminal);
        scrollTerm.setBorder(BorderFactory.createTitledBorder("Terminal"));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                scrollTable, scrollTerm);
        split.setResizeWeight(0.7);

        // Panel acciones
        JPanel pBot = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        txtClave = new JTextField(10);
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

    public String getTxtTamano()    { return txtTamano.getText().trim(); }
    public String getTxtClave()     { return txtClave.getText().trim(); }
    public JButton getBtnCrear()     { return btnCrear; }
    public JButton getBtnInsert()    { return btnInsert; }
    public JButton getBtnEliminar()  { return btnEliminar; }
    public JButton getBtnBuscar()    { return btnBuscar; }
    public JButton getBtnGuardar()   { return btnGuardar; }
    public JButton getBtnImportar()  { return btnImportar; }
    public DefaultTableModel getTableModel()    { return tableModel; }
    public RowHighlighter getHighlighter()      { return highlighter; }
    public JTextArea getTerminal()              { return terminal; }
}
