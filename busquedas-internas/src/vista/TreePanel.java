package vista;

import modelo.ArbolesBDigitalesModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class TreePanel extends JPanel {

    private ArbolesBDigitalesModel model;
    private Map<ArbolesBDigitalesModel.Node, Point> nodePositions = new HashMap<>();
    private int wordLength = 0;

    // Variables para el zoom y panning
    private double scale = 1.0;
    private int panX = 0, panY = 0;
    private Point lastDragPoint = null;

    // Variables para arrastrar nodos
    private ArbolesBDigitalesModel.Node draggedNode = null;
    private Point dragOffset = new Point(0, 0);

    public TreePanel(ArbolesBDigitalesModel model) {
        this.model = model;
        setBackground(Color.WHITE);

        // Mouse listener para arrastrar nodos
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Si se presiona el botón derecho, iniciamos panning
                if (SwingUtilities.isRightMouseButton(e)) {
                    lastDragPoint = e.getPoint();
                    return;
                }
                // En caso de clic izquierdo, buscar si se hizo clic en algún nodo.
                Point clickPoint = e.getPoint();
                Point transformed = new Point((int)((clickPoint.x - panX)/scale),
                        (int)((clickPoint.y - panY)/scale));
                for (Map.Entry<ArbolesBDigitalesModel.Node, Point> entry : nodePositions.entrySet()) {
                    Point pos = entry.getValue();
                    int radius = 15;
                    if (transformed.distance(pos) <= radius) {
                        draggedNode = entry.getKey();
                        dragOffset.x = transformed.x - pos.x;
                        dragOffset.y = transformed.y - pos.y;
                        break;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                draggedNode = null;
                lastDragPoint = null;
            }
        };
        addMouseListener(ma);

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    // Panning
                    if (lastDragPoint != null) {
                        int dx = e.getX() - lastDragPoint.x;
                        int dy = e.getY() - lastDragPoint.y;
                        panX += dx;
                        panY += dy;
                        lastDragPoint = e.getPoint();
                        repaint();
                    }
                } else {
                    // Arrastrar nodos
                    if (draggedNode != null) {
                        Point newPoint = new Point((int)((e.getX()-panX)/scale - dragOffset.x),
                                (int)((e.getY()-panY)/scale - dragOffset.y));
                        nodePositions.put(draggedNode, newPoint);
                        repaint();
                    }
                }
            }
        });

        // Listener para zoom con rueda del mouse.
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double delta = 0.05f * e.getWheelRotation();
                scale -= delta;
                if (scale < 0.1) scale = 0.1;
                if (scale > 5) scale = 5;
                repaint();
            }
        });
    }

    // Permite ajustar el espaciado horizontal según la longitud de la palabra.
    public void setWordLength(int length) {
        this.wordLength = length;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Aplicar la transformación para zoom y panning.
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(panX, panY);
        g2.scale(scale, scale);

        ArbolesBDigitalesModel.Node root = model.getRoot();
        if (root != null) {
            int initialXOffset = Math.max(getWidth()/4, wordLength * 30);
            drawTree(g2, root, getWidth()/2, 50, 80, initialXOffset);
        }
    }

    private void drawTree(Graphics g, ArbolesBDigitalesModel.Node node, int x, int y, int yOffset, int xOffset) {
        // Obtener o asignar posición
        Point pos = nodePositions.get(node);
        if (pos == null) {
            pos = new Point(x, y);
            nodePositions.put(node, pos);
        } else {
            x = pos.x;
            y = pos.y;
        }

        int radius = 15;
        // Dibujo del nodo: óvalo en negro y letra en negrita (blanca)
        g.setColor(Color.BLACK);
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        Font boldFont = new Font("Arial", Font.BOLD, 16);
        g.setFont(boldFont);
        g.setColor(Color.WHITE);
        String text = (node.data == null ? "" : node.data.toString());
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        g.drawString(text, x - textWidth/2, y + textHeight/4);

        // Hijo izquierdo: etiqueta "0" en azul
        if (node.left != null) {
            int childX = x - xOffset;
            int childY = y + yOffset;
            Point leftPos = nodePositions.get(node.left);
            if (leftPos == null) {
                leftPos = new Point(childX, childY);
                nodePositions.put(node.left, leftPos);
            } else {
                childX = leftPos.x;
                childY = leftPos.y;
            }
            g.setColor(Color.BLUE);
            g.drawLine(x, y, childX, childY);
            int midX = (x + childX) / 2;
            int midY = (y + childY) / 2;
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.drawString("0", midX - 5, midY - 5);

            drawTree(g, node.left, childX, childY, yOffset, xOffset/2);
        }
        // Hijo derecho: etiqueta "1" en rojo
        if (node.right != null) {
            int childX = x + xOffset;
            int childY = y + yOffset;
            Point rightPos = nodePositions.get(node.right);
            if (rightPos == null) {
                rightPos = new Point(childX, childY);
                nodePositions.put(node.right, rightPos);
            } else {
                childX = rightPos.x;
                childY = rightPos.y;
            }
            g.setColor(Color.RED);
            g.drawLine(x, y, childX, childY);
            int midX = (x + childX) / 2;
            int midY = (y + childY) / 2;
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.drawString("1", midX - 5, midY - 5);

            drawTree(g, node.right, childX, childY, yOffset, xOffset/2);
        }
    }
}
