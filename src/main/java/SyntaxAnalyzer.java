
public class SyntaxAnalyzer {

    public final String MAIN = "main";
    public final String VAR = "var";
    public final String CONST = "const";
    public final String ADD = "add";
    public final String SUB = "sub";
    public final String LT = "lt";
    public final String SET = "set";
    public final String IF = "if";
    public final String ELSE = "else";
    public final String IF1 = "if";
    public final String IF2 = "if";
    public final String WHILE = "while";
    public final String SEMICOLON = ";";
    public final String LBRA = "{";
    public final String RBRA = "}";
    public final String LPAR = "(";
    public final String RPAR = ")";
    public final String DO = "do";
    public final String EMPTY = "empty";
    public final String SEQ = "seq";
    public final String EXPR = "expr";
    public final String PROG = "prog";
    public final String ID = "id";
    public final String NUM = "num";
    public final String EQUAL = "equal";
    public final String PLUS = "+";
    public final String MINUS = "-";
    public final String LESS = "<";


    public LexicalAnalyzer lexer;

    public SyntaxAnalyzer(LexicalAnalyzer lexer) {
        this.lexer = lexer;
    }


    public Node parse() throws Exception {
        String sym = lexer.nextToken();
        Node node = new Node(PROG, statement());
        if (!sym.equals(lexer.EOF)) {
            throw new Exception("Invalid statement syntax");
        }
        return node;

    }

    public Node statement() throws Exception {
        Node n;
        switch (lexer.sym) {
            case MAIN:
                n = new Node(MAIN);
                lexer.nextToken();
                n.op1 = paren_expr();
                n.op2 = statement();
                break;
            case IF:
                n = new Node(IF1);
                lexer.nextToken();
                n.op1 = paren_expr();
                n.op2 = statement();
                if (lexer.sym.equals(ELSE)) {
                    n.kind = IF2;
                    lexer.nextToken();
                    n.op3 = statement();
                }
                break;
            case WHILE:
                n = new Node(WHILE);
                lexer.nextToken();
                n.op1 = paren_expr();
                n.op2 = statement();
                break;
            case DO:
                n = new Node(DO);
                lexer.nextToken();
                n.op1 = statement();
                if (!lexer.sym.equals(WHILE)) {
                    throw new Exception("\"while\" expected");
                }
                lexer.nextToken();
                n.op2 = paren_expr();
                if (!lexer.sym.equals(SEMICOLON)) {
                    throw new Exception("\";\" expected");
                }

                break;
            case SEMICOLON:
                n = new Node(EMPTY);
                lexer.nextToken();
                break;
            case LBRA:
                n = new Node(EMPTY);
                lexer.nextToken();
                while (!lexer.sym.equals(RBRA)) {
                    n = new Node(SEQ, n, statement());
                }
                lexer.nextToken();
                break;
            default:
                n = new Node(EXPR, expr());
                if (!lexer.sym.equals(SEMICOLON)) {
                    throw new Exception("\";\" expected");
                }
                lexer.nextToken();
                break;
        }
        return n;
    }

    public Node paren_expr() throws Exception {
        if (!lexer.sym.equals(LPAR)) {
            throw new Exception("\"(\" expected");
        }
        lexer.nextToken();
        Node n = expr();
        if (!lexer.sym.equals(RPAR)) {
            throw new Exception("\")\" expected");
        }
        lexer.sym = lexer.nextToken();
        return n;
    }

    public Node expr() throws Exception {
        if (!lexer.sym.equals(ID)) {
            return test();
        }
        Node n = test();
        if (n.kind.equals(VAR) && lexer.sym.equals(EQUAL)) {
            lexer.nextToken();
            n = new Node(SET, n, expr());
        }
        return n;
    }

    public Node test() throws Exception {
        Node n = sum();
        if (lexer.sym.equals(LESS)) {
            lexer.nextToken();
            n = new Node(LT, n, sum());
        }

        return n;
    }

    public Node sum() throws Exception {
        Node n = term();
        while (lexer.sym.equals(PLUS) || lexer.sym.equals(MINUS)) {
            if (lexer.sym.equals(PLUS)) n.kind = ADD;
            else n.kind = SUB;
            lexer.nextToken();
            n = new Node(LT, n, term());
        }
        return n;
    }

    public Node term() throws Exception {
        if (lexer.sym.contains(ID)) {
            String value = getIdValue(lexer.sym);
            Node n = new Node(VAR, value);
            lexer.nextToken();
            return n;
        } else if (lexer.sym.contains(NUM)) {
            String value = getNumValue(lexer.sym);
            return new Node(CONST, value);
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
