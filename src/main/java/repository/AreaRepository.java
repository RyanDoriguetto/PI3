package repository;

import model.Area;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class AreaRepository {
    private Connection connection;

    public AreaRepository(Connection connection) {
        this.connection = connection;
    }

    public Map<Integer, Area> buscarTodasAreas() throws SQLException {
        String sql = "SELECT id_area, nome, qtd_poltronas, preco, qtd_subareas, assentos_por_subarea FROM area";
        Map<Integer, Area> areasMap = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_area");
                String nome = rs.getString("nome");
                int qtdPoltronas = rs.getInt("qtd_poltronas");
                int preco = rs.getInt("preco");
                int qtdSubareas = rs.getInt("qtd_subareas");
                int assentosPorSubarea = rs.getInt("assentos_por_subarea");

                Area area = new Area(id, nome, qtdPoltronas, preco, qtdSubareas, assentosPorSubarea);
                areasMap.put(id, area);
            }
        }
        return areasMap;
    }
}
