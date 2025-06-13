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
    private TextField inputTextField;
    @FXML
    private TextArea outputTextArea;

    @FXML
    protected void onSendButtonClick() {
       outputTextArea.setText(outputTextArea.getText() +"\n" +inputTextField.getText());
       inputTextField.clear();
    }


}