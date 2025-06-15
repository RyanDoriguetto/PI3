package service;

import factory.IngressoFactory;
import model.Area;
import model.Usuario;
import model.Sessao;
import model.ingresso.Ingresso;
import repository.IngressoRepository;
import repository.Conexao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IngressoService {
    private IngressoRepository ingressoRepo;
    private Map<Integer, Ingresso> ingressosMap;
    private Map<Integer, Area> areasMap;

    public IngressoService(IngressoRepository ingressoRepo, Map<Integer, Area> areasMap) throws SQLException {
        this.ingressoRepo = ingressoRepo;
        this.areasMap = areasMap;
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

    public List<String> getPoltronasReservadas(int idSessao, int idArea) {
        return ingressoRepo.getPoltronasReservadas(idSessao, idArea);
    }


    public Ingresso criarIngresso(Usuario usuario, Sessao sessao, int idArea, String assento) throws SQLException {
        AreaService areaService = new AreaService(Conexao.getConexao());

        IngressoFactory factory = new IngressoFactory(areaService.getTodasAreas());
        Ingresso ingresso = factory.criarIngresso(usuario, sessao, idArea, assento);

        ingressoRepo.salvarIngresso(ingresso);

        ingressosMap.put(ingresso.getIdIngresso(), ingresso);

        return ingresso;
    }
}