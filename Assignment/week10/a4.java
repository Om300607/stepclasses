public class a4 {

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    static Node reverse(Node head) {
        Node prev = null;
        Node cur = head;
        while (cur != null) {
            Node nxt = cur.next;
            cur.next = prev;
            prev = cur;
            cur = nxt;
        }
        return prev;
    }

    static boolean isPalindrome(Node head) {
        if (head == null || head.next == null) {
            return true;
        }

        Node slow = head;
        Node fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        Node secondHalf = reverse(slow.next);
        slow.next = null;

        boolean result = true;
        Node p1 = head;
        Node p2 = secondHalf;
        while (p2 != null) {
            if (p1.data != p2.data) {
                result = false;
                break;
            }
            p1 = p1.next;
            p2 = p2.next;
        }

        slow.next = reverse(secondHalf);
        return result;
    }

    static Node build(int[] values) {
        Node head = null;
        Node tail = null;
        for (int v : values) {
            Node n = new Node(v);
            if (head == null) {
                head = n;
                tail = n;
            } else {
                tail.next = n;
                tail = n;
            }
        }
        return head;
    }

    static String toText(Node head) {
        StringBuilder sb = new StringBuilder();
        Node cur = head;
        while (cur != null) {
            sb.append(cur.data);
            if (cur.next != null) {
                sb.append(" -> ");
            }
            cur = cur.next;
        }
        return sb.toString();
    }

    static void test(String label, int[] values) {
        Node head = build(values);
        boolean result = isPalindrome(head);
        System.out.println(label + ": " + result + " (list afterwards: " + toText(head) + ")");
    }

    public static void main(String[] args) {
        test("Sample 1", new int[]{1, 2, 3, 2, 1});
        test("Sample 2", new int[]{1, 2, 3, 2});
        test("Sample 3", new int[]{1, 2, 2, 1});
        test("Single node", new int[]{7});
    }
}