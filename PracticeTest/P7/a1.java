// Problem 1: Checkout Payment Handler

abstract class PaymentMethod {
    private static int counter = 1000;          // shared across every subclass
    private final String transactionId;

    protected PaymentMethod() {
        transactionId = "TXN-" + (++counter);   // assigned once, in one place
    }

    public abstract String processPayment(double amount);

    // Compile-time polymorphism (overloading): reuses the single-argument version
    public String processPayment(double amount, String note) {
        return processPayment(amount) + " (" + note + ")";
    }

    public String getTransactionId() {
        return transactionId;
    }
}

class CreditCardPayment extends PaymentMethod {
    private final String cardNumberLastFour;

    public CreditCardPayment(String cardNumberLastFour) {
        super();
        this.cardNumberLastFour = cardNumberLastFour;
    }

    @Override
    public String processPayment(double amount) {
        return "Charged $" + amount + " to card ending " + cardNumberLastFour
                + " - Txn " + getTransactionId();
    }
}

class CashPayment extends PaymentMethod {
    public CashPayment() {
        super();
    }

    @Override
    public String processPayment(double amount) {
        return "Received $" + amount + " in cash - Txn " + getTransactionId();
    }
}
public class a1 {

    // Works with any PaymentMethod; never checks the actual subclass
    static void printConfirmation(PaymentMethod payment, double amount) {
        System.out.println(payment.processPayment(amount));
    }

    public static void main(String[] args) {
        CreditCardPayment cc = new CreditCardPayment("4471");
        CashPayment cash = new CashPayment();

        System.out.println(cc.processPayment(250.0));
        System.out.println(cash.processPayment(40.0));
        System.out.println(cc.processPayment(250.0, "Birthday gift"));

        PaymentMethod ref = cc; // upcasting: CreditCardPayment stored as its parent type
        printConfirmation(ref, 250.0);

        // PaymentMethod p = new PaymentMethod(); // COMPILE ERROR: abstract class cannot be instantiated
    }
}