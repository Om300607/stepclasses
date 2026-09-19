// Problem 3: Fleet Maintenance Tracker

public class a3 {

    interface Insurable {
        String getInsuranceInfo();
    }

    abstract static class ServiceableVehicle {
        private double mileage;

        public abstract String performMaintenance();

        double getMileage() {
            return mileage;
        }

        // Reject first, add second
        void addMileage(double km) {
            if (km < 0) {
                System.out.println("Rejected: distance cannot be negative");
                return;
            }
            mileage += km;
        }
    }

    static class Forklift extends ServiceableVehicle implements Insurable {
        private final String assetTag;

        public Forklift(String assetTag) {
            this.assetTag = assetTag;
        }

        protected String getAssetTag() {
            return assetTag;
        }

        @Override
        public String performMaintenance() {
            return "Forklift " + assetTag + ": hydraulic and fork inspection complete";
        }

        @Override
        public String getInsuranceInfo() {
            return "Insured under fleet policy - Asset " + assetTag;
        }
    }

    // Multilevel: ServiceableVehicle -> Forklift -> HeavyDutyForklift
    static class HeavyDutyForklift extends Forklift {

        public HeavyDutyForklift(String assetTag) {
            super(assetTag);
        }

        @Override
        public String performMaintenance() {
            // reuse Forklift's message, then glue the extra step on top
            return super.performMaintenance() + " | high-pressure hydraulic check complete";
        }
    }

    // A vehicle with NO insurance capability, used to show the "otherwise" branch
    static class Bulldozer extends ServiceableVehicle {
        @Override
        public String performMaintenance() {
            return "Bulldozer: track and blade inspection complete";
        }
    }

    static String getInsuranceIfApplicable(ServiceableVehicle v) {
        if (v instanceof Insurable) {
            Insurable ins = (Insurable) v;   // safe cast
            return ins.getInsuranceInfo();
        }
        return "No insurance record exists";
    }

    public static void main(String[] args) {
        Forklift f = new Forklift("FL-22");
        f.addMileage(120);
        System.out.println(f.getMileage());                 // 120.0
        f.addMileage(-30);                                  // rejected, mileage unchanged
        System.out.println(f.getMileage());                 // 120.0

        System.out.println(f.performMaintenance());

        HeavyDutyForklift hd = new HeavyDutyForklift("HD-9");
        System.out.println(hd.performMaintenance());

        System.out.println(getInsuranceIfApplicable(f));                 // Insured under fleet policy - Asset FL-22
        System.out.println(getInsuranceIfApplicable(new Bulldozer()));   // No insurance record exists
    }
}