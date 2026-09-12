public class a2 {
    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier.equals("public")) {
            return "ALLOWED";
        }
        if (fieldModifier.equals("private")) {
            return accessorContext.equals("SAME_CLASS") ? "ALLOWED" : "DENIED";
        }
        if (fieldModifier.equals("protected")) {
            if (accessorContext.equals("SAME_CLASS") || 
                accessorContext.equals("SAME_PACKAGE") || 
                accessorContext.equals("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")) {
                return "ALLOWED";
            }
            return "DENIED";
        }
        if (fieldModifier.equals("default")) {
            if (accessorContext.equals("SAME_CLASS") || accessorContext.equals("SAME_PACKAGE")) {
                return "ALLOWED";
            }
            return "DENIED";
        }
        return "DENIED";
    }

    public static String describeContext(String accessorContext) {
        String[] parts = accessorContext.split("_");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            sb.append(parts[i].substring(0, 1).toUpperCase())
              .append(parts[i].substring(1).toLowerCase());
            if (i < parts.length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
        System.out.println(classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
        System.out.println(describeContext("SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
    }
}