import java.util.ArrayList;
import java.util.List;

public class p3 {

    public static class PatientVitals {
        private List<Double> readings;

        public PatientVitals(double[] initialReadings) {
            this.readings = new ArrayList<>();
            if (initialReadings != null) {
                for (double reading : initialReadings) {
                    recordReading(reading);
                }
            }
        }

        public void recordReading(double reading) {
            if (reading > 0 && reading <= 45) {
                readings.add(reading);
            }
        }

        public double getAverage() {
            if (readings.isEmpty()) {
                return 0.0;
            }
            double sum = 0.0;
            for (double r : readings) {
                sum += r;
            }
            return sum / readings.size();
        }

        public double[] getAllReadings() {
            double[] copy = new double[readings.size()];
            for (int i = 0; i < readings.size(); i++) {
                copy[i] = readings.get(i);
            }
            return copy;
        }
    }

    public static void main(String[] args) {
        PatientVitals v = new PatientVitals(new double[]{36.5, -2, 37.1});
        double[] all = v.getAllReadings();
        for (double r : all) {
            System.out.println(r);
        }
    }
}