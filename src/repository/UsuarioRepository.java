package repository;

import model.Usuario;
import service.EnderecoService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UsuarioRepository {
    private Connection connection;
    private EnderecoService enderecoService;

    public UsuarioRepository(Connection connection, EnderecoService enderecoService) {
        this.connection = connection;
        this.enderecoService = enderecoService;
    }

    public Map<Integer, Usuario> buscarTodosUsuarios() throws SQLException {
        String sql = "SELECT id_usuario, id_endereco, nome, cpf, telefone, data_nasc FROM usuario";
        Map<Integer, Usuario> usuariosMap = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_usuario");
                int idEndereco = rs.getInt("id_endereco");

                Usuario usuario = new Usuario(
                        id,
                        enderecoService.getEnderecoPorId(idEndereco),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getDate("data_nasc").toLocalDate()
                );

                usuariosMap.put(id, usuario);
            }
        }
        return usuariosMap;
    }
}
