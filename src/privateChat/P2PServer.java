package privateChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JTextArea;

public class P2PServer {

    public PrintWriter createServer(final JTextArea chatTextArea, int portNumber) throws IOException {
        chatTextArea.append("Waiting for your friend to join\n");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(portNumber);
        }
        catch(BindException e) {
            chatTextArea.append("Address already in use.\n");
            chatTextArea.append("Try using different port number\n");
        }
        assert serverSocket != null;
        Socket clientSocket = serverSocket.accept();
        chatTextArea.append(clientSocket.getRemoteSocketAddress() + " connected. Listening at portNumber: " + portNumber + "\n");
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        final BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        new Thread(() -> {
            String message;
            while (true) {
                try {
                    message = in.readLine();
                    if(message == null) {
                        chatTextArea.append("Connection closed \n");
                        break;
                    }
                    message = "Your friend: " + message + "\n";
                    chatTextArea.append(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return out;
    }

}