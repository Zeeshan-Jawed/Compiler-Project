package SyntaxAnalyzer;
import Lexical.Token;
import java.util.List;
public class SyntaxClass {

    static int index=0;
    private final List<Token> token;

    public SyntaxClass(List<Token> token) {
        this.token=token;
    }
    public void run(){
        if (S()){
            if (token.get(index).CP.equals("$")){
                System.out.println("No Syntax Error");
            }
        }
        else System.out.println("Syntax Error At Line No.: "+token.get(index).line+" "+token.get(index).CP);
    }
    boolean S(){
        if (token.get(index).CP.equals("interface")||token.get(index).CP.equals("Access Modifier")
                ||token.get(index).CP.equals("const")
                ||token.get(index).CP.equals("conceptual")||token.get(index).CP.equals("class")){
            if(idefs()){
                if (am()){
                    if (cmod()){
                        if (token.get(index).CP.equals("class")){
                            index++;
                            if (token.get(index).CP.equals("Identifier")){
                                index++;
                                if (inh()){
                                    if (token.get(index).CP.equals("Open Curly")){
                                        index++;
                                        if (cbody()){
                                            if (token.get(index).CP.equals("Close Curly")){
                                                index++;
                                                if (defs()){
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    boolean idefs(){
        if (token.get(index).CP.equals("interface")){
            if (interfac()){
                return true;
            }
        }
        else if (token.get(index).CP.equals("Access Modifier")
                ||token.get(index).CP.equals("const")
                ||token.get(index).CP.equals("conceptual")||token.get(index).CP.equals("class")){
            return true;
        }
        return false;
    }
    boolean defs(){
        if (token.get(index).CP.equals("class")) {
            if (class_def()) {
                if (defs()) {
                    return true;
                }
            }
        }
        else if(token.get(index).CP.equals(("interface"))){
                if(interfac()){
                    if (defs()){
                        return true;
                    }
                }

            }
        else if(token.get(index).CP.equals(("$"))){
                return true;
            }

        return false;
    }
    boolean cmod(){
        if (token.get(index).CP.equals("const")
                ||token.get(index).CP.equals("conceptual")){
            index++;
            return true;
        }
        else if(token.get(index).CP.equals("class")){

            return true;
        }
        return false;
    }
    boolean class_def(){
        if (token.get(index).CP.equals("class")){
            if (token.get(index).CP.equals("class")){
                index++;
                if (token.get(index).CP.equals("Identifier")){
                    index++;
                    if (inh()){
                        if (implement()){
                            if (token.get(index).CP.equals("Open Curly")){
                                index++;
                                if (cbody()){
                                    if (token.get(index).CP.equals("Close Curly")){
                                        index++;
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean cbody(){
        if (token.get(index).CP.equals("Identifier")
                || token.get(index).CP.equals("Data Format")||token.get(index).CP.equals("Access Modifier")){
            if (constdef()){
                if (attdef()){
                    if (cbody()){
                        return true;
                    }
                }
            }
        }
        else if(token.get(index).CP.equals("Data Format")||token.get(index).CP.equals("Access Modifier")){
            if (func_def()){
                if (cbody()){
                    return true;
                }
            }
        }
        else if (token.get(index).CP.equals("class")||token.get(index).CP.equals("Close Curly")){
            return true;
        }

        return false;
    }
    boolean attdef(){
        if (token.get(index).CP.equals("Access Modifier")||token.get(index).CP.equals("Data Format")){
            if (dec()){
                return true;
            }
        }
        else if (token.get(index).CP.equals("Identifier")){
             if (arrdec()){
                return true;
            }
        }
        else if(token.get(index).CP.equals("Identifier")){
            if (objdec()){
                return true;
            }
        }

    return false;
    }
    boolean vardec(){
        if (token.get(index).CP.equals("Access Modifier")||token.get(index).CP.equals("Data Format")
                ){

                if (token.get(index).CP.equals("Data Format")){
                    index++;
                    if (token.get(index).CP.equals("Identifier")){
                        index++;
                        if (list()){
                            return true;
                        }
                    }
                }
            }
        return false;
    }
    boolean am() {
        if (token.get(index).CP.equals("Access Modifier")) {
            index++;
            return true;
        }
        else if(token.get(index).CP.equals("const")
                ||token.get(index).CP.equals("conceptual")||token.get(index).CP.equals("class")
        ||token.get(index).CP.equals("Data Format")||token.get(index).CP.equals("void")){
            return true;
        }
        return false;
    }
    boolean dec(){
       if(token.get(index).CP.equals("Access Modifier")||token.get(index).CP.equals("Data Format")){
           if (am()){
               if (token.get(index).CP.equals("Data Format")){
                   index++;
                   if (token.get(index).CP.equals("Identifier")){
                       index++;
                       if (init()){
                           if (list()){
                               return true;
                           }
                       }
                   }
               }

           }
       }
       return false;
    }
    boolean list(){
        if (token.get(index).CP.equals("Semicolon")){
            index++;
            return true;
        }
        else if (token.get(index).CP.equals("Comma")){
            index++;
            if (token.get(index).CP.equals("Identifier")){
                index++;
                if (init()){
                    if (list()){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    boolean list2(){
        if (token.get(index).CP.equals("Semicolon")){
            index++;
            return true;
        }
        else if (token.get(index).CP.equals("Comma")){
            index++;
            if (token.get(index).CP.equals("Identifier")){
                index++;
                if (list2()){
                    return true;
                }
            }
        }
        return false;
    }
    boolean init(){
        if (token.get(index).CP.equals("Assign Operator")){
            index++;
            if (oe()){
//                index++;
                return true;
            }
        }
        else if(token.get(index).CP.equals("Semicolon")||token.get(index).CP.equals("Comma")){
            return true;
        }
        return false;
    }
    boolean df(){
        if (token.get(index).CP.equals("Data Format")){
            index++;
            return true;
        }
        return false;
    }
    boolean dec_2(){
        if (df()){
            if (token.get(index).CP.equals("Identifier")){
                index++;
                if (init()){
                    if (list()){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    boolean  body(){
        return true;
    }
    boolean sst(){
        return true;
    }

    boolean mst(){
        return true;
    }
    boolean other(){
        return true;
    }
    boolean other2(){
        return true;
    }
    boolean inh(){
        if (token.get(index).CP.equals("inherit")) {
            index++;
            if (token.get(index).CP.equals("Identifier")){
                index++;
                return true;
            }
        }
        else if(token.get(index).CP.equals("implement")||token.get(index).CP.equals("Open Curly")){
            return true;
        }
        return false;
    }
    boolean constdef(){
        if (token.get(index).CP.equals("Identifier")){
            index++;
            if (token.get(index).CP.equals("Open Parentheses")){
                index++;
                if (params()){
                    if (token.get(index).CP.equals("Close Parentheses")){
                        index++;
                        if (token.get(index).CP.equals("Open Curly")){
                            index++;
                            if (mst()){
                                if (token.get(index).CP.equals("Close Curly")){
                                    index++;
                                }
                            }
                        }
                    }
                }
            }
        }
        else if (token.get(index).CP.equals("Data Format")||token.get(index).CP.equals("Access Modifier")){
            index++;
            return true;
        }
        return false;
    }
    boolean func_def(){
        if (token.get(index).CP.equals("Access Modifier")
        ||token.get(index).CP.equals("void")){
            if (am()){
                if (token.get(index).CP.equals("void")){
                    index++;
                    if (token.get(index).CP.equals("Identifier")){
                        index++;
                        if (token.get(index).CP.equals("Open Parentheses")){
                            index++;
                            if (params()){
                                if (token.get(index).CP.equals("Close Parentheses")){
                                    index++;
                                    if (token.get(index).CP.equals("Open Curly")){
                                        index++;
                                        if (mst()){
                                            if (token.get(index).CP.equals("Close Curly")){
                                                index++;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
        else if (token.get(index).CP.equals("Data Format")){
//            index++;
            if (ar()){
                if (token.get(index).CP.equals("Identifier")){
                    index++;
                    if (token.get(index).CP.equals("Open Parentheses")){
                        index++;
                        if (params()){
                            if (token.get(index).CP.equals("Close Parentheses")){
                                index++;
                                if (token.get(index).CP.equals("Open Curly")){
                                    index++;
                                    if (mst()){
                                        if (token.get(index).CP.equals("Close Curly")){
                                            index++;
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }

        }
        else if ((token.get(index).CP.equals("Identifier"))){
            index++;
            if (ar()){
                if (token.get(index).CP.equals("Identifier")){
                    index++;
                    if (token.get(index).CP.equals("Open Parentheses")){
                        index++;
                        if (params()){
                            if (token.get(index).CP.equals("Close Parentheses")){
                                index++;
                                if (token.get(index).CP.equals("Open Curly")){
                                    index++;
                                    if (mst()){
                                        if (token.get(index).CP.equals("Close Curly")){
                                            index++;
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
        return false;
    }
    boolean params(){
        if (token.get(index).CP.equals("Data Format")){
            if (ar()){
                if (token.get(index).CP.equals("Identifier")){
                    index++;
                    if (params2()){
                        return true;
                    }
                }
            }
        }
        else if (token.get(index).CP.equals("Identifier")){
            index++;
            if (ar()){
                if (token.get(index).CP.equals("Identifier")){
                    index++;
                    if (params2()){
                        return true;
                    }
                }
            }
        }
        else if (token.get(index).CP.equals("Close Parentheses")){
            return true;
        }
        return false;
    }
    boolean params2(){
        if (token.get(index).CP.equals("Comma")){
            index++;
            if (token.get(index).CP.equals("Data Format")){
                if (token.get(index).CP.equals("Identifier")){
                    index++;
                    if (params2()){
                        return true;
                    }
                }
            }
        }
        else if (token.get(index).CP.equals("Close Parentheses")){
            return true;
        }
        return false;
    }
    boolean ar(){
        if (token.get(index).CP.equals("Open Square")){
            index++;
            return true;
        }
        else if(token.get(index).CP.equals("Identifier")){
            return true;
        }
        return false;
    }
    boolean func_call(){
        if(token.get(index).CP.equals("Identifier")){
            index++;
            if (Y()){
                if (token.get(index).CP.equals("Open Parentheses")) {
                    index++;
                    if (params()) {
                        if (token.get(index).CP.equals("Close Parentheses")) {
                            index++;
                            if (token.get(index).CP.equals("Semi Colon")){
                                index ++;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    boolean Y(){
        return true;
    }

//    boolean for_st(){
//        if (token.get(index).CP.equals("for")){
//            index++;
//            if (token.get(index).CP.equals("Open Parentheses")){
//                index++;
//                if (C1()){
//                    if (C2()){
//                        if (token.get(index).CP.equals("Semi Colon")){
//                            index++;
//                            if (C3()){
//                                if (token.get(index).CP.equals("Close Parentheses")){
//                                    index++;
//                                    if (body()){
//                                        return true;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return false;
//    }
//    boolean C1(){
//        if (token.get(index).CP.equals("Data Format")){
//            if (dec_2()){
//                return true;
//            }
//        }
//        else if (token.get(index).CP.equals("Identifier")){
//            if (assin-st()){
//                return true;
//            }
//        }
//        else if (token.get(index).CP.equals("Semi Colon")){
//            index++;
//            return true;
//        }
//        return false;
//    }
//    boolean C2(){
//        if (){
//            if (oe()){
//                return true;
//            }
//        }
//        else if (token.get(index).CP.equals("Semi Colon")){
//            index++;
//            return true;
//        }
//        return false;
//    }
//    boolean C3(){
//        if (token.get(index).CP.equals("Identifier")){
//            index++;
//            if (X()){
//                if (C3_1()){
//                    return true;
//                }
//            }
//        }
//        else if (token.get(index).CP.equals("IncDec")){
//            index++;
//            if (token.get(index).CP.equals("Identifier")){
//                if (X()){
//                    return true;
//                }
//            }
//        }
//        else if (token.get(index).CP.equals("Close Parentheses")){
//            return true;
//        }
//        return false;
//    }
//    boolean C3_1(){
//        if (){
//            if (ASSIGN_OPR()) {
//                if (oe()){
//                    return true;
//
//                }
//            }
//        }
//        else if (INC_DEC_ST()){
//            index++;
//            return true;
//        }
//        return false;
//    }
    boolean INC_DEC_ST(){
        if (token.get(index).CP.equals("IncDec")){
            if (token.get(index).CP.equals("Identifier")){
                index++;
                if (X()){
                    if (token.get(index).CP.equals("Semi Colon")){
                        return true;
                    }
                }
            }
        }
        else if (token.get(index).CP.equals("Identifier")){
            index++;
            if (X()){
                if (token.get(index).CP.equals("IncDec")){
                    index++;
                    return true;
                }
            }
        }
        return false;
    }
    boolean ASSIGN_OPR(){
        if (token.get(index).CP.equals("Assign Operator")||token.get(index).CP.equals("Com-Ass")){
            index++;
            return true;
        }
        return true;
    }
    boolean X(){
        return true;
    }
    boolean objdec (){
        if(token.get(index).CP.equals("Identifier")){
            index++;
            if(token.get(index).CP.equals("Identifier")){
                index++;
                if(token.get(index).CP.equals("Assign Operator")){
                    index++;
                    if(token.get(index).CP.equals("new")){
                        if(token.get(index).CP.equals("Identifier")){
                            if (token.get(index).CP.equals("Open Parentheses")) {
                                index++;
                                if (params()) {
                                    if (token.get(index).CP.equals("Close Parentheses")) {
                                        index++;
                                        if (token.get(index).CP.equals("Semi Colon")){
                                            index ++;
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    boolean returns(){
        if (token.get(index).CP.equals("return")){
            index++;
            if (returns1()){
                return true;
            }
        }
        return false;
    }
    boolean returns1(){
        if (token.get(index).CP.equals("Semi Colon"))
        {
            index++;
            return true;
        }
        else if(token.get(index).CP.equals("Identifier")
                ||token.get(index).CP.equals("Text")||token.get(index).CP.equals("Float")
                ||token.get(index).CP.equals("Letter")||token.get(index).CP.equals("Whole Number")
                ||token.get(index).CP.equals("true")|token.get(index).CP.equals("false")){
            if (oe()){
                return true;
            }
        }
        return false;
    }
    boolean while_st(){
        if (token.get(index).CP.equals("while")){
            index++;
            if (token.get(index).CP.equals("Open Parentheses")){
                index++;
                if (oe()){
                    if (token.get(index).CP.equals("Close Parentheses")){
                        index++;
                        if (body()){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    boolean dowhile(){
        if (token.get(index).CP.equals("do")){
            index++;
            if (token.get(index).CP.equals("Open Curly")){
                index++;
                if (mst()){
                    if (token.get(index).CP.equals("Close Curly")){
                        index++;
                        if (token.get(index).CP.equals("while")){
                            index++;
                            if (token.get(index).CP.equals("Open Parentheses")){
                                index++;
                                if (oe()){
                                    if (token.get(index).CP.equals("Close Parentheses")){
                                        index++;
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    return false;
    }

    boolean swtcase(){
        if(token.get(index).CP.equals("switch")){
            index++;
            if (token.get(index).CP.equals("Open Parentheses")){
                index++;
                if (oe()){
                    if (token.get(index).CP.equals("Close Parentheses")){
                        index++;
                        if (token.get(index).CP.equals("Open Curly")){
                            index++;
                            if (swbody()){
                                if (defaultt()){
                                    if (token.get(index).CP.equals("Close Curly")){
                                        index++;
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean swbody(){
        if (token.get(index).CP.equals("case")){
            index++;
            if (token.get(index).CP.equals("Identifier")){
                index++;
                if (token.get(index).CP.equals("Colon")){
                    index++;
                    if (body()){
                        if (token.get(index).CP.equals("BRE_CON")){
                            index++;
                            if (token.get(index).CP.equals("Semi Colon")){
                                index++;
                                if (swbody()){
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    boolean defaultt(){
        if (token.get(index).CP.equals("default")){
            index++;
            if (token.get(index).CP.equals("Colon")){
                index++;
                if (token.get(index).CP.equals("Open Curly")){
                    index++;
                    if (oe()){
                        if (token.get(index).CP.equals("Close Curly")){
                            index++;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    boolean arrdec(){
        if (token.get(index).CP.equals("Data Format")){
            if (arrst()){
                if (token.get(index).CP.equals("Identifier")){
                    if (token.get(index).CP.equals("Assign Operator")){
                        if (arrst1()){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    boolean arrst(){
        if (token.get(index).CP.equals("Data Format")){
            if (token.get(index).CP.equals("Open Square")){
                index++;
                if (token.get(index).CP.equals("Close Square")){
                    index++;
                    return true;
                }
            }
        }
        else if (token.get(index).CP.equals("Identifier")){
            index++;
            if (token.get(index).CP.equals("Open Square")){
                index++;
                if (token.get(index).CP.equals("Close Square")){
                    index++;
                    return true;
                }
            }
        }
        return false;
    }
    boolean arrst1(){
        if (token.get(index).CP.equals("Open Curly")){
            index++;
            if (arrvar()){
                if (token.get(index).CP.equals("Close Curly")){
                    index++;
                    if (token.get(index).CP.equals("Semi Colon")){
                        index++;
                        return true;
                    }
                }
            }
        }
        else if (token.get(index).CP.equals("new")){
            if (token.get(index).CP.equals("Data Format")){
                if (token.get(index).CP.equals("open Square")){
                    index++;
                    if (oe()){
                        if (token.get(index).CP.equals("Close Square")){
                            index++;
                            if (token.get(index).CP.equals("Semi Colon")){
                                index++;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    boolean arrvar(){
//        if ()    first laganw
        if (oe()){
            return true;
        }
        else if (token.get(index).CP.equals("new")||token.get(index).CP.equals("Close Curly")){
            if (arrvar2()){
                return true;
            }
        }
        return false;
    }
    boolean arrvar2(){
        if (token.get(index).CP.equals("new")){
            index++;
            if (token.get(index).CP.equals("Identifier")){
                index++;
                if (token.get(index).CP.equals("Open Parentheses")) {
                    index++;
                    if (params()) {
                        if (token.get(index).CP.equals("Close Parentheses")) {
                            index++;
                        }
                    }
                }

            }
        }
        else if (token.get(index).CP.equals("Close Curly")){
            return true;
        }
        return false;
    }


    boolean conceptual(){
        if (token.get(index).CP.equals("conceptual")){
            index++;
            if (token.get(index).CP.equals("Open Curly")){
                index++;
                if (func_def()){
                    if (token.get(index).CP.equals("Close Curly")){
                        index++;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean interfac(){
        if (token.get(index).CP.equals("interface")){
            index++;
            if (token.get(index).CP.equals("Identifier")){
                index++;
                if (token.get(index).CP.equals("Open Curly")){
                    index++;
                    if (func_def()){
                        if (token.get(index).CP.equals("Close Curly")){
                            index++;
                            return true;
                        }
                    }
                }
            }
        }
        return true;
    }

    boolean implement(){
        if (token.get(index).CP.equals("implement")){
            index++;
            if (token.get(index).CP.equals("Identifier")){
                if (implement1()){

                }
            }
        }
        else if (token.get(index).CP.equals("Open Curly")){
            return true;
        }
        return false;
    }
    boolean implement1(){
        if (token.get(index).CP.equals("Comma")){
            index++;
            if (token.get(index).CP.equals("Identifier")){
                index++;
                if (implement()){
                    return true;
                }
            }
        }
        else if (token.get(index).CP.equals("Open Curly")){
            return true;
        }
        return false;
    }

    boolean _throw(){
        if (token.get(index).CP.equals("throw")){
            index++;
            if (token.get(index).CP.equals("new")){
                if (token.get(index).CP.equals("Error")){
                    if (token.get(index).CP.equals("Text")){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    boolean oe(){
        if (token.get(index).CP.equals("Identifier")||token.get(index).CP.equals("Text")
        ||token.get(index).CP.equals("Letter")||token.get(index).CP.equals("Whole Number")
        ||token.get(index).CP.equals("Float")||token.get(index).CP.equals("Bool")
                ||token.get(index).CP.equals("IncDec")||token.get(index).CP.equals("Not")
        ||token.get(index).CP.equals("Open Parentheses"))
        {
            if (ae()){
                if (oe1()){
                    return true;
                }
            }
        }
        return false;
    }
    boolean oe1(){
        if (token.get(index).CP.equals("Or")){
            index++;
            if (ae()){
                if (oe1()){
                    return true;
                }
            }
        }
        else if (token.get(index).CP.equals("Close Square")
                ||token.get(index).CP.equals("Close Culry") ||token.get(index).CP.equals("Close Parentheses")
                ||token.get(index).CP.equals("Semi Colon")||token.get(index).CP.equals("Comma")
                ||token.get(index).CP.equals("IncDec")){
            return true;
        }
        return false;
    }
    boolean ae(){
        if (token.get(index).CP.equals("Identifier")||token.get(index).CP.equals("Text")
                ||token.get(index).CP.equals("Letter")||token.get(index).CP.equals("Whole Number")
                ||token.get(index).CP.equals("Float")||token.get(index).CP.equals("Bool")
                ||token.get(index).CP.equals("IncDec")||token.get(index).CP.equals("Not")
                ||token.get(index).CP.equals("Open Parentheses")){
            if (re()){
                if (ae1()){
                    return true;
                }
            }
        }
        return false;
    }
    boolean ae1(){
        if (token.get(index).CP.equals("And")){
            index++;
            if (re()){
                if (ae1()){
                    return true;
                }
            }
        }
        else if (token.get(index).CP.equals("Close Square")
                ||token.get(index).CP.equals("Close Culry") ||token.get(index).CP.equals("Close Parentheses")
                ||token.get(index).CP.equals("Semi Colon")||token.get(index).CP.equals("Comma")
                ||token.get(index).CP.equals("IncDec")
                ||token.get(index).CP.equals("Or")
                ){
            return true;
        }
        return false;
    }
    boolean re(){
        if (token.get(index).CP.equals("Identifier")||token.get(index).CP.equals("Text")
                ||token.get(index).CP.equals("Letter")||token.get(index).CP.equals("Whole Number")
                ||token.get(index).CP.equals("Float")||token.get(index).CP.equals("Bool")
                ||token.get(index).CP.equals("IncDec")||token.get(index).CP.equals("Not")
                ||token.get(index).CP.equals("Open Parentheses")){
            if (e()){
                if (re1()){
                    return true;
                }
            }
        }
        return false;
    }
    boolean re1(){
        if (token.get(index).CP.equals("Co")){
            index++;
            if (e()){
                if (re1()){
                    return true;
                }
            }
        }
        else if (token.get(index).CP.equals("Close Square")
                ||token.get(index).CP.equals("Close Culry") ||token.get(index).CP.equals("Close Parentheses")
                ||token.get(index).CP.equals("Semi Colon")||token.get(index).CP.equals("Comma")
                ||token.get(index).CP.equals("IncDec")
                ||token.get(index).CP.equals("Or")
                ||token.get(index).CP.equals("And")){
            return true;
        }
        return false;
    }
     boolean e(){
         if (token.get(index).CP.equals("Identifier")||token.get(index).CP.equals("Text")
                 ||token.get(index).CP.equals("Letter")||token.get(index).CP.equals("Whole Number")
                 ||token.get(index).CP.equals("Float")||token.get(index).CP.equals("Bool")
                 ||token.get(index).CP.equals("IncDec")||token.get(index).CP.equals("Not")
                 ||token.get(index).CP.equals("Open Parentheses")){
             if (t()){
                 if (e1()){
                     return true;
                 }
             }
         }
         return false;
     }
     boolean e1(){
            if (token.get(index).CP.equals("PM")){
                index++;
                if (t()){
                    if (e1()){
                        return true;
                    }
                }
            }
            else if (token.get(index).CP.equals("Close Square")
                    ||token.get(index).CP.equals("Close Culry") ||token.get(index).CP.equals("Close Parentheses")
                    ||token.get(index).CP.equals("Semi Colon")||token.get(index).CP.equals("Comma")
                    ||token.get(index).CP.equals("IncDec")
                    ||token.get(index).CP.equals("Or")
                    ||token.get(index).CP.equals("And")||token.get(index).CP.equals("Co")){
                return true;
            }
    return false;
     }
    boolean t(){
        if (token.get(index).CP.equals("Identifier")||token.get(index).CP.equals("Text")
                ||token.get(index).CP.equals("Letter")||token.get(index).CP.equals("Whole Number")
                ||token.get(index).CP.equals("Float")||token.get(index).CP.equals("Bool")
                ||token.get(index).CP.equals("IncDec")||token.get(index).CP.equals("Not")
                ||token.get(index).CP.equals("Open Parentheses")){
            if (f()){
                if (t1()){
                    return true;
                }
            }
        }
        return false;
    }
    boolean t1(){
        if (token.get(index).CP.equals("MDM")){
            index++;
            if (f()){
                if (t1()){
                    return true;
                }
            }
        }
        else if (token.get(index).CP.equals("Close Square")
                ||token.get(index).CP.equals("Close Culry") ||token.get(index).CP.equals("Close Parentheses")
                ||token.get(index).CP.equals("Semi Colon")||token.get(index).CP.equals("Comma")
                ||token.get(index).CP.equals("IncDec")||token.get(index).CP.equals("PM")
                ||token.get(index).CP.equals("Or")
                ||token.get(index).CP.equals("And")||token.get(index).CP.equals("Co")){
            return true;
        }
        return false;
    }
    boolean f(){
        if (token.get(index).CP.equals("Identifier")||token.get(index).CP.equals("Text")
                ||token.get(index).CP.equals("Letter")||token.get(index).CP.equals("Whole Number")
                ||token.get(index).CP.equals("Float")||token.get(index).CP.equals("Bool")
                ||token.get(index).CP.equals("IncDec")||token.get(index).CP.equals("Not")
                ||token.get(index).CP.equals("Open Parentheses")){
            if (token.get(index).CP.equals("Text")
                    ||token.get(index).CP.equals("Letter")||token.get(index).CP.equals("WholeNumber")
                    ||token.get(index).CP.equals("Float")||token.get(index).CP.equals("Bool")){
                return true;
            }
            else if (token.get(index).CP.equals("Open Parentheses")){
                index++;
                if (oe()){
                    if (token.get(index).CP.equals("Close Parentheses")){
                        index++;
                        return true;
                    }
                }

            }
            else if (token.get(index).CP.equals("Not")){
                index++;
                if (f()){
                    return true;
                }
            }
            else if(token.get(index).CP.equals("Identifier")){
                if (func_call()){
                    if (token.get(index).CP.equals("dot")){
                        index++;
                        if (token.get(index).CP.equals("Identifer")){
                            index++;
                            if (x()){
                                return true;
                            }
                        }
                    }
                }

            }
            else if (token.get(index).CP.equals("IncDec")){
                index++;
                if (token.get(index).CP.equals("Identifier")){
                    if (x()){
                        return true;
                    }
                }
            }
            else if (f1()){
                return true;
            }
        }
        return false;
    }
    boolean f1(){
        if (token.get(index).CP.equals("Identifier")){
            index++;
            if (x()){
                return true;
            }
        }
        else if (token.get(index).CP.equals("Close Square")
                ||token.get(index).CP.equals("Close Culry") ||token.get(index).CP.equals("Close Parentheses")
                ||token.get(index).CP.equals("Semi Colon")||token.get(index).CP.equals("Comma")
                ||token.get(index).CP.equals("IncDec")||token.get(index).CP.equals("PM")
                ||token.get(index).CP.equals("MDM")||token.get(index).CP.equals("Or")
                ||token.get(index).CP.equals("And")||token.get(index).CP.equals("Co")){
            return true;
        }
        return false;
    }
    boolean x(){
        return true;
    }
}
