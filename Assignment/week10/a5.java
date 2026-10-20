public class a5 {

    static class Node {
        int coef;
        int exp;
        Node next;

        Node(int coef, int exp) {
            this.coef = coef;
            this.exp = exp;
        }
    }

    static Node addPolynomials(Node p, Node q) {
        Node dummy = new Node(0, 0);
        Node tail = dummy;

        while (p != null && q != null) {
            if (p.exp > q.exp) {
                tail.next = new Node(p.coef, p.exp);
                tail = tail.next;
                p = p.next;
            } else if (q.exp > p.exp) {
                tail.next = new Node(q.coef, q.exp);
                tail = tail.next;
                q = q.next;
            } else {
                int sum = p.coef + q.coef;
                if (sum != 0) {
                    tail.next = new Node(sum, p.exp);
                    tail = tail.next;
                }
                p = p.next;
                q = q.next;
            }
        }

        while (p != null) {
            tail.next = new Node(p.coef, p.exp);
            tail = tail.next;
            p = p.next;
        }

        while (q != null) {
            tail.next = new Node(q.coef, q.exp);
            tail = tail.next;
            q = q.next;
        }

        return dummy.next;
    }

    static Node build(int[][] terms) {
        Node head = null;
        Node tail = null;
        for (int[] t : terms) {
            Node n = new Node(t[0], t[1]);
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
        if (head == null) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        Node cur = head;
        boolean first = true;
        while (cur != null) {
            int abs = Math.abs(cur.coef);
            if (first) {
                if (cur.coef < 0) {
                    sb.append("-");
                }
            } else {
                sb.append(cur.coef < 0 ? " - " : " + ");
            }

            if (cur.exp == 0) {
                sb.append(abs);
            } else {
                if (abs != 1) {
                    sb.append(abs);
                }
                sb.append("x");
                if (cur.exp > 1) {
                    sb.append("^").append(cur.exp);
                }
            }

            first = false;
            cur = cur.next;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Node p1 = build(new int[][]{{5, 3}, {4, 2}, {2, 0}});
        Node q1 = build(new int[][]{{5, 2}, {-3, 1}, {1, 0}});
        System.out.println("Sample 1: " + toText(addPolynomials(p1, q1)));

        Node p2 = build(new int[][]{{3, 2}, {1, 0}});
        Node q2 = build(new int[][]{{-3, 2}, {4, 1}});
        System.out.println("Sample 2: " + toText(addPolynomials(p2, q2)));

        Node p3 = build(new int[][]{{2, 1}, {5, 0}});
        Node q3 = build(new int[][]{{-2, 1}, {-5, 0}});
        System.out.println("All terms cancel: " + toText(addPolynomials(p3, q3)));

        Node p4 = build(new int[][]{});
        Node q4 = build(new int[][]{{-1, 2}, {1, 0}});
        System.out.println("One empty: " + toText(addPolynomials(p4, q4)));
    }
}