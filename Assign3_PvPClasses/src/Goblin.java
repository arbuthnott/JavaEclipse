
public class Goblin extends Minion {

	// constructor
	public Goblin(int idx, String nm) {
		name = nm;
		iconIndex = idx;
		valueInGold = PvPFrame.getRandom(60, 115);
		numOfAttacks = PvPFrame.getRandom(1,3);
		attackBonus = PvPFrame.getRandom(-2,2);
		defense = PvPFrame.getRandom(6,12);
		maxHP = PvPFrame.getRandom(1,3);
		currentHP = maxHP;
	}
	
	// IMPLEMENT MESSAGE GETTER METHODS
	public String getAttackMessage() {
		if (PvPFrame.getRandom(0, 2) == 0)
			return "The Goblin attacks with a vicious bite!";
		else
			return "The Goblin rushes forward to attack!";
	}
	
	public String getDefenseMessage() {
		return "The Goblin is too quick to be hit.";
	}
	
	public String getStatus() {
		if (currentHP == maxHP) 
			return "Goblin is ready to fight.";
		else if (currentHP > 0)
			return "Goblin is bloodied.";
		else
			return "Goblin is dead.";
	}

}
