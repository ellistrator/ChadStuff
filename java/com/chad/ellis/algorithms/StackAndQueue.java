class Node {
    int value;
    Node next;

    public Node(int value) {
        this.value = value;
        this.next = null;
    }
}

class Stack {
    Node top;

    public Stack(int size) {
        this.top = new Node(0);
        Node n = this.top;
        for (int i = 0; i < size - 1; i ++) {
            n.next = new Node(i+1);
            n = n.next;
        }
    }

    public void push(int value) {
        Node n = new Node(value);
        if (this.top != null) {
            n.next = top;
        }
        this.top = n;
    }

    public Node pop() {
        if (this.top == null) {
            return null;
        }
        Node temp = top;
        top = top.next;
        return temp;
    }      
    
    public void print() {
        Node n = top;
        while (n != null) {
            System.out.println(n.value);
            n = n.next;
        }
    }
}

class Queue {
    Node top;

    public Queue(int size) {
        this.top = new Node(0);
        Node n = this.top;
        for (int i = 0; i < size - 1; i ++) {
            n.next = new Node(i+1);
            n = n.next;
        }
    }

    public void add(int value) {
        Node n = new Node(value);
        if (this.top != null) {
            n.next = top;
        }
        this.top = n;
    }

    public Node remove() {
        if (this.top == null) {
            return null;
        }
        Node n = top;
        if (top.next == null) {
            top = null;
            return n;
        }
        while (n.next.next != null) {
            n = n.next;
        }
        Node temp = n.next;
        n.next = null;
        return temp;
    }

    public void print() {
        Node n = top;
        while (n != null) {
            System.out.println(n.value);
            n = n.next;
        }
    }
}

public final class StackAndQueue {

    public static void main(String[] args) {
        Queue q = new Queue(5);
        q.print();
        q.remove();
        q.print();
        q.add(99);
        q.print();
        Stack s = new Stack(10);
        s.print();
        s.pop();
        s.print();
        s.push(88);
        s.print();
    }
    
}
