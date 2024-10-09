package com.pedropmbf.gestao_eventos.util;

import com.pedropmbf.gestao_eventos.entity.Evento;
import com.pedropmbf.gestao_eventos.entity.Participante;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorDeEventos {
    private List<Evento> eventos = new ArrayList<>();
    private static final String ARQUIVO_EVENTOS = "eventos.csv";
    private static final String ARQUIVO_PARTICIPANTES = "participantes.csv";

    // Método para inicializar eventos a partir de um arquivo CSV
    public GerenciadorDeEventos() {
        try {
            eventos = CSVUtils.importarEventosDeCSV(ARQUIVO_EVENTOS, ARQUIVO_PARTICIPANTES);
            System.out.println("Eventos importados com sucesso.");
        } catch (IOException e) {
            System.out.println("Não foi possível importar os eventos: " + e.getMessage());
        }
    }

    // Método para cadastrar evento
    public void cadastrarEvento(Evento evento) {
        if (eventos.size() < 10) {
            eventos.add(evento);
            System.out.println("Evento cadastrado com sucesso: " + evento);

            // Exportar os eventos atualizados para o CSV
            try {
                CSVUtils.exportarEventosParaCSV(eventos, ARQUIVO_EVENTOS, ARQUIVO_PARTICIPANTES);
            } catch (IOException e) {
                System.out.println("Erro ao exportar eventos: " + e.getMessage());
            }
        } else {
            System.out.println("Limite máximo de eventos atingido.");
        }
    }

    // Método para inscrever participante
    public void inscreverParticipanteNoEvento(String nomeEvento, String nomeParticipante, String categoriaIngresso) {
        Evento evento = buscarEventoPorNome(nomeEvento);
        if (evento != null) {
            Participante participante = new Participante(nomeParticipante, categoriaIngresso);
            if (evento.inscreverParticipante(participante)) {
                System.out.println("Inscrição realizada com sucesso: " + participante);

                // Exportar os eventos e participantes atualizados para o CSV
                try {
                    CSVUtils.exportarEventosParaCSV(eventos, ARQUIVO_EVENTOS, ARQUIVO_PARTICIPANTES);
                } catch (IOException e) {
                    System.out.println("Erro ao exportar participantes: " + e.getMessage());
                }
            } else {
                System.out.println("O evento está esgotado.");
            }
        } else {
            System.out.println("Evento não encontrado.");
        }
    }

    // Método para buscar um evento por nome
    public Evento buscarEventoPorNome(String nome) {
        for (Evento evento : eventos) {
            if (evento.getNome().equalsIgnoreCase(nome)) {
                return evento;
            }
        }
        return null;
    }

    // Método para listar eventos
    public void listarEventos() {
        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento cadastrado.");
        } else {
            for (Evento evento : eventos) {
                System.out.println(evento);
            }
        }
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }
}
