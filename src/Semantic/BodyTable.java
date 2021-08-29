package Semantic;

public class BodyTable {

    public String name;
    public String type;
    public String access_modifier;
    public String type_modifier;

    @Override
    public String toString() {
        return "BodyTable{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", access_modifier='" + access_modifier + '\'' +
                ", type_modifier='" + type_modifier + '\'' +
                '}';
    }
}
