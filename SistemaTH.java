
import java.util.ArrayList;
import Enumeraciones.EstadoJornada;
import Enumeraciones.EstadoOrden;
import Enumeraciones.Zona;

public class SistemaTH {

    private ArrayList<OrdenServicio> ordenes;
    private ArrayList<Cuadrilla> cuadrillas;
    private ArrayList<Jornada> jornadas;
    private double ingresosRegistro;
    private double ingresosVisitas;

    public SistemaTH() {
        ordenes = new ArrayList<>();
        cuadrillas = new ArrayList<>();
        jornadas = new ArrayList<>();

        ingresosRegistro = 0;
        ingresosVisitas = 0;

        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {

        ordenes.add(new Diagnostico(
            101, "Juan Perez", "Zona 10", 1.0, Zona.METROPOLITANA));

        ordenes.add(new Diagnostico(
            102, "Maria Lopez", "Mixco", 1.5, Zona.PERIFERICA));

        ordenes.add(new Instalacion(
            201, "Carlos Garcia", "Zona 1", 2.5,
            Zona.METROPOLITANA, 5));

        ordenes.add(new Instalacion(
            202, "Ana Morales", "Amatitlan", 4.0,
            Zona.PERIFERICA, 3));

        ordenes.add(new InstalacionElectricaCertificada(
            301, "Pedro Ramirez", "Zona 15", 2.5,
            Zona.METROPOLITANA, 5, 2000));

        ordenes.add(new InstalacionElectricaCertificada(
            302, "Sofia Castillo", "Villa Nueva", 3.0,
            Zona.PERIFERICA, 4, 1500));

        ordenes.add(new MantenimientoAC(
            401, "Luis Hernandez", "Zona 14", 2.0,
            Zona.METROPOLITANA, 12000));

        ordenes.add(new MantenimientoAC(
            402, "Lucia Gomez", "Zona 9", 3.0,
            Zona.METROPOLITANA, 24000));

        cuadrillas.add(new Cuadrilla(501, 8, true));
        cuadrillas.add(new Cuadrilla(502, 10, false));
        cuadrillas.add(new Cuadrilla(503, 6, true));

        for (OrdenServicio orden : ordenes) {
        orden.registrarInicial();
        }
    }

    public OrdenServicio buscarOrden(int codigo) {
        for (OrdenServicio orden : ordenes) {
            if (orden.getCodigo() == codigo) {
                return orden;
            }
        }
        return null;
    }

    public Cuadrilla buscarCuadrilla(int codigo) {
        for (Cuadrilla cuadrilla : cuadrillas) {
            if (cuadrilla.getCodigo() == codigo) {
                return cuadrilla;
            }
        }
        return null;
    }

    public Cotizacion cotizar(OrdenServicio orden) {
        if (orden == null) {
            throw new IllegalArgumentException("Orden invalida.");
        }

        double costo = orden.calcularCosto();

        if (!Double.isFinite(costo) || costo < 0) {
            throw new IllegalArgumentException("Costo invalido.");
        }

        return new Cotizacion(costo, orden.calcularDiasAtencion());
    }

    public void registrarOrden(OrdenServicio orden) {

        if (orden == null || orden.getCodigo() <= 0) {
            System.out.println("Orden invalida.");

        } else if (orden.isRegistrada()) {
            System.out.println("La orden ya fue registrada.");

        } else if (buscarOrden(orden.getCodigo()) != null) {
            System.out.println("Codigo de orden repetido.");

        } else {
            double costo = orden.calcularCosto();

            if (!Double.isFinite(costo) || costo < 0) {
                System.out.println("Costo invalido.");
                return;
            }

            orden.registrar(costo);
            ordenes.add(orden);
            ingresosRegistro += costo;

            System.out.println("Orden registrada correctamente.");
        }
    }

    public void registrarCuadrilla(Cuadrilla cuadrilla) {

        if (cuadrilla == null || cuadrilla.getCodigo() <= 0
                || !Double.isFinite(cuadrilla.getCapacidadHoras())
                || cuadrilla.getCapacidadHoras() <= 0) {
            System.out.println("Cuadrilla invalida.");

        } else if (buscarCuadrilla(cuadrilla.getCodigo()) != null) {
            System.out.println("Codigo de cuadrilla repetido.");

        } else {
            cuadrillas.add(cuadrilla);
            System.out.println("Cuadrilla registrada correctamente.");
        }
    }

    public Jornada crearJornada(int codigoCuadrilla) {

        Cuadrilla cuadrilla = buscarCuadrilla(codigoCuadrilla);

        if (cuadrilla == null) {
            System.out.println("La cuadrilla no existe.");
            return null;
        }

        for (Jornada jornada : jornadas) {
            if (jornada.getCuadrilla() == cuadrilla
                    && jornada.getEstado() != EstadoJornada.CERRADA) {
                System.out.println("La cuadrilla tiene una jornada abierta.");
                return null;
            }
        }

        Jornada nueva = new Jornada(cuadrilla);
        jornadas.add(nueva);

        System.out.println("Jornada creada correctamente.");
        return nueva;
    }

    public void iniciarJornada(Jornada jornada) {

        if (jornada == null || !jornadas.contains(jornada)) {
            System.out.println("La jornada no existe.");

        } else if (jornada.getEstado() != EstadoJornada.PREPARACION) {
            System.out.println("La jornada no esta en preparacion.");

        } else {
            int adicionales = jornada.iniciar();

            if (jornada.getEstado() == EstadoJornada.EN_ATENCION) {
                ingresosVisitas += adicionales * 40;

                System.out.printf(
                    "Cobro por visitas adicionales: Q%.2f%n",
                    adicionales * 40.0
                );
            }
        }
    }

    public void reporteOrdenes() {

        ArrayList<String> categorias = new ArrayList<>();

        for (OrdenServicio orden : ordenes) {
            if (!categorias.contains(orden.getCategoria())) {
                categorias.add(orden.getCategoria());
            }
        }

        EstadoOrden[] estados = EstadoOrden.values();

        System.out.println("\nREPORTE DE ORDENES");

        System.out.printf("%-32s", "Categoria");

        for (EstadoOrden estado : estados) {
            System.out.printf("%15s", estado);
        }

        System.out.println();

        for (String categoria : categorias) {

            System.out.printf("%-32s", categoria);

            for (EstadoOrden estado : estados) {
                int cantidad = 0;

                for (OrdenServicio orden : ordenes) {
                    if (orden.getCategoria().equals(categoria)
                            && orden.getEstado() == estado) {
                        cantidad++;
                    }
                }

                System.out.printf("%15d", cantidad);
            }

            System.out.println();
        }
    }

    public void reporteIngresos() {

        ArrayList<String> categorias = new ArrayList<>();

        for (OrdenServicio orden : ordenes) {
            if (!categorias.contains(orden.getCategoria())) {
                categorias.add(orden.getCategoria());
            }
        }

        System.out.println("\nREPORTE DE INGRESOS");

        System.out.printf("Ingresos por registro: Q%.2f%n",
                ingresosRegistro);

        System.out.printf("Ingresos por visitas adicionales: Q%.2f%n",
                ingresosVisitas);

        System.out.printf("Ingreso total: Q%.2f%n",
                ingresosRegistro + ingresosVisitas);

        System.out.println("\nINGRESOS POR CATEGORIA");

        for (String categoria : categorias) {
            double total = 0;

            for (OrdenServicio orden : ordenes) {
                if (orden.getCategoria().equals(categoria)) {
                    total += orden.getMontoCobrado();
                    total += orden.getCobrosVisitas();
                }
            }

            System.out.printf("%s: Q%.2f%n", categoria, total);
        }
    }

    public void reporteJornadas() {

        boolean hayJornadas = false;

        System.out.println("\nREPORTE DE JORNADAS");

        for (Jornada jornada : jornadas) {
            if (jornada.getEstado() != EstadoJornada.CERRADA) {

                hayJornadas = true;

                System.out.println("Cuadrilla: "
                        + jornada.getCuadrilla().getCodigo());

                System.out.println("Estado: " + jornada.getEstado());

                System.out.println("Horas asignadas: "
                        + jornada.calcularHorasAsignadas());

                System.out.println("Capacidad disponible: "
                        + jornada.calcularCapacidadDisponible());

                System.out.println();
            }
        }

        if (!hayJornadas) {
            System.out.println("No hay jornadas abiertas.");
        }
    }
}
