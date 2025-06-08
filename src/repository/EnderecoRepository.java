package repository;

import model.Endereco;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class EnderecoRepository {
    private Connection connection;

    public EnderecoRepository(Connection connection) {
        this.connection = connection;
    }

    public Map<Integer, Endereco> buscarTodosEnderecos() throws SQLException {
        String sql = "SELECT * FROM endereco";
        Map<Integer, Endereco> enderecosMap = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_endereco");
                String rua = rs.getString("rua");
                String quadra = rs.getString("quadra");
                String lote = rs.getString("lote");
                String bairro = rs.getString("bairro");
                String cidade = rs.getString("cidade");
                String estado = rs.getString("estado");

                Endereco endereco = new Endereco(id, rua, quadra, lote, bairro, cidade, estado);
                enderecosMap.put(id, endereco);
            }
        }

        return enderecosMap;
    }

    public void salvarEndereco(Endereco endereco) throws SQLException {
        String sql = "INSERT INTO endereco (rua, quadra, lote, bairro, cidade, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, endereco.getRua());
            ps.setString(2, endereco.getQuadra());
            ps.setString(3, endereco.getLote());
            ps.setString(4, endereco.getBairro());
            ps.setString(5, endereco.getCidade());
            ps.setString(6, endereco.getEstado());
            ps.executeUpdate();
        }
    }
}
