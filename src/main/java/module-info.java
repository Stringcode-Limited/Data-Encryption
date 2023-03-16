module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;
    requires AnimateFX;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo.controller;
    opens com.example.demo.controller to javafx.fxml;
//    opens com.example.demo.model to javafx.fxml;
//    exports com.example.demo.model;
    exports com.example.demo;
}