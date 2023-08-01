import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 500;
	public static final int HEIGHT = 500;
	public static final int UNIT_SIZE = 15;
	public static final int NUMBER_OF_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
	
	public final int x[] = new int[NUMBER_OF_UNITS];
	public final int y[] = new int[NUMBER_OF_UNITS];
	
	int length = 3;
	int foodEaten;
	int foodX;
	int foodY;
	char direction = 'D';
	boolean running = false;
	Random random;
	Timer timer;


	public GamePanel() {
		random = new Random();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.LIGHT_GRAY);
		keyPressed();
		play();
	}
	
	public void play() {
		addFood();
		running = true;
		
		timer = new Timer(80, this);
		timer.start();
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		draw(graphics);
	}
	
	public void move() {
		for(int i = length; i > 0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction) {
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
			default:
				y[0] = y[0] + UNIT_SIZE;
		}
	}
	
	public void checkFood() {
		if(x[0] == foodX && y[0] == foodY) {
			length++;
			foodEaten++;
			addFood();
		}
	}
	
	public void draw(Graphics graphics) {
		if(running) {
			//Drawing the food
			graphics.setColor(new Color(107, 27, 5));
			graphics.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);
			
			//Drawing the head of the snake
			graphics.setColor(Color.WHITE);
			graphics.fillRect(x[0], y[0], UNIT_SIZE, UNIT_SIZE);
			
			//Drawing the body of the snake
			for(int i = 1; i < length; i++) {
				graphics.setColor(new Color(67, 142, 148));
				graphics.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			}
			
			//Drawing the "Score" text
			graphics.setColor(Color.BLACK);
			graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 25));
			FontMetrics metrics = getFontMetrics(graphics.getFont());
			graphics.drawString("Score: " + foodEaten, (WIDTH - metrics.stringWidth("Score: " + foodEaten)) / 2, graphics.getFont().getSize());
		} else {
			gameOver(graphics);
		}
	}
	
	public void addFood() {
		foodX = random.nextInt((int)(WIDTH / UNIT_SIZE))*UNIT_SIZE;
		foodY = random.nextInt((int)(HEIGHT / UNIT_SIZE))*UNIT_SIZE;
	}
	
	public void checkHit() {
		for(int i = length; i > 0; i--) {
			if(x[0] == x[i] && y[0] == y[i]) {
				running = false;
			}
		}
		
		if(x[0] < 0 || x[0] > WIDTH || y[0] < 0 || y[0] > HEIGHT) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
	}
	
	public void gameOver(Graphics graphics) {
		//"Game Over" font
		graphics.setColor(Color.RED);
		graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 50));
		FontMetrics metrics = getFontMetrics(graphics.getFont());
		graphics.drawString("Game Over", (WIDTH - metrics.stringWidth("Game Over")) / 2, HEIGHT / 2);
		
		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 25));
		metrics = getFontMetrics(graphics.getFont());
		graphics.drawString("Final Score: " + foodEaten, (WIDTH - metrics.stringWidth("Final Score: " + foodEaten)) / 2, (HEIGHT / 2) + (graphics.getFont().getSize() * 2));
		
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if(running) {
			move();
			checkFood();
			checkHit();
		}
		repaint();
	}
	
	public void keyPressed() {
		getInputMap().put(KeyStroke.getKeyStroke("UP"), "up");
		getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "down");
		getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "left");
		getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "right");
		
		getActionMap().put("up", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if(direction != 'D') {
                	direction = 'U';
                }
            }
        });
		
		getActionMap().put("down", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if(direction != 'U') {
                	direction = 'D';
                }
            }
        });
		
		getActionMap().put("left", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if(direction != 'R') {
                	direction = 'L';
                }
            }
        });
		
		getActionMap().put("right", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if(direction != 'L') {
                	direction = 'R';
                }
            }
        });
	}

}
