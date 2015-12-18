
public class Unicorn extends Minion {

	// constructor
	public Unicorn(int idx, String nm) {
		name = nm;
		iconIndex = idx;
		valueInGold = PvPFrame.getRandom(250, 350);
		numOfAttacks = PvPFrame.getRandom(2,5);
		attackBonus = PvPFrame.getRandom(1,6);
		defense = PvPFrame.getRandom(9,13);
		maxHP = PvPFrame.getRandom(3,4);
		currentHP = maxHP;
	}
	
	// IMPLEMENT MESSAGE GETTER METHODS
	public String getAttackMessage() {
		if (PvPFrame.getRandom(0, 1) == 0)
			return "The Unicorn rears up to attack!";
		else
			return "The Unicorn lunges with its horn.";
	}
	
	public String getDefenseMessage() {
		if (PvPFrame.getRandom(0, 1) == 0)
			return "The Unicorn is too fast.";
		else
			return "The Unicorn's horn blocks the attack!";
	}
	
	public String getStatus() {
		if (currentHP == maxHP) 
			return "Unicorn is shining with power.";
		else if (currentHP >= 2*maxHP/3)
			return "Unicorn is limping.";
		else if (currentHP >= maxHP/3)
			return "Unicorn is hurt but defiant.";
		else if (currentHP > 0)
			return "Unicorn is bravely fighting through the pain.";
		else
			return "Unicorn is dead.";
	}
}
