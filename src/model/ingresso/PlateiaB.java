package model.ingresso;

import model.*;

public class PlateiaB extends Ingresso {
    public PlateiaB(int idIngresso, Usuario usuario, Sessao sessao, Area area, int posicaoPoltrona) {
        super(idIngresso, usuario, sessao, area, posicaoPoltrona);
        this.valorPago = area.getPreco();
    }

    @Override
    public String toString() {
        return "Plateia B - " + super.toString();
    }
}
