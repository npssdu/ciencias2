package controlador;

import modelo.HashModModel;
import vista.HashModView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.nio.file.*;
import java.awt.Color;

public class HashModController {
    private HashModModel model;
    private final HashModView view;
    private final Path baseDir = Paths.get("archivos/HashMod");
    private final boolean modoBloques;

    public HashModController(HashModModel m, HashModView v) {
        this(m, v, false);
    }
    public HashModController(HashModModel m, HashModView v, boolean modoBloques) {
        this.model = m;
        this.view = v;
        this.modoBloques = modoBloques;
        init();
        view.setVisible(true);
    }

    private void init() {
        view.getBtnCrear().addActionListener(e -> crear());
        view.getBtnInsert().addActionListener(e -> insertar());
        view.getBtnEliminar().addActionListener(e -> eliminar());
        view.getBtnBuscar().addActionListener(e -> buscar());
        view.getBtnGuardar().addActionListener(e -> guardar());
        view.getBtnImportar().addActionListener(e -> importar());
    }

    private void log(String msg) {
        view.getTerminal().append(msg + "\n");
    }

    private void crear() {
        try {
            int t = Integer.parseInt(view.getTxtTamano());
            model = new HashModModel(t);
            log("HashMod tabla tamaño=" + t);
            actualizarTabla();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view,"Tamaño inválido");
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
                for (String s : model.getTabla()) if (clave.equals(s)) {
                    log("Error: Clave duplicada: " + clave);
                    JOptionPane.showMessageDialog(view, "Clave duplicada");
                    return;
                }
                int idx = Integer.parseInt(clave) % model.getTamano();
                if (model.getTabla()[idx] == null) {
                    model.getTabla()[idx] = clave;
                    log("Clave insertada exitosamente en la posición: " + idx);
                    actualizarTabla();
                    return;
                }
                String[] options = {"Lineal", "Cuadrático", "Estructuras Anidadas", "Estructuras Enlazadas"};
                String metodo = (String) JOptionPane.showInputDialog(view,
                    "Colisión detectada en el índice " + idx + ".\nSeleccione el método de resolución de colisiones:",
                    "Resolución de Colisión",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
                if (metodo == null) {
                    log("Inserción cancelada por el usuario.");
                    return;
                }
                int resultado = -2;
                switch (metodo) {
                    case "Lineal":
                        resultado = modelo.ResolucionColisiones.solucionLineal(model.getTabla(), clave, idx, model.getTamano());
                        if (resultado != -1) log("Clave insertada exitosamente en la posición: " + resultado);
                        actualizarTabla();
                        break;
                    case "Cuadrático":
                        resultado = modelo.ResolucionColisiones.solucionCuadratica(model.getTabla(), clave, idx, model.getTamano());
                        if (resultado != -1) log("Clave insertada exitosamente en la posición: " + resultado);
                        actualizarTabla();
                        break;
                    case "Estructuras Anidadas": {
                        int[] pos = model.insertarEstructuraAnidada(clave);
                        if (pos[0] == -1) {
                            log("Error: Clave duplicada o tabla llena (anidada).");
                            JOptionPane.showMessageDialog(view, "Clave duplicada o tabla llena (anidada)");
                        } else {
                            log("Clave insertada en estructura anidada: columna " + pos[0] + ", fila " + pos[1]);
                            JOptionPane.showMessageDialog(view, "Clave insertada en estructura anidada: columna " + pos[0] + ", fila " + pos[1]);
                        }
                        view.updateTableAnidada(model.getTablaAnidada(), false);
                        return;
                    }
                    case "Estructuras Enlazadas": {
                        int[] pos = model.insertarEstructuraEnlazada(clave);
                        if (pos[0] == -1) {
                            log("Error: Clave duplicada o tabla llena (enlazada).");
                            JOptionPane.showMessageDialog(view, "Clave duplicada o tabla llena (enlazada)");
                        } else {
                            log("Clave insertada en estructura enlazada: columna " + pos[0] + ", fila " + pos[1]);
                            JOptionPane.showMessageDialog(view, "Clave insertada en estructura enlazada: columna " + pos[0] + ", fila " + pos[1]);
                        }
                        view.updateTableAnidada(model.getTablaAnidada(), true);
                        return;
                    }
                }
                if (resultado == -1) {
                    log("Error: Tabla llena. No se pudo insertar la clave: " + clave);
                    JOptionPane.showMessageDialog(view, "Tabla llena. No se pudo insertar la clave");
                } else if (resultado != -2) {
                    actualizarTabla();
                }
            } else {
                // --- BLOQUES ---
                int tam = model.getTamano();
                int blockSize = (int)Math.floor(Math.sqrt(tam));
                if (blockSize < 1) blockSize = 1;
                String[] tabla = model.getTabla();
                int numBlocks = (int)Math.ceil((double)tam / blockSize);
                boolean inserted = false;
                for (int b = 0; b < numBlocks; b++) {
                    int start = b * blockSize;
                    int end = Math.min(start + blockSize, tam);
                    // Buscar espacio en el bloque
                    for (int i = start; i < end; i++) {
                        if (tabla[i] == null) {
                            tabla[i] = clave;
                            log("[Bloques] Insertado en bloque " + (b+1) + " (índice " + i + ")");
                            inserted = true;
                            break;
                        }
                    }
                    if (inserted) break;
                }
                if (!inserted) {
                    log("[Bloques] No hay espacio en ningún bloque para insertar la clave.");
                    JOptionPane.showMessageDialog(view, "No hay espacio en ningún bloque para insertar la clave");
                }
                actualizarTabla();
            }
        } catch (NumberFormatException ex) {
            log("Error: Longitud de clave inválida.");
            JOptionPane.showMessageDialog(view, "Longitud de clave inválida");
        }
    }

    private void eliminar() {
        if (model == null) { JOptionPane.showMessageDialog(view,"Defina tamaño"); return; }
        String clave = view.getTxtClave();
        if (!modoBloques) {
            if (clave.isEmpty()) return;
            if (model.eliminar(clave)) {
                log("Eliminado '" + clave + "'");
                actualizarTabla();
                view.getHighlighter().highlightAll(Color.PINK);
            } else {
                JOptionPane.showMessageDialog(view,"No existe clave");
            }
        } else {
            // --- BLOQUES ---
            String[] tabla = model.getTabla();
            int tam = model.getTamano();
            int blockSize = (int)Math.floor(Math.sqrt(tam));
            if (blockSize < 1) blockSize = 1;
            int numBlocks = (int)Math.ceil((double)tam / blockSize);
            boolean eliminado = false;
            for (int b = 0; b < numBlocks; b++) {
                int start = b * blockSize;
                int end = Math.min(start + blockSize, tam);
                for (int i = start; i < end; i++) {
                    if (tabla[i] != null && tabla[i].equals(clave)) {
                        tabla[i] = null;
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
        if (model == null) { JOptionPane.showMessageDialog(view,"Defina tamaño"); return; }
        String clave = view.getTxtClave();
        if (!modoBloques) {
            if (clave.isEmpty()) return;
            int idx = model.buscar(clave);
            if (idx>=0) {
                log("Encontrado '" + clave + "' en índice " + idx);
                view.getHighlighter().highlight(idx, Color.GREEN.brighter());
            } else {
                JOptionPane.showMessageDialog(view,"No encontrado");
            }
        } else {
            // --- BLOQUES ---
            String[] tabla = model.getTabla();
            int tam = model.getTamano();
            int blockSize = (int)Math.floor(Math.sqrt(tam));
            if (blockSize < 1) blockSize = 1;
            int numBlocks = (int)Math.ceil((double)tam / blockSize);
            boolean found = false;
            for (int b = 0; b < numBlocks; b++) {
                int start = b * blockSize;
                int end = Math.min(start + blockSize, tam);
                for (int i = start; i < end; i++) {
                    if (tabla[i] != null && tabla[i].equals(clave)) {
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

    private void guardar() {
        try {
            Files.createDirectories(baseDir);
            String name = JOptionPane.showInputDialog("Nombre archivo:");
            if (name==null||name.isBlank()) return;
            Path f = baseDir.resolve(name+".csv");
            try(PrintWriter pw=new PrintWriter(f.toFile())){
                pw.println("Tamaño,"+model.getTamano());
                for (int i=0;i<model.getTamano();i++){
                    String v = model.getTabla()[i];
                    pw.println(i + "," + (v==null?"":v));
                }
            }
            log("Guardado en "+f);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(view,"Error guardando");
        }
    }

    private void importar() {
        try {
            Files.createDirectories(baseDir);
            JFileChooser fc=new JFileChooser(baseDir.toFile());
            if (fc.showOpenDialog(view)!=JFileChooser.APPROVE_OPTION) return;
            Path f=fc.getSelectedFile().toPath();
            var lines=Files.readAllLines(f);
            int t=Integer.parseInt(lines.get(0).split(",")[1]);
            model=new HashModModel(t);
            for(int i=1;i<lines.size();i++){
                var parts=lines.get(i).split(",",2);
                if (parts.length==2 && !parts[1].isEmpty())
                    model.insertar(parts[1]);
            }
            log("Importado desde "+f);
            actualizarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view,"Error importando");
        }
    }

    private void actualizarTabla() {
        DefaultTableModel tm=view.getTableModel();
        tm.setRowCount(0);
        String[] arr = model.getTabla();
        if (!modoBloques) {
            for (int i=0;i<arr.length;i++){
                tm.addRow(new Object[]{i, arr[i]==null?"":arr[i]});
            }
        } else {
            int tam = model.getTamano();
            int blockSize = (int)Math.floor(Math.sqrt(tam));
            if (blockSize < 1) blockSize = 1;
            int numBlocks = (int)Math.ceil((double)tam / blockSize);
            for (int b = 0; b < numBlocks; b++) {
                tm.addRow(new Object[]{"--- Bloque " + (b+1) + " ---", ""});
                int start = b * blockSize;
                int end = Math.min(start + blockSize, tam);
                for (int i = start; i < end; i++) {
                    tm.addRow(new Object[]{i, arr[i]==null?"":arr[i]});
                }
            }
        }
    }
}
