public class a4 {

    public static String normalizeCode(String raw) {
        if (raw == null) {
            return "";
        }
        String trimmed = raw.trim();
        if (trimmed.length() < 3) {
            return trimmed.toUpperCase();
        }
        return trimmed.substring(0, 3).toUpperCase() + trimmed.substring(3);
    }

    public static String validateAndFormat(String code) {
        if (code == null || code.length() != 13) {
            return "Invalid: wrong length";
        }

        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(code.charAt(i))) {
                return "Invalid: publisher code must be 3 letters";
            }
        }

        for (int i = 3; i < 13; i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return "Invalid: non-digit body";
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[").append(code.substring(0, 3)).append("] ");
        sb.append("YEAR: ").append(code.substring(3, 7)).append(" | ");
        sb.append("CATALOG: ").append(code.substring(7));

        return sb.toString();
    }

    public static void main(String[] args) {
        String code1 = normalizeCode(" pen2026004251 ");
        System.out.println(validateAndFormat(code1));

        String code2 = normalizeCode("12N2026004251");
        System.out.println(validateAndFormat(code2));
    }
}