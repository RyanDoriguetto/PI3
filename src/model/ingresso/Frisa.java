package model.ingresso;

import model.*;

public class Frisa extends Ingresso {
    private int numeroFrisa;
    public Frisa(int idIngresso, Usuario usuario, Sessao sessao, Area area, int posicaoPoltrona, int numeroFrisa) {
        super(idIngresso, usuario, sessao, area, posicaoPoltrona);
        this.numeroFrisa = numeroFrisa;
        this.valorPago = area.getPreco();
    }

    public int getNumeroFrisa() {
        return numeroFrisa;
    }

    @Override
    public String toString() {
        return "Frisa #" + numeroFrisa + " - " + super.toString();
    }
}

