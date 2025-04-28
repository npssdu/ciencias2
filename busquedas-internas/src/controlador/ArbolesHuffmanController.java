package controlador;

import modelo.ArbolesHuffmanModel;
import vista.ArbolesHuffmanView;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Map;

public class ArbolesHuffmanController {
    private ArbolesHuffmanModel model;
    private ArbolesHuffmanView view;

    public ArbolesHuffmanController(ArbolesHuffmanModel m, ArbolesHuffmanView v) {
        this.model = m;
        this.view = v;
        init();
    }

    private void init() {
        view.getBtnCrear().addActionListener(e -> crear());
        view.setVisible(true);
    }

    private void crear() {
        String palabra = view.getPalabra();
        if (palabra.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Ingrese una palabra.");
            return;
        }

        // paso a paso: get lista de strings
        var pasos = model.buildTree(palabra);
        for (String paso : pasos) {
            // usando JTextArea en JOptionPane para tablas multilinea
            JTextArea area = new JTextArea(paso);
            area.setEditable(false);
            JOptionPane.showMessageDialog(view, new JScrollPane(area),
                    "Construcción árbol Huffman", JOptionPane.INFORMATION_MESSAGE);
        }

        // finalmente, mostrar tabla completa Ki,Binario,Pi,Li,L
        DefaultTableModel tm = new DefaultTableModel(
                new String[]{"Ki","Binario","Pi","Li","L"}, 0);
        Map<Character,String> codes = model.getCodes();
        Map<Character,Double> probs = model.getProbabilities();
        for (var e : codes.entrySet()) {
            char k = e.getKey();
            String bin = e.getValue();
            double pi = probs.get(k);
            int li = bin.length();
            // L = límite prefijo = li + pi?
            // si el Excel define L, aquí asumimos L = li + pi
            double L = li + pi;
            tm.addRow(new Object[]{k, bin, pi, li, L});
        }
        JTable table = new JTable(tm);
        JOptionPane.showMessageDialog(view, new JScrollPane(table),
                "Tabla de códigos Huffman", JOptionPane.INFORMATION_MESSAGE);

        view.repaintTree();
    }
}
