package repository;

import model.Peca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PecaRepository {
    private Connection connection;

    public PecaRepository(Connection connection) {
        this.connection = connection;
    }

    public Map<Integer, Peca> buscarTodasPecas() throws SQLException {
        String sql = "SELECT id_peca, nome FROM peca";
        Map<Integer, Peca> pecasMap = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idPeca = rs.getInt("id_peca");
                String nome = rs.getString("nome");

                Peca peca = new Peca(idPeca, nome);
                pecasMap.put(idPeca, peca);
            }
        }
        return pecasMap;
    }
}
