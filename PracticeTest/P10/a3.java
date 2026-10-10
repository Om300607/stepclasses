import java.util.*;

class MarksGrid {
    String[] subjects;
    List<String> students = new ArrayList<>();
    List<Integer[]> marks = new ArrayList<>();

    MarksGrid(String... subjects) {
        this.subjects = subjects;
    }

    void addStudent(String name, Integer... m) {
        Integer[] row = new Integer[subjects.length];
        for (int i = 0; i < m.length && i < row.length; i++) {
            row[i] = m[i];
        }
        students.add(name);
        marks.add(row);
    }

    int total(int s) {
        int sum = 0;
        for (Integer v : marks.get(s)) {
            if (v != null) sum += v;
        }
        return sum;
    }

    void printTotals() {
        StringBuilder sb = new StringBuilder("Totals ");
        for (int i = 0; i < students.size(); i++) {
            sb.append(students.get(i)).append(" ").append(total(i));
            if (i < students.size() - 1) sb.append(", ");
        }
        System.out.println(sb);
    }

    void printToppers() {
        StringBuilder sb = new StringBuilder("Toppers ");
        for (int j = 0; j < subjects.length; j++) {
            int best = -1;
            String top = "-";
            for (int i = 0; i < students.size(); i++) {
                Integer v = marks.get(i)[j];
                if (v != null && v > best) {
                    best = v;
                    top = students.get(i);
                }
            }
            sb.append(subjects[j]).append(" ").append(top).append(" ").append(best);
            if (j < subjects.length - 1) sb.append(", ");
        }
        System.out.println(sb);
    }
}

public class a3 {
    public static void main(String[] args) {
        MarksGrid grid = new MarksGrid("Math", "Sci", "Eng");
        grid.addStudent("Anu", 80, 90, 70);
        grid.addStudent("Ravi", 50, 85);
        grid.addStudent("Meera", 95, 75, 80);
        grid.printTotals();
        grid.printToppers();
    }
}