import java.util.ArrayList;

import Enumeraciones.EstadoJornada;
import Enumeraciones.EstadoOrden;

public class SistemaTH {

    private ArrayList<OrdenServicio> ordenes;
    private ArrayList<Cuadrilla> cuadrillas;
    private ArrayList<Jornada> jornadas;
    private double ingresosRegistro;
    private double ingresosVisitas;

    public SistemaTH() {
        this.ordenes = new ArrayList<>();
        this.cuadrillas = new ArrayList<>();
        this.jornadas = new ArrayList<>();
        this.ingresosRegistro = 0;
        this.ingresosVisitas = 0;
    }

    public Cotizacion cotizar(OrdenServicio orden) {
        double costo = orden.calcularCosto();
        int dias = orden.calcularDiasAtencion();

        Cotizacion cotizacion = new Cotizacion(costo, dias);

        return cotizacion;
    }

    public void registrarOrden(OrdenServicio orden) {
        if (buscarOrden(orden.getCodigo()) == null) {

        ordenes.add(orden);

        ingresosRegistro += orden.calcularCosto();

        System.out.println("Orden registrada correctamente.");

        } else {
            System.out.println("Ya existe una orden con ese código.");
        }
    }

    public void registrarCuadrilla(Cuadrilla cuadrilla) {
        if (buscarCuadrilla(cuadrilla.getCodigo()) == null) {

        cuadrillas.add(cuadrilla);
        System.out.println("Cuadrilla registrada correctamente.");
        } else {
            System.out.println("Ya existe una cuadrilla con ese código.");
        }
    }

    public Jornada crearJornada(String codigoCuadrilla) {
        Cuadrilla cuadrilla = buscarCuadrilla(codigoCuadrilla);

        if (cuadrilla == null) {
            System.out.println("La cuadrilla no existe.");
            return null;
        }

        for (Jornada jornada : jornadas) {
            if (jornada.getCuadrilla() == cuadrilla
                    && jornada.getEstado() != EstadoJornada.CERRADA) {

                System.out.println("La cuadrilla ya tiene una jornada abierta.");
                return null;
            }
        }

        Jornada nuevaJornada = new Jornada(cuadrilla);
        jornadas.add(nuevaJornada);

        System.out.println("Jornada creada correctamente.");
        return nuevaJornada;
    }

    public void iniciarJornada(Jornada jornada) {
        if (!jornadas.contains(jornada)) {
        System.out.println("La jornada no existe en el sistema.");

        } else if (jornada.getEstado() != EstadoJornada.PREPARACION) {
            System.out.println("La jornada no está en preparación.");

        } else {
            int visitasAdicionales = jornada.iniciar();

            if (jornada.getEstado() == EstadoJornada.EN_ATENCION) {
                ingresosVisitas += visitasAdicionales * 40;

                System.out.println("Ingresos por visitas adicionales: Q"
                        + (visitasAdicionales * 40));
            }
        }
    }

    public OrdenServicio buscarOrden(String codigo) {
        for (OrdenServicio orden : ordenes) {
            if (orden.getCodigo().equals(codigo)) {
                return orden;
            }
        }

        return null;
    }

    public Cuadrilla buscarCuadrilla(String codigo) {
        for (Cuadrilla cuadrilla : cuadrillas) {
            if (cuadrilla.getCodigo().equals(codigo)) {
                return cuadrilla;
            }
        }

    return null;
    }

    public void reporteOrdenes() {

        int[][] cantidades = new int[4][5];

        for (OrdenServicio orden : ordenes) {

            int categoria = 0;
            int estadoOrden = 0;

            // Identificar categoria
            if (orden instanceof InstalacionElectricaCertificada) {
                categoria = 2;

            } else if (orden instanceof Diagnostico) {
                categoria = 0;

            } else if (orden instanceof Instalacion) {
                categoria = 1;

            } else if (orden instanceof MantenimientoAC) {
                categoria = 3;
            }

            // Identificar estado
            if (orden.getEstado() == EstadoOrden.PENDIENTE) {
                estadoOrden = 0;

            } else if (orden.getEstado() == EstadoOrden.ASIGNADA) {
                estadoOrden = 1;

            } else if (orden.getEstado() == EstadoOrden.EN_ATENCION) {
                estadoOrden = 2;

            } else if (orden.getEstado() == EstadoOrden.COMPLETADA) {
                estadoOrden = 3;

            } else if (orden.getEstado() == EstadoOrden.CANCELADA) {
                estadoOrden = 4;
            }

            cantidades[categoria][estadoOrden]++;
        }

        String[] categorias = {
            "Diagnostico",
            "Instalacion",
            "Instalacion certificada",
            "Mantenimiento aire"
        };

        System.out.println("REPORTE DE ORDENES");

        System.out.printf("%-25s %10s %10s %10s %10s %10s%n",
                "Categoria", "Pendiente", "Asignada",
                "Atencion", "Completada", "Cancelada");

        for (int i = 0; i < 4; i++) {

            System.out.printf("%-25s", categorias[i]);

            for (int j = 0; j < 5; j++) {
                System.out.printf("%10d", cantidades[i][j]);
            }

            System.out.println();
        }
    }

    public void reporteIngresos() {

        double ingresosDiagnostico = 0;
        double ingresosInstalacion = 0;
        double ingresosCertificada = 0;
        double ingresosMantenimiento = 0;

        for (OrdenServicio orden : ordenes) {

            double ingresoOrden = orden.getMontoCobrado()
                                + orden.getCobrosVisitas();

            if (orden instanceof InstalacionElectricaCertificada) {
                ingresosCertificada += ingresoOrden;

            } else if (orden instanceof Diagnostico) {
                ingresosDiagnostico += ingresoOrden;

            } else if (orden instanceof Instalacion) {
                ingresosInstalacion += ingresoOrden;

            } else if (orden instanceof MantenimientoAC) {
                ingresosMantenimiento += ingresoOrden;
            }
        }

        System.out.println("===== REPORTE DE INGRESOS =====");

        System.out.println("Ingresos por registro: Q"
                + ingresosRegistro);

        System.out.println("Ingresos por visitas adicionales: Q"
                + ingresosVisitas);

        System.out.println("Ingreso total: Q"
                + (ingresosRegistro + ingresosVisitas));

        System.out.println("===== INGRESOS POR CATEGORIA =====");

        System.out.println("Diagnosticos: Q"
                + ingresosDiagnostico);

        System.out.println("Instalaciones: Q"
                + ingresosInstalacion);

        System.out.println("Instalaciones certificadas: Q"
                + ingresosCertificada);

        System.out.println("Mantenimientos: Q"
                + ingresosMantenimiento);
    }

    public void reporteJornadas() {

        System.out.println("===== REPORTE DE JORNADAS =====");

        for (Jornada jornada : jornadas) {

            if (jornada.getEstado() != EstadoJornada.CERRADA) {

                System.out.println("Cuadrilla: "
                        + jornada.getCuadrilla().getCodigo());

                System.out.println("Estado: "
                        + jornada.getEstado());

                System.out.println("Horas asignadas: "
                        + jornada.calcularHorasAsignadas());

                System.out.println("Capacidad disponible: "
                        + jornada.calcularCapacidadDisponible());

                System.out.println("-------------------------");
            }
        }
    }
}