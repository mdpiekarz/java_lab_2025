package com.example.guichatclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    private static HelloController controller;
    static ClientReciver reciverThread;

    @Override
    public void start(Stage stage) throws IOException {
        reciverThread = new ClientReciver("localhost",5000);
        reciverThread.start();

//        FXMLLoader fxmlLoginLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
//        Scene scene = new Scene(fxmlLoginLoader.load(), 320, 240);
//        stage.setTitle("Login");
//        stage.setScene(scene);
//        controller = fxmlLoginLoader.getController();
//        controller.setClientReciver(reciverThread);
//        reciverThread.setLoginController(controller);
//        stage.show();


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 640);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        controller = fxmlLoader.getController();
        controller.setClientReciver(reciverThread);
        reciverThread.setController(controller);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}