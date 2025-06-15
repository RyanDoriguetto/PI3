package util;

import model.Area;

public class IngressoUtil {
    public static int calcularNumeroSubarea(Area area, String assento) {
        if (assento == null || assento.length() < 2) throw new IllegalArgumentException("Assento inválido");
        char c = assento.charAt(1);
        int num = Character.getNumericValue(c);
        if (num < 1 || num > area.getQtdSubareas()) throw new IllegalArgumentException("Subárea inválida");
        return num;
    }
}
