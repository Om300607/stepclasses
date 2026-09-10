public class a2 {
    public static boolean isPalindromeIterative(String text) {
        int left = 0;
        int right = text.length() - 1;
        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static boolean isPalindromeRecursive(String text) {
        if (text.length() <= 1) {
            return true;
        }
        if (text.charAt(0) != text.charAt(text.length() - 1)) {
            return false;
        }
        return isPalindromeRecursive(text.substring(1, text.length() - 1));
    }

    public static boolean isPalindromeArrayReversal(String text) {
        char[] arr = text.toCharArray();
        char[] reversed = new char[arr.length];
        for (int i = 0; i < arr.length; i++) {
            reversed[i] = arr[arr.length - 1 - i];
        }
        String reversedStr = new String(reversed);
        return text.equals(reversedStr);
    }

    public static void main(String[] args) {
        String[] testCases = {"madam", "hello"};
        
        for (String text : testCases) {
            boolean iter = isPalindromeIterative(text);
            boolean rec = isPalindromeRecursive(text);
            boolean arrRev = isPalindromeArrayReversal(text);
            
            System.out.printf("\"%s\" | Iterative: %s | Recursive: %s | Array Reversal: %s\n",
                    text,
                    iter ? "Palindrome" : "Not Palindrome",
                    rec ? "Palindrome" : "Not Palindrome",
                    arrRev ? "Palindrome" : "Not Palindrome");
        }
    }
}