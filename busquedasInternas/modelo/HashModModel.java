package busquedasInternas2.modelo;

public class HashModModel extends HashModel {

    public HashModModel(int tamanoTabla) {
        super(tamanoTabla);
    }

    @Override
    protected int calcularHash(String clave) {
        try {
            int num = Integer.parseInt(clave);
            return num % tamanoTabla;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
