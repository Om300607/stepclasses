import java.io.PrintStream;
import java.util.*;

public class a2 {
    static final String RUPEE = "\u20B9";
    static final String ARROW = "\u2192";

    interface Action {
        void run();
    }

    static void attempt(String prefix, Action action) {
        try {
            action.run();
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(prefix + e.getMessage() + ".");
        }
    }

    static String money(double value) {
        return RUPEE + String.format(Locale.US, "%.2f", value);
    }

    static String weightText(double weight) {
        return weight == Math.floor(weight) ? String.valueOf((long) weight) : String.valueOf(weight);
    }

    enum Status {
        BOOKED, PICKED_UP, IN_TRANSIT, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
    }

    interface ShippingType {
        String getName();
        double calculateCharge(double weightKg);
    }

    static class StandardShipping implements ShippingType {
        @Override
        public String getName() {
            return "Standard";
        }

        @Override
        public double calculateCharge(double weightKg) {
            return 40 + 10 * weightKg;
        }
    }

    static class ExpressShipping implements ShippingType {
        @Override
        public String getName() {
            return "Express";
        }

        @Override
        public double calculateCharge(double weightKg) {
            return 80 + 15 * weightKg;
        }
    }

    static class FragileShipping implements ShippingType {
        private final ShippingType base;

        FragileShipping(ShippingType base) {
            this.base = base;
        }

        @Override
        public String getName() {
            return "Fragile";
        }

        @Override
        public double calculateCharge(double weightKg) {
            return base.calculateCharge(weightKg) + 50;
        }
    }

    interface NotificationChannel {
        void send(String message);
    }

    static class SmsChannel implements NotificationChannel {
        @Override
        public void send(String message) {
            System.out.println("[SMS] " + message);
        }
    }

    static class EmailChannel implements NotificationChannel {
        @Override
        public void send(String message) {
            System.out.println("[Email] " + message);
        }
    }

    static class Customer {
        private final String name;
        private final List<Parcel> parcels = new ArrayList<>();

        Customer(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        void addParcel(Parcel parcel) {
            parcels.add(parcel);
        }

        List<Parcel> getParcels() {
            return Collections.unmodifiableList(parcels);
        }
    }

    static class Parcel {
        private static final Map<Status, Status> NEXT = new EnumMap<>(Status.class);

        static {
            NEXT.put(Status.BOOKED, Status.PICKED_UP);
            NEXT.put(Status.PICKED_UP, Status.IN_TRANSIT);
            NEXT.put(Status.IN_TRANSIT, Status.OUT_FOR_DELIVERY);
            NEXT.put(Status.OUT_FOR_DELIVERY, Status.DELIVERED);
        }

        private final String id;
        private final Customer customer;
        private final double weightKg;
        private final ShippingType shippingType;
        private final double charge;
        private Status status = Status.BOOKED;
        private final List<NotificationChannel> channels = new ArrayList<>();

        Parcel(String id, Customer customer, double weightKg, ShippingType shippingType) {
            if (weightKg <= 0) {
                throw new IllegalArgumentException("Weight must be positive");
            }
            this.id = id;
            this.customer = customer;
            this.weightKg = weightKg;
            this.shippingType = shippingType;
            this.charge = shippingType.calculateCharge(weightKg);
        }

        String getId() {
            return id;
        }

        Customer getCustomer() {
            return customer;
        }

        double getWeightKg() {
            return weightKg;
        }

        ShippingType getShippingType() {
            return shippingType;
        }

        double getCharge() {
            return charge;
        }

        Status getStatus() {
            return status;
        }

        void subscribe(NotificationChannel channel) {
            if (!channels.contains(channel)) {
                channels.add(channel);
            }
        }

        void advanceTo(Status target) {
            if (NEXT.get(status) != target) {
                throw new IllegalStateException("Invalid transition: " + status + " " + ARROW + " " + target + " is not allowed");
            }
            status = target;
            announce();
        }

        void cancel() {
            if (status != Status.BOOKED) {
                throw new IllegalStateException(id + " can be cancelled only while BOOKED");
            }
            status = Status.CANCELLED;
            announce();
        }

        void announce() {
            for (NotificationChannel channel : channels) {
                channel.send(id + " is now " + status + ".");
            }
        }
    }

    static class ParcelService {
        private final Map<String, Parcel> parcels = new LinkedHashMap<>();

        Parcel book(Customer customer, String parcelId, double weightKg, ShippingType type, NotificationChannel... channels) {
            if (parcels.containsKey(parcelId)) {
                throw new IllegalArgumentException("Parcel " + parcelId + " already exists");
            }
            Parcel parcel = new Parcel(parcelId, customer, weightKg, type);
            for (NotificationChannel channel : channels) {
                parcel.subscribe(channel);
            }
            parcels.put(parcelId, parcel);
            customer.addParcel(parcel);
            System.out.println("Parcel " + parcelId + " booked (" + type.getName() + ", " + weightText(weightKg) + " kg).");
            System.out.println("Charge: " + money(parcel.getCharge()) + ".");
            parcel.announce();
            return parcel;
        }

        private Parcel find(String parcelId) {
            Parcel parcel = parcels.get(parcelId);
            if (parcel == null) {
                throw new IllegalArgumentException("Parcel " + parcelId + " was not found");
            }
            return parcel;
        }

        void updateStatus(String parcelId, Status target) {
            find(parcelId).advanceTo(target);
        }

        void cancel(String parcelId) {
            find(parcelId).cancel();
        }
    }

    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));

        ParcelService service = new ParcelService();
        Customer customer = new Customer("Arjun");

        service.book(customer, "P101", 2, new ExpressShipping(), new SmsChannel(), new EmailChannel());
        service.updateStatus("P101", Status.PICKED_UP);
        attempt("Cancellation failed: ", () -> service.cancel("P101"));
        service.updateStatus("P101", Status.IN_TRANSIT);
        attempt("", () -> service.updateStatus("P101", Status.DELIVERED));
    }
}