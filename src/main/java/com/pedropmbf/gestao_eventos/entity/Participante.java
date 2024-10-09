package com.pedropmbf.gestao_eventos.entity;

public class Participante {
    private String nome;
    private String categoriaIngresso;

    public Participante(String nome, String categoriaIngresso) {
        this.nome = nome;
        this.categoriaIngresso = categoriaIngresso;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoriaIngresso() {
        return categoriaIngresso;
    }

    @Override
    public String toString() {
        return "Participante{" +
                "nome='" + nome + '\'' +
                ", categoriaIngresso='" + categoriaIngresso + '\'' +
                '}';
    }
}
