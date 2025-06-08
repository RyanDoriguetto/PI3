package service;

import model.Endereco;
import model.Usuario;
import repository.UsuarioRepository;
import util.UsuarioUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
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

    public void salvarUsuario(Endereco endereco, String nome, String cpf, String telefone, LocalDate dataNasc){
        if(!UsuarioUtil.validarCpf(cpf)){
            throw new IllegalArgumentException("CPF Inválido");
        }

        Usuario usuario = new Usuario(0, endereco, nome, cpf, telefone, dataNasc);
        usuarioRepo.salvarUsuario(usuario);
    }
}
