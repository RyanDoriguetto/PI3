package repository;

import java.sql.*;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/pi2025";
    private static final String USUARIO = "root";
    private static final String SENHA = "1234";

    public static Connection getConexao(){
        try{
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }
}