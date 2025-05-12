package controlador;

import modelo.BusquedaBinariaModel;
import vista.BusquedaBinariaView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.awt.Color;

public class BusquedaBinariaController {
    private BusquedaBinariaModel model;
    private final BusquedaBinariaView view;
    private final Path baseDir = Paths.get("archivos/BusquedaBinaria");

    public BusquedaBinariaController(BusquedaBinariaModel m, BusquedaBinariaView v) {
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
        view.getBtnGuardar().addActionListener(e -> guardar());
        view.getBtnImportar().addActionListener(e -> importar());
    }

    private void log(String msg) {
        view.getTerminal().append(msg + "\n");
    }

    private void crearEstructura() {
        try {
            int tam = Integer.parseInt(view.getTxtTamano());
            model = new BusquedaBinariaModel(tam);
            log("Estructura creada tamaño=" + tam);
            actualizarTabla();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Tamaño inválido");
        }
    }

    private void insertar() {
        if (model == null) {
            log("Error: No se ha definido el tamaño de la estructura.");
            JOptionPane.showMessageDialog(view, "Defina tamaño");
            return;
        }
        String clave = view.getTxtClave();
        if (clave.isEmpty()) {
            log("Error: Clave vacía.");
            JOptionPane.showMessageDialog(view, "Clave vacía");
            return;
        }
        try {
            int claveInt = Integer.parseInt(clave); // Convertir clave a int
            log("Intentando insertar clave: " + claveInt);
            if (model.insertar(claveInt)) {
                log("Clave insertada exitosamente: " + claveInt);
                actualizarTabla();
            } else {
                log("Error: No se pudo insertar la clave. Verifique duplicados o tamaño máximo.");
                JOptionPane.showMessageDialog(view, "No se pudo insertar la clave. Verifique duplicados o tamaño máximo.");
            }
        } catch (NumberFormatException ex) {
            log("Error: Clave debe ser un número entero válido.");
            JOptionPane.showMessageDialog(view, "Clave debe ser un número entero válido.");
        }
    }

    private void eliminar() {
        if (model == null) { JOptionPane.showMessageDialog(view,"Defina tamaño"); return; }
        try {
            int c = Integer.parseInt(view.getTxtClave());
            if (model.eliminar(c)) {
                actualizarTabla();
                view.getHighlighter().highlightAll(Color.PINK);
                log("Eliminado " + c);
            } else {
                JOptionPane.showMessageDialog(view,"No existe");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view,"Clave debe ser numérica");
        }
    }

    private void buscar() {
        if (model == null) { JOptionPane.showMessageDialog(view,"Defina tamaño"); return; }
        try {
            int c = Integer.parseInt(view.getTxtClave());
            int idx = model.buscar(c);
            if (idx >= 0) {
                view.getHighlighter().highlight(idx, Color.GREEN.brighter());
                log("Encontrado " + c + " en índice " + (idx+1));
            } else {
                JOptionPane.showMessageDialog(view,"No encontrado");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view,"Clave debe ser numérica");
        }
    }

    private void guardar() {
        try {
            Files.createDirectories(baseDir);
            String name = JOptionPane.showInputDialog("Nombre archivo:");
            if (name==null||name.isBlank()) return;
            Path file = baseDir.resolve(name + ".csv");
            try (PrintWriter pw = new PrintWriter(file.toFile())) {
                pw.println("Tamaño," + model.getTamano());
                for (int v : model.getDatos()) pw.println(v);
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
            model = new BusquedaBinariaModel(tam);
            for (int i=1;i<lines.size();i++){
                model.insertar(Integer.parseInt(lines.get(i).trim()));
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
        List<Integer> d = model.getDatos();
        for (int i=0;i<d.size();i++) {
            tm.addRow(new Object[]{i+1, d.get(i)});
        }
    }
}
