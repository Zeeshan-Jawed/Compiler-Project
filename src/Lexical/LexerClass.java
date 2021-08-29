package Lexical;

import SyntaxAnalyzer.SyntaxClass;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexerClass {

    BufferedReader reader;
    char current;
    List<Token> tokenList = new ArrayList<>();

    public static final String KEY_WORDS[] = new String[]{
            "import", "for", "while", "if", "else", "do",
            "void", "throw", "switch", "case", "class",
            "static", "conceptual", "interface", "package", "new","virtual","override",
             "inherit", "implement", "const","return","ArrayList","default","Error","print"};
    public static final String DATA_FORMAT[] = new String[]{"wn","float","letter","text","bool","email"};
    public static final String ACCESS_MODIFIER[] = new String[]{"pub","pro","pvt"};
    public static final String Escape_Sequences[]= new String[]{"\\n","\\t","\\f"};
    public static final String BRE_CON[] = new String[]{"break","continue"};
    public static final String Bool[] = new String[]{"true","false"};
    public static int line=1;
    public LexerClass(File file) {

        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        current = readNextChar();
    }
    public List<Token> generateTokens() {
        Token token = readNextToken();
        while (token != null) {
            tokenList.add(token);
            token = readNextToken();
        }
        return tokenList;
    }
    Token readNextToken() {

        int state = 1;
        while (true) {
            if (current == (char) (-1)) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            switch (state) {
                case 1: {
                     if ( current == '\r') {
                        current = readNextChar();
                        line++;
                        continue;
                    }
                    else if (current == ' ' || current == '\n' || current == '\t' ||
                            current == '\f' || current == '\b' ) {
                        current = readNextChar();
                        continue;
                    }
                     else if (current == '\\') {
                         current = readNextChar();
                         if (current=='n'){
                             current = readNextChar();
                             return new Token("Escape_Sequences", "\\n",line);
                         }
                         if (current=='t'){
                             current = readNextChar();
                             return new Token("Escape_Sequences", "\\t",line);
                         }
                     }
                    else if (current == ';') {
                        current = readNextChar();
                        return new Token("Semi Colon", ";",line);
                    } else if (current == '{') {
                        current = readNextChar();
                        return new Token("Open Curly", "{",line);
                    } else if (current == '}') {
                        current = readNextChar();
                        return new Token("Close Curly", "}",line);
                    } else if (current == '(') {
                        current = readNextChar();
                        return new Token("Open Parentheses", "(",line);
                    } else if (current == ')') {
                        current = readNextChar();
                        return new Token("Close Parentheses", ")",line);
                    } else if (current == ',') {
                        current = readNextChar();
                        return new Token("Comma", ",",line);
                    }
                     else if (current == ':') {
                         current = readNextChar();
                         return new Token("Colon", ":",line);
                     }else if (current == '[') {
                         current = readNextChar();
                         return new Token("Open Square", "[",line);
                     }
                     else if (current == ']') {
                         current = readNextChar();
                         return new Token("Close Square", "]",line);
                     }


                     else if (current == '+') {
                         current = readNextChar();
                         if(current=='+'){
                             current=readNextChar();
                             return new Token("IncDec", "++",line);
                         }
                         else if(current=='='){
                             current=readNextChar();
                             return new Token("Com-Ass", "+=",line);
                         }
                         return new Token("PM", "+",line);
                     }
                     else if (current == '-') {
                         current = readNextChar();
                         if(current=='-'){
                             current=readNextChar();
                             return new Token("IncDec", "--",line);
                         }
                         else if(current=='='){
                             current=readNextChar();
                             return new Token("Com-Ass", "-=",line);
                         }
                         return new Token("PM", "-",line);
                     }
                     else if (current == '*') {
                         current = readNextChar();
                         if(current=='='){
                             current=readNextChar();
                             return new Token("Com-Ass", "*=",line);
                         }
                         return new Token("MDM", "*",line);
                     }
                     else if (current == '%') {
                         current = readNextChar();
                         if(current=='='){
                             current=readNextChar();
                             return new Token("Com-Ass", "%=",line);
                         }
                         return new Token("MDM", "%",line);
                     }
                     else if (current=='/'){
                         current = readNextChar();
                           if(current=='='){
                             current=readNextChar();
                             return new Token("Com-Ass", "/=",line);
                         }
                         if(current=='/'){
                        for (; ; ) {
                            current = readNextChar();
                            if (current=='\r' ) {
                                break;
                            }
                        }
                        continue;
                         }
                         else if(current=='*'){
                             for (; ; ) {
                                 current = readNextChar();
                                 if (current=='\r'){
                                     line++;
                                 }
                                 if (current=='*' ) {
                                     current=readNextChar();
                                     if (current=='/' ) {
                                         current=readNextChar();

                                         break;
                                     }
                                 }
                             }
                             continue;
                         }else return new Token("MDM", "/",line);
                     }
                    else if (current == '=') {
                        current = readNextChar();
                        if (current == '=') {
                            current = readNextChar();
                            return new Token("Co", "==",line);
                        }
                        else if(current == '>'){
                            current = readNextChar();
                            return new Token("Co", "=>",line);
                        }
                        else {
                            return new Token("Assign Operator", "=",line);
                        }
                    }
                     else if (current == '<') {
                         current = readNextChar();
                         if (current=='='){
                             current = readNextChar();
                             return new Token("Co", "<=",line);
                         }
                         else return new Token("<", "<",line);
                     }
                     else if (current == '>') {
                         current = readNextChar();
                         return new Token(">", ">",line);
                     }
                    else if (current == '!') {
                        current = readNextChar();
                        if (current == '=') {
                            current = readNextChar();
                            return new Token("Co ", "!=",line);
                        } else return new Token("Not", "!",line);
                    }else if(current == '&'){
                        current = readNextChar();
                        if(current == '&'){
                            current = readNextChar();
                            return new Token(" And","&&",line);
                        }else return new Token("Not Defined","&",line);
                    }else if(current == '|') {
                        current = readNextChar();
                        if (current == '|') {
                            current = readNextChar();
                            return new Token("Or", "||",line);
                        } else return new Token("Not Defined", "|",line);
                    } else {
                        state = 2;
                        continue;
                    }

                }
                case 2: {

                    if(isfloat(String.valueOf(current))){
                        String num = String.valueOf(current);
                        while (current!='.'){
                            if (isNumber(current)){
                                current = readNextChar();
                                if(isNumber(current)||current=='.'){
                                    num += String.valueOf(current);
                                }
                            }
                            else{
                                return new Token("Whole Number", num,line);
                            }
                        }
                        if (current=='.'){
                            current = readNextChar();
                            num += String.valueOf(current);
                            if (isfloat(num)){
                                for (; ;){
                                    current = readNextChar();
                                    if (isNumber(current)){
                                        num += String.valueOf(current);

                                    }
                                    else if (String.valueOf(current).equals("e")||String.valueOf(current).equals("E")){
                                        num += String.valueOf(current);
                                        current = readNextChar();
                                        if (String.valueOf(current).equals("+")||String.valueOf(current).equals("-")){
                                            num += String.valueOf(current);
                                            current = readNextChar();
                                            num += String.valueOf(current);
                                            if (isfloat(num)){
                                                for (; ;){
                                                    String temp=num;
                                                    current = readNextChar();
                                                    temp +=String.valueOf(current);
                                                    if (isfloat(temp)){
                                                        num += String.valueOf(current);
                                                    }
                                                    else if (!isfloat(temp)){

                                                        return new Token("Float",num,line);
                                                    }

                                                }
                                            }
                                        }

                                    }
                                   else return new Token("Float",num,line);
                                }

                            }else return new Token("dot",".",line);
                        }
                    }

                    else state = 3;
                }
                case 3: {
                    if (String.valueOf(current).equals("'")){
                        String word = String.valueOf(current);
                        for (; ;){
                            current = readNextChar();
                            if (current!='\r'){
                                word += String.valueOf(current);
                            }
                            if (String.valueOf(current).equals("'")){
                                current = readNextChar();
                                if (isletter(word)){
                                    return new Token("Letter", word,line);
                                }
                            }
                            else if (current=='\r'){
                                current = readNextChar();
                                return new Token("Unexpected Token", word,line);
                            }
                        }
                    }
                    if (current=='"'){
                        String word = String.valueOf(current);

                        for (; ;){
                            current = readNextChar();
                            word += String.valueOf(current);
                            if (current=='"'){
                                current=readNextChar();
                                return new Token("Text", word,line);
                            }
                            if (current=='\r'){
                                break;
                            }
                        }
                    }
                    if (isText(current) || current == '_') {
                        String word = String.valueOf(current);
                        for (; ; ) {
                            current = readNextChar();
                            if (isText(current) || current == '_' || isNumber(current)) {
                                word += String.valueOf(current);
                            } else {
                                List key_words = Arrays.asList(KEY_WORDS);
                                List data_format=Arrays.asList(DATA_FORMAT);
                                List access_modifier=Arrays.asList(ACCESS_MODIFIER);
                                List bre_con=Arrays.asList(BRE_CON);
                                List bool=Arrays.asList(Bool);

                                if (identifier(word)){
                                    if (key_words.contains(word)){
                                        return new Token(word, word,line);}
                                    else if (data_format.contains(word)){
                                        return new Token("Data Format", word,line);}
                                    else if (access_modifier.contains(word)){
                                        return new Token("Access Modifier", word,line);}
                                    else if ( bre_con.contains(word)){
                                        return new Token("Bre_Con", word,line);}
                                    else if (bool.contains(word)){
                                        return new Token("Boolean", word,line);}

                                    return new Token("Identifier", word,line);
                                }
                            }
                        }
                    } else {

                        char temp=current;
                        current = readNextChar();
                        return new Token("Unexpected Token", String.valueOf(temp),line);
                    }
                }
            }
        }


    }


    boolean identifier(String word) {
        Pattern pattern = Pattern.compile("^([a-zA-Z_0-9][a-zA-Z\\d_$]*)$");
        Matcher matcher = pattern.matcher(word);
        boolean matchFound = matcher.find();
        if(matchFound) {
            return true;
        } else {
           return false;
        }
    }

    char readNextChar() {
        try {
            return (char) reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (char) (-1);
    }

    boolean isNumber(char c) {
        if (c >= '0' && c <= '9')
            return true;

        return false;
    }
    boolean isletter(String w) {
        Pattern pattern = Pattern.compile("^('\\\\?.'*)$");
        Matcher matcher = pattern.matcher(w);
        boolean matchFound = matcher.find();
        if(matchFound) {
            return true;
        } else {
            return false;
        }
    }
    boolean isfloat(String w){
        Pattern pattern = Pattern.compile("^[-+]?[0-9]*\\.?[0-9]?([eE]?[+-]?[0-9]+)?$");
        Matcher matcher = pattern.matcher(w);
        boolean matchFound = matcher.find();
        if(matchFound) {
            return true;
        } else {
            return false;
        }
    }
    boolean isText(char c) {
        if (c >= 'a' && c <= 'z')
            return true;
        if (c >= 'A' && c <= 'Z')
            return true;
        return false;

    }
}
