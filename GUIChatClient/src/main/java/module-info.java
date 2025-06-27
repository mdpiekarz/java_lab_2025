module com.example.guiclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.guichatclient to javafx.fxml;
    exports com.example.guichatclient;
}