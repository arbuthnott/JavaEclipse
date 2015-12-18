
public class Harpy extends Minion {

	// constructor
	public Harpy(int idx, String nm) {
		name = nm;
		iconIndex = idx;
		valueInGold = PvPFrame.getRandom(175, 240);
		numOfAttacks = PvPFrame.getRandom(2,3);
		attackBonus = PvPFrame.getRandom(2,4);
		defense = PvPFrame.getRandom(10,14);
		maxHP = PvPFrame.getRandom(2,3);
		currentHP = maxHP;
	}
	
	// IMPLEMENT MESSAGE GETTER METHODS
	public String getAttackMessage() {
		if (PvPFrame.getRandom(0, 2) == 0)
			return "The Harpy casts an evil spell!";
		else
			return "The Harpy swoops in for an attack with her talons.";
	}
	
	public String getDefenseMessage() {
		if (PvPFrame.getRandom(0, 1) == 0)
			return "The Harpy flies up out of reach.";
		else
			return "The attack doesn't penetrate her thick feathers.";
	}
	
	public String getStatus() {
		if (currentHP == maxHP) 
			return "Harpy is swooping and weaving.";
		else if (currentHP >= maxHP/2)
			return "Harpy has lost a few feathers.";
		else if (currentHP > 0)
			return "Harpy is grounded and hurt.";
		else
			return "Harpy is dead.";
	}
	
}
