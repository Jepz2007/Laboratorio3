import Enumeraciones.Zona;

public class InstalacionElectricaCertificada extends Instalacion {

    private double valorMateriales;

    public InstalacionElectricaCertificada(String codigo, String cliente, String direccion, double horas, Zona zona, int puntos, double valorMateriales) {
        super(codigo, cliente, direccion, horas, zona, puntos);
        this.valorMateriales = valorMateriales;
    }

    @Override
    public double calcularCosto() {
        double costoInstalacion = super.calcularCosto();
        double garantia = valorMateriales*0.05;

        return costoInstalacion + 120 + garantia;
    }

    @Override
    public int calcularDiasAtencion() {
        return super.calcularDiasAtencion()+2;
    }

    @Override
    public int getMaxVisitas() {
        return 2;
    }
}