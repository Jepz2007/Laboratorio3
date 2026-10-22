import java.util.Scanner;

public class Menu {

    Scanner scanner = new Scanner(System.in);

    public void mostrarMenu(){
        int opcion = 0;

        while (opcion !=4){
            System.out.println("1: Lista todas las cartas.");
            System.out.println("2: Buscar carta específica.");
            System.out.println("3: Ordenar por costo de energía.");
            System.out.println("4: Finalizar programa.");
            System.out.println();
            System.out.println("¿Que desea realizar?: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            if (opcion < 1 || opcion > 4){
                System.out.println("Opción inválida. Elija del 1 al 4.");
            } else {
                switch (opcion){
                    case 1:
                        break;
                    
                }
            }
        }
    }
}