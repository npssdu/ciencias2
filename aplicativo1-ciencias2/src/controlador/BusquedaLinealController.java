package controlador;

import modelo.BusquedaLinealModel;
import vista.BusquedaLinealView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.awt.Color;

public class BusquedaLinealController {
    private BusquedaLinealModel model;
    private final BusquedaLinealView view;
    private final Path baseDir = Paths.get("archivos/BusquedaLineal");

    public BusquedaLinealController(BusquedaLinealModel m, BusquedaLinealView v) {
        this.model = m;
        this.view = v;
        init();
        view.setVisible(true);
    }

    private void init() {
        view.getBtnCrear().addActionListener(e -> crearEstructura());
        view.getBtnInsert().addActionListener(e -> insertar());
        view.getBtnEliminar().addActionListener(e -> eliminar());
        view.getBtnBuscar().addActionListener(e -> buscar());
        view.getBtnOrdenar().addActionListener(e -> ordenar());
        view.getBtnGuardar().addActionListener(e -> guardar());
        view.getBtnImportar().addActionListener(e -> importar());
    }

    private void log(String msg) {
        view.getTerminal().append(msg + "\n");
    }

    private void crearEstructura() {
        String t = view.getTxtTamano();
        try {
            int tam = Integer.parseInt(t);
            model = new BusquedaLinealModel(tam);
            log("Estructura creada con tamaño=" + tam);
            actualizarTabla();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Tamaño inválido");
        }
    }

    private void insertar() {
        if (model == null) {
            JOptionPane.showMessageDialog(view, "Defina tamaño");
            return;
        }
        String clave = view.getTxtClave();
        if (clave.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Clave vacía");
            return;
        }
        try {
            int keyLength = Integer.parseInt(view.getTxtKeyLength());
            if (clave.length() != keyLength) {
                JOptionPane.showMessageDialog(view, "La clave debe tener exactamente " + keyLength + " caracteres.");
                return;
            }
            model.insertar(clave);
            log("Clave insertada: " + clave);
            actualizarTabla();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Longitud de clave inválida");
        }
    }

    private void eliminar() {
        String c = view.getTxtClave();
        if (model == null) { JOptionPane.showMessageDialog(view,"Defina tamaño"); return; }
        if (model.eliminar(c)) {
            actualizarTabla();
            view.getHighlighter().highlightAll(Color.PINK);
            log("Eliminado '" + c + "'");
        } else {
            JOptionPane.showMessageDialog(view,"Clave no existe");
        }
    }

    private void buscar() {
        String c = view.getTxtClave();
        if (model == null) { JOptionPane.showMessageDialog(view,"Defina tamaño"); return; }
        int idx = model.buscar(c);
        if (idx>=0) {
            view.getHighlighter().highlight(idx, Color.GREEN.brighter());
            log("Encontrado '" + c + "' en índice " + (idx+1));
        } else {
            JOptionPane.showMessageDialog(view,"No encontrado");
        }
    }

    private void ordenar() {
        if (model == null) return;
        model.ordenar();
        actualizarTabla();
        log("Lista ordenada");
    }

    private void guardar() {
        try {
            Files.createDirectories(baseDir);
            String name = JOptionPane.showInputDialog("Nombre archivo:");
            if (name==null || name.isBlank()) return;
            Path file = baseDir.resolve(name + ".csv");
            try (PrintWriter pw = new PrintWriter(file.toFile())) {
                pw.println("Tamaño," + model.getTamano());
                for (String c: model.getDatos())
                    pw.println(c);
            }
            log("Guardado en " + file);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(view,"Error guardando");
        }
    }

    private void importar() {
        try {
            Files.createDirectories(baseDir);
            JFileChooser fc = new JFileChooser(baseDir.toFile());
            if (fc.showOpenDialog(view)!=JFileChooser.APPROVE_OPTION) return;
            Path file = fc.getSelectedFile().toPath();
            List<String> lines = Files.readAllLines(file);
            int tam = Integer.parseInt(lines.get(0).split(",")[1]);
            model = new BusquedaLinealModel(tam);
            for (int i=1;i<lines.size();i++){
                model.insertar(lines.get(i).trim());
            }
            actualizarTabla();
            log("Importado desde " + file);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view,"Error importando");
        }
    }

    private void actualizarTabla() {
        DefaultTableModel tm = view.getTableModel();
        tm.setRowCount(0);
        List<String> d = model.getDatos();
        for (int i=0;i<d.size();i++)
            tm.addRow(new Object[]{i+1, d.get(i)});
    }
}
