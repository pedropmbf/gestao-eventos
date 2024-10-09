package com.pedropmbf.gestao_eventos;

import com.pedropmbf.gestao_eventos.controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carregar o FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pedropmbf/gestao_eventos/main.fxml"));
        Parent root = loader.load();

        // Criar a cena sem aplicar CSS externo
        Scene scene = new Scene(root);

        // Definir o controlador
        Controller controller = loader.getController();
        controller.setScene(scene, primaryStage);

        // Configurações do stage
        primaryStage.setTitle("Gestão de Eventos");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
