package vista;

import javax.swing.*;
import java.awt.*;

public class BusquedaRangosElementalesView extends JFrame {
    private JTextField txtDatos, txtRango;
    private JButton btnBuscarRango;
    private JTextArea areaResultados;

    public BusquedaRangosElementalesView() {
        setTitle("Búsqueda por Rangos - Métodos Elementales");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel panelInput = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelInput.add(new JLabel("Ingrese datos (separados por comas):"));
        txtDatos = new JTextField(20);
        panelInput.add(txtDatos);
        panelInput.add(new JLabel("Rango (min, max):"));
        txtRango = new JTextField(10);
        panelInput.add(txtRango);
        btnBuscarRango = new JButton("Buscar en rango");
        panelInput.add(btnBuscarRango);
        mainPanel.add(panelInput, BorderLayout.NORTH);

        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        mainPanel.add(new JScrollPane(areaResultados), BorderLayout.CENTER);

        add(mainPanel);
    }

    public String getDatos() {
        return txtDatos.getText().trim();
    }
    public String getRango() {
        return txtRango.getText().trim();
    }
    public JButton getBtnBuscarRango() {
        return btnBuscarRango;
    }
    public void setAreaResultados(String texto) {
        areaResultados.setText(texto);
    }
}
