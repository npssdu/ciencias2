package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class BoldRedRenderer extends DefaultTableCellRenderer {
    private int lastInsertedRow;

    public BoldRedRenderer(int lastInsertedRow) {
        this.lastInsertedRow = lastInsertedRow;
    }

    public void setLastInsertedRow(int row) {
        this.lastInsertedRow = row;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        // Si es la fila donde se insertó el último elemento, se muestra en negrita y rojo.
        if (row == lastInsertedRow) {
            comp.setFont(comp.getFont().deriveFont(Font.BOLD));
            comp.setForeground(Color.RED);
        } else {
            comp.setFont(comp.getFont().deriveFont(Font.PLAIN));
            comp.setForeground(Color.BLACK);
        }
        return comp;
    }
}
