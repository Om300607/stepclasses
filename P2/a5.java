public class a5 {
    public static String normalizeReference(String raw) {
        String trimmed = raw.trim();
        if (trimmed.length() < 3) {
            return trimmed.toUpperCase();
        }
        return trimmed.substring(0, 3).toUpperCase() + trimmed.substring(3);
    }

    public static String validateAndFormat(String reference) {
        if (reference.length() != 14) {
            return "Invalid: wrong length";
        }
        
        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(reference.charAt(i))) {
                return "Invalid: bank code must be 3 letters";
            }
        }
        
        for (int i = 3; i < 14; i++) {
            if (!Character.isDigit(reference.charAt(i))) {
                return "Invalid: non-digit body";
            }
        }
        
        String bankCode = reference.substring(0, 3);
        String day = reference.substring(3, 5);
        String month = reference.substring(5, 7);
        String year = reference.substring(7, 9);
        String seq = reference.substring(9, 14);
        
        StringBuilder formatted = new StringBuilder();
        formatted.append("[").append(bankCode).append("] ")
                 .append("DATE: ").append(day).append("/").append(month).append("/").append(year)
                 .append(" | SEQ: ").append(seq);
                 
        return formatted.toString();
    }

    public static void main(String[] args) {
        String raw1 = "   hdf03022600042  ";
        String normalized1 = normalizeReference(raw1);
        System.out.println(validateAndFormat(normalized1));
        
        String raw2 = "12F03022600042";
        String normalized2 = normalizeReference(raw2);
        System.out.println(validateAndFormat(normalized2));
    }
}