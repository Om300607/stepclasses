public class a1 {

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    static Node removeKthFromEnd(Node head, int k) {
        Node dummy = new Node(0);
        dummy.next = head;
        Node lead = dummy;
        Node trail = dummy;

        for (int i = 0; i < k + 1; i++) {
            lead = lead.next;
        }

        while (lead != null) {
            lead = lead.next;
            trail = trail.next;
        }

        trail.next = trail.next.next;
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
        Node orders1 = build(new int[]{10, 20, 30, 40, 50});
        System.out.print("Sample 1 (k = 2): ");
        print(removeKthFromEnd(orders1, 2));

        Node orders2 = build(new int[]{10, 20, 30, 40, 50});
        System.out.print("Sample 2 (k = 5): ");
        print(removeKthFromEnd(orders2, 5));

        Node orders3 = build(new int[]{10});
        System.out.print("Single node (k = 1): ");
        print(removeKthFromEnd(orders3, 1));
    }
}