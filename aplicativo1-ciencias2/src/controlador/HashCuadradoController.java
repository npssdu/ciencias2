package controlador;

import modelo.HashCuadradoModel;
import vista.HashCuadradoView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.io.*;
import java.nio.file.*;

public class HashCuadradoController {
    private HashCuadradoModel model;
    private final HashCuadradoView view;
    private final Path baseDir = Paths.get("archivos/HashCuadrado");

    public HashCuadradoController(HashCuadradoModel m, HashCuadradoView v) {
        this.model = m;
        this.view = v;
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
            model = new HashCuadradoModel(t);
            log("HashCuadrado tabla tamaño=" + t);
            actualizarTabla();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view,"Tamaño inválido");
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
            StringBuilder pasos = new StringBuilder();
            model.insertar(clave, pasos);
            log("Clave insertada: " + clave + "\n" + pasos);
            actualizarTabla();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Longitud de clave inválida");
        }
    }

    private void eliminar() {
        if (model == null) { JOptionPane.showMessageDialog(view,"Defina tamaño"); return; }
        String clave = view.getTxtClave();
        if (clave.isEmpty()) return;
        if (model.eliminar(clave)) {
            log("Eliminado '" + clave + "'");
            actualizarTabla();
            view.getHighlighter().highlightAll(Color.PINK);
        } else {
            JOptionPane.showMessageDialog(view,"No existe clave");
        }
    }

    private void buscar() {
        if (model == null) { JOptionPane.showMessageDialog(view,"Defina tamaño"); return; }
        String clave = view.getTxtClave();
        if (clave.isEmpty()) return;
        int idx = model.buscar(clave);
        if (idx>=0) {
            log("Encontrado '" + clave + "' en índice " + idx);
            view.getHighlighter().highlight(idx, Color.GREEN.brighter());
        } else {
            JOptionPane.showMessageDialog(view,"No encontrado");
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
            model=new HashCuadradoModel(t);
            for(int i=1;i<lines.size();i++){
                var parts=lines.get(i).split(",",2);
                if (parts.length==2 && !parts[1].isEmpty())
                    model.insertar(parts[1], new StringBuilder());
            }
            log("Importado desde "+f);
            actualizarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view,"Error importando");
        }
    }

    private void actualizarTabla() {
        DefaultTableModel tm = view.getTableModel();
        tm.setRowCount(0);
        String[] arr = model.getTabla();
        for (int i=0;i<arr.length;i++){
            tm.addRow(new Object[]{i, arr[i]==null?"":arr[i]});
        }
    }
}
