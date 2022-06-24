abstract public class FantasyCharacter implements Fighter{

    private final String name;
    private int healthPoints;
    private int strength;
    private int dexterity;
    private int xp;
    private int gold;
    private int healthPointsMax;

    public FantasyCharacter(String name, int healthPoints, int strength, int dexterity, int xp, int gold) {
        this.name = name;
        this.healthPoints = healthPoints;
        this.strength = strength;
        this.dexterity = dexterity;
        this.xp = xp;
        this.gold = gold;

        this.healthPointsMax = healthPoints;
    }

    @Override
    public int attack() {
        if ((dexterity * 2 + (strength/2)) > getRandomValue()) {
            return strength * 2;
        } else if (dexterity * 3 > getRandomValue()) {
            return strength;
        } else {
            return 0;
        }
    }

    public String getName() {
        return name;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        if(healthPoints > this.healthPointsMax) {
            this.healthPoints = this.healthPointsMax;
        } else {
            this.healthPoints = healthPoints;
        }
        System.out.printf("Hey %s your woods were healed. You HP: %d\n", this.name, this.healthPoints);
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    private int getRandomValue() {
        return (int) (Math.random() * 100);
    }

    @Override
    public String toString() {
        return String.format("%s health: %d", name, healthPoints);
    }
}
