package com.example.guichatclient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HelloController {
    //@FXML
   // private Label welcomeText;

    @FXML
    private TextField inputField;
    @FXML
    private TextArea outputArea;
    @FXML
    private ListView onlineList;

    @FXML
    private TextField loginField;

    @FXML
    private Button loginButton;


    private ClientReciver clientReciver;

    private ObservableList<String> onlineUsers;

    @FXML
    public void initialize() {
        onlineUsers = FXCollections.observableArrayList();
        onlineList.setItems(onlineUsers);
    }

    @FXML
    protected void onSendButtonClick() {
        String message = inputField.getText();
        //String output = outputArea.getText()+"\n"+message;
        //outputArea.setText(output+"\n"); //zakomentować w zad 3
        //outputArea.setScrollTop(Double.MAX_VALUE);
        inputField.setText("");
        clientReciver.broadcast(message);
    }

    @FXML
    protected void onLoginButtonClick(){
        clientReciver.login(loginField.getText());
        loginButton.setDisable(true);
    }

    public void setClientReciver(ClientReciver clientReciver) {
        this.clientReciver = clientReciver;
    }

    public void broadcast(String msg){
        outputArea.appendText(msg+"\n");
    }

    public void online(String msg){
        onlineUsers.clear();
        String[] users = msg.split(" ");
        onlineUsers.addAll(users);
    }
}