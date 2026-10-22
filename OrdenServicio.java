import Enumeraciones.EstadoOrden;
import Enumeraciones.Zona;


public abstract class OrdenServicio {

    private int codigo;
    private String cliente;
    private String direccion;
    private double horasEstimadas;
    private Zona zona;
    private EstadoOrden estado;
    private int visitas;
    private double montoCobrado;
    private double cobrosVisitas;
    private boolean registrada;

    public OrdenServicio(int codigo, String cliente,
            String direccion, double horasEstimadas, Zona zona) {

        if (codigo <= 0 || cliente == null || cliente.trim().isEmpty()
                || direccion == null || direccion.trim().isEmpty()
                || !Double.isFinite(horasEstimadas)
                || horasEstimadas <= 0 || zona == null) {
            throw new IllegalArgumentException("Datos de orden invalidos.");
        }

        this.codigo = codigo;
        this.cliente = cliente;
        this.direccion = direccion;
        this.horasEstimadas = horasEstimadas;
        this.zona = zona;
        this.estado = EstadoOrden.PENDIENTE;
        this.visitas = 0;
        this.montoCobrado = 0;
        this.cobrosVisitas = 0;
        this.registrada = false;
    }

    public abstract double calcularCosto();
    public abstract int calcularDiasAtencion();
    public abstract int getMaxVisitas();

    public String getCategoria() {
        return getClass().getSimpleName();
    }

    public int getCodigo() {
        return codigo;
    }

    public String getCliente() {
        return cliente;
    }

    public String getDireccion() {
        return direccion;
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

    public double getCobrosVisitas() {
        return cobrosVisitas;
    }

    public boolean isRegistrada() {
        return registrada;
    }

    public void registrar(double monto) {
        if (registrada || estado != EstadoOrden.PENDIENTE
                || !Double.isFinite(monto) || monto < 0) {
            throw new IllegalStateException("No se puede registrar la orden.");
        }

        montoCobrado = monto;
        registrada = true;
    }

    public void asignar() {
        if (!registrada || estado != EstadoOrden.PENDIENTE
                || visitas >= getMaxVisitas()) {
            throw new IllegalStateException("No se puede asignar la orden.");
        }

        estado = EstadoOrden.ASIGNADA;
    }

    public void retirar() {
        if (estado != EstadoOrden.ASIGNADA) {
            throw new IllegalStateException("La orden no esta asignada.");
        }

        estado = EstadoOrden.PENDIENTE;
    }

    public boolean iniciarVisita() {
        if (estado != EstadoOrden.ASIGNADA
                || visitas >= getMaxVisitas()) {
            throw new IllegalStateException("No se puede iniciar la visita.");
        }

        visitas++;
        estado = EstadoOrden.EN_ATENCION;

        if (visitas > 1) {
            cobrosVisitas += 40;
            return true;
        } else {
            return false;
        }
    }

    public void registrarResultado(boolean completada) {
        if (estado != EstadoOrden.EN_ATENCION) {
            throw new IllegalStateException("La orden no esta en atencion.");
        }

        if (completada) {
            estado = EstadoOrden.COMPLETADA;
        } else if (visitas >= getMaxVisitas()) {
            estado = EstadoOrden.CANCELADA;
        } else {
            estado = EstadoOrden.PENDIENTE;
        }
    }

    public void registrarInicial() {
    if (!registrada && estado == EstadoOrden.PENDIENTE) {
        registrada = true;
    }
}
}
