import Enumeraciones.Zona;


public class MantenimientoAC extends OrdenServicio {

    private int capacidadBTU;

    public MantenimientoAC(int codigo, String cliente,
            String direccion, double horas, Zona zona, int capacidadBTU) {

        super(codigo, cliente, direccion, horas, zona);

        if (zona != Zona.METROPOLITANA
                || capacidadBTU < 9000 || capacidadBTU > 60000) {
            throw new IllegalArgumentException(
                "Zona o capacidad BTU invalida.");
        }

        this.capacidadBTU = capacidadBTU;
    }

    @Override
    public double calcularCosto() {
        return 100 + getHorasEstimadas() * 110 + 80;
    }

    @Override
    public int calcularDiasAtencion() {
        return 2;
    }

    @Override
    public int getMaxVisitas() {
        return 1;
    }

    public int getCapacidadBTU() {
        return capacidadBTU;
    }
}
