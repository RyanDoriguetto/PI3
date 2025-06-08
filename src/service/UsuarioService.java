package service;

import model.Usuario;
import repository.UsuarioRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UsuarioService {
    private UsuarioRepository usuarioRepo;
    private Map<Integer, Usuario> usuariosMap;

    public UsuarioService(Connection connection, EnderecoService enderecoService) {
        this.usuarioRepo = new UsuarioRepository(connection, enderecoService);
        carregarUsuarios();
    }

    private void carregarUsuarios() {
        try {
            usuariosMap = usuarioRepo.buscarTodosUsuarios();
        } catch (SQLException e) {
            e.printStackTrace();
            usuariosMap = new HashMap<>();
        }
    }

    public Usuario getUsuarioPorId(int id) {
        return usuariosMap.get(id);
    }

    public Map<Integer, Usuario> getTodosUsuarios() {
        return usuariosMap;
    }
}
