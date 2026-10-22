import Enumeraciones.Zona;
import java.util.Scanner;
import java.util.ArrayList;

public class Menu {

    private Scanner scanner = new Scanner(System.in);
    private SistemaTH sistema = new SistemaTH();
    private ArrayList<Jornada> jornadas = new ArrayList<>();

    public void mostrarMenu() {

        int opcion = 0;

        while (opcion != 14) {

            System.out.println("\nTECNIHOGAR");
            System.out.println("1: Cotizar servicio.");
            System.out.println("2: Registrar orden.");
            System.out.println("3: Registrar cuadrilla.");
            System.out.println("4: Crear jornada.");
            System.out.println("5: Asignar orden a jornada.");
            System.out.println("6: Retirar orden de jornada.");
            System.out.println("7: Iniciar jornada.");
            System.out.println("8: Registrar resultado de visita.");
            System.out.println("9: Consultar orden.");
            System.out.println("10: Consultar cuadrilla.");
            System.out.println("11: Reporte de ordenes.");
            System.out.println("12: Reporte de ingresos.");
            System.out.println("13: Reporte de jornadas.");
            System.out.println("14: Finalizar programa.");
            System.out.print("¿Que desea realizar?: ");

            opcion = leerEntero();

            if (opcion < 1 || opcion > 14) {
                System.out.println("Opcion invalida. Elija del 1 al 14.");

            } else {
                try {
                    switch (opcion) {
                        case 1:
                            cotizarServicio();
                            break;
                        case 2:
                            registrarOrden();
                            break;
                        case 3:
                            registrarCuadrilla();
                            break;
                        case 4:
                            crearJornada();
                            break;
                        case 5:
                            asignarOrden();
                            break;
                        case 6:
                            retirarOrden();
                            break;
                        case 7:
                            iniciarJornada();
                            break;
                        case 8:
                            registrarResultado();
                            break;
                        case 9:
                            consultarOrden();
                            break;
                        case 10:
                            consultarCuadrilla();
                            break;
                        case 11:
                            sistema.reporteOrdenes();
                            break;
                        case 12:
                            sistema.reporteIngresos();
                            break;
                        case 13:
                            sistema.reporteJornadas();
                            break;
                        case 14:
                            System.out.println("Programa finalizado.");
                            break;
                    }

                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());

                } catch (IllegalStateException e) {
                    System.out.println("Operacion rechazada: " + e.getMessage());
                }
            }
        }
    }

    public int leerEntero() {
        while (true) {
            String entrada = scanner.nextLine().trim();

            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un entero valido: ");
            }
        }
    }

    public double leerDecimal() {
        while (true) {
            String entrada = scanner.nextLine().trim();

            try {
                double numero = Double.parseDouble(entrada);

                if (Double.isFinite(numero)) {
                    return numero;
                }

                System.out.print("Ingrese un numero finito: ");

            } catch (NumberFormatException e) {
                System.out.print("Ingrese un decimal valido: ");
            }
        }
    }

    public Zona seleccionarZona() {
        while (true) {
            System.out.println("\nZONAS");
            System.out.println("1: Metropolitana.");
            System.out.println("2: Periferica.");
            System.out.print("Seleccione la zona: ");

            int opcion = leerEntero();

            if (opcion == 1) {
                return Zona.METROPOLITANA;
            } else if (opcion == 2) {
                return Zona.PERIFERICA;
            } else {
                System.out.println("Zona invalida.");
            }
        }
    }

    public OrdenServicio ingresarServicio() {

        System.out.println("\nDATOS DEL SERVICIO");

        System.out.print("Codigo de la orden: ");
        int codigo = leerEntero();

        if (codigo <= 0) {
            System.out.println("El codigo debe ser positivo.");
            return null;
        }

        if (sistema.buscarOrden(codigo) != null) {
            System.out.println("Codigo repetido.");
            return null;
        }

        System.out.print("Nombre del cliente: ");
        String cliente = scanner.nextLine();

        if (cliente.trim().isEmpty()) {
            System.out.println("El nombre no puede estar vacio.");
            return null;
        }

        System.out.print("Direccion: ");
        String direccion = scanner.nextLine();

        if (direccion.trim().isEmpty()) {
            System.out.println("La direccion no puede estar vacia.");
            return null;
        }

        System.out.println("\nCATEGORIAS");
        System.out.println("1: Diagnostico.");
        System.out.println("2: Instalacion.");
        System.out.println("3: Instalacion electrica certificada.");
        System.out.println("4: Mantenimiento AC.");
        System.out.print("Seleccione una categoria: ");

        int categoria = leerEntero();

        if (categoria < 1 || categoria > 4) {
            System.out.println("Categoria invalida.");
            return null;
        }

        Zona zona = seleccionarZona();

        if (categoria == 4 && zona != Zona.METROPOLITANA) {
            System.out.println("Mantenimiento AC solo permite zona metropolitana.");
            return null;
        }

        System.out.print("Horas estimadas: ");
        double horas = leerDecimal();

        if (horas <= 0) {
            System.out.println("Las horas deben ser positivas.");
            return null;
        }

        if (categoria == 1) {

            if (horas > 1.5) {
                System.out.println("Diagnostico supera 1.5 horas.");
                return null;
            }

            return new Diagnostico(codigo, cliente, direccion, horas, zona);

        } else if (categoria == 2 || categoria == 3) {

            System.out.print("Cantidad de puntos: ");
            int puntos = leerEntero();

            if (puntos <= 0) {
                System.out.println("Los puntos deben ser positivos.");
                return null;
            }

            if (categoria == 2) {
                return new Instalacion(
                    codigo, cliente, direccion, horas, zona, puntos
                );
            }

            System.out.print("Valor de materiales: Q");
            double materiales = leerDecimal();

            if (materiales < 0) {
                System.out.println("Materiales invalidos.");
                return null;
            }

            return new InstalacionElectricaCertificada(
                codigo, cliente, direccion, horas, zona, puntos, materiales
            );

        } else {

            System.out.print("Capacidad BTU: ");
            int btu = leerEntero();

            if (btu < 9000 || btu > 60000) {
                System.out.println("BTU fuera de rango.");
                return null;
            }

            return new MantenimientoAC(
                codigo, cliente, direccion, horas, zona, btu
            );
        }
    }

    public void cotizarServicio() {

        OrdenServicio orden = ingresarServicio();

        if (orden == null) {
            return;
        }

        Cotizacion cotizacion = sistema.cotizar(orden);

        System.out.println("\nCOTIZACION");

        System.out.printf("Costo: Q%.2f%n", cotizacion.getCosto());

        System.out.println("Dias estimados: "
                + cotizacion.getDiasEstimados());

        System.out.print("¿Acepta la cotizacion? (1: Si, 2: No): ");
        int respuesta = leerEntero();

        if (respuesta == 1) {
            sistema.registrarOrden(orden);
        } else if (respuesta == 2) {
            System.out.println("Cotizacion rechazada.");
        } else {
            System.out.println("Opcion invalida. No se registro.");
        }
    }

    public void registrarOrden() {
        OrdenServicio orden = ingresarServicio();

        if (orden != null) {
            sistema.registrarOrden(orden);
        }
    }

    public void registrarCuadrilla() {

        System.out.println("\nREGISTRAR CUADRILLA");

        System.out.print("Codigo: ");
        int codigo = leerEntero();

        if (codigo <= 0 || sistema.buscarCuadrilla(codigo) != null) {
            System.out.println("Codigo invalido o repetido.");
            return;
        }

        System.out.print("Capacidad de horas: ");
        double capacidad = leerDecimal();

        if (capacidad <= 0) {
            System.out.println("La capacidad debe ser positiva.");
            return;
        }

        System.out.print("Certificada en refrigeracion (1: Si, 2: No): ");
        int respuesta = leerEntero();

        if (respuesta != 1 && respuesta != 2) {
            System.out.println("Opcion invalida.");
            return;
        }

        Cuadrilla cuadrilla = new Cuadrilla(
            codigo, capacidad, respuesta == 1
        );

        sistema.registrarCuadrilla(cuadrilla);
    }

    public void crearJornada() {

        System.out.print("Codigo de cuadrilla: ");
        int codigo = leerEntero();

        Jornada jornada = sistema.crearJornada(codigo);

        if (jornada != null) {
            jornadas.add(jornada);
            System.out.println("Numero de jornada: " + jornadas.size());
        }
    }

    public Jornada seleccionarJornada() {

        if (jornadas.isEmpty()) {
            System.out.println("No hay jornadas.");
            return null;
        }

        System.out.println("\nJORNADAS");

        for (int i = 0; i < jornadas.size(); i++) {
            Jornada jornada = jornadas.get(i);

            System.out.println((i + 1) + ": Cuadrilla "
                    + jornada.getCuadrilla().getCodigo()
                    + " - " + jornada.getEstado());
        }

        System.out.print("Numero de jornada: ");
        int numero = leerEntero();

        if (numero < 1 || numero > jornadas.size()) {
            System.out.println("Jornada invalida.");
            return null;
        }

        return jornadas.get(numero - 1);
    }

    public void asignarOrden() {

        Jornada jornada = seleccionarJornada();

        if (jornada == null) {
            return;
        }

        System.out.print("Codigo de orden: ");
        int codigo = leerEntero();

        OrdenServicio orden = sistema.buscarOrden(codigo);

        if (orden == null) {
            System.out.println("La orden no existe.");
        } else {
            jornada.agregarOrden(orden);
        }
    }

    public void retirarOrden() {

        Jornada jornada = seleccionarJornada();

        if (jornada == null) {
            return;
        }

        System.out.print("Codigo de orden: ");
        int codigo = leerEntero();

        OrdenServicio orden = sistema.buscarOrden(codigo);

        if (orden == null) {
            System.out.println("La orden no existe.");
        } else {
            jornada.retirarOrden(orden);
        }
    }

    public void iniciarJornada() {

        Jornada jornada = seleccionarJornada();

        if (jornada != null) {
            sistema.iniciarJornada(jornada);
        }
    }

    public void registrarResultado() {

        Jornada jornada = seleccionarJornada();

        if (jornada == null) {
            return;
        }

        System.out.print("Codigo de orden: ");
        int codigo = leerEntero();

        OrdenServicio orden = sistema.buscarOrden(codigo);

        if (orden == null) {
            System.out.println("La orden no existe.");
            return;
        }

        System.out.println("\nRESULTADO DE VISITA");
        System.out.println("1: Completada.");
        System.out.println("2: Visita fallida.");
        System.out.print("Seleccione resultado: ");

        int respuesta = leerEntero();

        if (respuesta == 1) {
            jornada.registrarResultado(orden, true);
        } else if (respuesta == 2) {
            jornada.registrarResultado(orden, false);
        } else {
            System.out.println("Resultado invalido.");
        }
    }

    public void consultarOrden() {

        System.out.print("Codigo de orden: ");
        int codigo = leerEntero();

        OrdenServicio orden = sistema.buscarOrden(codigo);

        if (orden == null) {
            System.out.println("La orden no existe.");
            return;
        }

        System.out.println("\nCONSULTAR ORDEN");
        System.out.println("Codigo: " + orden.getCodigo());
        System.out.println("Cliente: " + orden.getCliente());
        System.out.println("Direccion: " + orden.getDireccion());
        System.out.println("Categoria: " + orden.getCategoria());
        System.out.println("Zona: " + orden.getZona());
        System.out.println("Estado: " + orden.getEstado());
        System.out.println("Horas: " + orden.getHorasEstimadas());
        System.out.println("Visitas: " + orden.getVisitas());
        System.out.println("Maximo visitas: " + orden.getMaxVisitas());

        System.out.printf("Costo: Q%.2f%n", orden.calcularCosto());
        System.out.printf("Cobrado: Q%.2f%n", orden.getMontoCobrado());
        System.out.printf("Visitas adicionales: Q%.2f%n",
                orden.getCobrosVisitas());

        System.out.println("Dias estimados: "
                + orden.calcularDiasAtencion());
    }

    public void consultarCuadrilla() {

        System.out.print("Codigo de cuadrilla: ");
        int codigo = leerEntero();

        Cuadrilla cuadrilla = sistema.buscarCuadrilla(codigo);

        if (cuadrilla == null) {
            System.out.println("La cuadrilla no existe.");
            return;
        }

        System.out.println("\nCONSULTAR CUADRILLA");
        System.out.println("Codigo: " + cuadrilla.getCodigo());
        System.out.println("Capacidad: " + cuadrilla.getCapacidadHoras());

        if (cuadrilla.isCertificadaRefrigeracion()) {
            System.out.println("Certificada en refrigeracion: Si");
        } else {
            System.out.println("Certificada en refrigeracion: No");
        }
    }
}
