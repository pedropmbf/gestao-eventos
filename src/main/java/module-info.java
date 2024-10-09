module com.pedropmbf.gestao_eventos {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.pedropmbf.gestao_eventos to javafx.fxml;
    exports com.pedropmbf.gestao_eventos;
    exports com.pedropmbf.gestao_eventos.entity;
    opens com.pedropmbf.gestao_eventos.entity to javafx.fxml;
    exports com.pedropmbf.gestao_eventos.controller;
    opens com.pedropmbf.gestao_eventos.controller to javafx.fxml;
    exports com.pedropmbf.gestao_eventos.util;
    opens com.pedropmbf.gestao_eventos.util to javafx.fxml;
}