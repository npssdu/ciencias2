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
    private final boolean modoBloques;

    public BusquedaLinealController(BusquedaLinealModel m, BusquedaLinealView v) {
        this(m, v, false);
    }
    public BusquedaLinealController(BusquedaLinealModel m, BusquedaLinealView v, boolean modoBloques) {
        this.model = m;
        this.view = v;
        this.modoBloques = modoBloques;
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
            int keyLength = Integer.parseInt(view.getTxtKeyLength());
            if (clave.length() != keyLength) {
                log("Error: La clave debe tener exactamente " + keyLength + " caracteres.");
                JOptionPane.showMessageDialog(view, "La clave debe tener exactamente " + keyLength + " caracteres.");
                return;
            }
            if (!modoBloques) {
                log("Intentando insertar clave: " + clave);
                model.insertar(clave);
                log("Clave insertada exitosamente: " + clave);
            } else {
                // --- BLOQUES ---
                int tam = model.getTamano();
                int blockSize = (int)Math.floor(Math.sqrt(tam));
                if (blockSize < 1) blockSize = 1;
                List<String> datos = model.getDatos();
                int numBlocks = (int)Math.ceil((double)tam / blockSize);
                boolean inserted = false;
                for (int b = 0; b < numBlocks; b++) {
                    int start = b * blockSize;
                    int end = Math.min(start + blockSize, tam);
                    int countInBlock = 0;
                    for (int i = start; i < end && i < datos.size(); i++) countInBlock++;
                    if (countInBlock < (end - start)) {
                        // Hay espacio en este bloque
                        datos.add(start + countInBlock, clave);
                        log("[Bloques] Insertado en bloque " + (b+1) + " (índices " + start + "-" + (end-1) + ")");
                        inserted = true;
                        break;
                    }
                }
                if (!inserted) {
                    log("[Bloques] No hay espacio en ningún bloque para insertar la clave.");
                    JOptionPane.showMessageDialog(view, "No hay espacio en ningún bloque para insertar la clave");
                }
            }
            actualizarTabla();
        } catch (NumberFormatException ex) {
            log("Error: Longitud de clave inválida.");
            JOptionPane.showMessageDialog(view, "Longitud de clave inválida");
        }
    }

    private void eliminar() {
        String c = view.getTxtClave();
        if (model == null) { JOptionPane.showMessageDialog(view,"Defina tamaño"); return; }
        if (!modoBloques) {
            if (model.eliminar(c)) {
                actualizarTabla();
                view.getHighlighter().highlightAll(Color.PINK);
                log("Eliminado '" + c + "'");
            } else {
                JOptionPane.showMessageDialog(view,"Clave no existe");
            }
        } else {
            // --- BLOQUES ---
            List<String> datos = model.getDatos();
            int tam = model.getTamano();
            int blockSize = (int)Math.floor(Math.sqrt(tam));
            if (blockSize < 1) blockSize = 1;
            int numBlocks = (int)Math.ceil((double)tam / blockSize);
            boolean eliminado = false;
            for (int b = 0; b < numBlocks; b++) {
                int start = b * blockSize;
                int end = Math.min(start + blockSize, tam);
                for (int i = start; i < end && i < datos.size(); i++) {
                    if (datos.get(i).equals(c)) {
                        datos.remove(i);
                        log("[Bloques] Eliminado de bloque " + (b+1) + " (índice " + i + ")");
                        eliminado = true;
                        break;
                    }
                }
                if (eliminado) break;
            }
            if (eliminado) {
                actualizarTabla();
                view.getHighlighter().highlightAll(Color.PINK);
            } else {
                JOptionPane.showMessageDialog(view,"Clave no existe en ningún bloque");
            }
        }
    }

    private void buscar() {
        String c = view.getTxtClave();
        if (model == null) { JOptionPane.showMessageDialog(view,"Defina tamaño"); return; }
        if (!modoBloques) {
            int idx = model.buscar(c);
            if (idx>=0) {
                view.getHighlighter().highlight(idx, Color.GREEN.brighter());
                log("Encontrado '" + c + "' en índice " + (idx+1));
            } else {
                JOptionPane.showMessageDialog(view,"No encontrado");
            }
        } else {
            // --- BLOQUES ---
            List<String> datos = model.getDatos();
            int tam = model.getTamano();
            int blockSize = (int)Math.floor(Math.sqrt(tam));
            if (blockSize < 1) blockSize = 1;
            int numBlocks = (int)Math.ceil((double)tam / blockSize);
            boolean found = false;
            for (int b = 0; b < numBlocks; b++) {
                int start = b * blockSize;
                int end = Math.min(start + blockSize, tam);
                for (int i = start; i < end && i < datos.size(); i++) {
                    if (datos.get(i).equals(c)) {
                        view.getHighlighter().highlight(i, Color.GREEN.brighter());
                        log("[Bloques] Encontrado en bloque " + (b+1) + " (índice " + i + ")");
                        found = true;
                        break;
                    }
                }
                if (found) break;
            }
            if (!found) {
                JOptionPane.showMessageDialog(view,"No encontrado en ningún bloque");
            }
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
        if (!modoBloques) {
            for (int i=0;i<d.size();i++)
                tm.addRow(new Object[]{i+1, d.get(i)});
        } else {
            int tam = model.getTamano();
            int blockSize = (int)Math.floor(Math.sqrt(tam));
            if (blockSize < 1) blockSize = 1;
            int numBlocks = (int)Math.ceil((double)tam / blockSize);
            int idx = 0;
            for (int b = 0; b < numBlocks; b++) {
                tm.addRow(new Object[]{"--- Bloque " + (b+1) + " ---", ""});
                int start = b * blockSize;
                int end = Math.min(start + blockSize, tam);
                for (int i = start; i < end && idx < d.size(); i++, idx++) {
                    tm.addRow(new Object[]{idx+1, d.get(idx)});
                }
            }
        }
    }
}
