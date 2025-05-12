package vista;

import modelo.ArbolesBDigitalesModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Panel que dibuja el árbol y permite:
 *  - zoom/pan
 *  - arrastrar nodos
 *  - colorear nodos vacíos, llenos, y nodo buscado (morado)
 */
public class TreePanel extends JPanel {
    private final ArbolesBDigitalesModel model;
    private final Map<ArbolesBDigitalesModel.Node, Point> nodePositions = new HashMap<>();
    private int wordLength = 0;

    private double scale = 1.0;
    private int panX = 0, panY = 0;
    private Point lastPanPoint = null;

    private ArbolesBDigitalesModel.Node draggedNode = null;
    private Point dragOffset = new Point();

    // Nodo resaltado por búsqueda
    private ArbolesBDigitalesModel.Node highlightedNode = null;

    public TreePanel(ArbolesBDigitalesModel model) {
        this.model = model;
        setBackground(Color.WHITE);
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    lastPanPoint = e.getPoint();
                } else {
                    Point p = transform(e.getPoint());
                    for (var entry : nodePositions.entrySet()) {
                        if (entry.getValue().distance(p) <= 15) {
                            draggedNode = entry.getKey();
                            dragOffset.x = p.x - entry.getValue().x;
                            dragOffset.y = p.y - entry.getValue().y;
                            break;
                        }
                    }
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                lastPanPoint = null;
                draggedNode = null;
            }
        };
        addMouseListener(ma);
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) && lastPanPoint != null) {
                    panX += e.getX() - lastPanPoint.x;
                    panY += e.getY() - lastPanPoint.y;
                    lastPanPoint = e.getPoint();
                    repaint();
                } else if (draggedNode != null) {
                    Point p = transform(e.getPoint());
                    p.translate(-dragOffset.x, -dragOffset.y);
                    nodePositions.put(draggedNode, p);
                    repaint();
                }
            }
        });
        addMouseWheelListener(e -> {
            double delta = (e.getWheelRotation() < 0) ? 1.1 : 0.9;
            scale = Math.max(0.2, Math.min(5.0, scale * delta));
            repaint();
        });
    }

    public void setWordLength(int length) {
        this.wordLength = length;
    }

    public void setHighlightedNode(ArbolesBDigitalesModel.Node node) {
        this.highlightedNode = node;
        repaint();
    }

    private Point transform(Point p) {
        return new Point((int)((p.x - panX)/scale), (int)((p.y - panY)/scale));
    }

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D)g0;
        g.translate(panX, panY);
        g.scale(scale, scale);
        var root = model.getRoot();
        if (root != null) {
            int xOff = Math.max(getWidth()/4, wordLength*30);
            drawNode(g, root, getWidth()/2, 50, xOff, 80);
        }
    }

    private void drawNode(Graphics g, ArbolesBDigitalesModel.Node node,
                          int x, int y, int xOff, int yOff) {
        Point pos = nodePositions.get(node);
        if (pos == null) {
            pos = new Point(x, y);
            nodePositions.put(node, pos);
        } else {
            x = pos.x; y = pos.y;
        }

        // Color del nodo
        if (node == highlightedNode) {
            g.setColor(new Color(128, 0, 128)); // morado
        } else if (node.data == null) {
            g.setColor(new Color(173, 216, 230)); // azul claro
        } else {
            g.setColor(Color.BLACK);
        }
        g.fillOval(x-15, y-15, 30, 30);

        g.setFont(g.getFont().deriveFont(Font.BOLD, 14f));
        g.setColor(Color.WHITE);
        String txt = node.data == null ? "" : node.data.toString();
        FontMetrics fm = g.getFontMetrics();
        g.drawString(txt, x - fm.stringWidth(txt)/2, y + fm.getAscent()/4);

        // Hijo izquierdo
        if (node.left != null) {
            Point c = nodePositions.getOrDefault(node.left,
                new Point(x - xOff, y + yOff));
            nodePositions.putIfAbsent(node.left, c);
            g.setColor(Color.BLUE);
            g.drawLine(x,y,c.x,c.y);
            g.drawString("0", (x+c.x)/2 -5, (y+c.y)/2 -5);
            drawNode(g, node.left, c.x, c.y, xOff/2, yOff);
        }

        // Hijo derecho
        if (node.right != null) {
            Point c = nodePositions.getOrDefault(node.right,
                new Point(x + xOff, y + yOff));
            nodePositions.putIfAbsent(node.right, c);
            g.setColor(Color.RED);
            g.drawLine(x,y,c.x,c.y);
            g.drawString("1", (x+c.x)/2 -5, (y+c.y)/2 -5);
            drawNode(g, node.right, c.x, c.y, xOff/2, yOff);
        }
    }
}
