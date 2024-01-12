package semestrov.game;

import semestrov.testclient.ClientMessageHandler;

import java.awt.*;
import javax.swing.*;

public class GameWindow extends JFrame{
	GameProduction gameProduction;
	public GameWindow(ClientMessageHandler client){
		gameProduction = new GameProduction(client);
		this.add(gameProduction);
		this.setTitle("Ping Pong");
		this.setResizable(false);
//		this.setBackground(Color.decode("#c96568"));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}


	public void setRacket1( int y){
		gameProduction.setRacket1( y);
	}
	public void setRacket2( int y){
		gameProduction.setRacket2( y);
	}
	public void setBall(int x, int y){
		gameProduction.setBall(x, y);
	}
	public void setScore(int p1, int p2){
		gameProduction.setScore(p1,p2);
	}
}