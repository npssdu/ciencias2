package busquedasInternas2.modelo;

public class HashPlegamientoModel extends HashModel {

    public HashPlegamientoModel(int tamanoTabla) {
        super(tamanoTabla);
    }

    @Override
    protected int calcularHash(String clave) {
        int suma = 0;
        int tamanoPieza = clave.length() / 2;
        if (tamanoPieza == 0) {
            try {
                suma = Integer.parseInt(clave);
            } catch (NumberFormatException e) {
                return -1;
            }
        } else {
            String parte1 = clave.substring(0, tamanoPieza);
            String parte2 = clave.substring(tamanoPieza);
            try {
                int num1 = Integer.parseInt(parte1);
                int num2 = Integer.parseInt(parte2);
                suma = num1 + num2;
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        return suma % tamanoTabla + 1;
    }
}
