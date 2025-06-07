package model;

public class Area {
    private int idArea;
    private String nome;
    private int qtdPoltronas;
    private int preco;

    public Area(int idArea, String nome, int qtdPoltronas, int preco) {
        this.idArea = idArea;
        this.nome = nome;
        this.qtdPoltronas = qtdPoltronas;
        this.preco = preco;
    }

    public int getIdArea() {
        return idArea;
    }

    public String getNome() {
        return nome;
    }

    public int getQtdPoltronas() {
        return qtdPoltronas;
    }

    public int getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return "Area:\n" +
                "  ID: " + idArea + "\n" +
                "  Nome: " + nome + "\n" +
                "  Quantidade de Poltronas: " + qtdPoltronas + "\n" +
                "  Preço: " + preco;
    }
}
