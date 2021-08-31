package SyntaxAnalyzer;
import Lexical.Token;
import Semantic.BodyTable;
import Semantic.SemanticClass;


import java.util.ArrayList;
import java.util.List;
public class SyntaxClass   {
    String T;
    String N;
    String Am="pvt";
    String Cat;
    String Prnt=null;
    ArrayList<BodyTable> refDt;
    String cTm;
    String cName;
    String cType;
    String Pl;
    String Fname;
    String Ftype;

    static int index=0;
    private final List<Token> token;
    SemanticClass semanticClass =new SemanticClass();

    public SyntaxClass(List<Token> token) {
        this.token=token;
    }
    public void Dtempty(){
        this.cName="";
        this.cTm="";
        this.cType="";
        this.Am="pvt";
        this.Pl="";
    }
    public void empty(){
        this.T="";
        this.N="";
        this.Am="pvt";
        this.Cat="";
        this.Prnt="";
    }
    public void run(){

        if (S()){
            if (token.get(index).CP.equals("$")){
                System.out.println("No Syntax Error");
                System.out.println(semanticClass.mainTable);
                System.out.println(semanticClass.functionTable);


            }
        }
        else {
            System.out.println(semanticClass.mainTable);
            System.out.println(semanticClass.functionTable);
            System.out.println("Syntax Error At Line No.: " + token.get(index).line + " " + token.get(index).CP);
        }
    }
    boolean S(){
        if (token.get(index).CP.equals("interface")||token.get(index).CP.equals("Access Modifier")
                ||token.get(index).CP.equals("const")
                ||token.get(index).CP.equals("conceptual")||token.get(index).CP.equals("class")){
            if(idefs()){
                if (am()) {
                    if (cmod()) {
                        if (token.get(index).CP.equals("class")) {
                            T="class";
                            index++;
                            if (token.get(index).CP.equals("Identifier")) {
                                N=token.get(index).VP;
                                index++;
                                if (inh()) {
                                   refDt=semanticClass.create_DT();
                                    semanticClass.insert_MT(N,T,Am,Cat,Prnt,refDt);
                                    empty();
                                    if (token.get(index).CP.equals("Open Curly")) {
                                        semanticClass.createScope();
                                        index++;
                                        if (cbody()) {
                                            if (token.get(index).CP.equals("Close Curly")) {
                                                semanticClass.destroyScope();
                                                index++;
                                                if (defs()) {

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
            if (interfacedef()){
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
        if (am()){

            if (defs1()){
                return true;
            }
        }
        else if (token.get(index).CP.equals("$")){
            return true;
        }

        return false;
    }
    boolean defs1(){
        if (token.get(index).CP.equals("const")
                ||token.get(index).CP.equals("conceptual")||token.get(index).CP.equals("class")){
            if (classdef()){
                if (defs()){
                    return true;
                }
            }
        }
        else if (token.get(index).CP.equals("interface")){
            if (interfacedef()){
                if (defs()){
                    return true;
                }
            }
        }
        else if (token.get(index).CP.equals("$")){
            return true;
        }
        return false;
    }
    boolean classdef(){
        if (token.get(index).CP.equals("const")
                ||token.get(index).CP.equals("conceptual")||token.get(index).CP.equals("class")){
            if (cmod()){
                if (token.get(index).CP.equals("class")){
                    T="class";
                    index++;
                    if (token.get(index).CP.equals("Identifier")){
                        N=token.get(index).VP;
                        index++;
                        if (inh()){
                            if (implement()){
                               refDt=semanticClass.create_DT();
                                semanticClass.insert_MT(N,T,Am,Cat,Prnt,refDt);
                                empty();
                                if (token.get(index).CP.equals("Open Curly")){
                                    semanticClass.createScope();
                                    index++;
                                    if (cbody()){
                                        if (token.get(index).CP.equals("Close Curly")){
                                            semanticClass.destroyScope();
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
        }
        return false;
    }
    boolean am() {
        if (token.get(index).CP.equals("Access Modifier")) {
            Am=token.get(index).VP;
            index++;
            return true;
        }
        else if(token.get(index).CP.equals("const")
                ||token.get(index).CP.equals("conceptual")||token.get(index).CP.equals("class")
                ||token.get(index).CP.equals("Data Format")||token.get(index).CP.equals("void")||
                token.get(index).CP.equals("static")||token.get(index).CP.equals("interface")){
            return true;
        }
        return false;
    }
    boolean cmod(){
        if (token.get(index).CP.equals("const")
                ||token.get(index).CP.equals("conceptual")){
            Cat=token.get(index).VP;
            index++;

            return true;
        }
        else if(token.get(index).CP.equals("class")){
            Cat="General";
            return true;
        }
        return false;
    }
    boolean cbody(){
        if (token.get(index).CP.equals("Access Modifier")
                ||token.get(index).CP.equals("void")||token.get(index).CP.equals("static")
                ||token.get(index).CP.equals("Data Format")){
            if (am()){
                if (TM()){
                    if (cbody1()){
                        return true;
                    }
                }
            }
        }
       else if(token.get(index).CP.equals("Identifier")){
           if (objdec()){
               if (cbody()){
                   return true;
               }
           }
        }
        else if(token.get(index).CP.equals("conceptual")){
            if (conceptual()){
                if (cbody()){
                    return true;
                }
            }
        }
        else if(token.get(index).CP.equals("ArrayList")){
            if (Arraylist()){
                return true;
            }
        }
        else if(token.get(index).CP.equals("Close Curly")){
            return true;
        }
        return false;
    }
    boolean cbody1(){
        if (token.get(index).CP.equals("void")){
            cType="void";
            index++;
            if (token.get(index).CP.equals("Identifier")){
                cName=token.get(index).VP;
                index++;
                if (fn()){
                    if (cbody()){
                        return true;
                    }
                }
            }
        }
        else if (token.get(index).CP.equals("Data Format")){
            cType=token.get(index).VP;
                index++;
                if (Orarr()){
                    return true;
                }
        }
        else if(token.get(index).CP.equals("Close Curly")){
            return true;
        }
        return false;
    }
    boolean Orarr(){
        if (token.get(index).CP.equals("Identifier")){
            cName=token.get(index).VP;
            index++;
            if (orFn()){
                if (cbody()){
                    return true;
                }
            }
        }
        else if (token.get(index).CP.equals("Open Square")){
            index++;
            if (token.get(index).CP.equals("Close Square")){
                index++;
                if (token.get(index).CP.equals("Identifier")){
                    index++;
                    if (orarrfn()){
                        if (cbody()){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    boolean orarrfn(){
        if (token.get(index).CP.equals("Open Parentheses")){
            if (fn()){
                return true;
            }
        }
        else if (token.get(index).CP.equals("Assign Operator")){
            index++;
            if (arrInit1()){
                if (token.get(index).CP.equals("Semi Colon")) {
                    index++;
                    return true;
                }
            }
        }
        else if (token.get(index).CP.equals("Comma")){
                index++;
                if (token.get(index).CP.equals("Identifier")){
                    index++;
                    if (oArrinit()){
                        if (list2()){
                            return true;
                        }
                    }
                }
        }
        return false;
    }
    boolean fn(){
        if (token.get(index).CP.equals("Open Parentheses")){
            semanticClass.createScope();
            index++;
            if (PL()){
                if (token.get(index).CP.equals("Close Parentheses")){
                    index++;
                    if (token.get(index).CP.equals("Open Curly")){
                        index++;
                        if (MST()){
                            if (token.get(index).CP.equals("Close Curly")){
                                semanticClass.insert_DT(cName,Pl+"=>"+cType,cTm,Am,refDt);
                                Dtempty();
                                semanticClass.destroyScope();
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
    boolean orFn(){
        if (token.get(index).CP.equals("Open Parentheses")){
            semanticClass.createScope();
            if (fn()){
                return true;
            }
        }
        else if (token.get(index).CP.equals("Assign Operator")){
            if (init()){
                if (token.get(index).CP.equals("Semi Colon")) {
                    semanticClass.insert_DT(cName,cType,cTm,Am,refDt);
                    Dtempty();
                    index++;
                    return true;
                }
            }
        }
        else if (token.get(index).CP.equals("Semi Colon")) {
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
    boolean init(){
        if (token.get(index).CP.equals("Assign Operator")) {
            index++;
            if(oArrinit()){
                return true;
            }
        }
        else  if (token.get(index).CP.equals("Semi Colon")||token.get(index).CP.equals("Comma")) {
            return true;
        }
        return false;
    }
    boolean oArrinit(){
        if (token.get(index).CP.equals("this")||token.get(index).CP.equals("Identifier")
                ||token.get(index).CP.equals("Open Parentheses")||token.get(index).CP.equals("Text")
                ||token.get(index).CP.equals("Float") ||token.get(index).CP.equals("Letter")
                ||token.get(index).CP.equals("Whole Number")||token.get(index).CP.equals("Boolean")
                ||token.get(index).CP.equals("IncDec")||token.get(index).CP.equals("Not")){
            if (OE()){
                return true;
            }
        }
        else if (token.get(index).CP.equals("Open Curly")||token.get(index).CP.equals("new")){
                if (arrInit1()){
                    return true;
                }
        }
        else  if (token.get(index).CP.equals("Semi Colon")||token.get(index).CP.equals("Comma")) {
            return true;
        }
        return false;
    }
    boolean arrInit1(){
        if (token.get(index).CP.equals("Open Curly")){
            index++;
            if (arrvar()){
                if (token.get(index).CP.equals("Close Curly")){
                    index++;
                    return true;
                }
            }
        }
        else if (token.get(index).CP.equals("new")){
            index++;
            if (token.get(index).CP.equals("Data Format")){
                index++;
                if (token.get(index).CP.equals("Open Square")){
                    index++;
                    if (OE()){
                        if (token.get(index).CP.equals("Close Square")){
                            index++;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    boolean list(){
        if (token.get(index).CP.equals("Semi Colon")){
            semanticClass.insert_FT(Fname,Ftype);
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
        if (token.get(index).CP.equals("Semi Colon")){
            index++;
            return true;
        }
        else if (token.get(index).CP.equals("Comma")){
            index++;
            if (token.get(index).CP.equals("Identifier")){
                index++;
                if (oArrinit()){
                    if (list2()){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    boolean arrvar(){
        if (token.get(index).CP.equals("this")||token.get(index).CP.equals("Identifier")
                ||token.get(index).CP.equals("Open Parentheses")||token.get(index).CP.equals("Text")
                ||token.get(index).CP.equals("Float") ||token.get(index).CP.equals("Letter")
                ||token.get(index).CP.equals("Whole Number")||token.get(index).CP.equals("Boolean")
        ||token.get(index).CP.equals("IncDec")||token.get(index).CP.equals("Not")){
                if (OE()){
                    if (arrvar1()){
                        return true;
                    }
                }
        }
        else if(token.get(index).CP.equals("Close Curly")){
            return true;
        }
        return false;
    }
    boolean arrvar1(){
        if(token.get(index).CP.equals("Comma")){
            index++;
            if (OE()){
                if (arrvar1()){
                    return true;
                }
            }
        }
        else if(token.get(index).CP.equals("Close Curly")){
            return true;
        }
        return false;
    }
    boolean TM(){
        if (token.get(index).CP.equals("static")){
            cTm="static";
            index++;
            return true;
        }
        else if (token.get(index).CP.equals("Data Format")
                ||token.get(index).CP.equals("void")){
            cTm="null";
            return true;
        }
        return false;
    }

    boolean dec(){
        if (token.get(index).CP.equals("Data Format")){

                if (token.get(index).CP.equals("Data Format")){
                    Ftype=token.get(index).VP;
                    index++;
                    if (arr()){
                        if (token.get(index).CP.equals("Identifier")){
                            Fname=token.get(index).VP;
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
    boolean arr(){
        if (token.get(index).CP.equals("Open Square")){
            index++;
            if (token.get(index).CP.equals("Close Square")){
                index++;
                return true;
            }
        }
        else if (token.get(index).CP.equals("Identifier")){
            return true;
        }
        return false;
    }

    boolean body(){
        if (token.get(index).CP.equals("Semi Colon")){
            index++;
            return true;
        }
        else if (token.get(index).CP.equals("Data Format")||token.get(index).CP.equals("if")
        ||token.get(index).CP.equals("do")||token.get(index).CP.equals("while")
        ||token.get(index).CP.equals("for")||token.get(index).CP.equals("switch")
        ||token.get(index).CP.equals("Identifier")||token.get(index).CP.equals("Bre_Con")
                ||token.get(index).CP.equals("return")
        ||token.get(index).CP.equals("IncDec")||token.get(index).CP.equals("print")
        ||token.get(index).CP.equals("ArrayList")||token.get(index).CP.equals("throw")){
            if (SST()){
                return true;
            }

        }
        else if (token.get(index).CP.equals("Open Curly")){
            index++;
            if (MST()){
                if (token.get(index).CP.equals("Close Curly")){
                    index++;
                    return true;
                }
            }
        }
        return false;
    }
    boolean SST(){
        if (token.get(index).CP.equals("Data Format")||token.get(index).CP.equals("if")
                ||token.get(index).CP.equals("do")||token.get(index).CP.equals("while")
                ||token.get(index).CP.equals("for")||token.get(index).CP.equals("switch")
                ||token.get(index).CP.equals("Identifier")||token.get(index).CP.equals("Bre_Con")
                ||token.get(index).CP.equals("return")
                ||token.get(index).CP.equals("IncDec")||token.get(index).CP.equals("print")
                ||token.get(index).CP.equals("ArrayList")||token.get(index).CP.equals("throw")){
            if (dec()){
                return true;
            }
            else if (if_else()){
                return true;
            }
            else if (for_st()){
                return true;
            }
            else if (dowhile()){
                return true;
            }
            else if (while_st()){
                return true;
            }
            else if (Printst()){
                return true;
            }
            else if (Arraylist()){
                return true;
            }
            else if (swtcase()){
                return true;
            }
            else if (returns()){
                return true;
            }
            else if (_throw()){
                return true;
            }

            else if (token.get(index).CP.equals("Bre_Con")){
                index++;
                if (token.get(index).CP.equals("Semi Colon")){
                    index++;
                    return true;
                }

            }
            else if (token.get(index).CP.equals("IncDec")){
                index++;
                if (token.get(index).CP.equals("Identifier")){
                    index++;
                    if (X()){
                        if(token.get(index).CP.equals("Semi Colon")){
                            return true;
                        }
                    }
                }

            }
            else if (token.get(index).CP.equals("Identifier")){
                index++;
                if (other()){
                    return true;
                }
            }

        }
        return false;
}
boolean other() {
    if (token.get(index).CP.equals("Identifier") || token.get(index).CP.equals("dot")
            || token.get(index).CP.equals("Open Square") || token.get(index).CP.equals("Open Parentheses")
            || token.get(index).CP.equals("IncDec") || token.get(index).CP.equals("Assign Operator")) {
        if (token.get(index).CP.equals("Identifier")) {
            index++;
            if (token.get(index).CP.equals("Assign Operator")) {
                index++;
                if (token.get(index).CP.equals("new")) {
                    index++;
                    if (token.get(index).CP.equals("Identifier")) {
                        index++;
                        if (token.get(index).CP.equals("Open Parentheses")) {
                            index++;
                            if (PL()) {
                                if (token.get(index).CP.equals("Close Parentheses")) {
                                    index++;
                                    if (token.get(index).CP.equals("Semi Colon")) {
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
        else if (Y()){
            if (token.get(index).CP.equals("Open Parentheses")) {
                index++;
                if (PL()) {
                    if (token.get(index).CP.equals("Close Parentheses")) {
                        index++;
                        if (token.get(index).CP.equals("Semi Colon")) {
                            index++;
                            return true;
                        }
                    }
                }
            }
        }
        else if (X()) {
            if (other2()) {
                if (token.get(index).CP.equals("Semi Colon")) {
                    index++;
                    return true;
                }
            }
        }


    }
    return false;
}
boolean other2(){
    if (token.get(index).CP.equals("Assign Operator")) {
        index++;
        if(OE()){
            return true;
        }
    }
    if (token.get(index).CP.equals("IncDec")){
        index++;
        return true;
    }
    return false;
}
boolean MST(){
        if (token.get(index).CP.equals("Data Format")||token.get(index).CP.equals("if")
                ||token.get(index).CP.equals("do")||token.get(index).CP.equals("while")
                ||token.get(index).CP.equals("for")||token.get(index).CP.equals("switch")
                ||token.get(index).CP.equals("Identifier")||token.get(index).CP.equals("Bre_Con")
                ||token.get(index).CP.equals("return")
                ||token.get(index).CP.equals("IncDec")||token.get(index).CP.equals("print")
                ||token.get(index).CP.equals("ArrayList")||token.get(index).CP.equals("throw")){
            if (SST()){
                if (MST()){
                    return true;
                }
            }
        }
        else if (token.get(index).CP.equals("Close Curly")){
            return true;
        }
        return false;
}
    boolean if_else(){
        if (token.get(index).CP.equals("if")){
            index++;
            if(token.get(index).CP.equals("Open Parentheses")){
                index++;
                if (OE()){
                    if (token.get(index).CP.equals("Close Parentheses")){
                        index++;
                        if (body()){
                            if (Oelse()){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    boolean Oelse(){
        if (token.get(index).CP.equals("else")){
            index++;
            if (body()){
                return true;
            }
        }
        else if (token.get(index).CP.equals("Data Format")||token.get(index).CP.equals("Access Modifier")
                ||token.get(index).CP.equals("IncDec")||token.get(index).CP.equals("throw")
                ||token.get(index).CP.equals("while")||token.get(index).CP.equals("do")
                ||token.get(index).CP.equals("Close Curly")||token.get(index).CP.equals("if")
                ||token.get(index).CP.equals("return")
                ||token.get(index).CP.equals("Bre_Con")){

            return true;
        }
        return false;
    }
    boolean for_st(){
        if (token.get(index).CP.equals("for")){
            index++;
            if (token.get(index).CP.equals("Open Parentheses")){
                index++;
                if (C1()){
                    if (C2()){
                        if (token.get(index).CP.equals("Semi Colon")){
                            index++;
                            if (C3()){
                                if (token.get(index).CP.equals("Close Parentheses")){
                                    index++;
                                    if (body()){
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
    boolean C1(){
        if (token.get(index).CP.equals("Data Format")){
            if (dec()){
                return true;
            }
        }
        else if (token.get(index).CP.equals("Identifier")){
            if (assin_st()){
                return true;
            }
        }
        else if (token.get(index).CP.equals("Semi Colon")){
            index++;
            return true;
        }
        return false;
    }
    boolean C2(){

        if (OE()){
            return true;
        }

        else if (token.get(index).CP.equals("Semi Colon")){

            return true;
        }
        return false;
    }
    boolean C3(){
        if (token.get(index).CP.equals("Identifier")){
            index++;
            if (X()){
                if (C3_1()){
                    return true;
                }
            }
        }
        else if (token.get(index).CP.equals("IncDec")){
            index++;
            if (INC_DEC_ST1()){
               return true;
            }
        }
        else if (token.get(index).CP.equals("Close Parentheses")){
            return true;
        }
        return false;
    }
    boolean C3_1(){
        if (ASSIGN_OPR()){
            if (ASSIGN_OPR()) {
                if (OE()){
                    return true;

                }
            }
        }
        else if (INC_DEC_ST()){

            return true;
        }
        return false;
    }
    boolean assin_st(){
        if (token.get(index).CP.equals("Identifier")){
            index++;
            if (X()){
                if (ASSIGN_OPR()){
                    if (OE()){
                        if (token.get(index).CP.equals("Semi Colon")){
                            index++;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    boolean INC_DEC_ST(){
        if (token.get(index).CP.equals("IncDec")){
            index++;
            if (INC_DEC_ST1()){
                return true;
            }
        }

        return false;
    }
    boolean INC_DEC_ST1(){
        if (token.get(index).CP.equals("Identifier")){
            index++;
            if (X()){
                return true;
            }
        }
        else if (token.get(index).CP.equals("Close Parentheses")){
            return true;
        }
        return false;
    }
    boolean ASSIGN_OPR(){
        if (token.get(index).CP.equals("Assign Operator")||token.get(index).CP.equals("Com-Ass")){
            if (token.get(index).CP.equals("Assign Operator")){
                index++;
                return true;
            }
            else if (token.get(index).CP.equals("Com-Ass")){
                index++;
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
                if (OE()){
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
                if (MST()){
                    if (token.get(index).CP.equals("Close Curly")){
                        index++;
                        if (token.get(index).CP.equals("while")){
                            index++;
                            if (token.get(index).CP.equals("Open Parentheses")){
                                index++;
                                if (OE()){
                                    if (token.get(index).CP.equals("Close Parentheses")){
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
                if (OE()){
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
                        if (token.get(index).CP.equals("Bre_Con")){
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
        else if (token.get(index).CP.equals("default")||token.get(index).CP.equals("Close Curly")){
            return true;
        }
        return false;
    }
    boolean defaultt(){
        if (token.get(index).CP.equals("default")){
            index++;
            if (token.get(index).CP.equals("Colon")){
                index++;
                if (body()){
                    return true;
                }
            }
        }
        else if(token.get(index).CP.equals("Close Curly")) {
            return true;
        }
        return false;
    }
    boolean _throw(){
        if (token.get(index).CP.equals("throw")){
            index++;
            if (token.get(index).CP.equals("new")){
                index++;
                if (token.get(index).CP.equals("Error")){
                    index++;
                    if (token.get(index).CP.equals("Text")){
                        index++;
                        if (token.get(index).CP.equals("Semi Colon")){
                            index++;
                            return true;
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
                ||token.get(index).CP.equals("Boolean")){
            if (OE()){
                if (returns1()){
                    return true;
                }
            }
        }
        return false;
    }
    boolean Arraylist(){
        if (token.get(index).CP.equals("ArrayList")){
            index++;
            if (token.get(index).CP.equals("<")){
                index++;
                if (token.get(index).CP.equals("Data Format")){
                    index++;
                    if (token.get(index).CP.equals(">")){
                        index++;
                        if (token.get(index).CP.equals("Identifier")){
                            index++;
                            if (token.get(index).CP.equals("Assign Operator")){
                                index++;
                                if (token.get(index).CP.equals("new")){
                                    index++;
                                    if (token.get(index).CP.equals("ArrayList")) {
                                        index++;
                                        if (token.get(index).CP.equals("<")) {
                                            index++;
                                            if (token.get(index).CP.equals("Data Format")) {
                                                index++;
                                                if (token.get(index).CP.equals(">")) {
                                                    index++;
                                                    if (token.get(index).CP.equals("Open Parentheses")){
                                                        index++;
                                                        if (token.get(index).CP.equals("Close Parentheses")){
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
    boolean Printst(){
        if (token.get(index).CP.equals("print")){
            index++;
            if (token.get(index).CP.equals("Open Parentheses")){
                index++;
                if (token.get(index).CP.equals("Identifier")||token.get(index).CP.equals("Text")
                        ||token.get(index).CP.equals("Letter")||token.get(index).CP.equals("Whole Number")
                        ||token.get(index).CP.equals("Float")||token.get(index).CP.equals("Boolean")){
                    index++;
                    if (token.get(index).CP.equals("Close Parentheses")){
                        index++;
                        if (token.get(index).CP.equals("Semi Colon")){
                            index++;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    boolean conceptual(){
        if (token.get(index).CP.equals("conceptual")){
            index++;
            if (token.get(index).CP.equals("void")){
                index++;
                if (token.get(index).CP.equals("Identifier")){
                    index++;
                    if (token.get(index).CP.equals("Open Parentheses")) {
                        index++;
                        if (PL()) {
                            if (token.get(index).CP.equals("Close Parentheses")) {
                                index++;
                                if ((token.get(index).CP.equals("Semi Colon"))){
                                    index++;
                                    return true;
                                }
                            }
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
    boolean implement(){
        if (token.get(index).CP.equals("implement")){
            index++;
            if (token.get(index).CP.equals("Identifier")){
                String N=token.get(index).VP;
                T=semanticClass.lookup_MT(N);
                if (T.equals("null")){
                    System.out.println(N+" Undeclared");
                }
                else if(T.equals("class")){
                    System.out.println(N+" cannot be implement");
                }
                else Prnt=N;
                index++;
                if (implement1()){
                    return true;
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
                String N=token.get(index).VP;
                T=semanticClass.lookup_MT(N);
                if (T.equals("null")){
                    System.out.println(N+" Undeclared");
                }
                else if(T.equals("class")){
                    System.out.println(N+" cannot be implement");
                }
                else if(Prnt.equals(N)){
                    System.out.println("Same interface implement twice");
                }
                else Prnt+=","+N;
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
    boolean objdec (){
        if (token.get(index).CP.equals("Identifier")){
            index++;
            if(token.get(index).CP.equals("Identifier")){
                index++;
                if(token.get(index).CP.equals("Assign Operator")){
                    index++;
                    if(token.get(index).CP.equals("new")){
                        index++;
                        if(token.get(index).CP.equals("Identifier")){
                            index++;
                            if (token.get(index).CP.equals("Open Parentheses")) {
                                index++;
                                if (PL()) {
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
    boolean inh(){
        if (token.get(index).CP.equals("inherit")) {
            index++;
            if (token.get(index).CP.equals("Identifier")){
                String N=token.get(index).VP;
                T=semanticClass.lookup_MT(N);
                if (T.equals("null")){
                    System.out.println(N+" Undeclared");
                }
                else if(T.equals("class")&&semanticClass.Cat.equals("const")){
                    System.out.println("Const class cannot be inherited ");
                }
                else Prnt=N;
                index++;
                return true;
            }
        }
        else if(token.get(index).CP.equals("implement")||token.get(index).CP.equals("Open Curly")){
            return true;
        }
        return false;
    }
    boolean interfacedef(){
        if (token.get(index).CP.equals("interface")){
            T=token.get(index).VP;
            index++;
            if (token.get(index).CP.equals("Identifier")){
                N=token.get(index).VP;
                refDt=semanticClass.create_DT();
                semanticClass.insert_MT(N,T,Am,Cat,Prnt,refDt);
                empty();
                index++;

                if (token.get(index).CP.equals("Open Curly")){
                    semanticClass.createScope();
                    index++;
                    if (interfacebody()){
                        if (token.get(index).CP.equals("Close Curly")){
                            semanticClass.destroyScope();
                            index++;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    boolean interfacebody(){
        if (token.get(index).CP.equals("conceptual")){
            if (conceptual()){
                if (interfacebody()){
                    return true;
                }
            }
        }
        else if(dec()){
            if (interfacebody()){
                return true;
            }
        }
        else if (token.get(index).CP.equals("Close Curly")){

            return true;
        }
        return false;
    }
    boolean PL(){
        if (token.get(index).CP.equals("Data Format")){
            Pl=token.get(index).VP;
            index++;
            if (arr()){
                if (token.get(index).CP.equals("Identifier")){
                    index++;
                    if (PL2()){
                        return true;
                    }
                }
            }
        }
        else if (token.get(index).CP.equals("Identifier")){
            index++;
            if (arr()){
                if (token.get(index).CP.equals("Identifier")){
                    index++;
                    if (PL2()){
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
    boolean PL2(){
        if (token.get(index).CP.equals("Comma")){
            index++;
            if (token.get(index).CP.equals("Data Format")){
                Pl+=","+token.get(index).VP;
                index++;
                if (token.get(index).CP.equals("Identifier")){
                    index++;
                    if (PL2()){
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
    boolean Y(){
        if (token.get(index).CP.equals("dot")){
            index++;
            if ((token.get(index).CP.equals("Identifier"))){
                index++;
                if (Y()){
                    return true;
                }
            }
        }
        else if(token.get(index).CP.equals("Open Square")){
            index++;
            if (OE()){
                if (token.get(index).CP.equals("Close Square")){
                    index++;
                    if (token.get(index).CP.equals("dot")){
                        index++;
                        if ((token.get(index).CP.equals("Identifier"))){
                            index++;
                            if (Y()){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        else if(token.get(index).CP.equals("Open Parentheses")){
            return true;
        }
        return false;
    }
    boolean X(){
        if(token.get(index).CP.equals("dot")||token.get(index).CP.equals("Open Parentheses")
                ||token.get(index).CP.equals("Open Curly")||token.get(index).CP.equals("Open Square")){
            if(token.get(index).CP.equals("dot")){
                index++;
                if (token.get(index).CP.equals("Identifier")){
                    index++;
                    if (X()){
                        return true;
                    }
                }

            }
            else if (token.get(index).CP.equals("Open Square")){
                index++;
                if (OE()){
                    if (token.get(index).CP.equals("Close Square")){
                        index++;
                        if(token.get(index).CP.equals("dot")) {
                            index++;
                            if (token.get(index).CP.equals("Identifier")) {
                                index++;
                                if (X()) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            else if (token.get(index).CP.equals("Open Parentheses")){
                index++;
                if (PL()){
                    if (token.get(index).CP.equals("Close Parentheses")){
                        index++;
                        if (token.get(index).CP.equals("dot")){
                            index++;
                            if (token.get(index).CP.equals("Identifier")){
                                index++;
                                if (X()){
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        else if (token.get(index).CP.equals("Access Modifier")||token.get(index).CP.equals("Data Format")
                ||token.get(index).CP.equals("do")||token.get(index).CP.equals("while")
                ||token.get(index).CP.equals("switch")||token.get(index).CP.equals("Close Square")
                ||token.get(index).CP.equals("Close Curly") ||token.get(index).CP.equals("Close Parentheses")
                ||token.get(index).CP.equals("Semi Colon")||token.get(index).CP.equals("Comma")
                ||token.get(index).CP.equals("IncDec")||token.get(index).CP.equals("PM")
                ||token.get(index).CP.equals("MDM")||token.get(index).CP.equals("Or")
                ||token.get(index).CP.equals("And")||token.get(index).CP.equals("Co")
                ||token.get(index).CP.equals("Identifier")||token.get(index).CP.equals("if")
                ||token.get(index).CP.equals("else")||token.get(index).CP.equals("for")
                ||token.get(index).CP.equals("break")||token.get(index).CP.equals("continue")
                ||token.get(index).CP.equals("return")||token.get(index).CP.equals("throw")){
            return true;
        }
        return false;
    }
    boolean OE(){
        if (token.get(index).CP.equals("this")||token.get(index).CP.equals("Identifier")
                ||token.get(index).CP.equals("Open Parentheses")||token.get(index).CP.equals("Text")
                ||token.get(index).CP.equals("Float") ||token.get(index).CP.equals("Letter")
                ||token.get(index).CP.equals("Whole Number")||token.get(index).CP.equals("Boolean")
                ||token.get(index).CP.equals("IncDec")||token.get(index).CP.equals("Not")){
            if (AE()){
                if (OE1()){
                    return true;
                }
            }
        }
        return false;
    }
    boolean OE1(){
        if (token.get(index).CP.equals("Or")){
            index++;
            if (AE()){
                if (OE1()){
                    return true;
                }
            }
        }
        else if (token.get(index).CP.equals("Semi Colon")||token.get(index).CP.equals("Comma")
                ||token.get(index).CP.equals("Close Parentheses")
                ||token.get(index).CP.equals("Close Square")||token.get(index).CP.equals("Close Curly")
        ){
            return true;
        }
        return false;
    }
    boolean AE(){
        if (token.get(index).CP.equals("this")||token.get(index).CP.equals("Identifier")
                ||token.get(index).CP.equals("Open Parentheses")||token.get(index).CP.equals("Text")
                ||token.get(index).CP.equals("Float") ||token.get(index).CP.equals("Letter")
                ||token.get(index).CP.equals("Whole Number")||token.get(index).CP.equals("Boolean")
                ||token.get(index).CP.equals("IncDec")||token.get(index).CP.equals("Not")){
            if (RE()){
                if (AE1()){
                    return true;
                }
            }
        }
        return false;
    }
    boolean AE1(){
        if (token.get(index).CP.equals("And")){
            index++;
            if (RE()){
                if (AE1()){
                    return true;
                }
            }
        }
        else if (token.get(index).CP.equals("Semi Colon")||token.get(index).CP.equals("Comma")

                ||token.get(index).CP.equals("Or")||token.get(index).CP.equals("Close Parentheses")
                ||token.get(index).CP.equals("Close Square")||token.get(index).CP.equals("Close Curly")
        ){
            return true;
        }
        return false;
    }
    boolean RE(){
        if (token.get(index).CP.equals("this")||token.get(index).CP.equals("Identifier")
                ||token.get(index).CP.equals("Open Parentheses")||token.get(index).CP.equals("Text")
                ||token.get(index).CP.equals("Float") ||token.get(index).CP.equals("Letter")
                ||token.get(index).CP.equals("Whole Number")||token.get(index).CP.equals("Boolean")
                ||token.get(index).CP.equals("IncDec")||token.get(index).CP.equals("Not")){
            if (E()){
                if (RE1()){
                    return true;
                }
            }
        }
        return false;
    }
    boolean RE1(){
        if (token.get(index).CP.equals("Co")||token.get(index).CP.equals("<")||token.get(index).CP.equals(">")){
            index++;
            if (E()){
                if (RE1()){
                    return true;
                }
            }
        }
        else if (token.get(index).CP.equals("Semi Colon")||token.get(index).CP.equals("Comma")
                ||token.get(index).CP.equals("And")
                ||token.get(index).CP.equals("Or")||token.get(index).CP.equals("Close Parentheses")
                ||token.get(index).CP.equals("Close Square")||token.get(index).CP.equals("Close Curly")
        ){
            return true;
        }
        return false;
    }
    boolean E(){
        if (token.get(index).CP.equals("this")||token.get(index).CP.equals("Identifier")
                ||token.get(index).CP.equals("Open Parentheses")||token.get(index).CP.equals("Text")
                ||token.get(index).CP.equals("Float") ||token.get(index).CP.equals("Letter")
                ||token.get(index).CP.equals("Whole Number")||token.get(index).CP.equals("Boolean")
                ||token.get(index).CP.equals("IncDec")||token.get(index).CP.equals("Not")){
            if (T()){
                if (E1()){
                    return true;
                }
            }
        }
        return false;
    }
    boolean E1(){
        if (token.get(index).CP.equals("PM")){
            index++;
            if (T()){
                if (E1()){
                    return true;
                }
            }
        }
        else if (token.get(index).CP.equals("Semi Colon")||token.get(index).CP.equals("Comma")
                ||token.get(index).CP.equals("Co")||token.get(index).CP.equals("<")||token.get(index).CP.equals(">")
                ||token.get(index).CP.equals("And")
                ||token.get(index).CP.equals("Or")||token.get(index).CP.equals("Close Parentheses")
                ||token.get(index).CP.equals("Close Square")||token.get(index).CP.equals("Close Curly")
        ){
            return true;
        }
        return false;
    }
    boolean T(){
        if (token.get(index).CP.equals("this")||token.get(index).CP.equals("Identifier")
                ||token.get(index).CP.equals("Open Parentheses")||token.get(index).CP.equals("Text")
                ||token.get(index).CP.equals("Float") ||token.get(index).CP.equals("Letter")
                ||token.get(index).CP.equals("Whole Number")||token.get(index).CP.equals("Boolean")
                ||token.get(index).CP.equals("IncDec")||token.get(index).CP.equals("Not")){
            if (F()){
                if (T1()){
                    return true;
                }
            }
        }
        return false;
    }
    boolean T1(){
        if (token.get(index).CP.equals("MDM")){
            index++;
            if (F()){
                if (T1()){
                    return true;
                }
            }
        }

        else if (token.get(index).CP.equals("Semi Colon")||token.get(index).CP.equals("Comma")
                ||token.get(index).CP.equals("PM")
                ||token.get(index).CP.equals("Co")||token.get(index).CP.equals("<")||token.get(index).CP.equals(">")
                ||token.get(index).CP.equals("And")
                ||token.get(index).CP.equals("Or")||token.get(index).CP.equals("Close Parentheses")
                ||token.get(index).CP.equals("Close Square")||token.get(index).CP.equals("Close Curly")
        ){
            return true;
        }
        return false;
    }
    boolean F(){
        if (token.get(index).CP.equals("this")||token.get(index).CP.equals("Identifier")
                ||token.get(index).CP.equals("Open Parentheses")||token.get(index).CP.equals("Text")
                ||token.get(index).CP.equals("Float") ||token.get(index).CP.equals("Letter")
                ||token.get(index).CP.equals("Whole Number")||token.get(index).CP.equals("Boolean")
                ||token.get(index).CP.equals("IncDec")||token.get(index).CP.equals("Not")){
            if (Th()){
                if (token.get(index).CP.equals("Identifier")){
                    index++;
                    if (F1()){
                        return true;
                    }
                }
            }
            else if (token.get(index).CP.equals("Text")
                    ||token.get(index).CP.equals("Float") ||token.get(index).CP.equals("Letter")
                    ||token.get(index).CP.equals("Whole Number")||token.get(index).CP.equals("Boolean")){
                index++;
                return true;
            }
            else if (token.get(index).CP.equals("Open Parentheses")){
                index++;
                if (OE()){
                    return true;
                }
            }
            else if (token.get(index).CP.equals("IncDec")){
                index++;
                if (token.get(index).CP.equals("Identifier")){
                    index++;
                    return true;
                }
            }
            else if (token.get(index).CP.equals("Not")){
                index++;
                if (F()){
                    return true;
                }
            }
        }
        return false;
    }
    boolean F1(){
        if (token.get(index).CP.equals("Open Parentheses")){
            index++;
            if (PL()){
                if (token.get(index).CP.equals("Close Parentheses")){
                    index++;
                }
            }
        }
        else if (token.get(index).CP.equals("IncDec")){
            index++;
            return true;

        }
        else if (token.get(index).CP.equals("Open Square")){
            index++;
            if (OE()){
                if (token.get(index).CP.equals("Close Square")){
                    index++;
                    return true;
                }
            }
        }

        else if (token.get(index).CP.equals("Semi Colon")||token.get(index).CP.equals("Comma")
                ||token.get(index).CP.equals("MDM")||token.get(index).CP.equals("PM")
                ||token.get(index).CP.equals("Co")
                ||token.get(index).CP.equals("<")||token.get(index).CP.equals(">")
                ||token.get(index).CP.equals("And")
                ||token.get(index).CP.equals("Or")||token.get(index).CP.equals("Close Parentheses")
                ||token.get(index).CP.equals("Close Square")||token.get(index).CP.equals("Close Curly")
                ){
            return true;
        }
        return false;
    }
    boolean Th(){
        if (token.get(index).CP.equals("this")){
            index++;
            if (token.get(index).CP.equals("dot")){
                index++;
                return true;
            }
        }
        else if (token.get(index).CP.equals("Identifier")){
            return true;
        }
        return false;
    }
}
