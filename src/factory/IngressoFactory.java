package factory;

import model.*;
import model.ingresso.*;

import java.util.Map;

public class IngressoFactory {
    private Map<Integer, Area> areasMap;

    public IngressoFactory(Map<Integer, Area> areasMap) {
        this.areasMap = areasMap;
    }

    public Ingresso criarIngresso(int idIngresso, Usuario usuario, Sessao sessao, int idArea, int posicaoPoltrona, int numeroSubarea) {
        Area area = areasMap.get(idArea);
        if (area == null) {
            throw new IllegalArgumentException("Área inválida");
        }

        switch (area.getNome()) {
            case "Plateia A":
                return new PlateiaA(idIngresso, usuario, sessao, area, posicaoPoltrona);
            case "Plateia B":
                return new PlateiaB(idIngresso, usuario, sessao, area, posicaoPoltrona);
            case "Balcao Nobre":
                return new BalcaoNobre(idIngresso, usuario, sessao, area, posicaoPoltrona);
            case "Frisa":
                if (numeroSubarea < 1 || numeroSubarea > area.getQtdSubareas()) {
                    throw new IllegalArgumentException("Número de frisa inválido");
                }
                return new Frisa(idIngresso, usuario, sessao, area, posicaoPoltrona, numeroSubarea);
            case "Camarote":
                if (numeroSubarea < 1 || numeroSubarea > area.getQtdSubareas()) {
                    throw new IllegalArgumentException("Número de camarote inválido");
                }
                return new Camarote(idIngresso, usuario, sessao, area, posicaoPoltrona, numeroSubarea);
            default:
                throw new IllegalArgumentException("Área não reconhecida");
        }
    }
}