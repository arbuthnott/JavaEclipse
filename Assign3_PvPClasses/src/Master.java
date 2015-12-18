import javax.swing.ImageIcon;

public class Master extends Combatant {
	// properties
	private final Armament[] arms = new Armament[5];
	private final Minion[] minions = new Minion[5];
	private int gold;
	private ImageIcon icon85, icon150;
	
	// constructor
	public Master(String nm, int numAttacks, int attkBonus, int dfns, int mHP, int startGold,
			ImageIcon ic85, ImageIcon ic150) {
		name = nm;
		numOfAttacks = numAttacks;
		attackBonus = attkBonus;
		defense = dfns;
		maxHP = mHP;
		currentHP = maxHP;
		gold = startGold;
		icon85 = ic85;
		icon150 = ic150;
	}
	
	// message methods implementations
	public String getAttackMessage() {
		String message = name + " attacks";
		for (int idx=0; idx<5; idx++) {
			if (arms[idx] != null) {
				if (PvPFrame.getRandom(0,4) < 2 && arms[idx].getOffensive())
					message = name + " attacks with the " + arms[idx].getName();
			}
		}
		return message + "!";
	}
	
	public String getDefenseMessage() {
		String message = name + " dodges the attack!";
		for (int idx=0; idx<5; idx++) {
			if (arms[idx] != null) {
				if (PvPFrame.getRandom(0,4) < 2 && arms[idx].getDefensive())
					message = name + "'s " + arms[idx].getName() + " blocks the attack.";
			}
		}
		return message;
	}
	
	public String getStatus() {
		if (currentHP > 9)
			return name + " is not even winded.";
		else if (currentHP > 6)
			return name + " is mildly wounded.";
		else if (currentHP > 3)
			return name + " may be in trouble.";
		else if (currentHP > 0)
			return name + " is gravely wounded.";
		else
			return name + " is dead.";
	}
	
	// new getter methods
	public Armament[] getArms() {
		return arms;
	}
	public Minion[] getMinions() {
		return minions;
	}
	public int getGold() {
		return gold;
	}
	public ImageIcon getIcon85() {
		return icon85;
	}
	public ImageIcon getIcon150() {
		return icon150;
	}
	
	// GOLD AND MONEY METHODS
	
	// attempt to spend some gold.  Returns true and decrements gold if the result
	// is still positive.  Does not decrement and returns false if the result is neg.
	public boolean spend(int amount) {
		if (gold - amount >= 0) {
			gold -= amount;
			return true;
		}
		else
			return false;
	}
	
	public void earn(int amount) {
		gold += amount;
	}
	
	// the amount of gold to be awarded by anyone defeating this Master.
	public int getDefeatValue() {
		int value = 150 + gold;
		for (int idx=0; idx<5; idx++) {
			if (minions[idx] != null)
				value += (int)(minions[idx].getResidualValue());
			if (arms[idx] != null)
				value += (int)(0.5*arms[idx].getCost());
		}
		return value;
	}
	
	// MINION AND ITEM METHODS
	
	// utility method - moves nulls to the end of a 5-object array.
	private void packArray(Object[] arr) {
		Object[] packed = new Object[5];
		int targetIdx = 0;
		for (int idx=0; idx<5; idx++) {
			if (arr[idx] != null) {
				packed[targetIdx] = arr[idx];
				targetIdx++;
			}
		}
		for (int idx=0; idx<5; idx++)
			arr[idx] = packed[idx];
	}
	
	// add a minion.  Returns false if minion array is already full, true otherwise.
	public boolean addMinion(Minion minion) {
		if (minions[4] == null) {
			minions[4] = minion;
			packArray(minions);
			return true;
		}
		else
			return false;
	}
	
	// remove a minion.
	public void removeMinion(int index) {
		if (index >= 0 && index < 5)
			minions[index] = null;
		packArray(minions);
	}
	
	// add an item.  Returns false if item array is already full, true otherwise.
	public boolean addArmament(Armament arm) {
		if (arms[4] == null) {
			arms[4] = arm;
			packArray(arms);
			return true;
		}
		else
			return false;
	}
	
	// remove an item.
	public void removeArmament(int index) {
		if (index >= 0 && index < 5)
			arms[index] = null;
		packArray(arms);
	}
	
	
	// OVERRIDE COMBAT GETTER METHODS
	@Override public int getNumOfAttacks() {
		int totalAttacks = numOfAttacks;
		for (int idx=0; idx<arms.length; idx++) 
			if (arms[idx] != null)
				totalAttacks += arms[idx].getNumOfAttacksBonus();
		return totalAttacks;
	}
	
	@Override public int getAttackBonus() {
		int totalBonus = attackBonus;
		for (int idx=0; idx<arms.length; idx++)
			if (arms[idx] != null)
				totalBonus += arms[idx].getAttackBonus();
		return totalBonus;
	}
	
	@Override public int getDefense() {
		int totalDefense = defense;
		for (int idx=0; idx<arms.length; idx++)
			if (arms[idx] != null)
				totalDefense += arms[idx].getDefenseBonus();
		return totalDefense;
	}
	
	@Override public int getMaxHP() {
		int totalMaxHP = maxHP;
		for (int idx=0; idx<arms.length; idx++)
			if (arms[idx] != null)
				totalMaxHP += arms[idx].getMaxHPBonus();
		return totalMaxHP;
	}
	
	public void setToFull() {
		currentHP = getMaxHP();
	}
	
}
