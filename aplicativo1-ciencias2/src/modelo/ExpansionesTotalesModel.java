package modelo;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ExpansionesTotalesModel {
    private final int rows = 2;
    private int cols;
    private List<List<Integer>> table;
    private List<Integer> allKeys; // para rehash al expandir

    public ExpansionesTotalesModel(int initialCols) {
        this.cols = initialCols;
        initTable();
        allKeys = new ArrayList<>();
    }

    private void initTable() {
        table = new ArrayList<>();
        for (int r = 0; r < rows; r++) {
            List<Integer> row = new ArrayList<>();
            for (int c = 0; c < cols; c++) row.add(null);
            table.add(row);
        }
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }

    public Integer getCell(int r, int c) {
        return table.get(r).get(c);
    }

    /** Inserta k según k mod n, maneja expansión total. */
    public String insertar(int k) {
        StringBuilder log = new StringBuilder();
        allKeys.add(k);
        double density = (double) allKeys.size() / (rows * cols);
        log.append(String.format("Insertando k=%d → n=%d → densidad=%.2f\n",
                                 k, cols, density));
        if (density >= 0.75) {
            int opc = JOptionPane.showConfirmDialog(
                null,
                "La capacidad actual no permite más datos.\n" +
                "¿Expandir memoria totalmente?",
                "Aviso",
                JOptionPane.YES_NO_OPTION
            );
            if (opc == JOptionPane.YES_OPTION) {
                expandir(log);
            } else {
                log.append("Usuario canceló expansión. No se inserta k=").append(k).append("\n");
                allKeys.remove((Integer)k);
                return log.toString();
            }
        }
        int h = k % cols;
        log.append("h = k mod n = ").append(h).append("\n");
        // buscar primer hueco en columna h
        for (int r = 0; r < rows; r++) {
            if (table.get(r).get(h) == null) {
                table.get(r).set(h, k);
                log.append("Insertado en (").append(r).append(",").append(h).append(")\n");
                return log.toString();
            }
        }
        // columna llena → expandir una columna
        log.append("Columna ").append(h).append(" llena → expansión puntual\n");
        expandir(log);
        // reintentar inserción recursivamente
        return log.append(insertar(k)).toString();
    }

    /** Duplica cols y reubica todas las claves según el nuevo n */
    private void expandir(StringBuilder log) {
        log.append(">> Expansión total: n=").append(cols).append("→").append(cols*2).append("\n");
        cols *= 2;
        initTable();
        // rehash de allKeys
        List<Integer> copy = new ArrayList<>(allKeys);
        allKeys.clear();
        for (int k: copy) {
            allKeys.add(k);
            int h = k % cols;
            // insertar sin mensajes extra
            for (int r = 0; r < rows; r++) {
                if (table.get(r).get(h) == null) {
                    table.get(r).set(h, k);
                    break;
                }
            }
        }
        log.append("Reubicadas todas las claves con nuevo n=").append(cols).append("\n");
    }
}
