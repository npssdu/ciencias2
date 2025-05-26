package modelo;

import java.util.ArrayList;
import java.util.List;

public class ExpansionParcialModel {
    private final int rows = 2;
    private int cols = 2;
    private List<List<Integer>> table;
    private List<Integer> allKeys;

    public ExpansionParcialModel() {
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

    /** Inserta k según k mod n, maneja expansión parcial (solo una columna). */
    public String insertar(int k) {
        StringBuilder log = new StringBuilder();
        allKeys.add(k);
        double density = (double) allKeys.size() / (rows * cols);
        log.append(String.format("Insertando k=%d → n=%d → densidad=%.2f\n",
                                 k, cols, density));
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
        // columna llena → expandir solo esa columna
        log.append("Columna ").append(h).append(" llena → expansión parcial\n");
        expandirColumna(h, log);
        // reintentar inserción recursivamente
        return log.append(insertar(k)).toString();
    }

    /** Expande solo la columna h duplicando su tamaño. */
    private void expandirColumna(int h, StringBuilder log) {
        log.append(">> Expansión parcial: columna ").append(h).append(" duplicada\n");
        for (int r = 0; r < rows; r++) {
            table.get(r).add(h, null); // inserta una celda vacía en la columna h
        }
        cols++;
        // rehash de allKeys
        List<Integer> copy = new ArrayList<>(allKeys);
        allKeys.clear();
        initTable();
        for (int k : copy) {
            allKeys.add(k);
            int col = k % cols;
            for (int r = 0; r < rows; r++) {
                if (table.get(r).get(col) == null) {
                    table.get(r).set(col, k);
                    break;
                }
            }
        }
        log.append("Reubicadas todas las claves con nuevo n=").append(cols).append("\n");
    }
}
