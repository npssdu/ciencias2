package busquedasInternas2.controlador;

import busquedasInternas2.modelo.BusquedaLinealModel;
import busquedasInternas2.modelo.BusquedaBinariaModel;
import busquedasInternas2.modelo.HashModModel;
import busquedasInternas2.modelo.HashCuadradoModel;
import busquedasInternas2.modelo.HashPlegamientoModel;
import busquedasInternas2.modelo.HashTruncamientoModel;
import busquedasInternas2.vista.MainMenuView;
import busquedasInternas2.vista.BusquedaLinealView;
import busquedasInternas2.vista.BusquedaBinariaView;
import busquedasInternas2.vista.HashModView;
import busquedasInternas2.vista.HashCuadradoView;
import busquedasInternas2.vista.HashPlegamientoView;
import busquedasInternas2.vista.HashTruncamientoView;

import javax.swing.*;

public class MainMenuController {
    private MainMenuView view;

    public MainMenuController(MainMenuView view) {
        this.view = view;
        initController();
    }

    private void initController() {
        view.getBtnIniciar().addActionListener(e -> iniciar());
    }

    private void iniciar() {
        int tamano = view.getTamano();
        if (tamano <= 0) {
            JOptionPane.showMessageDialog(view, "Ingrese un tamaño válido.");
            return;
        }
        String metodo = view.getMetodoSeleccionado();
        switch (metodo) {
            case "Búsqueda Lineal":
                BusquedaLinealModel modeloBL = new BusquedaLinealModel(tamano);
                new BusquedaLinealController(modeloBL, new BusquedaLinealView());
                break;
            case "Búsqueda Binaria":
                BusquedaBinariaModel modeloBB = new BusquedaBinariaModel(tamano);
                new BusquedaBinariaController(modeloBB, new BusquedaBinariaView());
                break;
            case "Hash Mod":
                HashModModel modeloHM = new HashModModel(tamano);
                new HashController(modeloHM, new HashModView());
                break;
            case "Hash Cuadrado":
                HashCuadradoModel modeloHC = new HashCuadradoModel(tamano);
                new HashController(modeloHC, new HashCuadradoView());
                break;
            case "Hash Plegamiento":
                HashPlegamientoModel modeloHP = new HashPlegamientoModel(tamano);
                new HashController(modeloHP, new HashPlegamientoView());
                break;
            case "Hash Truncamiento":
                HashTruncamientoModel modeloHT = new HashTruncamientoModel(tamano);
                new HashController(modeloHT, new HashTruncamientoView());
                break;
        }
    }
}
