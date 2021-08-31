package Semantic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SemanticClass {
    public  String Cat;
    public  String Am="pvt";
    public  String Tm;
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

    public boolean insert_FT(String name,String type){
        FunctionTable functionTable=new FunctionTable();
        functionTable.name=name;
        functionTable.type=type;
        functionTable.scope=index;
        this.functionTable.add(functionTable);
        return true;
    }
   public boolean insert_DT(String name,String type,String typemodifier,String accessmodifier,ArrayList<BodyTable> link){
        BodyTable bodyTable=new BodyTable();
        bodyTable.name=name;
        bodyTable.type=type;
        bodyTable.type_modifier=typemodifier;
        bodyTable.access_modifier=accessmodifier;
        link.add(bodyTable);
        return true;
    }
    public String lookup_MT(String name){
        for (SymbolTable var : mainTable)
        {
            if (var.name.equals(name)){
                Am=var.access_modifier;
                Cat=var.category;
                return var.type;

            }
        }
        return "null";
    }
    void lookup_att_DT(){

    }
    void lookup_fn_DT(){

    }
    String lookup_FT(String name){
        for (FunctionTable var :functionTable){
            return var.type;
        }
            return "null";
    }
    public void compatibility(){

    }
    void compatibility1(){

    }
   public void createScope(){

       scope.add(index);
//       System.out.println(scope);
       index++;
    }
   public void destroyScope(){
        scope.pop();
    }
}
