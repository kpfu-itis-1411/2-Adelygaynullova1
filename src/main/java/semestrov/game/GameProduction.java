package semestrov.game;

import semestrov.testclient.ClientMessageHandler;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class GameProduction extends JPanel implements Runnable{

	static final int WINDOW_WIDTH = 1000;
	static final int WINDOW_HEIGHT = (int)(WINDOW_WIDTH * (0.5));
	static final Dimension WINDOW_SIZE = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
	static final int BALL_DIAMETER = 20;
	static final int RACKET_WIDTH = 80;
	static final int RACKET_HEIGHT = 115;


	public Thread gameThread;

	private ClientMessageHandler client;
	Image image;
	Graphics graphics;
	Random random;
	Racket racket1;
	Racket racket2;

	Ball ball;
	Score score;

	Image backgroundImage;

	public GameProduction(ClientMessageHandler client){
		this.client = client;
		newRackets();
		newBall(client);
		score = new Score(WINDOW_WIDTH, WINDOW_HEIGHT, client);
		this.setFocusable(true);
		this.addKeyListener(new buttonTracking());
		this.setPreferredSize(WINDOW_SIZE);
		ImageIcon backgroundImageIcon = new ImageIcon("C:\\Users\\User1\\IdeaProjects\\secondSemestrovWork\\src\\main\\resources\\pingPong\\backGround.png");
		backgroundImage = backgroundImageIcon.getImage().getScaledInstance(WINDOW_WIDTH, WINDOW_HEIGHT, Image.SCALE_DEFAULT);

		gameThread = new Thread(this);
		gameThread.start();
	}

	public void newBall(ClientMessageHandler client) {
		random = new Random();
		ball = new Ball((WINDOW_WIDTH /2)-(BALL_DIAMETER/2),random.nextInt(WINDOW_HEIGHT -BALL_DIAMETER) ,BALL_DIAMETER,BALL_DIAMETER,client);
	}
	public void newRackets() {
		racket1 = new Racket(0,(WINDOW_HEIGHT /2)-(RACKET_HEIGHT/2),RACKET_WIDTH,RACKET_HEIGHT,1,client);
		racket2 = new Racket(WINDOW_WIDTH -RACKET_WIDTH,(WINDOW_HEIGHT /2)-(RACKET_HEIGHT/2),RACKET_WIDTH,RACKET_HEIGHT,2,client);
	}
	public void paint(Graphics g) {
		image = createImage(getWidth(),getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image,0,0,this);
	}
	public void draw(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, this);
		racket1.draw(g);
		racket2.draw(g);
		ball.draw(g);
		score.draw(g);
		Toolkit.getDefaultToolkit().sync();

	}
	public void move() {
		racket1.move();
		racket2.move();
		ball.move();
	}

	public class buttonTracking extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			racket1.keyPressed(e);
			racket2.keyPressed(e);
		}
		public void keyReleased(KeyEvent e) {
			racket1.keyReleased(e);
			racket2.keyReleased(e);
		}
	}
	public void checkingForCollisions() {

		//отталкивание мяча от верха и низа окна
		if(ball.y <=0) {
			ball.setYDirection(-ball.ySpeed);
		}
		if(ball.y >= WINDOW_HEIGHT -BALL_DIAMETER) {
			ball.setYDirection(-ball.ySpeed);
		}
		//отталкивание от ракеток
		if(ball.intersects(racket1)) {
			ball.xSpeed = Math.abs(ball.xSpeed);

			ball.xSpeed++;

			if(ball.ySpeed >0)
				ball.ySpeed++;
			else
				ball.ySpeed--;

			ball.setXDirection(ball.xSpeed);
			ball.setYDirection(ball.ySpeed);
		}
		if(ball.intersects(racket2)) {
			ball.xSpeed = Math.abs(ball.xSpeed);

			ball.xSpeed++;

			if(ball.ySpeed >0)
				ball.ySpeed++;
			else
				ball.ySpeed--;

			ball.setXDirection(-ball.xSpeed);
			ball.setYDirection(ball.ySpeed);
		}
		//останавливает ракетки на концах окна
		if(racket1.y<=0)
			racket1.y=0;
		if(racket1.y >= (WINDOW_HEIGHT -RACKET_HEIGHT))
			racket1.y = WINDOW_HEIGHT -RACKET_HEIGHT;
		if(racket2.y<=0)
			racket2.y=0;
		if(racket2.y >= (WINDOW_HEIGHT -RACKET_HEIGHT))
			racket2.y = WINDOW_HEIGHT -RACKET_HEIGHT;

		//дает игроку + балл и создает новые ракетки и новый мяц
		if(ball.x <=0) {
			score.player2Score++;
			newRackets();
			newBall(client);


		}
		if(ball.x >= WINDOW_WIDTH -BALL_DIAMETER) {
			score.player1Score++;
			newRackets();
			newBall(client);


		}


	}


	public void run() {
		//game loop
		long lastTime = System.nanoTime();
		double updateRate =60.0; //игра обновляется с 60 раз в секунду.
		double ns = 1000000000 / updateRate;
		double delta = 0;
		while(true) {
			long now = System.nanoTime();
			delta += (now -lastTime)/ns;
			lastTime = now;
			if(delta >=1) {
				move();
				checkingForCollisions();
				repaint();
				delta--;
			}
		}
	}

	public void setRacket1(int y){
		racket1.y = y;
	}
	public void setRacket2(int y){
		racket2.y = y;
	}
	public void setBall(int x, int y){
		ball.x = x;
		ball.y = y;
	}
	public void setScore(int p1, int p2){
		score.player1Score = p1;
		score.player2Score = p2;

	}

}