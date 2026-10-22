import java.util.ArrayList;

import Enumeraciones.EstadoJornada;
import Enumeraciones.EstadoOrden;

public class Jornada {

    private Cuadrilla cuadrilla;
    private ArrayList<OrdenServicio> ordenes;
    private EstadoJornada estado;

    public Jornada(Cuadrilla cuadrilla) {
        if (cuadrilla == null) {
            throw new IllegalArgumentException("Cuadrilla invalida.");
        }

        this.cuadrilla = cuadrilla;
        this.ordenes = new ArrayList<>();
        this.estado = EstadoJornada.PREPARACION;
    }

    public void agregarOrden(OrdenServicio orden) {

        if (orden == null) {
            System.out.println("Orden invalida.");

        } else if (estado != EstadoJornada.PREPARACION) {
            System.out.println("La jornada no esta en preparacion.");

        } else if (!orden.isRegistrada()
                || orden.getEstado() != EstadoOrden.PENDIENTE) {
            System.out.println("La orden no esta disponible.");

        } else if (ordenes.contains(orden)) {
            System.out.println("La orden ya pertenece a esta jornada.");

        } else if (orden.getHorasEstimadas() > calcularCapacidadDisponible()) {
            System.out.println("No hay suficiente capacidad.");

        } else if (orden instanceof MantenimientoAC
                && !cuadrilla.isCertificadaRefrigeracion()) {
            System.out.println("La cuadrilla no tiene certificacion.");

        } else {
            orden.asignar();
            ordenes.add(orden);
            System.out.println("Orden asignada correctamente.");
        }
    }

    public void retirarOrden(OrdenServicio orden) {

        if (estado != EstadoJornada.PREPARACION) {
            System.out.println("La jornada no esta en preparacion.");

        } else if (orden == null || !ordenes.contains(orden)) {
            System.out.println("La orden no pertenece a esta jornada.");

        } else {
            orden.retirar();
            ordenes.remove(orden);
            System.out.println("Orden retirada correctamente.");
        }
    }

    public double calcularHorasAsignadas() {
        double total = 0;

        for (OrdenServicio orden : ordenes) {
            total += orden.getHorasEstimadas();
        }

        return total;
    }

    public double calcularCapacidadDisponible() {
        return cuadrilla.getCapacidadHoras() - calcularHorasAsignadas();
    }

    public int iniciar() {

        if (estado != EstadoJornada.PREPARACION) {
            System.out.println("La jornada no esta en preparacion.");
            return 0;
        }

        if (ordenes.isEmpty()) {
            System.out.println("No se puede iniciar una jornada vacia.");
            return 0;
        }

        for (OrdenServicio orden : ordenes) {
            if (orden.getEstado() != EstadoOrden.ASIGNADA
                    || orden.getVisitas() >= orden.getMaxVisitas()) {
                System.out.println("Hay una orden que no puede iniciar.");
                return 0;
            }
        }

        int adicionales = 0;

        for (OrdenServicio orden : ordenes) {
            if (orden.iniciarVisita()) {
                adicionales++;
            }
        }

        estado = EstadoJornada.EN_ATENCION;
        System.out.println("Jornada iniciada correctamente.");

        return adicionales;
    }

    public void registrarResultado(OrdenServicio orden, boolean completada) {

        if (estado != EstadoJornada.EN_ATENCION) {
            System.out.println("La jornada no esta en atencion.");

        } else if (orden == null || !ordenes.contains(orden)) {
            System.out.println("La orden no pertenece a esta jornada.");

        } else if (orden.getEstado() != EstadoOrden.EN_ATENCION) {
            System.out.println("La orden ya tiene resultado.");

        } else {
            orden.registrarResultado(completada);
            System.out.println("Resultado registrado correctamente.");
            cerrar();
        }
    }

    private void cerrar() {

        for (OrdenServicio orden : ordenes) {
            if (orden.getEstado() == EstadoOrden.EN_ATENCION) {
                return;
            }
        }

        estado = EstadoJornada.CERRADA;
        System.out.println("Jornada cerrada correctamente.");
    }

    public EstadoJornada getEstado() {
        return estado;
    }

    public Cuadrilla getCuadrilla() {
        return cuadrilla;
    }
}
