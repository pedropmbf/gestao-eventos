package com.pedropmbf.gestao_eventos.util;

import com.pedropmbf.gestao_eventos.entity.Evento;
import com.pedropmbf.gestao_eventos.entity.Participante;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVUtils {
    private static final String CSV_DELIMITER = ",";

    // Método para exportar eventos e seus participantes para arquivos CSV
    public static void exportarEventosParaCSV(List<Evento> eventos, String arquivoEventos, String arquivoParticipantes) throws IOException {
        // Exportar eventos
        BufferedWriter writerEventos = new BufferedWriter(new FileWriter(arquivoEventos));
        for (Evento evento : eventos) {
            writerEventos.write(evento.getNome() + CSV_DELIMITER + evento.getData() + CSV_DELIMITER + evento.getLocal() + CSV_DELIMITER +
                    evento.getCapacidadeMaxima() + CSV_DELIMITER + evento.getVagasDisponiveis());
            writerEventos.newLine();
        }
        writerEventos.close();

        // Exportar participantes
        BufferedWriter writerParticipantes = new BufferedWriter(new FileWriter(arquivoParticipantes));
        for (Evento evento : eventos) {
            for (Participante participante : evento.getParticipantes()) {
                writerParticipantes.write(evento.getNome() + CSV_DELIMITER + participante.getNome() + CSV_DELIMITER + participante.getCategoriaIngresso());
                writerParticipantes.newLine();
            }
        }
        writerParticipantes.close();
    }

    // Método para importar eventos de um arquivo CSV
    public static List<Evento> importarEventosDeCSV(String arquivoEventos, String arquivoParticipantes) throws IOException {
        List<Evento> eventos = new ArrayList<>();

        // Importar eventos
        BufferedReader readerEventos = new BufferedReader(new FileReader(arquivoEventos));
        String linhaEvento;
        while ((linhaEvento = readerEventos.readLine()) != null) {
            // Ignorar linhas vazias ou mal formatadas
            if (linhaEvento.trim().isEmpty()) {
                continue;
            }

            String[] dados = linhaEvento.split(CSV_DELIMITER);

            // Certifique-se de que a linha tem o número correto de colunas
            if (dados.length < 4) {
                System.out.println("Linha mal formatada ou incompleta no arquivo de eventos: " + linhaEvento);
                continue; // Pula essa linha
            }

            try {
                Evento evento = new Evento(dados[0], dados[1], dados[2], Integer.parseInt(dados[3]));
                eventos.add(evento);
            } catch (NumberFormatException e) {
                System.out.println("Erro ao converter capacidade máxima para o evento: " + dados[0]);
            }
        }
        readerEventos.close();

        // Importar participantes e associar ao evento
        BufferedReader readerParticipantes = new BufferedReader(new FileReader(arquivoParticipantes));
        String linhaParticipante;
        while ((linhaParticipante = readerParticipantes.readLine()) != null) {
            // Ignorar linhas vazias ou mal formatadas
            if (linhaParticipante.trim().isEmpty()) {
                continue;
            }

            String[] dados = linhaParticipante.split(CSV_DELIMITER);

            // Certifique-se de que a linha tem o número correto de colunas
            if (dados.length < 3) {
                System.out.println("Linha mal formatada ou incompleta no arquivo de participantes: " + linhaParticipante);
                continue; // Pula essa linha
            }

            String nomeEvento = dados[0];
            String nomeParticipante = dados[1];
            String categoriaIngresso = dados[2];

            // Procurar o evento correspondente
            for (Evento evento : eventos) {
                if (evento.getNome().equals(nomeEvento)) {
                    Participante participante = new Participante(nomeParticipante, categoriaIngresso);
                    evento.inscreverParticipante(participante);
                    break;
                }
            }
        }
        readerParticipantes.close();

        return eventos;
    }
}
