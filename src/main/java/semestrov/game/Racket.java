package semestrov.game;

import semestrov.testclient.ClientMessageHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Racket extends Rectangle{

	int id;
	int ySpeed;
	int speed = 10;

	Image image1;
	Image image2;

	ClientMessageHandler client;
	Racket(int x, int y, int RACKET_WIDTH, int RACKET_HEIGHT, int id,ClientMessageHandler client){
		super(x,y,RACKET_WIDTH,RACKET_HEIGHT);
		this.client = client;
		this.id=id;
		ImageIcon img1 = new ImageIcon("C:\\Users\\User1\\IdeaProjects\\secondSemestrovWork\\src\\main\\resources\\pingPong\\racket01.png");
		ImageIcon img2 = new ImageIcon("C:\\Users\\User1\\IdeaProjects\\secondSemestrovWork\\src\\main\\resources\\pingPong\\racket1.png");
		image1 = img1.getImage();
		image2 = img2.getImage();
	}

	public void keyPressed(KeyEvent e) {
		switch(id) {
			case 1:
				if(e.getKeyCode()==KeyEvent.VK_W) {
					setYDirection(-speed);
				}
				if(e.getKeyCode()==KeyEvent.VK_S) {
					setYDirection(speed);
				}
				break;
			case 2:
				if(e.getKeyCode()==KeyEvent.VK_UP) {
					setYDirection(-speed);
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN) {
					setYDirection(speed);
				}
				break;
		}
	}
	public void keyReleased(KeyEvent e) {
		switch(id) {
			case 1:
				if(e.getKeyCode()==KeyEvent.VK_W) {
					setYDirection(0);
				}
				if(e.getKeyCode()==KeyEvent.VK_S) {
					setYDirection(0);
				}
				break;
			case 2:
				if(e.getKeyCode()==KeyEvent.VK_UP) {
					setYDirection(0);
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN) {
					setYDirection(0);
				}
				break;
		}
	}
	public void setYDirection(int yDirection) {
		ySpeed = yDirection;
	}
	public void move() {
		y= y + ySpeed;
		client.sendPlayerPosition(id,y);
	}
	public void draw(Graphics g) {
		if(id==1)
			g.drawImage(image1, x, y, width, height, null);
		else
			g.drawImage(image2, x, y, width, height, null);

	}
}
