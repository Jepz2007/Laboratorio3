import Enumeraciones.EstadoOrden;
import Enumeraciones.Zona;

public abstract class OrdenServicio {

    private String codigo;
    private String cliente;
    private String direccion;
    private double horasEstimadas;
    private Zona zona;
    private EstadoOrden estado;
    private int visitas;
    private double montoCobrado;
    private double cobrosVisitas;

    protected OrdenServicio(String codigo, String cliente,
            String direccion, double horasEstimadas, Zona zona) {

        this.codigo = codigo;
        this.cliente = cliente;
        this.direccion = direccion;
        this.horasEstimadas = horasEstimadas;
        this.zona = zona;
        this.estado = EstadoOrden.PENDIENTE;
        this.visitas = 0;
        this.montoCobrado = 0;
        this.cobrosVisitas = 0;
    }

    public abstract double calcularCosto();

    public abstract int calcularDiasAtencion();

    public abstract int getMaxVisitas();

    public String getCodigo() {
        return codigo;
    }

    public double getHorasEstimadas() {
        return horasEstimadas;
    }

    public Zona getZona() {
        return zona;
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public int getVisitas() {
        return visitas;
    }

    public double getMontoCobrado() {
        return montoCobrado;
    }

    public void asignar() {
        if (estado == EstadoOrden.PENDIENTE){
            estado = EstadoOrden.ASIGNADA;
        }
    }

    public void retirar() {
        if (estado == EstadoOrden.ASIGNADA){
            estado = EstadoOrden.PENDIENTE;
        }
    }

    public boolean iniciarVisita() {
        if (estado == EstadoOrden.ASIGNADA) {
        visitas++;
        estado = EstadoOrden.EN_ATENCION;

        return visitas > 1;
        }else{
        return false;
        }
    }

    public void registrarResultado(boolean completada) {

        if (estado == EstadoOrden.EN_ATENCION) {

            if (completada) {
                estado = EstadoOrden.COMPLETADA;

            } else if (visitas >= getMaxVisitas()) {
                estado = EstadoOrden.CANCELADA;

            } else {
                estado = EstadoOrden.PENDIENTE;
            }
        }
    }

    public void registrarCobroVisita() {
    cobrosVisitas += 40;
    }

    public double getCobrosVisitas() {
    return cobrosVisitas;
    }
}