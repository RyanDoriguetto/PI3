package model;

public class Sessao {
    private int idSessao;
    private Peca peca;
    private Horario horario;

    public Sessao(int idSessao, Peca peca, Horario horario) {
        this.idSessao = idSessao;
        this.peca = peca;
        this.horario = horario;
    }

    public int getIdSessao() {
        return idSessao;
    }

    public Peca getPeca() {
        return peca;
    }

    public Horario getHorario() {
        return horario;
    }

    @Override
    public String toString() {
        return "Sessao:\n" +
                "  ID: " + idSessao + "\n" +
                "  Peça: " + peca.toString().replaceAll("(?m)^", "    ") + "\n" +
                "  Horário: " + horario.toString().replaceAll("(?m)^", "    ");
    }
}