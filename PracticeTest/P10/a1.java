import java.util.*;

abstract class Device {
    String id;
    boolean on;
    Device(String id) {
        this.id = id;
    }
}

interface Dimmable {
    void dim(int level);
}

interface Schedulable {
    void schedule(String time);
}

interface EnergyMonitor {
    int energy();
}

class Light extends Device implements Dimmable, Schedulable {
    int level;
    String time;
    Light(String id) {
        super(id);
    }
    public void dim(int level) {
        this.level = level;
    }
    public void schedule(String time) {
        this.time = time;
    }
}

class Fan extends Device implements Schedulable {
    String time;
    Fan(String id) {
        super(id);
    }
    public void schedule(String time) {
        this.time = time;
    }
}

class Plug extends Device implements EnergyMonitor {
    int kwh;
    Plug(String id, int kwh) {
        super(id);
        this.kwh = kwh;
    }
    public int energy() {
        return kwh;
    }
}

public class a1 {
    public static void main(String[] args) {
        Map<String, Device> devices = new LinkedHashMap<>();
        devices.put("L1", new Light("L1"));
        devices.put("F1", new Fan("F1"));
        devices.put("P1", new Plug("P1", 12));

        String[] commands = {
            "ON L1",
            "DIM L1 40",
            "DIM F1 50",
            "SCHEDULE F1 22:00",
            "ENERGY P1",
            "ENERGY L1"
        };

        for (String c : commands) {
            String[] p = c.split(" ");
            String cmd = p[0];
            Device d = devices.get(p[1]);
            if (d == null) {
                System.out.println(p[1] + " not found");
                continue;
            }
            switch (cmd) {
                case "ON":
                    d.on = true;
                    System.out.println(d.id + " turned ON");
                    break;
                case "OFF":
                    d.on = false;
                    System.out.println(d.id + " turned OFF");
                    break;
                case "DIM":
                    if (d instanceof Dimmable) {
                        ((Dimmable) d).dim(Integer.parseInt(p[2]));
                        System.out.println(d.id + " dimmed to " + p[2]);
                    } else {
                        System.out.println(d.id + " rejected: DIM unsupported");
                    }
                    break;
                case "SCHEDULE":
                    if (d instanceof Schedulable) {
                        ((Schedulable) d).schedule(p[2]);
                        System.out.println(d.id + " scheduled " + p[2]);
                    } else {
                        System.out.println(d.id + " rejected: SCHEDULE unsupported");
                    }
                    break;
                case "ENERGY":
                    if (d instanceof EnergyMonitor) {
                        System.out.println(d.id + " energy " + ((EnergyMonitor) d).energy() + " kWh");
                    } else {
                        System.out.println(d.id + " rejected: ENERGY unsupported");
                    }
                    break;
                default:
                    System.out.println(cmd + " unknown command");
            }
        }
    }
}