class LibraryMemberBean {
    private String membershipId;
    private String name;
    private boolean premiumMember;
    private int securityAnswerHash;
    private boolean hasSecurityAnswer;

    public LibraryMemberBean() {
        this(null, null);
    }

    public LibraryMemberBean(String name) {
        this(null, name);
    }

    public LibraryMemberBean(String membershipId, String name) {
        this.membershipId = membershipId;
        this.name = name;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        if (this.membershipId == null) {
            this.membershipId = membershipId;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPremiumMember() {
        return premiumMember;
    }

    public void setPremiumMember(boolean premiumMember) {
        this.premiumMember = premiumMember;
    }

    public void setSecurityAnswer(String answer) {
        if (answer != null) {
            this.securityAnswerHash = answer.hashCode();
            this.hasSecurityAnswer = true;
        }
    }
}

public class a4 {
    public static void main(String[] args) {
        System.out.println(new LibraryMemberBean("Priya Nair").getMembershipId());
        System.out.println(new LibraryMemberBean("LIB-8841", "Priya Nair").getMembershipId());

        LibraryMemberBean m = new LibraryMemberBean();
        m.setMembershipId("LIB-8841");
        m.setMembershipId("FAKE-0000");
        System.out.println(m.getMembershipId());
    }
}