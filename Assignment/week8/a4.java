import java.util.*;

public class a4 {
    interface Action {
        void run();
    }

    static void attempt(String prefix, Action action) {
        try {
            action.run();
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(prefix + e.getMessage() + ".");
        }
    }

    interface CreditPolicy {
        String getName();
        int getLimit();
    }

    static class RegularPolicy implements CreditPolicy {
        @Override
        public String getName() {
            return "Regular";
        }

        @Override
        public int getLimit() {
            return 24;
        }
    }

    static class HonorsPolicy implements CreditPolicy {
        @Override
        public String getName() {
            return "Honors";
        }

        @Override
        public int getLimit() {
            return 28;
        }
    }

    static class ExchangePolicy implements CreditPolicy {
        @Override
        public String getName() {
            return "Exchange";
        }

        @Override
        public int getLimit() {
            return 20;
        }
    }

    static class Student {
        private final String name;
        private final CreditPolicy policy;
        private int credits;

        Student(String name, CreditPolicy policy, int credits) {
            this.name = name;
            this.policy = policy;
            this.credits = credits;
        }

        String getName() {
            return name;
        }

        CreditPolicy getPolicy() {
            return policy;
        }

        int getCredits() {
            return credits;
        }

        boolean canTake(int extraCredits) {
            return credits + extraCredits <= policy.getLimit();
        }

        void addCredits(int extraCredits) {
            credits += extraCredits;
        }

        void removeCredits(int removedCredits) {
            credits -= removedCredits;
        }
    }

    static class Enrollment {
        private final Student student;
        private final Elective elective;

        Enrollment(Student student, Elective elective) {
            this.student = student;
            this.elective = elective;
        }

        Student getStudent() {
            return student;
        }

        Elective getElective() {
            return elective;
        }
    }

    static class Elective {
        private final String name;
        private final int credits;
        private final int capacity;
        private final List<Enrollment> enrollments = new ArrayList<>();
        private final Queue<Student> waitlist = new LinkedList<>();

        Elective(String name, int credits, int capacity) {
            if (credits <= 0 || capacity <= 0) {
                throw new IllegalArgumentException("Credits and capacity must be positive");
            }
            this.name = name;
            this.credits = credits;
            this.capacity = capacity;
        }

        String getName() {
            return name;
        }

        int getCredits() {
            return credits;
        }

        int getCapacity() {
            return capacity;
        }

        private Enrollment findEnrollment(Student student) {
            for (Enrollment enrollment : enrollments) {
                if (enrollment.getStudent() == student) {
                    return enrollment;
                }
            }
            return null;
        }

        private String creditStatus(Student student) {
            return "credits: " + student.getCredits() + "/" + student.getPolicy().getLimit();
        }

        private void admit(Student student) {
            enrollments.add(new Enrollment(student, this));
            student.addCredits(credits);
        }

        synchronized void enroll(Student student) {
            if (findEnrollment(student) != null || waitlist.contains(student)) {
                throw new IllegalStateException(student.getName() + " is already enrolled in or waitlisted for " + name);
            }
            if (!student.canTake(credits)) {
                throw new IllegalArgumentException(student.getName() + " would exceed the " + student.getPolicy().getName()
                        + " credit limit (" + (student.getCredits() + credits) + "/" + student.getPolicy().getLimit() + ")");
            }
            if (enrollments.size() < capacity) {
                admit(student);
                System.out.println(student.getName() + " enrolled in " + name + " (" + creditStatus(student) + ").");
            } else {
                System.out.println(name + " is full.");
                waitlist.add(student);
                System.out.println(student.getName() + " added to waitlist (position " + waitlist.size() + ").");
            }
        }

        synchronized void drop(Student student) {
            Enrollment enrollment = findEnrollment(student);
            if (enrollment == null) {
                throw new IllegalStateException(student.getName() + " is not enrolled in " + name);
            }
            enrollments.remove(enrollment);
            student.removeCredits(credits);
            System.out.println(student.getName() + " dropped " + name + " (" + creditStatus(student) + ").");
            promoteFromWaitlist();
        }

        private void promoteFromWaitlist() {
            Iterator<Student> iterator = waitlist.iterator();
            while (enrollments.size() < capacity && iterator.hasNext()) {
                Student next = iterator.next();
                if (next.canTake(credits)) {
                    iterator.remove();
                    admit(next);
                    System.out.println(next.getName() + " promoted from waitlist and enrolled in " + name + " (" + creditStatus(next) + ").");
                }
            }
        }
    }

    static class EnrollmentService {
        private final Map<String, Elective> electives = new LinkedHashMap<>();

        void createElective(String name, int credits, int capacity) {
            if (electives.containsKey(name)) {
                throw new IllegalArgumentException("Elective " + name + " already exists");
            }
            electives.put(name, new Elective(name, credits, capacity));
        }

        private Elective find(String electiveName) {
            Elective elective = electives.get(electiveName);
            if (elective == null) {
                throw new IllegalArgumentException("Elective " + electiveName + " was not found");
            }
            return elective;
        }

        void enroll(Student student, String electiveName) {
            find(electiveName).enroll(student);
        }

        void drop(Student student, String electiveName) {
            find(electiveName).drop(student);
        }
    }

    public static void main(String[] args) {
        EnrollmentService service = new EnrollmentService();
        service.createElective("Cloud Computing", 4, 2);

        Student asha = new Student("Asha", new RegularPolicy(), 20);
        Student ravi = new Student("Ravi", new HonorsPolicy(), 22);
        Student neha = new Student("Neha", new ExchangePolicy(), 12);
        Student kiran = new Student("Kiran", new RegularPolicy(), 22);

        attempt("Enrollment failed: ", () -> service.enroll(asha, "Cloud Computing"));
        attempt("Enrollment failed: ", () -> service.enroll(ravi, "Cloud Computing"));
        attempt("Enrollment failed: ", () -> service.enroll(neha, "Cloud Computing"));
        attempt("Enrollment failed: ", () -> service.enroll(kiran, "Cloud Computing"));
        attempt("Drop failed: ", () -> service.drop(asha, "Cloud Computing"));
    }
}