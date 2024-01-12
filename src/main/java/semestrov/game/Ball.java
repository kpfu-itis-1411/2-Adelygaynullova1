package semestrov.game;

import semestrov.testclient.ClientMessageHandler;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Ball extends Rectangle{

	Random random;
	int xSpeed;
	int ySpeed;
	int originalSpeed = 2;
	Image image;
	ClientMessageHandler client;
	// Если случайное значение равно 0, то мяч будет двигаться влево или вверх, в противном случае - вправо или вниз
	Ball(int x, int y, int width, int height,ClientMessageHandler client){
		super(x,y,width,height);
		this.client = client;
		random = new Random();
		int randomXDirection = random.nextInt(2);
		if(randomXDirection == 0)
			randomXDirection--;
		setXDirection(randomXDirection* originalSpeed);

		int randomYDirection = random.nextInt(2);
		if(randomYDirection == 0)
			randomYDirection--;
		setYDirection(randomYDirection* originalSpeed);

		ImageIcon img = new ImageIcon("C:\\Users\\User1\\IdeaProjects\\secondSemestrovWork\\src\\main\\resources\\pingPong\\016214d5fa5cbb39c6998e13b4198e14.swg-PhotoRoom.png-PhotoRoom.png");
		image = img.getImage();

	}

	public void setXDirection(int randomXDirection) {
		xSpeed = randomXDirection;
	}
	public void setYDirection(int randomYDirection) {
		ySpeed = randomYDirection;
	}
	public void move() {
		x += xSpeed;
		y += ySpeed;
		client.sendBallPosition(x,y);
	}
	public void draw(Graphics g) {
		g.drawImage(image, x, y, width, height, null);
	}
}