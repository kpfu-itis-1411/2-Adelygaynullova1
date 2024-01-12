package semestrov.game;

import semestrov.testclient.ClientMessageHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Score extends Rectangle{

	static int WINDOW_WIDTH;
	static int WINDOW_HEIGHT;
	int player1Score;
	int player2Score;
	ClientMessageHandler client;



	Score(int GAME_WIDTH, int GAME_HEIGHT, ClientMessageHandler client){
		this.client = client;
		Score.WINDOW_WIDTH = GAME_WIDTH;
		Score.WINDOW_HEIGHT = GAME_HEIGHT;

	}
	public void draw(Graphics g) {
		client.sendScore(player1Score, player2Score);
		g.setColor(Color.white);
		g.setFont(new Font("Calibre",Font.PLAIN,38));

		try {
			BufferedImage backgroundImage = ImageIO.read(new File("C:\\Users\\User1\\IdeaProjects\\secondSemestrovWork\\src\\main\\resources\\pingPong\\tablo.png"));
			int scaledWidth = 490;
			int scaledHeight = 90;
			g.drawImage(backgroundImage, (WINDOW_WIDTH / 2) - (scaledWidth / 2), 0, scaledWidth, scaledHeight, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawString(String.valueOf(player1Score /10)+String.valueOf(player1Score %10), (WINDOW_WIDTH /2)-75, 53);
		g.drawString(String.valueOf(player2Score /10)+String.valueOf(player2Score %10), (WINDOW_WIDTH /2)+30, 53);
	}
}