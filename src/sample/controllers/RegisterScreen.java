package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.services.User;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegisterScreen extends Database {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    public TextField firstnameField;

    @FXML
    private PasswordField passwordRigField;

    @FXML
    private Button RegistrationButton;

    @FXML
    public TextField lastNamefield;

    @FXML
    private PasswordField conformPasswordField;

    @FXML
    private TextField UsernameField;

    @FXML
    private Button backButton;
    @FXML
    private Label errorLabel;

    @FXML
    void initialize() {
        backButton.setOnAction(actionEvent -> {
            backButton.getScene().getWindow().hide();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/fxml/sample.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root=loader.getRoot();
            Stage stage=new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        });

        RegistrationButton.setOnAction(actionEvent -> {
            if ((UsernameField.getText().length()>=7)&&
                    (Objects.equals(passwordRigField.getText(), conformPasswordField.getText()))
                   ) {

                RegisterNewUser();
                RegistrationButton.getScene().getWindow().hide();
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/fxml/sample.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root=loader.getRoot();
                Stage stage=new Stage();
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.show();


            }else{
                errorLabel.setText("Incorrect Values");
            }
        });
    }

   public void RegisterNewUser() {
        Database dbHandler=new Database();

        String firstName=firstnameField.getText();
        String LastName=lastNamefield.getText();
        String userName=UsernameField.getText();
        String password=passwordRigField.getText();


        User user =new User(firstName,LastName,userName,password);

        dbHandler.signUpUser(user);
        System.out.println("Зарегался");
    }
}