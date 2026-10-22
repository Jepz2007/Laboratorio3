import Enumeraciones.Zona;


public class InstalacionElectricaCertificada extends Instalacion {

    private double valorMateriales;

    public InstalacionElectricaCertificada(int codigo, String cliente,
            String direccion, double horas, Zona zona,
            int puntos, double valorMateriales) {

        super(codigo, cliente, direccion, horas, zona, puntos);

        if (!Double.isFinite(valorMateriales) || valorMateriales < 0) {
            throw new IllegalArgumentException(
                "Valor de materiales invalido.");
        }

        this.valorMateriales = valorMateriales;
    }

    @Override
    public double calcularCosto() {
        return super.calcularCosto() + 120 + valorMateriales * 0.05;
    }

    @Override
    public int calcularDiasAtencion() {
        return super.calcularDiasAtencion() + 2;
    }

    @Override
    public int getMaxVisitas() {
        return 2;
    }

    public double getValorMateriales() {
        return valorMateriales;
    }
}
