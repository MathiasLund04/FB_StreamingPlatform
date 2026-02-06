package com.example.fb_streamingplatform;

import Infrastructure.DbConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        var url = getClass().getResource("/main-view.fxml");

        System.out.println("FXML url = " + url);

        FXMLLoader Loader = new FXMLLoader(url);
        Scene scene = new Scene(Loader.load(), 800, 600);

        stage.setTitle("Streaming Platform");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
