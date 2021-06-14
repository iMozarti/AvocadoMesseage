package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.services.User;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller extends RegisterScreen {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    public TextField LoginField;

    @FXML
    public javafx.scene.control.PasswordField PasswordField;

    @FXML
    private Button LogInButton;

    @FXML
    private Button RegisterButton;

    @FXML
    private Label error_label1;

    @FXML
    private Label error_label2;


    public static String userNameText,password;

    @FXML
    void initialize() {
        RegisterButton.setOnAction(actionEvent -> {
            RegisterButton.getScene().getWindow().hide();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/fxml/RegisterScreen.fxml"));
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
        LogInButton.setOnAction(actionEvent -> {
            userNameText=LoginField.getText();
            password=PasswordField.getText();
            if(!userNameText.equals("")&&!password.equals("")){
                loginUser(userNameText,password);
            } else {
                System.out.println("Login pr password is empty");
            }
        });
    }

    public void loginUser(String userNameText, String password) {
        Database dbHandler=new Database();
        User user=new User();
        user.setUserName(userNameText);
        user.setPassword(password);
        ResultSet result=dbHandler.getUser(user);

        int counter=0;
        try {
            while (result.next()) {
                counter++;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        if (counter>=1){
            System.out.println("Success");
            LogInButton.getScene().getWindow().hide();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/fxml/Chat.fxml"));
            error_label1.setVisible(false);
            error_label2.setVisible(false);
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root4=loader.getRoot();
            Stage stage4=new Stage();
            stage4.setResizable(false);
            stage4.setScene(new Scene(root4));
            stage4.show();
        }else{
            error_label1.setVisible(true);
            error_label2.setVisible(true);
        }
    }
}


