package Lexical;

public class Token {
    public String VP;
    public String CP;
    public int line;

    public Token(String CP, String VP,int line) {
        this.VP = VP;
        this.CP = CP;
        this.line=line;
    }

    public String toString() {
        return formateOutPut(CP, VP);
    }
    String formateOutPut(String l,String t){
        String outPut=l;
        for(int i=l.length() ; i<16 ; i++){
            outPut+=' ';
        }
        return outPut+ VP +"\t \t"+line;
    }

}