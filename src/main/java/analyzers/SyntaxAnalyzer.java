package analyzers;

import entities.Constants;
import entities.Node;

public class SyntaxAnalyzer {


    public LexicalAnalyzer lexer;

    public SyntaxAnalyzer(LexicalAnalyzer lexer) {
        this.lexer = lexer;
    }

    public void printTree(Node node){
     // пробежаться по дереву и вывести
        System.out.println("+-" + node.kind  +"(" + node.value + ")");

        if(node.op1!=null ) {
           // System.out.println("  "  + "(" + node.value + ")");
            printTree(node.op1);}
        if(node.op2!=null ) {
            //System.out.println("  " + node.kind + "(" + node.value + ")");
            printTree(node.op2);}
        }


    public Node parse() throws Exception {
        lexer.nextToken();
        if (!lexer.sym.equals(Constants.MAIN))
            throw new Exception("Wrong program start with token = " + lexer.sym + ". Should start with 'main'.");
        //System.out.println("sym in parse = " + lexer.sym);
        lexer.nextToken();
        Node node = new Node(Constants.PROG, statement());

        if (!lexer.sym.equals(Constants.EOF)) {
            throw new Exception("Invalid statement syntax");
        }
        printTree(node);
        return node;

    }

    public Node statement() throws Exception {
        Node n;
        //System.out.println("sym statement start = " + lexer.sym);
        switch (lexer.sym) {
            case Constants.IF:
                n = new Node(Constants.IF1);
                lexer.nextToken();
                n.op1 = paren_expr();
                n.op2 = statement();
                if (lexer.sym.equals(Constants.ELSE)) {
                    n.kind = Constants.IF2;
                    lexer.nextToken();
                    n.op3 = statement();
                }
                break;
            case Constants.WHILE:
                n = new Node(Constants.WHILE);
                lexer.nextToken();
                n.op1 = paren_expr();
                n.op2 = statement();
                break;
            case Constants.DO:
                n = new Node(Constants.DO);
                lexer.nextToken();
                n.op1 = statement();
                if (!lexer.sym.equals(Constants.WHILE)) {
                    throw new Exception("\"while\" expected");
                }
                lexer.nextToken();
                n.op2 = paren_expr();
                if (!lexer.sym.equals(Constants.SEMICOLON)) {
                    throw new Exception("\";\" expected");
                }
                break;
            case Constants.SEMICOLON:
                n = new Node(Constants.EMPTY);
                lexer.nextToken();
                break;
            case Constants.LBRA:
                //System.out.println("sym inside LBRA = " + lexer.sym);
                n = new Node(Constants.EMPTY);
                lexer.nextToken();
                while (!lexer.sym.equals(Constants.RBRA)) {
                    //System.out.println("node = "+n);
                    n = new Node(Constants.SEQ, n, statement());
                   // System.out.println("Last token = " + lexer.sym);
                }
                lexer.nextToken();
                break;
            default:
                //System.out.println("sym in default = "+lexer.sym);
                n = new Node(Constants.EXPR, expr());
                //System.out.println("in default = " + lexer.sym);
               // lexer.nextToken();
                //System.out.println("in default2 = "+lexer.sym);
                if (!lexer.sym.equals(Constants.SEMICOLON)) {
                    throw new Exception("\";\" expected");
                }
                lexer.nextToken();
                break;
        }
        return n;
    }

    public Node paren_expr() throws Exception {
        if (!lexer.sym.equals(Constants.LPAR)) {
            throw new Exception("\"(\" expected");
        }
        lexer.nextToken();
        Node n = expr();
        if (!lexer.sym.equals(Constants.RPAR)) {
            throw new Exception("\")\" expected");
        }
        lexer.sym = lexer.nextToken();
        return n;
    }

    public Node expr() throws Exception {
        if (!lexer.sym.contains(Constants.ID)) {
            //System.out.println("in expr = "+lexer.sym);
            return test();
        }
        Node n = test();
        if (n.kind.equals(Constants.VAR) && lexer.sym.equals(Constants.EQUAL)) {
            lexer.nextToken();
            //System.out.println("kind in if expr() = " + n.kind + " lexer sym = " + lexer.sym);
            n = new Node(Constants.SET, n, expr());
        }
        return n;
    }

    public Node test() throws Exception {
        Node n = sum();
        if (lexer.sym.equals(Constants.LESS)) {
            lexer.nextToken();
            n = new Node(Constants.LT, n, sum());
        }
        return n;
    }

    public Node sum() throws Exception {
        Node n = term();
        //System.out.println("lexer.sym in sum after term = "+lexer.sym);
        while (lexer.sym.equals(Constants.PLUS) || lexer.sym.equals(Constants.MINUS)) {
            if (lexer.sym.equals(Constants.PLUS)) n.kind = Constants.ADD;
            else n.kind = Constants.SUB;
            lexer.nextToken();
            n = new Node(Constants.LT, n, term());
        }
        return n;
    }

    public Node term() throws Exception {
        //System.out.println("lexer.sym in term = "+lexer.sym);
        if (lexer.sym.contains(Constants.ID)) {
            String value = getIdValue(lexer.sym);
            //System.out.println("value = "+value);
            Node n = new Node(Constants.VAR, value);
            lexer.nextToken();
            return n;
        } else if (lexer.sym.contains(Constants.NUM)) {
            String value = getNumValue(lexer.sym);
            lexer.nextToken();
            return new Node(Constants.CONST, value);
        } else return paren_expr();
    }

    private String getIdValue(String ident) {
        String[] words = ident.split(":");
        return words[1].trim();
    }

    private String getNumValue(String ident) {
        String[] words = ident.split(":");
        return words[1].trim();
    }
}
