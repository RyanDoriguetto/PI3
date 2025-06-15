package model.ingresso;

import model.*;

public abstract class Ingresso {
    protected int idIngresso;
    protected Usuario usuario;
    protected Sessao sessao;
    protected String posicaoPoltrona;
    protected int valorPago;
    protected Area area;

    public Ingresso(int idIngresso, Usuario usuario, Sessao sessao, Area area, String posicaoPoltrona) {
        this.idIngresso = idIngresso;
        this.usuario = usuario;
        this.sessao = sessao;
        this.area = area;
        this.posicaoPoltrona = posicaoPoltrona;
    }

    public Ingresso(Usuario usuario, Sessao sessao, Area area, String posicaoPoltrona) {
        this.usuario = usuario;
        this.sessao = sessao;
        this.area = area;
        this.posicaoPoltrona = posicaoPoltrona;
    }

    public Area getArea() {
        return area;
    }

    public int getIdIngresso() {
        return idIngresso;
    }

    public int getValorPago() {
        return valorPago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public String getPosicaoPoltrona() {
        return posicaoPoltrona;
    }

    public void setIdIngresso(int idIngresso) {
        this.idIngresso = idIngresso;
    }

    @Override
    public String toString() {
        return "ID: " + idIngresso + ", Usuário: " + usuario.getNome() +
                ", Sessão: " + sessao.toString().replaceAll("(?m)^", "    ") + "\n" +
                "Área: " + area.getNome() + ", Poltrona: " + posicaoPoltrona +
                ", Valor: " + valorPago;
    }

}
