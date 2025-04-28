package vista;

import modelo.ArbolesHuffmanModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class TreePanelHuffman extends JPanel {
    private ArbolesHuffmanModel model;
    private Map<ArbolesHuffmanModel.Node, Point> pos = new HashMap<>();

    // Zoom y pan
    private double scale = 1.0;
    private int panX=0, panY=0;
    private Point lastPan;

    public TreePanelHuffman(ArbolesHuffmanModel m) {
        this.model = m;
        setBackground(Color.WHITE);

        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    lastPan = e.getPoint();
                }
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) && lastPan != null) {
                    panX += e.getX() - lastPan.x;
                    panY += e.getY() - lastPan.y;
                    lastPan = e.getPoint();
                    repaint();
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                lastPan = null;
            }
        };
        addMouseListener(ma);
        addMouseMotionListener(ma);
        addMouseWheelListener(e -> {
            scale *= (e.getWheelRotation()<0 ? 1.1 : 0.9);
            scale = Math.max(0.2, Math.min(5, scale));
            repaint();
        });
    }

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D)g0;
        g.translate(panX, panY);
        g.scale(scale, scale);
        var root = model.getRoot();
        if (root != null) {
            drawNode(g, root, getWidth()/2, 50, getWidth()/4, 80);
        }
    }

    private void drawNode(Graphics2D g, ArbolesHuffmanModel.Node node,
                          int x, int y, int xOff, int yOff) {
        Point p = pos.get(node);
        if (p==null) { p=new Point(x,y); pos.put(node,p); }
        else { x=p.x; y=p.y; }

        int r = 20;
        // color según tipo
        if (node == model.getRoot())         g.setColor(Color.ORANGE);
        else if (node.symbol == null)        g.setColor(new Color(173,216,230)); // light blue
        else                                 g.setColor(Color.GREEN);

        g.fillOval(x-r, y-r, 2*r, 2*r);

        // probabilidad encima
        if (node.symbol!=null) {
            double pi = model.getProbabilities().get(node.symbol);
            g.setColor(Color.DARK_GRAY);
            g.setFont(g.getFont().deriveFont(Font.BOLD,12f));
            String t = String.format("%.3f", pi);
            g.drawString(t, x - g.getFontMetrics().stringWidth(t)/2, y - r - 4);
        }

        // símbolo o “⊕” para interno
        g.setColor(Color.BLACK);
        g.setFont(g.getFont().deriveFont(Font.BOLD,14f));
        String s = node.symbol==null? "⊕": node.symbol.toString();
        g.drawString(s, x - g.getFontMetrics().stringWidth(s)/2, y +5);

        // hijos
        if (node.left!=null) {
            var c = node.left;
            Point p2 = pos.getOrDefault(c, new Point(x-(int)xOff, y+(int)yOff));
            pos.putIfAbsent(c,p2);
            g.setColor(Color.BLACK);
            g.drawLine(x, y, p2.x, p2.y);
            drawNode(g,c,p2.x,p2.y, xOff/2, yOff);
        }
        if (node.right!=null) {
            var c = node.right;
            Point p2 = pos.getOrDefault(c, new Point(x+(int)xOff, y+(int)yOff));
            pos.putIfAbsent(c,p2);
            g.setColor(Color.BLACK);
            g.drawLine(x, y, p2.x, p2.y);
            drawNode(g,c,p2.x,p2.y, xOff/2, yOff);
        }
    }
}
