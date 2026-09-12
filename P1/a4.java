public class a4 {
    public static char findFirstNonRepeatingChar(String text) {
        int[] freq = new int[256];
        for (int i = 0; i < text.length(); i++) {
            freq[text.charAt(i)]++;
        }
        for (int i = 0; i < text.length(); i++) {
            if (freq[text.charAt(i)] == 1) {
                return text.charAt(i);
            }
        }
        return '\0';
    }

    public static void main(String[] args) {
        String[] testCases = {"swiss", "aabbcc"};
        
        for (String text : testCases) {
            char result = findFirstNonRepeatingChar(text);
            if (result != '\0') {
                System.out.printf("Input: \"%s\" | Output: First Non-Repeating Character: '%c'\n", text, result);
            } else {
                System.out.printf("Input: \"%s\" | Output: No Non-Repeating Character Found\n", text);
            }
        }
    }
}