package vista;

import javax.swing.*;
import java.awt.*;

public class HashTruncamientoView extends HashView {
    private JTextField txtPosiciones;

    public HashTruncamientoView() {
        super("Función Hash Truncamiento");

        // Panel para ingresar las posiciones (centrado)
        JPanel panelExtra = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelExtra.add(new JLabel("Posiciones (separadas por comas):"));
        txtPosiciones = new JTextField(15);
        panelExtra.add(txtPosiciones);
        // Se agrega el panel extra al sur
        add(panelExtra, BorderLayout.SOUTH);
    }

    public String getTxtPosiciones() {
        return txtPosiciones.getText().trim();
    }
}
