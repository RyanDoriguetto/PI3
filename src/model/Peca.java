package model;

public class Peca {
    private int idPeca;
    private String nome;

    public Peca(int idPeca, String nome){
        this.idPeca = idPeca;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public int getIdPeca() {
        return idPeca;
    }

    @Override
    public String toString() {
        return "Peça:\n" +
                "  ID: " + idPeca + "\n" +
                "  Nome: " + nome;
    }
}
