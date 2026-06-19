package servicio;

import dao.CalculadoraDAO;
import modelo.Operacion;

public class CalculadoraService {

    private final CalculadoraDAO dao =
            new CalculadoraDAO();

    public double sumar(double a, double b) {

        double r = a + b;

        dao.guardarOperacion(
                new Operacion(
                        a,
                        b,
                        "SUMA",
                        r
                )
        );
        return r;
    }

    public double restar(double a, double b) {
        double r = a - b;

        dao.guardarOperacion(
                new Operacion(
                        a,
                        b,
                        "RESTA",
                        r
                )
        );

        return r;
    }

    public double multiplicar(double a, double b) {
        double r = a * b;

        dao.guardarOperacion(
                new Operacion(
                        a,
                        b,
                        "MULTIPLICACION",
                        r
                )
        );
        return r;
    }

    public double dividir(double a, double b) {

        if (b == 0) {
            throw new ArithmeticException(
                    "NO se puede dividir entre cero."
            );
        }

        double r = a / b;
        dao.guardarOperacion(
                new Operacion(
                        a,
                        b,
                        "DIVISION",
                        r
                )
        );

        return r;
    }
}   
