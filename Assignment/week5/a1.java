class LibraryMember {
    private String membershipId;
    String branchCode;
    protected double finesOwed;
    public String displayName;

    public LibraryMember(String membershipId, String branchCode, double finesOwed, String displayName) {
        if (membershipId == null || membershipId.trim().length() < 4) {
            throw new IllegalArgumentException("construction rejected");
        }
        this.membershipId = membershipId.trim();
        this.branchCode = branchCode;
        this.finesOwed = finesOwed;
        this.displayName = displayName;
    }

    public String getMembershipId() {
        return membershipId;
    }
}

public class a1 {

    public static String classifyAccess(String fieldModifier, String accessorContext) {
        switch (fieldModifier) {
            case "public":
                return "ALLOWED";
            case "protected":
                if ("SAME_CLASS".equals(accessorContext) || "SAME_PACKAGE".equals(accessorContext)) {
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

    public static String summarizeByModifier(String[][] attempts) {
        String[] modifiers = {"private", "default", "protected", "public"};
        int[] allowed = new int[4];
        int[] denied = new int[4];

        for (String[] attempt : attempts) {
            String mod = attempt[0];
            String ctx = attempt[1];
            String result = classifyAccess(mod, ctx);

            for (int i = 0; i < modifiers.length; i++) {
                if (modifiers[i].equals(mod)) {
                    if ("ALLOWED".equals(result)) {
                        allowed[i]++;
                    } else {
                        denied[i]++;
                    }
                    break;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < modifiers.length; i++) {
            sb.append(modifiers[i]).append(": ").append(allowed[i]).append(" allowed / ")
              .append(denied[i]).append(" denied");
            if (i < modifiers.length - 1) {
                sb.append(" | ");
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(classifyAccess("private", "SAME_CLASS"));
        System.out.println(classifyAccess("protected", "DIFFERENT_PACKAGE"));

        String[][] attempts = {
            {"private", "SAME_CLASS"},
            {"private", "SAME_PACKAGE"},
            {"default", "SAME_PACKAGE"},
            {"default", "DIFFERENT_PACKAGE"},
            {"protected", "SAME_PACKAGE"},
            {"protected", "SAME_CLASS"},
            {"public", "DIFFERENT_PACKAGE"}
        };
        System.out.println(summarizeByModifier(attempts));

        try {
            new LibraryMember("LB9", "BR1", 0, "Priya Nair");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}