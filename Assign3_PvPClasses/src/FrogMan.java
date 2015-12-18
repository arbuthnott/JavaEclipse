
public class FrogMan extends Minion {

	// constructor
	public FrogMan(int idx, String nm) {
		name = nm;
		iconIndex = idx;
		valueInGold = PvPFrame.getRandom(100, 200);
		numOfAttacks = PvPFrame.getRandom(2,4);
		attackBonus = PvPFrame.getRandom(0,3);
		defense = PvPFrame.getRandom(7,13);
		maxHP = PvPFrame.getRandom(2,4);
		currentHP = maxHP;
	}
	
	// IMPLEMENT MESSAGE GETTER METHODS
	public String getAttackMessage() {
		if (PvPFrame.getRandom(0,1) == 1)
			return "Frogman attacks with a kick!";
		else
			return "Frogman makes a leaping attack!";
	}
	
	public String getDefenseMessage() {
		return "Frogman hops away from the attack.";
	}
	
	public String getStatus() {
		if (currentHP == maxHP) 
			return "Frogman is slimy and ready to fight.";
		else if (currentHP >= maxHP/2)
			return "Frogman is hurt but still hopping.";
		else if (currentHP > 0)
			return "Frogman is nearly belly-up.";
		else
			return "Frogman is dead.";
	}
}
