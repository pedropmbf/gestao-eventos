package com.pedropmbf.gestao_eventos.controller;

import com.pedropmbf.gestao_eventos.entity.Evento;
import com.pedropmbf.gestao_eventos.util.CSVUtils;
import com.pedropmbf.gestao_eventos.util.GerenciadorDeEventos;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Controller {

    private GerenciadorDeEventos gerenciador = new GerenciadorDeEventos();
    private Stage stage;

    public void setScene(Scene scene, Stage stage) {
        this.stage = stage;
    }

    @FXML
    private TextField nomeEventoField, dataEventoField, localEventoField, capacidadeEventoField;
    @FXML
    private TextField nomeParticipanteField, nomeEventoParticipanteField, nomeEventoListarField;
    @FXML
    private ComboBox<String> categoriaIngressoComboBox;
    @FXML
    private CheckBox mostrarParticipantesCheckBox;
    @FXML
    private TextArea resultadoTextArea;
    @FXML
    private Label mensagemLabel;
    @FXML
    private VBox telaCadastro;
    @FXML
    private VBox telaListagem;

    @FXML
    public void initialize() {
        categoriaIngressoComboBox.setItems(FXCollections.observableArrayList("CAMAROTE", "FRONTSTAGE", "BACKSTAGE", "PREMIUM"));
    }

    @FXML
    public void cadastrarEvento() {
        String nome = nomeEventoField.getText();
        String data = dataEventoField.getText();
        String local = localEventoField.getText();
        int capacidade;
        try {
            capacidade = Integer.parseInt(capacidadeEventoField.getText());
        } catch (NumberFormatException e) {
            mensagemLabel.setText("Capacidade inválida.");
            return;
        }

        Evento evento = new Evento(nome, data, local, capacidade);
        gerenciador.cadastrarEvento(evento);
        mensagemLabel.setText("Evento cadastrado com sucesso!");

        nomeEventoField.clear();
        dataEventoField.clear();
        localEventoField.clear();
        capacidadeEventoField.clear();
    }

    @FXML
    public void inscreverParticipante() {
        String nomeParticipante = nomeParticipanteField.getText();
        String nomeEvento = nomeEventoParticipanteField.getText();
        String categoriaIngresso = categoriaIngressoComboBox.getValue();

        if (categoriaIngresso == null || nomeParticipante.isEmpty() || nomeEvento.isEmpty()) {
            mensagemLabel.setText("Por favor, preencha todos os campos.");
            return;
        }

        gerenciador.inscreverParticipanteNoEvento(nomeEvento, nomeParticipante, categoriaIngresso);
        mensagemLabel.setText("Participante inscrito!");

        nomeParticipanteField.clear();
        nomeEventoParticipanteField.clear();
        categoriaIngressoComboBox.getSelectionModel().clearSelection();
    }

    @FXML
    public void listarEvento() {
        String nomeEvento = nomeEventoListarField.getText();
        Evento evento = gerenciador.buscarEventoPorNome(nomeEvento);

        if (evento == null) {
            resultadoTextArea.setText("Evento não encontrado.");
            return;
        }

        boolean mostrarParticipantes = mostrarParticipantesCheckBox.isSelected();
        StringBuilder resultado = new StringBuilder();

        resultado.append("Evento: ").append(evento.getNome()).append("\n");
        resultado.append("Data: ").append(evento.getData()).append("\n");
        resultado.append("Local: ").append(evento.getLocal()).append("\n");
        resultado.append("Capacidade: ").append(evento.getCapacidadeMaxima()).append("\n");

        if (mostrarParticipantes) {
            resultado.append("Participantes:\n");
            evento.getParticipantes().forEach(p -> resultado.append(p.getNome()).append(" - ").append(p.getCategoriaIngresso()).append("\n"));
        } else {
            int camaroteCount = (int) evento.getParticipantes().stream().filter(p -> "CAMAROTE".equals(p.getCategoriaIngresso())).count();
            int frontstageCount = (int) evento.getParticipantes().stream().filter(p -> "FRONTSTAGE".equals(p.getCategoriaIngresso())).count();
            int backstageCount = (int) evento.getParticipantes().stream().filter(p -> "BACKSTAGE".equals(p.getCategoriaIngresso())).count();
            int premiumCount = (int) evento.getParticipantes().stream().filter(p -> "PREMIUM".equals(p.getCategoriaIngresso())).count();

            resultado.append("Número de participantes por categoria:\n");
            resultado.append("CAMAROTE: ").append(camaroteCount).append("\n");
            resultado.append("FRONTSTAGE: ").append(frontstageCount).append("\n");
            resultado.append("BACKSTAGE: ").append(backstageCount).append("\n");
            resultado.append("PREMIUM: ").append(premiumCount).append("\n");
        }

        resultadoTextArea.setText(resultado.toString());
    }

    @FXML
    public void listarTodosEventos() {
        StringBuilder resultado = new StringBuilder();

        if (gerenciador.getEventos().isEmpty()) {
            resultadoTextArea.setText("Nenhum evento cadastrado.");
            return;
        }

        resultado.append("Eventos cadastrados:\n");
        for (Evento evento : gerenciador.getEventos()) {
            resultado.append("Evento: ").append(evento.getNome()).append("\n")
                    .append("Data: ").append(evento.getData()).append("\n")
                    .append("Local: ").append(evento.getLocal()).append("\n")
                    .append("Capacidade: ").append(evento.getCapacidadeMaxima()).append("\n\n");
        }

        resultadoTextArea.setText(resultado.toString());
    }

    @FXML
    public void importarCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importar CSV de Eventos");
        File eventoFile = fileChooser.showOpenDialog(stage);

        fileChooser.setTitle("Importar CSV de Participantes");
        File participanteFile = fileChooser.showOpenDialog(stage);

        if (eventoFile != null && participanteFile != null) {
            try {
                gerenciador.setEventos(CSVUtils.importarEventosDeCSV(eventoFile.getAbsolutePath(), participanteFile.getAbsolutePath()));
                mensagemLabel.setText("Eventos importados com sucesso.");
            } catch (IOException e) {
                mensagemLabel.setText("Erro ao importar arquivos CSV.");
            }
        }
    }

    @FXML
    public void exportarCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar CSV de Eventos");
        fileChooser.setInitialFileName("eventos.csv");
        File eventoFile = fileChooser.showSaveDialog(stage);

        fileChooser.setTitle("Salvar CSV de Participantes");
        fileChooser.setInitialFileName("participantes.csv");
        File participanteFile = fileChooser.showSaveDialog(stage);

        if (eventoFile != null && participanteFile != null) {
            try {
                CSVUtils.exportarEventosParaCSV(gerenciador.getEventos(), eventoFile.getAbsolutePath(), participanteFile.getAbsolutePath());
                mensagemLabel.setText("Eventos exportados com sucesso.");
            } catch (IOException e) {
                mensagemLabel.setText("Erro ao exportar arquivos CSV.");
            }
        }
    }

    @FXML
    public void mostrarTelaCadastro() {
        telaCadastro.setVisible(true);
        telaListagem.setVisible(false);
        telaCadastro.toFront();
        telaListagem.toBack();
    }

    @FXML
    public void mostrarTelaListagem() {
        telaCadastro.setVisible(false);
        telaListagem.setVisible(true);
        telaListagem.toFront();
        telaCadastro.toBack();
    }

    @FXML
    public void minimizarJanela() {
        stage.setIconified(true);
    }

    @FXML
    public void fecharJanela() {
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
