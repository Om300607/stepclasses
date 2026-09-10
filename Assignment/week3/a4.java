class BrokenLibraryMember {
    static String name;
    static String memberId;
    static int booksIssued;

    public BrokenLibraryMember(String name, String memberId, int booksIssued) {
        BrokenLibraryMember.name = name;
        BrokenLibraryMember.memberId = memberId;
        BrokenLibraryMember.booksIssued = booksIssued;
    }

    public void printName() {
        System.out.println(name);
    }
}

class LibraryMember {
    static String libraryName = "City Central Library";
    static int memberCount = 0;

    String name;
    String memberId;
    int booksIssued;

    public LibraryMember(String name, int booksIssued) {
        memberCount++;
        this.name = name;
        this.memberId = "LM-" + (1000 + memberCount);
        this.booksIssued = booksIssued;
    }

    public void printMemberCard() {
        System.out.println(this.name + " | " + this.memberId);
    }

    public static void printTotalMembers() {
        System.out.println("Total members: " + memberCount);
    }
}

public class a4 {
    public static void main(String[] args) {
        BrokenLibraryMember b1 = new BrokenLibraryMember("Aditi", "LM-1001", 2);
        BrokenLibraryMember b2 = new BrokenLibraryMember("Rohan", "LM-1002", 1);

        b1.printName();
        b2.printName();
        System.out.println("(Aditi's data was overwritten — both members now show \"Rohan\")");

        LibraryMember m1 = new LibraryMember("Aditi", 2);
        LibraryMember m2 = new LibraryMember("Rohan", 1);

        m1.printMemberCard();
        m2.printMemberCard();
        LibraryMember.printTotalMembers();
    }
}