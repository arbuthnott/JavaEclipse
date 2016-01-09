import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextArea;

public class GameFrame extends JFrame {

	private JPanel contentPane;
	private GamePanel canvas;
	private JButton skipButton;
	private JTextArea instructionArea;
	public final int MIN_GUTTER_WIDTH = 10;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameFrame frame = new GameFrame();
					frame.setVisible(true);
					//frame.drawBoard();
					//frame.canvas.repaint();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GameFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		int squareSize = (561 - 2*MIN_GUTTER_WIDTH) / 8;
		GamePanel canvas = new GamePanel(MIN_GUTTER_WIDTH, squareSize);
		canvas.setBackground(Color.BLACK);
		// TODO: MESS WITH DIMENSIONS HERE TO CENTRE THE BOARD.
		int boardSize = 2*MIN_GUTTER_WIDTH + 8 * squareSize;
		canvas.setBounds(784 - boardSize, 0, 784, 561);
		
		canvas.addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent arg0) {}

			@Override
			public void mouseMoved(MouseEvent evt) {
				Point pt = canvas.getSquare(evt.getX(), evt.getY());
				canvas.setUnderMouse(pt);
				canvas.repaint();
			}
			
		});
		canvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent evt) {
				Point pt = canvas.getSquare(evt.getX(), evt.getY());
				canvas.click(pt);
				skipButton.setText(canvas.getSkipText());
				instructionArea.setText(canvas.getInstruction());
			}

			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			
		});
		contentPane.add(canvas);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("F:\\ZRandom\\EclipseWorkspace\\boardGameImages\\logo.png"));
		lblNewLabel.setBounds(10, 11, 208, 89);
		contentPane.add(lblNewLabel);
		
		JButton newGameButton = new JButton("New Game");
		newGameButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		newGameButton.setBounds(10, 111, 208, 23);
		newGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO reset to new game
				
			}
		});
		contentPane.add(newGameButton);
		
		skipButton = new JButton("Skip Move");
		skipButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		skipButton.setBounds(10, 467, 208, 33);
		skipButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO skip current move or attack.
				canvas.skip();
				skipButton.setText(canvas.getSkipText());
				instructionArea.setText(canvas.getInstruction());
			}
		});
		contentPane.add(skipButton);
		
		instructionArea = new JTextArea();
		instructionArea.setFont(new Font("Tahoma", Font.PLAIN, 18));
		instructionArea.setLineWrap(true);
		instructionArea.setWrapStyleWord(true);
		instructionArea.setText("Blue Player - Choose a Piece to Move!");
		instructionArea.setForeground(Color.WHITE);
		instructionArea.setBackground(Color.DARK_GRAY);
		instructionArea.setBounds(10, 245, 208, 145);
		contentPane.add(instructionArea);
	}
}
