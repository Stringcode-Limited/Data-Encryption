package com.example.demo.controller;

import com.example.demo.HelloApplication;
import com.example.demo.service.DesEncryption;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController extends Thread implements Initializable {
    final FileChooser fileChooser = new FileChooser();

    @FXML
    private TextArea plainTextArea;

    @FXML
    private TextArea encryptedTextArea;

    @FXML
    private TextField keyTextField;

    @FXML
    private Button encrypBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        encrypBtn.setDisable(true);

        plainTextArea.setOnKeyPressed(e ->{
            if(e.getText().isEmpty() && keyTextField.getText().isEmpty()) encrypBtn.setDisable(true);
            else encrypBtn.setDisable(false);
        });

        keyTextField.setOnKeyPressed(e ->{
           if(e.getText().isEmpty() && plainTextArea.getText().isEmpty()) encrypBtn.setDisable(true);
           else encrypBtn.setDisable(false);
        });


        encryptedTextArea.setOnKeyPressed(e ->{
            System.out.println(e.getText());
        });
    }
//   FUNCTION TO SHOW FILE-CHOOSER

    public File displayFileChooser(String title) {
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Images", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);
        return file;
    }

    public void switchSence(Event event, String fxml) {

        try {
            FXMLLoader pane = new FXMLLoader(HelloApplication.class.getResource(fxml));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(pane.load(), 520, 610);
            stage.setScene(scene);
            if (fxml.equals("sign-in.fxml") || fxml.equals("sign-out.fxml")) {
                stage.setResizable(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //   METHOD TO SWITCH TO DARK MODE
//    public void switchTheme(boolean theme) {
//        if (theme) {
//            appContainer.getStylesheets().add(1, String.valueOf(HelloApplication.class.getResource("styles/dark-mode.css")));
//        } else {
//            appContainer.getStylesheets().remove(1);
//        }
//    }

    //  ALGORITHM TO SWITCH PANE ON CLICK OF EACH ITEM IN THE SIDEBAR
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


    // FUNCTION TO ADD DATA TO DEPARTMENT CHOICEBOX
    // FUNCTION TO DISPLAY MODAL
//    public void displayModal(String displayType) {
//        if (displayType != null) {
//            modalContainer.setVisible(true);
//            if (displayType.equals("FORM")) {
//                confirmationModal.setMaxHeight(0);
//                confirmationModal.setMinHeight(0);
//                confirmationModal.setVisible(false);
//                new BounceInDown(addCategoryModalForm).play();
//                addDepartmentField.requestFocus();
//            }
//        } else {
//            confirmationModal.setMaxHeight(250);
//            confirmationModal.setMinHeight(250);
//            addCategoryModalForm.setMaxHeight(250);
//            addCategoryModalForm.setMinHeight(250);
//            confirmationModal.setVisible(true);
//            addCategoryModalForm.setVisible(true);
//            modalContainer.setVisible(false);
//        }
//    }

//   FUNCTION TO DISPLAY TOAST

//    public void displayModal(String heading, String subHeading, String type) {
//        modalHeading.setText(heading);
//        modalSubHeading.setText(subHeading);
//        addCategoryModalForm.setMaxHeight(0);
//        addCategoryModalForm.setMinHeight(0);
//        addCategoryModalForm.setVisible(false);
//        new BounceInDown(confirmationModal).play();
//        modalContainer.setVisible(true);
//        if (type == null) {
//            modalYesOption.setMaxWidth(0);
//            modalYesOption.setPadding(new Insets(0, 0, 0, 0));
//            modalYesOption.setVisible(false);
//        } else {
//            modalYesOption.setMaxWidth(150);
//            modalYesOption.setVisible(true);
//            modalYesOption.setText(type.toLowerCase());
//        }
//    }

    public void toast(String title, String message) {
        Notifications notificationsBuilder = Notifications.create()
                .title(title)
                .text(message)
                .graphic(null)
                .hideAfter(Duration.seconds(3))
                .position(Pos.TOP_LEFT);
        notificationsBuilder.showConfirm();
    }


//    @FXML
//    protected void onModalClose() {
//        displayModal(null);
//    }

    @FXML
    protected  void onEncrypt() {
        String message = plainTextArea.getText();

        String keyString = keyTextField.getText();

        String encrypted = new DesEncryption().encrypt(message, keyString);
        if(!encrypted.isEmpty()) toast("Success","Encrypted successfully");
        else toast("Failed","An error occurred");

        encryptedTextArea.setText(encrypted);
    }

    @FXML
    protected  void onDecrypt(){
        System.out.println("world");
    }




//  DATABASE MANIPULATIONS

}
    //  RETRIEVE LOCATIONS FROM DATABASE
