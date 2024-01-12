package semestrov.testclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import semestrov.game.GameWindow;
import semestrov.repeater.cmdmodel.Cmd;
import semestrov.testclient.GUI.InvitationWindow;
import semestrov.testclient.GUI.MenuWindow;
import semestrov.testclient.GUI.SearchingForPlayer;
import semestrov.testclient.GUI.WaitingWindow;


import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


import java.io.*;

public class ClientMessageHandler implements Runnable {

    private Socket socket;
    private PrintWriter out;
    private String player_1;
    private String player_2;
    private GameWindow gameWindow;
    private WaitingWindow waitingWindow = new WaitingWindow();
    public ClientMessageHandler(Socket socket) {
        this.socket = socket;
        try {
            this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String cmd = bufferedReader.readLine();
                ObjectMapper mapper = new ObjectMapper();
                Cmd command = mapper.readValue(cmd, Cmd.class);
                this.player_1 = command.player_1;
                this.player_2 = command.player_2;
                switch (command.code){
                    case 1 -> invitationCommand(player_1, player_2);
                    case 2 -> openGameWindow();
                    case 3 -> waitingPlayer();
                    case 4 -> menuWindow(player_1);
                    case 5 -> gameWindow.setRacket1(command.y);
                    case 6 -> gameWindow.setRacket2(command.y);
                    case 7 -> gameWindow.setBall(command.x, command.y);
                    case 8 -> gameWindow.setScore(command.x, command.y);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void invitationCommand(String player_1, String player_2) {
        JFrame mainFrame = new JFrame();
        InvitationWindow invitationWindow = new InvitationWindow(mainFrame, player_1);
        invitationWindow.setVisible(true);
        boolean statusInvitation = invitationWindow.getStatusInvitation();
        sendInvitationResponse(statusInvitation, player_1, player_2);
    }

    private void openGameWindow() {
        gameWindow = new GameWindow(this);
    }
    private void waitingPlayer() {
        waitingWindow.init();
    }
    private void menuWindow(String player_1) throws IOException {
        waitingWindow.remove();
        MenuWindow menuWindow = new MenuWindow();
        switch (menuWindow.getStatusMenu()) {
            case 1 -> {
                SearchingForPlayer findSecondPlayer = new SearchingForPlayer();
                String player_2 = findSecondPlayer.getSecondPlayerName();
                String message = "{\"code\":\"" + 1 + "\", \"player_1\":\"" + player_1 + "\", \"player_2\":\"" + player_2 + "\", \"x\":\"" + 0 + "\", \"y\":\"" + 0 + "\" }\n";
                out.println(message);
            }
            case 2 -> socket.close();
        }
    }

    private void sendInvitationResponse(boolean accepted, String player_1, String player_2) {
        int sts = accepted ? 2 : 7;
        String status = "{\"code\":\"" + sts + "\", \"player_1\":\"" + player_1 + "\", \"player_2\":\"" + player_2 + "\", \"x\":\"" + 0 + "\", \"y\":\"" + 0 + "\" }\n";
        out.println(status);
    }
    public void sendBallPosition(int x, int y) {
        String status = "{\"code\":\"" + 3 + "\", \"player_1\":\"" + player_1 + "\", \"player_2\":\"" + player_2 + "\", \"x\":\"" + x + "\", \"y\":\"" + y + "\" }\n";
        out.println(status);
    }
    public void sendPlayerPosition(int id, int y) {
        int i = id + 3;
        String status = "{\"code\":\"" +i + "\", \"player_1\":\"" + player_1 + "\", \"player_2\":\"" + player_2 + "\", \"x\":\"" + 0 + "\", \"y\":\"" + y + "\" }\n";
        out.println(status);
    }
    public void sendScore(int player1, int player2) {
        String status = "{\"code\":\"" + 6 + "\", \"player_1\":\"" + player_1 + "\", \"player_2\":\"" + player_2 + "\", \"x\":\"" + player1 + "\", \"y\":\"" + player2 + "\" }\n";
        out.println(status);
    }
}
