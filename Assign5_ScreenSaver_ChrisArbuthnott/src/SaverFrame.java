import java.awt.EventQueue;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import javax.swing.JSeparator;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class SaverFrame extends JFrame {

	// Controls for dynamic use
	private JPanel contentPane;
	private CirclePanel circlePanel;
	private PolyPanel polyPanel;
	private JMenu menuCircles, menuTriangles, menuStars, menuBoxes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SaverFrame frame = new SaverFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SaverFrame() {
		setMinimumSize(new Dimension(400, 300));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 450);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		// MAIN MENU
		JMenu mnChooseDisplay = new JMenu("ScreenSaver");
		menuBar.add(mnChooseDisplay);
		
		JMenuItem mntmSwarmCircles = new JMenuItem("Circles");
		mntmSwarmCircles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				polyPanel.toBack();
				circlePanel.toFront();
				menuCircles.setEnabled(true);
				menuTriangles.setEnabled(false);
				menuStars.setEnabled(false);
				menuBoxes.setEnabled(false);
			}
		});
		mnChooseDisplay.add(mntmSwarmCircles);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		JMenuItem mntmOthers = new JMenuItem("Others");
		mntmOthers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				circlePanel.toBack();
				polyPanel.toFront();
				menuCircles.setEnabled(false);
				menuTriangles.setEnabled(true);
				menuStars.setEnabled(true);
				menuBoxes.setEnabled(true);
			}
		});
		mnChooseDisplay.add(mntmOthers);
		
		JSeparator separator_5 = new JSeparator();
		mnChooseDisplay.add(separator_5);
		
		JMenuItem mntmPauseunpause = new JMenuItem("Pause/Unpause");
		mntmPauseunpause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		mntmPauseunpause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				circlePanel.togglePause();
				polyPanel.togglePause();
			}
		});
		mnChooseDisplay.add(mntmPauseunpause);
		mnChooseDisplay.add(mntmExit);
		
		// CIRCLE MENU
		menuCircles = new JMenu("Circles");
		menuBar.add(menuCircles);
		
		JMenuItem itemCircle = new JMenuItem("+1 circle");
		itemCircle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, InputEvent.CTRL_MASK));
		itemCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				circlePanel.addShape(Circle.randomBall(circlePanel.getWidth(), circlePanel.getHeight()));
			}
		});
		menuCircles.add(itemCircle);
		
		JMenuItem itemCircleFive = new JMenuItem("+5 circles");
		itemCircleFive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int idx=0; idx<5; idx++)
					circlePanel.addShape(Circle.randomBall(circlePanel.getWidth(), circlePanel.getHeight()));
			}
		});
		menuCircles.add(itemCircleFive);
		
		JMenuItem itemCircleTwenty = new JMenuItem("+20 circles");
		itemCircleTwenty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int idx=0; idx<20; idx++)
					circlePanel.addShape(Circle.randomBall(circlePanel.getWidth(), circlePanel.getHeight()));
			}
		});
		menuCircles.add(itemCircleTwenty);
		
		JSeparator separator = new JSeparator();
		menuCircles.add(separator);
		
		JMenuItem itemCircleColor = new JMenuItem("morph colors");
		itemCircleColor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		itemCircleColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color clr = new Color(Shape.getRandom(0, 255),Shape.getRandom(0, 255),Shape.getRandom(0, 255));
				circlePanel.changeColors(clr);
			}
		});
		menuCircles.add(itemCircleColor);
		
		JMenuItem mntmStartOver = new JMenuItem("start over");
		mntmStartOver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Circle> newShapes = new ArrayList<Circle>();
				circlePanel.setShapes(newShapes);
				circlePanel.addShape(Circle.randomBall(circlePanel.getWidth(), circlePanel.getHeight()));
			}
		});
		
		JMenuItem mntmRandomColors = new JMenuItem("random colors");
		mntmRandomColors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				circlePanel.randomColors();
			}
		});
		mntmRandomColors.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
		menuCircles.add(mntmRandomColors);
		
		JSeparator separator_1 = new JSeparator();
		menuCircles.add(separator_1);
		menuCircles.add(mntmStartOver);
		
		// TRIANGLE MENU
		menuTriangles = new JMenu("Triangles");
		menuTriangles.setEnabled(false);
		menuBar.add(menuTriangles);
		
		JMenuItem mntmTriangle = new JMenuItem("+1 triangle");
		mntmTriangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				polyPanel.addShape((Polygon)Triangle.randomTriangle(polyPanel.getWidth(),polyPanel.getHeight()));
			}
		});
		menuTriangles.add(mntmTriangle);
		
		JMenuItem mntmTriangles = new JMenuItem("+5 triangles");
		mntmTriangles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int idx=0; idx<5; idx++)
					polyPanel.addShape((Polygon)Triangle.randomTriangle(polyPanel.getWidth(),polyPanel.getHeight()));
			}
		});
		menuTriangles.add(mntmTriangles);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("+20 triangles");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int idx=0; idx<20; idx++)
					polyPanel.addShape((Polygon)Triangle.randomTriangle(polyPanel.getWidth(),polyPanel.getHeight()));
			}
		});
		menuTriangles.add(mntmNewMenuItem);
		
		JSeparator separator_2 = new JSeparator();
		menuTriangles.add(separator_2);
		
		JMenuItem mntmStartOver_1 = new JMenuItem("start over");
		mntmStartOver_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Polygon> newPolys = new ArrayList<Polygon>();
				newPolys.add((Polygon)Triangle.randomTriangle(polyPanel.getWidth(), polyPanel.getHeight()));
				polyPanel.setShapes(newPolys);
			}
		});
		menuTriangles.add(mntmStartOver_1);
		
		// STAR MENU
		menuStars = new JMenu("Stars");
		menuStars.setEnabled(false);
		menuBar.add(menuStars);
		
		JMenuItem mntmStar = new JMenuItem("+1 star");
		mntmStar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				polyPanel.addShape((Polygon)Star.randomStar(polyPanel.getWidth(), polyPanel.getHeight()));
			}
		});
		menuStars.add(mntmStar);
		
		JMenuItem mntmStars = new JMenuItem("+5 stars");
		mntmStars.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int idx=0; idx<5; idx++)
					polyPanel.addShape((Polygon)Star.randomStar(polyPanel.getWidth(), polyPanel.getHeight()));
			}
		});
		menuStars.add(mntmStars);
		
		JMenuItem mntmStars_1 = new JMenuItem("+20 stars");
		mntmStars_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int idx=0; idx<20; idx++)
					polyPanel.addShape((Polygon)Star.randomStar(polyPanel.getWidth(), polyPanel.getHeight()));
			}
		});
		menuStars.add(mntmStars_1);
		
		JSeparator separator_3 = new JSeparator();
		menuStars.add(separator_3);
		
		JMenuItem mntmStartOver_2 = new JMenuItem("start over");
		mntmStartOver_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Polygon> newPolys = new ArrayList<Polygon>();
				newPolys.add((Polygon)Star.randomStar(polyPanel.getWidth(), polyPanel.getHeight()));
				polyPanel.setShapes(newPolys);
			}
		});
		menuStars.add(mntmStartOver_2);
		
		menuBoxes = new JMenu("Boxes");
		menuBoxes.setEnabled(false);
		menuBar.add(menuBoxes);
		
		// BOX MENU
		JMenuItem mntmBox = new JMenuItem("+1 box");
		mntmBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				polyPanel.addShape((Polygon)Box.randomBox(polyPanel.getWidth(),polyPanel.getHeight()));
			}
		});
		menuBoxes.add(mntmBox);
		
		JMenuItem mntmBoxes = new JMenuItem("+5 boxes");
		mntmBoxes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int idx=0; idx<5; idx++)
					polyPanel.addShape((Polygon)Box.randomBox(polyPanel.getWidth(),polyPanel.getHeight()));
			}
		});
		menuBoxes.add(mntmBoxes);
		
		JMenuItem mntmBoxes_1 = new JMenuItem("+20 boxes");
		mntmBoxes_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int idx=0; idx<20; idx++)
					polyPanel.addShape((Polygon)Box.randomBox(polyPanel.getWidth(),polyPanel.getHeight()));
			}
		});
		menuBoxes.add(mntmBoxes_1);
		
		JSeparator separator_4 = new JSeparator();
		menuBoxes.add(separator_4);
		
		JMenuItem mntmStartOver_3 = new JMenuItem("start over");
		mntmStartOver_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Polygon> newPolys = new ArrayList<Polygon>();
				newPolys.add((Polygon)Box.randomBox(polyPanel.getWidth(), polyPanel.getHeight()));
				polyPanel.setShapes(newPolys);
			}
		});
		menuBoxes.add(mntmStartOver_3);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		// CIRCLE PANEL
		circlePanel = new CirclePanel();
		circlePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				circlePanel.explode(e.getX(), e.getY());
			}
		});
		contentPane.add(circlePanel);
		
		// OTHER SHAPES PANEL
		polyPanel = new PolyPanel();
		polyPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				polyPanel.explode(e.getX(), e.getY());
			}
		});
		contentPane.add(polyPanel);
	}
}
