/**
 * Created by Sanya on 22.04.2014.
 */

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SyntaxAnalyzer {

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


    public LexicAnalyzer lexer;

    public SyntaxAnalyzer(LexicAnalyzer lexer) {
        this.lexer = lexer;
    }


    public Node parse() throws Exception {
        String sym = lexer.nextToken();
        Node node = new Node(PROG, statement(sym));
        if (sym != lexer.EOF) {
            throw new Exception("Invalid statement syntax");
        }
        return node;

    }

    public Node statement(String sym) throws Exception {
        Node n;
        if (sym == IF) {
            n = new Node(IF1);
            sym = lexer.nextToken();
            n.op1 = paren_expr(sym);
            n.op2 = statement(sym);
            if (sym == ELSE) {
                n.kind = IF2;
                lexer.nextToken();
                n.op3 = statement(sym);
            }
        } else if (sym == WHILE) {
            n = new Node(WHILE);
            sym = lexer.nextToken();
            n.op1 = paren_expr(sym);
            n.op2 = statement(sym);
        } else if (sym == DO) {
            n = new Node(DO);
            sym = lexer.nextToken();
            n.op1 = statement(sym);
            if (sym != WHILE) {
                throw new Exception("\"while\" expected");
            }
            sym = lexer.nextToken();
            n.op2 = paren_expr(sym);
            if (sym != SEMICOLON) {
                throw new Exception("\";\" expected");
            }

        } else if (sym == SEMICOLON) {
            n = new Node(EMPTY);
            sym = lexer.nextToken();
        } else if (sym == LBRA) {
            n = new Node(EMPTY);
            sym = lexer.nextToken();
            while (sym != LBRA) {
                n = new Node(SEQ, n, statement(sym));
            }
            sym = lexer.nextToken();
        } else {
            n = new Node(EXPR, expr(sym));
            if (sym != SEMICOLON) {
                throw new Exception("\";\" expected");
            }
            sym = lexer.nextToken();
        }


        //  sym = lexer.nextToken();
        return n;
    }

    public Node paren_expr(String sym) throws Exception {
        if (sym != LPAR) {
            throw new Exception("\"(\" expected");
        }
        sym = lexer.nextToken();
        Node n = expr(sym);
        if (sym != RPAR) {
            throw new Exception("\")\" expected");
        }
        sym = lexer.nextToken();
        return n;
    }

    public Node expr(String sym) throws Exception {
        if (sym != ID) {
            return test(sym);
        }
        Node n = test(sym);
        if (n.kind == VAR && sym == EQUAL) {
            sym = lexer.nextToken();
            Node node = new Node(SET, n, expr(sym));
        }

        return n;
    }

    public Node test(String sym) throws Exception {
        Node n = summa(sym);
        if (sym == LESS) {
            sym = lexer.nextToken();
            Node node = new Node(LT, n, summa(sym));
        }

        return n;
    }

    public Node summa(String sym) throws Exception {
        Node n = term(sym);
        while (sym == PLUS || sym == MINUS) {
            if (sym == PLUS) n.kind = ADD;
            else n.kind = SUB;
            sym = lexer.nextToken();
            Node node = new Node(LT, n, term(sym));
        }
        return n;
    }

    public Node term(String sym) throws Exception {
        if (sym.contains(ID)) {
            String value = getIdValue(sym);
            Node n = new Node(VAR, value);
            sym = lexer.nextToken();
            return n;
        } else if (sym.contains(NUM)) {
            String value = getNumValue(sym);
            Node n = new Node(CONST, value);
            return n;
        } else return paren_expr(sym);
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
