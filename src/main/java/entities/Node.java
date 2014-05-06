package entities;


public class Node {

    public  String kind = "";
    public  String value = "";
    public Node op1;
    public Node op2;
    public Node op3;

    public Node(String kind, Node op1, Node op2) {
        this.kind = kind;
        this.op1 = op1;
        this.op2 = op2;
    }

    public Node(String kind) {
        this.kind = kind;
    }

    public Node(String kind, String value) {
        this.kind = kind;
        this.value = value;
    }

    public Node(String kind, Node op1) {
        this.kind = kind;
        this.op1 = op1;
    }
}
