package repository;

import model.*;
import service.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class SessaoRepository {
    private Connection connection;
    private PecaService pecaService;
    private HorarioService horarioService;

    public SessaoRepository(Connection connection, PecaService pecaService, HorarioService horarioService) {
        this.connection = connection;
        this.pecaService = pecaService;
        this.horarioService = horarioService;
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

                Peca peca = pecaService.getPecaPorId(idPeca);
                Horario horario = horarioService.getHorarioPorId(idHorario);

                Sessao sessao = new Sessao(idSessao, peca, horario);
                sessoesMap.put(idSessao, sessao);
            }
        }
        return sessoesMap;
    }

    public Sessao buscarSessaoPorHorarioEPeca(int idHorario, int idPeca) {
        String sql = "SELECT * FROM sessao WHERE id_horario = ? AND id_peca = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idHorario);
            stmt.setInt(2, idPeca);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) { // tem que chamar isso antes de ler os dados
                    System.out.println("Sessao encontrada: id_sessao=" + rs.getInt("id_sessao") +
                            ", id_peca=" + rs.getInt("id_peca") +
                            ", id_horario=" + rs.getInt("id_horario"));
                    return new Sessao(
                            rs.getInt("id_sessao"),
                            pecaService.getPecaPorId(rs.getInt("id_peca")),
                            horarioService.getHorarioPorId(rs.getInt("id_horario"))
                    );
                } else {
                    System.out.println("Nenhuma sessão encontrada para id_horario=" + idHorario + " e id_peca=" + idPeca);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
