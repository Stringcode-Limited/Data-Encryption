package com.example.demo.controller;

import com.example.demo.HelloApplication;
import com.example.demo.controller.validator.Validator;
import com.example.demo.model.Session;
import com.example.demo.model.database.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.demo.enums.Validation.CONFIRMATION;
import static com.example.demo.enums.Validation.EMAIL;

public class SignUpController implements Initializable {
    private Stage stage;

    @FXML

//  ERROR LABELS
    private Label emailError,passwordError,confirmPasswordError;

    @FXML

//  TEXT FIELDS
    private TextField emailField;


    @FXML

//  PASSWORD FIELDS
    private PasswordField passwordField,confirmPasswordField;

    @FXML
    private Label signInText;

    @FXML
    private Button signUpBtn;


    DatabaseConnection databaseConnection  = new DatabaseConnection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signUpBtn.setOnAction(e -> onSignUp(e));
        signInText.setOnMouseClicked(e -> switchSence(e,"screens/sign-in.fxml","sign-in", null,"Sign in - ENCRYPTO"));
    }

    public void toast(String title, String message) {
        Notifications notificationsBuilder = Notifications.create()
                .title(title)
                .text(message)
                .graphic(null)
                .hideAfter(Duration.seconds(3))
                .position(Pos.TOP_LEFT);

        notificationsBuilder.showConfirm();
    }

    public boolean addUser(Session session){
        if(databaseConnection.dbConnect()){
            String ADD_ADMIN = "INSERT INTO ORG (email,password,isAdmin) VALUES(?,?,?)";
            int upd =0;
            try{
                PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(ADD_ADMIN);
                preparedStatement.setString(1,session.getEmail());
                preparedStatement.setString(2,session.getPassword());
                preparedStatement.setBoolean(3,false);
                upd = preparedStatement.executeUpdate();
                if(upd != 0) return true;
            }catch (SQLException | SecurityException se){
                se.printStackTrace();
            }
        }
        return false;
    }

    public  void switchSence(Event event, String fxml, String window, Session session, String title){
        try{

            FXMLLoader pane = new FXMLLoader(HelloApplication.class.getResource(fxml));
            stage =(Stage)((Node)event.getSource()).getScene().getWindow();
            System.out.println(fxml);
            Scene scene = new Scene(pane.load());
            stage.setTitle(title);
            if(window.equals("user"))  {
                UserController controller = pane.getController();
                if(session != null) controller.getLoggedInUser(session);
                stage.setTitle("User - Encrypto");


            }
            if(window.equals("sign-in"))  {
                LoginController controller = pane.getController();
                stage.setTitle("Sign in - Encrypto");

            }
            stage.setScene(scene);
            stage.setResizable(true);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    protected void onSignUp(Event event){
        Session session = new Session();
        Validator validator = new Validator();

        session.setEmail(validator.validateTextFields(emailError,emailField,EMAIL.toString(),"Email",null));
        session.setPassword(validator.validatePasswordFields(passwordError,passwordField,null,"Password",null));
        session.setPassword(validator.validatePasswordFields(confirmPasswordError,confirmPasswordField,passwordField,"Password",CONFIRMATION.toString()));

        if(session.isLoggedIn()){
            boolean added = addUser(session);
            if(added) switchSence(event,"screens/user-dashboard.fxml","user",session,"User - ENCRYPTO");
            else toast("failed","an error occurred");
        }
    }
}
