package controlador;

import modelo.BusquedaLinealModel;
import vista.BusquedaLinealView;
import vista.BoldRedRenderer;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class BusquedaLinealController {
    private BusquedaLinealModel model;
    private BusquedaLinealView view;
    private int lastInsertedIndex = -1;

    public BusquedaLinealController(BusquedaLinealModel model, BusquedaLinealView view) {
        this.model = model;
        this.view = view;
        initController();
        view.setVisible(true);
    }

    private void initController() {
        view.getBtnInsert().addActionListener(e -> insertar());
        view.getBtnUpdate().addActionListener(e -> actualizar());
        view.getBtnDelete().addActionListener(e -> eliminar());
        view.getBtnBuscar().addActionListener(e -> buscar());
        view.getBtnReset().addActionListener(e -> reiniciar());
        actualizarTabla();
    }

    private void insertar() {
        String clave = view.getTxtInsert();
        if (clave.isEmpty())
            return;
        // Verificar si la clave ya existe
        if (model.buscar(clave) != -1) {
            JOptionPane.showMessageDialog(view, "La clave '" + clave + "' ya existe. No se puede insertar duplicada.");
            view.setTxtInsert("");
            return;
        }
        boolean exito = model.insertar(clave);
        if (exito) {
            lastInsertedIndex = model.getUltimoIndiceInsertado();
            // Operación paso a paso
            StringBuilder pasoAPaso = new StringBuilder();
            pasoAPaso.append("Operación paso a paso:\n");
            pasoAPaso.append("1. Se recibe la clave: ").append(clave).append("\n");
            pasoAPaso.append("2. Se inserta secuencialmente en el índice: ").append(lastInsertedIndex + 1).append("\n");
            pasoAPaso.append("   (Los índices se muestran desde 1 en adelante)");
            JOptionPane.showMessageDialog(view, pasoAPaso.toString());
        } else {
            JOptionPane.showMessageDialog(view, "Estructura llena.");
        }
        view.setTxtInsert("");
        actualizarTabla();
    }

    private void actualizar() {
        try {
            int index = Integer.parseInt(view.getTxtUpdateIndex());
            String valor = view.getTxtUpdateValue();
            // Convertir de 1-based a 0-based
            index = index - 1;
            if (model.actualizar(index, valor)) {
                JOptionPane.showMessageDialog(view, "Índice " + (index + 1) + " actualizado a '" + valor + "'");
            } else {
                JOptionPane.showMessageDialog(view, "Índice inválido.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Índice inválido.");
        }
        view.setTxtUpdateIndex("");
        view.setTxtUpdateValue("");
        actualizarTabla();
    }

    private void eliminar() {
        try {
            int index = Integer.parseInt(view.getTxtDelete());
            // Convertir de 1-based a 0-based
            index = index - 1;
            if (model.eliminar(index)) {
                JOptionPane.showMessageDialog(view, "Índice " + (index + 1) + " eliminado.");
            } else {
                JOptionPane.showMessageDialog(view, "Índice inválido.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Índice inválido.");
        }
        view.setTxtDelete("");
        actualizarTabla();
    }

    private void buscar() {
        String clave = view.getTxtBuscar();
        int indice = model.buscar(clave);
        if (indice != -1) {
            JOptionPane.showMessageDialog(view, "Clave '" + clave + "' encontrada en el índice " + (indice + 1));
        } else {
            JOptionPane.showMessageDialog(view, "Clave no encontrada.");
        }
        view.setTxtBuscar("");
    }

    private void reiniciar() {
        model.reiniciar();
        lastInsertedIndex = -1;
        actualizarTabla();
    }

    private void actualizarTabla() {
        DefaultTableModel dtm = view.getTableModel();
        dtm.setRowCount(0);
        String[] estructura = model.getEstructura();
        for (int i = 0; i < estructura.length; i++) {
            String valor = (estructura[i] == null) ? "-" : estructura[i];
            dtm.addRow(new Object[]{(i + 1), valor, "-"});
        }
        BoldRedRenderer renderer = new BoldRedRenderer(lastInsertedIndex);
        view.getTable().getColumnModel().getColumn(1).setCellRenderer(renderer);
    }
}
