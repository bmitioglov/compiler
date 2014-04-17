import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainClass {
    public static  void main(String[] args) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("textfile.txt");
            LexicAnalyzer la = new LexicAnalyzer(fileReader);
            la.analyze();
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
