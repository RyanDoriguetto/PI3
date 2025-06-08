package service;

import model.ingresso.Ingresso;
import model.Sessao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IngressoService {
    Map<Sessao, List<Ingresso>> ingressosPorSessao = new HashMap<>();
}
