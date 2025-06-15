package model.ingresso;

import model.*;

public class IngressoFrisa extends Ingresso {
    private int numeroFrisa;
    public IngressoFrisa(int idIngresso, Usuario usuario, Sessao sessao, Area area, String posicaoPoltrona, int numeroFrisa) {
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

