package dao;

import conexion.ConexionOracle;
import modelo.Operacion;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CalculadoraDAO {

    public void guardarOperacion(Operacion op) {

        String sql = """
                INSERT INTO OPERACIONES_OP
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
                    "OPERACION almacenada correctamente ."
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
