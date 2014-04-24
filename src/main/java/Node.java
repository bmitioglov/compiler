/**
 * Created by Sanya on 22.04.2014.
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class Node {

    public  String kind = "";
    public  String value = "";
    public Node op1;
    public Node op2;
    public Node op3;

    public Node(String kind, String value, Node op1, Node op2, Node op3) {
        this.kind = kind;
        this.value = value;
        this.op1 = op1;
        this.op2 = op2;
        this.op3 = op3;
    }

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
