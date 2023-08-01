import javax.swing.JFrame;

public class GameFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public GameFrame() {
		setTitle("Snake");
		GamePanel panel = new GamePanel();
		this.add(panel);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		panel.requestFocusInWindow();
	}

}
