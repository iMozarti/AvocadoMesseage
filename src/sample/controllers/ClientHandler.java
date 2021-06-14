package sample.controllers;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {
private final ArrayList<ClientHandler> clients;
private final Socket socket;
private final BufferedReader reader;
private final PrintWriter writer;
    public ClientHandler(Socket socket, ArrayList<ClientHandler> clients) throws IOException {
        this.socket=socket;
        this.clients=clients;
        this.reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer=new PrintWriter(socket.getOutputStream(),true);
    }
    @Override
    public void run() {
        try {
            String msg;
            while ((msg = reader.readLine()) != null) {
                for (ClientHandler cl : clients) {
                    cl.writer.println(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
                writer.close();
                socket.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
