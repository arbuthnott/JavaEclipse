
public class Minotaur extends Minion {

	// constructor
	public Minotaur(int idx, String nm) {
		name = nm;
		iconIndex = idx;
		valueInGold = PvPFrame.getRandom(300, 500);
		numOfAttacks = PvPFrame.getRandom(3,5);
		attackBonus = PvPFrame.getRandom(-1,4);
		defense = PvPFrame.getRandom(11,14);
		maxHP = PvPFrame.getRandom(4,6);
		currentHP = maxHP;
	}
	
	// IMPLEMENT MESSAGE GETTER METHODS
	public String getAttackMessage() {
		if (PvPFrame.getRandom(0, 1) == 0)
			return "The Minotaur swings its mighty fists.";
		else if (PvPFrame.getRandom(0, 1) == 0)
			return "The Minotaur charges forward!";
		else
			return "The Minotaur swings its massive horns.";
	}
	
	public String getDefenseMessage() {
		return "The Minotaur blocks with its horns.";
	}
	
	public String getStatus() {
		if (currentHP == maxHP) 
			return "Minotaur is at full power.";
		else if (currentHP >= 3*maxHP/4)
			return "Minotaur is just hurt enough to be angry.";
		else if (currentHP >= maxHP/2)
			return "Minotaur has some flesh wounds.";
		else if (currentHP >= maxHP/4)
			return "Minotaur is wounded.";
		else if (currentHP > 0)
			return "Minotaur is mooing mournfully.";
		else
			return "Minotaur is dead.";
	}
}
