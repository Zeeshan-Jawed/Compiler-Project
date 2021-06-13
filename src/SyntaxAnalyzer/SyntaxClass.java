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
            System.out.println("working");
        }
        else System.out.println("error");
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
        if (token.get(index).CP.equals("class")){
            if (class_def()){
                if (defs()){
                    return true;
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
        }
        return false;
    }
    boolean cmod(){
        if (token.get(index).CP.equals("const")
                ||token.get(index).CP.equals("conceptual")||token.get(index).CP.equals("class")){
            index++;
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
        if (token.get(index).CP.equals("Identifier")){
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
        if (token.get(index).CP.equals("Access Modifier")){
            if (vardec()){
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
            if (token.get(index).CP.equals("Access Modifier")){
                if (token.get(index).CP.equals("Data Format")){
                    if (token.get(index).CP.equals("Identifier")){
                        index++;
                        if (list2()){
                            return true;
                        }
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
        return false;
    }
    boolean dec(){
       if(token.get(index).CP.equals("Access Modifier")||token.get(index).CP.equals("Data Format")){
           if (am()){
               if (token.get(index).CP.equals("Data Format")){
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
        else if(token.get(index).CP.equals("implement")||token.get(index).CP.equals("CloseCurly")){
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
        return false;
    }
    boolean func_def(){
        return true;
    }
    boolean params(){
        return true;
    }
    boolean params2(){
        return true;
    }
    boolean ar(){
        return true;
    }
    boolean func_call(){
        return true;
    }
    boolean Y(){
        return true;
    }
    boolean interfac(){

        return true;
    }

    boolean implement(){
        return true;
    }
    boolean for_st(){
        return true;
    }
    boolean C1(){
        return true;
    }
    boolean C2(){
        return true;
    }
    boolean C3(){
        return true;
    }
    boolean C3_1(){
        return true;
    }
    boolean ASSIGN_OPR(){
        return true;
    }
    boolean X(){
        return true;
    }
    boolean arrdec(){
        return true;
    }
    boolean objdec (){
        return true;
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
        return true;
    }
}
