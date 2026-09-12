import java.util.Arrays;

class PatientVitals {
    private double[] readings = new double[500];
    private int count = 0;

    public PatientVitals(double[] initialReadings) {
        if (initialReadings != null) {
            for (double r : initialReadings) {
                recordReading(r);
            }
        }
    }

    public void recordReading(double reading) {
        if (reading > 0 && reading <= 45 && count < readings.length) {
            readings[count++] = reading;
        }
    }

    public double getAverage() {
        if (count == 0) return 0.0;
        double sum = 0;
        for (int i = 0; i < count; i++) {
            sum += readings[i];
        }
        return sum / count;
    }

    public double[] getAllReadings() {
        double[] copy = new double[count];
        System.arraycopy(readings, 0, copy, 0, count);
        return copy;
    }
}

public class a3 {
    public static void main(String[] args) {
        PatientVitals v = new PatientVitals(new double[]{36.5, -2, 37.1});
        System.out.println(Arrays.toString(v.getAllReadings()));
        
        double[] copy = v.getAllReadings();
        copy[0] = 999;
        System.out.println(v.getAllReadings()[0]);
    }
}