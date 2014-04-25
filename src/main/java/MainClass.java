import java.io.FileReader;
import java.io.IOException;

public class MainClass {
    public static  void main(String[] args) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("textfile.txt");
            LexicalAnalyzer la = new LexicalAnalyzer(fileReader);
            SyntaxAnalyzer sa = new SyntaxAnalyzer(la);
//            la.analyze();
            sa.parse();
        } catch (Exception e) {
            System.out.println("Exception was caughted: ");
            e.printStackTrace();
        } finally {
            try {
                if (fileReader != null) fileReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
