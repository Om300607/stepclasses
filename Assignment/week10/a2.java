public class a2 {

    static class Node {
        String data;
        Node prev;
        Node next;

        Node(String data) {
            this.data = data;
        }
    }

    static class BrowserHistory {
        private Node current;

        BrowserHistory(String homepage) {
            current = new Node(homepage);
        }

        void visit(String url) {
            Node node = new Node(url);
            node.prev = current;
            current.next = node;
            current = node;
        }

        String back(int steps) {
            while (steps > 0 && current.prev != null) {
                current = current.prev;
                steps--;
            }
            return current.data;
        }

        String forward(int steps) {
            while (steps > 0 && current.next != null) {
                current = current.next;
                steps--;
            }
            return current.data;
        }
    }

    public static void main(String[] args) {
        BrowserHistory browser = new BrowserHistory("srm.edu");
        StringBuilder out = new StringBuilder();

        browser.visit("a.com");
        browser.visit("b.com");
        browser.visit("c.com");

        out.append(browser.back(1)).append(", ");
        out.append(browser.back(1)).append(", ");
        out.append(browser.forward(1)).append(", ");
        browser.visit("d.com");
        out.append(browser.forward(2)).append(", ");
        out.append(browser.back(2)).append(", ");
        out.append(browser.back(7));

        System.out.println(out);
    }
}