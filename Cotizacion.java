public class Cotizacion {

    private double costo;
    private int diasEstimados;

    public Cotizacion(double costo, int diasEstimados) {
        this.costo = costo;
        this.diasEstimados = diasEstimados;
    }

    public double getCosto() {
        return costo;
    }

    public int getDiasEstimados() {
        return diasEstimados;
    }
}