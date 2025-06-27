module pl.umcs.oop.breakout {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.umcs.oop.breakout to javafx.fxml;
    exports pl.umcs.oop.breakout;
}