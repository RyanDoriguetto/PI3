package model.ingresso;

import model.*;

public class IngressoBalcaoNobre extends Ingresso{

    public IngressoBalcaoNobre(int idIngresso, Usuario usuario, Sessao sessao, Area area, int posicaoPoltrona) {
        super(idIngresso, usuario, sessao, area, posicaoPoltrona);
        this.valorPago = area.getPreco();
    }

    @Override
    public String toString() {
        return "Balcão Nobre - " + super.toString();
    }
}
