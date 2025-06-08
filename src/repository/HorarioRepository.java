package repository;

import model.Horario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public class HorarioRepository {
    private Connection connection;

    public HorarioRepository(Connection connection) {
        this.connection = connection;
    }

    public Horario buscarPorId(int idHorario) throws SQLException {
        String sql = "SELECT id_horario, turno, hora_inicio, hora_fim FROM horario WHERE id_horario = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idHorario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String turno = rs.getString("turno");
                    LocalTime horaInicio = rs.getTime("hora_inicio").toLocalTime();
                    LocalTime horaFim = rs.getTime("hora_fim").toLocalTime();
                    return new Horario(idHorario, turno, horaInicio, horaFim);
                }
            }
        }
        return null;
    }
}