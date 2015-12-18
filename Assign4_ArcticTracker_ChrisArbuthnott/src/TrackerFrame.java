import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.CardLayout;

import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;

import java.awt.Component;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

@SuppressWarnings("serial")
public class TrackerFrame extends JFrame {
	
	// Data class to manage Animal objects and File I/O.
	private ArcticDataManager data = new ArcticDataManager();
	
	// top level panels
	private JPanel contentPane;
	private JPanel panelSplash, panelCollect, panelDisplay;
	private JTabbedPane tabbedPane;
	
	// Images for dynamic use
	private ImageIcon[] animalPics = new ImageIcon[3];
	private JLabel lblAnimalImage;
	
	// Gui objects for dynamic use
	private JTextField penguinNameField;
	private JTextField penguinWeightField;
	private JTextField penguinBPField;
	private JComboBox<String> penguinGenderComboBox;
	private JTextField penguinGPSC;
	private JTextField penguinGPSB;
	private JTextField penguinGPSA;
	private JTextArea txtAreaPenguinInstruction;
	private JTextField seaLionNameField;
	private JTextField seaLionWeightField;
	private JComboBox<String> seaLionGenderComboBox;
	private JTextField seaLionSpotsField;
	private JTextField seaLionGPSA;
	private JTextField seaLionGPSB;
	private JTextField seaLionGPSC;
	private JTextArea txtAreaSeaLionInstruction;
	private JTextField walrusNameField;
	private JTextField walrusWeightField;
	private JComboBox<String> walrusGenderComboBox;
	private JComboBox<String> walrusDentalComboBox;
	private JTextField walrusGPSA;
	private JTextField walrusGPSB;
	private JTextField walrusGPSC;
	private JTextArea txtAreaWalrusInstruction;
	private JLabel lblDisplayIcon;
	private JList<String> listAnimals;
	private JTextArea txtAreaViewAnimal;
	
	// show the panel with the input name.
	private void showPanel(JPanel panel) {
		panelSplash.setVisible(false);
		panelCollect.setVisible(false);
		panelDisplay.setVisible(false);
		panel.setVisible(true);
	}
	
	private void closeApp() {
		setVisible(false);
		dispose();
	}
	
	// get list of created files from ArcticDataManager, use it
	// to populate list of available files.
	private void populateAnimalList() {
		String[] filenames = data.getAnimalFileNames();
		DefaultListModel<String> model = (DefaultListModel<String>)listAnimals.getModel();
		model.clear();
		for (int idx=0; idx<filenames.length; idx++)
			model.addElement(filenames[idx]);
		listAnimals.clearSelection();
		txtAreaViewAnimal.setText("Click a file to view animal details.");
	}
	
