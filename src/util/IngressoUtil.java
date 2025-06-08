package util;

import model.Area;

public class IngressoUtil {
    public static int calcularNumeroSubarea(Area area, int posicaoPoltrona) {
        int assentosPorSubarea = area.getAssentosPorSubarea();
        return (assentosPorSubarea > 0)
                ? ((posicaoPoltrona - 1) / assentosPorSubarea) + 1
                : 0;
    }
}
