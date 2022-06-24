package NPC;

import java.util.Arrays;
import java.util.Optional;

public class Merchant implements Seller {

    @Override
    public String sell(Goods goods, int gold) {
        String result = "";
        String goodName = "";

        if (goods == Goods.POTION) {
            goodName = "potion";
        }

        Optional<Goods> goodEnum = Goods.getGoodByName(goodName);

        if(goodEnum.isPresent()) {
            if(goodEnum.get().getCosts() < gold) {
                result = goodName;
            } else {
                System.out.println("Sorry but you have not enough money.");
            }
        }
        return result;
    }

    public enum Goods {
        POTION("potion", 10);

        private final int costs;
        private final String name;

        Goods(String name, int costs){
            this.costs = costs;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public int getCosts() {
            return costs;
        }

        public static Optional<Goods> getGoodByName(String name) {
            return Arrays.stream(Goods.values())
                    .filter(good -> good.getName().equals(name))
                    .findFirst();
        }
    }
}
