package repository;

import model.Endereco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
