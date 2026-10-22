
public class Cuadrilla {

    private int codigo;
    private double capacidadHoras;
    private boolean certificadaRefrigeracion;

    public Cuadrilla(int codigo, double capacidadHoras,
            boolean certificadaRefrigeracion) {

        if (codigo <= 0 || !Double.isFinite(capacidadHoras)
                || capacidadHoras <= 0) {
            throw new IllegalArgumentException("Datos de cuadrilla invalidos.");
        }

        this.codigo = codigo;
        this.capacidadHoras = capacidadHoras;
        this.certificadaRefrigeracion = certificadaRefrigeracion;
    }

    public int getCodigo() {
        return codigo;
    }

    public double getCapacidadHoras() {
        return capacidadHoras;
    }

    public boolean isCertificadaRefrigeracion() {
        return certificadaRefrigeracion;
    }
}
