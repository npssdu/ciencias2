package controlador;

import modelo.BusquedaBinariaModel;
import vista.BusquedaBinariaView;
import vista.BoldRedRenderer;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class BusquedaBinariaController {
    private BusquedaBinariaModel model;
    private BusquedaBinariaView view;
    private int lastInsertedIndex = -1;

    public BusquedaBinariaController(BusquedaBinariaModel model, BusquedaBinariaView view) {
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
        try {
            int clave = Integer.parseInt(view.getTxtInsert());
            if (model.getLista().contains(clave)) {
                JOptionPane.showMessageDialog(view, "La clave '" + clave + "' ya existe. No se puede insertar duplicada.");
                view.setTxtInsert("");
                return;
            }
            if (model.insertar(clave)) {
                lastInsertedIndex = model.getLista().indexOf(clave);
                StringBuilder pasoAPaso = new StringBuilder();
                pasoAPaso.append("Operación paso a paso:\n");
                pasoAPaso.append("1. Se recibe la clave numérica: ").append(clave).append("\n");
                pasoAPaso.append("2. Se inserta en la lista y se ordena.\n");
                pasoAPaso.append("3. La clave se encuentra en la posición: ").append(lastInsertedIndex + 1).append("\n");
                JOptionPane.showMessageDialog(view, pasoAPaso.toString());
            } else {
                JOptionPane.showMessageDialog(view, "Estructura llena.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Ingrese un valor numérico.");
        }
        view.setTxtInsert("");
        actualizarTabla();
    }

    private void actualizar() {
        try {
            int index = Integer.parseInt(view.getTxtUpdateIndex());
            int nuevoValor = Integer.parseInt(view.getTxtUpdateValue());
            // Convertir de 1-based a 0-based
            index = index - 1;
            if (model.actualizar(index, nuevoValor)) {
                JOptionPane.showMessageDialog(view, "Índice " + (index + 1) + " actualizado a '" + nuevoValor + "'");
            } else {
                JOptionPane.showMessageDialog(view, "Índice inválido.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Valores inválidos.");
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
        try {
            int clave = Integer.parseInt(view.getTxtBuscar());
            int indice = model.buscar(clave);
            if (indice != -1) {
                JOptionPane.showMessageDialog(view, "Clave '" + clave + "' encontrada en el índice " + (indice + 1));
            } else {
                JOptionPane.showMessageDialog(view, "Clave no encontrada.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Ingrese un valor numérico.");
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
        int index = 0;
        for (Integer valor : model.getLista()) {
            dtm.addRow(new Object[]{(index + 1), valor, "-"});
            index++;
        }
        BoldRedRenderer renderer = new BoldRedRenderer(lastInsertedIndex);
        view.getTable().getColumnModel().getColumn(1).setCellRenderer(renderer);
    }
}
