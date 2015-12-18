
public class Pixie extends Minion {

	// constructor
	public Pixie(int idx, String nm) {
		name = nm;
		iconIndex = idx;
		valueInGold = PvPFrame.getRandom(45, 95);
		numOfAttacks = 1;
		attackBonus = PvPFrame.getRandom(1,3);
		defense = PvPFrame.getRandom(11,16);
		maxHP = PvPFrame.getRandom(1,2);
		currentHP = maxHP;
	}
	
	// IMPLEMENT MESSAGE GETTER METHODS
	public String getAttackMessage() {
		if (PvPFrame.getRandom(0, 1) == 0)
			return "The Pixie bravely attacks!";
		else
			return "The Pixie weaves a quick attack spell.";
	}
	
	public String getDefenseMessage() {
		return "The attack misses the darting Pixie.";
	}
	
	public String getStatus() {
		if (currentHP == maxHP) 
			return "Pixie is at full flutter.";
		else if (currentHP > 0)
			return "Pixie is mildly swatted.";
		else
			return "Pixie is dead.";
	}

}

