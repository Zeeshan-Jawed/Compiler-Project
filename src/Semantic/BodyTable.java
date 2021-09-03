package Semantic;

import java.util.Objects;

public class BodyTable {

    public String name;
    public String type;
    public String access_modifier;
    public String type_modifier;


    @Override
    public String toString() {
        return "\n BodyTable{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", access_modifier='" + access_modifier + '\'' +
                ", type_modifier='" + type_modifier + '\'' +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BodyTable bodyTable = (BodyTable) o;
        return Objects.equals(name, bodyTable.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
