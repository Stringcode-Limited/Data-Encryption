package com.example.demo.controller;

import com.example.demo.HelloApplication;
import com.example.demo.controller.validator.Validator;
import com.example.demo.model.database.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static com.example.demo.enums.Validation.NAME;


public class LoginController implements Initializable {

    private Stage stage;

    private Scene scene;
    @FXML
    private VBox loginAlert;

    AdminController adminController = new AdminController();

    @FXML

//    LOGIN USERNAME TEXT FIELD

    private TextField loginusernamField;

    @FXML

//    LOGIN USERNAME TEXT FIELD

    private PasswordField loginPasswordField;



    @FXML

//   ERROR LABELS

    private Label loginUsernameError,loginPasswordError,alertText;

    @FXML
    private Button loginBtn;

    DatabaseConnection databaseConnection = new DatabaseConnection();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginBtn.setOnAction(e ->{
            onLogin(e);
        });
    }

    protected  boolean onLogin(ActionEvent event){
        switchSence(event,"screens/admin-dashboard.fxml");
        Validator validator = new Validator();
        validator.validateTextFields(loginUsernameError,loginusernamField,NAME.toString(),"Username",null);
        validator.validatePasswordFields(loginPasswordError,loginPasswordField,null,"Password",null);

        return false;
    }

    public  void switchSence(ActionEvent event, String fxml){

        try{

            FXMLLoader pane = new FXMLLoader(HelloApplication.class.getResource(fxml));
            stage =(Stage)((Node)event.getSource()).getScene().getWindow();
            System.out.println(fxml);
            Scene scene = new Scene(pane.load(), 1200, 700);
            System.out.println(fxml);
            AdminController adminController1 = pane.getController();
            stage.setScene(scene);
                stage.setResizable(true);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
