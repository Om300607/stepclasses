// Problem 5: Connected Home Control Panel

public class a5 {

    interface RemoteControllable {
        String connect(String appId);
    }

    interface EnergyTrackable {
        double getConsumptionWatts();
    }

    abstract static class HomeDevice {
        private static int counter = 1000;
        private final String serialNumber;

        protected HomeDevice() {
            serialNumber = "HD-" + (++counter);
        }

        public abstract String activate();

        String getSerialNumber() {
            return serialNumber;
        }
    }

    // Remote-controllable AND energy-trackable
    static class WashingMachine extends HomeDevice implements RemoteControllable, EnergyTrackable {
        private final double consumptionWatts;

        public WashingMachine(double consumptionWatts) {
            super();
            this.consumptionWatts = consumptionWatts;
        }

        @Override
        public String activate() {
            return "Washing machine " + getSerialNumber() + " started a cycle";
        }

        @Override
        public String connect(String appId) {
            return getSerialNumber() + " connected to " + appId;
        }

        @Override
        public double getConsumptionWatts() {
            return consumptionWatts;
        }
    }

    // Sibling of WashingMachine: same parent, but energy-trackable only
    static class Refrigerator extends HomeDevice implements EnergyTrackable {
        private final double consumptionWatts;

        public Refrigerator(double consumptionWatts) {
            super();
            this.consumptionWatts = consumptionWatts;
        }

        @Override
        public String activate() {
            return "Refrigerator " + getSerialNumber() + " started cooling";
        }

        @Override
        public double getConsumptionWatts() {
            return consumptionWatts;
        }
    }

    // Not a HomeDevice: it connects to devices but isn't one
    static class MobileApp implements RemoteControllable {
        private final String appName;

        public MobileApp(String appName) {
            this.appName = appName;
        }

        @Override
        public String connect(String appId) {
            return appName + " connected to " + appId;
        }
    }

    static void connectAll(RemoteControllable[] items, String appId) {
        for (RemoteControllable item : items) {
            System.out.println(item.connect(appId));
        }
    }

    // Returns 0.0 when the device has no energy tracking
    static double getConsumptionIfTrackable(HomeDevice d) {
        if (d instanceof EnergyTrackable) {
            EnergyTrackable et = (EnergyTrackable) d;   // safe cast
            return et.getConsumptionWatts();
        }
        return 0.0;
    }

    public static void main(String[] args) {
        WashingMachine wm = new WashingMachine(500.0);
        System.out.println(wm.activate());                 // Washing machine HD-1001 started a cycle
        System.out.println(wm.connect("HomeConnect"));     // HD-1001 connected to HomeConnect

        Refrigerator fridge = new Refrigerator(150.0);
        System.out.println(getConsumptionIfTrackable(fridge));   // 150.0

        MobileApp app = new MobileApp("HomeConnect App");
        System.out.println(app.connect("HomeConnect"));    // HomeConnect App connected to HomeConnect

        HomeDevice ref = wm; // upcasting: WashingMachine stored as its parent type
        System.out.println(getConsumptionIfTrackable(ref));      // 500.0

        connectAll(new RemoteControllable[]{wm, app}, "HomeConnect");
    }
}