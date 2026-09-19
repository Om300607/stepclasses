// Problem 4: Arena Battle Simulator

public class a4 {

    interface Attackable {
        String attack();
        String attack(String weaponName);   // overload: same name, different parameter list
    }

    interface Defendable {
        String defend();
    }

    abstract static class GameCharacter {
        private static int counter = 1000;
        private final String characterId;

        protected GameCharacter() {
            characterId = "GC-" + (++counter);
        }

        public abstract String getSpecialMove();

        String getCharacterId() {
            return characterId;
        }
    }

    static class Warrior extends GameCharacter implements Attackable, Defendable {
        private final String name;

        public Warrior(String name) {
            super();
            this.name = name;
        }

        @Override
        public String attack() {
            return name + " strikes with a blade";
        }

        @Override
        public String attack(String weaponName) {
            String article = "aeiouAEIOU".indexOf(weaponName.charAt(0)) >= 0 ? "an" : "a";
            return name + " strikes with " + article + " " + weaponName;
        }

        @Override
        public String defend() {
            return name + " raises a shield";
        }

        @Override
        public String getSpecialMove() {
            return name + " unleashes Whirlwind Slash";
        }
    }

    // Not a GameCharacter: no characterId, no special move. It only CAN defend.
    static class Trap implements Defendable {
        private final String trapType;

        public Trap(String trapType) {
            this.trapType = trapType;
        }

        @Override
        public String defend() {
            return trapType + " triggers automatically";
        }
    }

    static void resolveDefense(Defendable[] combatants) {
        for (Defendable c : combatants) {
            System.out.println(c.defend());
        }
    }

    public static void main(String[] args) {
        Warrior w = new Warrior("Kael");
        System.out.println(w.attack());
        System.out.println(w.attack("Iron Sword"));
        System.out.println(w.defend());
        System.out.println(w.getSpecialMove());

        Trap t = new Trap("Spike Pit");
        System.out.println(t.defend());

        resolveDefense(new Defendable[]{w, t});
    }
}