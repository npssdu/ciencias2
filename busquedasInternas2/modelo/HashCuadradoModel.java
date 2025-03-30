package busquedasInternas2.modelo;

public class HashCuadradoModel extends HashModel {

    public HashCuadradoModel(int tamanoTabla) {
        super(tamanoTabla);
    }

    @Override
    protected int calcularHash(String clave) {
        try {
            int num = Integer.parseInt(clave);
            long cuadrado = (long) num * num;
            return (int) (cuadrado % tamanoTabla);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
