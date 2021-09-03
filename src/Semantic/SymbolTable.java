package Semantic;


import java.util.ArrayList;
import java.util.Objects;

public class SymbolTable {
    public String name;
    public String type;
    public String access_modifier;
    public String category;
    public String parent;
    ArrayList<BodyTable > link;

    @Override
    public String toString() {
        return "\n SymbolTable{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", access_modifier='" + access_modifier + '\'' +
                ", category='" + category + '\'' +
                ", parent='" + parent + '\'' +
                ", link=" + link +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SymbolTable that = (SymbolTable) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
