import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

abstract class Room {
    int number;
    Room(int number) {
        this.number = number;
    }
    abstract String category();
    abstract double nightlyRate();
}

class StandardRoom extends Room {
    StandardRoom(int number) {
        super(number);
    }
    String category() {
        return "Standard";
    }
    double nightlyRate() {
        return 150.0;
    }
}

class DeluxeRoom extends Room {
    DeluxeRoom(int number) {
        super(number);
    }
    String category() {
        return "Deluxe";
    }
    double nightlyRate() {
        return 200.0;
    }
}

class SuiteRoom extends Room {
    SuiteRoom(int number) {
        super(number);
    }
    String category() {
        return "Suite";
    }
    double nightlyRate() {
        return 350.0;
    }
}

class Booking {
    Room room;
    String customer;
    LocalDate from;
    LocalDate to;
    double total;
    boolean cancelled;
    Booking(Room room, String customer, LocalDate from, LocalDate to, double total) {
        this.room = room;
        this.customer = customer;
        this.from = from;
        this.to = to;
        this.total = total;
    }
    boolean overlaps(LocalDate f, LocalDate t) {
        return f.isBefore(to) && t.isAfter(from);
    }
}

class BookingManager {
    Map<Integer, Room> rooms = new LinkedHashMap<>();
    List<Booking> bookings = new ArrayList<>();

    void addRoom(Room r) {
        rooms.put(r.number, r);
    }

    Booking book(String customer, int roomNumber, LocalDate from, LocalDate to) {
        Room room = rooms.get(roomNumber);
        if (room == null) {
            System.out.println("Room " + roomNumber + " does not exist.");
            return null;
        }
        if (!to.isAfter(from)) {
            System.out.println("Booking failed: invalid dates.");
            return null;
        }
        for (Booking b : bookings) {
            if (!b.cancelled && b.room == room && b.overlaps(from, to)) {
                System.out.println("Booking failed: " + room.category() + " Room " + room.number + " is not available for " + from + " to " + to + ".");
                return null;
            }
        }
        long nights = ChronoUnit.DAYS.between(from, to);
        double total = nights * room.nightlyRate();
        Booking booking = new Booking(room, customer, from, to, total);
        bookings.add(booking);
        System.out.println(room.category() + " Room " + room.number + " booked from " + from + " to " + to + ". Total price: $" + String.format("%.2f", total));
        return booking;
    }

    void cancel(Booking b, LocalDate today) {
        if (b == null || b.cancelled) {
            System.out.println("No active booking to cancel.");
            return;
        }
        long daysBefore = ChronoUnit.DAYS.between(today, b.from);
        if (daysBefore < 0) {
            System.out.println("Cancellation failed: stay has already started.");
            return;
        }
        double refund = daysBefore >= 2 ? b.total : b.total * 0.5;
        b.cancelled = true;
        System.out.println(b.room.category() + " Room " + b.room.number + " booking cancelled successfully. Refund: $" + String.format("%.2f", refund));
    }
}

public class a3 {
    public static void main(String[] args) {
        BookingManager manager = new BookingManager();
        manager.addRoom(new DeluxeRoom(101));
        manager.addRoom(new StandardRoom(205));
        manager.addRoom(new SuiteRoom(301));

        LocalDate today = LocalDate.of(2024, 11, 20);

        Booking b1 = manager.book("Customer 1", 101, LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 5));
        manager.book("Customer 2", 205, LocalDate.of(2024, 12, 3), LocalDate.of(2024, 12, 7));
        manager.book("Customer 3", 101, LocalDate.of(2024, 12, 3), LocalDate.of(2024, 12, 7));
        manager.cancel(b1, today);
        manager.book("Customer 3", 101, LocalDate.of(2024, 12, 3), LocalDate.of(2024, 12, 7));
    }
}