package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(HelloApplication.class.getResource("screens/sign-in.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 610);
        stage.setTitle("Sign in - Encrypto");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) throws InterruptedException {
        launch();
    }
}