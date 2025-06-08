package model.ingresso;

import model.*;

public class PlateiaA extends Ingresso{

    public PlateiaA(int idIngresso, Usuario usuario, Sessao sessao, Area area, int posicaoPoltrona) {
        super(idIngresso, usuario, sessao, area, posicaoPoltrona);
        this.valorPago = area.getPreco();
    }

    @Override
    public String toString() {
        return "Plateia A - " + super.toString();
    }
}
