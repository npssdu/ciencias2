package busquedasInternas2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class MainMenu extends JFrame {
    // Componentes del menú principal
    private JComboBox<String> comboAlgoritmos;
    private JTextField txtTamano;
    private JButton btnIniciar;

    public MainMenu() {
        setTitle("Menú de Algoritmos y Funciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Selección de método
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Seleccione el método:"), gbc);

        String[] opciones = {
                "Búsqueda Lineal",
                "Búsqueda Binaria",
                "Hash Mod",
                "Hash Cuadrado",
                "Hash Plegamiento",
                "Hash Truncamiento"
        };
        comboAlgoritmos = new JComboBox<>(opciones);
        gbc.gridx = 1;
        add(comboAlgoritmos, gbc);

        // Tamaño de la estructura
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Tamaño de la estructura:"), gbc);

        txtTamano = new JTextField(10);
        gbc.gridx = 1;
        add(txtTamano, gbc);

        // Botón para iniciar
        btnIniciar = new JButton("Iniciar");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(btnIniciar, gbc);

        btnIniciar.addActionListener(e -> iniciarMetodo());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void iniciarMetodo() {
        String opcion = (String) comboAlgoritmos.getSelectedItem();
        int tamano;
        try {
            tamano = Integer.parseInt(txtTamano.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un tamaño válido.");
            return;
        }

        switch(opcion) {
            case "Búsqueda Lineal":
                new BusquedaLinealWindow(tamano);
                break;
            case "Búsqueda Binaria":
                new BusquedaBinariaWindow(tamano);
                break;
            case "Hash Mod":
                new HashModWindow(tamano);
                break;
            case "Hash Cuadrado":
                new HashCuadradoWindow(tamano);
                break;
            case "Hash Plegamiento":
                new HashPlegamientoWindow(tamano);
                break;
            case "Hash Truncamiento":
                new HashTruncamientoWindow(tamano);
                break;
        }
    }

    // ======================================
    // Ventana para Búsqueda Lineal
    // ======================================
    class BusquedaLinealWindow extends JFrame {
        private String[] estructura;
        private DefaultTableModel tableModel;
        private JTable table;
        private JTextField txtInsert, txtUpdateIndex, txtUpdateValue, txtDelete, txtSearch;
        private int nextInsertIndex = 0;
        private int tamano;

        public BusquedaLinealWindow(int tamano) {
            this.tamano = tamano;
            estructura = new String[tamano];
            initComponents();
        }

        private void initComponents() {
            setTitle("Búsqueda Lineal");
            setLayout(new BorderLayout());

            JPanel panelControls = new JPanel(new GridLayout(0, 2, 5, 5));
            // Insertar
            panelControls.add(new JLabel("Insertar clave:"));
            txtInsert = new JTextField();
            panelControls.add(txtInsert);
            JButton btnInsert = new JButton("Insertar");
            btnInsert.addActionListener(e -> insertar());
            panelControls.add(btnInsert);
            panelControls.add(new JLabel(""));

            // Actualizar
            panelControls.add(new JLabel("Actualizar índice:"));
            txtUpdateIndex = new JTextField();
            panelControls.add(txtUpdateIndex);
            panelControls.add(new JLabel("Nuevo valor:"));
            txtUpdateValue = new JTextField();
            panelControls.add(txtUpdateValue);
            JButton btnUpdate = new JButton("Actualizar");
            btnUpdate.addActionListener(e -> actualizar());
            panelControls.add(btnUpdate);
            panelControls.add(new JLabel(""));

            // Eliminar
            panelControls.add(new JLabel("Eliminar índice:"));
            txtDelete = new JTextField();
            panelControls.add(txtDelete);
            JButton btnDelete = new JButton("Eliminar");
            btnDelete.addActionListener(e -> eliminar());
            panelControls.add(btnDelete);
            panelControls.add(new JLabel(""));

            // Buscar
            panelControls.add(new JLabel("Buscar clave:"));
            txtSearch = new JTextField();
            panelControls.add(txtSearch);
            JButton btnSearch = new JButton("Buscar");
            btnSearch.addActionListener(e -> buscar());
            panelControls.add(btnSearch);
            panelControls.add(new JLabel(""));

            // Reiniciar
            JButton btnReset = new JButton("Reiniciar");
            btnReset.addActionListener(e -> reiniciar());
            panelControls.add(btnReset);
            panelControls.add(new JLabel(""));

            add(panelControls, BorderLayout.NORTH);

            // Tabla de visualización
            tableModel = new DefaultTableModel(new Object[]{"Índice", "Valor", "Colisiones"}, 0);
            table = new JTable(tableModel);
            add(new JScrollPane(table), BorderLayout.CENTER);

            actualizarTabla();

            setSize(600, 400);
            setLocationRelativeTo(null);
            setVisible(true);
        }

        private void insertar() {
            String clave = txtInsert.getText().trim();
            if(clave.isEmpty()) return;
            if(nextInsertIndex < tamano) {
                estructura[nextInsertIndex] = clave;
                nextInsertIndex++;
                JOptionPane.showMessageDialog(this, "Clave insertada en el índice " + (nextInsertIndex - 1));
            } else {
                JOptionPane.showMessageDialog(this, "Estructura llena.");
            }
            txtInsert.setText("");
            actualizarTabla();
        }

        private void actualizar() {
            try {
                int index = Integer.parseInt(txtUpdateIndex.getText().trim());
                String valor = txtUpdateValue.getText().trim();
                if(index >= 0 && index < tamano) {
                    estructura[index] = valor;
                    JOptionPane.showMessageDialog(this, "Índice " + index + " actualizado.");
                }
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Índice inválido.");
            }
            txtUpdateIndex.setText("");
            txtUpdateValue.setText("");
            actualizarTabla();
        }

        private void eliminar() {
            try {
                int index = Integer.parseInt(txtDelete.getText().trim());
                if(index >= 0 && index < tamano) {
                    estructura[index] = null;
                    JOptionPane.showMessageDialog(this, "Índice " + index + " eliminado.");
                }
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Índice inválido.");
            }
            txtDelete.setText("");
            actualizarTabla();
        }

        private void buscar() {
            String clave = txtSearch.getText().trim();
            if(clave.isEmpty()) return;
            for (int i = 0; i < tamano; i++) {
                if(clave.equals(estructura[i])){
                    JOptionPane.showMessageDialog(this, "Clave encontrada en el índice " + i);
                    txtSearch.setText("");
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Clave no encontrada.");
            txtSearch.setText("");
        }

        private void reiniciar() {
            Arrays.fill(estructura, null);
            nextInsertIndex = 0;
            actualizarTabla();
        }

        private void actualizarTabla() {
            tableModel.setRowCount(0);
            for (int i = 0; i < tamano; i++) {
                String valor = (estructura[i] == null) ? "-" : estructura[i];
                tableModel.addRow(new Object[]{i, valor, "-"});
            }
        }
    }

    // ======================================
    // Ventana para Búsqueda Binaria
    // ======================================
    class BusquedaBinariaWindow extends JFrame {
        private ArrayList<String> lista;
        private DefaultTableModel tableModel;
        private JTable table;
        private JTextField txtInsert, txtUpdateIndex, txtUpdateValue, txtDelete, txtSearch;
        private int tamano;

        public BusquedaBinariaWindow(int tamano) {
            this.tamano = tamano;
            lista = new ArrayList<>();
            initComponents();
        }

        private void initComponents() {
            setTitle("Búsqueda Binaria");
            setLayout(new BorderLayout());

            JPanel panelControls = new JPanel(new GridLayout(0, 2, 5, 5));
            panelControls.add(new JLabel("Insertar clave (numérica):"));
            txtInsert = new JTextField();
            panelControls.add(txtInsert);
            JButton btnInsert = new JButton("Insertar");
            btnInsert.addActionListener(e -> insertar());
            panelControls.add(btnInsert);
            panelControls.add(new JLabel(""));

            panelControls.add(new JLabel("Actualizar índice:"));
            txtUpdateIndex = new JTextField();
            panelControls.add(txtUpdateIndex);
            panelControls.add(new JLabel("Nuevo valor:"));
            txtUpdateValue = new JTextField();
            panelControls.add(txtUpdateValue);
            JButton btnUpdate = new JButton("Actualizar");
            btnUpdate.addActionListener(e -> actualizar());
            panelControls.add(btnUpdate);
            panelControls.add(new JLabel(""));

            panelControls.add(new JLabel("Eliminar índice:"));
            txtDelete = new JTextField();
            panelControls.add(txtDelete);
            JButton btnDelete = new JButton("Eliminar");
            btnDelete.addActionListener(e -> eliminar());
            panelControls.add(btnDelete);
            panelControls.add(new JLabel(""));

            panelControls.add(new JLabel("Buscar clave:"));
            txtSearch = new JTextField();
            panelControls.add(txtSearch);
            JButton btnSearch = new JButton("Buscar");
            btnSearch.addActionListener(e -> buscar());
            panelControls.add(btnSearch);
            panelControls.add(new JLabel(""));

            JButton btnReset = new JButton("Reiniciar");
            btnReset.addActionListener(e -> reiniciar());
            panelControls.add(btnReset);
            panelControls.add(new JLabel(""));

            add(panelControls, BorderLayout.NORTH);

            tableModel = new DefaultTableModel(new Object[]{"Índice", "Valor", "Colisiones"}, 0);
            table = new JTable(tableModel);
            add(new JScrollPane(table), BorderLayout.CENTER);

            actualizarTabla();

            setSize(600, 400);
            setLocationRelativeTo(null);
            setVisible(true);
        }

        private void insertar() {
            String clave = txtInsert.getText().trim();
            if(clave.isEmpty()) return;
            if(lista.size() < tamano) {
                lista.add(clave);
                // Ordenamos la lista para aplicar búsqueda binaria
                Collections.sort(lista, Comparator.comparingInt(Integer::parseInt));
                JOptionPane.showMessageDialog(this, "Clave insertada.");
            } else {
                JOptionPane.showMessageDialog(this, "Estructura llena.");
            }
            txtInsert.setText("");
            actualizarTabla();
        }

        private void actualizar() {
            try {
                int index = Integer.parseInt(txtUpdateIndex.getText().trim());
                String valor = txtUpdateValue.getText().trim();
                if(index >= 0 && index < lista.size()) {
                    lista.set(index, valor);
                    Collections.sort(lista, Comparator.comparingInt(Integer::parseInt));
                    JOptionPane.showMessageDialog(this, "Índice " + index + " actualizado.");
                }
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Índice inválido.");
            }
            txtUpdateIndex.setText("");
            txtUpdateValue.setText("");
            actualizarTabla();
        }

        private void eliminar() {
            try {
                int index = Integer.parseInt(txtDelete.getText().trim());
                if(index >= 0 && index < lista.size()){
                    lista.remove(index);
                    JOptionPane.showMessageDialog(this, "Índice " + index + " eliminado.");
                }
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Índice inválido.");
            }
            txtDelete.setText("");
            actualizarTabla();
        }

        private void buscar() {
            String clave = txtSearch.getText().trim();
            if(clave.isEmpty()) return;
            int low = 0;
            int high = lista.size()-1;
            int key = Integer.parseInt(clave);
            while(low <= high) {
                int mid = (low + high) / 2;
                int midVal = Integer.parseInt(lista.get(mid));
                if(midVal < key)
                    low = mid + 1;
                else if(midVal > key)
                    high = mid - 1;
                else {
                    JOptionPane.showMessageDialog(this, "Clave encontrada en el índice " + mid);
                    txtSearch.setText("");
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Clave no encontrada.");
            txtSearch.setText("");
        }

        private void reiniciar() {
            lista.clear();
            actualizarTabla();
        }

        private void actualizarTabla() {
            tableModel.setRowCount(0);
            for (int i = 0; i < lista.size(); i++) {
                tableModel.addRow(new Object[]{i, lista.get(i), "-"});
            }
        }
    }

    // ======================================
    // Clases abstractas y ventanas para funciones hash
    // ======================================
    abstract class HashWindow extends JFrame {
        protected ArrayList<String>[] tabla;
        protected int tamanoTabla;
        protected DefaultTableModel tableModel;
        protected JTable table;
        protected JTextField txtInsert, txtUpdateOld, txtUpdateNew, txtDelete;

        public HashWindow(int tamanoTabla) {
            this.tamanoTabla = tamanoTabla;
            tabla = new ArrayList[tamanoTabla];
            for (int i = 0; i < tamanoTabla; i++) {
                tabla[i] = new ArrayList<>();
            }
            initComponents();
        }

        protected void initComponents() {
            setLayout(new BorderLayout());
            JPanel panelControls = new JPanel(new GridLayout(0, 2, 5, 5));

            panelControls.add(new JLabel("Insertar clave:"));
            txtInsert = new JTextField();
            panelControls.add(txtInsert);
            JButton btnInsert = new JButton("Insertar");
            btnInsert.addActionListener(e -> insertarClave());
            panelControls.add(btnInsert);
            panelControls.add(new JLabel(""));

            panelControls.add(new JLabel("Actualizar clave (vieja):"));
            txtUpdateOld = new JTextField();
            panelControls.add(txtUpdateOld);
            panelControls.add(new JLabel("Actualizar clave (nueva):"));
            txtUpdateNew = new JTextField();
            panelControls.add(txtUpdateNew);
            JButton btnUpdate = new JButton("Actualizar");
            btnUpdate.addActionListener(e -> actualizarClave());
            panelControls.add(btnUpdate);
            panelControls.add(new JLabel(""));

            panelControls.add(new JLabel("Eliminar clave:"));
            txtDelete = new JTextField();
            panelControls.add(txtDelete);
            JButton btnDelete = new JButton("Eliminar");
            btnDelete.addActionListener(e -> eliminarClave());
            panelControls.add(btnDelete);
            panelControls.add(new JLabel(""));

            JButton btnReset = new JButton("Reiniciar");
            btnReset.addActionListener(e -> reiniciarTabla());
            panelControls.add(btnReset);
            panelControls.add(new JLabel(""));

            add(panelControls, BorderLayout.NORTH);

            tableModel = new DefaultTableModel(new Object[]{"Índice", "Clave(s)", "Colisiones"}, 0);
            table = new JTable(tableModel);
            add(new JScrollPane(table), BorderLayout.CENTER);

            setSize(600, 400);
            setLocationRelativeTo(null);
            setVisible(true);
            actualizarTabla();
        }

        protected abstract int calcularHash(String clave);

        protected void insertarClave() {
            String clave = txtInsert.getText().trim();
            if(clave.isEmpty()) return;
            int indice = calcularHash(clave);
            if(indice == -1) return;
            tabla[indice].add(clave);
            JOptionPane.showMessageDialog(this, "Clave '" + clave + "' insertada en el índice " + indice + ".");
            txtInsert.setText("");
            actualizarTabla();
        }

        protected void actualizarClave() {
            String claveAntigua = txtUpdateOld.getText().trim();
            String claveNueva = txtUpdateNew.getText().trim();
            if(claveAntigua.isEmpty() || claveNueva.isEmpty()){
                JOptionPane.showMessageDialog(this, "Ingrese ambas claves para actualizar.");
                return;
            }
            boolean encontrada = false;
            for (int i = 0; i < tamanoTabla; i++) {
                if(tabla[i].contains(claveAntigua)){
                    tabla[i].remove(claveAntigua);
                    int nuevoIndice = calcularHash(claveNueva);
                    if(nuevoIndice == -1) return;
                    tabla[nuevoIndice].add(claveNueva);
                    JOptionPane.showMessageDialog(this, "Clave actualizada a '" + claveNueva + "'.");
                    encontrada = true;
                    break;
                }
            }
            if(!encontrada)
                JOptionPane.showMessageDialog(this, "Clave '" + claveAntigua + "' no encontrada.");
            txtUpdateOld.setText("");
            txtUpdateNew.setText("");
            actualizarTabla();
        }

        protected void eliminarClave() {
            String clave = txtDelete.getText().trim();
            if(clave.isEmpty()) return;
            boolean encontrada = false;
            for (int i = 0; i < tamanoTabla; i++) {
                if(tabla[i].contains(clave)){
                    tabla[i].remove(clave);
                    JOptionPane.showMessageDialog(this, "Clave '" + clave + "' eliminada del índice " + i + ".");
                    encontrada = true;
                    break;
                }
            }
            if(!encontrada)
                JOptionPane.showMessageDialog(this, "Clave '" + clave + "' no encontrada.");
            txtDelete.setText("");
            actualizarTabla();
        }

        protected void reiniciarTabla() {
            for (int i = 0; i < tamanoTabla; i++) {
                tabla[i].clear();
            }
            actualizarTabla();
        }

        protected void actualizarTabla() {
            tableModel.setRowCount(0);
            int digitos = String.valueOf(tamanoTabla - 1).length();
            String formato = "%0" + digitos + "d";
            for (int i = 0; i < tamanoTabla; i++) {
                String indiceFormateado = String.format(formato, i);
                String claves = tabla[i].isEmpty() ? "-" : String.join(", ", tabla[i]);
                String colisiones = (tabla[i].size() > 1) ? ("Sí (" + (tabla[i].size() - 1) + ")") : "No";
                tableModel.addRow(new Object[]{indiceFormateado, claves, colisiones});
            }
        }
    }

    // ======================================
    // Ventana para Función Hash Mod
    // ======================================
    class HashModWindow extends HashWindow {
        public HashModWindow(int tamanoTabla) {
            super(tamanoTabla);
            setTitle("Función Hash Mod");
        }

        protected int calcularHash(String clave) {
            try {
                int num = Integer.parseInt(clave);
                return num % tamanoTabla;
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Clave no numérica.");
                return -1;
            }
        }
    }

    // ======================================
    // Ventana para Función Hash Cuadrado
    // ======================================
    class HashCuadradoWindow extends HashWindow {
        public HashCuadradoWindow(int tamanoTabla) {
            super(tamanoTabla);
            setTitle("Función Hash Cuadrado");
        }

        protected int calcularHash(String clave) {
            try {
                int num = Integer.parseInt(clave);
                long cuadrado = (long) num * num;
                return (int)(cuadrado % tamanoTabla);
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Clave no numérica.");
                return -1;
            }
        }
    }

    // ======================================
    // Ventana para Función Hash Plegamiento
    // ======================================
    class HashPlegamientoWindow extends HashWindow {
        public HashPlegamientoWindow(int tamanoTabla) {
            super(tamanoTabla);
            setTitle("Función Hash Plegamiento");
        }

        protected int calcularHash(String clave) {
            int suma = 0;
            int tamanoPieza = clave.length() / 2;
            if(tamanoPieza == 0) {
                try {
                    suma = Integer.parseInt(clave);
                } catch(NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Error al convertir la clave.");
                    return -1;
                }
            } else {
                String parte1 = clave.substring(0, tamanoPieza);
                String parte2 = clave.substring(tamanoPieza);
                try {
                    int num1 = Integer.parseInt(parte1);
                    int num2 = Integer.parseInt(parte2);
                    suma = num1 + num2;
                } catch(NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Error al convertir partes de la clave.");
                    return -1;
                }
            }
            return suma % tamanoTabla;
        }
    }

    // ======================================
    // Ventana para Función Hash Truncamiento
    // ======================================
    class HashTruncamientoWindow extends HashWindow {
        public HashTruncamientoWindow(int tamanoTabla) {
            super(tamanoTabla);
            setTitle("Función Hash Truncamiento");
        }

        protected int calcularHash(String clave) {
            // Se truncan los dígitos de la clave según la cantidad de dígitos del tamaño de la estructura
            int digitos = String.valueOf(tamanoTabla - 1).length();
            String truncada = (clave.length() > digitos) ? clave.substring(clave.length() - digitos) : clave;
            try {
                int num = Integer.parseInt(truncada);
                return num % tamanoTabla;
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Clave no numérica.");
                return -1;
            }
        }
    }

    // ======================================
    // Método principal
    // ======================================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu());
    }
}
