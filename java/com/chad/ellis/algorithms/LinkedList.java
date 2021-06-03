
class Node {
    int value;
    Node next;

    public Node(int value) {
        this.value = value;
        this.next = null;
    }

    public Node(int value, Node next) {
        this.value = value;
        this.next = next;
    }
}

class LList {
    Node head;

    public LList(int size) {
        head = new Node(0);
        Node n = head;
        for (int i = 0; i < size-1; i++) {
            n.next = new Node(i+1);
            n = n.next;
        }
    }

    public void print() {
        Node n = head;
        while (n != null) {
            System.out.println(n.value);
            n = n.next;
        }
    }

    public void remove(int value) {
        if (head == null) {
            return;
        }
        if (head.value == value) {
            head = head.next;
            return;
        }

        Node n = head;
        while (n.next != null && n.next.value != value) {
            n = n.next;
        }
        if (n.next != null) {
            n.next = n.next.next;
        }
    }
}

public final class LinkedList {


    public static void main(String[] strs) {
        LList llist = new LList(5);
        System.out.println("Full");
        llist.print();
        System.out.println("Rem 3");
        llist.remove(3);
        llist.print();
        System.out.println("Rem 4");
        llist.remove(4);
        llist.print();
        System.out.println("Rem 0");
        llist.remove(0);
        llist.print();
        System.out.println("Rem 1");
        llist.remove(1);
        llist.print();
        System.out.println("Rem 3");
        llist.remove(3);
        llist.print();
        System.out.println("Rem 2");
        llist.remove(2);
        llist.print();
    }
    
}
