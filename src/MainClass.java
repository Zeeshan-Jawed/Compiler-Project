import Lexical.LexerClass;
import Lexical.Token;
import SyntaxAnalyzer.SyntaxClass;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.List;


public class MainClass {

    public static void main(String[] args) {

        URL Input_url = ClassLoader.getSystemResource("Lexical/input.txt");
        File file = null;

        try {
            file = new File(Input_url.toURI());
            LexerClass lexerClass = new LexerClass(file);
            List<Token> tokenList = lexerClass.generateTokens();

            PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
            Token t= new Token("$"," ",-1);
            tokenList.add(t);
            System.out.println("Token File Generated");
//            JPanel panel = new JPanel(new BorderLayout());
//            final JList list = new JList(tokenList.toArray());
//            JScrollPane scrollPane = new JScrollPane();
//            scrollPane.setViewportView(list);
//            list.setLayoutOrientation(JList.VERTICAL);
//            panel.add(scrollPane);
//            JFrame frame = new JFrame("Tokens");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.add(panel);
//            frame.setSize(600, 450);
//            frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
            SyntaxClass syntaxClass=new SyntaxClass(tokenList);
            syntaxClass.run();
            System.setOut(out);

            System.out.println("Lexical Analyzer");
            for (int i = 0; i < tokenList.size(); i++) {
                System.out.println(tokenList.get(i).toString());
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
