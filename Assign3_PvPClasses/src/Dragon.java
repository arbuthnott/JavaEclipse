
public class Dragon extends Minion {
	
	// constructor
	public Dragon(int idx, String nm) {
		name = nm;
		iconIndex = idx;
		valueInGold = PvPFrame.getRandom(400,600);
		numOfAttacks = PvPFrame.getRandom(4,6);
		attackBonus = PvPFrame.getRandom(-4,1);
		defense = PvPFrame.getRandom(11,15);
		maxHP = PvPFrame.getRandom(6,8);
		currentHP = maxHP;
	}
	
	// IMPLEMENT MESSAGE GETTER METHODS
	public String getAttackMessage() {
		if (PvPFrame.getRandom(0, 1) == 0)
			return "Dragon attacks with fiery breath.";
		else
			return "Dragon attacks with sharp claws!";
	}
	
	public String getDefenseMessage() {
		return "The attack bounces off the Dragon's scales.";
	}
	
	public String getStatus() {
		if (currentHP == maxHP) 
			return "Dragon is at full strength.";
		else if (currentHP >= 3*maxHP/4)
			return "Dragon is mildly bruised.";
		else if (currentHP >= maxHP/2)
			return "Dragon is bruised and battered.";
		else if (currentHP >= maxHP/4)
			return "Dragon is wounded.";
		else if (currentHP > 0)
			return "Dragon's flame is nearly extinguished.";
		else
			return "Dragon is dead.";
	}

}
