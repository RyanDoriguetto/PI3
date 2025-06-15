package repository;

import repository.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstatisticaRepository {

    public PecaVenda getPecaComMaisIngressos() throws SQLException {
        String sql = "SELECT p.id_peca, p.nome, COUNT(i.id_ingresso) AS total_ingressos " +
                "FROM ingresso i " +
                "JOIN sessao s ON i.id_sessao = s.id_sessao " +
                "JOIN peca p ON s.id_peca = p.id_peca " +
                "GROUP BY p.id_peca, p.nome " +
                "ORDER BY total_ingressos DESC " +
                "LIMIT 1";

        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new PecaVenda(rs.getInt("id_peca"), rs.getString("nome"), rs.getInt("total_ingressos"));
            }
            return null;
        }
    }

    public PecaVenda getPecaComMenosIngressos() throws SQLException {
        String sql = "SELECT p.id_peca, p.nome, COUNT(i.id_ingresso) AS total_ingressos " +
                "FROM ingresso i " +
                "JOIN sessao s ON i.id_sessao = s.id_sessao " +
                "JOIN peca p ON s.id_peca = p.id_peca " +
                "GROUP BY p.id_peca, p.nome " +
                "ORDER BY total_ingressos ASC " +
                "LIMIT 1";

        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new PecaVenda(rs.getInt("id_peca"), rs.getString("nome"), rs.getInt("total_ingressos"));
            }
            return null;
        }
    }

    public SessaoOcupacao getSessaoComMaiorOcupacao() throws SQLException {
        String sql = "WITH total_assentos_teatro AS (" +
                "  SELECT SUM(qtd_poltronas + (qtd_subareas * assentos_por_subarea)) AS total_assentos FROM area" +
                ") " +
                "SELECT s.id_sessao, p.nome AS nome_peca, COUNT(i.id_ingresso) AS ingressos_vendidos, " +
                "       (COUNT(i.id_ingresso) * 100.0 / t.total_assentos) AS ocupacao_percentual " +
                "FROM sessao s " +
                "JOIN peca p ON s.id_peca = p.id_peca " +
                "LEFT JOIN ingresso i ON s.id_sessao = i.id_sessao " +
                "CROSS JOIN total_assentos_teatro t " +
                "GROUP BY s.id_sessao, p.nome, t.total_assentos " +
                "ORDER BY ocupacao_percentual DESC " +
                "LIMIT 1";

        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new SessaoOcupacao(rs.getInt("id_sessao"), rs.getString("nome_peca"),
                        rs.getInt("ingressos_vendidos"), rs.getDouble("ocupacao_percentual"));
            }
            return null;
        }
    }

    public SessaoOcupacao getSessaoComMenorOcupacao() throws SQLException {
        String sql = "WITH total_assentos_teatro AS (" +
                "  SELECT SUM(qtd_poltronas + (qtd_subareas * assentos_por_subarea)) AS total_assentos FROM area" +
                ") " +
                "SELECT s.id_sessao, p.nome AS nome_peca, COUNT(i.id_ingresso) AS ingressos_vendidos, " +
                "       (COUNT(i.id_ingresso) * 100.0 / t.total_assentos) AS ocupacao_percentual " +
                "FROM sessao s " +
                "JOIN peca p ON s.id_peca = p.id_peca " +
                "LEFT JOIN ingresso i ON s.id_sessao = i.id_sessao " +
                "CROSS JOIN total_assentos_teatro t " +
                "GROUP BY s.id_sessao, p.nome, t.total_assentos " +
                "ORDER BY ocupacao_percentual ASC " +
                "LIMIT 1";

        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new SessaoOcupacao(rs.getInt("id_sessao"), rs.getString("nome_peca"),
                        rs.getInt("ingressos_vendidos"), rs.getDouble("ocupacao_percentual"));
            }
            return null;
        }
    }

    public LucroSessao getSessaoMaisLucrativa() throws SQLException {
        String sql = "SELECT s.id_sessao, p.nome AS nome_peca, COALESCE(SUM(i.valor_pago), 0) AS total_lucro " +
                "FROM sessao s " +
                "JOIN peca p ON s.id_peca = p.id_peca " +
                "LEFT JOIN ingresso i ON s.id_sessao = i.id_sessao " +
                "GROUP BY s.id_sessao, p.nome " +
                "ORDER BY total_lucro DESC " +
                "LIMIT 1";

        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new LucroSessao(rs.getInt("id_sessao"), rs.getString("nome_peca"), rs.getDouble("total_lucro"));
            }
            return null;
        }
    }

    public LucroSessao getSessaoMenosLucrativa() throws SQLException {
        String sql = "SELECT s.id_sessao, p.nome AS nome_peca, COALESCE(SUM(i.valor_pago), 0) AS total_lucro " +
                "FROM sessao s " +
                "JOIN peca p ON s.id_peca = p.id_peca " +
                "LEFT JOIN ingresso i ON s.id_sessao = i.id_sessao " +
                "GROUP BY s.id_sessao, p.nome " +
                "ORDER BY total_lucro ASC " +
                "LIMIT 1";

        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new LucroSessao(rs.getInt("id_sessao"), rs.getString("nome_peca"), rs.getDouble("total_lucro"));
            }
            return null;
        }
    }

    public List<FaturamentoMedio> getFaturamentoMedioPorPeca() throws SQLException {
        String sql = "SELECT p.id_peca, p.nome, " +
                "       COALESCE(SUM(i.valor_pago), 0) / COUNT(DISTINCT s.id_sessao) AS faturamento_medio " +
                "FROM peca p " +
                "JOIN sessao s ON p.id_peca = s.id_peca " +
                "LEFT JOIN ingresso i ON s.id_sessao = i.id_sessao " +
                "GROUP BY p.id_peca, p.nome";

        List<FaturamentoMedio> lista = new ArrayList<>();
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new FaturamentoMedio(
                        rs.getInt("id_peca"),
                        rs.getString("nome"),
                        rs.getDouble("faturamento_medio")
                ));
            }
        }
        return lista;
    }

    public static class PecaVenda {
        public int idPeca;
        public String nomePeca;
        public int totalIngressos;

        public PecaVenda(int idPeca, String nomePeca, int totalIngressos) {
            this.idPeca = idPeca;
            this.nomePeca = nomePeca;
            this.totalIngressos = totalIngressos;
        }
    }

    public static class SessaoOcupacao {
        public int idSessao;
        public String nomePeca;
        public int ingressosVendidos;
        public double ocupacaoPercentual;

        public SessaoOcupacao(int idSessao, String nomePeca, int ingressosVendidos, double ocupacaoPercentual) {
            this.idSessao = idSessao;
            this.nomePeca = nomePeca;
            this.ingressosVendidos = ingressosVendidos;
            this.ocupacaoPercentual = ocupacaoPercentual;
        }
    }

    public static class LucroSessao {
        public int idSessao;
        public String nomePeca;
        public double totalLucro;

        public LucroSessao(int idSessao, String nomePeca, double totalLucro) {
            this.idSessao = idSessao;
            this.nomePeca = nomePeca;
            this.totalLucro = totalLucro;
        }
    }

    public static class FaturamentoMedio {
        public int idPeca;
        public String nomePeca;
        public double faturamentoMedio;

        public FaturamentoMedio(int idPeca, String nomePeca, double faturamentoMedio) {
            this.idPeca = idPeca;
            this.nomePeca = nomePeca;
            this.faturamentoMedio = faturamentoMedio;
        }
    }
}
