/**
 * Created by Sanya on 22.04.2014.
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class Node {

    public  String kind = "";
    public  String value = "";
    public Node op1 = "";
    public Node op2 = "";
    public Node op3 = "";

    public Node(String kind, String value, String op1, String op2, String op3) {
        this.kind = kind;
        this.value = value;
        this.op1 = op1;
        this.op2 = op2;
        this.op3 = op3;
    }

}
