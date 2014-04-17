import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LexicAnalyzer {

        public LexicAnalyzer(FileReader fileReader){
            symbols = new HashMap<>();
            words = new HashMap<>();
            symbols.put('{', "LBRA");
            symbols.put('}', "RBRA");
            symbols.put('=', "EQUAL");
            symbols.put(';', "SEMICOLON");
            symbols.put('(', "LPAR");
            symbols.put(')', "RPAR");
            symbols.put('+', "PLUS");
            symbols.put( '-', "MINUS");
            symbols.put('<', "LESS");

            words.put("if", "IF");
            words.put( "else" ,"ELSE");
            words.put( "do"   ,"DO");
            words.put( "while","WHILE");
            this.fileReader = fileReader;
        }

        private FileReader fileReader;
        private Map<Character, String> symbols;
        private Map<String, String> words;
        private final String EOF = "eof";
        private final String NUM = "num";
        private final String ID = "id";
        private final String SPACE = "space";

        public void analyze() throws Exception {
//                int c = getChar();
//                while (c!=-1) {
//                    System.out.print((char) c);
//                    c = getChar();
//                }
                nextToken();
        }

    private int getChar() throws IOException {
        int first = fileReader.read();
        return first;
    }

    @SuppressWarnings("unchecked")
    public void nextToken() throws Exception {
        String sym = " ";
        Character ch;
        int curIntCh = getChar();
        int value;
        while (sym != null){
            ch = (char) curIntCh;
            if (ch=='\r' || ch =='\n'){
                curIntCh = getChar();
                ch = (char) curIntCh;
                sym = "special char";
                System.out.println(sym);
                continue;
            }
            System.out.println("char get = "+ ch);
            if (curIntCh==-1) {
                sym = EOF;
                System.out.println("end of file");
                break;
            }
            else
            if (Character.isSpaceChar(ch)){
                curIntCh = getChar();
                ch = (char) curIntCh;
                sym = SPACE;
            }
            else
            if (symbols.keySet().contains(ch)){
                sym = symbols.get(ch);
                curIntCh = getChar();
                ch = (char) curIntCh;
            }
            else
            if (Character.isDigit(ch)){
                int val = 0;
                while (Character.isDigit(ch)) {
                    val = val * 10 + Character.getNumericValue(ch);//TODO проверить
                    curIntCh = getChar();
                    ch = (char) curIntCh;
                }
                value = val;
                sym = NUM +": "+value;
            }
            else
            if (Character.isAlphabetic(ch)){
                String ident = "";
                while (Character.isAlphabetic(ch)){
                    ident = ident + String.valueOf(ch).toLowerCase();
                    curIntCh = getChar();
                    ch = (char) curIntCh;
                }
                if (words.keySet().contains(ident)){
                    sym = words.get(ident);
                }
                else
                if (ident.length() == 1){
                    sym = ID;
                }
                else{
                    sym = null;
                    throw new Exception("Unknown identifier");
                }
            }
            else {
                sym = null;
                throw new Exception("Unexpected symbol: "+ch);
            }
            System.out.println(sym);

        }
    }
}
