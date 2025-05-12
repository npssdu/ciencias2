package vista;

import modelo.ArbolesBResiduosParticularModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class TreePanelBResiduosParticular extends JPanel {
    private final ArbolesBResiduosParticularModel model;
    private final Map<ArbolesBResiduosParticularModel.Node, Point> positions = new HashMap<>();
    private int wordLength = 0;

    // Zoom & pan
    private double scale = 1.0;
    private int panX = 0, panY = 0;
    private Point lastPan = null;

    // Drag nodos
    private ArbolesBResiduosParticularModel.Node dragged = null;
    private Point dragOff = new Point();

    // Highlighted node
    private ArbolesBResiduosParticularModel.Node highlightedNode = null;

    public TreePanelBResiduosParticular(ArbolesBResiduosParticularModel model) {
        this.model = model;
        setBackground(Color.WHITE);

        // Mouse
        addMouseWheelListener(e -> {
            double d = 1 - 0.1 * e.getWheelRotation();
            scale = Math.max(0.1, Math.min(scale * d, 5));
            repaint();
        });
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    lastPan = e.getPoint();
                } else {
                    Point pt = transformPoint(e.getPoint());
                    for (var entry : positions.entrySet()) {
                        if (pt.distance(entry.getValue()) < 15) {
                            dragged = entry.getKey();
                            dragOff.x = pt.x - entry.getValue().x;
                            dragOff.y = pt.y - entry.getValue().y;
                            break;
                        }
                    }
                }
            }
            public void mouseReleased(MouseEvent e) {
                dragged = null;
                lastPan = null;
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) && lastPan!=null) {
                    panX += e.getX() - lastPan.x;
                    panY += e.getY() - lastPan.y;
                    lastPan = e.getPoint();
                    repaint();
                } else if (dragged!=null) {
                    Point pt = transformPoint(e.getPoint());
                    positions.put(dragged, new Point(pt.x - dragOff.x, pt.y - dragOff.y));
                    repaint();
                }
            }
        });
    }

    /** Ajusta el espaciado según n letras */
    public void setWordLength(int n) {
        this.wordLength = n;
    }

    public void setHighlightedNode(ArbolesBResiduosParticularModel.Node node) {
        this.highlightedNode = node;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics _g) {
        super.paintComponent(_g);
        Graphics2D g = (Graphics2D)_g;
        g.translate(panX, panY);
        g.scale(scale, scale);
        ArbolesBResiduosParticularModel.Node root = model.getRoot();
        if (root != null) {
            int initialOffset = Math.max(getWidth()/4, wordLength*30);
            drawNode(g, root, getWidth()/2, 40, initialOffset, 80);
        }
    }

    /** Transformación de pantalla->lógica */
    private Point transformPoint(Point p) {
        return new Point(
                (int)((p.x - panX)/scale),
                (int)((p.y - panY)/scale)
        );
    }

    /**
     * Dibuja recursivamente cada nodo y sus enlaces (0=izq-azul, 1=der-rojo).
     * No usa lambdas para evitar ese error de variable final.
     */
    private void drawNode(Graphics2D g,
                          ArbolesBResiduosParticularModel.Node node,
                          int x, int y,
                          int xOffset, int yOffset) {
        // posición
        Point pos = positions.get(node);
        if (pos == null) {
            pos = new Point(x, y);
            positions.put(node, pos);
        } else {
            x = pos.x;
            y = pos.y;
        }

        // Color del nodo
        if (node == highlightedNode) {
            g.setColor(new Color(128, 0, 128)); // Purple for highlighted node
        } else if (node.data == null) {
            g.setColor(new Color(173, 216, 230)); // Light blue for empty nodes
        } else {
            g.setColor(Color.BLACK);
        }
        g.fillOval(x - 15, y - 15, 30, 30);

        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(Color.WHITE);
        if (node.data != null) {
            String s = node.data.toString();
            FontMetrics fm = g.getFontMetrics();
            g.drawString(s, x - fm.stringWidth(s) / 2, y + fm.getAscent() / 4);
        }

        // hijo izquierdo
        if (node.left != null) {
            Point c = positions.getOrDefault(node.left, new Point(x - xOffset, y + yOffset));
            positions.putIfAbsent(node.left, c);
            g.setColor(Color.BLUE);
            g.drawLine(x, y, c.x, c.y);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.drawString("0", (x + c.x) / 2 - 5, (y + c.y) / 2 - 5);
            drawNode(g, node.left, c.x, c.y, xOffset / 2, yOffset);
        }
        // hijo derecho
        if (node.right != null) {
            Point c = positions.getOrDefault(node.right, new Point(x + xOffset, y + yOffset));
            positions.putIfAbsent(node.right, c);
            g.setColor(Color.RED);
            g.drawLine(x, y, c.x, c.y);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.drawString("1", (x + c.x) / 2 - 5, (y + c.y) / 2 - 5);
            drawNode(g, node.right, c.x, c.y, xOffset / 2, yOffset);
        }
    }
}
