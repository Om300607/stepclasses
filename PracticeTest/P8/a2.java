import java.util.*;

abstract class Vehicle {
    String name;
    boolean available = true;
    Vehicle(String name) {
        this.name = name;
    }
    abstract double charge(int days);
}

class LuxuryCar extends Vehicle {
    LuxuryCar(String name) {
        super(name);
    }
    double charge(int days) {
        return days * 100.0 * 1.10;
    }
}

class StandardCar extends Vehicle {
    StandardCar(String name) {
        super(name);
    }
    double charge(int days) {
        return days * 50.0;
    }
}

class Rental {
    Vehicle vehicle;
    String customer;
    int days;
    double total;
    Rental(Vehicle vehicle, String customer, int days, double total) {
        this.vehicle = vehicle;
        this.customer = customer;
        this.days = days;
        this.total = total;
    }
}

class RentalService {
    Map<Vehicle, Rental> active = new LinkedHashMap<>();

    void rent(String customer, Vehicle v, int days) {
        if (days <= 0) {
            System.out.println("Invalid rental period.");
            return;
        }
        if (!v.available) {
            System.out.println(v.name + " is not available for rent.");
            return;
        }
        double total = v.charge(days);
        v.available = false;
        active.put(v, new Rental(v, customer, days, total));
        System.out.println(v.name + " rented for " + days + " days. Total charge: $" + String.format("%.2f", total));
    }

    void giveBack(Vehicle v) {
        if (v.available || !active.containsKey(v)) {
            System.out.println(v.name + " was not rented.");
            return;
        }
        active.remove(v);
        v.available = true;
        System.out.println(v.name + " returned. Now available.");
    }
}

public class a2 {
    public static void main(String[] args) {
        RentalService service = new RentalService();
        Vehicle luxury = new LuxuryCar("Luxury Car A");
        Vehicle standard = new StandardCar("Standard Car B");

        service.rent("Customer", luxury, 3);
        service.rent("Customer", standard, 5);
        service.rent("Customer", luxury, 2);
        service.giveBack(luxury);
    }
}