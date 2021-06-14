package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.services.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class Chat extends Thread  {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitButton;

    @FXML
    private TextField SearchField;

    @FXML
    private Button SearchButton;

    @FXML
    private TextField messageField;

    @FXML
    private Button SendButton;

    @FXML
    private Label FirstNameLabel;
    @FXML
    private Label LastNameLabel;

    @FXML
    private ListView<String> friendList;

    @FXML
    private TextArea messeagesarea;
    BufferedReader reader;
    PrintWriter writer;
    Socket socket;

    public void connectSocket() {
        try {
            socket = new Socket("localhost", 9092);
            System.out.println("Socket is connected with server!");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        connectSocket();
        imena();
        vivodpolzovatelei();
        messagetFrombase();

        exitButton.setOnAction(actionEvent -> {
            exitButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/fxml/sample.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        });
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];
                System.out.println(cmd);
                StringBuilder fulmsg = new StringBuilder();
                for(int i = 1; i < tokens.length; i++) {
                    fulmsg.append(tokens[i]);
                }
                System.out.println(fulmsg);

                if (cmd.equalsIgnoreCase(sample.controllers.Controller.userNameText + ":")) {
                    continue;
                } else if(fulmsg.toString().equalsIgnoreCase("bye")) {
                    break;
                }

                messeagesarea.appendText(msg + "\n");
            }
            reader.close();
            writer.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void imena() throws ClassNotFoundException, SQLException {
        String sql="SELECT * FROM users WHERE UserName= ?";
        Database database=new Database();
        PreparedStatement preparedStatement=database.getDbConnection().prepareStatement(sql);
        preparedStatement.setString(1,sample.controllers.Controller.userNameText);
        ResultSet resultSet=preparedStatement.executeQuery();
        while (resultSet.next()){
            FirstNameLabel.setText(resultSet.getString("FirstName"));
            LastNameLabel.setText(resultSet.getString("LastName"));
        }
        database.dbConnection.close();
    }

    public void vivodpolzovatelei() throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM USERS";
        Database database=new Database();
        Statement statement=database.getDbConnection().createStatement();
        ResultSet resultSet=statement.executeQuery(sql);

        while (resultSet.next()){
            String firstname=resultSet.getString("FirstName");
            String lastname=resultSet.getString("LastName");
            String fuulname=firstname+" "+lastname;
            System.out.println(fuulname);
            friendList.getItems().add(fuulname);
        }
    }

    public void messagetFrombase() throws SQLException, ClassNotFoundException {
        String sql1="SELECT * FROM message";
        Database database = new Database();
        Statement statement=database.getDbConnection().createStatement();
        ResultSet resultSet=statement.executeQuery(sql1);
        while(resultSet.next()){
            String UserName = resultSet.getString("UserName");
            String MessageText = resultSet.getString("MessageText");
            String FullMessage = UserName + ": " + MessageText;
            messeagesarea.appendText(FullMessage+"\n");
        }
    }

    public void send(){
        Database dbHandler=new Database();
        String UserName = sample.controllers.Controller.userNameText;
        String messeage=messageField.getText();
        if (messeage.replaceAll(" ", "").length() != 0){
            Message message = new Message(UserName, messeage);
            dbHandler.messageTobase(message);
            writer.println(sample.controllers.Controller.userNameText+": "+messeage);
            messeagesarea.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            messeagesarea.appendText("Me: "+messeage+"\n");
            messageField.setText("");
        }
    }

    public void sendMessageByKey(KeyEvent event) {
        if(event.getCode().toString().equals("ENTER")) {
            send();
        }
    }

    public void handleSentVent(MouseEvent mouseEvent) {
        if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {
            send();
        }
    }
}