	// show details from file selected in list.
	private void displaySelectedAnimal() {
		txtAreaViewAnimal.setText("");
		String filename = listAnimals.getSelectedValue();
		String[] lines = {"Choose an Animal to view details."};
		if (filename != null)
			lines = data.getLinesFromFile(filename);
		for (int idx=0; idx<lines.length; idx++)
			txtAreaViewAnimal.append(lines[idx]+"\n");
		if (filename == null)
			lblDisplayIcon.setIcon(null);
		else if (filename.startsWith("Penguin"))
			lblDisplayIcon.setIcon(animalPics[0]);
		else if (filename.startsWith("SeaLion"))
			lblDisplayIcon.setIcon(animalPics[1]);
		else if (filename.startsWith("Walrus"))
			lblDisplayIcon.setIcon(animalPics[2]);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrackerFrame frame = new TrackerFrame();
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
	public TrackerFrame() {
		
		// load animal images.
		animalPics[0] = new ImageIcon(getClass().getResource("/images/penguin.jpg"));
		animalPics[1] = new ImageIcon(getClass().getResource("/images/sealion.jpg"));
		animalPics[2] = new ImageIcon(getClass().getResource("/images/walrus2.jpg"));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 348);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		panelSplash = new JPanel();
		contentPane.add(panelSplash, "name_22364962720600");
		panelSplash.setLayout(null);
		
		JButton btnSplashToCollect = new JButton("Record Data");
		btnSplashToCollect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showPanel(panelCollect);
			}
		});
		btnSplashToCollect.setFont(new Font("Arial", Font.BOLD, 12));
		btnSplashToCollect.setBounds(28, 222, 104, 44);
		panelSplash.add(btnSplashToCollect);
		
		JButton btnSplashToDisplay = new JButton("View Data");
		btnSplashToDisplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				populateAnimalList();
				showPanel(panelDisplay);
			}
		});
		btnSplashToDisplay.setFont(new Font("Arial", Font.BOLD, 12));
		btnSplashToDisplay.setBounds(160, 222, 104, 44);
		panelSplash.add(btnSplashToDisplay);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeApp();
			}
		});
		btnClose.setFont(new Font("Arial", Font.BOLD, 12));
		btnClose.setBounds(292, 222, 104, 44);
		panelSplash.add(btnClose);
		
		JLabel lblNewLabel_1 = new JLabel("Arctic Tracker");
		lblNewLabel_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		lblNewLabel_1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 28));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(23, 26, 234, 50);
		panelSplash.add(lblNewLabel_1);
		
		JLabel lblSplashBackground = new JLabel("");
		lblSplashBackground.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblSplashBackground.setBounds(0, 0, 424, 300);
		lblSplashBackground.setIcon(new ImageIcon(getClass().getResource("/images/arctic.jpg")));
		panelSplash.add(lblSplashBackground);
		
		panelCollect = new JPanel();
		contentPane.add(panelCollect, "name_22367145892531");
		panelCollect.setLayout(null);
		
		JButton btnCollectToDisplay = new JButton("View Data");
		btnCollectToDisplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				populateAnimalList();
				showPanel(panelDisplay);
			}
		});
		btnCollectToDisplay.setFont(new Font("Arial", Font.BOLD, 12));
		btnCollectToDisplay.setBounds(313, 218, 101, 30);
		panelCollect.add(btnCollectToDisplay);
		
		JButton btnCollectToSplash = new JButton("Home");
		btnCollectToSplash.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPanel(panelSplash);
			}
		});
		btnCollectToSplash.setFont(new Font("Arial", Font.BOLD, 12));
		btnCollectToSplash.setBounds(313, 259, 101, 30);
		panelCollect.add(btnCollectToSplash);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		tabbedPane.setBounds(10, 28, 285, 261);
		panelCollect.add(tabbedPane);
		
		JPanel panelPenguin = new JPanel();
		tabbedPane.addTab("Penguin", null, panelPenguin, null);
		panelPenguin.setLayout(null);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblName.setBounds(10, 11, 40, 14);
		panelPenguin.add(lblName);
		
		JLabel lblNewLabel = new JLabel("Weight (kg):");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(126, 39, 71, 14);
		panelPenguin.add(lblNewLabel);
		
		penguinNameField = new JTextField();
		penguinNameField.setBounds(48, 8, 222, 20);
		panelPenguin.add(penguinNameField);
		penguinNameField.setColumns(10);
		
		penguinGenderComboBox = new JComboBox<String>();
		penguinGenderComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Select Gender", "Female", "Male"}));
		penguinGenderComboBox.setBounds(10, 36, 106, 20);
		panelPenguin.add(penguinGenderComboBox);
		
		penguinWeightField = new JTextField();
		penguinWeightField.setBounds(199, 36, 71, 20);
		panelPenguin.add(penguinWeightField);
		penguinWeightField.setColumns(10);
		
		JLabel lblBloodPressure = new JLabel("Blood Pressure (ex 130/80):");
		lblBloodPressure.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBloodPressure.setBounds(10, 67, 155, 14);
		panelPenguin.add(lblBloodPressure);
		
		penguinBPField = new JTextField();
		penguinBPField.setBounds(166, 64, 104, 20);
		panelPenguin.add(penguinBPField);
		penguinBPField.setColumns(10);
		
		JLabel lblGpsCoordinates = new JLabel("GPS Coordinates:");
		lblGpsCoordinates.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblGpsCoordinates.setBounds(10, 101, 132, 14);
		panelPenguin.add(lblGpsCoordinates);
		
		JLabel lblNewLabel_2 = new JLabel("ex: -85.2, 126.1");
		lblNewLabel_2.setForeground(Color.GRAY);
		lblNewLabel_2.setBounds(10, 115, 112, 14);
		panelPenguin.add(lblNewLabel_2);
		
		penguinGPSC = new JTextField();
		penguinGPSC.setBounds(10, 197, 112, 20);
		panelPenguin.add(penguinGPSC);
		penguinGPSC.setColumns(10);
		
		penguinGPSB = new JTextField();
		penguinGPSB.setBounds(10, 166, 112, 20);
		panelPenguin.add(penguinGPSB);
		penguinGPSB.setColumns(10);
		
		penguinGPSA = new JTextField();
		penguinGPSA.setBounds(10, 135, 112, 20);
		panelPenguin.add(penguinGPSA);
		penguinGPSA.setColumns(10);
		
		JButton btnPenguinSaveData = new JButton("Save Data");
		btnPenguinSaveData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// pass form data to ArcticDataManager, display returned message.
				String result = data.savePenguin(penguinNameField.getText(),
						(String)penguinGenderComboBox.getSelectedItem(),
						penguinWeightField.getText(), penguinBPField.getText(),
						penguinGPSA.getText(), penguinGPSB.getText(), penguinGPSC.getText());
				txtAreaPenguinInstruction.setText(result);
			}
		});
		btnPenguinSaveData.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnPenguinSaveData.setBounds(138, 186, 132, 36);
		panelPenguin.add(btnPenguinSaveData);
		
		txtAreaPenguinInstruction = new JTextArea();
		txtAreaPenguinInstruction.setRequestFocusEnabled(false);
		txtAreaPenguinInstruction.setEditable(false);
		txtAreaPenguinInstruction.setFocusable(false);
		txtAreaPenguinInstruction.setAlignmentX(Component.RIGHT_ALIGNMENT);
		txtAreaPenguinInstruction.setOpaque(false);
		txtAreaPenguinInstruction.setFont(new Font("Tahoma", Font.PLAIN, 9));
		txtAreaPenguinInstruction.setLineWrap(true);
		txtAreaPenguinInstruction.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		txtAreaPenguinInstruction.setWrapStyleWord(true);
		txtAreaPenguinInstruction.setText("Enter all data, including up to 3 GPS coordinate locations, then click SAVE DATA to record to file.");
		txtAreaPenguinInstruction.setBounds(138, 126, 132, 49);
		panelPenguin.add(txtAreaPenguinInstruction);
		
		JPanel panelSeaLion = new JPanel();
		tabbedPane.addTab("Sea Lion", null, panelSeaLion, null);
		panelSeaLion.setLayout(null);
		
		JLabel label = new JLabel("Name:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label.setBounds(10, 14, 40, 14);
		panelSeaLion.add(label);
		
		seaLionNameField = new JTextField();
		seaLionNameField.setColumns(10);
		seaLionNameField.setBounds(48, 11, 222, 20);
		panelSeaLion.add(seaLionNameField);
		
		seaLionGenderComboBox = new JComboBox<String>();
		seaLionGenderComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Select Gender", "Female", "Male"}));
		seaLionGenderComboBox.setBounds(10, 39, 106, 20);
		panelSeaLion.add(seaLionGenderComboBox);
		
		JLabel label_1 = new JLabel("Weight (kg):");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_1.setBounds(126, 42, 71, 14);
		panelSeaLion.add(label_1);
		
		seaLionWeightField = new JTextField();
		seaLionWeightField.setColumns(10);
		seaLionWeightField.setBounds(199, 39, 71, 20);
		panelSeaLion.add(seaLionWeightField);
		
		JLabel lblNumberOfSpots = new JLabel("Number of Spots:");
		lblNumberOfSpots.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNumberOfSpots.setBounds(10, 70, 106, 14);
		panelSeaLion.add(lblNumberOfSpots);
		
		seaLionSpotsField = new JTextField();
		seaLionSpotsField.setColumns(10);
		seaLionSpotsField.setBounds(113, 68, 104, 20);
		panelSeaLion.add(seaLionSpotsField);
		
		JLabel label_3 = new JLabel("GPS Coordinates:");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_3.setBounds(10, 104, 132, 14);
		panelSeaLion.add(label_3);
		
		JLabel label_4 = new JLabel("ex: -85.2, 126.1");
		label_4.setForeground(Color.GRAY);
		label_4.setBounds(10, 118, 112, 14);
		panelSeaLion.add(label_4);
		
		seaLionGPSA = new JTextField();
		seaLionGPSA.setColumns(10);
		seaLionGPSA.setBounds(10, 138, 112, 20);
		panelSeaLion.add(seaLionGPSA);
		
		seaLionGPSB = new JTextField();
		seaLionGPSB.setColumns(10);
		seaLionGPSB.setBounds(10, 169, 112, 20);
		panelSeaLion.add(seaLionGPSB);
		
		seaLionGPSC = new JTextField();
		seaLionGPSC.setColumns(10);
		seaLionGPSC.setBounds(10, 200, 112, 20);
		panelSeaLion.add(seaLionGPSC);
		
		txtAreaSeaLionInstruction = new JTextArea();
		txtAreaSeaLionInstruction.setEditable(false);
		txtAreaSeaLionInstruction.setFocusable(false);
		txtAreaSeaLionInstruction.setRequestFocusEnabled(false);
		txtAreaSeaLionInstruction.setWrapStyleWord(true);
		txtAreaSeaLionInstruction.setText("Enter all data, including up to 3 GPS coordinate locations, then click SAVE DATA to record to file.");
		txtAreaSeaLionInstruction.setOpaque(false);
		txtAreaSeaLionInstruction.setLineWrap(true);
		txtAreaSeaLionInstruction.setFont(new Font("Tahoma", Font.PLAIN, 9));
		txtAreaSeaLionInstruction.setAlignmentY(1.0f);
		txtAreaSeaLionInstruction.setAlignmentX(1.0f);
		txtAreaSeaLionInstruction.setBounds(138, 129, 132, 49);
		panelSeaLion.add(txtAreaSeaLionInstruction);
		
		JButton btnSeaLionSaveData = new JButton("Save Data");
		btnSeaLionSaveData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// pass form data to ArcticDataManager, display returned message.
				String result = data.saveSeaLion(seaLionNameField.getText(),
						(String)seaLionGenderComboBox.getSelectedItem(),
						seaLionWeightField.getText(), seaLionSpotsField.getText(),
						seaLionGPSA.getText(), seaLionGPSB.getText(), seaLionGPSC.getText());
				txtAreaSeaLionInstruction.setText(result);
			}
		});
		btnSeaLionSaveData.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSeaLionSaveData.setBounds(138, 189, 132, 36);
		panelSeaLion.add(btnSeaLionSaveData);
		
		JPanel panelWalrus = new JPanel();
		tabbedPane.addTab("Walrus", null, panelWalrus, null);
		panelWalrus.setLayout(null);
		
		JLabel label_5 = new JLabel("Name:");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_5.setBounds(10, 14, 40, 14);
		panelWalrus.add(label_5);
		
		walrusNameField = new JTextField();
		walrusNameField.setColumns(10);
		walrusNameField.setBounds(48, 11, 222, 20);
		panelWalrus.add(walrusNameField);
		
		walrusGenderComboBox = new JComboBox<String>();
		walrusGenderComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Select Gender", "Female", "Male"}));
		walrusGenderComboBox.setBounds(10, 39, 106, 20);
		panelWalrus.add(walrusGenderComboBox);
		
		JLabel label_6 = new JLabel("Weight (kg):");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_6.setBounds(126, 42, 71, 14);
		panelWalrus.add(label_6);
		
		walrusWeightField = new JTextField();
		walrusWeightField.setColumns(10);
		walrusWeightField.setBounds(199, 39, 71, 20);
		panelWalrus.add(walrusWeightField);
		
		JLabel label_8 = new JLabel("GPS Coordinates:");
		label_8.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_8.setBounds(10, 104, 132, 14);
		panelWalrus.add(label_8);
		
		JLabel label_9 = new JLabel("ex: -85.2, 126.1");
		label_9.setForeground(Color.GRAY);
		label_9.setBounds(10, 118, 112, 14);
		panelWalrus.add(label_9);
		
		walrusGPSA = new JTextField();
		walrusGPSA.setColumns(10);
		walrusGPSA.setBounds(10, 138, 112, 20);
		panelWalrus.add(walrusGPSA);
		
		walrusGPSB = new JTextField();
		walrusGPSB.setColumns(10);
		walrusGPSB.setBounds(10, 169, 112, 20);
		panelWalrus.add(walrusGPSB);
		
		walrusGPSC = new JTextField();
		walrusGPSC.setColumns(10);
		walrusGPSC.setBounds(10, 200, 112, 20);
		panelWalrus.add(walrusGPSC);
		
		txtAreaWalrusInstruction = new JTextArea();
		txtAreaWalrusInstruction.setFocusable(false);
		txtAreaWalrusInstruction.setRequestFocusEnabled(false);
		txtAreaWalrusInstruction.setEditable(false);
		txtAreaWalrusInstruction.setWrapStyleWord(true);
		txtAreaWalrusInstruction.setText("Enter all data, including up to 3 GPS coordinate locations, then click SAVE DATA to record to file.");
		txtAreaWalrusInstruction.setOpaque(false);
		txtAreaWalrusInstruction.setLineWrap(true);
		txtAreaWalrusInstruction.setFont(new Font("Tahoma", Font.PLAIN, 9));
		txtAreaWalrusInstruction.setAlignmentY(1.0f);
		txtAreaWalrusInstruction.setAlignmentX(1.0f);
		txtAreaWalrusInstruction.setBounds(138, 129, 132, 49);
		panelWalrus.add(txtAreaWalrusInstruction);
		
		JButton btnWalrusSaveData = new JButton("Save Data");
		btnWalrusSaveData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// pass form data to ArcticDataManager, display returned message.
				String result = data.saveWalrus(walrusNameField.getText(),
						(String)walrusGenderComboBox.getSelectedItem(),
						walrusWeightField.getText(), (String)walrusDentalComboBox.getSelectedItem(),
						walrusGPSA.getText(), walrusGPSB.getText(), walrusGPSC.getText());
				txtAreaWalrusInstruction.setText(result);
			}
		});
		btnWalrusSaveData.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnWalrusSaveData.setBounds(138, 189, 132, 36);
		panelWalrus.add(btnWalrusSaveData);
		
		walrusDentalComboBox = new JComboBox<String>();
		walrusDentalComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Dental Health", "Good", "Average", "Poor"}));
		walrusDentalComboBox.setBounds(10, 70, 132, 20);
		panelWalrus.add(walrusDentalComboBox);
		
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				// change the animal image on display.
				lblAnimalImage.setIcon(animalPics[tabbedPane.getSelectedIndex()]);
			}
		});
		
		JLabel lblAnimalType = new JLabel("Enter Animal Data");
		lblAnimalType.setHorizontalAlignment(SwingConstants.CENTER);
		lblAnimalType.setFont(new Font("Arial", Font.BOLD, 16));
		lblAnimalType.setBounds(87, 0, 233, 30);
		panelCollect.add(lblAnimalType);
		
		lblAnimalImage = new JLabel("");
		lblAnimalImage.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblAnimalImage.setBounds(313, 51, 101, 140);
		lblAnimalImage.setIcon(animalPics[0]);
		panelCollect.add(lblAnimalImage);
		
		panelDisplay = new JPanel();
		contentPane.add(panelDisplay, "name_22369098121726");
		panelDisplay.setLayout(null);
		
		JButton btnDisplayToCollect = new JButton("Enter Data");
		btnDisplayToCollect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPanel(panelCollect);
			}
		});
		btnDisplayToCollect.setFont(new Font("Arial", Font.BOLD, 12));
		btnDisplayToCollect.setBounds(313, 218, 101, 30);
		panelDisplay.add(btnDisplayToCollect);
		
		JButton btnDisplayToSplash = new JButton("Home");
		btnDisplayToSplash.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPanel(panelSplash);
			}
		});
		btnDisplayToSplash.setFont(new Font("Arial", Font.BOLD, 12));
		btnDisplayToSplash.setBounds(313, 259, 101, 30);
		panelDisplay.add(btnDisplayToSplash);
		
		lblDisplayIcon = new JLabel("");
		lblDisplayIcon.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblDisplayIcon.setBounds(313, 38, 101, 140);
		panelDisplay.add(lblDisplayIcon);
		
		JLabel lblAnimalsOnFile = new JLabel("Animals on File:");
		lblAnimalsOnFile.setHorizontalAlignment(SwingConstants.CENTER);
		lblAnimalsOnFile.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAnimalsOnFile.setBounds(10, 11, 114, 20);
		panelDisplay.add(lblAnimalsOnFile);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 51, 114, 238);
		panelDisplay.add(scrollPane);
		
		listAnimals = new JList<String>();
		listAnimals.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				displaySelectedAnimal();
			}
		});
		listAnimals.setModel(new DefaultListModel<String>());
		scrollPane.setViewportView(listAnimals);
		listAnimals.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JLabel lblclickToView = new JLabel("(click to view)");
		lblclickToView.setForeground(Color.GRAY);
		lblclickToView.setHorizontalAlignment(SwingConstants.CENTER);
		lblclickToView.setBounds(10, 30, 114, 14);
		panelDisplay.add(lblclickToView);
		
		txtAreaViewAnimal = new JTextArea();
		txtAreaViewAnimal.setLineWrap(true);
		txtAreaViewAnimal.setWrapStyleWord(true);
		txtAreaViewAnimal.setRequestFocusEnabled(false);
		txtAreaViewAnimal.setEditable(false);
		txtAreaViewAnimal.setOpaque(false);
		txtAreaViewAnimal.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtAreaViewAnimal.setText("Species: Penguin\r\nName: Jimmy O'Halloran\r\nKnown Locations (latitude, longitude):");
		txtAreaViewAnimal.setBounds(145, 51, 149, 238);
		panelDisplay.add(txtAreaViewAnimal);
	}
}
