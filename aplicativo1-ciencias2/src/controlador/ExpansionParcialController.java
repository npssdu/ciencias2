package controlador;

import modelo.ExpansionParcialModel;
import vista.ExpansionParcialView;

import javax.swing.*;

public class ExpansionParcialController {
    private final ExpansionParcialModel model;
    private final ExpansionParcialView view;

    public ExpansionParcialController(ExpansionParcialModel m, ExpansionParcialView v) {
        this.model = m;
        this.view = v;
        initTable();
        initController();
    }

    private void initTable() {
        var tm = view.getTableModel();
        int cols = model.getCols();
        tm.setColumnCount(cols);
        String[] headers = new String[cols];
        for (int i = 0; i < cols; i++) {
            headers[i] = Integer.toString(i);
        }
        tm.setColumnIdentifiers(headers);
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
        initTable();
        for (int r = 0; r < model.getRows(); r++) {
            for (int c = 0; c < model.getCols(); c++) {
                view.getTableModel().setValueAt(model.getCell(r, c), r, c);
            }
        }
    }
}
