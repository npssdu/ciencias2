package vista;

import modelo.ArbolesBResiduosMultiplesModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class TreePanelBResiduosMultiples extends JPanel {
    private final ArbolesBResiduosMultiplesModel model;
    private final Map<ArbolesBResiduosMultiplesModel.Node, Point> nodePos = new HashMap<>();
    private int wordLength = 0;

    // Para arrastrar nodos
    private ArbolesBResiduosMultiplesModel.Node draggedNode = null;
    private Point dragOffset = new Point();

    // Zoom y pan
    private double scale = 1.0;
    private int panX = 0, panY = 0;
    private Point lastPanPoint = null;

    // Nodo resaltado
    private ArbolesBResiduosMultiplesModel.Node highlightedNode = null;

    public TreePanelBResiduosMultiples(ArbolesBResiduosMultiplesModel model) {
        this.model = model;
        setBackground(Color.WHITE);

        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();
                // Pan con botón derecho
                if (SwingUtilities.isRightMouseButton(e)) {
                    lastPanPoint = p;
                    return;
                }
                // Detectar clic en nodo para arrastrar
                Point logical = screenToLogical(p);
                for (var entry : nodePos.entrySet()) {
                    if (logical.distance(entry.getValue()) <= 15) {
                        draggedNode = entry.getKey();
                        dragOffset.x = logical.x - entry.getValue().x;
                        dragOffset.y = logical.y - entry.getValue().y;
                        break;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                draggedNode = null;
                lastPanPoint = null;
            }
        };
        addMouseListener(ma);

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Pan
                if (SwingUtilities.isRightMouseButton(e) && lastPanPoint != null) {
                    int dx = e.getX() - lastPanPoint.x;
                    int dy = e.getY() - lastPanPoint.y;
                    panX += dx;
                    panY += dy;
                    lastPanPoint = e.getPoint();
                    repaint();
                }
                // Drag nodo
                else if (draggedNode != null) {
                    Point logical = screenToLogical(e.getPoint());
                    logical.translate(-dragOffset.x, -dragOffset.y);
                    nodePos.put(draggedNode, logical);
                    repaint();
                }
            }
        });

        addMouseWheelListener(e -> {
            double delta = e.getWheelRotation() * 0.1;
            scale = Math.max(0.2, Math.min(5.0, scale - delta));
            repaint();
        });
    }

    /** Ajusta espacio según longitud de palabra */
    public void setWordLength(int n) {
        this.wordLength = n;
    }

    /** Resalta un nodo específico */
    public void setHighlightedNode(ArbolesBResiduosMultiplesModel.Node node) {
        this.highlightedNode = node;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0;
        // Aplicar pan y zoom
        g.translate(panX, panY);
        g.scale(scale, scale);

        ArbolesBResiduosMultiplesModel.Node root = model.getRoot();
        if (root != null) {
            int w = getWidth();
            int xOff = Math.max(w / 4, wordLength * 30);
            drawNode(g, root, w / 2, 50, xOff, 80);
        }
    }

    private void drawNode(Graphics2D g,
                          ArbolesBResiduosMultiplesModel.Node node,
                          int x, int y, int xOffset, int yOffset) {
        // Obtener o asignar posición lógica
        Point pos = nodePos.get(node);
        if (pos == null) {
            pos = new Point(x, y);
            nodePos.put(node, pos);
        } else {
            x = pos.x;
            y = pos.y;
        }

        // Dibujar nodo
        int r = 15;
        if (node == highlightedNode) {
            g.setColor(new Color(128, 0, 128)); // Morado para nodo resaltado
        } else if (node.data == null) {
            g.setColor(new Color(173, 216, 230)); // Azul claro para nodos vacíos
        } else {
            g.setColor(Color.BLACK);
        }
        g.fillOval(x - r, y - r, 2 * r, 2 * r);
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.BOLD, 14f));
        String txt = node.data == null ? "" : node.data.toString();
        FontMetrics fm = g.getFontMetrics();
        g.drawString(txt, x - fm.stringWidth(txt) / 2, y + fm.getAscent() / 4);

        int ramas = model.getNumChildren();
        int bits  = model.getM();

        // Dibujar cada rama/hijo
        for (int i = 0; i < ramas; i++) {
            // Etiqueta binaria de i en 'bits' dígitos
            String label = Integer.toBinaryString(i);
            label = String.format("%" + bits + "s", label).replace(' ', '0');

            // Asegurar espacio en children
            if (i < node.children.size()) {
                var child = node.children.get(i);
                if (child != null) {
                    Point cp = nodePos.getOrDefault(child,
                        new Point(x + (i - ramas/2) * (xOffset / ramas),
                                  y + yOffset));
                    nodePos.putIfAbsent(child, cp);

                    // Línea de conexión
                    g.setColor(Color.GRAY);
                    g.drawLine(x, y, cp.x, cp.y);
                    // Etiqueta en la línea
                    int mx = (x + cp.x) / 2;
                    int my = (y + cp.y) / 2;
                    g.setFont(g.getFont().deriveFont(Font.PLAIN, 12f));
                    g.setColor(Color.BLUE);
                    g.drawString(label, mx - fm.stringWidth(label)/2, my - 5);

                    // Recursión
                    drawNode(g, child, cp.x, cp.y, xOffset/2, yOffset);
                }
            }
        }
    }

    /** Convierte coordenada de pantalla a lógica del árbol (considerando pan/zoom) */
    private Point screenToLogical(Point screen) {
        double lx = (screen.x - panX) / scale;
        double ly = (screen.y - panY) / scale;
        return new Point((int) lx, (int) ly);
    }
}
