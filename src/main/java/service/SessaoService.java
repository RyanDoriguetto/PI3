package service;

import model.Sessao;
import repository.SessaoRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Sessao buscarSessaoPorHorarioEPeca(int idHorario, int idPeca) {
        return sessaoRepo.buscarSessaoPorHorarioEPeca(idHorario, idPeca);
    }

    public Sessao getSessaoPorId(int id) {
        return sessoesMap.get(id);
    }

    public Map<Integer, Sessao> getTodasSessoes() {
        return sessoesMap;
    }

    public List<Integer> getHorariosPorPeca(int idPeca) {
        return sessoesMap.values().stream()
                .filter(s -> s.getPeca().getIdPeca() == idPeca)
                .map(s -> s.getHorario().getIdHorario())
                .distinct()
                .toList();
    }

    public Map<Integer, List<Integer>> getPecasHorariosMap() {
        return sessoesMap.values().stream()
                .collect(Collectors.groupingBy(
                        s -> s.getPeca().getIdPeca(),
                        Collectors.mapping(s -> s.getHorario().getIdHorario(), Collectors.toList())
                ));
    }
}
