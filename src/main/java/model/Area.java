package model;

public class Area {
    private int idArea;
    private String nome;
    private int qtdPoltronas;
    private int preco;
    private int qtdSubareas;
    private int assentosPorSubarea;

    public Area(int idArea, String nome, int qtdPoltronas, int preco, int qtdSubareas, int assentosPorSubarea) {
        this.idArea = idArea;
        this.nome = nome;
        this.qtdPoltronas = qtdPoltronas;
        this.preco = preco;
        this.qtdSubareas = qtdSubareas;
        this.assentosPorSubarea = assentosPorSubarea;
    }

    public int getQtdSubareas() {
        return qtdSubareas;
    }

    public int getAssentosPorSubarea() {
        return assentosPorSubarea;
    }

    public String getNome() {
        return nome;
    }

    public int getIdArea() {
        return idArea;
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
