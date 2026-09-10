public class a5 {
    public static String reverseCustomerName(String customerName) {
        char[] nameChars = customerName.toCharArray();
        String reversed = "";
        for (int i = nameChars.length - 1; i >= 0; i--) {
            reversed += nameChars[i];
        }
        return reversed;
    }

    public static void main(String[] args) {
        String name = "Sunil";
        String reversed = reverseCustomerName(name);
        System.out.println("Original Name: " + name);
        System.out.println("Reversed Name: " + reversed);
    }
}