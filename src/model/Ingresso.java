package model;

public class Ingresso {
    private int idIngresso;
    private Usuario usuario;
    private Sessao sessao;
    private Area area;
    private int posicaoPoltrona;
    private int valorPago;

    public Ingresso(int idIngresso, Usuario usuario, Sessao sessao, Area area, int posicaoPoltrona, int valorPago) {
        this.idIngresso = idIngresso;
        this.usuario = usuario;
        this.sessao = sessao;
        this.area = area;
        this.posicaoPoltrona = posicaoPoltrona;
        this.valorPago = valorPago;
    }

    public int getIdIngresso() {
        return idIngresso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public Area getArea() {
        return area;
    }

    public int getPosicaoPoltrona() {
        return posicaoPoltrona;
    }

    public int getValorPago() {
        return valorPago;
    }

    @Override
    public String toString() {
        return "Ingresso:\n" +
                "  ID: " + idIngresso + "\n" +
                "  Usuário: " + usuario.getNome() + "\n" +
                "  Sessão: " + sessao.toString().replaceAll("(?m)^", "    ") + "\n" +
                "  Área: " + area.getNome() + "\n" +
                "  Poltrona: " + posicaoPoltrona + "\n" +
                "  Valor Pago: " + valorPago;
    }
}
