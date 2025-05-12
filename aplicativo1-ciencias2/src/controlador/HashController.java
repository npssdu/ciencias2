package controlador;

import modelo.HashModel;
import modelo.HashTruncamientoModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vista.HashView;
import vista.HashTruncamientoView;
import vista.BoldRedRenderer;

public class HashController {
    private HashModel model;
    private HashView view;
    private int lastInsertedIndex = -1;

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
        view.getBtnBuscar().addActionListener(e -> buscar());
        view.getBtnReset().addActionListener(e -> reiniciar());
        actualizarTabla();
    }

    private void insertar() {
        String clave = view.getTxtInsert();
        if (clave.isEmpty())
            return;
        // Si es de HashTruncamiento, obtener las posiciones ingresadas
        if (model instanceof HashTruncamientoModel && view instanceof vista.HashTruncamientoView) {
            String posStr = ((vista.HashTruncamientoView) view).getTxtPosiciones();
            if (!posStr.isEmpty()) {
                String[] tokens = posStr.split(",");
                int[] posiciones = new int[tokens.length];
                try {
                    for (int i = 0; i < tokens.length; i++) {
                        posiciones[i] = Integer.parseInt(tokens[i].trim());
                    }
                    ((HashTruncamientoModel) model).setPosiciones(posiciones);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Formato inválido en posiciones.");
                    return;
                }
            }
        }
        // Verificar que la clave no exista
        if (model.buscar(clave) != -1) {
            JOptionPane.showMessageDialog(view, "La clave '" + clave + "' ya existe. No se puede insertar duplicada.");
            view.setTxtInsert("");
            return;
        }
        int indiceInsercion = model.getIndiceDeInsercion(clave);
        boolean colision = (model.getTabla()[indiceInsercion].size() > 0);
        // Operación paso a paso según el algoritmo hash
        StringBuilder pasoAPaso = new StringBuilder();
        pasoAPaso.append("Operación paso a paso:\n");
        if(model.getClass().getSimpleName().equals("HashModModel")) {
            pasoAPaso.append("1. Convertir la clave '").append(clave).append("' a número.\n");
            pasoAPaso.append("2. Calcular: (").append(clave).append(" % ").append(model.getTamanoTabla()).append(") = ").append(indiceInsercion).append("\n");
        } else if(model.getClass().getSimpleName().equals("HashCuadradoModel")) {
            try {
                int num = Integer.parseInt(clave);
                long cuadrado = (long) num * num;
                pasoAPaso.append("1. Convertir la clave '").append(clave).append("' a número: ").append(num).append("\n");
                pasoAPaso.append("2. Calcular el cuadrado: ").append(num).append("² = ").append(cuadrado).append("\n");
                pasoAPaso.append("3. Calcular: (").append(cuadrado).append(" % ").append(model.getTamanoTabla()).append(") = ").append(indiceInsercion).append("\n");
            } catch(NumberFormatException ex) {
                pasoAPaso.append("Error: Clave no numérica.");
            }
        } else if(model.getClass().getSimpleName().equals("HashPlegamientoModel")) {
            // Para Hash Plegamiento se explica la división en grupos
            int groupSize = String.valueOf(model.getTamanoTabla() - 1).length();
            pasoAPaso.append("1. Se divide la clave '").append(clave).append("' en grupos de ").append(groupSize).append(" dígitos.\n");
            StringBuilder grupos = new StringBuilder();
            int suma = 0;
            for (int i = 0; i < clave.length(); i += groupSize) {
                int fin = Math.min(i + groupSize, clave.length());
                String grupo = clave.substring(i, fin);
                grupos.append("[").append(grupo).append("] ");
                try {
                    suma += Integer.parseInt(grupo);
                } catch(NumberFormatException ex) {
                    // Error de conversión
                }
            }
            pasoAPaso.append("   Grupos obtenidos: ").append(grupos.toString()).append("\n");
            pasoAPaso.append("2. Sumar los grupos: suma = ").append(suma).append("\n");
            pasoAPaso.append("3. Sumar 1: ").append(suma).append(" + 1 = ").append(suma + 1).append("\n");
            pasoAPaso.append("4. Calcular: (").append(suma + 1).append(" % ").append(model.getTamanoTabla()).append(") = ").append(indiceInsercion).append("\n");
        } else if(model.getClass().getSimpleName().equals("HashTruncamientoModel")) {
            // Para Hash Truncamiento se explica la selección de posiciones
            if (view instanceof vista.HashTruncamientoView) {
                String pos = ((vista.HashTruncamientoView) view).getTxtPosiciones();
                pasoAPaso.append("1. Se extraen las posiciones (").append(pos).append(") de la clave '").append(clave).append("'.\n");
                // Simular la extracción de dígitos
                StringBuilder sb = new StringBuilder();
                String[] tokens = pos.split(",");
                for (String token : tokens) {
                    int posInt = Integer.parseInt(token.trim());
                    int idx = posInt - 1;
                    if(idx >= 0 && idx < clave.length()){
                        sb.append(clave.charAt(idx));
                    }
                }
                pasoAPaso.append("2. Dígitos extraídos: ").append(sb.toString()).append("\n");
                try {
                    int num = Integer.parseInt(sb.toString());
                    pasoAPaso.append("3. Sumar 1: ").append(num).append(" + 1 = ").append(num + 1).append("\n");
                    pasoAPaso.append("4. Calcular: (").append(num + 1).append(" % ").append(model.getTamanoTabla()).append(") = ").append(indiceInsercion).append("\n");
                } catch(NumberFormatException ex) {
                    pasoAPaso.append("Error en conversión de dígitos extraídos.");
                }
            }
        } else {
            pasoAPaso.append("Operación no definida para este algoritmo.");
        }

        // Se muestra la operación paso a paso
        JOptionPane.showMessageDialog(view, pasoAPaso.toString());

        boolean exito = model.insertar(clave);
        if (exito) {
            lastInsertedIndex = indiceInsercion;
            String mensaje = "Clave '" + clave + "' insertada en el índice " + (indiceInsercion + 1);
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

    private void buscar() {
        String clave = view.getTxtBuscar();
        if (clave.isEmpty())
            return;
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
        int digitos = String.valueOf(model.getTamanoTabla()).length();
        String formato = "%0" + digitos + "d";
        for (int i = 0; i < model.getTamanoTabla(); i++) {
            String indiceFormateado = String.format(formato, (i + 1));
            String claves = model.getTabla()[i].isEmpty() ? "-" : String.join(", ", model.getTabla()[i]);
            String colisiones = (model.getTabla()[i].size() > 1) ? ("Sí (" + (model.getTabla()[i].size() - 1) + ")") : "No";
            dtm.addRow(new Object[]{indiceFormateado, claves, colisiones});
        }
        BoldRedRenderer renderer = new BoldRedRenderer(lastInsertedIndex);
        view.getTable().getColumnModel().getColumn(1).setCellRenderer(renderer);
    }
}
