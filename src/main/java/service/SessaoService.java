package service;

import model.Sessao;
import repository.SessaoRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SessaoService {
    private SessaoRepository sessaoRepo;
    private Map<Integer, Sessao> sessoesMap;

    public SessaoService(Connection connection, PecaService pecaService, HorarioService horarioService) {
        this.sessaoRepo = new SessaoRepository(connection, pecaService, horarioService);
        carregarSessoes();
    }

    private void carregarSessoes() {
        try {
            sessoesMap = sessaoRepo.buscarTodasSessoes();
        } catch (SQLException e) {
            e.printStackTrace();
            sessoesMap = new HashMap<>();
        }
    }

    public Sessao getSessaoPorId(int id) {
        return sessoesMap.get(id);
    }

    public Map<Integer, Sessao> getTodasSessoes() {
        return sessoesMap;
    }
}
