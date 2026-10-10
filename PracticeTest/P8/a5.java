import java.util.*;

interface PaymentMethod {
    boolean pay(double amount);
    String label();
}

class CreditCardPayment implements PaymentMethod {
    double limit;
    CreditCardPayment(double limit) {
        this.limit = limit;
    }
    public boolean pay(double amount) {
        if (amount <= limit) {
            limit -= amount;
            return true;
        }
        return false;
    }
    public String label() {
        return "Credit Card";
    }
}

class DigitalWalletPayment implements PaymentMethod {
    double balance;
    DigitalWalletPayment(double balance) {
        this.balance = balance;
    }
    public boolean pay(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
    public String label() {
        return "Digital Wallet";
    }
}

interface OrderListener {
    void notify(String message);
}

class ConsoleNotifier implements OrderListener {
    public void notify(String message) {
        System.out.println("Notification: " + message);
    }
}

class MenuItem {
    String name;
    double price;
    MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class Restaurant {
    String name;
    Map<String, MenuItem> menu = new LinkedHashMap<>();
    Restaurant(String name) {
        this.name = name;
    }
    void addItem(MenuItem item) {
        menu.put(item.name, item);
    }
    MenuItem find(String itemName) {
        return menu.get(itemName);
    }
}

class OrderItem {
    MenuItem item;
    int quantity;
    OrderItem(MenuItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
    double total() {
        return item.price * quantity;
    }
}

enum OrderStatus {
    CREATED, PLACED, PAID
}

class Order {
    static int counter = 123;
    int id;
    String customer;
    List<OrderItem> items = new ArrayList<>();
    List<OrderListener> listeners = new ArrayList<>();
    OrderStatus status = OrderStatus.CREATED;

    Order(String customer) {
        this.customer = customer;
        this.id = counter++;
        System.out.println("Order #" + id + " created for " + customer + ".");
    }

    void addListener(OrderListener l) {
        listeners.add(l);
    }

    void publish(String message) {
        for (OrderListener l : listeners) l.notify(message);
    }

    void addItem(MenuItem item, int quantity) {
        if (status != OrderStatus.CREATED) {
            System.out.println("Items cannot be added after the order is placed.");
            return;
        }
        if (item == null || quantity <= 0) {
            System.out.println("Invalid item or quantity.");
            return;
        }
        items.add(new OrderItem(item, quantity));
        System.out.println("Added " + item.name + " (Qty " + quantity + ") to order #" + id + ".");
    }

    double total() {
        double t = 0;
        for (OrderItem i : items) t += i.total();
        return t;
    }

    void place() {
        if (items.isEmpty()) {
            System.out.println("Order #" + id + " cannot be placed with an empty cart.");
            return;
        }
        if (status != OrderStatus.CREATED) {
            System.out.println("Order #" + id + " is already placed.");
            return;
        }
        status = OrderStatus.PLACED;
        publish("Order #" + id + " placed. Total: $" + String.format("%.2f", total()) + ". Awaiting payment.");
    }

    void pay(PaymentMethod method) {
        if (status != OrderStatus.PLACED) {
            System.out.println("Order #" + id + " is not awaiting payment.");
            return;
        }
        if (method.pay(total())) {
            status = OrderStatus.PAID;
            System.out.println("Payment via " + method.label() + " successful.");
            publish("Order #" + id + " paid and placed. Status: " + status + ".");
        } else {
            System.out.println("Payment via " + method.label() + " failed.");
            publish("Order #" + id + " payment failed. Status: " + status + " (payment pending).");
        }
    }
}

class Customer {
    String name;
    Customer(String name) {
        this.name = name;
    }
    Order createOrder(OrderListener listener) {
        Order o = new Order(name);
        o.addListener(listener);
        return o;
    }
}

public class a5 {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant("Food Court");
        restaurant.addItem(new MenuItem("Pizza", 250));
        restaurant.addItem(new MenuItem("Soda", 50));
        restaurant.addItem(new MenuItem("Burger", 150));

        OrderListener notifier = new ConsoleNotifier();
        Customer customer = new Customer("Customer");

        Order first = customer.createOrder(notifier);
        first.place();
        first.addItem(restaurant.find("Pizza"), 2);
        first.addItem(restaurant.find("Soda"), 1);
        first.place();
        first.pay(new CreditCardPayment(1000));

        Order second = customer.createOrder(notifier);
        second.addItem(restaurant.find("Burger"), 1);
        second.place();
        second.pay(new DigitalWalletPayment(100));
    }
}