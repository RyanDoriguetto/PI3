package service;

import model.Endereco;
import repository.EnderecoRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class EnderecoService {
    private EnderecoRepository enderecoRepo;
    private Map<Integer, Endereco> enderecosMap;

    public EnderecoService(Connection connection) {
        this.enderecoRepo = new EnderecoRepository(connection);
        carregarEnderecos();
    }

    private void carregarEnderecos() {
        try {
            enderecosMap = enderecoRepo.buscarTodosEnderecos();
        } catch (SQLException e) {
            e.printStackTrace();
            enderecosMap = new HashMap<>();
        }
    }

    public Endereco getEnderecoPorId(int id) {
        return enderecosMap.get(id);
    }

    public Map<Integer, Endereco> getTodosEnderecos() {
        return enderecosMap;
    }
}
