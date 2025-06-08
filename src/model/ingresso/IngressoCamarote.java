package model.ingresso;

import model.*;

public class IngressoCamarote extends Ingresso {
    protected int numeroCamarote;
    public IngressoCamarote(int idIngresso, Usuario usuario, Sessao sessao, Area area, int posicaoPoltrona, int numeroCamarote) {
        super(idIngresso, usuario, sessao, area, posicaoPoltrona);
        this.numeroCamarote = numeroCamarote;
        this.valorPago = area.getPreco();
    }

    public int getNumeroCamarote() {
        return numeroCamarote;
    }

    @Override
    public String toString() {
        return "Camarote #" + numeroCamarote + " - " + super.toString();
    }
}
