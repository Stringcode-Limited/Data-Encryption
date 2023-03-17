package com.example.demo.controller;

import com.example.demo.HelloApplication;
import com.example.demo.model.Session;
import com.example.demo.service.DesDecryption;
import com.example.demo.service.DesEncryption;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserController extends Thread implements Initializable {
    final FileChooser fileChooser = new FileChooser();

    @FXML
    private TextArea plainTextArea;

    @FXML
    private TextArea encryptedTextArea;

    @FXML
    private TextField keyTextField;

    @FXML
    private Button decryptBtn, logoutBtn;

    @FXML
    private Label userEmailLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        decryptBtn.setDisable(true);

        plainTextArea.setOnKeyPressed(e ->{
            if(e.getText().isEmpty() && keyTextField.getText().isEmpty()) decryptBtn.setDisable(true);
            else decryptBtn.setDisable(false);
        });

        keyTextField.setOnKeyPressed(e ->{
            if(e.getText().isEmpty() && plainTextArea.getText().isEmpty()) decryptBtn.setDisable(true);
            else decryptBtn.setDisable(false);
        });


        encryptedTextArea.setOnKeyPressed(e ->{
            System.out.println(e.getText());
        });
        logoutBtn.setOnAction(e -> switchSence(e, "screens/sign-in.fxml","Sign in - Encrypto"));
    }

    public File displayFileChooser(String title) {
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);
        return file;
    }

    public void switchSence(Event event, String fxml,String title) {

        try {
            FXMLLoader pane = new FXMLLoader(HelloApplication.class.getResource(fxml));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(pane.load(), 520, 610);
            stage.setScene(scene);
            stage.setTitle(title);
            if (fxml.equals("sign-in.fxml") || fxml.equals("sign-out.fxml")) {
                stage.setResizable(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void switchActiveLink(HBox[] links, HBox activeLink) {

        if (activeLink == null) {
            for (HBox link : links) {
                link.getStyleClass().remove("active");
            }
        } else {
            for (HBox link : links) {
                link.getStyleClass().remove("active");
            }
            activeLink.getStyleClass().add("active");
        }
    }

    public void getLoggedInUser(Session session){
        userEmailLabel.setText(session.getEmail());
    }


    public void toast(String title, String message,Pos postion) {
        Notifications notificationsBuilder = Notifications.create()
                .title(title)
                .text(message)
                .graphic(null)
                .hideAfter(Duration.seconds(3))
                .position(postion);
        notificationsBuilder.showConfirm();
    }

    @FXML
    protected void  onImportFile(){
        importFile();
    }

    public  void importFile() {
        fileChooser.setTitle("Choose a .txt file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
                encryptedTextArea.setText(sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    @FXML
    protected  void onDecrypt() {
        String message = encryptedTextArea.getText();

        String keyString = keyTextField.getText();

        if(keyTextField.getText().length() != 8 || keyTextField.getText().isEmpty()) toast("Failed","Key must be 8 characters", Pos.CENTER);
        if(encryptedTextArea.getText().isEmpty()) toast("Failed","No encrypted text", Pos.CENTER);

        if(!encryptedTextArea.getText().isEmpty() && !keyTextField.getText().isEmpty()) {

            String decrypted = new DesDecryption().decrypt(message.trim(), keyString);

            if (decrypted != null) {
                toast("Success", "Decrypted successfully", Pos.TOP_LEFT);
                plainTextArea.setText(decrypted);
            } else toast("Failed", "Incorrect key", Pos.TOP_LEFT);
        }
    }


}