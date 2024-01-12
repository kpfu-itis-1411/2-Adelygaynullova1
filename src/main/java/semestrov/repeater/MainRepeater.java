package semestrov.repeater;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class MainRepeater {

    public static int SERVER_PORT = 50000;

    private Map<String, Socket> clientConnectionList;

    public static void main(String[] args) {
        MainRepeater mainRepeater = new MainRepeater();
    }

    public MainRepeater() {

        clientConnectionList = new HashMap<>();

        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ServerClientHandler(clientConnectionList, clientSocket)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
