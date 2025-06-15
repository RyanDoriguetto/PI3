package model.ingresso;

import model.*;

public class IngressoPlateiaB extends Ingresso {
    public IngressoPlateiaB(int idIngresso, Usuario usuario, Sessao sessao, Area area, String posicaoPoltrona) {
        super(idIngresso, usuario, sessao, area, posicaoPoltrona);
        this.valorPago = area.getPreco();
    }

    @Override
    public String toString() {
        return "Plateia B - " + super.toString();
    }
}
