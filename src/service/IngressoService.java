package service;

import model.Sessao;
import model.ingresso.Ingresso;
import repository.IngressoRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IngressoService {
    private Map<Integer, Ingresso> ingressosMap;

    public IngressoService(IngressoRepository ingressoRepo) throws SQLException {
        this.ingressosMap = ingressoRepo.buscarTodosIngressos();
    }

    public Ingresso buscarPorId(int id) {
        return ingressosMap.get(id);
    }

    public List<Ingresso> listarTodos() {
        return new ArrayList<>(ingressosMap.values());
    }

    public Map<Sessao, List<Ingresso>> agruparPorSessao() {
        Map<Sessao, List<Ingresso>> ingressosAgrupadosPorSessao = new HashMap<>();
        for (Ingresso ingresso : ingressosMap.values()) {
            ingressosAgrupadosPorSessao
                    .computeIfAbsent(ingresso.getSessao(), k -> new ArrayList<>())
                    .add(ingresso);
        }
        return ingressosAgrupadosPorSessao;
    }
}
