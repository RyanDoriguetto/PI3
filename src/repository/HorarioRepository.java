package repository;

import model.Horario;

import java.sql.*;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class HorarioRepository {
    private Connection connection;

    public HorarioRepository(Connection connection) {
        this.connection = connection;
    }

    public Map<Integer, Horario> buscarTodosHorarios() throws SQLException {
        String sql = "SELECT id_horario, turno, hora_inicio, hora_fim FROM horario";
        Map<Integer, Horario> horariosMap = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idHorario = rs.getInt("id_horario");
                String turno = rs.getString("turno");
                LocalTime horaInicio = rs.getTime("hora_inicio").toLocalTime();
                LocalTime horaFim = rs.getTime("hora_fim").toLocalTime();

                Horario horario = new Horario(idHorario, turno, horaInicio, horaFim);
                horariosMap.put(idHorario, horario);
            }
        }
        return horariosMap;
    }
}