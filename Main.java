import Enumeraciones.Zona;

public class Main {
    public static void main(String[] args) {

        SistemaTH sistema = new SistemaTH();

        Instalacion instalacion = new Instalacion(
            "I001",
            "Juan Perez",
            "Zona 10",
            2.5,
            Zona.METROPOLITANA,
            5
        );

        Cotizacion cotizacion = sistema.cotizar(instalacion);

        System.out.println("Costo: Q" + cotizacion.getCosto());
        System.out.println("Dias estimados: "
                + cotizacion.getDiasEstimados());
    }
}