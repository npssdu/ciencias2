package busquedasInternas2.modelo;

public class HashTruncamientoModel extends HashModel { // Cambiar de nuevo función truncamiento

    public HashTruncamientoModel(int tamanoTabla) {
        super(tamanoTabla);
    }

    @Override
    protected int calcularHash(String clave) {
        int intervalo = String.valueOf(tamanoTabla).length() - 1;
        StringBuilder truncada = new StringBuilder();

        for (int i = 0; i < clave.length(); i += intervalo) {
            truncada.append(clave.charAt(i));
        }
 
        try {
            int num = Integer.parseInt(truncada.toString());
            return (num + 1) % tamanoTabla;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
