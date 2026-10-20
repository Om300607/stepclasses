public class a3 {

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    static Node mergeSorted(Node a, Node b) {
        Node dummy = new Node(0);
        Node tail = dummy;

        while (a != null && b != null) {
            if (a.data <= b.data) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }

        tail.next = (a != null) ? a : b;
        return dummy.next;
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

    static void print(Node head) {
        StringBuilder sb = new StringBuilder();
        Node cur = head;
        while (cur != null) {
            sb.append(cur.data);
            if (cur.next != null) {
                sb.append(" -> ");
            }
            cur = cur.next;
        }
        System.out.println(sb.length() == 0 ? "(empty)" : sb.toString());
    }

    public static void main(String[] args) {
        Node a1 = build(new int[]{3, 8, 15});
        Node b1 = build(new int[]{5, 8, 12, 20});
        System.out.print("Sample 1: ");
        print(mergeSorted(a1, b1));

        Node a2 = build(new int[]{});
        Node b2 = build(new int[]{4});
        System.out.print("Sample 2: ");
        print(mergeSorted(a2, b2));

        Node a3 = build(new int[]{});
        Node b3 = build(new int[]{});
        System.out.print("Both empty: ");
        print(mergeSorted(a3, b3));
    }
}