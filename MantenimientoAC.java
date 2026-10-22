import Enumeraciones.Zona;

public class MantenimientoAC extends OrdenServicio {

    private int capacidadBTU;

    public MantenimientoAC(String codigo, String cliente, String direccion, double horas, Zona zona, int capacidadBTU) {
        super(codigo, cliente, direccion, horas, zona);
        this.capacidadBTU = capacidadBTU;
    }

    public int getCapacidadBTU() {
        return capacidadBTU;
    }

    @Override
    public double calcularCosto() {
        double costobase = 0;
        double costoHoras = getHorasEstimadas()*110;

        if (getZona()== Zona.METROPOLITANA){
            costobase = 100;
        }
        return costobase + costoHoras + 80;
    }

    @Override
    public int calcularDiasAtencion() {
        return 2;
    }

    @Override
    public int getMaxVisitas() {
        return 1;
    }
}