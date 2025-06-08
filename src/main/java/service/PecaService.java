package service;

import model.Peca;
import repository.PecaRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PecaService {
    private PecaRepository pecaRepo;
    private Map<Integer, Peca> pecasMap;

    public PecaService(Connection connection) {
        this.pecaRepo = new PecaRepository(connection);
        carregarPecas();
    }

    private void carregarPecas() {
        try {
            pecasMap = pecaRepo.buscarTodasPecas();
        } catch (SQLException e) {
            e.printStackTrace();
            pecasMap = new HashMap<>();
        }
    }

    public Peca getPecaPorId(int id) {
        return pecasMap.get(id);
    }

    public Map<Integer, Peca> getTodasPecas() {
        return pecasMap;
    }
}
