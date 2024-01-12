package semestrov.testclient;

import semestrov.testclient.GUI.SearchingForPlayer;
import semestrov.testclient.GUI.MenuWindow;
import semestrov.testclient.GUI.StartGame;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TestClient_2 {

    public static int SERVER_PORT = 50000;
    public static void main(String[] args) {
        StartGame nameInput = new StartGame();
        String name = nameInput.getClientName();

        System.out.println(name);
        String server_address = args.length > 1 ? args[1] : "127.0.0.1";
        int server_port = args.length > 2 ? Integer.parseInt(args[2]) : SERVER_PORT;
        try {
            Socket socket = new Socket(server_address, server_port);
            String message =  "{\"name\":\"" + name + "\"}\n";
            socket.getOutputStream().write(message.getBytes(StandardCharsets.UTF_8));
            Thread thread = new Thread(new ClientMessageHandler(socket));
            thread.setDaemon(true);
            thread.start();
            try {
                MenuWindow mainMenu = new MenuWindow();
                if (mainMenu.getStatusMenu() == 1) {
                    SearchingForPlayer findSecondPlayer = new SearchingForPlayer();
                    String gamer2 = findSecondPlayer.getSecondPlayerName();
                    String message2 = "{\"code\":\"" + 1 + "\", \"player_1\":\"" + name + "\", \"player_2\":\"" + gamer2 + "\", \"x\":\"" + 0 + "\", \"y\":\"" + 0 + "\" }\n";
                    socket.getOutputStream().write(message2.getBytes(StandardCharsets.UTF_8));
                    socket.getOutputStream().flush();
                }
                if (mainMenu.getStatusMenu() == 2){
                    socket.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

