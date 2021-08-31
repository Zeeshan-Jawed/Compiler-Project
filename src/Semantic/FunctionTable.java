package Semantic;

public class FunctionTable {
    public String name;
    public String type;
    public int scope;

    @Override
    public String toString() {
        return "FunctionTable{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", scope=" + scope +
                '}';
    }
}
