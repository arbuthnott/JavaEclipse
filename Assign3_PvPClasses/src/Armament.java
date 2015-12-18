
public class Armament {
	
	// properties
	private int numOfAttacksBonus;
	private int attackBonus;
	private int defenseBonus;
	private int maxHPBonus;
	
	private int typeIndex;
	private String name;
	private int cost;
	
	private boolean isDefensive, isOffensive;
	
	// constructor - one case per type.
	public Armament(int index) {
		isDefensive = false;
		isOffensive = false;
		typeIndex = index;
		switch (typeIndex) {
			case 0: {
				name = "Amulet";
				cost = PvPFrame.getRandom(100, 300);
				numOfAttacksBonus = 0;
				attackBonus = 0;
				defenseBonus = PvPFrame.getRandom(1, 3);
				maxHPBonus = PvPFrame.getRandom(1, 2);
				break;
			}
			case 1: {
				name = "Armor";
				isDefensive = true;
				cost = PvPFrame.getRandom(125, 500);
				numOfAttacksBonus = 0;
				attackBonus = PvPFrame.getRandom(-2, 0);
				defenseBonus = 0;
				maxHPBonus = PvPFrame.getRandom(1, 5);
				break;
			}
			case 2: {
				name = "Axe";
				isOffensive = true;
				cost = PvPFrame.getRandom(95, 275);
				numOfAttacksBonus = PvPFrame.getRandom(2, 3);
				attackBonus = PvPFrame.getRandom(-1, 0);
				defenseBonus = 0;
				maxHPBonus = 0;
				break;
			}
			case 3: {
				name = "Book";
				cost = PvPFrame.getRandom(60, 150);
				numOfAttacksBonus = 0;
				attackBonus = PvPFrame.getRandom(0, 2);
				defenseBonus = PvPFrame.getRandom(1, 2);
				maxHPBonus = 0;
				break;
			}
			case 4: {
				name = "Boots";
				cost = PvPFrame.getRandom(100, 200);
				numOfAttacksBonus = 0;
				attackBonus = 0;
				defenseBonus = PvPFrame.getRandom(1, 4);
				maxHPBonus = PvPFrame.getRandom(0, 1);
				break;
			}
			case 5: {
				name = "Bow";
				isOffensive = true;
				cost = PvPFrame.getRandom(200, 450);
				numOfAttacksBonus = PvPFrame.getRandom(1, 2);
				attackBonus = 0;
				defenseBonus = PvPFrame.getRandom(1, 2);
				maxHPBonus = 0;
				break;
			}
			case 6: {
				name = "Dagger";
				isOffensive = true;
				cost = PvPFrame.getRandom(60, 130);
				numOfAttacksBonus = 1;
				attackBonus = 1;
				defenseBonus = 0;
				maxHPBonus = 0;
				break;
			}
			case 7: {
				name = "Gauntlet";
				isDefensive = true;
				isOffensive = true;
				cost = PvPFrame.getRandom(120, 240);
				numOfAttacksBonus = PvPFrame.getRandom(0, 1);
				attackBonus = 0;
				defenseBonus = 0;
				maxHPBonus = PvPFrame.getRandom(1, 2);
				break;
			}
			case 8: {
				name = "Hammer";
				isOffensive = true;
				cost = PvPFrame.getRandom(200, 350);
				numOfAttacksBonus = PvPFrame.getRandom(3, 5);
				attackBonus = PvPFrame.getRandom(-2, 0);
				defenseBonus = 0;
				maxHPBonus = 0;
				break;
			}
			case 9: {
				name = "Helmet";
				isDefensive = true;
				cost = PvPFrame.getRandom(100, 200);
				numOfAttacksBonus = 0;
				attackBonus = 0;
				defenseBonus = PvPFrame.getRandom(-1, 0);
				maxHPBonus = PvPFrame.getRandom(1, 2);
				break;
			}
			case 10: {
				name = "Potion";
				cost = PvPFrame.getRandom(400, 600);
				numOfAttacksBonus = PvPFrame.getRandom(0, 2);
				attackBonus = PvPFrame.getRandom(0, 2);
				defenseBonus = PvPFrame.getRandom(0, 2);
				maxHPBonus = PvPFrame.getRandom(0, 2);
				break;
			}
			case 11: {
				name = "Shield";
				isDefensive = true;
				cost = PvPFrame.getRandom(110, 200);
				numOfAttacksBonus = 0;
				attackBonus = 0;
				defenseBonus = PvPFrame.getRandom(1, 3);
				maxHPBonus = 0;
				break;
			}
			case 12: {
				name = "Spear";
				isOffensive = true;
				cost = PvPFrame.getRandom(175, 285);
				numOfAttacksBonus = PvPFrame.getRandom(1, 2);
				attackBonus = 0;
				defenseBonus = PvPFrame.getRandom(0, 1);
				maxHPBonus = 0;
				break;
			}
			case 13: {
				name = "Sword";
				isOffensive = true;
				isDefensive = true;
				cost = PvPFrame.getRandom(350, 600);
				numOfAttacksBonus = PvPFrame.getRandom(1, 4);
				attackBonus = PvPFrame.getRandom(1, 4);
				defenseBonus = PvPFrame.getRandom(0, 1);
				maxHPBonus = 0;
				break;
			}
			case 14: {
				name = "Wand";
				isOffensive = true;
				cost = PvPFrame.getRandom(250, 450);
				numOfAttacksBonus = 0;
				attackBonus = PvPFrame.getRandom(1, 3);
				defenseBonus = PvPFrame.getRandom(1, 2);
				maxHPBonus = 0;
				break;
			}
		}
	}
	
	// get methods
	public int getNumOfAttacksBonus() {
		return numOfAttacksBonus;
	}
	
	public int getAttackBonus() {
		return attackBonus;
	}
	
	public int getDefenseBonus() {
		return defenseBonus;
	}
	
	public int getMaxHPBonus() {
		return maxHPBonus;
	}
	
	public int getCost() {
		return cost;
	}
	
	public int getIndex() {
		return typeIndex;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean getOffensive() {
		return isOffensive;
	}
	
	public boolean getDefensive() {
		return isDefensive;
	}
}
