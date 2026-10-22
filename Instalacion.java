import Enumeraciones.Zona;


public class Instalacion extends OrdenServicio {

    private int puntos;

    public Instalacion(int codigo, String cliente,
            String direccion, double horas, Zona zona, int puntos) {

        super(codigo, cliente, direccion, horas, zona);

        if (puntos <= 0) {
            throw new IllegalArgumentException(
                "Los puntos deben ser positivos.");
        }

        this.puntos = puntos;
    }

    private double calcularHorasPorPuntos() {
        return puntos * 0.75;
    }

    protected int calcularHorasFacturables() {
        double horasPuntos = calcularHorasPorPuntos();
        double horasMayores = Math.max(horasPuntos, getHorasEstimadas());

        if (horasMayores > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Demasiadas horas.");
        }

        return (int) Math.ceil(horasMayores);
    }

    @Override
    public double calcularCosto() {
        double costoBase;

        if (getZona() == Zona.METROPOLITANA) {
            costoBase = 100;
        } else {
            costoBase = 175;
        }

        return costoBase + calcularHorasFacturables() * 90;
    }

    @Override
    public int calcularDiasAtencion() {
        if (getZona() == Zona.METROPOLITANA) {
            return 3;
        } else {
            return 5;
        }
    }

    @Override
    public int getMaxVisitas() {
        return 3;
    }

    public int getPuntos() {
        return puntos;
    }
}
