package model;

import java.time.LocalTime;

public class Horario {
    private int idHorario;
    private String turno;
    private LocalTime horaInicio;
    private LocalTime horaFim;

    public Horario(String turno, LocalTime horaInicio, LocalTime horaFim) {
        this.idHorario = idHorario;
        this.turno = turno;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public String getTurno() {
        return turno;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    @Override
    public String toString() {
        return "Horario:\n" +
                "  ID: " + idHorario + "\n" +
                "  Turno: " + turno + "\n" +
                "  Início: " + horaInicio + "\n" +
                "  Fim: " + horaFim;
    }
}
