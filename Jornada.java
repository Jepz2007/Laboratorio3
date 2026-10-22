import java.util.ArrayList;

import Enumeraciones.EstadoJornada;
import Enumeraciones.EstadoOrden;

public class Jornada {

    private Cuadrilla cuadrilla;
    private ArrayList<OrdenServicio> ordenes;
    private EstadoJornada estado;

    public Jornada(Cuadrilla cuadrilla) {
        this.cuadrilla = cuadrilla;
        this.ordenes = new ArrayList<>();
        this.estado = EstadoJornada.PREPARACION;
    }

    public void agregarOrden(OrdenServicio orden) {

        if (estado != EstadoJornada.PREPARACION) {
        System.out.println("La jornada no está en preparación.");

        } else if (orden.getEstado() != EstadoOrden.PENDIENTE) {
            System.out.println("La orden no está pendiente.");

        } else if (orden.getHorasEstimadas() > calcularCapacidadDisponible()) {
            System.out.println("No hay suficiente capacidad.");

        } else if (orden instanceof MantenimientoAC && !cuadrilla.isCertificadaRefrigeracion()) {
            System.out.println("La cuadrilla no tiene certificación.");

        } else {
            ordenes.add(orden);
            orden.asignar();

            System.out.println("Orden asignada correctamente.");
        }
    }

    public void retirarOrden(OrdenServicio orden) {

        if (estado != EstadoJornada.PREPARACION) {
        System.out.println("La jornada no está en preparación.");

        } else if (!ordenes.contains(orden)) {
            System.out.println("La orden no pertenece a esta jornada.");

        } else {
            ordenes.remove(orden);
            orden.retirar();

            System.out.println("Orden retirada correctamente.");
        }
    }

    public double calcularHorasAsignadas() {
        double totalhoras = 0;

        for (OrdenServicio orden: ordenes){
            totalhoras += orden.getHorasEstimadas();
        }

        return totalhoras;
    }

    public double calcularCapacidadDisponible() {
        return cuadrilla.getCapacidadHoras() - calcularHorasAsignadas();
    }

    public int iniciar() {

        if (estado != EstadoJornada.PREPARACION) {
            System.out.println("La jornada no está en preparación.");
            return 0;

        } else if (ordenes.isEmpty()) {
            System.out.println("No se puede iniciar una jornada vacía.");
            return 0;

        } else {
            int visitasAdicionales = 0;

            estado = EstadoJornada.EN_ATENCION;

            for (OrdenServicio orden : ordenes) {

                if (orden.iniciarVisita()) {
                    visitasAdicionales++;
                    orden.registrarCobroVisita();
                }
            }

            System.out.println("Jornada iniciada correctamente.");

            return visitasAdicionales;
        }
    }

    public void registrarResultado(OrdenServicio orden, boolean completada) {
        if (estado != EstadoJornada.EN_ATENCION) {
            System.out.println("La jornada no está en atención.");

        } else if (!ordenes.contains(orden)) {
            System.out.println("La orden no pertenece a esta jornada.");

        } else if (orden.getEstado() != EstadoOrden.EN_ATENCION) {
            System.out.println("La orden ya tiene un resultado.");

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
