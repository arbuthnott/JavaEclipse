
public abstract class Combatant {
	
	// properties
	protected int numOfAttacks;
	protected int attackBonus;
	protected int defense;
	protected int maxHP;
	protected int currentHP;
	protected String name;
	
	// message generation abstract methods
	public abstract String getAttackMessage();
	public abstract String getDefenseMessage();
	public abstract String getStatus();
	
	// default getters and setters
	public int getNumOfAttacks() {
		return numOfAttacks;
	}
	public int getAttackBonus() {
		return attackBonus;
	}
	public int getDefense() {
		return defense;
	}
	public int getMaxHP() {
		return maxHP;
	}
	public int getCurrentHP() {
		return currentHP;
	}
	public String getName() {
		return name;
	}
	
	// COMBAT METHODS
	
	// Decreases currentHP by the input amount.
	public void damage(int amount) {
		currentHP = Math.max(0, currentHP - amount);
	}// end damage method
	
	// Increases currentHP by the input amount, to a max of maxHP
	public void heal(int amount) {
		currentHP = Math.min(getMaxHP(), currentHP + amount);
	}// end heal method
	
	// heals all damage.
	public void heal() {
		currentHP = maxHP;
	}
	
	// Returns an array of modified attack rolls (1-20 plus bonuses).
	public int[] getAttacks() {
		int[] attacks = new int[getNumOfAttacks()];
		for (int idx=0; idx<getNumOfAttacks(); idx++) {
			attacks[idx] = PvPFrame.getRandom(1,20);
			if (attacks[idx] > 18)
				attacks[idx] += 100;
			else if (attacks[idx] < 5)
				attacks[idx] -= 100;
			attacks[idx] += getAttackBonus();
		}
		return attacks;
	}
	
	// calculates how many of the input attacks are hits and applies the damage.
	// returns the amount of damage applied.
	public int applyAttacks(int[] attacks) {
		int hits = 0;
		for (int idx=0; idx<attacks.length; idx++) {
			if (attacks[idx] > getDefense()) {
				hits++;
			}
		}
		if (hits > 0)
			damage(hits);
		return hits;
	}
	
}// end Combatant class
