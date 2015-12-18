
public abstract class Minion extends Combatant {
	
	// properties
	protected boolean bonusesOn;
	protected int valueInGold;
	protected int iconIndex; // to reference icon, and compare for bonuses.
	
	// append an "owners" name to their own.
	public void setOwner(Master mstr) {
		name = mstr.getName() + "'s " + name;
	}
	
	// Getter methods
	public int getIndex() {
		return iconIndex;
	}
	public int getCost() {
		return valueInGold;
	}
	public int getResidualValue() {
		return (int)(valueInGold*(currentHP/maxHP));
	}
	
	// Bonus calculations against other minion types
	public void checkBonuses(Minion opponent) {
		bonusesOn = ((opponent.getIndex() - iconIndex) % 7 == 1);
	}
	
	public void setBonuses(boolean setting) {
		bonusesOn = setting;
	}
	
	// OVERRIDE COMBAT METHODS
	@Override public int[] getAttacks() {
		int[] attacks = super.getAttacks();
		if (bonusesOn) {
			for (int idx=0; idx< attacks.length; idx++)
				attacks[idx] += 3;
		}
		return attacks;
	}
	
	@Override public int applyAttacks(int[] attacks) {
		if (bonusesOn) {
			for (int idx=0; idx<attacks.length; idx++)
				attacks[idx] -= 2;
		}
		return super.applyAttacks(attacks);
	}

}
