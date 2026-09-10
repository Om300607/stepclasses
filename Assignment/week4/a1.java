class FoodOrder {
    private String studentName;
    private String dishName;
    private boolean isDelivered;

    public FoodOrder(String studentName, String dishName) {
        if (studentName == null || studentName.trim().isEmpty() ||
            dishName == null || dishName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid order details");
        }
        this.studentName = studentName.trim();
        this.dishName = dishName.trim();
        this.isDelivered = false;
    }

    public void markDelivered() {
        if (isDelivered) {
            System.out.println("Order for " + studentName + " has already been delivered!");
        } else {
            isDelivered = true;
            System.out.println("Order for " + studentName + " marked delivered.");
        }
    }

    public static void processBatch(String[][] rawOrders) {
        int validCount = 0;
        int rejectedCount = 0;

        for (String[] rawOrder : rawOrders) {
            try {
                new FoodOrder(rawOrder[0], rawOrder[1]);
                validCount++;
            } catch (IllegalArgumentException e) {
                rejectedCount++;
            }
        }

        System.out.println("Valid: " + validCount + " | Rejected: " + rejectedCount);
    }
}

public class a1 {
    public static void main(String[] args) {
        String[][] rawOrders = {
            {"Ravi", "Paneer Butter Masala"},
            {"", "Chole Bhature"},
            {"Meera", " "},
            {"Divya", "Veg Biryani"}
        };

        FoodOrder.processBatch(rawOrders);
    }
}