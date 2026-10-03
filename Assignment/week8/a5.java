import java.io.PrintStream;
import java.util.*;

public class a5 {
    static final String RUPEE = "\u20B9";

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

    static long toPaise(double rupees) {
        return Math.round(rupees * 100);
    }

    static String plain(long paise) {
        return String.format(Locale.US, "%.2f", paise / 100.0);
    }

    static String money(long paise) {
        return RUPEE + plain(paise);
    }

    interface PricingPlan {
        String getName();
        long priceFor(long basePaise);
    }

    static class DayScholarPlan implements PricingPlan {
        @Override
        public String getName() {
            return "Day Scholar";
        }

        @Override
        public long priceFor(long basePaise) {
            return basePaise;
        }
    }

    static class HostellerPlan implements PricingPlan {
        @Override
        public String getName() {
            return "Hosteller";
        }

        @Override
        public long priceFor(long basePaise) {
            return Math.round(basePaise * 0.90);
        }
    }

    static class StaffPlan implements PricingPlan {
        @Override
        public String getName() {
            return "Staff";
        }

        @Override
        public long priceFor(long basePaise) {
            return Math.round(basePaise * 0.80);
        }
    }

    enum CardState {
        ACTIVE, BLOCKED
    }

    enum TransactionType {
        TOP_UP, PURCHASE, REFUND
    }

    static class Transaction {
        private final int id;
        private final TransactionType type;
        private final long amountPaise;
        private final String description;
        private boolean refunded;

        Transaction(int id, TransactionType type, long amountPaise, String description) {
            this.id = id;
            this.type = type;
            this.amountPaise = amountPaise;
            this.description = description;
        }

        int getId() {
            return id;
        }

        TransactionType getType() {
            return type;
        }

        long getAmountPaise() {
            return amountPaise;
        }

        String getDescription() {
            return description;
        }

        boolean isRefunded() {
            return refunded;
        }

        void markRefunded() {
            refunded = true;
        }
    }

    static class SmartCard {
        private static final long MIN_TOP_UP = 10000;
        private static final long MAX_BALANCE = 500000;

        private final String id;
        private final PricingPlan plan;
        private CardState state = CardState.ACTIVE;
        private final List<Transaction> transactions = new ArrayList<>();

        SmartCard(String id, PricingPlan plan) {
            this.id = id;
            this.plan = plan;
        }

        String getId() {
            return id;
        }

        PricingPlan getPlan() {
            return plan;
        }

        CardState getState() {
            return state;
        }

        long getBalance() {
            long sum = 0;
            for (Transaction transaction : transactions) {
                sum += transaction.getAmountPaise();
            }
            return sum;
        }

        private void requireActive() {
            if (state == CardState.BLOCKED) {
                throw new IllegalStateException("Card " + id + " is blocked");
            }
        }

        private Transaction addTransaction(TransactionType type, long amountPaise, String description) {
            Transaction transaction = new Transaction(transactions.size() + 1, type, amountPaise, description);
            transactions.add(transaction);
            return transaction;
        }

        void topUp(double rupees) {
            requireActive();
            long paise = toPaise(rupees);
            if (paise < MIN_TOP_UP) {
                throw new IllegalArgumentException("Minimum top-up is " + money(MIN_TOP_UP));
            }
            if (getBalance() + paise > MAX_BALANCE) {
                throw new IllegalArgumentException("Balance cannot exceed " + money(MAX_BALANCE));
            }
            addTransaction(TransactionType.TOP_UP, paise, "Top-up");
            System.out.println(id + " topped up with " + money(paise) + ". Balance: " + money(getBalance()) + ".");
        }

        void purchase(String item, double priceRupees) {
            requireActive();
            long base = toPaise(priceRupees);
            if (base <= 0) {
                throw new IllegalArgumentException("Price must be positive");
            }
            long charged = plan.priceFor(base);
            long balance = getBalance();
            if (charged > balance) {
                throw new IllegalStateException("Insufficient balance (required " + money(charged) + ", available " + money(balance) + ")");
            }
            addTransaction(TransactionType.PURCHASE, -charged, item);
            System.out.println(item + " purchased for " + money(charged) + ". Balance: " + money(getBalance()) + ".");
        }

        void refund(String item) {
            Transaction target = null;
            boolean found = false;
            for (Transaction transaction : transactions) {
                if (transaction.getType() == TransactionType.PURCHASE && transaction.getDescription().equals(item)) {
                    found = true;
                    if (!transaction.isRefunded()) {
                        target = transaction;
                        break;
                    }
                }
            }
            if (!found) {
                throw new IllegalArgumentException("No purchase found for " + item);
            }
            if (target == null) {
                throw new IllegalStateException(item + " has already been refunded");
            }
            long amount = -target.getAmountPaise();
            target.markRefunded();
            addTransaction(TransactionType.REFUND, amount, item);
            System.out.println("Refund of " + money(amount) + " for " + item + " processed. Balance: " + money(getBalance()) + ".");
        }

        void block() {
            state = CardState.BLOCKED;
            System.out.println("Card " + id + " blocked.");
        }

        void unblock() {
            state = CardState.ACTIVE;
            System.out.println("Card " + id + " unblocked.");
        }

        String getMiniStatement() {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < transactions.size(); i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                long amount = transactions.get(i).getAmountPaise();
                builder.append(amount >= 0 ? "+" : "-").append(plain(Math.abs(amount)));
            }
            builder.append(" = ").append(money(getBalance())).append(".");
            return "Mini-statement for " + id + ": " + builder;
        }
    }

    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));

        SmartCard card = new SmartCard("C-2045", new HostellerPlan());

        card.topUp(500);
        card.purchase("Veg Thali", 120);
        card.purchase("Cold Coffee", 60);
        attempt("Purchase failed: ", () -> card.purchase("Bulk Order", 400));
        card.refund("Veg Thali");
        attempt("Refund rejected: ", () -> card.refund("Veg Thali"));
        System.out.println(card.getMiniStatement());
    }
}