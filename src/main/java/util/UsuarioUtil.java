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

    public static String aplicarMascaraCPF(String texto) {
        if (texto == null) return "";

        String numeros = texto.replaceAll("[^\\d]", "");

        if (numeros.length() > 11) {
            numeros = numeros.substring(0, 11);
        }

        StringBuilder cpfFormatado = new StringBuilder();
        int len = numeros.length();

        for (int i = 0; i < len; i++) {
            cpfFormatado.append(numeros.charAt(i));
            if (i == 2 || i == 5) cpfFormatado.append('.');
            if (i == 8) cpfFormatado.append('-');
        }

        return cpfFormatado.toString();
    }

    public static String aplicarMascaraTelefone(String telefone) {
        String numeros = telefone.replaceAll("[^\\d]", "");
        if (numeros.length() > 11) {
            numeros = numeros.substring(0, 11);
        }
        StringBuilder sb = new StringBuilder();
        int len = numeros.length();
        for (int i = 0; i < len; i++) {
            if (i == 0) sb.append('(');
            sb.append(numeros.charAt(i));
            if (i == 1) sb.append(") ");
            else if (i == 2) sb.append(" ");
            else if (i == 6 && len > 10) sb.append('-');
            else if (i == 5 && len <= 10) sb.append('-');
        }
        return sb.toString();
    }

    public static String aplicarMascaraData(String valor) {
        String numeros = valor.replaceAll("[^0-9]", "");
        StringBuilder dataFormatada = new StringBuilder();

        for (int i = 0; i < numeros.length() && i < 8; i++) {
            dataFormatada.append(numeros.charAt(i));
            if (i == 1 || i == 3) {
                dataFormatada.append('/');
            }
        }

        return dataFormatada.toString();
    }
}
