package modelo;

public class HashCuadradoModel extends HashModel {

    public HashCuadradoModel(int tamanoTabla) {
        super(tamanoTabla);
    }

    @Override
    protected int calcularHash(String clave) {
        try {
            // Convertir la clave a número
            long num = Long.parseLong(clave);
            // Elevar al cuadrado
            long cuadrado = num * num;
            // Convertir el cuadrado a String para trabajar con los dígitos
            String sCuadrado = String.valueOf(cuadrado);
            int len = sCuadrado.length();
            String extraido;

            if (len % 2 == 0) {
                // Si la cantidad de dígitos es par, tomar los dos dígitos centrales
                int inicio = (len / 2) - 1;
                extraido = sCuadrado.substring(inicio, inicio + 2);
            } else {
                // Si la cantidad de dígitos es impar, tomar el dígito central y el inmediato a la izquierda
                int centro = len / 2;
                if (centro - 1 >= 0) {
                    extraido = sCuadrado.substring(centro - 1, centro + 1);
                } else {
                    // En caso de que la longitud sea 1, se toma la única cifra
                    extraido = sCuadrado;
                }
            }

            // Convertir la cadena extraída a número, sumarle 1 y aplicar módulo
            int valorExtraido = Integer.parseInt(extraido);
            // valorExtraido = valorExtraido + 1;
            return valorExtraido % tamanoTabla;
        } catch (NumberFormatException e) {
            // En caso de error (por ejemplo, clave no numérica), retornar -1
            return -1;
        }
    }
}
