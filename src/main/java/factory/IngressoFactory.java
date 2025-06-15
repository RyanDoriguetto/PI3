package factory;

import model.Sessao;
import model.Usuario;
import model.ingresso.*;
import util.IngressoUtil;
import model.Area;

import java.util.Map;

public class IngressoFactory {
    private Map<Integer, Area> areasMap;

    public IngressoFactory(Map<Integer, Area> areasMap) {
        this.areasMap = areasMap;
    }

    // Método pra criar ingresso *novo* (id = 0)
    public Ingresso criarIngresso(Usuario usuario, Sessao sessao, int idArea, String assento) {
        return criarIngressoComId(0, usuario, sessao, idArea, assento);
    }

    // Método pra criar ingresso *existente* (com id do banco)
    public Ingresso criarIngressoComId(int idIngresso, Usuario usuario, Sessao sessao, int idArea, String assento) {
        Area area = areasMap.get(idArea);
        if (area == null) {
            throw new IllegalArgumentException("Área inválida");
        }

        switch (area.getNome()) {
            case "Plateia A":
                return new IngressoPlateiaA(idIngresso, usuario, sessao, area, assento);
            case "Plateia B":
                return new IngressoPlateiaB(idIngresso, usuario, sessao, area, assento);
            case "Balcao Nobre":
                return new IngressoBalcaoNobre(idIngresso, usuario, sessao, area, assento);
            case "Frisa":
                int numeroSubareaFrisa = IngressoUtil.calcularNumeroSubarea(area, assento);
                if (numeroSubareaFrisa < 1 || numeroSubareaFrisa > area.getQtdSubareas()) {
                    throw new IllegalArgumentException("Número de frisa inválido");
                }
                return new IngressoFrisa(idIngresso, usuario, sessao, area, assento, numeroSubareaFrisa);
            case "Camarote":
                int numeroSubareaCamarote = IngressoUtil.calcularNumeroSubarea(area, assento);
                if (numeroSubareaCamarote < 1 || numeroSubareaCamarote > area.getQtdSubareas()) {
                    throw new IllegalArgumentException("Número de camarote inválido");
                }
                return new IngressoCamarote(idIngresso, usuario, sessao, area, assento, numeroSubareaCamarote);
            default:
                throw new IllegalArgumentException("Área não reconhecida");
        }
    }
}
