package repository;

import factory.IngressoFactory;
import model.*;
import model.ingresso.Ingresso;
import service.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class IngressoRepository {
    private Connection connection;
    private UsuarioService usuarioService;
    private SessaoService sessaoService;
    private AreaService areaService;
    private IngressoFactory ingressoFactory;

    public IngressoRepository(Connection connection, UsuarioService usuarioService,
                              SessaoService sessaoService, AreaService areaService) {
        this.connection = connection;
        this.usuarioService = usuarioService;
        this.sessaoService = sessaoService;
        this.areaService = areaService;
        this.ingressoFactory = new IngressoFactory(areaService.getTodasAreas());
    }

    public Map<Integer, Ingresso> buscarTodosIngressos() throws SQLException {
        String sql = "SELECT id_ingresso, id_usuario, id_sessao, id_area, posicaoPoltrona, valor_pago FROM ingresso";
        Map<Integer, Ingresso> ingressosMap = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_ingresso");
                int idUsuario = rs.getInt("id_usuario");
                int idSessao = rs.getInt("id_sessao");
                int idArea = rs.getInt("id_area");
                int posicaoPoltrona = rs.getInt("posicaoPoltrona");

                Usuario usuario = usuarioService.getUsuarioPorId(idUsuario);
                Sessao sessao = sessaoService.getSessaoPorId(idSessao);
                Area area = areaService.getAreaPorId(idArea);

                Ingresso ingresso = ingressoFactory.criarIngresso(id, usuario, sessao, idArea, posicaoPoltrona);
                ingressosMap.put(id, ingresso);
            }
        }
        return ingressosMap;
    }

    public void salvarIngresso(Ingresso ingresso) throws SQLException {
        String sql = "INSERT INTO ingresso (id_usuario, id_sessao, id_area, posicaoPoltrona, valor_pago) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ingresso.getUsuario().getIdUsuario());
            ps.setInt(2, ingresso.getSessao().getIdSessao());
            ps.setInt(3, ingresso.getArea().getIdArea());
            ps.setInt(4, ingresso.getPosicaoPoltrona());
            ps.setInt(5, ingresso.getValorPago());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGerado = rs.getInt(1);
                    ingresso.setIdIngresso(idGerado);
                }
            }
        }
    }
}
