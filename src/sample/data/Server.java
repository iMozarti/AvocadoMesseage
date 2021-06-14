package sample.data;

import sample.controllers.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public ArrayList<ClientHandler> clients= new ArrayList<ClientHandler>();
    Server() throws IOException {
        Socket socket;
        ServerSocket serverSocket = new ServerSocket(9092);
        while (true){
            System.out.println("Waiting for clients...");
            socket=serverSocket.accept();
            System.out.println("Connected");
            ClientHandler clientThread=new ClientHandler(socket,clients);
            clients.add(clientThread);
            clientThread.start();
        }
    }
}