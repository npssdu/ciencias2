package vista;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class RowHighlighter {
    private final JTable table;
    private final DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();

    public RowHighlighter(JTable table) {
        this.table = table;
    }

    public void highlight(int row, Color color) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable t,Object val,
                    boolean sel,boolean foc,int r,int col){
                Component c = defaultRenderer.getTableCellRendererComponent(t,val,sel,foc,r,col);
                c.setBackground(r==row ? color : Color.WHITE);
                return c;
            }
        });
        table.repaint();
    }

    public void highlightAll(Color color) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable t,Object val,
                    boolean sel,boolean foc,int r,int col){
                Component c = defaultRenderer.getTableCellRendererComponent(t,val,sel,foc,r,col);
                c.setBackground(color);
                return c;
            }
        });
        table.repaint();
        // restaurar
        SwingUtilities.invokeLater(() -> {
            table.setDefaultRenderer(Object.class, defaultRenderer);
            table.repaint();
        });
    }
}
