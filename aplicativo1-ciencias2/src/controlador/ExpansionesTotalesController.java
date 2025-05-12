package controlador;

import modelo.ExpansionesTotalesModel;
import vista.ExpansionesTotalesView;

import javax.swing.*;

public class ExpansionesTotalesController {
    private final ExpansionesTotalesModel model;
    private final ExpansionesTotalesView view;

    public ExpansionesTotalesController(ExpansionesTotalesModel m,
                                        ExpansionesTotalesView v) {
        this.model = m;
        this.view = v;
        initTable();
        initController();
    }

    private void initTable() {
        var tm = view.getTableModel();
        // Configurar columnas
        int cols = model.getCols();
        tm.setColumnCount(cols);
        // Crear identificadores [ "0","1",... ]
        String[] headers = new String[cols];
        for (int i = 0; i < cols; i++) {
            headers[i] = Integer.toString(i);
        }
        tm.setColumnIdentifiers(headers);

        // Rellenar filas vacías (rows = 2)
        tm.setRowCount(model.getRows());
    }

    private void initController() {
        view.getBtnInsert().addActionListener(e -> onInsert());
    }

    private void onInsert() {
        String s = view.getTxtK();
        try {
            int k = Integer.parseInt(s);
            String log = model.insertar(k);
            view.getConsola().append(log + "\n");
            refreshTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un entero válido.");
        }
    }

    private void refreshTable() {
        // Reconfiguramos columnas por si cambió cols tras expansión
        initTable();
        for (int r = 0; r < model.getRows(); r++) {
            for (int c = 0; c < model.getCols(); c++) {
                view.getTableModel().setValueAt(model.getCell(r, c), r, c);
            }
        }
    }
}
