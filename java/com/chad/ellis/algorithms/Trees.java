class Node {
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public Node(int value, Node left, Node right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }
}

class Tree {
    Node root;

    public void create() {
        this.root = new Node(5, 
            new Node(3, new Node(2), new Node(4)),
            new Node(8, new Node(7), new Node(9)));
    }

    private boolean find(int value, Node n) {
        if (n == null) return false;
        if (n.value == value) return true;
        if (find(value, n.left)) return true;
        if (find(value, n.right)) return true;
        return false;
    }

    public boolean exists(int value) {
        return find(value, root);
    }
}

public final class Trees {

    public static void main(String[] strs) {
        Tree t = new Tree();
        t.create();
        System.out.println(t.exists(3));
        System.out.println(t.exists(99));
    }
    
}
