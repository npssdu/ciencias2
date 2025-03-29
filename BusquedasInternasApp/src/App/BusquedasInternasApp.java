/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package App;

/**
 *
 * @author khasil
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class BusquedasInternasApp extends JFrame {
    private Integer[] arreglo;
    private int tamañoActual = 0;
    private int tamañoMaximo;
    private boolean ordenado = false;
    private static final int REPETICIONES_TIEMPO = 1000;

    // Componentes de la interfaz
    private JTextField tamañoField, claveField, nuevoValorField;
    private JTable tablaArreglo;
    private DefaultTableModel tableModel;
    private JTextArea logArea;

    public BusquedasInternasApp() {
        setTitle("Búsquedas Internas en Ciencias de la Computación");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        // Panel superior para controles
        JPanel controlPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        controlPanel.add(new JLabel("Tamaño Máximo:"));
        controlPanel.add(new JLabel("Clave:"));
        controlPanel.add(new JLabel("Nuevo Valor:"));
        controlPanel.add(new JLabel(""));
        
        tamañoField = new JTextField();
        claveField = new JTextField();
        nuevoValorField = new JTextField();
        JButton crearArregloButton = new JButton("Crear Arreglo");
        
        controlPanel.add(tamañoField);
        controlPanel.add(claveField);
        controlPanel.add(nuevoValorField);
        controlPanel.add(crearArregloButton);

        // Panel izquierdo para botones de operaciones (vertical)
        JPanel operacionesPanel = new JPanel();
        operacionesPanel.setLayout(new BoxLayout(operacionesPanel, BoxLayout.Y_AXIS));
        operacionesPanel.setBorder(BorderFactory.createTitledBorder("Operaciones"));
        
        JButton insertarButton = new JButton("Insertar");
        JButton eliminarButton = new JButton("Eliminar");
        JButton modificarButton = new JButton("Modificar");
        JButton mostrarButton = new JButton("Mostrar");
        JButton hashModButton = new JButton("Función Hash Mod");
        
        for (JButton boton : new JButton[]{insertarButton, eliminarButton, modificarButton, mostrarButton, hashModButton}) {
            boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, boton.getMinimumSize().height));
            operacionesPanel.add(boton);
            operacionesPanel.add(Box.createVerticalStrut(5));
        }

        // Panel derecho para botones de búsquedas (vertical)
        JPanel busquedasPanel = new JPanel();
        busquedasPanel.setLayout(new BoxLayout(busquedasPanel, BoxLayout.Y_AXIS));
        busquedasPanel.setBorder(BorderFactory.createTitledBorder("Búsquedas"));
        
        JButton secuencialButton = new JButton("Búsqueda Secuencial");
        JButton binariaButton = new JButton("Búsqueda Binaria");
        JButton modButton = new JButton("Búsqueda Mod");
        
        for (JButton boton : new JButton[]{secuencialButton, binariaButton, modButton}) {
            boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, boton.getMinimumSize().height));
            busquedasPanel.add(boton);
            busquedasPanel.add(Box.createVerticalStrut(5));
        }

        // Tabla para mostrar el arreglo
        tableModel = new DefaultTableModel(new Object[]{"Índice", "Valor"}, 0);
        tablaArreglo = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(tablaArreglo);

        // Área de logs
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logArea);

        // Panel central para tabla
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Panel principal para botones
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        buttonPanel.add(operacionesPanel);
        buttonPanel.add(busquedasPanel);

        // Agregar componentes al frame
        add(controlPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(logScrollPane, BorderLayout.SOUTH);

        // Configurar eventos
        crearArregloButton.addActionListener(e -> crearArreglo());
        insertarButton.addActionListener(e -> insertarElemento());
        eliminarButton.addActionListener(e -> eliminarElemento());
        modificarButton.addActionListener(e -> modificarElemento());
        mostrarButton.addActionListener(e -> mostrarArreglo());
        secuencialButton.addActionListener(e -> busquedaSecuencial());
        binariaButton.addActionListener(e -> busquedaBinaria());
        modButton.addActionListener(e -> busquedaMod());
        hashModButton.addActionListener(e -> funcionHashMod());
    }

    private void crearArreglo() {
        try {
            tamañoMaximo = Integer.parseInt(tamañoField.getText());
            if (tamañoMaximo <= 0) {
                log("El tamaño debe ser mayor que cero");
                return;
            }
            arreglo = new Integer[tamañoMaximo];
            tamañoActual = 0;
            ordenado = false;
            log("Arreglo creado con tamaño máximo: " + tamañoMaximo);
            mostrarArreglo();
        } catch (NumberFormatException ex) {
            log("Por favor ingrese un número válido para el tamaño");
        }
    }

    private void insertarElemento() {
        if (arreglo == null) {
            log("Primero debe crear un arreglo");
            return;
        }

        try {
            int clave = Integer.parseInt(claveField.getText());
            
            if (tamañoActual >= tamañoMaximo) {
                log("El arreglo está lleno. No se puede insertar más elementos.");
                return;
            }
            
            for (int i = 0; i < tamañoActual; i++) {
                if (arreglo[i] != null && arreglo[i] == clave) {
                    log("Error: La clave " + clave + " ya existe en el arreglo");
                    return;
                }
            }
            
            arreglo[tamañoActual] = clave;
            tamañoActual++;
            log("Elemento " + clave + " insertado correctamente");
            
            if (ordenado) {
                Arrays.sort(arreglo, 0, tamañoActual);
                log("Arreglo reordenado para mantener el orden");
            }
            
            mostrarArreglo();
        } catch (NumberFormatException ex) {
            log("Por favor ingrese un número válido para la clave");
        }
    }

    private void eliminarElemento() {
        if (arreglo == null) {
            log("Primero debe crear un arreglo");
            return;
        }

        try {
            int clave = Integer.parseInt(claveField.getText());
            int posicion = -1;
            
            for (int i = 0; i < tamañoActual; i++) {
                if (arreglo[i] != null && arreglo[i] == clave) {
                    posicion = i;
                    break;
                }
            }
            
            if (posicion == -1) {
                log("Elemento " + clave + " no encontrado en el arreglo");
                return;
            }
            
            for (int i = posicion; i < tamañoActual - 1; i++) {
                arreglo[i] = arreglo[i + 1];
            }
            arreglo[tamañoActual - 1] = null;
            tamañoActual--;
            log("Elemento " + clave + " eliminado correctamente");
            
            mostrarArreglo();
        } catch (NumberFormatException ex) {
            log("Por favor ingrese un número válido para la clave");
        }
    }

    private void modificarElemento() {
        if (arreglo == null) {
            log("Primero debe crear un arreglo");
            return;
        }

        try {
            int clave = Integer.parseInt(claveField.getText());
            int nuevoValor = Integer.parseInt(nuevoValorField.getText());
            boolean encontrado = false;
            
            for (int i = 0; i < tamañoActual; i++) {
                if (arreglo[i] != null && arreglo[i] == nuevoValor && arreglo[i] != clave) {
                    log("Error: El nuevo valor " + nuevoValor + " ya existe en el arreglo");
                    return;
                }
            }
            
            for (int i = 0; i < tamañoActual; i++) {
                if (arreglo[i] != null && arreglo[i] == clave) {
                    arreglo[i] = nuevoValor;
                    encontrado = true;
                    break;
                }
            }
            
            if (!encontrado) {
                log("Elemento " + clave + " no encontrado en el arreglo");
                return;
            }
            
            log("Elemento " + clave + " modificado a " + nuevoValor);
            
            if (ordenado) {
                Arrays.sort(arreglo, 0, tamañoActual);
                log("Arreglo reordenado para mantener el orden");
            }
            
            mostrarArreglo();
        } catch (NumberFormatException ex) {
            log("Por favor ingrese números válidos para la clave y el nuevo valor");
        }
    }

    private void mostrarArreglo() {
        tableModel.setRowCount(0);
        
        if (arreglo == null) {
            log("No hay arreglo creado para mostrar");
            return;
        }
        
        for (int i = 0; i < tamañoActual; i++) {
            tableModel.addRow(new Object[]{i, arreglo[i]});
        }
        
        log("Arreglo mostrado. Tamaño actual: " + tamañoActual + "/" + tamañoMaximo);
    }

    private void busquedaSecuencial() {
        if (arreglo == null) {
            log("Primero debe crear un arreglo");
            return;
        }

        try {
            int clave = Integer.parseInt(claveField.getText());
            boolean encontrado = false;
            int posicion = -1;
            int comparaciones = 0;
            
            long startTime = System.nanoTime();
            for (int r = 0; r < REPETICIONES_TIEMPO; r++) {
                encontrado = false;
                posicion = -1;
                comparaciones = 0;
                
                for (int i = 0; i < tamañoActual; i++) {
                    comparaciones++;
                    if (arreglo[i] != null && arreglo[i] == clave) {
                        encontrado = true;
                        posicion = i;
                        break;
                    }
                }
            }
            long endTime = System.nanoTime();
            double duration = (endTime - startTime) / (REPETICIONES_TIEMPO * 1000.0);
            
            if (encontrado) {
                log("Búsqueda Secuencial:\n" +
                    "Elemento " + clave + " encontrado en posición " + posicion + 
                    "\nComparaciones: " + comparaciones + 
                    String.format("\nTiempo promedio: %.3f µs", duration));
            } else {
                log("Búsqueda Secuencial:\n" +
                    "Elemento " + clave + " no encontrado" +
                    "\nComparaciones: " + comparaciones + 
                    String.format("\nTiempo promedio: %.3f µs", duration));
            }
        } catch (NumberFormatException ex) {
            log("Por favor ingrese un número válido para la clave");
        }
    }

    private void busquedaBinaria() {
        if (arreglo == null) {
            log("Primero debe crear un arreglo");
            return;
        }

        try {
            int clave = Integer.parseInt(claveField.getText());
            
            if (!ordenado) {
                long sortStartTime = System.nanoTime();
                Arrays.sort(arreglo, 0, tamañoActual);
                long sortEndTime = System.nanoTime();
                ordenado = true;
                log("Arreglo ordenado para búsqueda binaria en " + 
                    (sortEndTime - sortStartTime)/1000 + " µs");
                mostrarArreglo();
            }
            
            int inicio = 0;
            int fin = tamañoActual - 1;
            int comparaciones = 0;
            boolean encontrado = false;
            int posicion = -1;
            
            long startTime = System.nanoTime();
            for (int r = 0; r < REPETICIONES_TIEMPO; r++) {
                inicio = 0;
                fin = tamañoActual - 1;
                encontrado = false;
                posicion = -1;
                comparaciones = 0;
                
                while (inicio <= fin) {
                    comparaciones++;
                    int medio = (inicio + fin) / 2;
                    
                    if (arreglo[medio] == clave) {
                        encontrado = true;
                        posicion = medio;
                        break;
                    } else if (arreglo[medio] < clave) {
                        inicio = medio + 1;
                    } else {
                        fin = medio - 1;
                    }
                }
            }
            long endTime = System.nanoTime();
            double duration = (endTime - startTime) / (REPETICIONES_TIEMPO * 1000.0);
            
            if (encontrado) {
                log("Búsqueda Binaria:\n" +
                    "Elemento " + clave + " encontrado en posición " + posicion + 
                    "\nComparaciones: " + comparaciones + 
                    String.format("\nTiempo promedio: %.3f µs", duration));
            } else {
                log("Búsqueda Binaria:\n" +
                    "Elemento " + clave + " no encontrado" +
                    "\nComparaciones: " + comparaciones + 
                    String.format("\nTiempo promedio: %.3f µs", duration));
            }
        } catch (NumberFormatException ex) {
            log("Por favor ingrese un número válido para la clave");
        }
    }

    private void busquedaMod() {
        if (arreglo == null) {
            log("Primero debe crear un arreglo");
            return;
        }

        try {
            String[] partes = claveField.getText().split(" ");
            if (partes.length != 2) {
                log("Formato incorrecto. Ingrese: [divisor] [criterio]");
                return;
            }
            
            int divisor = Integer.parseInt(partes[0]);
            int criterio = Integer.parseInt(partes[1]);
            
            if (divisor == 0) {
                log("El divisor no puede ser cero");
                return;
            }

            int coincidencias = 0;
            StringBuilder resultados = new StringBuilder();
            
            long startTime = System.nanoTime();
            for (int r = 0; r < REPETICIONES_TIEMPO; r++) {
                coincidencias = 0;
                resultados = new StringBuilder();
                
                for (int i = 0; i < tamañoActual; i++) {
                    if (arreglo[i] != null && arreglo[i] % divisor == criterio) {
                        coincidencias++;
                        resultados.append("Posición ").append(i)
                                 .append(": ").append(arreglo[i])
                                 .append(" (")
                                 .append(arreglo[i]).append(" % ").append(divisor)
                                 .append(" = ").append(arreglo[i] % divisor)
                                 .append(")\n");
                    }
                }
            }
            long endTime = System.nanoTime();
            double duration = (endTime - startTime) / (REPETICIONES_TIEMPO * 1000.0);
            
            if (coincidencias > 0) {
                log("Búsqueda Mod (≡" + criterio + " mod " + divisor + "):\n" +
                    coincidencias + " elementos encontrados\n" +
                    resultados.toString() + 
                    String.format("Tiempo promedio: %.3f µs", duration));
            } else {
                log("Búsqueda Mod (≡" + criterio + " mod " + divisor + "):\n" +
                    "No se encontraron elementos\n" +
                    String.format("Tiempo promedio: %.3f µs", duration));
            }
        } catch (NumberFormatException ex) {
            log("Ingrese números válidos: [divisor] [criterio]");
        } catch (Exception ex) {
            log("Error en búsqueda Mod: " + ex.getMessage());
        }
    }

    private void funcionHashMod() {
        if (arreglo == null) {
            log("Primero debe crear un arreglo");
            return;
        }

        try {
            int m = tamañoMaximo;
            Arrays.fill(arreglo, null);
            tamañoActual = 0;
            
            String input = JOptionPane.showInputDialog(this, 
                "Ingrese las claves a hashear separadas por comas:", 
                "Entrada para función hash", 
                JOptionPane.PLAIN_MESSAGE);
            
            if (input == null || input.trim().isEmpty()) {
                log("Operación hash cancelada");
                return;
            }
            
            String[] claves = input.split(",");
            int colisiones = 0;
            
            long startTime = System.nanoTime();
            
            for (String claveStr : claves) {
                try {
                    int k = Integer.parseInt(claveStr.trim());
                    int posicion = hashMod(k, m);
                    
                    if (arreglo[posicion] != null) {
                        colisiones++;
                        log("Colisión en posición " + posicion + 
                            " para clave " + k + 
                            " (valor existente: " + arreglo[posicion] + ")");
                    }
                    
                    arreglo[posicion] = k;
                    tamañoActual++;
                } catch (NumberFormatException e) {
                    log("Clave inválida ignorada: " + claveStr);
                }
            }
            
            long endTime = System.nanoTime();
            double duration = (endTime - startTime) / 1000.0;
            
            log("\nResultado de función hash mod " + m + ":");
            log("Total claves insertadas: " + claves.length);
            log("Colisiones detectadas: " + colisiones);
            log(String.format("Tiempo de ejecución: %.3f µs", duration));
            log("Factor de carga: " + (double)tamañoActual/tamañoMaximo);
            
            mostrarArreglo();
            
        } catch (Exception ex) {
            log("Error en función hash: " + ex.getMessage());
        }
    }

    private int hashMod(int k, int m) {
        return ((k % m) + m) % m;
    }

    private void log(String mensaje) {
        logArea.append(mensaje + "\n\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BusquedasInternasApp app = new BusquedasInternasApp();
            app.setVisible(true);
        });
    }
}