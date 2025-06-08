package model;

public class Endereco {
    private int idEndereco;
    private String rua;
    private String quadra;
    private String lote;
    private String bairro;
    private String cidade;
    private String estado;

    public Endereco(int idEndereco, String rua, String quadra, String lote, String bairro, String cidade, String estado) {
        this.idEndereco = idEndereco;
        this.rua = rua;
        this.quadra = quadra;
        this.lote = lote;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public int getIdEndereco() {
        return idEndereco;
    }

    public String getRua() {
        return rua;
    }

    public String getQuadra() {
        return quadra;
    }

    public String getLote() {
        return lote;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    @Override
    public String toString() {
        return "    Rua: " + rua + "\n" +
                "    Quadra: " + quadra + "\n" +
                "    Lote: " + lote + "\n" +
                "    Bairro: " + bairro + "\n" +
                "    Cidade: " + cidade + "\n" +
                "    Estado: " + estado;
    }
}
