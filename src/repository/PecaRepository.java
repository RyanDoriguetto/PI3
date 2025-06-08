package repository;

import model.Peca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PecaRepository {
    private Connection connection;

    public PecaRepository(Connection connection) {
        this.connection = connection;
    }

    public Peca buscarPorId(int idPeca) throws SQLException {
        String sql = "SELECT id_peca, nome FROM peca WHERE id_peca = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPeca);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("nome");
                    return new Peca(idPeca, nome);
                }
            }
        }
        return null;
    }
}
