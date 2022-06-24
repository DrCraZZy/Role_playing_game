import Fighter.FantasyCharacter;
import Fighter.Hero;

public class BattleScene {
    public void fight(FantasyCharacter hero, FantasyCharacter monster, Realm.FightCallback fightCallback) {
        Runnable runnable = () -> {
            int turn = 1;
            boolean isFightEnded = false;
            while(!isFightEnded) {
                System.out.println("---Turn: " + turn + "---");
                if(turn++ % 2 != 0){
                    isFightEnded = makeHit(monster, hero, fightCallback);
                } else {
                    isFightEnded = makeHit(hero, monster, fightCallback);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private Boolean makeHit(FantasyCharacter defender, FantasyCharacter attacker, Realm.FightCallback fightCallback) {
        int hit = attacker.attack();
        int defenderHealth = defender.getHealthPoints() - hit;
        if (hit != 0) {
            System.out.printf("%s make damage %d%n", attacker.getName(), hit);
            System.out.printf("%s get %d HP%n", defender.getName(), defenderHealth);
        } else {
            System.out.printf("%s missed%n", attacker.getName());
        }
        if (defenderHealth <= 0 && defender instanceof Hero) {
            System.out.println("Sorry, you fell in battle...");
            fightCallback.fightLost();
            return true;
        } else if (defenderHealth <= 0) {
            System.out.printf(
                    "You win this battle and gain %d exp and %d gold%n",
                    defender.getXp(), defender.getGold());
            attacker.setXp(attacker.getGold() + defender.getXp());
            attacker.setGold(attacker.getGold() + defender.getGold());
            fightCallback.fightWin();
            return true;
        } else {
            defender.setHealthPoints(defenderHealth);
            return false;
        }
    }
}
