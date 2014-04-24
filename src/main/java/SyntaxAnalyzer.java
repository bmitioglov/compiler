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

    public void term() {
        if () ;
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
        if (sym == IF) {
            Node n = new Node(IF1);
            sym = lexer.nextToken();
            n.op1 = paren_expr();
            n.op2 = statement(sym);
            if (sym == ELSE) {
                n.kind = IF2;
                lexer.nextToken();
                n.op3 = statement(sym);
            }
        } else if (sym == WHILE) {
            Node n = new Node(WHILE);
            sym = lexer.nextToken();
            n.op1 = paren_expr();
            n.op2 = statement(sym);
        }
        else if (sym == DO) {
            Node n = new Node(DO);
            sym = lexer.nextToken();
            n.op1 = statement(sym);
            if (sym != WHILE) {
                throw new Exception("\"while\" expected");
            }
            sym = lexer.nextToken();
            n.op2 = paren_expr();
            if (sym != SEMICOLON){
                throw new Exception("\";\" expected");
            }

        }
        else if (sym == SEMICOLON){
            Node n = new Node(EMPTY);
            sym = lexer.nextToken();
        }
        else if (sym == LBRA){
            Node n = new Node(EMPTY);
            sym = lexer.nextToken();
            while (sym != LBRA){
                Node n = new Node(SEQ, n, statement(sym));
            }
            sym = lexer.nextToken();
        }
        else {
            Node n = new Node(EXPR, expr());
            if (sym != SEMICOLON){
                throw new Exception("\";\" expected");
            }
            sym = lexer.nextToken();
        }


        //  sym = lexer.nextToken();
        return n;
    }

    public Node paren_expr(String sym) throws  Exception {
        if (sym != LPAR){
            throw new Exception("\"(\" expected");
        }
        sym = lexer.nextToken();
        Node n = expr(sym);
        if (sym != RPAR){
            throw new Exception("\")\" expected");
        }
        sym = lexer.nextToken();
        return n;
    }
    public Node expr(String sym) throws Exception {
        if(sym != ID){
            return test(sym);
        }
        Node n = test(sym);
        if (n.kind == VAR && sym == EQUAL){
            sym = lexer.nextToken();
             Node node = new Node(SET, n, expr(sym));
        }

        return n;
    }
    public Node test (String sym) throws Exception {
        Node n = summa(sym);
           if(sym == LESS){
               sym = lexer.nextToken();
               Node node = new Node(LT, n, summa(sym));
        }

        return n;
    }
    public Node summa (String sym) throws Exception {
        Node n = term(sym);
        while (sym == PLUS || sym == MINUS) {
            if (sym == PLUS) n.kind = ADD;
            else n.kind = SUB;
            sym = lexer.nextToken();
            Node node = new Node(LT, n, term(sym));
        }
        return n;
    }
    public Node term (String sym) throws Exception {
        if(sym == ID){
            Node n = new Node(VAR, value);
            sym = lexer.nextToken();
            return n;
        }
        else if (sym == NUM){
            Node n = new Node(CONST, value);
            return n;
        }
        else return paren_expr(sym);
    }
