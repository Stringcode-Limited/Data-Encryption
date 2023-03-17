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
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.demo.enums.Validation.EMAIL;
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

    private Label loginUsernameError,loginPasswordError,signUpText;

    @FXML
    private Button loginBtn;

    DatabaseConnection databaseConnection = new DatabaseConnection();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginAlert.setVisible(false);

        signUpText.setOnMouseClicked(e -> switchSence(e,"screens/sign-up.fxml","none", null,"Sign up - ENCRYPTO"));

        loginBtn.setOnAction(e ->{
            onLogin(e);
        });
    }

    protected  void onLogin(ActionEvent event){
        Validator validator = new Validator();
        Session session = new Session();
        session.setEmail(validator.validateTextFields(loginUsernameError,loginusernamField,EMAIL.toString(),"Email",null));
        session.setPassword(validator.validatePasswordFields(loginPasswordError,loginPasswordField,null,"Password",null));

        if(session.isLoggedIn()){
            String role = confirmAuth(session);
            if(role.equals("none")) toast("Failed","Incorrect email or password");
            if(role.equals("admin"))   switchSence(event,"screens/admin-dashboard.fxml",role,session,null);
            if(role.equals("user"))   switchSence(event,"screens/user-dashboard.fxml",role,session, null);
        }
    }

    public  void switchSence(Event event, String fxml, String window, Session session, String title){

        try{

            FXMLLoader pane = new FXMLLoader(HelloApplication.class.getResource(fxml));
            stage =(Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            System.out.println(fxml);
            Scene scene = new Scene(pane.load());
            if(window.equals("admin")) {
                AdminController controller = pane.getController();
                if(session != null) controller.getLoggedInUser(session);
                stage.setTitle("Admin - Encrypto");

            }
            if(window.equals("user"))  {
                UserController controller = pane.getController();
                if(session != null) controller.getLoggedInUser(session);
                stage.setTitle("User - Encrypto");

            }
            if(window.equals("sign-up"))  {
                SignUpController controller = pane.getController();
                 scene = new Scene(pane.load());
                stage.setTitle("Sign up - Encrypto");
            }

            stage.setScene(scene);
                stage.setResizable(true);
        }catch (IOException e){
            e.printStackTrace();
        }
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

    public String confirmAuth (Session session){
        if(databaseConnection.dbConnect()){
            String CONFIRM_USERNAME = "SELECT * FROM ORG WHERE  email = ? AND password = ?";
            try{
                PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(CONFIRM_USERNAME);
                preparedStatement.setString(1,session.getEmail());
                preparedStatement.setString(2,session.getPassword());
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()) {
                    session.setEmail(resultSet.getString("email"));
                    session.setAdmin(resultSet.getBoolean("isAdmin"));
                    if(session.isAdmin()) return "admin";
                    else return "user";
                }
            }catch (SecurityException | SQLException se){
                se.printStackTrace();
            }
        }
        return "none";
    }
}
