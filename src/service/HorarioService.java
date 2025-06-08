package service;

import model.Area;
import model.Horario;
import repository.HorarioRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class HorarioService {
    private HorarioRepository horarioRepo;
    private Map<Integer, Horario> horariosMap;

    public HorarioService(Connection connection) {
        this.horarioRepo = new HorarioRepository(connection);
        carregarHorarios();
    }

    private void carregarHorarios() {
        try {
            horariosMap = horarioRepo.buscarTodosHorarios();
        } catch (SQLException e) {
            e.printStackTrace();
            horariosMap = new HashMap<>();
        }
    }

    public Horario getHorarioPorId(int id) {
        return horariosMap.get(id);
    }

    public Map<Integer, Area> getTodosHorarios() {
        return horariosMap;
    }
}
