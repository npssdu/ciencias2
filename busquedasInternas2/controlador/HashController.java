package busquedasInternas2.controlador;

import busquedasInternas2.modelo.HashModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import busquedasInternas2.vista.HashView;

public class HashController {
    private HashModel model;
    private HashView view;

    public HashController(HashModel model, HashView view) {
        this.model = model;
        this.view = view;
        initController();
        view.setVisible(true);
    }

    private void initController() {
        view.getBtnInsert().addActionListener(e -> insertar());
        view.getBtnUpdate().addActionListener(e -> actualizar());
        view.getBtnDelete().addActionListener(e -> eliminar());
        view.getBtnReset().addActionListener(e -> reiniciar());
        actualizarTabla();
    }

    private void insertar() {
        String clave = view.getTxtInsert();
        if (clave.isEmpty())
            return;
        int indiceInsercion = model.getIndiceDeInsercion(clave);
        boolean colision = (model.getTabla()[indiceInsercion].size() > 0);
        boolean exito = model.insertar(clave);
        if (exito) {
            String mensaje = "Clave '" + clave + "' insertada en el índice " + indiceInsercion;
            mensaje += colision ? " (Colisión detectada)" : " (Sin colisión)";
            JOptionPane.showMessageDialog(view, mensaje);
        } else {
            JOptionPane.showMessageDialog(view, "Error al insertar la clave.");
        }
        view.setTxtInsert("");
        actualizarTabla();
    }

    private void actualizar() {
        String claveAntigua = view.getTxtUpdateOld();
        String claveNueva = view.getTxtUpdateNew();
        if (claveAntigua.isEmpty() || claveNueva.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Ingrese ambas claves para actualizar.");
            return;
        }
        model.actualizar(claveAntigua, claveNueva);
        JOptionPane.showMessageDialog(view, "Clave '" + claveAntigua + "' actualizada a '" + claveNueva + "'.");
        view.setTxtUpdateOld("");
        view.setTxtUpdateNew("");
        actualizarTabla();
    }

    private void eliminar() {
        String clave = view.getTxtDelete();
        if (clave.isEmpty())
            return;
        model.eliminar(clave);
        JOptionPane.showMessageDialog(view, "Clave '" + clave + "' eliminada.");
        view.setTxtDelete("");
        actualizarTabla();
    }

    private void reiniciar() {
        model.reiniciar();
        actualizarTabla();
    }

    private void actualizarTabla() {
        DefaultTableModel dtm = view.getTableModel();
        dtm.setRowCount(0);
        int digitos = String.valueOf(model.getTamanoTabla() - 1).length();
        String formato = "%0" + digitos + "d";
        for (int i = 0; i < model.getTamanoTabla(); i++) {
            String indiceFormateado = String.format(formato, i);
            String claves = model.getTabla()[i].isEmpty() ? "-" : String.join(", ", model.getTabla()[i]);
            String colisiones = (model.getTabla()[i].size() > 1) ? ("Sí (" + (model.getTabla()[i].size() - 1) + ")") : "No";
            dtm.addRow(new Object[]{indiceFormateado, claves, colisiones});
        }
    }
}
