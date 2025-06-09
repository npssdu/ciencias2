package modelo;

import java.util.*;

public class ExpansionTotalModel {
    private int N, R;
    private double DO, DOredu;
    private List<List<Integer>> buckets;
    private List<List<Integer>> colisiones;
    private List<Integer> claves;

    public ExpansionTotalModel() {
        claves = new ArrayList<>();
    }

    public void inicializar(int N, int R, double DO, double DOredu) {
        this.N = N;
        this.R = R;
        this.DO = DO;
        this.DOredu = DOredu;
        buckets = new ArrayList<>();
        colisiones = new ArrayList<>();
        claves.clear();

        for (int i = 0; i < N; i++) {
            buckets.add(new ArrayList<>());
            colisiones.add(new ArrayList<>());
        }
    }

    public String insertar(int k) {
        StringBuilder log = new StringBuilder();
        claves.add(k);
        int h = k % N;
        log.append("k = ").append(k).append(" → h = k mod N = ").append(h).append("\n");

        if (buckets.get(h).size() < R) {
            buckets.get(h).add(k);
            log.append("Insertado en cubeta ").append(h).append("\n");
        } else {
            colisiones.get(h).add(k);
            log.append("Colisión → agregado a lista secundaria de cubeta ").append(h).append("\n");
        }

        double ocupacion = (claves.size() * 100.0) / (N * R);
        log.append(String.format("Ocupación actual: %.2f%%\n", ocupacion));
        if (ocupacion > DO) {
            log.append("Ocupación superó DO → expansión total activada\n");
            expandir();
            log.append("Nueva cantidad de cubetas: ").append(N).append("\n");
        }
        return log.toString();
    }

    private void expandir() {
        N *= 2;
        List<Integer> old = new ArrayList<>(claves);
        inicializar(N, R, DO, DOredu);
        for (int k : old) insertar(k);
    }

    public List<List<Integer>> getBuckets() {
        return buckets;
    }

    public List<List<Integer>> getColisiones() {
        return colisiones;
    }

    public void eliminar(int k) {
        for (List<Integer> b : buckets) b.remove((Integer) k);
        for (List<Integer> c : colisiones) c.remove((Integer) k);
        claves.remove((Integer) k);
    }
}
