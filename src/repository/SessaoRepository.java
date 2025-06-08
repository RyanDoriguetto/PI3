package repository;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SessaoRepository {
    private Connection connection;
    private PecaRepository pecaRepo;
    private HorarioRepository horarioRepo;

    public SessaoRepository(Connection connection, PecaRepository pecaRepo, HorarioRepository horarioRepo) {
        this.connection = connection;
        this.pecaRepo = pecaRepo;
        this.horarioRepo = horarioRepo;
    }

    public Map<Integer, Sessao> buscarTodasSessoes() throws SQLException {
        String sql = "SELECT id_sessao, id_peca, id_horario FROM sessao";
        Map<Integer, Sessao> sessoesMap = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idSessao = rs.getInt("id_sessao");
                int idPeca = rs.getInt("id_peca");
                int idHorario = rs.getInt("id_horario");

                Peca peca = pecaRepo.buscarPorId(idPeca);
                Horario horario = horarioRepo.buscarPorId(idHorario);

                Sessao sessao = new Sessao(idSessao, peca, horario);
                sessoesMap.put(idSessao, sessao);
            }
        }
        return sessoesMap;
    }
}
