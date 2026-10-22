import Enumeraciones.Zona;

public class Instalacion extends OrdenServicio {

    private int puntosaInstalar;

    public Instalacion(String codigo, String cliente, String direccion, double horas, Zona zona, int puntos) {
        super(codigo, cliente, direccion, horas, zona);
        puntosaInstalar = puntos;
    }

    @Override
    public double calcularCosto() {
        double costobase = 0;

        if (getZona()== Zona.METROPOLITANA){
            costobase = 100;
        }else{
            costobase = 175;
        }

        return costobase + (calcularHorasFacturables()*90);
        
    }

    public double calcularHorasPuntos(){
        return puntosaInstalar*0.75;
    }

    public int calcularHorasFacturables() {
        double horasPuntos = calcularHorasPuntos();
        double horasEstimadas = getHorasEstimadas();

        return (int) Math.ceil(Math.max(horasPuntos, horasEstimadas));
    }

    @Override
    public int calcularDiasAtencion() {
        if (getZona()== Zona.METROPOLITANA){
            return 3;
        }else{
        return 5;
        }
    }

    @Override
    public int getMaxVisitas() {
        return 3;
    }
}