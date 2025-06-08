package factory;

import model.*;
import model.ingresso.*;
import util.IngressoUtil;

import java.util.Map;

public class IngressoFactory {
    private Map<Integer, Area> areasMap;

    public IngressoFactory(Map<Integer, Area> areasMap) {
        this.areasMap = areasMap;
    }

    public Ingresso criarIngresso(int idIngresso, Usuario usuario, Sessao sessao, int idArea, int posicaoPoltrona) {
        Area area = areasMap.get(idArea);
        if (area == null) {
            throw new IllegalArgumentException("Área inválida");
        }

        switch (area.getNome()) {
            case "Plateia A":
                return new IngressoPlateiaA(idIngresso, usuario, sessao, area, posicaoPoltrona);
            case "Plateia B":
                return new IngressoPlateiaB(idIngresso, usuario, sessao, area, posicaoPoltrona);
            case "Balcao Nobre":
                return new IngressoBalcaoNobre(idIngresso, usuario, sessao, area, posicaoPoltrona);
            case "Frisa":
                int numeroSubareaFrisa = IngressoUtil.calcularNumeroSubarea(area, posicaoPoltrona);

                if (numeroSubareaFrisa < 1 || numeroSubareaFrisa > area.getQtdSubareas()) {
                    throw new IllegalArgumentException("Número de frisa inválido");
                }

                return new IngressoFrisa(idIngresso, usuario, sessao, area, posicaoPoltrona, numeroSubareaFrisa);
            case "Camarote":
                int numeroSubareaCamarote = IngressoUtil.calcularNumeroSubarea(area, posicaoPoltrona);

                if (numeroSubareaCamarote < 1 || numeroSubareaCamarote > area.getQtdSubareas()) {
                    throw new IllegalArgumentException("Número de camarote inválido");
                }

                return new IngressoCamarote(idIngresso, usuario, sessao, area, posicaoPoltrona, numeroSubareaCamarote);
            default:
                throw new IllegalArgumentException("Área não reconhecida");
        }
    }
}