// Problem 5: Community Library Checkout System

interface Renewable {
    String renew();
}

interface Reservable {
    String reserve();
}

abstract class LibraryItem {
    private static int counter = 1000;
    private final String itemId;

    protected LibraryItem() {
        itemId = "LI-" + (++counter);
    }

    public abstract int getLoanPeriodDays();

    public String getItemId() {
        return itemId;
    }
}

// Renewable AND Reservable
class Textbook extends LibraryItem implements Renewable, Reservable {
    private final String title;

    public Textbook(String title) {
        super();
        this.title = title;
    }

    @Override
    public int getLoanPeriodDays() {
        return 14;
    }

    @Override
    public String renew() {
        return title + " renewed";
    }

    @Override
    public String reserve() {
        return title + " reserved";
    }
}

// Sibling of Textbook: same parent, but Renewable only
class Magazine extends LibraryItem implements Renewable {
    private final String title;

    public Magazine(String title) {
        super();
        this.title = title;
    }

    @Override
    public int getLoanPeriodDays() {
        return 7;
    }

    @Override
    public String renew() {
        return title + " renewed";
    }
}

// Not a LibraryItem at all
class DigitalPass implements Renewable {
    private final String resourceName;

    public DigitalPass(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String renew() {
        return resourceName + " renewed";
    }
}

public class a5 {

    static void processCheckouts(LibraryItem[] items) {
        for (LibraryItem item : items) {
            System.out.println(item.getItemId() + " loan period: "
                    + item.getLoanPeriodDays() + " days");
        }
    }

    // Takes Object on purpose: works on anything, related to LibraryItem or not
    static String reserveIfSupported(Object o) {
        if (o instanceof Reservable) {
            Reservable r = (Reservable) o; // safe cast
            return r.reserve();
        }
        return "Reservation not supported";
    }

    public static void main(String[] args) {
        Textbook t = new Textbook("Java Fundamentals");
        System.out.println(t.getLoanPeriodDays());   // 14
        System.out.println(t.renew());
        System.out.println(t.reserve());

        Magazine m = new Magazine("Tech Monthly");
        System.out.println(reserveIfSupported(m));   // Reservation not supported

        DigitalPass d = new DigitalPass("E-Journal Access");
        System.out.println(reserveIfSupported(d));   // Reservation not supported

        LibraryItem ref = t; // upcasting: Textbook stored as its parent type
        System.out.println(reserveIfSupported(ref)); // Java Fundamentals reserved

        processCheckouts(new LibraryItem[]{t, m});
    }
}