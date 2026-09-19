// Problem 2: Home Safety Alert Network

interface Alertable {
    String sendAlert(String message);
}

class SecuritySensor {
    private final String zoneName;

    public SecuritySensor(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getZoneName() {
        return zoneName;
    }
}

class MotionSensor extends SecuritySensor implements Alertable {
    public MotionSensor(String zoneName) {
        super(zoneName);
    }

    @Override
    public String sendAlert(String message) {
        return "[" + getZoneName() + "] " + message;
    }
}

// Multilevel: SecuritySensor -> MotionSensor -> DualZoneMotionSensor
class DualZoneMotionSensor extends MotionSensor {
    private final String secondZoneName;

    public DualZoneMotionSensor(String zoneName, String secondZoneName) {
        super(zoneName);
        this.secondZoneName = secondZoneName;
    }

    @Override
    public String sendAlert(String message) {
        // reuse the parent's message, then add the new detail on top
        return super.sendAlert(message) + " [also covering " + secondZoneName + "]";
    }
}

// No relationship to SecuritySensor at all - only the Alertable capability
class SmokeDetector implements Alertable {
    private final String deviceId;

    public SmokeDetector(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String sendAlert(String message) {
        return "[" + deviceId + "] " + message;
    }
}

public class a2 {

    static void broadcastAll(Alertable[] devices, String message) {
        for (Alertable device : devices) {
            System.out.println(device.sendAlert(message));
        }
    }

    static String getZoneIfMotionSensor(Alertable a) {
        if (a instanceof MotionSensor) {
            MotionSensor ms = (MotionSensor) a; // safe downcast
            return ms.getZoneName();
        }
        return "Not a motion sensor";
    }

    public static void main(String[] args) {
        MotionSensor m = new MotionSensor("Living Room");
        DualZoneMotionSensor d = new DualZoneMotionSensor("Hallway", "Stairwell");
        SmokeDetector s = new SmokeDetector("SD-01");

        System.out.println(m.sendAlert("Motion detected"));
        System.out.println(d.sendAlert("Motion detected"));
        System.out.println(s.sendAlert("Smoke detected"));

        System.out.println("--- broadcast ---");
        broadcastAll(new Alertable[]{m, d, s}, "Alert!");

        System.out.println(getZoneIfMotionSensor(m));
        System.out.println(getZoneIfMotionSensor(s));
    }
}