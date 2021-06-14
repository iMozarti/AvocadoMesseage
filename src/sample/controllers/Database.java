package sample.controllers;


import sample.services.Configs;
import sample.services.Message;
import sample.services.User;

import java.sql.*;

public class Database extends Configs {
    Connection dbConnection;
    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException{
        String connectionString="jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection=DriverManager.getConnection(connectionString, dbUser,dbPass);
        return dbConnection;
    }
    public void signUpUser(User user) {
        String insert= "INSERT INTO users(FirstName,LastName,UserName,Password)VALUES(?,?,?,?)";
        try {


            PreparedStatement post=getDbConnection().prepareStatement(insert);
            post.setString(1,user.getFirstName());
            post.setString(2,user.getLastName());

            post.setString(3,user.getUserName());
            post.setString(4,user.getPassword());

            post.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }


    }
    public ResultSet getUser(User user){
        ResultSet resultSet=null;

        String select= "SELECT * FROM users Where UserName =? AND Password =?";
        try {


            PreparedStatement prst=getDbConnection().prepareStatement(select);

            prst.setString(1,user.getUserName());
            prst.setString(2,user.getPassword());


            resultSet= prst.executeQuery();
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
        return resultSet;
    }

    public void messageTobase(Message message){
        String insertM = "INSERT INTO avocadomessage.message(UserName, MessageText)VALUES(?,?)";
        try {
            PreparedStatement post = getDbConnection().prepareStatement(insertM);
            post.setString(1, message.getUserName());
            post.setString(2, message.getMessageText());
            post.executeUpdate();
        }catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
    }
}
