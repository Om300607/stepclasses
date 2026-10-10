import java.util.*;

class Book {
    String isbn;
    String title;
    Book(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }
}

public class a4 {
    static String findBook(List<Book> catalog, String targetIsbn) {
        int low = 0, high = catalog.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = catalog.get(mid).isbn.compareTo(targetIsbn);
            if (cmp == 0) return catalog.get(mid).title;
            if (cmp < 0) low = mid + 1;
            else high = mid - 1;
        }
        return "Not Found";
    }

    public static void main(String[] args) {
        List<Book> catalog = Arrays.asList(
            new Book("0001112223", "Introduction to Algebra"),
            new Book("0001234456", "Beginning Python"),
            new Book("0004445558", "Classic Mythology"),
            new Book("0006567890", "Data and Society"),
            new Book("0009556667", "European History")
        );

        System.out.println(findBook(catalog, "0004445558"));
        System.out.println(findBook(catalog, "0009999887"));
    }
}