import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LexicalAnalyzer {

    public LexicalAnalyzer(FileReader fileReader) throws IOException {
        symbols = new HashMap<>();
        words = new HashMap<>();
        symbols.put('{', "LBRA");
        symbols.put('}', "RBRA");
        symbols.put('=', "EQUAL");
        symbols.put(';', "SEMICOLON");
        symbols.put('(', "LPAR");
        symbols.put(')', "RPAR");
        symbols.put('+', "PLUS");
        symbols.put('-', "MINUS");
        symbols.put('<', "LESS");
        symbols.put('[', "SRLPAR");
        symbols.put(']', "SRRPAR");

        words.put("if", "IF");
        words.put("else", "ELSE");
        words.put("do", "DO");
        words.put("while", "WHILE");
        words.put("int", "INT");
        words.put("main", "MAIN");
        this.fileReader = fileReader;
        curCh = getChar();
    }

    public Map<Character, String> symbols;
    public Map<String, String> words;
    public final String EOF = "eof";
    public final String NUM = "num";
    public final String ID = "id";
    private int curCh;
    public String sym;
    private FileReader fileReader;


    private int getChar() throws IOException {
        return fileReader.read();
    }

    @SuppressWarnings("unchecked")
    public String nextToken() throws Exception {
        Character ch;
        int value;
        while (true) {
            ch = (char) curCh;
            if (ch=='\r' || ch =='\n'){
                curCh = getChar();
                continue;
            }
            if (curCh == -1) {
                sym = EOF;
                System.out.println("end of file");
                return sym;
            }
            else
            if (Character.isSpaceChar(ch)){
                curCh = getChar();
            }
            else
            if (symbols.keySet().contains(ch)){
                sym = symbols.get(ch);
                curCh = getChar();
                return sym;
            }
            else
            if (Character.isDigit(ch)){
                int val = 0;
                while (Character.isDigit(ch)) {
                    val = val * 10 + Character.getNumericValue(ch);
                    curCh = getChar();
                    ch = (char) curCh;
                }
                value = val;
                sym = NUM +": "+value;
                return sym;
            }
            else
            if (Character.isAlphabetic(ch)){
                String ident = "";
                while (Character.isAlphabetic(ch)){
                    ident = ident + String.valueOf(ch).toLowerCase();
                    curCh = getChar();
                    ch = (char) curCh;
                }
                if (words.keySet().contains(ident)){
                    sym = words.get(ident);
                    return sym;
                }
                else
                if (ident.length() == 1){
                    sym = ID + ": " + ident;
                    return sym;
                }
                else{
                    throw new Exception("Unknown identifier: " + ident);
                }
            }
            else {
                throw new Exception("Unexpected symbol: "+ch);
            }

        }
    }
}
