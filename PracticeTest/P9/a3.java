import java.util.*;

public class a3 {
    static String findBook(List<String[]> catalog, String targetIsbn) {
        int low = 0, high = catalog.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = catalog.get(mid)[0].compareTo(targetIsbn);
            if (cmp == 0) return catalog.get(mid)[1];
            if (cmp < 0) low = mid + 1;
            else high = mid - 1;
        }
        return "Not Found";
    }

    public static void main(String[] args) {
        List<String[]> catalog = new ArrayList<>();
        catalog.add(new String[]{"0001112223", "Introduction to Algebra"});
        catalog.add(new String[]{"0001234456", "Beginning Python"});
        catalog.add(new String[]{"0004445558", "Classic Mythology"});
        catalog.add(new String[]{"0006567890", "Data and Society"});
        catalog.add(new String[]{"0009556667", "European History"});

        System.out.println(findBook(catalog, "0004445558"));
        System.out.println(findBook(catalog, "0009999887"));
    }
}