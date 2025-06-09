package controlador;

import modelo.ExpansionTotalModel;
import vista.ExpansionTotalView;

public class ExpansionTotalController {
    private final ExpansionTotalModel model;
    private final ExpansionTotalView view;

    public ExpansionTotalController(ExpansionTotalModel model, ExpansionTotalView view) {
        this.model = model;
        this.view = view;
        init();
    }

    private void init() {
        view.btnInit.addActionListener(e -> onInit());
        view.btnInsert.addActionListener(e -> onInsert());
        view.btnDelete.addActionListener(e -> onDelete());
    }

    private void onInit() {
        try {
            int N = Integer.parseInt(view.txtBuckets.getText());
            int R = Integer.parseInt(view.txtRecords.getText());
            double DO = Double.parseDouble(view.txtDO.getText());
            double DOr = Double.parseDouble(view.txtDOredu.getText());
            model.inicializar(N, R, DO, DOr);
            view.consola.setText("Inicializado con N=" + N + ", R=" + R + "\n");
        } catch (Exception ex) {
            view.consola.append("Error: parámetros inválidos\n");
        }
    }

    private void onInsert() {
        try {
            int k = Integer.parseInt(view.txtKey.getText());
            String log = model.insertar(k);
            view.consola.append(log + "\n");
        } catch (Exception ex) {
            view.consola.append("Error al insertar\n");
        }
    }

    private void onDelete() {
        try {
            int k = Integer.parseInt(view.txtKey.getText());
            model.eliminar(k);
            view.consola.append("Eliminado: " + k + "\n");
        } catch (Exception ex) {
            view.consola.append("Error al eliminar\n");
        }
    }
}
