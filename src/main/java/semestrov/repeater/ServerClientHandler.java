package semestrov.repeater;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import semestrov.repeater.cmdmodel.Cmd;
import semestrov.repeater.cmdmodel.HelloClient;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ServerClientHandler implements Runnable {

    private Map<String, Socket> clientConnectionMap;

    private Socket clientSocket;

    private String name;

    public ServerClientHandler(Map<String, Socket> clientConnectionList, Socket clientSocket) {
        this.clientConnectionMap = clientConnectionList;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String cmd = bufferedReader.readLine();
            ObjectMapper mapper = new ObjectMapper();
            HelloClient clientInfo = mapper.readValue(cmd, HelloClient.class);
            if (clientInfo != null && clientInfo.name != null) {
                clientConnectionMap.put(clientInfo.name, clientSocket);
                name = clientInfo.name;
            }
            while (true) {
                cmd = bufferedReader.readLine();
                JsonNode jsonNode = mapper.readTree(cmd);
                if (jsonNode.get("code") != null) {
                    Cmd command = mapper.readValue(cmd, Cmd.class);
                    if (command != null) {
                        switch (command.code) {
                            case 1 -> redirect(command);
                            case 2 -> startGame(command);
                            case 3 -> ball(command);
                            case 4 -> firstPlayer(command);
                            case 5 -> secondPlayer(command);
                            case 6 -> score(command);
                            case 7 -> notStartGame(command);
                        }
                    } else {
                        clientConnectionMap.remove(name);
                        clientSocket.close();
                        return;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void redirect(Cmd command) throws IOException {
        Socket playerSocket = clientConnectionMap.get(command.player_1);
        Socket otherClientSocket = clientConnectionMap.get(command.player_2);
        if (otherClientSocket != null && otherClientSocket.isConnected()) {
            String invitation = "{\"code\":\"" + 1 + "\", \"player_1\":\"" + command.player_1 + "\", \"player_2\":\"" + command.player_2 + "\", \"x\":\"" + 0 + "\", \"y\":\"" + 0 + "\" }\n";
            String waiting = "{\"code\":\"" + 3 + "\", \"player_1\":\"" + command.player_1 + "\", \"player_2\":\"" + command.player_2 + "\", \"x\":\"" + 0 + "\", \"y\":\"" + 0 + "\" }\n";
            otherClientSocket.getOutputStream().write(invitation.getBytes(StandardCharsets.UTF_8));
            otherClientSocket.getOutputStream().flush();
            playerSocket.getOutputStream().write(waiting.getBytes(StandardCharsets.UTF_8));
            playerSocket.getOutputStream().flush();
        }
    }
    private void notStartGame(Cmd command) throws IOException {
        Socket playerSocket = clientConnectionMap.get(command.player_1);
        Socket otherClientSocket = clientConnectionMap.get(command.player_2);
        if (otherClientSocket != null && otherClientSocket.isConnected()) {
            String waiting = "{\"code\":\"" + 4 + "\", \"player_1\":\"" + command.player_1 + "\", \"player_2\":\"" + command.player_2 + "\", \"x\":\"" + 0 + "\", \"y\":\"" + 0 + "\" }\n";
            playerSocket.getOutputStream().write(waiting.getBytes(StandardCharsets.UTF_8));
            playerSocket.getOutputStream().flush();
        }
    }
    private void startGame(Cmd command) throws IOException {
        Socket otherClientSocket = clientConnectionMap.get(command.player_1);
        Socket playerSocket = clientConnectionMap.get(command.player_2);
        if (otherClientSocket != null && otherClientSocket.isConnected()) {
            String invitation;
            invitation = "{\"code\":\"" + 2 + "\", \"player_1\":\"" + command.player_1 + "\", \"player_2\":\"" + command.player_2 + "\", \"x\":\"" + 0 + "\", \"y\":\"" + 0 + "\" }\n";
            otherClientSocket.getOutputStream().write(invitation.getBytes(StandardCharsets.UTF_8));
            otherClientSocket.getOutputStream().flush();
            playerSocket.getOutputStream().write(invitation.getBytes(StandardCharsets.UTF_8));
            playerSocket.getOutputStream().flush();
        }
    }

    private void firstPlayer(Cmd command) throws IOException {
        Socket player_2_Socket = clientConnectionMap.get(command.player_1);
        Socket player_1_Socket = clientConnectionMap.get(command.player_2);
        if (player_2_Socket != null && player_2_Socket.isConnected() && player_1_Socket != null && player_1_Socket.isConnected()) {
            String cords;
            cords = "{\"code\":\"" + 5 + "\", \"player_1\":\"" + command.player_1 + "\", \"player_2\":\"" + command.player_2 + "\", \"x\":\"" + command.x + "\", \"y\":\"" + command.y + "\" }\n";
            player_2_Socket.getOutputStream().write(cords.getBytes(StandardCharsets.UTF_8));
            player_2_Socket.getOutputStream().flush();
        }
    }
    private void secondPlayer(Cmd command) throws IOException {
        Socket player_2_Socket = clientConnectionMap.get(command.player_1);
        Socket player_1_Socket = clientConnectionMap.get(command.player_2);
        if (player_2_Socket != null && player_2_Socket.isConnected() && player_1_Socket != null && player_1_Socket.isConnected()) {
            String cords;
            cords = "{\"code\":\"" + 6 + "\", \"player_1\":\"" + command.player_1 + "\", \"player_2\":\"" + command.player_2 + "\", \"x\":\"" + command.x + "\", \"y\":\"" + command.y + "\" }\n";
            player_1_Socket.getOutputStream().write(cords.getBytes(StandardCharsets.UTF_8));
            player_1_Socket.getOutputStream().flush();
        }
    }
    private void ball(Cmd command) throws IOException {
        Socket player_2_Socket = clientConnectionMap.get(command.player_1);
        Socket player_1_Socket = clientConnectionMap.get(command.player_2);
        if (player_2_Socket != null && player_2_Socket.isConnected() && player_1_Socket != null && player_1_Socket.isConnected()) {
            String cords;
            cords = "{\"code\":\"" + 7 + "\", \"player_1\":\"" + command.player_1 + "\", \"player_2\":\"" + command.player_2 + "\", \"x\":\"" + command.x + "\", \"y\":\"" + command.y + "\" }\n";
            player_1_Socket.getOutputStream().write(cords.getBytes(StandardCharsets.UTF_8));
            player_1_Socket.getOutputStream().flush();
        }
    }
    private void score(Cmd command) throws IOException {
        Socket player_2_Socket = clientConnectionMap.get(command.player_1);
        Socket player_1_Socket = clientConnectionMap.get(command.player_2);
        if (player_2_Socket != null && player_2_Socket.isConnected() && player_1_Socket != null && player_1_Socket.isConnected()) {
            String cords;
            cords = "{\"code\":\"" + 8 + "\", \"player_1\":\"" + command.player_1 + "\", \"player_2\":\"" + command.player_2 + "\", \"x\":\"" + command.x + "\", \"y\":\"" + command.y + "\" }\n";
            player_1_Socket.getOutputStream().write(cords.getBytes(StandardCharsets.UTF_8));
            player_1_Socket.getOutputStream().flush();
        }
    }
}
