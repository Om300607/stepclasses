public class a2 {

    public static String classifyAccess(String fieldModifier, String accessorContext) {
        switch (fieldModifier) {
            case "public":
                return "ALLOWED";
            case "protected":
                if ("SAME_CLASS".equals(accessorContext) || 
                    "SAME_PACKAGE".equals(accessorContext) || 
                    "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE".equals(accessorContext)) {
                    return "ALLOWED";
                }
                return "DENIED";
            case "default":
                if ("SAME_CLASS".equals(accessorContext) || "SAME_PACKAGE".equals(accessorContext)) {
                    return "ALLOWED";
                }
                return "DENIED";
            case "private":
                if ("SAME_CLASS".equals(accessorContext)) {
                    return "ALLOWED";
                }
                return "DENIED";
            default:
                return "DENIED";
        }
    }

    public static String describeContext(String accessorContext) {
        String[] parts = accessorContext.toLowerCase().split("_");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            if (!parts[i].isEmpty()) {
                sb.append(Character.toUpperCase(parts[i].charAt(0)))
                  .append(parts[i].substring(1));
                if (i < parts.length - 1) {
                    sb.append(" ");
                }
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
        System.out.println(classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
        System.out.println(describeContext("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
    }
}