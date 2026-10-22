import Enumeraciones.Zona;

public class Diagnostico extends OrdenServicio {

    public Diagnostico(String codigo, String cliente, String direccion, double horas, Zona zona) {

        super(codigo, cliente, direccion, horas, zona);
    }

    @Override
    public double calcularCosto() {
        if (getZona()== Zona.METROPOLITANA){
            return 150;
        }else{
        return 225;
        }
    }

    @Override
    public int calcularDiasAtencion() {
        if (getZona()== Zona.METROPOLITANA){
            return 1;
        }else{
        return 2;
        }
    }

    @Override
    public int getMaxVisitas() {
        return 3;
    }
}