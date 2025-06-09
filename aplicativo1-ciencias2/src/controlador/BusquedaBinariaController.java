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
    private final boolean modoBloques;

    public BusquedaBinariaController(BusquedaBinariaModel m, BusquedaBinariaView v) {
        this(m, v, false);
    }
    public BusquedaBinariaController(BusquedaBinariaModel m, BusquedaBinariaView v, boolean modoBloques) {
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
            if (!modoBloques) {
                log("Intentando insertar clave: " + claveInt);
                if (model.insertar(claveInt)) {
                    log("Clave insertada exitosamente: " + claveInt);
                } else {
                    log("Error: No se pudo insertar la clave. Verifique duplicados o tamaño máximo.");
                    JOptionPane.showMessageDialog(view, "No se pudo insertar la clave. Verifique duplicados o tamaño máximo.");
                }
            } else {
                // --- BLOQUES ---
                int tam = model.getTamano();
                int blockSize = (int)Math.floor(Math.sqrt(tam));
                if (blockSize < 1) blockSize = 1;
                List<Integer> datos = model.getDatos();
                int numBlocks = (int)Math.ceil((double)tam / blockSize);
                boolean inserted = false;
                for (int b = 0; b < numBlocks; b++) {
                    int start = b * blockSize;
                    int end = Math.min(start + blockSize, tam);
                    int countInBlock = 0;
                    for (int i = start; i < end && i < datos.size(); i++) countInBlock++;
                    if (countInBlock < (end - start)) {
                        // Hay espacio en este bloque
                        datos.add(start + countInBlock, claveInt);
                        log("[Bloques] Insertado en bloque " + (b+1) + " (índices " + start + "-" + (end-1) + ")");
                        inserted = true;
                        break;
                    }
                }
                if (!inserted) {
                    log("[Bloques] No hay espacio en ningún bloque para insertar la clave.");
                    JOptionPane.showMessageDialog(view, "No hay espacio en ningún bloque para insertar la clave");
                } else {
                    // Mantener orden dentro de cada bloque
                    for (int b = 0; b < numBlocks; b++) {
                        int start = b * blockSize;
                        int end = Math.min(start + blockSize, datos.size());
                        datos.subList(start, end).sort(Integer::compareTo);
                    }
                }
            }
            actualizarTabla();
        } catch (NumberFormatException ex) {
            log("Error: Clave debe ser un número entero válido.");
            JOptionPane.showMessageDialog(view, "Clave debe ser un número entero válido.");
        }
    }

    private void eliminar() {
        if (model == null) { JOptionPane.showMessageDialog(view,"Defina tamaño"); return; }
        try {
            int c = Integer.parseInt(view.getTxtClave());
            if (!modoBloques) {
                if (model.eliminar(c)) {
                    actualizarTabla();
                    view.getHighlighter().highlightAll(Color.PINK);
                    log("Eliminado " + c);
                } else {
                    JOptionPane.showMessageDialog(view,"No existe");
                }
            } else {
                // --- BLOQUES ---
                List<Integer> datos = model.getDatos();
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
                    JOptionPane.showMessageDialog(view,"No existe en ningún bloque");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view,"Clave debe ser numérica");
        }
    }

    private void buscar() {
        if (model == null) { JOptionPane.showMessageDialog(view,"Defina tamaño"); return; }
        try {
            int c = Integer.parseInt(view.getTxtClave());
            if (!modoBloques) {
                int idx = model.buscar(c);
                if (idx >= 0) {
                    view.getHighlighter().highlight(idx, Color.GREEN.brighter());
                    log("Encontrado " + c + " en índice " + (idx+1));
                } else {
                    JOptionPane.showMessageDialog(view,"No encontrado");
                }
            } else {
                // --- BLOQUES ---
                List<Integer> datos = model.getDatos();
                int tam = model.getTamano();
                int blockSize = (int)Math.floor(Math.sqrt(tam));
                if (blockSize < 1) blockSize = 1;
                int numBlocks = (int)Math.ceil((double)tam / blockSize);
                boolean found = false;
                for (int b = 0; b < numBlocks; b++) {
                    int start = b * blockSize;
                    int end = Math.min(start + blockSize, tam);
                    // Búsqueda binaria dentro del bloque
                    int left = start;
                    int right = Math.min(end, datos.size()) - 1;
                    while (left <= right) {
                        int mid = left + (right - left) / 2;
                        int val = datos.get(mid);
                        if (val == c) {
                            view.getHighlighter().highlight(mid, Color.GREEN.brighter());
                            log("[Bloques] Encontrado en bloque " + (b+1) + " (índice " + mid + ")");
                            found = true;
                            break;
                        } else if (val < c) {
                            left = mid + 1;
                        } else {
                            right = mid - 1;
                        }
                    }
                    if (found) break;
                }
                if (!found) {
                    JOptionPane.showMessageDialog(view,"No encontrado en ningún bloque");
                }
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
        if (!modoBloques) {
            for (int i=0;i<d.size();i++) {
                tm.addRow(new Object[]{i+1, d.get(i)});
            }
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
