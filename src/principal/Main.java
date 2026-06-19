package principal;

import servicio.CalculadoraService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        CalculadoraService calculadora = new CalculadoraService();

        int opcion;

        do {
            System.out.println();
            System.out.println("==================");
            System.out.println(" CALCULADORA ");
            System.out.println("==================");
            System.out.println("1. Sumar");
            System.out.println("2. Restar");
            System.out.println("3. Multiplicar");
            System.out.println("4. Dividir");
            System.out.println("5. Salir");
            System.out.print("Seleccione: ");

            opcion = sc.nextInt();

            if (opcion >= 1 && opcion <= 4) {

                System.out.print("NUMERO 1: ");
                double a = sc.nextDouble();

                System.out.print("NUMERO 2: ");
                double b = sc.nextDouble();

                try {
                    double resultado = switch (opcion) {
                        case 1 -> calculadora.sumar(a, b);
                        case 2 -> calculadora.restar(a, b);
                        case 3 -> calculadora.multiplicar(a, b);
                        default -> calculadora.dividir(a, b);
                    };

                    System.out.println(
                            "RESULTADO = " + resultado
                    );

                } catch (Exception e) {
                    System.out.println(
                            e.getMessage()
                    );
                }
            }
        } while (opcion != 5);

        System.out.println(
                "programa FINALIZADO!"
        );
    }
}