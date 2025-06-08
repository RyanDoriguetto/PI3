package model.ingresso;

import model.*;

public class BalcaoNobre extends Ingresso{

    public BalcaoNobre(int idIngresso, Usuario usuario, Sessao sessao, Area area, int posicaoPoltrona) {
        super(idIngresso, usuario, sessao, area, posicaoPoltrona);
        this.valorPago = area.getPreco();
    }

    @Override
    public String toString() {
        return "Balcão Nobre - " + super.toString();
    }
}
