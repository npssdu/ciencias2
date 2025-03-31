package busquedasInternas2;

import busquedasInternas2.controlador.MainMenuController;
import busquedasInternas2.vista.MainMenuView;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenuView view = new MainMenuView();
            new MainMenuController(view);
            view.setVisible(true);
        });
    }
}
