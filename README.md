# CalculadoraOracle
## Proyecto Java 21 + IntelliJ IDEA + Oracle Database 19c:

<img width="1024" height="1024" alt="image" src="https://github.com/user-attachments/assets/f4ce4da5-ca8f-460d-bb4b-c767a08636c5" />  

```
Calculadora de consola desarrollada con **Java 21**, utilizando **Oracle Database 19c** como motor de persistencia y siguiendo una **arquitectura por capas** .

---

# Tecnologías utilizadas

- Java 21
- IntelliJ IDEA
- Oracle Database 19c
- JDBC (ojdbc11.jar)
- Programa de consola
- Arquitectura por capas

---

# Estructura del proyecto

```text
CalculadoraOracle
│
├── src
│
├── principal
│      Main.java
│
├── conexion
│      ConexionOracle.java
│
├── dao
│      CalculadoraDAO.java
│
├── modelo
│      Operacion.java
│
└── servicio
       CalculadoraService.java
```

---

# Clase modelo

## modelo/Operacion.java

```java
package modelo;

public class Operacion {

    private int id;
    private double numero1;
    private double numero2;
    private String operacion;
    private double resultado;

    public Operacion() {
    }

    public Operacion(
            double numero1,
            double numero2,
            String operacion,
            double resultado) {

        this.numero1 = numero1;
        this.numero2 = numero2;
        this.operacion = operacion;
        this.resultado = resultado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getNumero1() {
        return numero1;
    }

    public void setNumero1(double numero1) {
        this.numero1 = numero1;
    }

    public double getNumero2() {
        return numero2;
    }

    public void setNumero2(double numero2) {
        this.numero2 = numero2;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public double getResultado() {
        return resultado;
    }

    public void setResultado(double resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {

        return id +
                " | " +
                numero1 +
                " | " +
                numero2 +
                " | " +
                operacion +
                " | " +
                resultado;
    }

}
```

---

# Clase de conexión

## conexion/ConexionOracle.java

```java
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionOracle {

    private static final String URL =
            "jdbc:oracle:thin:@localhost:1521:XE";

    private static final String USER = "SYSTEM";

    private static final String PASSWORD = "oracle";

    public static Connection conectar() {

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

            return DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

        } catch (Exception e) {

            System.out.println("Error conexión");

            e.printStackTrace();

            return null;
        }

    }

}
```

---

# Capa DAO

## dao/CalculadoraDAO.java

```java
package dao;

import conexion.ConexionOracle;
import modelo.Operacion;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CalculadoraDAO {

    public void guardarOperacion(Operacion op) {

        String sql = """
                INSERT INTO OPERACIONES
                (
                    NUMERO1,
                    NUMERO2,
                    OPERACION,
                    RESULTADO
                )
                VALUES
                (
                    ?,?,?,?
                )
                """;

        try (

                Connection cn =
                        ConexionOracle.conectar();

                PreparedStatement ps =
                        cn.prepareStatement(sql)

        ) {

            ps.setDouble(1, op.getNumero1());
            ps.setDouble(2, op.getNumero2());
            ps.setString(3, op.getOperacion());
            ps.setDouble(4, op.getResultado());

            ps.executeUpdate();

            System.out.println(
                    "Operación almacenada correctamente."
            );

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
```

---

# Capa de servicio

## servicio/CalculadoraService.java

```java
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
                    "No se puede dividir entre cero."
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
```

---

# Clase principal

## principal/Main.java

```java
package principal;

import servicio.CalculadoraService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        CalculadoraService calculadora =
                new CalculadoraService();

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

                System.out.print("Número 1: ");
                double a = sc.nextDouble();

                System.out.print("Número 2: ");
                double b = sc.nextDouble();

                try {

                    double resultado = switch (opcion) {

                        case 1 -> calculadora.sumar(a, b);
                        case 2 -> calculadora.restar(a, b);
                        case 3 -> calculadora.multiplicar(a, b);
                        default -> calculadora.dividir(a, b);

                    };

                    System.out.println(
                            "Resultado = " + resultado
                    );

                } catch (Exception e) {

                    System.out.println(
                            e.getMessage()
                    );

                }

            }

        } while (opcion != 5);

        System.out.println(
                "Programa finalizado."
        );

    }

}
```

---

# Script Oracle Database 19c

```sql
CREATE TABLE OPERACIONES
(
    ID NUMBER GENERATED BY DEFAULT AS IDENTITY,

    NUMERO1 NUMBER(18,2),

    NUMERO2 NUMBER(18,2),

    OPERACION VARCHAR2(30),

    RESULTADO NUMBER(18,2),

    FECHA_REGISTRO DATE DEFAULT SYSDATE,

    CONSTRAINT PK_OPERACIONES
        PRIMARY KEY(ID)
);
```

---

# Dependencia JDBC Oracle

Agregar al proyecto el controlador JDBC compatible con Java 21.

```text
ojdbc11.jar
```

Pasos:

1. Descargar `ojdbc11.jar`.
2. Abrir IntelliJ IDEA.
3. File → Project Structure.
4. Modules → Dependencies.
5. Agregar el archivo JAR.
6. Aplicar los cambios.

---

# Flujo del programa

```text
Usuario
   │
   ▼
Main.java
   │
   ▼
CalculadoraService
   │
   ▼
CalculadoraDAO
   │
   ▼
ConexionOracle
   │
   ▼
Oracle Database 19c
```

---

# Funcionamiento

1. El usuario ejecuta `Main`.
2. Se presenta el menú de opciones.
3. El usuario selecciona una operación matemática.
4. Se ingresan dos números.
5. `CalculadoraService` realiza el cálculo.
6. Se crea un objeto `Operacion`.
7. `CalculadoraDAO` registra la operación en la tabla `OPERACIONES`.
8. Oracle almacena la información.
9. El resultado se muestra en la consola.

---

# Arquitectura por capas

## modelo

Representa las entidades del sistema.

```text
Operacion
```

---

## conexion

Administra la conexión JDBC con Oracle Database.

```text
ConexionOracle
```

---

## dao

Gestiona el acceso y la persistencia de los datos.

```text
CalculadoraDAO
```

---

## servicio

Contiene la lógica de negocio de la calculadora.

```text
CalculadoraService
```

---

## principal

Controla la interacción con el usuario mediante consola y coordina el flujo de ejecución.

```text
Main
```

---

# Características del proyecto

- Arquitectura por capas.
- Programa de consola.
- Compatible con Java 21.
- Persistencia de operaciones en Oracle Database 19c.
- Uso de JDBC.
- Código organizado y modular.
- Registro automático de cada operación matemática realizada.
- Manejo de excepciones para división entre cero.
- Fácil de extender con nuevas operaciones matemáticas.

---

# Resultado

Cada operación ejecutada desde la consola queda almacenada automáticamente en la tabla `OPERACIONES`, permitiendo mantener un historial de cálculos realizados mientras se conserva una separación clara entre la interfaz de usuario, la lógica de negocio y el acceso a datos .
:. . / .  
