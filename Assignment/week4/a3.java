class Canteen {
    String canteenCode;
    String canteenName;
    int trustScore;

    public Canteen(String canteenCode, String canteenName, int trustScore) {
        this.canteenCode = canteenCode;
        this.canteenName = canteenName;
        this.trustScore = trustScore;
    }

    public Canteen(String canteenCode, String canteenName) {
        this(canteenCode, canteenName, 3);
    }

    public int compareTo(Canteen other) {
        if (this.trustScore != other.trustScore) {
            return other.trustScore - this.trustScore;
        }

        int codeCompare = this.canteenCode.compareToIgnoreCase(other.canteenCode);
        if (codeCompare != 0) {
            return codeCompare;
        }

        return Integer.compare(this.canteenName.length(), other.canteenName.length());
    }

    public static Canteen[] rankCanteens(Canteen[] canteens) {
        for (int i = 0; i < canteens.length - 1; i++) {
            for (int j = 0; j < canteens.length - 1 - i; j++) {
                if (canteens[j].compareTo(canteens[j + 1]) > 0) {
                    Canteen temp = canteens[j];
                    canteens[j] = canteens[j + 1];
                    canteens[j + 1] = temp;
                }
            }
        }
        return canteens;
    }
}

public class a3 {
    public static void main(String[] args) {
        Canteen[] canteens = {
            new Canteen("HB3-C", "Spice Junction", 3),
            new Canteen("hb1-c", "Grand Mess", 5),
            new Canteen("HB2-C", "Southern Treats")
        };

        Canteen[] ranked = Canteen.rankCanteens(canteens);

        System.out.print("[");
        for (int i = 0; i < ranked.length; i++) {
            System.out.print("\"" + ranked[i].canteenCode + "\"");
            if (i < ranked.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}