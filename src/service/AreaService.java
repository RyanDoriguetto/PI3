package service;

import model.Area;
import repository.AreaRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AreaService {
    private AreaRepository areaRepo;
    private Map<Integer, Area> areasMap;

    public AreaService(Connection connection) {
        this.areaRepo = new AreaRepository(connection);
        carregarAreas();
    }

    private void carregarAreas() {
        try {
            areasMap = areaRepo.buscarTodasAreas();
        } catch (SQLException e) {
            e.printStackTrace();
            areasMap = new HashMap<>();
        }
    }

    public Area getAreaPorId(int id) {
        return areasMap.get(id);
    }

    public Map<Integer, Area> getTodasAreas() {
        return areasMap;
    }
}
