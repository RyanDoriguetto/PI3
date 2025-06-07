package model;

import java.time.LocalDate;

public class Usuario {
    private int idUsuario;
    private Endereco endereco;
    private String nome;
    private String cpf;
    private String telefone;
    private LocalDate dataNasc;

    public Usuario(int idUsuario, Endereco endereco, String nome, String cpf, String telefone, LocalDate dataNasc){
        this.idUsuario = idUsuario;
        this.endereco = endereco;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.dataNasc = dataNasc;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    @Override
    public String toString() {
        return "Usuário:\n" +
                "  ID: " + idUsuario + "\n" +
                "  Nome: " + nome + "\n" +
                "  CPF: " + cpf + "\n" +
                "  Telefone: " + telefone + "\n" +
                "  Data de Nascimento: " + dataNasc + "\n" +
                "  Endereço:\n" + endereco.toString().replaceAll("(?m)^", "    ");
    }
}
