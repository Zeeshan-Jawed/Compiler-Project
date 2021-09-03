package Semantic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SemanticClass {
    public  String Cat;
    public  String Am="pvt";

    public  String Tm;
    public ArrayList<BodyTable> lk;

    static int index=0;
    Stack <Integer> scope=new Stack<>();
    public List<SymbolTable> mainTable =new ArrayList<SymbolTable>();
   public List<FunctionTable> functionTable =new ArrayList<FunctionTable>();

    public ArrayList<BodyTable>create_DT(){

        ArrayList<BodyTable> bodyTable =new ArrayList<BodyTable>();
        return bodyTable;

    }
    public boolean insert_MT(String name,String type,String access_modifier,
                             String category,String parent,ArrayList<BodyTable> link){
       SymbolTable obj =new SymbolTable();
        obj.name=name;
        obj.type=type;
        obj.access_modifier=access_modifier;
        obj.category=category;
        obj.parent=parent;
        obj.link=link;
        if (!mainTable.contains(obj)){
            mainTable.add(obj);
            return true;
        }
        else {
            System.out.println("Redeclaration "+obj.type+":"+obj.name);
            return false;
        }

    }

    public boolean insert_DT(String name,String type,String typemodifier,String accessmodifier,ArrayList<BodyTable> link){
        BodyTable bodyTable=new BodyTable();
        bodyTable.name=name;
        bodyTable.type=type;
        bodyTable.type_modifier=typemodifier;
        bodyTable.access_modifier=accessmodifier;
        if (!link.contains(bodyTable)){
            link.add(bodyTable);
            return true;
        }
        else {
            System.out.println("Variable '"+bodyTable.name+"' is already defined in this scope");
            return false;
        }
    }

    public boolean insert_FT(String name,String type){
        FunctionTable functionTable=new FunctionTable();
        functionTable.name=name;
        functionTable.type=type;
        functionTable.scope=index;
        if (!this.functionTable.contains(functionTable)){
            this.functionTable.add(functionTable);
            return true;
        }
        else {
            System.out.println("Variable '"+functionTable.name+"' is already defined in this scope");
            return false;
        }
    }

    public String lookup_MT(String name){
        for (SymbolTable var : mainTable)
        {
            if (var.name.equals(name)){
                Am=var.access_modifier;
                Cat=var.category;
                lk= var.link;
                return var.type;

            }
        }
        return "null";
    }
    String lookup_att_DT(String name,ArrayList<BodyTable>link){
        for (BodyTable var :link){
            if (var.name.equals(name)){
                return var.type;
            }
        }
        return "null";
    }
    public String lookup_fn_DT(String name,ArrayList<BodyTable>link){
        for (BodyTable var :link){
            if (var.name.equals(name)){
                return var.type;
            }
        }
        return "null";
    }
   public String lookup_FT(String name){
        for (FunctionTable var :functionTable){
            if (var.name.equals(name)){
                return var.type;
            }
        }
            return "null";
    }
    public String compatibility(String T1,String T2,String opr){
        String type;
        if (opr.equals("=")) {
            if (T1.equals(T2)) {
                type = T1;
                return type;
            }
            else
            {
                if (T1.equals("float") && (T2.equals("wn") )) {
                    type = T1;
                    return type;
                }
            }
        }
        else if (opr.equals("+")){
            if (T1.equals(T2)) {
                type = T1;
                return type;
            }
            else
            {
                if (T1.equals("float") && T2.equals("wn")) {
                    type = "float";
                    return type;
                }
            }
        }
        else if (opr.equals("-")){
            if (!T1.equals("text") && !T2.equals("text")){
                if (T1.equals(T2)) {
                    type = T1;
                    return type;
                }
                else
                {
                    if (T1.equals("float") && T2.equals("wn")) {
                        type = "float";
                        return type;
                    }
                }
            }
            else if (T1.equals("text") && T2.equals("text")){
                return "Operator / cannot be applied to 'Text'";
            }
        }
        else if (opr.equals("*")){
            if (!T1.equals("text") && !T2.equals("text")){
                if (T1.equals(T2)) {
                    type = T1;
                    return type;
                }
                else
                {
                    if (T1.equals("float") && T2.equals("wn")) {
                        type = "float";
                        return type;
                    }
                }
            }
            else if (T1.equals("text") && T2.equals("text")){
                return "Operator / cannot be applied to 'Text'";
            }
        }
        else if (opr.equals("/")){
            if (!T1.equals("text") && !T2.equals("text")){
                if (T1.equals(T2)) {
                    type = T1;
                    return type;
                }
                else
                {
                    if (T1.equals("float") && T2.equals("wn")) {
                        type = "float";
                        return type;
                    }
                }
            }
           else if (T1.equals("text") && T2.equals("text")){
                return "Operator / cannot be applied to 'Text'";
            }
        }

        return "Type Error";

    }
    void compatibility1(){

    }
   public void createScope(){
        index++;
        scope.add(index);


    }
   public void destroyScope(){
        scope.pop();
    }
}
