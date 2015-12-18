	/**
	 * Game: Minion Battle.
	 * Created by Chris Arbuthnott
	 * for PROG 1400, Assignment 3
	 * Player vs Player assignment
	 * March 9th, 2015
	 * 
	 * Images created by Lorc,
	 * colored or modified by Chris Arbuthnott
	 * freely available for use at:
	 * http://game-icons.net/
	 */

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Color;
import java.awt.Component;
import javax.swing.border.LineBorder;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Insets;
import javax.swing.JScrollPane;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class PvPFrame extends JFrame {

	// TOP LEVEL PANELS / VARIABLES
	private boolean twoPlayer = false;
	private Master player, enemy;
	private JPanel contentPane;
	private final JPanel panelSplash, panelRules, panelCreate, panelBuy, panelFight, panelEnd;
	
	// IMAGES FOR DYNAMIC USE
	private final ImageIcon[] itemImages = new ImageIcon[15];
	private final ImageIcon[] playerImages85 = new ImageIcon[7];
	private final ImageIcon[] playerImages150 = new ImageIcon[7];
	private final ImageIcon[] minionImages50 = new ImageIcon[7];
	private final ImageIcon[] minionImages85 = new ImageIcon[7];
	private final ImageIcon[] minionImages150 = new ImageIcon[7];
	
	// CHARACTER CREATION VARIABLES
	private int createPlayerImgIndex, createEnemyImgIndex, createPlayerNumAtt, createEnemyNumAtt,
		createPlayerAttBns, createEnemyAttBns, createPlayerDef, createEnemyDef,
		createPlayerMaxHP, createEnemyMaxHP, createPlayerGold, createEnemyGold,
		createPlayerTraitsChosen, createEnemyTraitsChosen;
	private boolean createPlayerDone, createEnemyDone;
	
	// CHARACTER CREATE PANEL GUI OBJECTS
	private final JLabel lblCreateLeftIcon, lblCreateRightIcon, lblYourEnemy,
		lblCreateLeftNumAttacks, lblCreateLeftAttBonus, lblCreateLeftDefense,
		lblCreateLeftMaxHP, lblCreateLeftGold,
		lblCreateRightNumAttacks, lblCreateRightAttBonus, lblCreateRightDefense,
		lblCreateRightMaxHP, lblCreateRightGold;
	private final JButton btnCreateLeftDone, btnCreateRightDone, btnCreateLeftChangeIcon, btnCreateRightChangeIcon;
	private final JCheckBox chckbxCreateLeftRich, chckbxCreateLeftFast, chckbxCreateLeftTough,
		chckbxCreateLeftSkilled, chckbxCreateLeftStrong;
	private final JCheckBox[] chckbxCreateLeftArray = new JCheckBox[5];
	private final JCheckBox chckbxCreateRightRich, chckbxCreateRightFast, chckbxCreateRightTough,
		chckbxCreateRightSkilled, chckbxCreateRightStrong;
	private final JCheckBox[] chckbxCreateRightArray = new JCheckBox[5];
	
	// STORE PANEL VARIABLES:
	private Master shopper;
	private final Minion[] storeMinions = new Minion[10];
	private final Armament[] storeArms = new Armament[10];
	
	// STORE PANEL GUI OBJECTS
	private final JButton btnStoreDone;
	private final JLabel lblStoreMainIcon, lblStoreGoldValue, lblStoreNumAttValue,
		lblStoreAttBnsValue, lblStoreDefValue, lblStoreMaxHPValue;
	
	private final JLabel[] storeOwnedItemIconLabels = new JLabel[5];
	private final JLabel[] storeOwnedMinionIconLabels = new JLabel[5];
	
	private final JLabel[] storeItemIconLabels = new JLabel[10];
	private final JLabel[] storeItemNumAttValues = new JLabel[10];
	private final JLabel[] storeItemAttBnsValues = new JLabel[10];
	private final JLabel[] storeItemDefValues = new JLabel[10];
	private final JLabel[] storeItemMaxHPValues = new JLabel[10];
	private final JLabel[] storeItemCostValues = new JLabel[10];
	private final JButton[] storeItemBuyButtons = new JButton[10];
	
	private final JLabel[] storeMinionIconLabels = new JLabel[10];
	private final JLabel[] storeMinionNumAttValues = new JLabel[10];
	private final JLabel[] storeMinionAttBnsValues = new JLabel[10];
	private final JLabel[] storeMinionDefValues = new JLabel[10];
	private final JLabel[] storeMinionMaxHPValues = new JLabel[10];
	private final JLabel[] storeMinionCostValues = new JLabel[10];
	private final JButton[] storeMinionBuyButtons = new JButton[10];

	// FIGHT PANEL VARIABLES
	private int activePlayerIndex = -2; 
	private int activeEnemyIndex = -2;
	private Combatant playerFighter, enemyFighter, currentAttacker, currentDefender;
	private boolean fightSelectionEnabled = true;
	
	// FIGHT PANEL GUI OBJECTS:
	private final JLabel lblFightEnemyIconLabel, lblFightPlayerActiveLabel, lblFightEnemyActiveLabel, lblFightPlayerIconLabel;
	private final JLabel lblFightPlayerNumAttValue, lblFightPlayerAttBnsValue, lblFightPlayerDefValue, lblFightPlayerMaxHPValue;
	private final JLabel lblFightEnemyNumAttValue, lblFightEnemyAttBnsValue, lblFightEnemyDefValue, lblFightEnemyMaxHPValue;
	private final JLabel lblFightInstruction;
	private final JButton btnFightButton;
	private final JTextArea textArea;
	
	private final JLabel[] fightPlayerItemIconLabels = new JLabel[5];
	private final JLabel[] fightEnemyItemIconLabels = new JLabel[5];
	
	private final JLabel[] fightPlayerMinionIconLabels = new JLabel[5];
	private final JLabel[] fightPlayerMinionNumAttValues = new JLabel[5];
	private final JLabel[] fightPlayerMinionAttBnsValues = new JLabel[5];
	private final JLabel[] fightPlayerMinionDefValues = new JLabel[5];
	private final JLabel[] fightPlayerMinionMaxHPValues = new JLabel[5];
	
	private final JLabel[] fightEnemyMinionIconLabels = new JLabel[5];
	private final JLabel[] fightEnemyMinionNumAttValues = new JLabel[5];
	private final JLabel[] fightEnemyMinionAttBnsValues = new JLabel[5];
	private final JLabel[] fightEnemyMinionDefValues = new JLabel[5];
	private final JLabel[] fightEnemyMinionMaxHPValues = new JLabel[5];
	
	// END PANEL VARIABLES AND OBJECTS
	private final JLabel lblEndMessage;
	private final JButton btnEndRestart;
	
	// STATIC SUPPORT FOR RANDOM NUMBERS
	protected static final java.util.Random rand= new java.util.Random();
	public static int getRandom(int minVal, int maxVal) {
		return minVal + rand.nextInt(1+maxVal-minVal);
	}
	
	// TIMERHANDLER CLASS FOR THE BATTLES, WITH ASSOCIATED VARIABLES AND METHODS
	private final int ANIMATION_DELAY = 1500;
	private final Timer timer = new Timer(ANIMATION_DELAY, new TimerHandler());
	private String[] playByPlay = {};
	private int playByPlayPointer = 0;
	private JButton btnNewButton;
	
	
	private class TimerHandler implements ActionListener {
		@Override public void actionPerformed(ActionEvent ae) {
			tick();
		}// end action performed method.
	}// an inner class!!
	
	// function called by timer.
	private void tick() {
		textArea.append(playByPlay[playByPlayPointer]);
		playByPlayPointer++;
		if (playByPlayPointer >= playByPlay.length) {
			timer.stop();
			fightEnd();
		}
	}
	
	// load images into arrays for use.
	private void loadImages() {
		String[] playerFileNames = {"PvPMasterA.png","PvPMasterB.png","PvPMasterC.png","PvPMasterD.png",
				"PvPMasterE.png","PvPMasterF.png","PvPMasterG.png"};
		String[] minionFileNames = {"PvPFairy.png","PvPFrogman.png","PvPDragon.png","PvPHarpy.png",
				"PvPUnicorn.png","PvPMinotaur.png","PvPGoblin.png"};
		String[] itemFileNames = {"PvPAmulet.png","PvPArmor.png","PvPAxe.png","PvPBook.png","PvPBoots.png",
				"PvPBow.png","PvPDagger.png","PvPGauntlet.png","PvPHammer.png","PvPHelmet.png",
				"PvPPotion.png","PvPShield.png","PvPSpear.png","PvPSword.png","PvPWand.png",};
		ImageIcon unscaledImage;
		String pathString;
		for (int idx=0; idx<7; idx++) {
			pathString = "/images/"+playerFileNames[idx];
			unscaledImage = new ImageIcon(getClass().getResource(pathString));
			playerImages85[idx] = new ImageIcon(unscaledImage.getImage().getScaledInstance(77,77,Image.SCALE_SMOOTH));
			playerImages150[idx] = new ImageIcon(unscaledImage.getImage().getScaledInstance(142,142,Image.SCALE_SMOOTH));
		}
		for (int idx=0; idx<7; idx++) {
			pathString = "/images/"+minionFileNames[idx];
			unscaledImage = new ImageIcon(getClass().getResource(pathString));
			minionImages50[idx] = new ImageIcon(unscaledImage.getImage().getScaledInstance(48,48,Image.SCALE_SMOOTH));
			minionImages85[idx] = new ImageIcon(unscaledImage.getImage().getScaledInstance(77,77,Image.SCALE_SMOOTH));
			minionImages150[idx] = new ImageIcon(unscaledImage.getImage().getScaledInstance(142,142,Image.SCALE_SMOOTH));
		}
		for (int idx=0; idx<15; idx++) {
			pathString = "/images/"+itemFileNames[idx];
			unscaledImage = new ImageIcon(getClass().getResource(pathString));
			itemImages[idx] = new ImageIcon(unscaledImage.getImage().getScaledInstance(48,48,Image.SCALE_SMOOTH));
		}
	}
	
	
	////////////////////////////
	// TOP-LEVEL METHODS
	////////////////////////////
	
	// make the input panel visible.
	private void showPanel(JPanel panel) {
		panelSplash.setVisible(false);
		panelRules.setVisible(false);
		panelCreate.setVisible(false);
		panelBuy.setVisible(false);
		panelFight.setVisible(false);
		panelEnd.setVisible(false);
		panel.setVisible(true);
	}
	

	// close the application.
	private void closeApp() {
		setVisible(false);
		dispose();
	}
	
	// reset game variables to raw start values.
	private void restart() {
		player = null;
		enemy = null;
		shopper = null;
		playerFighter = null;
		enemyFighter = null;
		lblFightPlayerIconLabel.setIcon(null);
		lblFightEnemyIconLabel.setIcon(null);
		activePlayerIndex = -2;
		activeEnemyIndex = -2;
		resetCreationValues();
		fightSelectionEnabled = true;
		btnFightButton.setText("FIGHT!");
		btnFightButton.setEnabled(false);
		
		showPanel(panelSplash);
	}
	
	// resets all variables, except player stats, to initial values.
	private void restartAfterWin() {
		enemy = null;
		shopper = null;
		playerFighter = null;
		enemyFighter = null;
		lblFightPlayerIconLabel.setIcon(null);
		lblFightEnemyIconLabel.setIcon(null);
		activePlayerIndex = -2;
		activeEnemyIndex = -2;
		
		fightSelectionEnabled = true;
		btnFightButton.setText("FIGHT!");
		btnFightButton.setEnabled(false);
		
		player.heal();
		for (int idx=0; idx<5; idx++)
			if (player.getMinions()[idx] != null)
				player.getMinions()[idx].heal();
		
		// reset selected Character Creation Values.
		createEnemyImgIndex = 1;
		createEnemyNumAtt = 3;
		createEnemyAttBns = 2;
		createEnemyDef = 11;
		createEnemyMaxHP = 10;
		createEnemyGold = 1000;
		createEnemyTraitsChosen = 0;
		
		createPlayerNumAtt = player.getNumOfAttacks();
		createPlayerAttBns = player.getAttackBonus();
		createPlayerDef = player.getDefense();
		createPlayerMaxHP = player.getMaxHP();
		createPlayerGold = player.getGold();
		createPlayerDone = true;
		
		for (int idx=0; idx<5; idx++) {
			chckbxCreateLeftArray[idx].setEnabled(false);
			chckbxCreateRightArray[idx].setSelected(false);
			chckbxCreateRightArray[idx].setEnabled(true);
		}
		btnCreateLeftDone.setEnabled(false);
		btnCreateRightDone.setEnabled(false);
		
		if (twoPlayer)
			showPanel(panelCreate);
		else {
			randomizeEnemy();
			enemy = new Master("The Enemy", createEnemyNumAtt, createEnemyAttBns,
					createEnemyDef, createEnemyMaxHP, createEnemyGold, 
					playerImages85[createEnemyImgIndex], 
					playerImages150[createEnemyImgIndex]);
			
			refillShop();
			shopper = enemy;
			enemyRandomShop();
			
			refillShop();
			displayShop();
			shopper = player;
			displayShopper();
			showPanel(panelBuy);
		}
	}
	
	
	///////////////////////////
	// CREATE CHARACTER METHODS
	///////////////////////////
	
	// reset character creation variables to start values.
	private void resetCreationValues() {
		createPlayerImgIndex = 0;
		createPlayerNumAtt = 3;
		createPlayerAttBns = 2;
		createPlayerDef = 11;
		createPlayerMaxHP = 10;
		createPlayerGold = 1000;
		
		createEnemyImgIndex = 1;
		createEnemyNumAtt = 3;
		createEnemyAttBns = 2;
		createEnemyDef = 11;
		createEnemyMaxHP = 10;
		createEnemyGold = 1000;
		
		createPlayerTraitsChosen = 0;
		createEnemyTraitsChosen = 0;
		for (int idx=0; idx<5; idx++) {
			chckbxCreateLeftArray[idx].setSelected(false);
			chckbxCreateLeftArray[idx].setEnabled(true);
			chckbxCreateRightArray[idx].setSelected(false);
			chckbxCreateRightArray[idx].setEnabled(true);
		}
		createPlayerDone = false;
		createEnemyDone = false;
	}
	
	// enter info from variables into Character Creation GUI.
	private void displayCreationValues() {
		//lblCreateLeftNumAttacks, lblCreateLeftAttBonus, lblCreateLeftDefense,
		//lblCreateLeftMaxHP, lblCreateLeftGold
		
		lblCreateLeftIcon.setIcon(playerImages150[createPlayerImgIndex]);
		lblCreateRightIcon.setIcon(playerImages150[createEnemyImgIndex]);
		
		lblCreateLeftNumAttacks.setText(""+createPlayerNumAtt);
		if (createPlayerAttBns > 0)
			lblCreateLeftAttBonus.setText("+"+createPlayerAttBns);
		else
			lblCreateLeftAttBonus.setText(""+createPlayerAttBns);
		lblCreateLeftDefense.setText(""+createPlayerDef);
		lblCreateLeftMaxHP.setText(""+createPlayerMaxHP);
		lblCreateLeftGold.setText(""+createPlayerGold);
		
		lblCreateRightNumAttacks.setText(""+createEnemyNumAtt);
		if (createEnemyAttBns > 0)
			lblCreateRightAttBonus.setText("+"+createEnemyAttBns);
		else
			lblCreateRightAttBonus.setText(""+createEnemyAttBns);
		lblCreateRightDefense.setText(""+createEnemyDef);
		lblCreateRightMaxHP.setText(""+createEnemyMaxHP);
		lblCreateRightGold.setText(""+createEnemyGold);
	}
	
	// for 1-player, choose random traits for the enemy.
	private void randomizeEnemy() {
		int traitOneIdx = getRandom(0,4);
		int traitTwoIdx = getRandom(0,4);
		while (traitTwoIdx == traitOneIdx)
			traitTwoIdx = getRandom(0,4);
		chckbxCreateRightArray[traitOneIdx].setSelected(true);
		chckbxCreateRightArray[traitTwoIdx].setSelected(true);
		chckbxCreateRightArray[traitOneIdx].setEnabled(false);
		chckbxCreateRightArray[traitTwoIdx].setEnabled(false);
		btnCreateRightDone.setEnabled(false);
		btnCreateRightChangeIcon.setEnabled(false);
		createEnemyDone = true;
	}
	
	// for 'true' input, enables DONE button and disables additional checkboxes.
	// reverse for 'false' input.
	private void enableCreatePlayerDone(boolean enable) {
		btnCreateLeftDone.setEnabled(enable);
		for (int idx=0; idx<5; idx++) {
			if (!chckbxCreateLeftArray[idx].isSelected())
				chckbxCreateLeftArray[idx].setEnabled(!enable);
		}
	}
	

	// for 'true' input, enables DONE button and disables additional checkboxes.
	// reverse for false.
	private void enableCreateEnemyDone(boolean enable) {
		btnCreateRightDone.setEnabled(enable);
		for (int idx=0; idx<5; idx++) {
			if (!chckbxCreateRightArray[idx].isSelected())
				chckbxCreateRightArray[idx].setEnabled(!enable);
		}
	}
	
	// create the player and enemy Master Objects using creation variables.
	private void createMasters() {
		player = new Master("The Hero", createPlayerNumAtt, createPlayerAttBns,
				createPlayerDef, createPlayerMaxHP, createPlayerGold, 
				playerImages85[createPlayerImgIndex], 
				playerImages150[createPlayerImgIndex]);
		enemy = new Master("The Enemy", createEnemyNumAtt, createEnemyAttBns,
				createEnemyDef, createEnemyMaxHP, createEnemyGold, 
				playerImages85[createEnemyImgIndex], 
				playerImages150[createEnemyImgIndex]);
	}
	
	
	/////////////////////////////
	// SHOP METHODS
	/////////////////////////////
	
	
	
	private Minion createRandomMinion() {
		int minionIndex = getRandom(0,6);
		switch (minionIndex) {
		case 0: return new Pixie(0, "Pixie");
		case 1: return new FrogMan(1, "Frogman");
		case 2: return new Dragon(2, "Dragon");
		case 3: return new Harpy(3, "Harpy");
		case 4: return new Unicorn(4, "Unicorn");
		case 5: return new Minotaur(5, "Minotaur");
		case 6: return new Goblin(6, "Goblin");
		default: return null;
		}
	}
		
	private Armament createRandomArmament() {
		return new Armament(getRandom(0,14));
	}
	
	// randomly populate the shop with minions and items for sale
	private void refillShop() {
		for (int idx=0; idx<10; idx++) {
			storeMinions[idx] = createRandomMinion();
			storeArms[idx] = createRandomArmament();
		}
		displayShop();
	}
	
	// display minions and items for sale
	private void displayShop() {
		Minion minion;
		for (int idx=0; idx<10; idx++) {
			minion = storeMinions[idx];
			if (minion != null) {
				storeMinionIconLabels[idx].setIcon(minionImages50[minion.getIndex()]);
				storeMinionNumAttValues[idx].setText(""+minion.getNumOfAttacks());
				if (minion.getAttackBonus() > 0)
					storeMinionAttBnsValues[idx].setText("+"+minion.getAttackBonus());
				else
					storeMinionAttBnsValues[idx].setText(""+minion.getAttackBonus());
				storeMinionDefValues[idx].setText(""+minion.getDefense());
				storeMinionMaxHPValues[idx].setText(""+minion.getMaxHP());
				storeMinionCostValues[idx].setText(""+minion.getCost());
			}
			else {
				storeMinionIconLabels[idx].setIcon(null);
				storeMinionNumAttValues[idx].setText("");
				storeMinionAttBnsValues[idx].setText("");
				storeMinionDefValues[idx].setText("");
				storeMinionMaxHPValues[idx].setText("");
				storeMinionCostValues[idx].setText("");
			}
			
			Armament arm = storeArms[idx];
			if (arm != null) {
				storeItemIconLabels[idx].setIcon(itemImages[arm.getIndex()]);
				storeItemNumAttValues[idx].setText(""+arm.getNumOfAttacksBonus());
				if (arm.getAttackBonus() > 0)
					storeItemAttBnsValues[idx].setText("+"+arm.getAttackBonus());
				else
					storeItemAttBnsValues[idx].setText(""+arm.getAttackBonus());
				storeItemDefValues[idx].setText(""+arm.getDefenseBonus());
				storeItemMaxHPValues[idx].setText(""+arm.getMaxHPBonus());
				storeItemCostValues[idx].setText(""+arm.getCost());
			}
			else {
				storeItemIconLabels[idx].setIcon(null);
				storeItemNumAttValues[idx].setText("");
				storeItemAttBnsValues[idx].setText("");
				storeItemDefValues[idx].setText("");
				storeItemMaxHPValues[idx].setText("");
				storeItemCostValues[idx].setText("");
			}
		}
	}
	
	// For 1-player, have enemy randomly buy some items and minions.
	private void enemyRandomShop() {
		for (int idx=0; idx<25; idx++) {
			if (getRandom(0,1) == 0)
				buyItem(getRandom(0,9));
			else
				buyMinion(getRandom(0,9));
		}
	}
	
	// display the shopper icon, stats, and purchased items and minions.
	private void displayShopper() {
		lblStoreMainIcon.setIcon(shopper.getIcon150());
		lblStoreNumAttValue.setText(""+shopper.getNumOfAttacks());
		if (shopper.getAttackBonus() > 0)
			lblStoreAttBnsValue.setText("+"+shopper.getAttackBonus());
		else
			lblStoreAttBnsValue.setText(""+shopper.getAttackBonus());
		lblStoreDefValue.setText(""+shopper.getDefense());
		lblStoreMaxHPValue.setText(""+shopper.getMaxHP());
		lblStoreGoldValue.setText(""+shopper.getGold());
		
		for (int idx=0; idx<5; idx++) {
			Minion minion = shopper.getMinions()[idx];
			if (minion != null)
				storeOwnedMinionIconLabels[idx].setIcon(minionImages50[minion.getIndex()]);
			else
				storeOwnedMinionIconLabels[idx].setIcon(null);
			Armament arm = shopper.getArms()[idx];
			if (arm != null)
				storeOwnedItemIconLabels[idx].setIcon(itemImages[arm.getIndex()]);
			else
				storeOwnedItemIconLabels[idx].setIcon(null);
		}
	}
	
	// shopper buys item (selected by index) from the shop.
	// checks for null items, and insufficient gold.
	private void buyItem(int itemIndex) {
		Armament arm = storeArms[itemIndex];
		if (arm != null) {
			if (shopper.spend(arm.getCost())) {
				if (shopper.addArmament(arm)) {
					displayShopper();
					storeArms[itemIndex] = null;
					displayShop();
				}
				else
					shopper.earn(arm.getCost());
			}
		}
	}
	
	// shopper buys minion (selected by index) from the shop.
	// checks for null minions, and insufficient gold.
	private void buyMinion(int minionIndex) {
		Minion minion = storeMinions[minionIndex];
		if (minion != null) {
			if (shopper.spend(minion.getCost())) {
				if (shopper.addMinion(minion)) {
					minion.setOwner(shopper);
					displayShopper();
					storeMinions[minionIndex] = null;
					displayShop();
				}
				else
					shopper.earn(minion.getCost());
			}
		}
	}
	
	
	/////////////////////////////
	// BATTLE METHODS
	/////////////////////////////	
	
	// In the fight panel, Display players, minions, items and stats.
	private void displayCombatants() {
		lblFightPlayerIconLabel.setIcon(player.getIcon85());
		lblFightPlayerNumAttValue.setText(""+player.getNumOfAttacks());
		if (player.getAttackBonus() > 0)
			lblFightPlayerAttBnsValue.setText("+"+player.getAttackBonus());
		else
			lblFightPlayerAttBnsValue.setText(""+player.getAttackBonus());
		lblFightPlayerDefValue.setText(""+player.getDefense());
		lblFightPlayerMaxHPValue.setText(""+player.getCurrentHP()+"/"+player.getMaxHP());
		
		lblFightEnemyIconLabel.setIcon(enemy.getIcon85());
		lblFightEnemyNumAttValue.setText(""+enemy.getNumOfAttacks());
		if (player.getAttackBonus() > 0)
			lblFightEnemyAttBnsValue.setText("+"+enemy.getAttackBonus());
		else
			lblFightEnemyAttBnsValue.setText(""+enemy.getAttackBonus());
		lblFightEnemyDefValue.setText(""+enemy.getDefense());
		lblFightEnemyMaxHPValue.setText(""+enemy.getCurrentHP()+"/"+enemy.getMaxHP());
		
		Minion minion;
		for (int idx=0; idx<5; idx++) {
			minion = player.getMinions()[idx];
			if (minion != null) {
				fightPlayerMinionIconLabels[idx].setIcon(minionImages85[minion.getIndex()]);
				fightPlayerMinionNumAttValues[idx].setText(""+minion.getNumOfAttacks());
				if (minion.getAttackBonus() > 0)
					fightPlayerMinionAttBnsValues[idx].setText("+"+minion.getAttackBonus());
				else
					fightPlayerMinionAttBnsValues[idx].setText(""+minion.getAttackBonus());
				fightPlayerMinionDefValues[idx].setText(""+minion.getDefense());
				fightPlayerMinionMaxHPValues[idx].setText(""+minion.getCurrentHP()+"/"+minion.getMaxHP());
			}
			else {
				fightPlayerMinionIconLabels[idx].setIcon(null);
				fightPlayerMinionNumAttValues[idx].setText("");
				fightPlayerMinionAttBnsValues[idx].setText("");
				fightPlayerMinionDefValues[idx].setText("");
				fightPlayerMinionMaxHPValues[idx].setText("");
			}
			
			minion = enemy.getMinions()[idx];
			if (minion != null) {
				fightEnemyMinionIconLabels[idx].setIcon(minionImages85[minion.getIndex()]);
				fightEnemyMinionNumAttValues[idx].setText(""+minion.getNumOfAttacks());
				if (minion.getAttackBonus() > 0)
					fightEnemyMinionAttBnsValues[idx].setText("+"+minion.getAttackBonus());
				else
					fightEnemyMinionAttBnsValues[idx].setText(""+minion.getAttackBonus());
				fightEnemyMinionDefValues[idx].setText(""+minion.getDefense());
				fightEnemyMinionMaxHPValues[idx].setText(""+minion.getCurrentHP()+"/"+minion.getMaxHP());
			}
			else {
				fightEnemyMinionIconLabels[idx].setIcon(null);
				fightEnemyMinionNumAttValues[idx].setText("");
				fightEnemyMinionAttBnsValues[idx].setText("");
				fightEnemyMinionDefValues[idx].setText("");
				fightEnemyMinionMaxHPValues[idx].setText("");
			}
		}
		
		Armament arm;
		for (int idx=0; idx<5; idx++) {
			arm = player.getArms()[idx];
			if (arm != null)
				fightPlayerItemIconLabels[idx].setIcon(itemImages[arm.getIndex()]);
			else
				fightPlayerItemIconLabels[idx].setIcon(null);
			
			arm = enemy.getArms()[idx];
			if (arm != null)
				fightEnemyItemIconLabels[idx].setIcon(itemImages[arm.getIndex()]);
			else
				fightEnemyItemIconLabels[idx].setIcon(null);
		}
		greyOutActiveMinions();
	}
	
	// grey out player and enemy icons that are the current active combatants.
	private void greyOutActiveMinions() {
		lblFightPlayerIconLabel.setEnabled(true);
		lblFightEnemyIconLabel.setEnabled(true);
		for (int idx=0; idx<5; idx++) {
			fightPlayerMinionIconLabels[idx].setEnabled(true);
			fightEnemyMinionIconLabels[idx].setEnabled(true);
		}
		if (activePlayerIndex == -1)
			lblFightPlayerIconLabel.setEnabled(false);
		else if (activePlayerIndex >= 0)
			fightPlayerMinionIconLabels[activePlayerIndex].setEnabled(false);
		
		if (activeEnemyIndex == -1)
			lblFightEnemyIconLabel.setEnabled(false);
		else if (activeEnemyIndex >= 0)
			fightEnemyMinionIconLabels[activeEnemyIndex].setEnabled(false);
	}
	
	// Moves player or player's minion to Active role.  Input index is -1 for
	// the player, and otherwise the index of the minion.
	private void setPlayerCombatant(int index) {
		if (index == -1) {
			activePlayerIndex = -1;
			playerFighter = player;
			lblFightPlayerActiveLabel.setIcon(player.getIcon150());
		}
		else {
			Minion minion = player.getMinions()[index];
			if (minion != null) {
				activePlayerIndex = index;
				playerFighter = minion;
				lblFightPlayerActiveLabel.setIcon(minionImages150[minion.getIndex()]);
			}
		}
		greyOutActiveMinions();
		if (enemyFighter != null && playerFighter != null) {
			lblFightInstruction.setText("Click Fight Button to Start!");
			btnFightButton.setEnabled(true);
		}
	}
	
	// Moves enemy or enemy's minion to active role.  Input index is -1 for
	// the enemy, and otherwise the index of the minion.
	private void setEnemyCombatant(int index) {
		if (index == -1) {
			activeEnemyIndex = -1;
			enemyFighter = enemy;
			lblFightEnemyActiveLabel.setIcon(enemy.getIcon150());
		}
		else {
			Minion minion = enemy.getMinions()[index];
			if (minion != null) {
				activeEnemyIndex = index;
				enemyFighter = minion;
				lblFightEnemyActiveLabel.setIcon(minionImages150[minion.getIndex()]);
			}
		}
		greyOutActiveMinions();
		if (enemyFighter != null && playerFighter != null) {
			lblFightInstruction.setText("Click Fight Button to Start!");
			btnFightButton.setEnabled(true);
		}
	}
	
	// For 1-player, randomly choose combatant for the enemy's side.
	private void chooseEnemyCombatant() {
		Combatant choice = null;
		int randomRoll;
		int index = -1;
		while (choice == null) {
			randomRoll = getRandom(0,10);
			if (randomRoll == 10) {
				choice = enemy;
				index = -1;
			}
			else {
				index = (int)(randomRoll/2);
				choice = enemy.getMinions()[index];
			}
		}
		setEnemyCombatant(index);
	}

	// calculate fight results, and start the timer to display the fight
	private void fightStart() {
		lblFightInstruction.setText("Fight in Progress!");
		btnFightButton.setEnabled(false);
		fightSelectionEnabled = false;
		if (activePlayerIndex >= 0 && activeEnemyIndex >= 0) {
			player.getMinions()[activePlayerIndex].checkBonuses(enemy.getMinions()[activeEnemyIndex]);
			enemy.getMinions()[activeEnemyIndex].checkBonuses(player.getMinions()[activePlayerIndex]);
		}
		if (getRandom(1,20)+playerFighter.getDefense()+playerFighter.getAttackBonus() >= 
				getRandom(1, 20)+enemyFighter.getDefense()+enemyFighter.getAttackBonus()) {
			currentAttacker = playerFighter;
			currentDefender = enemyFighter;
		}
		else {
			currentAttacker = enemyFighter;
			currentDefender = playerFighter;
		}
		while (playerFighter.getCurrentHP() > 0 && enemyFighter.getCurrentHP() > 0) {
			fightRound();
		}
		timer.start();
	}

	// Report on end of battle.
	private void fightEnd() {
		btnFightButton.setText("NEXT >>");
		btnFightButton.setEnabled(true);
		
		// losing side is currentAttacker.
		lblFightInstruction.setText(currentAttacker.getName() + " has lost.");
	}
	
	// Execute after end-of-fight messages are read.  May reset battle screen if only
	// minions died, or move to End Panel if player or enemy died.
	private void fightNextClick() {
		Combatant loser = currentAttacker;
		if (loser == player) {
			lblEndMessage.setText("You Lose\nBetter luck next time!");
			btnEndRestart.setText("Play\nAgain");
			showPanel(panelEnd);
		}
		else if (loser == enemy) {
			int winnings = enemy.getDefeatValue();
			player.earn(winnings);
			
			lblEndMessage.setText("You Win!\nCollect " + winnings + " Gold.");
			btnEndRestart.setText("Back to\nShop");
			showPanel(panelEnd);
		}
		else if (loser == playerFighter) { // player minion died
			player.removeMinion(activePlayerIndex);
			
			playerFighter = null;
			activePlayerIndex = -2;
			lblFightPlayerActiveLabel.setIcon(null);
			
			fightSelectionEnabled = true;
			displayCombatants();
			
			btnFightButton.setText("FIGHT!");
			btnFightButton.setEnabled(false);
			lblFightInstruction.setText("Choose your Combatant");
			
			if (!twoPlayer)
				chooseEnemyCombatant();
		}
		else { // enemy minion died
			enemy.removeMinion(activeEnemyIndex);
			
			enemyFighter = null;
			activeEnemyIndex = -2;
			lblFightEnemyActiveLabel.setIcon(null);
			
			fightSelectionEnabled = true;
			displayCombatants();
			
			btnFightButton.setText("FIGHT!");
			btnFightButton.setEnabled(false);
			lblFightInstruction.setText("Choose your Combatant");
			
			if (!twoPlayer)
				chooseEnemyCombatant();
		}
	}
	
	// Conduct one attack, record results to playByPlay array, then swap
	// attacker and defender.
	private void fightRound() {
		queueLine("\n"+currentAttacker.getAttackMessage()+"\n");
		int damage = currentDefender.applyAttacks(currentAttacker.getAttacks());
		if (damage == 0)
			queueLine(currentDefender.getDefenseMessage() + "\n");
		else {
			queueLine(String.format("Attack causes %d damage to %s!%n", damage, currentDefender.getName()));
			queueLine(currentDefender.getStatus() + "\n");
		}
		Combatant temp = currentAttacker;
		currentAttacker = currentDefender;
		currentDefender = temp;
	}
	
	// utility method to add a line to playByPlay array.
	private void queueLine(String line) {
		String[] newArr = new String[playByPlay.length + 1];
		for (int idx=0; idx<playByPlay.length; idx++)
			newArr[idx] = playByPlay[idx];
		newArr[playByPlay.length] = line;
		playByPlay = newArr;
	}
	
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PvPFrame frame = new PvPFrame();
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
	public PvPFrame() {
		loadImages();
		
		setVisible(true);
		setSize(new Dimension(600, 700));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		panelSplash = new JPanel();
		panelSplash.setVisible(false);
		contentPane.add(panelSplash, "name_5669030957429");
		panelSplash.setLayout(null);
		
		JButton btnSplashPlay1 = new JButton("1 Player");
		btnSplashPlay1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				twoPlayer = false;
				resetCreationValues();
				randomizeEnemy();
				
				displayCreationValues();
				showPanel(panelCreate);
			}
		});
		btnSplashPlay1.setMargin(new Insets(2, 4, 2, 4));
		btnSplashPlay1.setFont(new Font("Arial", Font.BOLD, 16));
		btnSplashPlay1.setBounds(140, 370, 89, 75);
		panelSplash.add(btnSplashPlay1);
		
		JButton btnSplashPlay2 = new JButton("2 Player");
		btnSplashPlay2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				twoPlayer = true;
				resetCreationValues();
				displayCreationValues();
				showPanel(panelCreate);
			}
		});
		btnSplashPlay2.setFont(new Font("Arial", Font.BOLD, 16));
		btnSplashPlay2.setMargin(new Insets(2, 4, 2, 4));
		btnSplashPlay2.setBounds(247, 370, 89, 75);
		panelSplash.add(btnSplashPlay2);
		
		JButton btnSplashClose = new JButton("Close");
		btnSplashClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closeApp();
			}
		});
		btnSplashClose.setFont(new Font("Arial", Font.BOLD, 16));
		btnSplashClose.setMargin(new Insets(2, 4, 2, 4));
		btnSplashClose.setBounds(354, 370, 89, 75);
		panelSplash.add(btnSplashClose);
		
		btnNewButton = new JButton("View Rules");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPanel(panelRules);
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 13));
		btnNewButton.setBounds(449, 536, 113, 28);
		panelSplash.add(btnNewButton);
		
		JLabel lblSplashBg = new JLabel("New label");
		lblSplashBg.setBounds(0, 0, 584, 662);
		lblSplashBg.setIcon(new ImageIcon(getClass().getResource("/images/PvPBackground.png")));
		panelSplash.add(lblSplashBg);
		
		///////////////////////////
		// CHARACTER CREATION PANEL
		///////////////////////////
		panelCreate = new JPanel();
		panelCreate.setVisible(false);
		
		panelRules = new JPanel();
		panelRules.setVisible(false);
		contentPane.add(panelRules, "name_9775085323996");
		panelRules.setLayout(null);
		
		JButton btnBackToStart = new JButton("Back to Start Screen");
		btnBackToStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPanel(panelSplash);
			}
		});
		btnBackToStart.setFont(new Font("Arial", Font.PLAIN, 13));
		btnBackToStart.setBounds(189, 596, 181, 43);
		panelRules.add(btnBackToStart);
		
		JLabel lblRulesBg = new JLabel("");
		lblRulesBg.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblRulesBg.setBounds(10, 11, 564, 640);
		lblRulesBg.setIcon(new ImageIcon(getClass().getResource("/images/PvPRulesBackground.png")));
		panelRules.add(lblRulesBg);
		contentPane.add(panelCreate, "name_5675246554263");
		panelCreate.setLayout(null);
		
		lblCreateLeftIcon = new JLabel("");
		lblCreateLeftIcon.setBorder(new LineBorder(Color.BLUE, 4));
		lblCreateLeftIcon.setBounds(64, 92, 150, 150);
		lblCreateLeftIcon.setIcon(playerImages150[0]);
		panelCreate.add(lblCreateLeftIcon);
		
		lblCreateRightIcon = new JLabel("");
		lblCreateRightIcon.setBorder(new LineBorder(Color.RED, 4));
		lblCreateRightIcon.setBounds(367, 92, 150, 150);
		lblCreateRightIcon.setIcon(playerImages150[1]);
		panelCreate.add(lblCreateRightIcon);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(0, 0, 0));
		separator.setOpaque(true);
		separator.setForeground(Color.BLACK);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(292, 26, 4, 610);
		panelCreate.add(separator);
		
		JLabel lblCreateYourCharacter = new JLabel("Create Your Character");
		lblCreateYourCharacter.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateYourCharacter.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblCreateYourCharacter.setFont(new Font("Arial", Font.BOLD, 20));
		lblCreateYourCharacter.setBounds(0, 11, 285, 53);
		panelCreate.add(lblCreateYourCharacter);
		
		lblYourEnemy = new JLabel("Your Enemy");
		lblYourEnemy.setFont(new Font("Arial", Font.BOLD, 20));
		lblYourEnemy.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourEnemy.setBounds(292, 11, 285, 53);
		panelCreate.add(lblYourEnemy);
		
		JTextPane txtpnNumberOf = new JTextPane();
		txtpnNumberOf.setOpaque(false);
		txtpnNumberOf.setFont(new Font("Arial", Font.BOLD, 9));
		txtpnNumberOf.setText("  # of\r\nAttacks");
		txtpnNumberOf.setBounds(17, 253, 37, 34);
		panelCreate.add(txtpnNumberOf);
		
		JTextPane txtpnAttackBonus = new JTextPane();
		txtpnAttackBonus.setText("Attack\r\nBonus");
		txtpnAttackBonus.setOpaque(false);
		txtpnAttackBonus.setFont(new Font("Arial", Font.BOLD, 9));
		txtpnAttackBonus.setBounds(74, 253, 37, 34);
		panelCreate.add(txtpnAttackBonus);
		
		JTextPane txtpnDefense = new JTextPane();
		txtpnDefense.setText("\r\nDefense");
		txtpnDefense.setOpaque(false);
		txtpnDefense.setFont(new Font("Arial", Font.BOLD, 9));
		txtpnDefense.setBounds(124, 253, 42, 34);
		panelCreate.add(txtpnDefense);
		
		JTextPane txtpnMaxHp = new JTextPane();
		txtpnMaxHp.setText("Max.\r\n HP");
		txtpnMaxHp.setOpaque(false);
		txtpnMaxHp.setFont(new Font("Arial", Font.BOLD, 9));
		txtpnMaxHp.setBounds(186, 253, 26, 34);
		panelCreate.add(txtpnMaxHp);
		
		JTextPane txtpnGold = new JTextPane();
		txtpnGold.setText("\r\nGold");
		txtpnGold.setOpaque(false);
		txtpnGold.setFont(new Font("Arial", Font.BOLD, 9));
		txtpnGold.setBounds(234, 253, 28, 34);
		panelCreate.add(txtpnGold);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(17, 285, 265, 2);
		panelCreate.add(separator_1);
		
		lblCreateLeftNumAttacks = new JLabel("3");
		lblCreateLeftNumAttacks.setForeground(new Color(0, 0, 153));
		lblCreateLeftNumAttacks.setFont(new Font("Arial", Font.BOLD, 18));
		lblCreateLeftNumAttacks.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateLeftNumAttacks.setBounds(17, 289, 37, 28);
		panelCreate.add(lblCreateLeftNumAttacks);
		
		lblCreateLeftAttBonus = new JLabel("+3");
		lblCreateLeftAttBonus.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateLeftAttBonus.setForeground(new Color(0, 0, 153));
		lblCreateLeftAttBonus.setFont(new Font("Arial", Font.BOLD, 18));
		lblCreateLeftAttBonus.setBounds(64, 289, 37, 28);
		panelCreate.add(lblCreateLeftAttBonus);
		
		lblCreateLeftDefense = new JLabel("12");
		lblCreateLeftDefense.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateLeftDefense.setForeground(new Color(0, 0, 153));
		lblCreateLeftDefense.setFont(new Font("Arial", Font.BOLD, 18));
		lblCreateLeftDefense.setBounds(124, 289, 37, 28);
		panelCreate.add(lblCreateLeftDefense);
		
		lblCreateLeftMaxHP = new JLabel("10");
		lblCreateLeftMaxHP.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateLeftMaxHP.setForeground(new Color(0, 0, 153));
		lblCreateLeftMaxHP.setFont(new Font("Arial", Font.BOLD, 18));
		lblCreateLeftMaxHP.setBounds(177, 289, 37, 28);
		panelCreate.add(lblCreateLeftMaxHP);
		
		lblCreateLeftGold = new JLabel("1527");
		lblCreateLeftGold.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateLeftGold.setForeground(new Color(0, 0, 153));
		lblCreateLeftGold.setFont(new Font("Arial", Font.BOLD, 18));
		lblCreateLeftGold.setBounds(220, 289, 62, 28);
		panelCreate.add(lblCreateLeftGold);
		
		JTextPane textPane = new JTextPane();
		textPane.setText("  # of\r\nAttacks");
		textPane.setOpaque(false);
		textPane.setFont(new Font("Arial", Font.BOLD, 9));
		textPane.setBounds(309, 253, 37, 34);
		panelCreate.add(textPane);
		
		lblCreateRightNumAttacks = new JLabel("3");
		lblCreateRightNumAttacks.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateRightNumAttacks.setForeground(new Color(204, 51, 0));
		lblCreateRightNumAttacks.setFont(new Font("Arial", Font.BOLD, 18));
		lblCreateRightNumAttacks.setBounds(309, 289, 37, 28);
		panelCreate.add(lblCreateRightNumAttacks);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setText("Attack\r\nBonus");
		textPane_1.setOpaque(false);
		textPane_1.setFont(new Font("Arial", Font.BOLD, 9));
		textPane_1.setBounds(366, 253, 37, 34);
		panelCreate.add(textPane_1);
		
		lblCreateRightAttBonus = new JLabel("+3");
		lblCreateRightAttBonus.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateRightAttBonus.setForeground(new Color(204, 51, 0));
		lblCreateRightAttBonus.setFont(new Font("Arial", Font.BOLD, 18));
		lblCreateRightAttBonus.setBounds(356, 289, 37, 28);
		panelCreate.add(lblCreateRightAttBonus);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(309, 285, 265, 2);
		panelCreate.add(separator_2);
		
		JTextPane textPane_2 = new JTextPane();
		textPane_2.setText("\r\nDefense");
		textPane_2.setOpaque(false);
		textPane_2.setFont(new Font("Arial", Font.BOLD, 9));
		textPane_2.setBounds(416, 253, 42, 34);
		panelCreate.add(textPane_2);
		
		lblCreateRightDefense = new JLabel("12");
		lblCreateRightDefense.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateRightDefense.setForeground(new Color(204, 51, 0));
		lblCreateRightDefense.setFont(new Font("Arial", Font.BOLD, 18));
		lblCreateRightDefense.setBounds(416, 289, 37, 28);
		panelCreate.add(lblCreateRightDefense);
		
		JTextPane textPane_3 = new JTextPane();
		textPane_3.setText("Max.\r\n HP");
		textPane_3.setOpaque(false);
		textPane_3.setFont(new Font("Arial", Font.BOLD, 9));
		textPane_3.setBounds(478, 253, 26, 34);
		panelCreate.add(textPane_3);
		
		lblCreateRightMaxHP = new JLabel("10");
		lblCreateRightMaxHP.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateRightMaxHP.setForeground(new Color(204, 51, 0));
		lblCreateRightMaxHP.setFont(new Font("Arial", Font.BOLD, 18));
		lblCreateRightMaxHP.setBounds(469, 289, 37, 28);
		panelCreate.add(lblCreateRightMaxHP);
		
		JTextPane textPane_4 = new JTextPane();
		textPane_4.setText("\r\nGold");
		textPane_4.setOpaque(false);
		textPane_4.setFont(new Font("Arial", Font.BOLD, 9));
		textPane_4.setBounds(526, 253, 28, 34);
		panelCreate.add(textPane_4);
		
		lblCreateRightGold = new JLabel("1527");
		lblCreateRightGold.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateRightGold.setForeground(new Color(204, 51, 0));
		lblCreateRightGold.setFont(new Font("Arial", Font.BOLD, 18));
		lblCreateRightGold.setBounds(512, 289, 62, 28);
		panelCreate.add(lblCreateRightGold);
		
		btnCreateLeftDone = new JButton("DONE");
		btnCreateLeftDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createPlayerDone = true;
				btnCreateLeftDone.setEnabled(false);
				btnCreateLeftChangeIcon.setEnabled(false);
				for (int idx=0; idx<5; idx++)
					chckbxCreateLeftArray[idx].setEnabled(false);
				if (createEnemyDone) {
					createMasters();
					if (!twoPlayer) {
						refillShop();
						shopper = enemy;
						enemyRandomShop();
					}
					refillShop();
					displayShop();
					shopper = player;
					displayShopper();
					showPanel(panelBuy);
				}
			}
		});
		btnCreateLeftDone.setFont(new Font("Arial", Font.BOLD, 18));
		btnCreateLeftDone.setBounds(64, 602, 150, 34);
		btnCreateLeftDone.setEnabled(false);
		panelCreate.add(btnCreateLeftDone);
		
		btnCreateRightDone = new JButton("DONE");
		btnCreateRightDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createEnemyDone = true;
				btnCreateRightDone.setEnabled(false);
				btnCreateRightChangeIcon.setEnabled(false);
				for (int idx=0; idx<5; idx++)
					chckbxCreateRightArray[idx].setEnabled(false);
				if (createPlayerDone) {
					createMasters();
					refillShop();
					displayShop();
					shopper = player;
					displayShopper();
					showPanel(panelBuy);
				}
			}
		});
		btnCreateRightDone.setFont(new Font("Arial", Font.BOLD, 18));
		btnCreateRightDone.setBounds(367, 602, 150, 34);
		btnCreateRightDone.setEnabled(false);
		panelCreate.add(btnCreateRightDone);
		
		chckbxCreateLeftRich = new JCheckBox("    Rich");
		chckbxCreateLeftRich.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (chckbxCreateLeftRich.isSelected()) {
					createPlayerGold += 500;
					createPlayerTraitsChosen += 1;
					if (createPlayerTraitsChosen == 2) {
						enableCreatePlayerDone(true);
					}
				}
				else {
					createPlayerGold -= 500;
					createPlayerTraitsChosen -= 1;
					if (createPlayerTraitsChosen == 1) {
						enableCreatePlayerDone(false);
					}
				}
				displayCreationValues();
			}
		});
		chckbxCreateLeftRich.setBounds(89, 546, 97, 23);
		chckbxCreateLeftArray[4] = chckbxCreateLeftRich;
		panelCreate.add(chckbxCreateLeftRich);
		
		chckbxCreateLeftFast = new JCheckBox("    Fast");
		chckbxCreateLeftFast.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (chckbxCreateLeftFast.isSelected()) {
					createPlayerDef += 3;
					createPlayerTraitsChosen += 1;
					if (createPlayerTraitsChosen == 2) {
						enableCreatePlayerDone(true);
					}
				}
				else {
					createPlayerDef -= 3;
					createPlayerTraitsChosen -= 1;
					if (createPlayerTraitsChosen == 1) {
						enableCreatePlayerDone(false);
					}
				}
				displayCreationValues();
			}
		});
		chckbxCreateLeftFast.setBounds(89, 506, 97, 23);
		chckbxCreateLeftArray[3] = chckbxCreateLeftFast;
		panelCreate.add(chckbxCreateLeftFast);
		
		chckbxCreateLeftTough = new JCheckBox("    Tough");
		chckbxCreateLeftTough.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (chckbxCreateLeftTough.isSelected()) {
					createPlayerMaxHP += 4;
					createPlayerTraitsChosen += 1;
					if (createPlayerTraitsChosen == 2) {
						enableCreatePlayerDone(true);
					}
				}
				else {
					createPlayerMaxHP -= 4;
					createPlayerTraitsChosen -= 1;
					if (createPlayerTraitsChosen == 1) {
						enableCreatePlayerDone(false);
					}
				}
				displayCreationValues();
			}
		});
		chckbxCreateLeftTough.setBounds(89, 468, 97, 23);
		chckbxCreateLeftArray[2] = chckbxCreateLeftTough;
		panelCreate.add(chckbxCreateLeftTough);
		
		chckbxCreateLeftSkilled = new JCheckBox("    Skilled");
		chckbxCreateLeftSkilled.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (chckbxCreateLeftSkilled.isSelected()) {
					createPlayerAttBns += 3;
					createPlayerTraitsChosen += 1;
					if (createPlayerTraitsChosen == 2) {
						enableCreatePlayerDone(true);
					}
				}
				else {
					createPlayerAttBns -= 3;
					createPlayerTraitsChosen -= 1;
					if (createPlayerTraitsChosen == 1) {
						enableCreatePlayerDone(false);
					}
				}
				displayCreationValues();
			}
		});
		chckbxCreateLeftSkilled.setBounds(89, 430, 97, 23);
		chckbxCreateLeftArray[1] = chckbxCreateLeftSkilled;
		panelCreate.add(chckbxCreateLeftSkilled);
		
		chckbxCreateLeftStrong = new JCheckBox("    Strong");
		chckbxCreateLeftStrong.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (chckbxCreateLeftStrong.isSelected()) {
					createPlayerNumAtt += 2;
					createPlayerTraitsChosen += 1;
					if (createPlayerTraitsChosen == 2) {
						enableCreatePlayerDone(true);
					}
				}
				else {
					createPlayerNumAtt -= 2;
					createPlayerTraitsChosen -= 1;
					if (createPlayerTraitsChosen == 1) {
						enableCreatePlayerDone(false);
					}
				}
				displayCreationValues();
			}
		});
		chckbxCreateLeftStrong.setBounds(89, 392, 97, 23);
		chckbxCreateLeftArray[0] = chckbxCreateLeftStrong;
		panelCreate.add(chckbxCreateLeftStrong);
		
		JLabel lblNewLabel_1 = new JLabel("Choose Two Traits:");
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(Color.WHITE);
		lblNewLabel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 18));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(20, 347, 254, 28);
		panelCreate.add(lblNewLabel_1);
		
		chckbxCreateRightRich = new JCheckBox("    Rich");
		chckbxCreateRightRich.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (chckbxCreateRightRich.isSelected()) {
					createEnemyGold += 500;
					createEnemyTraitsChosen += 1;
					if (createEnemyTraitsChosen == 2) {
						enableCreateEnemyDone(true);
					}
				}
				else {
					createEnemyGold -= 500;
					createEnemyTraitsChosen -= 1;
					if (createEnemyTraitsChosen == 1) {
						enableCreateEnemyDone(false);
					}
				}
				displayCreationValues();
			}
		});
		chckbxCreateRightRich.setBounds(381, 546, 97, 23);
		chckbxCreateRightArray[4] = chckbxCreateRightRich;
		panelCreate.add(chckbxCreateRightRich);
		
		chckbxCreateRightFast = new JCheckBox("    Fast");
		chckbxCreateRightFast.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (chckbxCreateRightFast.isSelected()) {
					createEnemyDef += 3;
					createEnemyTraitsChosen += 1;
					if (createEnemyTraitsChosen == 2) {
						enableCreateEnemyDone(true);
					}
				}
				else {
					createEnemyDef -= 3;
					createEnemyTraitsChosen -= 1;
					if (createEnemyTraitsChosen == 1) {
						enableCreateEnemyDone(false);
					}
				}
				displayCreationValues();
			}
		});
		chckbxCreateRightFast.setBounds(381, 506, 97, 23);
		chckbxCreateRightArray[3] = chckbxCreateRightFast;
		panelCreate.add(chckbxCreateRightFast);
		
		chckbxCreateRightTough = new JCheckBox("    Tough");
		chckbxCreateRightTough.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (chckbxCreateRightTough.isSelected()) {
					createEnemyMaxHP += 4;
					createEnemyTraitsChosen += 1;
					if (createEnemyTraitsChosen == 2) {
						enableCreateEnemyDone(true);
					}
				}
				else {
					createEnemyMaxHP -= 4;
					createEnemyTraitsChosen -= 1;
					if (createEnemyTraitsChosen == 1) {
						enableCreateEnemyDone(false);
					}
				}
				displayCreationValues();
			}
		});
		chckbxCreateRightTough.setBounds(381, 468, 97, 23);
		chckbxCreateRightArray[2] = chckbxCreateRightTough;
		panelCreate.add(chckbxCreateRightTough);
		
		chckbxCreateRightSkilled = new JCheckBox("    Skilled");
		chckbxCreateRightSkilled.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (chckbxCreateRightSkilled.isSelected()) {
					createEnemyAttBns += 3;
					createEnemyTraitsChosen += 1;
					if (createEnemyTraitsChosen == 2) {
						enableCreateEnemyDone(true);
					}
				}
				else {
					createEnemyAttBns -= 3;
					createEnemyTraitsChosen -= 1;
					if (createEnemyTraitsChosen == 1) {
						enableCreateEnemyDone(false);
					}
				}
				displayCreationValues();
			}
		});
		chckbxCreateRightSkilled.setBounds(381, 430, 97, 23);
		chckbxCreateRightArray[1] = chckbxCreateRightSkilled;
		panelCreate.add(chckbxCreateRightSkilled);
		
		chckbxCreateRightStrong = new JCheckBox("    Strong");
		chckbxCreateRightStrong.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (chckbxCreateRightStrong.isSelected()) {
					createEnemyNumAtt += 2;
					createEnemyTraitsChosen += 1;
					if (createEnemyTraitsChosen == 2) {
						enableCreateEnemyDone(true);
					}
				}
				else {
					createEnemyNumAtt -= 2;
					createEnemyTraitsChosen -= 1;
					if (createEnemyTraitsChosen == 1) {
						enableCreateEnemyDone(false);
					}
				}
				displayCreationValues();
			}
		});
		chckbxCreateRightStrong.setBounds(381, 392, 97, 23);
		chckbxCreateRightArray[0] = chckbxCreateRightStrong;
		panelCreate.add(chckbxCreateRightStrong);
		
		JLabel label = new JLabel("Choose Two Traits:");
		label.setBorder(new LineBorder(new Color(0, 0, 0)));
		label.setBackground(Color.WHITE);
		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Arial", Font.PLAIN, 18));
		label.setBounds(312, 347, 254, 28);
		panelCreate.add(label);
		
		btnCreateLeftChangeIcon = new JButton("Change Icon");
		btnCreateLeftChangeIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createPlayerImgIndex = (createPlayerImgIndex + 1) % 7;
				lblCreateLeftIcon.setIcon(playerImages150[createPlayerImgIndex]);
			}
		});
		btnCreateLeftChangeIcon.setFont(new Font("Arial", Font.PLAIN, 14));
		btnCreateLeftChangeIcon.setBounds(64, 58, 150, 23);
		panelCreate.add(btnCreateLeftChangeIcon);
		
		btnCreateRightChangeIcon = new JButton("Change Icon");
		btnCreateRightChangeIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createEnemyImgIndex = (createEnemyImgIndex +1 ) % 7;
				lblCreateRightIcon.setIcon(playerImages150[createEnemyImgIndex]);
			}
		});
		btnCreateRightChangeIcon.setFont(new Font("Arial", Font.PLAIN, 14));
		btnCreateRightChangeIcon.setBounds(367, 59, 150, 23);
		panelCreate.add(btnCreateRightChangeIcon);
		
		//////////////////
		//CREATE BUY PANEL
		//////////////////
		
		panelBuy = new JPanel();
		contentPane.add(panelBuy, "name_5677254112498");
		panelBuy.setLayout(null);
		
		lblStoreMainIcon = new JLabel("");
		lblStoreMainIcon.setBorder(new LineBorder(Color.BLUE, 4));
		lblStoreMainIcon.setBounds(26, 22, 150, 150);
		panelBuy.add(lblStoreMainIcon);
		
		JTextPane textPane_5 = new JTextPane();
		textPane_5.setEditable(false);
		textPane_5.setText("  # of\r\nAttacks");
		textPane_5.setOpaque(false);
		textPane_5.setFont(new Font("Arial", Font.BOLD, 9));
		textPane_5.setBounds(10, 172, 37, 34);
		panelBuy.add(textPane_5);
		
		JTextPane textPane_6 = new JTextPane();
		textPane_6.setEditable(false);
		textPane_6.setText("Attack\r\nBonus");
		textPane_6.setOpaque(false);
		textPane_6.setFont(new Font("Arial", Font.BOLD, 9));
		textPane_6.setBounds(57, 172, 37, 34);
		panelBuy.add(textPane_6);
		
		JTextPane textPane_7 = new JTextPane();
		textPane_7.setEditable(false);
		textPane_7.setText("\r\nDefense");
		textPane_7.setOpaque(false);
		textPane_7.setFont(new Font("Arial", Font.BOLD, 9));
		textPane_7.setBounds(104, 172, 42, 34);
		panelBuy.add(textPane_7);
		
		JTextPane textPane_8 = new JTextPane();
		textPane_8.setEditable(false);
		textPane_8.setText("Max.\r\n HP");
		textPane_8.setOpaque(false);
		textPane_8.setFont(new Font("Arial", Font.BOLD, 9));
		textPane_8.setBounds(151, 172, 26, 34);
		panelBuy.add(textPane_8);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(10, 204, 177, 2);
		panelBuy.add(separator_3);
		
		lblStoreNumAttValue = new JLabel("3");
		lblStoreNumAttValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblStoreNumAttValue.setForeground(new Color(0, 0, 153));
		lblStoreNumAttValue.setFont(new Font("Arial", Font.BOLD, 18));
		lblStoreNumAttValue.setBounds(10, 204, 37, 28);
		panelBuy.add(lblStoreNumAttValue);
		
		lblStoreAttBnsValue = new JLabel("+3");
		lblStoreAttBnsValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblStoreAttBnsValue.setForeground(new Color(0, 0, 153));
		lblStoreAttBnsValue.setFont(new Font("Arial", Font.BOLD, 18));
		lblStoreAttBnsValue.setBounds(57, 204, 37, 28);
		panelBuy.add(lblStoreAttBnsValue);
		
		lblStoreDefValue = new JLabel("12");
		lblStoreDefValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblStoreDefValue.setForeground(new Color(0, 0, 153));
		lblStoreDefValue.setFont(new Font("Arial", Font.BOLD, 18));
		lblStoreDefValue.setBounds(104, 204, 37, 28);
		panelBuy.add(lblStoreDefValue);
		
		lblStoreMaxHPValue = new JLabel("10");
		lblStoreMaxHPValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblStoreMaxHPValue.setForeground(new Color(0, 0, 153));
		lblStoreMaxHPValue.setFont(new Font("Arial", Font.BOLD, 18));
		lblStoreMaxHPValue.setBounds(150, 204, 37, 28);
		panelBuy.add(lblStoreMaxHPValue);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBackground(Color.BLACK);
		separator_4.setForeground(Color.BLACK);
		separator_4.setBounds(0, 249, 557, 2);
		panelBuy.add(separator_4);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setBackground(Color.BLACK);
		separator_5.setForeground(Color.BLACK);
		separator_5.setBounds(0, 453, 557, 2);
		panelBuy.add(separator_5);
		
		JLabel lblBuyArmaments = new JLabel("Buy Armaments");
		lblBuyArmaments.setFont(new Font("Arial", Font.BOLD, 12));
		lblBuyArmaments.setBounds(0, 231, 98, 20);
		panelBuy.add(lblBuyArmaments);
		
		JLabel lblBuyMinions = new JLabel("Buy Minions");
		lblBuyMinions.setFont(new Font("Arial", Font.BOLD, 12));
		lblBuyMinions.setBounds(0, 434, 98, 20);
		panelBuy.add(lblBuyMinions);
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setForeground(Color.BLACK);
		separator_6.setBackground(Color.BLACK);
		separator_6.setBounds(208, 40, 349, 2);
		panelBuy.add(separator_6);
		
		JSeparator separator_7 = new JSeparator();
		separator_7.setForeground(Color.BLACK);
		separator_7.setBackground(Color.BLACK);
		separator_7.setBounds(208, 123, 349, 2);
		panelBuy.add(separator_7);
		
		JLabel lblArmaments = new JLabel("Armaments:");
		lblArmaments.setFont(new Font("Arial", Font.BOLD, 12));
		lblArmaments.setBounds(208, 22, 98, 20);
		panelBuy.add(lblArmaments);
		
		JLabel lblWeapons = new JLabel("Minions:");
		lblWeapons.setFont(new Font("Arial", Font.BOLD, 12));
		lblWeapons.setBounds(208, 105, 98, 20);
		panelBuy.add(lblWeapons);
		
		JLabel lblGold = new JLabel("GOLD:");
		lblGold.setForeground(new Color(204, 153, 51));
		lblGold.setFont(new Font("Arial", Font.BOLD, 18));
		lblGold.setBounds(283, 204, 70, 34);
		panelBuy.add(lblGold);
		
		lblStoreGoldValue = new JLabel("1245");
		lblStoreGoldValue.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblStoreGoldValue.setBackground(new Color(255, 255, 255));
		lblStoreGoldValue.setOpaque(true);
		lblStoreGoldValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblStoreGoldValue.setForeground(new Color(0, 0, 0));
		lblStoreGoldValue.setFont(new Font("Arial", Font.BOLD, 18));
		lblStoreGoldValue.setBounds(358, 204, 70, 34);
		panelBuy.add(lblStoreGoldValue);
		
		btnStoreDone = new JButton("DONE");
		btnStoreDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (shopper == enemy || !twoPlayer) {
					// proceed to fight panel.
					lblFightPlayerActiveLabel.setIcon(null);
					lblFightEnemyActiveLabel.setIcon(null);
					textArea.setText("");
					lblFightInstruction.setText("Choose your Combatant!");
					player.setToFull();
					enemy.setToFull();
					if (!twoPlayer)
						chooseEnemyCombatant();
					displayCombatants();
					showPanel(panelFight);
				}
				else {
					// renew shop for 2nd player
					for (int idx=0; idx<10; idx++) {
						storeMinions[idx] = createRandomMinion();
						storeArms[idx] = createRandomArmament();
					}
					shopper = enemy;
					displayShopper();
					displayShop();
				}
			}
		});
		btnStoreDone.setBackground(new Color(0, 0, 139));
		btnStoreDone.setForeground(new Color(255, 255, 255));
		btnStoreDone.setFont(new Font("Arial", Font.BOLD, 18));
		btnStoreDone.setBounds(228, 642, 129, 20);
		panelBuy.add(btnStoreDone);
		
		
		//My experiment:
		//Create labels in for loops, and add them to arrays.
		JLabel placeholder;
		JButton buttonholder;
		int xpos, ypos;
		
		for (int idx=0; idx<5; idx++) {
			xpos = 208 + 75*idx;
			ypos = 50;
			placeholder = new JLabel("");
			placeholder.setBorder(new LineBorder(new Color(0,0,0)));
			placeholder.setBackground(new Color(0, 0, 0));
			placeholder.setOpaque(true);
			placeholder.setBounds(xpos,ypos,50,50);
			storeOwnedItemIconLabels[idx] = placeholder;
			panelBuy.add(placeholder);
			
			ypos = 133;
			placeholder = new JLabel("");
			placeholder.setBorder(new LineBorder(new Color(0,0,0)));
			placeholder.setBackground(new Color(0, 0, 0));
			placeholder.setOpaque(true);
			placeholder.setBounds(xpos,ypos,50,50);
			storeOwnedMinionIconLabels[idx] = placeholder;
			panelBuy.add(placeholder);
		}
		
		for (int idx=0; idx<10; idx++) {
			xpos = 26 + 105*(idx % 5);
			ypos = 260 + 90*((int)idx/5);
			
			placeholder = new JLabel("");
			placeholder.setBorder(new LineBorder(new Color(0,0,0)));
			placeholder.setBackground(new Color(0, 0, 0));
			placeholder.setOpaque(true);
			placeholder.setBounds(xpos,ypos,50,50);
			storeItemIconLabels[idx] = placeholder;
			panelBuy.add(placeholder);
			
			placeholder = new JLabel("#Att:");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setBounds(xpos+52, ypos, 26, 14);
			panelBuy.add(placeholder);
			
			placeholder = new JLabel("+1");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setForeground(new Color(0, 0, 153));
			placeholder.setBounds(xpos+78, ypos, 26, 14);
			storeItemNumAttValues[idx] = placeholder;
			panelBuy.add(placeholder);
			
			placeholder = new JLabel("A.B.:");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setBounds(xpos+52, ypos+12, 26, 14);
			panelBuy.add(placeholder);
			
			placeholder = new JLabel("+2");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setForeground(new Color(0, 0, 153));
			placeholder.setBounds(xpos+78, ypos+12, 26, 14);
			storeItemAttBnsValues[idx] = placeholder;
			panelBuy.add(placeholder);
			
			placeholder = new JLabel("Def:");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setBounds(xpos+52, ypos+24, 26, 14);
			panelBuy.add(placeholder);
			
			placeholder = new JLabel("+2");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setForeground(new Color(0, 0, 153));
			placeholder.setBounds(xpos+78, ypos+24, 26, 14);
			storeItemDefValues[idx] = placeholder;
			panelBuy.add(placeholder);
			
			placeholder = new JLabel("HP:");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setBounds(xpos+52, ypos+36, 26, 14);
			panelBuy.add(placeholder);
			
			placeholder = new JLabel("+0");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setForeground(new Color(0, 0, 153));
			placeholder.setBounds(xpos+78, ypos+36, 26, 14);
			storeItemMaxHPValues[idx] = placeholder;
			panelBuy.add(placeholder);
			
			placeholder = new JLabel("Cost:");
			placeholder.setBounds(xpos, ypos+50, 37, 14);
			panelBuy.add(placeholder);
			
			placeholder = new JLabel("525");
			placeholder.setBounds(xpos+30, ypos+50, 46, 14);
			storeItemCostValues[idx] = placeholder;
			panelBuy.add(placeholder);
			
			buttonholder = new JButton("BUY");
			buttonholder.setBounds(xpos, ypos+65, 77, 14);
			final int currentIndex = idx;
			buttonholder.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					buyItem(currentIndex);
				}
			});
			storeItemBuyButtons[idx] = buttonholder;
			panelBuy.add(buttonholder);
		}
		
		for (int idx=0; idx<10; idx++) {
			xpos = 26 + 105*(idx % 5);
			ypos = 465 + 90*((int)idx/5);
			
			placeholder = new JLabel("");
			placeholder.setBorder(new LineBorder(new Color(0,0,0)));
			placeholder.setBackground(new Color(0, 0, 0));
			placeholder.setOpaque(true);
			placeholder.setBounds(xpos,ypos,50,50);
			storeMinionIconLabels[idx] = placeholder;
			panelBuy.add(placeholder);
			
			placeholder = new JLabel("#Att:");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setBounds(xpos+52, ypos, 26, 14);
			panelBuy.add(placeholder);
			
			placeholder = new JLabel("4");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setForeground(new Color(0, 0, 153));
			placeholder.setBounds(xpos+78, ypos, 26, 14);
			storeMinionNumAttValues[idx] = placeholder;
			panelBuy.add(placeholder);
			
			placeholder = new JLabel("A.B.:");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setBounds(xpos+52, ypos+12, 26, 14);
			panelBuy.add(placeholder);
			
			placeholder = new JLabel("-1");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setForeground(new Color(0, 0, 153));
			placeholder.setBounds(xpos+78, ypos+12, 26, 14);
			storeMinionAttBnsValues[idx] = placeholder;
			panelBuy.add(placeholder);
			
			placeholder = new JLabel("Def:");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setBounds(xpos+52, ypos+24, 26, 14);
			panelBuy.add(placeholder);
			
			placeholder = new JLabel("15");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setForeground(new Color(0, 0, 153));
			placeholder.setBounds(xpos+78, ypos+24, 26, 14);
			storeMinionDefValues[idx] = placeholder;
			panelBuy.add(placeholder);
			
			placeholder = new JLabel("HP:");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setBounds(xpos+52, ypos+36, 26, 14);
			panelBuy.add(placeholder);
			
			placeholder = new JLabel("3");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setForeground(new Color(0, 0, 153));
			placeholder.setBounds(xpos+78, ypos+36, 26, 14);
			storeMinionMaxHPValues[idx] = placeholder;
			panelBuy.add(placeholder);
			
			placeholder = new JLabel("Cost:");
			placeholder.setBounds(xpos, ypos+50, 37, 14);
			panelBuy.add(placeholder);
			
			placeholder = new JLabel("360");
			placeholder.setBounds(xpos+30, ypos+50, 46, 14);
			storeMinionCostValues[idx] = placeholder;
			panelBuy.add(placeholder);
			
			buttonholder = new JButton("BUY");
			buttonholder.setBounds(xpos, ypos+65, 77, 14);
			final int currentIndex = idx;
			buttonholder.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					buyMinion(currentIndex);
				}
			});
			storeMinionBuyButtons[idx] = buttonholder;
			panelBuy.add(buttonholder);
		}
		
		/////////////
		//FIGHT PANEL
		/////////////
		
		panelFight = new JPanel();
		panelFight.setBackground(new Color(176, 224, 230));
		panelFight.setVisible(false);
		contentPane.add(panelFight, "name_5751730414719");
		panelFight.setLayout(null);
		
		// Active icons
		lblFightPlayerActiveLabel = new JLabel("");
		lblFightPlayerActiveLabel.setBorder(new LineBorder(Color.BLUE, 4));
		lblFightPlayerActiveLabel.setBackground(new Color(0, 0, 0));
		lblFightPlayerActiveLabel.setOpaque(true);
		lblFightPlayerActiveLabel.setBounds(216, 0, 150, 150);
		panelFight.add(lblFightPlayerActiveLabel);
		
		lblFightEnemyActiveLabel = new JLabel("");
		lblFightEnemyActiveLabel.setBorder(new LineBorder(Color.RED, 4));
		lblFightEnemyActiveLabel.setBackground(new Color(0, 0, 0));
		lblFightEnemyActiveLabel.setOpaque(true);
		lblFightEnemyActiveLabel.setBounds(216, 512, 150, 150);
		panelFight.add(lblFightEnemyActiveLabel);
		
		// Player display
		lblFightPlayerIconLabel = new JLabel("");
		lblFightPlayerIconLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if (fightSelectionEnabled)
					lblFightPlayerIconLabel.setBorder(new LineBorder(Color.GREEN, 4));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if (fightSelectionEnabled)
					lblFightPlayerIconLabel.setBorder(new LineBorder(Color.BLUE, 4));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (fightSelectionEnabled)
					setPlayerCombatant(-1);
			}
		});
		lblFightPlayerIconLabel.setBorder(new LineBorder(Color.BLUE, 4));
		lblFightPlayerIconLabel.setBackground(new Color(0, 0, 0));
		lblFightPlayerIconLabel.setOpaque(true);
		lblFightPlayerIconLabel.setBounds(60, 0, 85, 85);
		panelFight.add(lblFightPlayerIconLabel);
		
		JLabel lbl1 = new JLabel("#Att:");
		lbl1.setFont(new Font("Arial", Font.PLAIN, 9));
		lbl1.setBounds(148, 0, 26, 14);
		panelFight.add(lbl1);
		
		lblFightPlayerNumAttValue = new JLabel("4");
		lblFightPlayerNumAttValue.setFont(new Font("Arial", Font.PLAIN, 9));
		lblFightPlayerNumAttValue.setForeground(new Color(0, 0, 153));
		lblFightPlayerNumAttValue.setBounds(175, 0, 26, 14);
		panelFight.add(lblFightPlayerNumAttValue);
		
		JLabel lbl2 = new JLabel("A.B.:");
		lbl2.setFont(new Font("Arial", Font.PLAIN, 9));
		lbl2.setBounds(148, 20, 26, 14);
		panelFight.add(lbl2);
		
		lblFightPlayerAttBnsValue = new JLabel("-1");
		lblFightPlayerAttBnsValue.setFont(new Font("Arial", Font.PLAIN, 9));
		lblFightPlayerAttBnsValue.setForeground(new Color(0, 0, 153));
		lblFightPlayerAttBnsValue.setBounds(175, 20, 26, 14);
		panelFight.add(lblFightPlayerAttBnsValue);
		
		JLabel lbl3 = new JLabel("Def:");
		lbl3.setFont(new Font("Arial", Font.PLAIN, 9));
		lbl3.setBounds(148, 40, 26, 14);
		panelFight.add(lbl3);
		
		lblFightPlayerDefValue = new JLabel("15");
		lblFightPlayerDefValue.setFont(new Font("Arial", Font.PLAIN, 9));
		lblFightPlayerDefValue.setForeground(new Color(0, 0, 153));
		lblFightPlayerDefValue.setBounds(175, 40, 26, 14);
		panelFight.add(lblFightPlayerDefValue);
		
		JLabel lbl4 = new JLabel("HP:");
		lbl4.setFont(new Font("Arial", Font.PLAIN, 9));
		lbl4.setBounds(148, 60, 26, 14);
		panelFight.add(lbl4);
		
		lblFightPlayerMaxHPValue = new JLabel("3");
		lblFightPlayerMaxHPValue.setFont(new Font("Arial", Font.PLAIN, 9));
		lblFightPlayerMaxHPValue.setForeground(new Color(0, 0, 153));
		lblFightPlayerMaxHPValue.setBounds(175, 60, 26, 14);
		panelFight.add(lblFightPlayerMaxHPValue);
		
		// Enemy Display
		lblFightEnemyIconLabel = new JLabel("");
		lblFightEnemyIconLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if (twoPlayer && fightSelectionEnabled)
					lblFightEnemyIconLabel.setBorder(new LineBorder(Color.GREEN, 4));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if (twoPlayer && fightSelectionEnabled)
					lblFightEnemyIconLabel.setBorder(new LineBorder(Color.RED, 4));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (twoPlayer && fightSelectionEnabled)
					setEnemyCombatant(-1);
			}
		});
		lblFightEnemyIconLabel.setBorder(new LineBorder(Color.RED, 4));
		lblFightEnemyIconLabel.setBackground(new Color(0, 0, 0));
		lblFightEnemyIconLabel.setOpaque(true);
		lblFightEnemyIconLabel.setBounds(432, 577, 85, 85);
		panelFight.add(lblFightEnemyIconLabel);
		
		JLabel lbl5 = new JLabel("#Att:");
		lbl5.setFont(new Font("Arial", Font.PLAIN, 9));
		lbl5.setBounds(392, 577, 26, 14);
		panelFight.add(lbl5);
		
		lblFightEnemyNumAttValue = new JLabel("4");
		lblFightEnemyNumAttValue.setFont(new Font("Arial", Font.PLAIN, 9));
		lblFightEnemyNumAttValue.setForeground(new Color(153, 0, 0));
		lblFightEnemyNumAttValue.setBounds(416, 577, 26, 14);
		panelFight.add(lblFightEnemyNumAttValue);
		
		JLabel lbl6 = new JLabel("A.B.:");
		lbl6.setFont(new Font("Arial", Font.PLAIN, 9));
		lbl6.setBounds(392, 597, 26, 14);
		panelFight.add(lbl6);
		
		lblFightEnemyAttBnsValue = new JLabel("-1");
		lblFightEnemyAttBnsValue.setFont(new Font("Arial", Font.PLAIN, 9));
		lblFightEnemyAttBnsValue.setForeground(new Color(153, 0, 0));
		lblFightEnemyAttBnsValue.setBounds(416, 597, 26, 14);
		panelFight.add(lblFightEnemyAttBnsValue);
		
		JLabel lbl7 = new JLabel("Def:");
		lbl7.setFont(new Font("Arial", Font.PLAIN, 9));
		lbl7.setBounds(392, 617, 26, 14);
		panelFight.add(lbl7);
		
		lblFightEnemyDefValue = new JLabel("15");
		lblFightEnemyDefValue.setFont(new Font("Arial", Font.PLAIN, 9));
		lblFightEnemyDefValue.setForeground(new Color(153, 0, 0));
		lblFightEnemyDefValue.setBounds(416, 617, 26, 14);
		panelFight.add(lblFightEnemyDefValue);
		
		JLabel lbl8 = new JLabel("HP:");
		lbl8.setFont(new Font("Arial", Font.PLAIN, 9));
		lbl8.setBounds(382, 637, 26, 14);
		panelFight.add(lbl8);
		
		lblFightEnemyMaxHPValue = new JLabel("3");
		lblFightEnemyMaxHPValue.setFont(new Font("Arial", Font.PLAIN, 9));
		lblFightEnemyMaxHPValue.setForeground(new Color(153, 0, 0));
		lblFightEnemyMaxHPValue.setBounds(406, 637, 26, 14);
		panelFight.add(lblFightEnemyMaxHPValue);
		
		
		// use a for-loop to add minion icons and info.
		for (int idx=0; idx<5; idx++) {
			xpos = 10;
			ypos = 172 + 96*idx;
			placeholder = new JLabel("");
			final JLabel currentLabel = placeholder;
			final int currentIndex = idx;
			placeholder.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
					if (fightSelectionEnabled)
						currentLabel.setBorder(new LineBorder(Color.GREEN, 4));
				}
				@Override
				public void mouseExited(MouseEvent e) {
					if (fightSelectionEnabled)
						currentLabel.setBorder(new LineBorder(Color.BLUE, 4));
				}
				@Override
				public void mouseClicked(MouseEvent e) {
					if (fightSelectionEnabled)
						setPlayerCombatant(currentIndex);
				}
			});
			placeholder.setBorder(new LineBorder(new Color(0, 0, 255), 4));
			placeholder.setBackground(new Color(0, 0, 0));
			placeholder.setOpaque(true);
			placeholder.setBounds(xpos, ypos, 85, 85);
			fightPlayerMinionIconLabels[idx] = placeholder;
			panelFight.add(placeholder);
			
			placeholder = new JLabel("#Att:");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setBounds(xpos+88, ypos, 26, 14);
			panelFight.add(placeholder);
			
			placeholder = new JLabel("4");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setForeground(new Color(0, 0, 153));
			placeholder.setBounds(xpos+115, ypos, 26, 14);
			fightPlayerMinionNumAttValues[idx] = placeholder;
			panelFight.add(placeholder);
			
			placeholder = new JLabel("A.B.:");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setBounds(xpos+88, ypos+20, 26, 14);
			panelFight.add(placeholder);
			
			placeholder = new JLabel("-1");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setForeground(new Color(0, 0, 153));
			placeholder.setBounds(xpos+115, ypos+20, 26, 14);
			fightPlayerMinionAttBnsValues[idx] = placeholder;
			panelFight.add(placeholder);
			
			placeholder = new JLabel("Def:");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setBounds(xpos+88, ypos+40, 26, 14);
			panelFight.add(placeholder);
			
			placeholder = new JLabel("15");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setForeground(new Color(0, 0, 153));
			placeholder.setBounds(xpos+115, ypos+40, 26, 14);
			fightPlayerMinionDefValues[idx] = placeholder;
			panelFight.add(placeholder);
			
			placeholder = new JLabel("HP:");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setBounds(xpos+88, ypos+60, 26, 14);
			panelFight.add(placeholder);
			
			placeholder = new JLabel("3");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setForeground(new Color(0, 0, 153));
			placeholder.setBounds(xpos+115, ypos+60, 26, 14);
			fightPlayerMinionMaxHPValues[idx] = placeholder;
			panelFight.add(placeholder);
			
			xpos = 489;
			ypos = 407 - 96*idx;
			placeholder = new JLabel("");
			final JLabel currentLabel2 = placeholder;
			final int currentIndex2 = idx;
			placeholder.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
					if (twoPlayer && fightSelectionEnabled)
						currentLabel2.setBorder(new LineBorder(Color.GREEN, 4));
				}
				@Override
				public void mouseExited(MouseEvent e) {
					if (twoPlayer && fightSelectionEnabled)
						currentLabel2.setBorder(new LineBorder(Color.RED, 4));
				}
				@Override
				public void mouseClicked(MouseEvent e) {
					if (twoPlayer && fightSelectionEnabled)
						setEnemyCombatant(currentIndex2);
				}
			});
			placeholder.setBorder(new LineBorder(new Color(255, 0, 0), 4));
			placeholder.setBackground(new Color(0, 0, 0));
			placeholder.setOpaque(true);
			placeholder.setBounds(xpos, ypos, 85, 85);
			fightEnemyMinionIconLabels[idx] = placeholder;
			panelFight.add(placeholder);
			
			placeholder = new JLabel("#Att:");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setBounds(xpos-40, ypos, 26, 14);
			panelFight.add(placeholder);
			
			placeholder = new JLabel("4");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setForeground(new Color(153, 0, 0));
			placeholder.setBounds(xpos-16, ypos, 26, 14);
			fightEnemyMinionNumAttValues[idx] = placeholder;
			panelFight.add(placeholder);
			
			placeholder = new JLabel("A.B.:");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setBounds(xpos-40, ypos+20, 26, 14);
			panelFight.add(placeholder);
			
			placeholder = new JLabel("-1");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setForeground(new Color(153, 0, 0));
			placeholder.setBounds(xpos-16, ypos+20, 26, 14);
			fightEnemyMinionAttBnsValues[idx] = placeholder;
			panelFight.add(placeholder);
			
			placeholder = new JLabel("Def:");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setBounds(xpos-40, ypos+40, 26, 14);
			panelFight.add(placeholder);
			
			placeholder = new JLabel("15");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setForeground(new Color(153, 0, 0));
			placeholder.setBounds(xpos-16, ypos+40, 26, 14);
			fightEnemyMinionDefValues[idx] = placeholder;
			panelFight.add(placeholder);
			
			placeholder = new JLabel("HP:");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setBounds(xpos-40, ypos+60, 26, 14);
			panelFight.add(placeholder);
			
			placeholder = new JLabel("3");
			placeholder.setFont(new Font("Arial", Font.PLAIN, 9));
			placeholder.setForeground(new Color(153, 0, 0));
			placeholder.setBounds(xpos-16, ypos+60, 26, 14);
			fightEnemyMinionMaxHPValues[idx] = placeholder;
			panelFight.add(placeholder);
			
			xpos = 3 + 52*(idx-2) - 52*(idx-2)*((int)((4-idx)/3));
			ypos = 0 + 52*idx - 52*(idx-2)*((int)(idx/3));
			
			placeholder = new JLabel("");
			placeholder.setBounds(xpos, ypos, 50, 50);
			placeholder.setBorder(new LineBorder(new Color(0, 0, 0)));
			placeholder.setBackground(new Color(0, 0, 0));
			placeholder.setOpaque(true);
			fightPlayerItemIconLabels[idx] = placeholder;
			panelFight.add(placeholder);
			
			xpos = 524 -52*(idx-2) + 52*(idx-2)*((int)((4-idx)/3));
			ypos = 612 - 52*idx + 52*(idx-2)*((int)(idx/3));
			
			placeholder = new JLabel("");
			placeholder.setBounds(xpos, ypos, 50, 50);
			placeholder.setBorder(new LineBorder(new Color(0, 0, 0)));
			placeholder.setBackground(new Color(0, 0, 0));
			placeholder.setOpaque(true);
			fightEnemyItemIconLabels[idx] = placeholder;
			panelFight.add(placeholder);
		}
		
		
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setMargin(new Insets(6, 6, 6, 6));
		textArea.setCaretColor(Color.WHITE);
		textArea.setBorder(new LineBorder(Color.DARK_GRAY, 4));
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.WHITE);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setBounds(143, 191, 298, 279);
		
		JScrollPane pane = new JScrollPane (textArea);
		pane.setBounds(149, 194, 286, 273);
		panelFight.add(pane);
		
		lblFightInstruction = new JLabel("Player, choose your Combatant!");
		lblFightInstruction.setBorder(new LineBorder(Color.DARK_GRAY, 3));
		lblFightInstruction.setForeground(Color.BLACK);
		lblFightInstruction.setBackground(Color.WHITE);
		lblFightInstruction.setOpaque(true);
		lblFightInstruction.setFont(new Font("Arial", Font.BOLD, 14));
		lblFightInstruction.setHorizontalAlignment(SwingConstants.CENTER);
		lblFightInstruction.setBounds(149, 158, 286, 28);
		panelFight.add(lblFightInstruction);
		
		btnFightButton = new JButton("FIGHT!");
		btnFightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (fightSelectionEnabled)
					fightStart();
				else
					fightNextClick();
			}
		});
		btnFightButton.setEnabled(false);
		btnFightButton.setForeground(Color.RED);
		btnFightButton.setFont(new Font("Arial", Font.BOLD, 18));
		btnFightButton.setBounds(216, 471, 150, 30);
		panelFight.add(btnFightButton);
		
		JLabel lblBackground2 = new JLabel("");
		lblBackground2.setOpaque(true);
		lblBackground2.setBackground(new Color(255, 228, 225));
		lblBackground2.setBounds(179, 313, 405, 349);
		panelFight.add(lblBackground2);
		
		JLabel lblBackground1 = new JLabel("");
		lblBackground1.setBackground(new Color(255, 228, 225));
		lblBackground1.setOpaque(true);
		lblBackground1.setBounds(399, 0, 185, 314);
		panelFight.add(lblBackground1);
		
		panelEnd = new JPanel();
		panelEnd.setVisible(false);
		contentPane.add(panelEnd, "name_14524402995748");
		panelEnd.setLayout(null);
		
		lblEndMessage = new JLabel("New label");
		lblEndMessage.setOpaque(true);
		lblEndMessage.setBackground(Color.WHITE);
		lblEndMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblEndMessage.setFont(new Font("Arial", Font.PLAIN, 20));
		lblEndMessage.setBounds(141, 208, 308, 106);
		panelEnd.add(lblEndMessage);
		
		JButton btnEndClose = new JButton("Close");
		btnEndClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closeApp();
			}
		});
		btnEndClose.setMargin(new Insets(2, 4, 2, 4));
		btnEndClose.setFont(new Font("Arial", Font.BOLD, 16));
		btnEndClose.setBounds(312, 355, 120, 75);
		panelEnd.add(btnEndClose);
		
		btnEndRestart = new JButton("Play\r\nAgain");
		btnEndRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (enemy.getCurrentHP() > 0)
					restart();
				else {
					restartAfterWin();
				}
			}
		});
		btnEndRestart.setMargin(new Insets(2, 4, 2, 4));
		btnEndRestart.setFont(new Font("Arial", Font.BOLD, 16));
		btnEndRestart.setBounds(155, 355, 120, 75);
		panelEnd.add(btnEndRestart);
		
		JLabel lblEndBg = new JLabel("");
		lblEndBg.setBounds(0, 0, 584, 662);
		lblEndBg.setIcon(new ImageIcon(getClass().getResource("/images/PvPBackgroundEnd.png")));
		panelEnd.add(lblEndBg);
		
		//this.pack();
	}
}
