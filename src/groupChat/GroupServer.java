package groupChat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import javax.swing.JTextArea;

public class GroupServer {

    static JTextArea chatTextArea;
    static Vector<Socket> connectedClients = new Vector<Socket>(5, 5);

    public void createServer(final JTextArea chatTextArea, final int portNumber) {
        GroupServer.chatTextArea = chatTextArea;
        try {
            final ServerSocket serverSocket = new ServerSocket(portNumber);
            new Thread(() -> {
                while (true) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        connectedClients.addElement(clientSocket);
                        GroupServer.chatTextArea.append("A new client connected.\n");
                        HandleClientThread handleClientThread = new HandleClientThread(portNumber, clientSocket);
                        Thread newThread = new Thread(handleClientThread);
                        newThread.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }).start();
        } catch (BindException e) {
            chatTextArea.append("Address already in use.\n");
            chatTextArea.append("Try using different port number\n");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

}

class HandleClientThread implements Runnable {

    int portNumber;
    Socket clientSocket;

    public HandleClientThread(int portNumber, Socket clientSocket) {
        this.portNumber = portNumber;
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                new Broadcast(clientSocket.getRemoteSocketAddress() + ": " + inputLine).send();
                if (inputLine.equalsIgnoreCase("bye")) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class Broadcast {

    String message;

    Broadcast(String message) {
        this.message = message;
    }

    void send() {

        Socket clientSocket;
        PrintWriter out;
        String messageToBeSent;

        for (int i = 0; i < GroupServer.connectedClients.size(); i++) {
            clientSocket = GroupServer.connectedClients.get(i);
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                messageToBeSent = message;
                out.println(messageToBeSent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}