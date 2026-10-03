import java.io.PrintStream;
import java.util.*;

public class a3 {
    static final String DEGREE = "\u00B0C";

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

    interface Capability {
        String getName();
        String apply(int value);
    }

    static class PowerCapability implements Capability {
        private boolean on;

        @Override
        public String getName() {
            return "Power";
        }

        @Override
        public String apply(int value) {
            if (value != 0 && value != 1) {
                throw new IllegalArgumentException("power value must be 0 (off) or 1 (on)");
            }
            on = value == 1;
            return on ? "ON" : "OFF";
        }

        boolean isOn() {
            return on;
        }
    }

    static abstract class RangedCapability implements Capability {
        private final String name;
        private final String label;
        private final int min;
        private final int max;
        private final String unit;
        private int value;

        RangedCapability(String name, String label, int min, int max, String unit) {
            this.name = name;
            this.label = label;
            this.min = min;
            this.max = max;
            this.unit = unit;
            this.value = min;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String apply(int newValue) {
            if (newValue < min || newValue > max) {
                throw new IllegalArgumentException(label + " must be between " + min + unit + " and " + max + unit);
            }
            value = newValue;
            return label + " set to " + value + unit;
        }

        int getValue() {
            return value;
        }
    }

    static class BrightnessCapability extends RangedCapability {
        BrightnessCapability() {
            super("Brightness", "brightness", 0, 100, "%");
        }
    }

    static class TemperatureCapability extends RangedCapability {
        TemperatureCapability() {
            super("Temperature", "temperature", 16, 30, DEGREE);
        }
    }

    static class Device {
        private final String name;
        private final Map<String, Capability> capabilities = new LinkedHashMap<>();

        Device(String name, Capability... initial) {
            this.name = name;
            for (Capability capability : initial) {
                capabilities.put(key(capability.getName()), capability);
            }
        }

        private static String key(String capabilityName) {
            return capabilityName.toLowerCase();
        }

        String getName() {
            return name;
        }

        boolean supports(String capabilityName) {
            return capabilities.containsKey(key(capabilityName));
        }

        void addCapability(Capability capability) {
            if (supports(capability.getName())) {
                throw new IllegalArgumentException(name + " already has the " + capability.getName() + " capability");
            }
            capabilities.put(key(capability.getName()), capability);
            System.out.println(name + ": " + capability.getName() + " capability added.");
        }

        void apply(String capabilityName, int value) {
            Capability capability = capabilities.get(key(capabilityName));
            if (capability == null) {
                throw new IllegalArgumentException(name + " does not support " + capabilityName);
            }
            String result;
            try {
                result = capability.apply(value);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(name + " " + e.getMessage());
            }
            System.out.println(name + ": " + result + ".");
        }
    }

    static class SceneStep {
        private final String capabilityName;
        private final int value;

        SceneStep(String capabilityName, int value) {
            this.capabilityName = capabilityName;
            this.value = value;
        }

        String getCapabilityName() {
            return capabilityName;
        }

        int getValue() {
            return value;
        }
    }

    static class Scene {
        private final String name;
        private final List<SceneStep> steps = new ArrayList<>();

        Scene(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        Scene addStep(String capabilityName, int value) {
            steps.add(new SceneStep(capabilityName, value));
            return this;
        }

        void run(List<Device> devices) {
            System.out.println("Scene '" + name + "' started.");
            int applied = 0;
            for (SceneStep step : steps) {
                for (Device device : devices) {
                    if (device.supports(step.getCapabilityName())) {
                        try {
                            device.apply(step.getCapabilityName(), step.getValue());
                            applied++;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Rejected: " + e.getMessage() + ".");
                        }
                    }
                }
            }
            System.out.println("Scene '" + name + "' completed: " + applied + " actions applied.");
        }
    }

    static class ControlPanel {
        private final List<Device> devices = new ArrayList<>();
        private final Map<String, Scene> scenes = new LinkedHashMap<>();

        void addDevice(Device device) {
            devices.add(device);
        }

        void addScene(Scene scene) {
            scenes.put(scene.getName(), scene);
        }

        private Device findDevice(String deviceName) {
            for (Device device : devices) {
                if (device.getName().equals(deviceName)) {
                    return device;
                }
            }
            throw new IllegalArgumentException("Device " + deviceName + " was not found");
        }

        void runScene(String sceneName) {
            Scene scene = scenes.get(sceneName);
            if (scene == null) {
                throw new IllegalArgumentException("Scene " + sceneName + " was not found");
            }
            scene.run(devices);
        }

        void setValue(String deviceName, String capabilityName, int value) {
            findDevice(deviceName).apply(capabilityName, value);
        }

        void addCapability(String deviceName, Capability capability) {
            findDevice(deviceName).addCapability(capability);
        }
    }

    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));

        ControlPanel panel = new ControlPanel();
        panel.addDevice(new Device("Lab AC", new PowerCapability(), new TemperatureCapability()));
        panel.addDevice(new Device("Ceiling Lights", new PowerCapability(), new BrightnessCapability()));
        panel.addDevice(new Device("Projector", new PowerCapability()));

        panel.addScene(new Scene("Lecture Mode")
                .addStep("Power", 1)
                .addStep("Brightness", 40)
                .addStep("Temperature", 24));

        panel.runScene("Lecture Mode");
        attempt("Rejected: ", () -> panel.setValue("Lab AC", "Temperature", 12));
        panel.addCapability("Projector", new BrightnessCapability());
        panel.setValue("Projector", "Brightness", 70);
    }
}