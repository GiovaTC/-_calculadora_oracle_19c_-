package dao;

import conexion.ConexionOracle;
import modelo.Operacion;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CalculadoraDAO {

    public void guardarOperacion(Operacion op) {

        String sql = """
                INSERT INTO OPERACIONES
                """;
    }
}
