package util;

public class UsuarioUtil {
    public static boolean validarCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) return false;
        if (cpf.chars().distinct().count() == 1) return false;

        int soma1 = 0, soma2 = 0;
        for (int i = 0; i < 9; i++) {
            int digito = cpf.charAt(i) - '0';
            soma1 += digito * (10 - i);
            soma2 += digito * (11 - i);
        }

        int digito1 = 11 - (soma1 % 11);
        digito1 = (digito1 >= 10) ? 0 : digito1;
        soma2 += digito1 * 2;
        int digito2 = 11 - (soma2 % 11);
        digito2 = (digito2 >= 10) ? 0 : digito2;

        return digito1 == (cpf.charAt(9) - '0') && digito2 == (cpf.charAt(10) - '0');
    }
}