//        if self.lexer.sym == L
// exer.IF:
//    n = Node(Parser.IF1)
//    self.lexer.next_tok()
//    n.op1 = self.paren_expr()
//    n.op2 = self.statement()
//            if self.lexer.sym == Lexer.ELSE:
//    n.kind = Parser.IF2
//    self.lexer.next_tok()
//    n.op3 = self.statement()
//    elif self.lexer.sym == Lexer.WHILE:
//    n = Node(Parser.WHILE)
//    self.lexer.next_tok()
//    n.op1 = self.paren_expr()
//    n.op2 = self.statement();
//    elif self.lexer.sym == Lexer.DO:
//    n = Node(Parser.DO)
//    self.lexer.next_tok()
//    n.op1 = self.statement()
//            if self.lexer.sym != Lexer.WHILE:
//            self.error('"while" expected')
//            self.lexer.next_tok()
//    n.op2 = self.paren_expr()
//            if self.lexer.sym != Lexer.SEMICOLON:
//            self.error('";" expected')
//    elif self.lexer.sym == Lexer.SEMICOLON:
//    n = Node(Parser.EMPTY)
//    self.lexer.next_tok()
//    elif self.lexer.sym == Lexer.LBRA:
//    n = Node(Parser.EMPTY)
//    self.lexer.next_tok()
//            while self.lexer.sym != Lexer.RBRA:
//    n = Node(Parser.SEQ, op1 = n, op2 = self.statement())
//            self.lexer.next_tok()
//            else:
//    n = Node(Parser.EXPR, op1 = self.expr())
//            if self.lexer.sym != Lexer.SEMICOLON:
//            self.error('";" expected')
//            self.lexer.next_tok()
//            return n
}
//    def error(self, msg):
//    print 'Parser error:', msg
//    sys.exit(1)
//
//    def term(self):
//            if self.lexer.sym == Lexer.ID:
//    n = Node(Parser.VAR, self.lexer.value)
//    self.lexer.next_tok()
//            return n
//    elif self.lexer.sym == Lexer.NUM:
//    n = Node(Parser.CONST, self.lexer.value)
//    self.lexer.next_tok()
//            return n
//    else:
//            return self.paren_expr()
//
//    def summa(self):
//    n = self.term()
//            while self.lexer.sym == Lexer.PLUS or self.lexer.sym == Lexer.MINUS:
//            if self.lexer.sym == Lexer.PLUS:
//    kind = Parser.ADD
//    else:
//    kind = Parser.SUB
//    self.lexer.next_tok()
//    n = Node(kind, op1 = n, op2 = self.term())
//            return n
//
//    def test(self):
//    n = self.summa()
//            if self.lexer.sym == Lexer.LESS:
//            self.lexer.next_tok()
//    n = Node(Parser.LT, op1 = n, op2 = self.summa())
//            return n
//
//    def expr(self):
//            if self.lexer.sym != Lexer.ID:
//            return self.test()
//    n = self.test()
//            if n.kind == Parser.VAR and self.lexer.sym == Lexer.EQUAL:
//            self.lexer.next_tok()
//    n = Node(Parser.SET, op1 = n, op2 = self.expr())
//            return n
//
//    def paren_expr(self):
//            if self.lexer.sym != Lexer.LPAR:
//            self.error('"(" expected')
//            self.lexer.next_tok()
//    n = self.expr()
//            if self.lexer.sym != Lexer.RPAR:
//            self.error('")" expected')
//            self.lexer.next_tok()
//            return n
//
//    def statement(self):
//            if self.lexer.sym == Lexer.IF:
//    n = Node(Parser.IF1)
//    self.lexer.next_tok()
//    n.op1 = self.paren_expr()
//    n.op2 = self.statement()
//            if self.lexer.sym == Lexer.ELSE:
//    n.kind = Parser.IF2
//    self.lexer.next_tok()
//    n.op3 = self.statement()
//    elif self.lexer.sym == Lexer.WHILE:
//    n = Node(Parser.WHILE)
//    self.lexer.next_tok()
//    n.op1 = self.paren_expr()
//    n.op2 = self.statement();
//    elif self.lexer.sym == Lexer.DO:
//    n = Node(Parser.DO)
//    self.lexer.next_tok()
//    n.op1 = self.statement()
//            if self.lexer.sym != Lexer.WHILE:
//            self.error('"while" expected')
//            self.lexer.next_tok()
//    n.op2 = self.paren_expr()
//            if self.lexer.sym != Lexer.SEMICOLON:
//            self.error('";" expected')
//    elif self.lexer.sym == Lexer.SEMICOLON:
//    n = Node(Parser.EMPTY)
//    self.lexer.next_tok()
//    elif self.lexer.sym == Lexer.LBRA:
//    n = Node(Parser.EMPTY)
//    self.lexer.next_tok()
//            while self.lexer.sym != Lexer.RBRA:
//    n = Node(Parser.SEQ, op1 = n, op2 = self.statement())
//            self.lexer.next_tok()
//            else:
//    n = Node(Parser.EXPR, op1 = self.expr())
//            if self.lexer.sym != Lexer.SEMICOLON:
//            self.error('";" expected')
//            self.lexer.next_tok()
//            return n
//
//    def parse(self):
//            self.lexer.next_tok()
//    node = Node(Parser.PROG, op1 = self.statement())
//            if (self.lexer.sym != Lexer.EOF):
//            self.error("Invalid statement syntax")
//            return node
}
