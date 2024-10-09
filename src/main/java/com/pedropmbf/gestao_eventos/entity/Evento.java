package com.pedropmbf.gestao_eventos.entity;

import java.util.ArrayList;
import java.util.List;

public class Evento {
    private String nome;
    private String data;
    private String local;
    private int capacidadeMaxima;
    private int vagasDisponiveis;
    private List<Participante> participantes;

    public Evento(String nome, String data, String local, int capacidadeMaxima) {
        this.nome = nome;
        this.data = data;
        this.local = local;
        this.capacidadeMaxima = capacidadeMaxima;
        this.vagasDisponiveis = capacidadeMaxima; // Inicialmente, as vagas disponíveis são iguais à capacidade máxima
        this.participantes = new ArrayList<>();
    }

    public int getVagasDisponiveis() {
        return vagasDisponiveis;
    }

    public String getNome() {
        return nome;
    }

    public String getData() {
        return data;
    }

    public String getLocal() {
        return local;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public List<Participante> getParticipantes() {
        return participantes;
    }

    public boolean inscreverParticipante(Participante participante) {
        if (vagasDisponiveis > 0) {
            participantes.add(participante);
            vagasDisponiveis--;
            return true;
        } else {
            return false; // Evento esgotado
        }
    }

    @Override
    public String toString() {
        return "Evento{" +
                "nome='" + nome + '\'' +
                ", data='" + data + '\'' +
                ", local='" + local + '\'' +
                ", capacidadeMaxima=" + capacidadeMaxima +
                ", vagasDisponiveis=" + vagasDisponiveis +
                '}';
    }
}
