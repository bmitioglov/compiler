import org.junit.Test;

import java.io.FileReader;

public class LexicalAnalyzerTest {

    @Test
    public void lexicalAnalyze() throws Exception {
        FileReader fileReader;
        fileReader = new FileReader("textfile.txt");
        LexicalAnalyzer la = new LexicalAnalyzer(fileReader);
        String token = la.nextToken();
        while (!token.equals(la.EOF)) {
            System.out.println(token);
            token = la.nextToken();
        }
    }
}
