import NPC.Merchant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Realm {

    private static BufferedReader br;
    private static FantasyCharacter player = null;
    private static BattleScene battleScene = null;

    public static void main(String[] args) {

        br = new BufferedReader(new InputStreamReader(System.in));
        battleScene = new BattleScene();
        System.out.println("Enter hero name: ");
        try {
            command(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void command(String string) throws IOException {
        if (player == null) {
            player = new Hero(string, 100, 20, 20, 0, 0);
            System.out.printf(
                    "%s volunteered to save our world from dragons! May his armor be strong and his biceps round!",
                    player.getName()
            );
            printNavigation();
        }
        switch (string) {
            case "1" -> {
                bye();
                printNavigation();
                command(br.readLine());
            }
            case "2" -> commitFight();
            case "3" -> System.exit(1);
            case "yes" -> command("2");
            case "no" -> {
                printNavigation();
                command(br.readLine());
            }
        }
        command(br.readLine());
    }

    private static void printNavigation() {
        System.out.println("------------------------");
        System.out.println("Where do you want to go?");
        System.out.println("1. To the Merchant");
        System.out.println("2. To dark forest");
        System.out.println("3. Exit");
    }

    interface FightCallback {
        void fightWin();
        void fightLost();
    }

    private static void commitFight() {
        battleScene.fight(player,
                createMonster(),
                new FightCallback() {
                    @Override
                    public void fightWin() {
                        System.out.printf("%s wins! Now we have got %d exp and %d gold (%d HP)\n",
                                player.getName(), player.getXp(), player.getGold(), player.getHealthPoints());
                        System.out.println("Would you like to continue the adventure (yes) or return to the city (no)?");
                        try {
                            command(br.readLine());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void fightLost()  {
                        System.out.println("Sorry but you lost. Try it again and be careful\n");
                        try {
                            command("3"); //Exit game
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private static void bye() throws IOException {
        Merchant merchant = new Merchant();
        System.out.println("Merchant waiting for you");
        System.out.println("What do you want to bye?");
        System.out.println("0. See your props (props)");
        System.out.println("1. Potion to heal your woods, costs 50 gold (potion)");

        switch (br.readLine()) {
            case "0", "props" -> System.out.printf("%s - HP: %d | Gold: %d | EXP: %d\n",
                    player.getName(),
                    player.getHealthPoints(),
                    player.getGold(),
                    player.getXp()
            );
            case "1", "potion" -> {
                String potion = merchant.sell(Merchant.Goods.POTION, player.getGold());
                if (!potion.equals("")) {
                    player.setHealthPoints(player.getHealthPoints() + 50);
                }
            }
            default -> {}
        }
    }

    private static FantasyCharacter createMonster() {
        int random = (int) (Math.random() * 10);
        if (random == 0 || random == 10) {
            return new Dragon("Dragon", 100, 20, 20, 200, 50);
        } else if (random % 2 == 0) {
            return new Goblin("Goblin", 50, 10, 10, 100, 20);
        } else {
            return new Skeleton("Skeleton", 25, 20, 20, 100, 10);
        }
    }
}
