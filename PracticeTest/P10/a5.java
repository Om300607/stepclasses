abstract class Resource {
    String id;
    Resource(String id) {
        this.id = id;
    }
}

interface Reservable {
    void reserve(String member);
}

interface Downloadable {
    void download();
}

class Book extends Resource implements Reservable {
    String reservedBy;
    Book(String id) {
        super(id);
    }
    public void reserve(String member) {
        reservedBy = member;
    }
}

class EBook extends Resource implements Downloadable {
    int downloads;
    EBook(String id) {
        super(id);
    }
    public void download() {
        downloads++;
    }
}

class Library {
    Resource[] items = new Resource[2];
    int size;

    int search(String id) {
        int lo = 0, hi = size - 1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            int cmp = items[mid].id.compareTo(id);
            if (cmp == 0) return mid;
            if (cmp < 0) lo = mid + 1;
            else hi = mid - 1;
        }
        return -(lo + 1);
    }

    String add(Resource r) {
        int pos = search(r.id);
        if (pos >= 0) return "duplicate rejected";
        pos = -(pos + 1);
        if (size == items.length) {
            items = java.util.Arrays.copyOf(items, size * 2);
        }
        for (int i = size; i > pos; i--) {
            items[i] = items[i - 1];
        }
        items[pos] = r;
        size++;
        return r.id + " added";
    }

    String reserve(String id, String member) {
        int pos = search(id);
        if (pos < 0) return id + " not found";
        if (items[pos] instanceof Reservable) {
            ((Reservable) items[pos]).reserve(member);
            return id + " reserved for " + member;
        }
        return id + " rejected: reserve unsupported";
    }

    String download(String id) {
        int pos = search(id);
        if (pos < 0) return id + " not found";
        if (items[pos] instanceof Downloadable) {
            ((Downloadable) items[pos]).download();
            return id + " downloaded";
        }
        return id + " rejected: download unsupported";
    }

    String find(String id) {
        int pos = search(id);
        if (pos < 0) return id + " not found";
        return id + " found at index " + pos;
    }
}

public class a5 {
    public static void main(String[] args) {
        Library lib = new Library();
        String[] commands = {
            "ADD Book B1",
            "ADD Book B1",
            "ADD Ebook E1",
            "RESERVE B1 by M1",
            "DOWNLOAD B1",
            "DOWNLOAD E1",
            "FIND E1"
        };
        for (String c : commands) {
            String[] p = c.split(" ");
            switch (p[0]) {
                case "ADD":
                    Resource r = p[1].equalsIgnoreCase("Book") ? new Book(p[2]) : new EBook(p[2]);
                    System.out.println(lib.add(r));
                    break;
                case "RESERVE":
                    System.out.println(lib.reserve(p[1], p[3]));
                    break;
                case "DOWNLOAD":
                    System.out.println(lib.download(p[1]));
                    break;
                case "FIND":
                    System.out.println(lib.find(p[1]));
                    break;
                default:
                    System.out.println("unknown command");
            }
        }
    }
}