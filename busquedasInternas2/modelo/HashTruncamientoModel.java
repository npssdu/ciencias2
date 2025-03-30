package busquedasInternas2.modelo;

public class HashTruncamientoModel extends HashModel {

    public HashTruncamientoModel(int tamanoTabla) {
        super(tamanoTabla);
    }

    @Override
    protected int calcularHash(String clave) {
        int digitos = String.valueOf(tamanoTabla - 1).length();
        String truncada = (clave.length() > digitos) ? clave.substring(clave.length() - digitos) : clave;
        try {
            int num = Integer.parseInt(truncada);
            return num % tamanoTabla;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
