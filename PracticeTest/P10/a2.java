import java.util.*;

class Customer {
    int id;
    String name;
    Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer c = (Customer) o;
        return id == c.id && name.equals(c.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
    @Override
    public String toString() {
        return "(" + id + ", " + name + ")";
    }
}

class CustomerRegistry {
    Set<Customer> customers = new HashSet<>();
    boolean add(Customer c) {
        return customers.add(c);
    }
    boolean contains(Customer c) {
        return customers.contains(c);
    }
    int uniqueCount() {
        return customers.size();
    }
}

public class a2 {
    public static void main(String[] args) {
        CustomerRegistry registry = new CustomerRegistry();
        System.out.println(registry.add(new Customer(101, "Asha")));
        System.out.println(registry.add(new Customer(101, "Asha")) ? "true" : "false (duplicate rejected)");
        System.out.println(registry.add(new Customer(102, "Ravi")));
        System.out.println("unique count " + registry.uniqueCount());
        System.out.println("contains (101, Asha)? " + registry.contains(new Customer(101, "Asha")));
    }
}