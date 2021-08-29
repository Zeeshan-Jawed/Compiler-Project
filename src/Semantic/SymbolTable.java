package Semantic;


import java.util.ArrayList;

public class SymbolTable {
    public String name;
    public String type;
    public String access_modifier;
    public String category;
    public String parent;
    ArrayList<BodyTable > link;

    @Override
    public String toString() {
        return "SymbolTable{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", access_modifier='" + access_modifier + '\'' +
                ", category='" + category + '\'' +
                ", parent='" + parent + '\'' +
                ", link=" + link +
                '}';
    }
}
